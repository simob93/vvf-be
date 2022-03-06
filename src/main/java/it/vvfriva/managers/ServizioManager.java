package it.vvfriva.managers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.vvfriva.entity.Gradi;
import it.vvfriva.entity.Servizio;
import it.vvfriva.exception.UserFriendlyException;
import it.vvfriva.repository.ServizioRepository;
import it.vvfriva.utils.Messages;
import it.vvfriva.utils.ResponseMessage;
import it.vvfriva.utils.Utils;
/**
 * Manager per la gestione dei servizi vigile. 
 * il vigile viene inserito all'interno di una squadra 
 * @author simone
 *
 */
@Service
public class ServizioManager extends DbManagerStandard<Servizio> {
	
	final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired ServizioRepository servizioRepository;
	@PersistenceContext
	EntityManager em;
	
	@Autowired 
	private GradiManager gradiManager;

	/**
	 * Metodo che mi ritorno la lista relative a tutti i servizi 
	 * legati al {@code vigile} richiesto
	 * @param idVigile
	 * @return
	 * @throws Exception
	 */
	public List<Servizio> list(Integer idVigile) throws Exception {
		return list(idVigile, null, null, null, null, null);
	}
	/**
	 * 
	 * @param idVigile       - identificativo del vigile
	 * @param dataInizio     - data di inizio
	 * @param dataFine       - data di fine
	 * @param idTeam         - squadra di appartenenza
	 * @param letter         - lettera 
	 * @param idToExclude    - esclusione registrazione
	 * @return ritorna una lista dei servizi filtrata per i parametri impostati
	 * @throws Exception
	 */
	public List<Servizio> list(Integer idVigile, Date dataInizio, Date dataFine, Integer idTeam, String letter, Integer idToExclude) throws Exception {
		
		
		List<Servizio> data = null;

		dataInizio = Utils.isValidDate(dataInizio) ? dataInizio : Utils.startOfDay(Utils.encodeDate(1890, 01, 01));
		dataFine = Utils.isValidDate(dataFine) ? dataFine :  Utils.startOfDay(Utils.encodeDate(3200, 01, 01));
		
		try {
			
			CriteriaBuilder cb = em.getCriteriaBuilder();
		    CriteriaQuery<Servizio> cq = cb.createQuery(Servizio.class);
		 
		    Root<Servizio> servizio = cq.from(Servizio.class);
		    
		    List<Predicate> predicates = new ArrayList<Predicate>();

		    if (Utils.isValidId(idVigile)) {
		    	predicates.add(cb.equal(servizio.get("idVigile"), idVigile));
		    }
		 
		    predicates.add((cb.lessThanOrEqualTo(servizio.get("dateStart"), dataFine)));
	    	predicates.add(cb.or(cb.isNull(servizio.get("dateEnd")), cb.greaterThanOrEqualTo(servizio.get("dateEnd"), dataInizio)));
		    
		    if (Utils.isValidId(idTeam)) {
			    predicates.add(cb.equal(servizio.get("idTeam"), idTeam));
		    }
		    
		    if (!Utils.isEmptyString(letter)) {
			    predicates.add(cb.equal(servizio.get("letter"), letter));
		    }
		    
		    if (Utils.isValidId(idToExclude)) {
			    predicates.add(cb.notEqual(servizio.get("id"), idToExclude));

		    }
		    cq.where(predicates.toArray(new Predicate[0]));
		    cq.orderBy(cb.asc(servizio.get("dateStart")));
		    data = em.createQuery(cq).getResultList();	    
			
			
			//data = servizioRepository.listByIdVigile(idVigile,  new Sort(Sort.Direction.ASC, "dateStart"));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception in functiion: " + this.getClass().getCanonicalName() +".listByArea", e);
			throw new Exception(Messages.getMessage("search.ko"));
		}
		return data;
	}
	/**
	 * 
	 * @param idVigile
	 * @param data
	 * @return ritorna l'ultimo servizio attivo in a partire da una determinata data di un singolo vigile 
	 * @throws Exception
	 */	
	public Servizio getLast(Integer idVigile, Date data)  {
		
		if (!Utils.isValidId(idVigile)) {
			logger.error("Exception in function: 'getLastActiveServizio' invalid field idVigile");
			StringBuilder sb = new StringBuilder();
			sb.append(Messages.getMessage("search.ko")).append(": ");
			sb.append(Messages.getMessageFormatted("field.err.mandatory", new Object[] { Messages.getMessage("field.idVigile") }));
			throw new UserFriendlyException (sb.toString());
		}
		Servizio result = null;
		CriteriaBuilder cb = em.getCriteriaBuilder();
	    CriteriaQuery<Servizio> cq = cb.createQuery(Servizio.class);
	 
	    Root<Servizio> servizio = cq.from(Servizio.class);
	    
	    List<Predicate> predicates = new ArrayList<Predicate>();
	    
	    predicates.add(cb.equal(servizio.get("idVigile"), idVigile));
    	predicates.add(cb.lessThanOrEqualTo(servizio.get("dateStart"), data));
	    
	    cq.where(predicates.toArray(new Predicate[0]));
	    cq.orderBy(cb.desc(servizio.get("dateStart")));
	    List<Servizio> listServizo = em.createQuery(cq).setMaxResults(1).getResultList();	
	    if (!Utils.isEmptyList(listServizo)) {
	    	result = listServizo.get(0);
	    }
	    return result;
	}
	
