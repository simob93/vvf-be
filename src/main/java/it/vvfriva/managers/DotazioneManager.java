package it.vvfriva.managers;

import java.math.BigDecimal;
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

import it.vvfriva.entity.Dotazione;
import it.vvfriva.repository.DotazioneRepository;
import it.vvfriva.utils.Messages;
import it.vvfriva.utils.Utils;
/**
 * 
 * @author simone
 *
 */
@Service
@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class DotazioneManager extends DbManagerStandard<Dotazione> {

	final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired DotazioneRepository repository;
	@Autowired EntityManager em;
	
	
	@Override
	public CrudRepository<Dotazione, Integer> getRepository() {
		return repository;
	}

	@Override
	public boolean checkCampiObbligatori(Dotazione object) {
		
		if (!Utils.isValidId(object.getIdVigile())) {
			logger.warn("Cant' persist record invalid field 'idVigile'"); 
			addMessage(Messages.getMessageFormatted("field.err.mandatory",
					new Object[] { Messages.getMessage("field.idVgile") }));
			return false;
		}
		if (!Utils.isValidId(object.getIdArticolo())) {
			logger.warn("Cant' persist record invalid field 'idArticolo'"); 
			addMessage(Messages.getMessageFormatted("field.err.mandatory",
					new Object[] { Messages.getMessage("field.idArticolo") }));
			return false;
		}
		if ((object.getQuantita() != null) && (object.getQuantita().compareTo(new BigDecimal("0")) <= 0)) {
			logger.warn("Cant' persist record invalid field 'quantita'"); 
			addMessage(Messages.getMessageFormatted("field.err.mandatory",
					new Object[] { Messages.getMessage("field.quantita") }));
			return false;
		}
		if (!Utils.isValidDate(object.getDataConsegna())) {
			logger.warn("Cant' persist record invalid field 'dataConsegna'"); 
			addMessage(Messages.getMessageFormatted("field.err.mandatory",
					new Object[] { Messages.getMessage("field.dataConsegna") }));
			return false;
		}
		
		return true;
	}

	@Override
	public boolean checkObjectForInsert(Dotazione object) {
		return true;
	}

	@Override
	public boolean checkObjectForUpdate(Dotazione object) {
		return true;
	}
	@Override
	public boolean checkObjectForDelete(Dotazione object) {
		return true;
	}
	
	/**
	 * Ricerca tutte le dotazione di un determinato vigile
	 * 
	 * @param idVigile
	 * @return
	 * @throws Exception
	 */
	public List<Dotazione> list(Integer idVigile) throws Exception {
		
		if (!Utils.isValidId(idVigile)) {
			StringBuffer sbuf = new StringBuffer();
			sbuf.append(Messages.getMessage("search.ko")).append(": ").append(
					Messages.getMessageFormatted("item.not.valid", new Object[] { Messages.getMessage("field.idVigile") }));
			logger.error("Error on method: " + this.getClass().getCanonicalName() + ".list ", sbuf.toString());
			throw new Exception(sbuf.toString());
		}
		 List<Dotazione> data = null;
		try {
			
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Dotazione> cr = cb.createQuery(Dotazione.class);
			Root<Dotazione> root = cr.from(Dotazione.class);
			
			cr.select(root);
			
			List<Predicate> predicates = new ArrayList<Predicate>();
			predicates.add(cb.equal(root.get("idVigile"), idVigile));
		    cr.where(predicates.toArray(new Predicate[0]));
			
		    TypedQuery<Dotazione> query = em.createQuery(cr);
			
		    cr.orderBy(cb.desc(root.get("dataConsegna")));
			
			data = query.getResultList();
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in method: " + this.getClass().getCanonicalName() + ".list", e.getMessage());
			throw new Exception(Messages.getMessage("search.ko"));
		} 
		return data;
	}

}
