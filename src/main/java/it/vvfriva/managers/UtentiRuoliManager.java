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
import org.springframework.stereotype.Service;

import it.vvfriva.entity.UtentiRuoli;
import it.vvfriva.exception.UserFriendlyException;
import it.vvfriva.repository.UtentiRuoliRepository;
import it.vvfriva.utils.Messages;
import it.vvfriva.utils.ResponseMessage;
import it.vvfriva.utils.Utils;
/**
 * 
 * @author simone
 *
 */
@Service
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
			throw new UserFriendlyException(Messages.getMessage("search.ko"));
		}
		return data;
	}


	@Override
	public boolean controllaCampiObbligatori(UtentiRuoli object, List<ResponseMessage> msg) {
		return true;
	}
}
