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
import org.springframework.stereotype.Service;

import it.vvfriva.entity.ArticoliCategorie;
import it.vvfriva.exception.UserFriendlyException;
import it.vvfriva.repository.ArticoliCategorieRepository;
import it.vvfriva.specifications.ArticoliCategorieSpecification;
import it.vvfriva.utils.Messages;
import it.vvfriva.utils.ResponseMessage;
import it.vvfriva.utils.Utils;

/**
 * @author simone
 *
 */
@Service
public class ArticoliCategorieManager extends DbManagerStandard<ArticoliCategorie> {

	final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	ArticoliCategorieRepository repository;
	@Autowired
	EntityManager em;

	/**
	 * Ritorna tutti gli articoli
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<ArticoliCategorie> list(Integer idArticolo) throws Exception {

		if (!Utils.isValidId(idArticolo)) {
			logger.error("Error in method " + Utils.errorInMethod("invalid field 'idArticolo'"));
			StringBuilder sb = new StringBuilder();
			sb.append(Messages.getMessage("search.ok")).append(": ").append(Messages.getMessageFormatted(
					"field.err.mandatory", new String[] { Messages.getMessage("field.idarticolo") }));
			throw new Exception(sb.toString());
		}

		List<ArticoliCategorie> data = null;
		try {

			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<ArticoliCategorie> cr = cb.createQuery(ArticoliCategorie.class);
			Root<ArticoliCategorie> root = cr.from(ArticoliCategorie.class);
			cr.select(root);
			List<Predicate> predicates = new ArrayList<Predicate>();

			predicates.add(cb.equal(root.get("idArticolo"), idArticolo));

			cr.where(predicates.toArray(new Predicate[0]));
			TypedQuery<ArticoliCategorie> query = em.createQuery(cr);

			data = query.getResultList();

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(Utils.errorInMethod(e.getMessage()));
			throw new Exception(Messages.getMessage("search.ko"));
		}
		return data;
	}

	@Override
	public boolean controllaCampiObbligatori(ArticoliCategorie object, List<ResponseMessage> msg) {
		if (!Utils.isValidId(object.getIdArticolo())) {
			logger.warn("Can't persist record 'ArticoliCategorie'  invalid field 'idArticolo'");
			msg.add(new ResponseMessage(Messages.getMessageFormatted("field.err.mandatory",
					new String[] { Messages.getMessage("field.idarticolo") })));
			return false;
		}
		if (!Utils.isValidId(object.getIdCategoria())) {
			logger.warn("Can't persist record 'ArticoliCategorie'  invalid field 'idCategoria'");
			msg.add(new ResponseMessage(Messages.getMessageFormatted("field.err.mandatory",
					new String[] { Messages.getMessage("field.idcategoria") })));
			return false;
		}
		return true;
	}

	@Override
	public void operazionePrimaDiInserire(ArticoliCategorie object) {
		List<ArticoliCategorie> articoliCategorie = repository.findAll(ArticoliCategorieSpecification.ConIdArticolo(object.getIdArticolo())
				.and(ArticoliCategorieSpecification.ConCategoriaId(object.getIdCategoria())));
		if (articoliCategorie.size() > 0) {
			throw new UserFriendlyException(Messages.getMessage("articoli.categoria.alreadyexist"));
		}
	}

	@Override
	public void operazionePrimaDiModificare(ArticoliCategorie object) {
		List<ArticoliCategorie> articoliCategorie = repository.findAll(ArticoliCategorieSpecification.ConIdArticolo(object.getIdArticolo())
				.and(ArticoliCategorieSpecification.ConCategoriaId(object.getIdCategoria()))
				.and(ArticoliCategorieSpecification.ConIdDiversoDa(object.getId())));
		if (articoliCategorie.size() > 0) {
			throw new UserFriendlyException(Messages.getMessage("articoli.categoria.alreadyexist"));
		}
	}
}
