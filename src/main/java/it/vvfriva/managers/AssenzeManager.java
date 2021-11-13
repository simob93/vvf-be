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
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import it.vvfriva.entity.Assenze;
import it.vvfriva.repository.AssenzeRepository;
import it.vvfriva.utils.CustomException;
import it.vvfriva.utils.Messages;
import it.vvfriva.utils.Utils;
/**
 * Manger per la gestione delle assenze dei vigili
 * 
 * @author simone
 *
 */
@Service
@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AssenzeManager extends DbManagerStandard<Assenze> {
	
	private final Logger logger = LoggerFactory.getLogger(AssenzeManager.class);

	@Autowired 
	private EntityManager em;	
	
	@Autowired 
	private AssenzeRepository assenzaRepository;
	
	@Override
	public CrudRepository<Assenze, Integer> getRepository() {
		return assenzaRepository;
	}
	/**
	 * 
	 * @param idVigile
	 * @return lista di assenza di un vigile passato a parametro 
	 * @throws CustomException
	 */
	public List<Assenze> listBy(Integer idVigile) throws CustomException {
		return listBy(idVigile, null, null, null);
	}
	
	/**
	 * 
	 * @param idVigile    - identificativo del vigile
	 * @param dal         - da 
	 * @param al          - a
	 * @param idToExlcude - possibilità di escludere un particolare id assenza 
	 * @return
	 * @throws CustomException
	 */
	public List<Assenze> listBy(Integer idVigile, Date dal, Date al, Integer idToExlcude) throws CustomException {
		
		if (!Utils.isValidId(idVigile)) {
			logger.error("Exception in method: " + this.getClass().getCanonicalName() + ".listBy invalid filed idVigile");
			StringBuilder sb = new StringBuilder();
			sb.append(Messages.getMessage("search.ko")).append(": ")
			.append(Messages.getMessageFormatted("field.err.mandatory", new String[] {"idVigile"}));
			throw new CustomException(sb.toString());
		}
		List<Assenze> data = null;
		try {
			
			dal = dal != null ? dal : Utils.startOfDay(Utils.encodeDate(1890, 01, 01));
			al = al != null ? al : Utils.startOfDay(Utils.encodeDate(2100, 01, 01));
			
			CriteriaBuilder cb = em.getCriteriaBuilder();
		    CriteriaQuery<Assenze> cq = cb.createQuery(Assenze.class);
		 
		    Root<Assenze> assenza = cq.from(Assenze.class);
		    
		    List<Predicate> predicates = new ArrayList<Predicate>();
		    
		    predicates.add(cb.equal(assenza.get("idVigile"), idVigile));
		    
		    predicates.add((cb.lessThanOrEqualTo(assenza.get("dal"), al)));
	    	predicates.add(cb.or(cb.isNull(assenza.get("al")), cb.greaterThanOrEqualTo(assenza.get("al"), dal)));
	    	if (Utils.isValidId(idToExlcude)) {
	    		predicates.add(cb.notEqual(assenza.get("id"), idToExlcude));
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
	 * @param idVigile   - identificativo del vigile
	 * @param data       - data dal
	 * @return ritorna l'ultilma scadenza a paratire da una determinata data 
	 * @throws CustomException
	 */
	public Assenze getLast(Integer idVigile, Date data) throws CustomException {
		
		if (!Utils.isValidId(idVigile)) {
			logger.error("Exception in method: " + this.getClass().getCanonicalName() + ".listBy invalid filed idVigile");
			StringBuilder sb = new StringBuilder();
			sb.append(Messages.getMessage("search.ko")).append(": ")
			.append(Messages.getMessageFormatted("field.err.mandatory", new String[] {"idVigile"}));
			throw new CustomException(sb.toString());
		}
		
		Assenze result = null;
		
		try {
			
			data = data != null ? data : new Date();
			
			CriteriaBuilder cb = em.getCriteriaBuilder();
		    CriteriaQuery<Assenze> cq = cb.createQuery(Assenze.class);
		    Root<Assenze> assenza = cq.from(Assenze.class);
		    List<Predicate> predicates = new ArrayList<Predicate>();
		    
		    predicates.add(cb.equal(assenza.get("idVigile"), idVigile));
	    	predicates.add(cb.or(cb.isNull(assenza.get("al")), cb.greaterThanOrEqualTo(assenza.get("al"), data)));
	    	
		    cq.where(predicates.toArray(new Predicate[0]));
		    
		    cq.orderBy(cb.asc(assenza.get("dal")));
		    
		    List<Assenze> listAssenze = em.createQuery(cq).setMaxResults(1).getResultList();
		    if (!Utils.isEmptyList(listAssenze)) {
		    	result = listAssenze.get(0);
		    }
			
		} catch (Exception e) {
			logger.error("Exception in method: " + this.getClass().getCanonicalName() + ".listBy", e);
			e.printStackTrace();
			throw new CustomException(Messages.getMessage("search.ko"));
		}
		return result;
	}
	

	@Override
	public boolean checkCampiObbligatori(Assenze object) {
		
		if (!Utils.isValidDate(object.getDal())) {
			logger.error("Exception in method: " + this.getClass().getCanonicalName() + ".checkCampiObbligatori invalid filed data dal");
			addMessage("Campo data dal obbligatorio");
			return false;
		}
		
		if (!Utils.isValidId(object.getMotivo())) {
			logger.error("Exception in method: " + this.getClass().getCanonicalName() + ".checkCampiObbligatori invalid motivo id");
			addMessage("Campo motivo obbligatorio");
			return false;
		}
		try {
			List<Assenze> listAssenze = this.listBy(object.getIdVigile(), object.getDal(), object.getAl(), object.getId());
			if (!Utils.isEmptyList(listAssenze)) {
				addMessage("è gia presente un'assenza per il periodo impostato");
				return false;
			}
		} catch (CustomException e) {
			e.printStackTrace();
			addMessage(e.getMessage() != null ? e.getMessage() : Messages.getMessage("search.ko"));
			return false;
		}
		
		return true;
	}

	@Override
	public boolean checkObjectForInsert(Assenze object) {
		return true;
	}

	@Override
	public boolean checkObjectForUpdate(Assenze object) {
		return true;
	}
	
	@Override
	public boolean checkObjectForDelete(Assenze object) {
		return true;
	}

}
