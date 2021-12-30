package it.vvfriva.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.vvfriva.entity.Person;
import it.vvfriva.entity.Servizio;
import it.vvfriva.entity.TurniPianifica;
import it.vvfriva.entity.TurniPianificaEvento;
import it.vvfriva.entity.TurniPianificaSaltoTurno;
import it.vvfriva.entity.Vigile;
import it.vvfriva.enums.EnumsTurni;
import it.vvfriva.managers.GradiManager;
import it.vvfriva.managers.PersonManager;
import it.vvfriva.managers.ServizioManager;
import it.vvfriva.managers.TurniPianificaEventoManager;
import it.vvfriva.managers.TurniPianificaManager;
import it.vvfriva.managers.TurniPianificaSaltoTurnoManager;
import it.vvfriva.managers.VigileManager;
import it.vvfriva.models.JsonResponse;
import it.vvfriva.models.KeyValue;
import it.vvfriva.models.KeyValueInt;
import it.vvfriva.models.ModelPianificazioneTurni;
import it.vvfriva.models.VigileModel;
import it.vvfriva.utils.CostantiVVF;
import it.vvfriva.utils.Messages;
import it.vvfriva.utils.Utils;

@Service
public class TurnoEventoService {
	
	@Autowired PersonManager personManager;
	@Autowired VigileManager vigileManager;
	@Autowired GradiManager gradiManager;
	@Autowired ServizioManager servizioManager;
	@Autowired TurniPianificaEventoManager turniPianificaEventoManager;
	@Autowired TurniPianificaManager turniPianificaManager;
	@Autowired TurniPianificaSaltoTurnoManager turniPianificaSaltoTurnoManager;
	
	
	private final Logger logger =  LoggerFactory.getLogger(this.getClass());
	
	
	/**
	 * Metodo per la generazione del turnario squadre
	 * @param dal
	 * @param al
	 * @return
	 */
	@Transactional
	public JsonResponse<List<ModelPianificazioneTurni>> calc(Date dal, Date al) {
		Boolean success = true;
		String message = "";
		List<ModelPianificazioneTurni> data = new ArrayList<ModelPianificazioneTurni>();
		JsonResponse<List<ModelPianificazioneTurni>>  result = null;
		try {
			
			Date from = Utils.getMonday(dal);
			Date to = Utils.getSunday(al);
			
			Date startCiclo = Utils.encodeDate(2013, 4, 8);
			
			/* Mi scarico le squadre che sono state definite nelle impostazioni  */
			List<Person> squadre = personManager.list(Arrays.asList(CostantiVVF.AREA_SQUADRE), null, null, false);
			if (Utils.isEmptyList(squadre)) {
				logger.error("Exception in method: " + this.getClass().getCanonicalName() + ".calc sqaudre not defined");
				throw new Exception("Impossibile generare un turnario suqadre non ancora definite");
			}
			int totaleSquadre = squadre.size();
			
			List<KeyValue> turniOriginali = Arrays.asList(EnumsTurni.values())
					.stream()
					.map(tur-> new KeyValue(tur.getId(), tur.getDescr(), null))
					.collect(Collectors.toList());
			
			/* vengono definiti i turni standard  */
			List<KeyValue> turni = null;
			
			KeyValueInt vigileTurno = null;
			TurniPianificaEvento turno = null;
			TurniPianifica pianificazione = null;
			
			while ( Utils.dateCompareTo(from, to) <= 0) {
				
				turni = new ArrayList<KeyValue>(turniOriginali);
				int sizeTurni = turni.size();
				
				/* recupero sempre il lunedi della settimana */
				Date weekDal = Utils.getMonday(from);
				Date weekAl =  Utils.getSunday(from);
				
				
				/* numero di giorni decisi dall'inzio della generazione del turnario */
				int dayDiff = Utils.daysBetween(startCiclo, from);
				/*
				 * calcolo quante settimane sono passate, questo perche le squadre ruotano di
				 * settimana in settimana
				 */
				int numeroOfweekPassed = dayDiff / 7;
				/*
				 * devo sempre avere un numero < del numero totale di squadre utilizzo il
				 * modulo, viene recuperata la squadra di turno
				 */
				int squadraDiTurno = (numeroOfweekPassed % totaleSquadre);
				Person squadra = squadre.get(squadraDiTurno);
				
				// recupero la pianificazione
				pianificazione = this.turniPianificaManager.getPianificazioneByWeek(weekDal, weekAl);
				if (pianificazione == null) {
					pianificazione = new TurniPianifica();
					pianificazione.setDal(weekDal);
					pianificazione.setAl(weekAl);
					pianificazione.setIdSquadra(squadra.getId());
					this.turniPianificaManager.save(pianificazione);
					
				} else if (squadra.getId().compareTo(pianificazione.getIdSquadra()) != 0) {
					// caso squadre nuove
					pianificazione.setIdSquadra(squadra.getId());
					this.turniPianificaManager.update(pianificazione);
				}
				this.turniPianificaEventoManager.deleteTurniByPianificazione(pianificazione.getId());
				this.turniPianificaSaltoTurnoManager.deleteByIdPianificazione(pianificazione.getId());
				/*
				 * recupero i vigili attivi ad oggi, quindi tutti quei vigili che hanno un
				 * servizio attivo per la squadra passata, vengono esclusi dalla lista eventuali
				 * vigili con movimentazioni di assenza
				 */
				Date today = new Date();
				Date dalFiltroVigili = weekDal;
				dalFiltroVigili = Utils.setTime(dalFiltroVigili, today);
				List<VigileModel> listaVigiliAttivi  = vigileManager.list(false, squadra.getId(), true, dalFiltroVigili, dalFiltroVigili);
				if (!Utils.isEmptyList(listaVigiliAttivi)) {
					Collections.sort(listaVigiliAttivi,   (v1, v2) -> v1.getLettera().compareTo(v2.getLettera()));
				}
				
				if (Utils.isEmptyList(listaVigiliAttivi)) {
					logger.error("Exception in method: " + this.getClass().getCanonicalName() + ".calc vigili not defined for squadra " + squadra.getName());
					//throw new Exception("Squadra vuota!!! nessun vigile ancora inserito");
					from = Utils.addDays(from, 7);
					continue;
					
				}
				List<KeyValueInt> listVigiliSquadra = new ArrayList<KeyValueInt>();
				for (VigileModel model: listaVigiliAttivi) {
					listVigiliSquadra.add(new KeyValueInt(model.getIdServizio(), model.getId(), null));
				}
				/*
				 * Indica se è previsto il salto turno ossia, 
				 * se  il numero di vigili è maggiore del numero dei turni possibili 
				 * */
				boolean prevediSaltoTurno = ( listVigiliSquadra.size() > sizeTurni );
				int nSaltoTurno = 0;
				if ( prevediSaltoTurno ) {
					nSaltoTurno = listVigiliSquadra.size() - sizeTurni;
					for (int i = 0, len = nSaltoTurno; i < len; i++) {
						// aggancio i turni fake
						turni.add(new KeyValue(null, "Salto turno: " + (i + 1), null) );
					}
					sizeTurni = turni.size();
				}
				

				/*
				 * trovo quante volte le squadre si sono girate nel tempo,
				 * (numeroOfweekPassed / totaleSquadre)
				 * dopodiche viene eseguito il resto dell'operazione con i turni possibili
				 * indentificando quante volte  
				 * */
				int ciclo = (numeroOfweekPassed / totaleSquadre) % sizeTurni;
				
				ModelPianificazioneTurni turnoModel = null;
				for (int i = 0, len = sizeTurni; i < len; i++) {
					
					turnoModel = new ModelPianificazioneTurni();
					
					int giornoSettimana = i,
						indexVigile = -1;
					    
					boolean saltoTurno = ( giornoSettimana >= turniOriginali.size() );
					
					indexVigile = ((giornoSettimana - ciclo) % listVigiliSquadra.size());
					if (indexVigile < 0)
						indexVigile = indexVigile + listVigiliSquadra.size();
					
					vigileTurno = listVigiliSquadra.get(indexVigile);
					
					Date dataTurno = (Date) from.clone();
					
					if (i == 7 || i == 8) {
						dataTurno =Utils.getSaturday(weekDal);
					}
					if (i == 9 || i >= 10) {
						dataTurno = Utils.getSunday(weekDal);
					}
					
					
					turnoModel.setDescrGiornoSettimana(turni.get(i).getValore());
					Vigile vigile = vigileManager.getById(vigileTurno.getVal1());
					turnoModel.setDescrVigile(vigile.getNominativo());
					Servizio servizio = this.servizioManager.getObjById(vigileTurno.getCodice());
					turnoModel.setCodiceTelefono(vigile.getCodPhone());
					turnoModel.setDescrSquadra(servizio.getTeamFormatted());
					turnoModel.setDescrGrado(servizio.getGradoFormatted());
					turnoModel.setIdSquadra(servizio.getIdTeam());
					turnoModel.setIdVigile(vigile.getId());
					turnoModel.setDal(weekDal);
					turnoModel.setNote(servizio.getNote());
					turnoModel.setLetteraVigile(servizio.getLetter());
					turnoModel.setAl(weekAl);
					turnoModel.setSaltoTurno(saltoTurno);
					
					if (Utils.isValidId(servizio.getGrado())) {
						Person p = this.personManager.getObjById(servizio.getGrado());
						if (p != null) {
							if (Utils.stringEguals(p.getFlag(), CostantiVVF.CAPOSQUADRA)) {
								turnoModel.setCapoSquadra(true);
							}
						} 
					}
								
				
					if (!saltoTurno) {
						turnoModel.setData(dataTurno);
						turno = new TurniPianificaEvento();
						turno.setIdVigile(vigile.getId());
						turno.setIdTurno(giornoSettimana);
						turno.setData(dataTurno);
						turno.setIdPianificazione(pianificazione.getId());
						this.turniPianificaEventoManager.save(turno);

					} else {
						
						TurniPianificaSaltoTurno salto = new TurniPianificaSaltoTurno();
						salto.setIdPianificazione(pianificazione.getId());
						salto.setIdVigile(vigile.getId());
						this.turniPianificaSaltoTurnoManager.save(salto);
						turnoModel.setSaltoTurno(true);
					}
					data.add(turnoModel);
					
					if (i <= 6 )
						from = Utils.addDays(from, 1);
				}
			}
			message = Messages.getMessage("calendar.ok");
		} catch (Exception e) {
			logger.error("Exception in method: " + this.getClass().getCanonicalName() + ".calc", e);
			success = false;
			message = (e.getMessage() != null ? e.getMessage() : Messages.getMessage("calendar.ko"));
			e.printStackTrace();
		} finally {
			result = new JsonResponse<List<ModelPianificazioneTurni>>(success, message, data);
		}
		return result;
	}
}
