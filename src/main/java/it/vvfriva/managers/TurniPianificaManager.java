package it.vvfriva.managers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.vvfriva.entity.TurniPianifica;
import it.vvfriva.repository.TurniPianificaRepository;
import it.vvfriva.utils.CustomException;
import it.vvfriva.utils.Messages;
import it.vvfriva.utils.ResponseMessage;
import it.vvfriva.utils.Utils;
/**
 * Manager della vera e propria gestesione dei turni 
 * @author simone
 *
 */
@Service
public class TurniPianificaManager extends DbManagerStandard<TurniPianifica>  {
	private final Logger log = LoggerFactory.getLogger(TurniPianificaManager.class);

	@Autowired EntityManager em;
	@Autowired PersonManager personManager;
	@Autowired TurniPianificaRepository turniPianificaRepository;
	@Autowired TurniPianificaSaltoTurnoManager turniPianificaSaltoTurnoManager;
	/**
	 * Metodo mi torna tutte le pianificazioni di un turnario a partire da una determinata data
	 * in ingresso 
	 *  
	 * @param idTe
	 * @param dal
	 * @return
	 * @throws Exception
	 */
	public List<TurniPianifica> getNextPianificazioni(Integer idTe, Date dal) throws Exception {
		
		if (!Utils.isValidId(idTe)) {
			StringBuilder sb = new StringBuilder();
			sb.append(Messages.getMessage("search.ko")).append(": ")
			.append(Messages.getMessageFormatted("field.err.mandatory", new String[] {"idTe"}));
			log.error("Exception in method: "+ this.getClass().getCanonicalName() + ".list" +"Invalid filed idTe");
			throw new Exception(sb.toString());
		}
		List<TurniPianifica> result = null;
		try {
			
			CriteriaBuilder cb = em.getCriteriaBuilder();
		    CriteriaQuery<TurniPianifica> cq = cb.createQuery(TurniPianifica.class);
		 
		    Root<TurniPianifica> servizio = cq.from(TurniPianifica.class);
		    List<Predicate> predicates = new ArrayList<Predicate>();
		    predicates.add(cb.equal(servizio.get("idTe"), idTe));
		    predicates.add(cb.greaterThanOrEqualTo(servizio.get("dal"), dal));
		    cq.where(predicates.toArray(new Predicate[0]));
		    cq.orderBy(cb.desc(servizio.get("dal")));
		    result = em.createQuery(cq).getResultList();
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Exception in method" + this.getClass().getCanonicalName() + ".getLastPianificazione", e);
			throw new Exception(Messages.getMessage("search.ko"));
		}
		return result;
	}
	
