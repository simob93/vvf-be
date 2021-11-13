package it.vvfriva.managers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import it.vvfriva.entity.Gradi;
import it.vvfriva.entity.Servizio;
import it.vvfriva.enums.DbOperation;
import it.vvfriva.repository.GradiRepository;
import it.vvfriva.utils.CustomException;
import it.vvfriva.utils.Messages;
import it.vvfriva.utils.Utils;
/**
 * Manager per la gestione dei gradi vigili
 * @author simone
 *
 */
@Service
@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class GradiManager extends DbManagerStandard<Gradi> {
	
	private static final Logger logger = LoggerFactory.getLogger(GradiManager.class);

	private @Autowired AutowireCapableBeanFactory beanFactory;
	
	@Autowired 
	private EntityManager em;	
	
	@Autowired 
	private GradiRepository gradiRepository;
	
	@Autowired
	private ServizioManager servizioManager;
	
	public GradiManager getNewInstance() {
		GradiManager scandezeManager = new GradiManager();
		beanFactory.autowireBean(scandezeManager);
		return scandezeManager;
	}
	/**
	 * 
	 * @param idVigile     - identificativo del vigile
	 * @param dal          - data dal
	 * @param al           - data al
	 * @param idToExlcude  - esclusione di un determinato grado (es: cotrnollo dei dati duplicati)
	 * @param idServizio   - filtra i gradi in base al servizio - squadra passatato 
	 * @return ritorna una lista di gradi in base ai parametri passati a parametro 
	 * @throws CustomException
	 */
	public List<Gradi> listBy(Date dal, Date al, Integer idToExlcude, Integer idServizio) throws CustomException {
		
		
		List<Gradi> data = null;
		try {
			
			dal = dal != null ?  dal : Utils.startOfDay(Utils.encodeDate(1890, 01, 01));
			al = al != null ?  al : Utils.startOfDay(Utils.encodeDate(3200, 01, 01));
			
			CriteriaBuilder cb = em.getCriteriaBuilder();
		    CriteriaQuery<Gradi> cq = cb.createQuery(Gradi.class);
		 
		    Root<Gradi> assenza = cq.from(Gradi.class);
		    
		    List<Predicate> predicates = new ArrayList<Predicate>();
		    predicates.add((cb.lessThanOrEqualTo(assenza.get("dal"), al)));
	    	predicates.add(cb.or(cb.isNull(assenza.get("al")), cb.greaterThanOrEqualTo(assenza.get("al"), dal)));
	    	if (Utils.isValidId(idToExlcude)) {
	    		predicates.add(cb.notEqual(assenza.get("id"), idToExlcude));
	    	}
	    	if (Utils.isValidId(idServizio)) {
	    		predicates.add(cb.equal(assenza.get("idServizio"), idServizio));	
	    	}
		    cq.where(predicates.toArray(new Predicate[0]));
		    cq.orderBy(cb.asc(assenza.get("dal")));
		    data = em.createQuery(cq).getResultList();	    
			
		} catch (Exception e) {
			logger.error("Exception in method: " + this.getClass().getCanonicalName() + ".listBy", e);
			e.printStackTrace();
			throw new CustomException(Messages.getMessage("search.ko"));
		}
		return data;
	}
	/**
	 * 
	 * @param idServizio
	 * @param al
	 * @return ritorna in base al servizio passato, l'ultimo grado attivo (quello con data fine a null) 
	 * @throws Exception
	 */
	public Gradi getLastActiveGrado(Integer idServizio, Date al) throws Exception {
		
		if (!Utils.isValidId(idServizio)) {
			StringBuilder sb = new StringBuilder();
			sb.append(Messages.getMessage("search.ko")).append(": ")
			.append(Messages.getMessageFormatted("field.err.mandatory", new String[] {"idServizio"}));
			throw new Exception(sb.toString());
		}
		
		Gradi data = null;
		try {
			
			CriteriaBuilder cb = em.getCriteriaBuilder();
		    CriteriaQuery<Gradi> cq = cb.createQuery(Gradi.class);
		    Root<Gradi> gradi = cq.from(Gradi.class);
		    List<Predicate> predicates = new ArrayList<Predicate>();
		    predicates.add(cb.equal(gradi.get("idServizio"), idServizio));
		    predicates.add(
	    		cb.or(
    				cb.isNull(gradi.get("al")),
    				cb.lessThanOrEqualTo(gradi.get("al"), al)
    		));
		    cq.where(predicates.toArray(new Predicate[0]));
		    cq.orderBy(cb.desc(gradi.get("dal")));
		    List<Gradi> listGradi = em.createQuery(cq).setMaxResults(1).getResultList();
		    if (!Utils.isEmptyList(listGradi)) {
		    	data = listGradi.get(0);
		    }
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(Messages.getMessage("search.ko"));
		}
		return data;
	}
	

	@Override
	public boolean checkCampiObbligatori(Gradi object) {
		
		if (!Utils.isValidDate(object.getDal())) {
			logger.error("Exception in method: " + this.getClass().getCanonicalName() + ".checkCampiObbligatori invalid filed data dal");
			addMessage("Campo data dal obbligatorio");
			return false;
		}
		
		if (!Utils.isValidId(object.getGrado())) {
			logger.error("Exception in method: " + this.getClass().getCanonicalName() + ".checkCampiObbligatori invalid grado");
			addMessage("Campo grado obbligatorio");
			return false;
		}
		
		if (!Utils.isValidId(object.getIdServizio())) {
			logger.error("Exception in method: " + this.getClass().getCanonicalName() + ".checkCampiObbligatori invalid motivo idServizio");
			addMessage("Campo id Servizio obbligatorio");
			return false;
		}
		try {
//			List<Gradi> listGradi = this.listBy(object.getIdVigile(), object.getDal(), object.getAl(), object.getId(), object.getIdServizio());
//			if (!Utils.isEmptyList(listGradi)) {
//				addMessage("è gia presente un grado per il periodo impostato");
//				return false;
//			}
			//controllo che le date facciano parte del servizio
			Servizio servizio = servizioManager.getObjById(object.getIdServizio());
			if (servizio == null) {
				addMessage("Nessun servizio attivo");
				return false;
			}
			Date dataInizioServizio = Utils.startOfDay(servizio.getDateStart());
			Date dataFineServzio = servizio.getDateEnd() != null ? Utils.endOfDay(servizio.getDateEnd()) :  null;
			Date dataInizioGrado = Utils.startOfDay(object.getDal());
			Date dataFineGrado = object.getAl() != null ?  Utils.endOfDay(object.getAl()): null;
			
			//la data di inizio servizio deve essere minore rispetto o uguale alla data di inzio grado
			if (dataInizioServizio.compareTo(dataInizioGrado) > 0) {
				addMessage("La data di inizio servizio deve essere minore della data di inizio del grado");
				return false;
			}
			if ( (dataFineServzio != null) &&
					(dataFineGrado != null) &&
						(dataFineServzio.compareTo(dataFineGrado) < 0)) {
				addMessage("La data di fine servizio deve essere maggiore della data di fine del grado");
				return false;
			}
			
			if (dataFineServzio != null && dataFineGrado == null) {
				addMessage("Data fine Grado obbligatoria il servizio del vigile risulta chiuso ");
				return false;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			addMessage(e.getMessage() != null ? e.getMessage() : Messages.getMessage("search.ko"));
			return false;
		}
		
		return true;
	}

	@Override
	public boolean checkObjectForInsert(Gradi object) {
		return true;
	}

	@Override
	public boolean checkObjectForUpdate(Gradi object) {
		return true;
	}

	@Override
	public CrudRepository<Gradi, Integer> getRepository() {
		return gradiRepository;
	}

	@Override
	public boolean beforeDbAction(DbOperation action, Gradi object) {
		try {
			if (action == DbOperation.INSERT) {
				// cerco l'ultimo grado attivo 
				Gradi ultimoGrado = getLastActiveGrado(object.getIdServizio(), new Date());
				if (ultimoGrado != null && !Utils.isValidDate(ultimoGrado.getAl())) {
					ultimoGrado.setAl(new LocalDateTime(object.getDal()).minusMinutes(1).toDate());
					this.getNewInstance().dbManager(DbOperation.UPDATE, ultimoGrado);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			addMessage(e.getMessage() != null ? Messages.getMessage("operation.ko") : e.getMessage());
			return false;
		}
		return true;
		
	}
	
	@Override
	public boolean checkObjectForDelete(Gradi object) {
		return true;
	}

}
