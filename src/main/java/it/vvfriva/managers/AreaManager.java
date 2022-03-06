package it.vvfriva.managers;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.vvfriva.entity.Area;
import it.vvfriva.repository.AreaRepository;
import it.vvfriva.utils.CostantiVVF;
import it.vvfriva.utils.ResponseMessage;
import it.vvfriva.utils.Utils;
/**
 * Manager per la gestione delle aree.
 * Le aree sono delle voci preconfigurate a database 
 * 
 * 1  = Patenti Civili
 * 2  = Autorizzazioni
 * 3  = Gradi
 * 4  = Ruoli vigili
 * 5  = Patenti di servizio
 * 7  = Enti
 * 8  = Faldoni
 * 9  = Movimenti di Assenza 
 * 10 = Squadra 
 * 
 * 
 * @author simone
 *
 */
@Service
public class AreaManager extends DbManagerStandard<Area> {

	final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired AreaRepository repository;
	@Autowired EntityManager em;
	
	/**
	 * 
	 * @return
	 */
	public  List<Area> list() {
		return this.list(null, null);
	}
	/**
	 * Ritorna una lista di aree
	 * @param ids   - array di aree da poter filtrare
	 * @return
	 */
	public List<Area> list(List<Integer> ids, Boolean gestScadenza) {
		List<Area> data = null;
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
		    CriteriaQuery<Area> cq = cb.createQuery(Area.class);
		    Root<Area> area = cq.from(Area.class);
		    List<Predicate> predicates = new ArrayList<Predicate>();

		    if (!Utils.isEmptyList(ids)) {
		    	
		    	In<Integer> inClause = cb.in(area.get("id"));
		    	for (Integer idArea : ids) {
		    		inClause.value(idArea);
		    	}
		        predicates.add(inClause);
		    }
		    
		    if (gestScadenza != null && gestScadenza == true) {
		    	predicates.add(cb.equal(area.get("manageExpiry"), CostantiVVF.TRUE));
		    }
		    
		    cq.where(predicates.toArray(new Predicate[0]));
		    cq.orderBy(cb.asc(area.get("name")));
		    data = em.createQuery(cq).getResultList();	    
			
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Exception in function" + this.getClass().getCanonicalName() + ".list", ex);
		}	
		return data;
	}
	@Override
	public boolean controllaCampiObbligatori(Area object, List<ResponseMessage> msg){
		return false;
	}
}
