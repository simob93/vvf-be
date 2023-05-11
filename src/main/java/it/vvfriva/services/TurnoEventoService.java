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
import it.vvfriva.entity.Vigile;
import it.vvfriva.enums.EnumsTurni;
import it.vvfriva.exception.UserFriendlyException;
import it.vvfriva.managers.PersonManager;
import it.vvfriva.managers.ServizioManager;
import it.vvfriva.managers.VigileManager;
import it.vvfriva.models.JsonResponse;
import it.vvfriva.models.KeyValue;
import it.vvfriva.models.ModelPianificazioneTurni;
import it.vvfriva.models.VigileModel;
import it.vvfriva.utils.CostantiVVF;
import it.vvfriva.utils.Utils;

@Service
public class TurnoEventoService {

	@Autowired
	PersonManager personManager;
	@Autowired
	VigileManager vigileManager;
	@Autowired
	ServizioManager servizioManager;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * Metodo per la generazione del turnario squadre
	 *
	 * @param dal
	 * @param al
	 * @return
	 */
	@Transactional
	public JsonResponse<List<ModelPianificazioneTurni>> calcolaTurnario(Date dal, Date al) {
		
		List<ModelPianificazioneTurni> data = new ArrayList<ModelPianificazioneTurni>();
		Date from = Utils.getMonday(dal);
		Date to = Utils.getSunday(al);

		Date dataInizioOrigine = Utils.encodeDate(2013, 4, 8);

		List<Person> squadre = personManager.list(Arrays.asList(CostantiVVF.AREA_SQUADRE), null, null, false);
		if (Utils.isEmptyList(squadre)) {
			logger.error(
					"Exception in method: " + this.getClass().getCanonicalName() + ".calc sqaudre not defined");
			throw new UserFriendlyException("Impossibile generare un turnario suqadre non ancora definite");
		}
		int totaleSquadre = squadre.size();

		List<KeyValue> turniOriginali = Arrays.asList(EnumsTurni.values()).stream()
				.map(tur -> new KeyValue(tur.getId(), tur.getDescr(), null)).collect(Collectors.toList());

		List<KeyValue> turni = null;
		while (Utils.dateCompareTo(from, to) <= 0) {

			turni = new ArrayList<KeyValue>(turniOriginali);
			Date weekDal = Utils.getMonday(from);
			Date weekAl = Utils.getSunday(from);

			int giorniDaOrigine = Utils.daysBetween(dataInizioOrigine, from);
			int numeroDiSettinanePassate = (giorniDaOrigine / 7);
			int squadraDiTurno = (numeroDiSettinanePassate % totaleSquadre); //ogni numero totale di squadre
			Person squadra = squadre.get(squadraDiTurno);
			List<VigileModel> listaVigiliAttivi = vigileManager.list(false, squadra.getId(), true, from, from);
			if (!Utils.isEmptyList(listaVigiliAttivi)) {
				Collections.sort(listaVigiliAttivi, (v1, v2) -> v1.getLettera().compareTo(v2.getLettera()));
			}
			if (Utils.isEmptyList(listaVigiliAttivi)) {
				logger.error("Exception in method: " + this.getClass().getCanonicalName()
						+ ".calc vigili not defined for squadra " + squadra.getName());
				// throw new Exception("Squadra vuota!!! nessun vigile ancora inserito");
				from = Utils.addDays(from, 7);
				continue;
			}

			boolean prevediSaltoTurno = (listaVigiliAttivi.size() > turniOriginali.size());
			int nSaltoTurno = 0;
			if (prevediSaltoTurno) {
				nSaltoTurno = listaVigiliAttivi.size() - turniOriginali.size();
				for (int i = 0, len = nSaltoTurno; i < len; i++) {
					turni.add(new KeyValue(null, "Salto turno: " + (i + 1), null));
				}
			}
			int numeroGiriSquadre = (numeroDiSettinanePassate / totaleSquadre);
			int saltoVigili = (numeroGiriSquadre % listaVigiliAttivi.size());

			ModelPianificazioneTurni turnoModel = null;
			int contatoreTurni = 0;
			int turno = 0;
			while (contatoreTurni < turni.size()) {
				turnoModel = new ModelPianificazioneTurni();
				int idx = (turno - saltoVigili);
				if (idx < 0) {
					idx = idx + listaVigiliAttivi.size();
				}
				if (idx > listaVigiliAttivi.size() - 1) {
					// numero di vigili inferiore al numero di turni
					turno = saltoVigili;
					continue;
				}

				VigileModel vigileTurno = listaVigiliAttivi.get(idx);
				boolean saltoTurno = (contatoreTurni >= turniOriginali.size());
				Date dataTurno = (Date) from.clone();

				if (contatoreTurni == 7 || contatoreTurni == 8) {
					dataTurno = Utils.getSaturday(weekDal);
				}
				if (contatoreTurni == 9 || contatoreTurni >= 10) {
					dataTurno = Utils.getSunday(weekDal);
				}
				turnoModel.setDescrGiornoSettimana(turni.get(contatoreTurni).getValore());
				Vigile vigile = vigileManager.getById(vigileTurno.getId());
				Servizio servizio = this.servizioManager.getObjById(vigileTurno.getIdServizio());

				turnoModel.setDescrVigile(vigile.getNominativo());
				turnoModel.setCodiceTelefono(vigile.getCodPhone());
				turnoModel.setDescrSquadra(servizio.getTeamFormatted());
				turnoModel.setDescrGrado(servizio.getGradoFormatted());
				turnoModel.setDescrGiornoSettimana(turni.get(contatoreTurni).getValore());
				turnoModel.setDal(weekDal);
				turnoModel.setNote(servizio.getNote());
				turnoModel.setLetteraVigile(servizio.getLetter());
				turnoModel.setAl(weekAl);
				turnoModel.setData(dataTurno);
				turnoModel.setSaltoTurno(saltoTurno);

				if (Utils.isValidId(servizio.getGrado())) {
					Person p = this.personManager.getObjById(servizio.getGrado());
					if (p != null) {
						if (Utils.stringEguals(p.getFlag(), CostantiVVF.CAPOSQUADRA)) {
							turnoModel.setCapoSquadra(true);
						}
					}
				}

				data.add(turnoModel);

				if (contatoreTurni <= 6)
					from = Utils.addDays(from, 1);

				contatoreTurni++;
				turno++;
			}
		}
		return new JsonResponse<List<ModelPianificazioneTurni>>(true, data);
	}
}
