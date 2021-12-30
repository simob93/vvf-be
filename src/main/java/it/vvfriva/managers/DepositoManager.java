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

import it.vvfriva.entity.Deposito;
import it.vvfriva.repository.DepositoRepository;
import it.vvfriva.utils.CustomException;
import it.vvfriva.utils.Messages;
import it.vvfriva.utils.ResponseMessage;
import it.vvfriva.utils.Utils;
/**
 * @author simone
 *
 */
@Service
public class DepositoManager extends DbManagerStandard<Deposito> {

	final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired DepositoRepository repository;
	@Autowired EntityManager em;
	
	/**
	 * Ritorna tutti i depositi 
	 * @param attivi     - se T solo depositi attivi se F anche non attivi
	 * @return
	 * @throws Exception 
	 */
	public List<Deposito> list(Boolean attivi) throws Exception {
		List<Deposito> data = null;
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Deposito> cr = cb.createQuery(Deposito.class);
			Root<Deposito> root = cr.from(Deposito.class);
			cr.select(root);
			List<Predicate> predicates = new ArrayList<Predicate>();
			
		    if (attivi != null && attivi.booleanValue()) {
		    	// solo deopositi attivi
		    	predicates.add(cb.equal(root.get("attivo"), Utils.getIntegerBoolValue(attivi.booleanValue())));	
		    }
		    cr.where(predicates.toArray(new Predicate[0]));
			TypedQuery<Deposito> query = em.createQuery(cr);
			
			data = query.getResultList();
		    
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(Utils.errorInMethod(e.getMessage()));
			throw new Exception(Messages.getMessage("search.ko"));
		}
		return data;
	}


	@Override
	public boolean controllaCampiObbligatori(Deposito object, List<ResponseMessage> msg)
			throws CustomException, Exception {
		if (Utils.isEmptyString(object.getDescrizione())) {
			logger.warn("Can't persist record 'Deposito'  invalid field 'descrizione'");
			msg.add(new ResponseMessage(Messages.getMessageFormatted("field.err.mandatory", new String[] {Messages.getMessage("field.descrizione")})));
			return false;
		}
		return true;
	}
}