	/**
	 * Metodo ritorna tutte le pianificazioni di un singolo turnario 
	 * @param idTe
	 * @return
	 * @throws Exception
	 */
	public List<TurniPianifica> listByIdTe(Integer idTe) throws Exception {
		
		if (!Utils.isValidId(idTe)) {
			StringBuilder sb = new StringBuilder();
			sb.append(Messages.getMessage("search.ko")).append(": ")
			.append(Messages.getMessageFormatted("field.err.mandatory", new String[] {"idTe"}));
			log.error("Exception in method: "+ this.getClass().getCanonicalName() + ".list" +"Invalid filed idTe");
			throw new Exception(sb.toString());
		}
		List<TurniPianifica> result = null;
		try {
			
			CriteriaBuilder cb = em.getCriteriaBuilder();
		    CriteriaQuery<TurniPianifica> cq = cb.createQuery(TurniPianifica.class);
		 
		    Root<TurniPianifica> servizio = cq.from(TurniPianifica.class);
		    List<Predicate> predicates = new ArrayList<Predicate>();
		    predicates.add(cb.equal(servizio.get("idTe"), idTe));
		    cq.where(predicates.toArray(new Predicate[0]));
		    cq.orderBy(cb.desc(servizio.get("dal")));
		    result = em.createQuery(cq).getResultList();
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Exception in method" + this.getClass().getCanonicalName() + ".getLastPianificazione", e);
			throw new Exception(Messages.getMessage("search.ko"));
		}
		return result;
	}
	
	
	/**
	 * ritorna l'ultima pianificazione fatta per la squadra passata 
	 * @return
	 * @throws Exception
	 */
	public TurniPianifica getLastPianificazione(Integer idSquadra, Date dal, Date al, Integer idTe) throws Exception {
		
		if (!Utils.isValidId(idSquadra)) {
			StringBuilder sb = new StringBuilder();
			sb.append(Messages.getMessage("search.ko")).append(": ")
			.append(Messages.getMessageFormatted("field.err.mandatory", new String[] {"idSquadra"}));
			log.error("Exception in method: "+ this.getClass().getCanonicalName() + ".getLastPianificazione" +"Invalid filed idSqudra");
			throw new Exception(sb.toString());
		}
		TurniPianifica result = null;
		try {
			
			CriteriaBuilder cb = em.getCriteriaBuilder();
		    CriteriaQuery<TurniPianifica> cq = cb.createQuery(TurniPianifica.class);
		 
		    Root<TurniPianifica> servizio = cq.from(TurniPianifica.class);
		    List<Predicate> predicates = new ArrayList<Predicate>();
		    predicates.add(cb.equal(servizio.get("idSquadra"), idSquadra));
		    predicates.add(cb.equal(servizio.get("idTe"), idTe));
		    predicates.add(cb.greaterThanOrEqualTo(servizio.get("al"), dal));
		    predicates.add(cb.lessThanOrEqualTo(servizio.get("dal"), al));
		    cq.where(predicates.toArray(new Predicate[0]));
		    cq.orderBy(cb.desc(servizio.get("dal")));
		    List<TurniPianifica> listaPianificazione = em.createQuery(cq).setMaxResults(1).getResultList();
		    
			if (!Utils.isEmptyList(listaPianificazione)) {
				result = listaPianificazione.get(0);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Exception in method" + this.getClass().getCanonicalName() + ".getLastPianificazione", e);
			throw new Exception(Messages.getMessage("search.ko"));
		}
		return result;
	}
	/**
	 * ritorna la prima pianificazione precedente alla data passata
	 * @param idSquadra
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public TurniPianifica getPrev(Integer idSquadra, Date data, Integer idTe) throws Exception {
		
		if (!Utils.isValidId(idSquadra)) {
			StringBuilder sb = new StringBuilder();
			sb.append(Messages.getMessage("search.ko")).append(": ")
			.append(Messages.getMessageFormatted("field.err.mandatory", new String[] {"idSquadra"}));
			log.error("Exception in method: "+ this.getClass().getCanonicalName() + ".getLastPianificazione" +"Invalid filed idSqudra");
			throw new Exception(sb.toString());
		}
		TurniPianifica result = null;
		try {
			
			CriteriaBuilder cb = em.getCriteriaBuilder();
		    CriteriaQuery<TurniPianifica> cq = cb.createQuery(TurniPianifica.class);
		 
		    Root<TurniPianifica> servizio = cq.from(TurniPianifica.class);
		    
		    List<Predicate> predicates = new ArrayList<Predicate>();
		    predicates.add(cb.equal(servizio.get("idSquadra"), idSquadra));
		    predicates.add(cb.equal(servizio.get("idTe"), idTe));
		    predicates.add(cb.lessThan(servizio.get("dal"), data));
		    cq.where(predicates.toArray(new Predicate[0]));
		    cq.orderBy(cb.desc(servizio.get("dal")));
		    List<TurniPianifica> listaPianificazione = em.createQuery(cq).setMaxResults(1).getResultList();	
			if (!Utils.isEmptyList(listaPianificazione)) {
				result = listaPianificazione.get(0);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Exception in method" + this.getClass().getCanonicalName() + ".getLastPianificazione", e);
			throw new Exception(Messages.getMessage("search.ko"));
		}
		return result;
	}
	
	/**
	 * 
	 * @param weekDal
	 * @param weekAl
	 * @return
	 * @throws Exception 
	 */
	public TurniPianifica getPianificazioneByWeek(Date weekDal, Date weekAl) throws Exception {
		TurniPianifica result = null;
		try {
			
			CriteriaBuilder cb = em.getCriteriaBuilder();
		    CriteriaQuery<TurniPianifica> cq = cb.createQuery(TurniPianifica.class);
		 
		    Root<TurniPianifica> servizio = cq.from(TurniPianifica.class);
		    
		    List<Predicate> predicates = new ArrayList<Predicate>();
		    predicates.add(cb.equal(servizio.get("dal"), weekDal));
		    predicates.add(cb.equal(servizio.get("al"), weekAl));
		    cq.where(predicates.toArray(new Predicate[0]));
		    cq.orderBy(cb.desc(servizio.get("dal")));
		    List<TurniPianifica> listaPianificazione = em.createQuery(cq).setMaxResults(1).getResultList();	
			if (!Utils.isEmptyList(listaPianificazione)) {
				result = listaPianificazione.get(0);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Exception in method" + this.getClass().getCanonicalName() + ".getLastPianificazione", e);
			throw new Exception(Messages.getMessage("search.ko"));
		}
		return result;
	}


	@Override
	public boolean controllaCampiObbligatori(TurniPianifica object, List<ResponseMessage> msg)
			throws CustomException, Exception {
		return true;
	}

}
