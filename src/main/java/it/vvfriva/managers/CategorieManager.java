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

import it.vvfriva.entity.Categorie;
import it.vvfriva.repository.CategorieRepository;
import it.vvfriva.utils.Messages;
import it.vvfriva.utils.Utils;
/**
 * @author simone
 *
 */
@Service
@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CategorieManager extends DbManagerStandard<Categorie> {

	final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired CategorieRepository repository;
	@Autowired EntityManager em;
	
	@Override
	public CrudRepository<Categorie, Integer> getRepository() {
		return repository;
	}
	
	/**
	 * Ritorna tutti gli articoli 
	 * @return
	 * @throws Exception 
	 */
	public List<Categorie> list() throws Exception {
		List<Categorie> data = null;
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Categorie> cr = cb.createQuery(Categorie.class);
			Root<Categorie> root = cr.from(Categorie.class);
			cr.select(root);
			List<Predicate> predicates = new ArrayList<Predicate>();
			
		    cr.where(predicates.toArray(new Predicate[0]));
			TypedQuery<Categorie> query = em.createQuery(cr);
			
			data = query.getResultList();
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(Utils.errorInMethod(e.getMessage()));
			throw new Exception(Messages.getMessage("search.ko"));
		}
		return data;
	}
	
	@Override
	public boolean checkCampiObbligatori(Categorie object) {
		if (Utils.isEmptyString(object.getDescrizione())) {
			logger.warn("Can't persist record 'Categorie'  invalid field 'descrizione'");
			addMessage(Messages.getMessageFormatted("field.err.mandatory", new String[] {Messages.getMessage("field.descrizione")}));
			return false;
		}
		return true;
	}
	@Override
	public boolean checkObjectForInsert(Categorie object) {
		return true;
	}
	@Override
	public boolean checkObjectForDelete(Categorie object) {
		return true;
	}
	@Override
	public boolean checkObjectForUpdate(Categorie object) {
		return true;
	}
}
