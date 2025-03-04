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

import it.vvfriva.entity.ArticoliDepositi;
import it.vvfriva.repository.ArticoliDepositoRepository;
import it.vvfriva.utils.Messages;
import it.vvfriva.utils.Utils;

/**
 * @author simone
 *
 */
@Service
@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ArticoliDepositiManager extends DbManagerStandard<ArticoliDepositi> {

	final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	ArticoliDepositoRepository repository;
	
	@Autowired
	EntityManager em;

	@Override
	public CrudRepository<ArticoliDepositi, Integer> getRepository() {
		return repository;
	}

	/**
	 * Ritorna tutti gli articoli
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<ArticoliDepositi> list(Integer idArticolo) throws Exception {

		if (!Utils.isValidId(idArticolo)) {
			logger.error("Error in method " + Utils.errorInMethod("invalid field 'idArticolo'"));
			StringBuilder sb = new StringBuilder();
			sb.append(Messages.getMessage("search.ok")).append(": ").append(Messages.getMessageFormatted(
					"field.err.mandatory", new String[] { Messages.getMessage("field.idarticolo") }));
			throw new Exception(sb.toString());
		}

		List<ArticoliDepositi> data = null;
		try {

			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<ArticoliDepositi> cr = cb.createQuery(ArticoliDepositi.class);
			Root<ArticoliDepositi> root = cr.from(ArticoliDepositi.class);
			cr.select(root);
			List<Predicate> predicates = new ArrayList<Predicate>();

			predicates.add(cb.equal(root.get("idArticolo"), idArticolo));

			cr.where(predicates.toArray(new Predicate[0]));
			TypedQuery<ArticoliDepositi> query = em.createQuery(cr);

			data = query.getResultList();

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(Utils.errorInMethod(e.getMessage()));
			throw new Exception(Messages.getMessage("search.ko"));
		}
		return data;
	}

	@Override
	public boolean checkCampiObbligatori(ArticoliDepositi object) {
		if (!Utils.isValidId(object.getIdArticolo())) {
			logger.warn("Can't persist record 'ArticoliDepositi'  invalid field 'idArticolo'");
			addMessage(Messages.getMessageFormatted("field.err.mandatory",
					new String[] { Messages.getMessage("field.idarticolo") }));
			return false;
		}
		if (!Utils.isValidId(object.getIdDeposito())) {
			logger.warn("Can't persist record 'ArticoliDepositi'  invalid field 'idDeposito'");
			addMessage(Messages.getMessageFormatted("field.err.mandatory",
					new String[] { Messages.getMessage("field.iddeposito") }));
			return false;
		}
		return true;
	}

	@Override
	public boolean checkObjectForInsert(ArticoliDepositi object) {
		return true;
	}

	@Override
	public boolean checkObjectForDelete(ArticoliDepositi object) {
		return true;
	}

	@Override
	public boolean checkObjectForUpdate(ArticoliDepositi object) {
		return true;
	}
}
