package it.vvfriva.managers;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

import it.vvfriva.entity.UtentiRuoli;
import it.vvfriva.repository.UtentiRuoliRepository;
import it.vvfriva.utils.Utils;
/**
 * 
 * @author simone
 *
 */
@Service
@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UtentiRuoliManager extends DbManagerStandard<UtentiRuoli> {

	private final Logger logger = LoggerFactory.getLogger(UtentiRuoliManager.class);

	
	@Autowired UtentiRuoliRepository repository;
	@PersistenceContext EntityManager em;
	
	/**
	 * metodo che ritorna tutti gli account di un singolo ruolo
	 * 
	 * @param idRuolo
	 * @return
	 */
	public List<UtentiRuoli> getUtentiByIdRuolo(Integer idRuolo) {
		List<UtentiRuoli> data = null;
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
		    CriteriaQuery<UtentiRuoli> cq = cb.createQuery(UtentiRuoli.class);
		    Root<UtentiRuoli> utRuoli = cq.from(UtentiRuoli.class);
		    List<Predicate> predicates = new ArrayList<Predicate>();
		    predicates.add(cb.equal(utRuoli.get("idRuolo"), idRuolo));
		    cq.where(predicates.toArray(new Predicate[0]));
		    data = em.createQuery(cq)
		    		.getResultList();
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(Utils.errorInMethod(e.getMessage()));
		}
		return data;
	}
	
	@Override
	public CrudRepository<UtentiRuoli, Integer> getRepository() {
		return this.repository;
	}
	
	@Override
	public boolean checkCampiObbligatori(UtentiRuoli object) {
		return true;
	}

	@Override
	public boolean checkObjectForInsert(UtentiRuoli object) {
		return true;
	}

	@Override
	public boolean checkObjectForUpdate(UtentiRuoli object) {
		return true;
	}

	@Override
	public boolean checkObjectForDelete(UtentiRuoli object) {
		return true;
	}

}
