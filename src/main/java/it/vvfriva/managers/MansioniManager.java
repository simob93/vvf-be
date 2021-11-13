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

import it.vvfriva.entity.Mansioni;
import it.vvfriva.repository.MansioniRepository;
import it.vvfriva.utils.CustomException;
import it.vvfriva.utils.Messages;
import it.vvfriva.utils.Utils;
/**
 * Manager per la gestione delle mansioni dei vigili all'interno del Corpo 
 * 
 * @author simone
 *
 */
@Service
@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MansioniManager extends DbManagerStandard<Mansioni> {
	
	private static final Logger logger = LoggerFactory.getLogger(MansioniManager.class);

	@Autowired 
	private EntityManager em;	
	
	@Autowired 
	private MansioniRepository carrieraRepository;
	
	@Override
	public CrudRepository<Mansioni, Integer> getRepository() {
		return carrieraRepository;
	}
	/**
	 * 
	 * @param idVigile
	 * @return filtra tutte le mansioni di un vigile passato a parametro
	 * @throws CustomException
	 */
	public List<Mansioni> listBy(Integer idVigile) throws CustomException {
		return listBy(idVigile, null, null, null);
	}
	
	/**
	 * 
	 * @param idVigile       -identificativo del vigile
	 * @param dal            - dal
	 * @param al             - al
	 * @param idToExlcude    - possibilità di eslcudere una particolare registrazione
	 * @return filtra la tabella per una serie di parametri passati 
	 * @throws CustomException
	 */
	public List<Mansioni> listBy(Integer idVigile, Date dal, Date al, Integer idToExlcude) throws CustomException {
		
		if (!Utils.isValidId(idVigile)) {
			logger.error("Exception in method: " + this.getClass().getCanonicalName() + ".listBy invalid filed idVigile");
			StringBuilder sb = new StringBuilder();
			sb.append(Messages.getMessage("search.ko")).append(": ")
			.append(Messages.getMessageFormatted("field.err.mandatory", new String[] {"idVigile"}));
			throw new CustomException(sb.toString());
		}
		List<Mansioni> data = null;
		try {
			
			dal = dal != null ? dal : Utils.startOfDay(Utils.encodeDate(1890, 01, 01));
			al = al != null ? al : Utils.endOfDay(new Date());
			
			CriteriaBuilder cb = em.getCriteriaBuilder();
		    CriteriaQuery<Mansioni> cq = cb.createQuery(Mansioni.class);
		 
		    Root<Mansioni> assenza = cq.from(Mansioni.class);
		    
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
	

	@Override
	public boolean checkCampiObbligatori(Mansioni object) {
		
		if (!Utils.isValidDate(object.getDal())) {
			logger.error("Exception in method: " + this.getClass().getCanonicalName() + ".checkCampiObbligatori invalid filed data dal");
			addMessage("Campo data dal obbligatorio");
			return false;
		}
		
		if (!Utils.isValidId(object.getTipo())) {
			logger.error("Exception in method: " + this.getClass().getCanonicalName() + ".checkCampiObbligatori invalid filed id");
			addMessage("Campo tipo obbligatorio");
			return false;
		}
		return true;
	}

	@Override
	public boolean checkObjectForInsert(Mansioni object) {
		return true;
	}

	@Override
	public boolean checkObjectForUpdate(Mansioni object) {
		return true;
	}
	
	@Override
	public boolean checkObjectForDelete(Mansioni object) {
		return true;
	}

}