	@Override
	public boolean controllaCampiObbligatori(Servizio object, List<ResponseMessage> msg) {
		if (!Utils.isValidDate(object.getDateStart())) {
			logger.error("Can't persist record invalid field 'date start'");
			msg.add(new ResponseMessage(Messages.getMessageFormatted("field.err.mandatory", new Object[] {Messages.getMessage("field.date.start")})));
			return false;
		}
		//se ho una squadra allora devo anche obbligare ad inserire una lettera al vigile
		if (Utils.isValidId(object.getIdTeam()) && 
				Utils.isEmptyString(object.getLetter())) {
			logger.error("Can't persist record invalid field 'lettera vigile'");
			msg.add(new ResponseMessage(Messages.getMessageFormatted("field.err.mandatory", new Object[] {"Lettera"})));
			return false;
		}
		if (!Utils.isValidId(object.getIdVigile())) {
			logger.error("Can't persist record invalid field 'id vigile'");
			msg.add(new ResponseMessage(Messages.getMessageFormatted("field.err.mandatory", new Object[] {Messages.getMessage("field.vigile")})));
			return false;
		}
		return true;
	}
	
	@Override
	public void operazioneDopoInserimento(Servizio object){
		if (Utils.isValidId(object.getGrado())) {
			//viene inserito anche il grado
			Gradi grado = new Gradi();
			grado.setDal(object.getDateStart());
			grado.setAl(object.getDateEnd());
			grado.setGrado(object.getGrado());
			grado.setIdServizio(object.getId());
			gradiManager.save(grado);
		}
	}
	@Override
	public void operazionePrimaDiInserire(Servizio object)  {
		Servizio ultimoServizio = getLast(object.getIdVigile(), object.getDateStart());
		if (ultimoServizio != null && !Utils.isValidDate(ultimoServizio.getDateEnd())) {
			//chiudo il precedente servizio				
			ultimoServizio.setDateEnd(new LocalDateTime(object.getDateStart()).minusMinutes(1).toDate());
			this.update(ultimoServizio);
		}
	}
	@Override
	public void operazioneDopoModifica(Servizio object){
		if (Utils.isValidDate(object.getDateEnd())) {
			Gradi grado = gradiManager.getLastActiveGrado(object.getId(), object.getDateEnd());
			if (grado != null && !Utils.isValidDate(grado.getAl())) {
				grado.setAl(object.getDateEnd());
				gradiManager.update(grado);
			}
		}
	}
}
