package it.vvfriva.managers;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
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

import it.vvfriva.entity.Articoli;
import it.vvfriva.repository.ArticoliRepository;
import it.vvfriva.utils.Messages;
import it.vvfriva.utils.Utils;
/**
 * @author simone
 *
 */
@Service
@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ArticoliManager extends DbManagerStandard<Articoli> {

	final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired ArticoliRepository repository;
	@Autowired EntityManager em;
	
	@Override
	public CrudRepository<Articoli, Integer> getRepository() {
		return repository;
	}
	
	/**
	 * Ritorna tutti gli articoli 
	 * @return
	 * @throws Exception 
	 */
	public List<Articoli> list() throws Exception {
		List<Articoli> data = null;
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Articoli> cr = cb.createQuery(Articoli.class);
			Root<Articoli> root = cr.from(Articoli.class);
			cr.select(root);
			List<Predicate> predicates = new ArrayList<Predicate>();
			
		    cr.where(predicates.toArray(new Predicate[0]));
			TypedQuery<Articoli> query = em.createQuery(cr);
			
			data = query.getResultList();
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(Utils.errorInMethod(e.getMessage()));
			throw new Exception(Messages.getMessage("search.ko"));
		}
		return data;
	}
	
	@Override
	public boolean checkCampiObbligatori(Articoli object) {
		if (Utils.isEmptyString(object.getDescrizione())) {
			logger.warn("Can't persist record 'Articoli'  invalid field 'descrizione'");
			addMessage(Messages.getMessageFormatted("field.err.mandatory", new String[] {Messages.getMessage("field.descrizione")}));
			return false;
		}
		return true;
	}
	@Override
	public boolean checkObjectForInsert(Articoli object) {
		return true;
	}
	@Override
	public boolean checkObjectForDelete(Articoli object) {
		return true;
	}
	@Override
	public boolean checkObjectForUpdate(Articoli object) {
		return true;
	}
}
