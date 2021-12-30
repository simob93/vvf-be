package it.vvfriva.managers;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.vvfriva.entity.Articoli;
import it.vvfriva.repository.ArticoliRepository;
import it.vvfriva.utils.CustomException;
import it.vvfriva.utils.Messages;
import it.vvfriva.utils.ResponseMessage;
import it.vvfriva.utils.Utils;

/**
 * @author simone
 *
 */
@Service
public class ArticoliManager extends DbManagerStandard<Articoli> {

	final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	ArticoliRepository repository;
	@Autowired
	EntityManager em;

	/**
	 * Ritorna tutti gli articoli 
	 * @param idCategoria 
	 * @param idDeposito 
	 * @param descrizione 
	 * @return
	 * @throws Exception 
	 */
	public List<Articoli> list(String descrizione, Integer idDeposito, Integer idCategoria) throws Exception {
		List<Articoli> data = null;
		try {
			
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Articoli> cr = cb.createQuery(Articoli.class);
			Root<Articoli> root = cr.from(Articoli.class);
			cr.select(root);
			List<Predicate> predicates = new ArrayList<Predicate>();
			
			if (Utils.isValidId(idDeposito)) {
				predicates.add(cb.equal(root.join("depositi", JoinType.INNER).get("idDeposito"), idDeposito)); 
			}
			if (Utils.isValidId(idCategoria)) {
				predicates.add(cb.equal(root.join("categorie", JoinType.INNER).get("idCategoria"), idCategoria)); 
			}
			if (!Utils.isEmptyString(descrizione)) {
				predicates.add(cb.like(cb.lower(root.<String>get("descrizione")), "%"+ descrizione.toLowerCase() +"%"));
			}
			cr.orderBy(cb.asc(root.get("descrizione")));
			cr.where(predicates.toArray(new Predicate[0]));
			data =  em.createQuery(cr).getResultList();
			
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(Utils.errorInMethod(e.getMessage()));
			throw new Exception(Messages.getMessage("search.ko"));
		}
		return data;
	}


	@Override
	public boolean controllaCampiObbligatori(Articoli object, List<ResponseMessage> msg)
			throws CustomException, Exception {
		
		if (Utils.isEmptyString(object.getDescrizione())) {
			logger.warn("Can't persist record 'Articoli'  invalid field 'descrizione'");
			msg.add(new ResponseMessage(Messages.getMessageFormatted("field.err.mandatory",
					new String[] { Messages.getMessage("field.descrizione") })));
			return false;
		}
		return true;
	}
}
