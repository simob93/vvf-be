package it.vvfriva.managers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import it.vvfriva.entity.Protocol;
import it.vvfriva.enums.DbOperation;
import it.vvfriva.repository.ProtocolRepository;
import it.vvfriva.utils.Messages;
import it.vvfriva.utils.Utils;
/**
 * Manager per la gestione dei protoccoli 
 * @author simone
 *
 */
@Service
@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ProtocolManager extends DbManagerStandard<Protocol> {
	
	final int SEARCH_FIELD_OBJECT = 1;
	final int SEARCH_FIELD_STRUID = 2;
	
	final Logger log = LoggerFactory.getLogger(this.getClass());	
	@Autowired 
	ProtocolRepository protocolRepository;
	
	@PersistenceContext
	EntityManager em;

	
	@Override
	public CrudRepository<Protocol, Integer> getRepository() {
		return protocolRepository;
	}
	
	/*******************************************************************************************************
	 * QUERY
	 *******************************************************************************************************/
	
	/**
	 * 
	 * @param firstResult     - possibilita di paginare la lista
	 * @param maxResult       - possibilitÓ di paginare la lista
	 * @param oggetto            - campo di ricerca
	 * @param idFaldone      - tipologia di ricerca
	 * @param from            - a partire da
	 * @param to              - fino a
	 * @param numeroProtocollo    - tipologia di protocollo
	 * @return ritorna una lista di protocolli filtrata per i parametri in ingresso 
	 * @throws Exception
	 */
	public List<Protocol> list(Integer firstResult, Integer maxResult, String oggetto, Integer idFaldone, String tipologia, Date from, Date to, String numeroProtocollo) throws Exception {
		List<Protocol> data = null;
		if (!Utils.geZero(firstResult)) {
			log.error("Exception in method: " + this.getClass().getCanonicalName() + ".list invalid field firstResult");
			StringBuilder sb = new StringBuilder();
			sb.append(Messages.getMessage("search.ko")).append(": ")
			.append(Messages.getMessageFormatted("field.err.mandatory", new String[] {"firstResult"}));
			throw new Exception(sb.toString());
		}
		if (!Utils.geZero(maxResult)) {
			log.error("Exception in method: " + this.getClass().getCanonicalName() + ".list invalid field maxResult");
			StringBuilder sb = new StringBuilder();
			sb.append(Messages.getMessage("search.ko")).append(": ")
			.append(Messages.getMessageFormatted("field.err.mandatory", new String[] {"maxResult"}));
			throw new Exception(sb.toString());
		}
		try {
					
			CriteriaBuilder cb = em.getCriteriaBuilder();
		    CriteriaQuery<Protocol> cq = cb.createQuery(Protocol.class);
		 
		    Root<Protocol> person = cq.from(Protocol.class);
		    List<Predicate> predicates = new ArrayList<Predicate>();
		    
		    if (Utils.isValidDate(from)) {
		    	predicates.add(cb.greaterThanOrEqualTo(person.get("date"), Utils.startOfDay(from)));
		    }
		    if (Utils.isValidDate(to)) {
		    	predicates.add(cb.lessThanOrEqualTo(person.get("date"), Utils.endOfDay(to)));
		    }
		    
		    if (!Utils.isEmptyString(oggetto)) {
		    	predicates.add(cb.like(cb.upper(person.get("object")),"%" + oggetto.toUpperCase() + "%"));
		    }
		    if (!Utils.isEmptyString(numeroProtocollo)) {
		    	predicates.add(cb.like(person.get("strUid"), "%" + numeroProtocollo + "%"));
		    }
		    if (!Utils.isEmptyString(tipologia)) {
		    	predicates.add(cb.equal(person.get("type"), tipologia));
		    }
		    if (Utils.isValidId(idFaldone)) {
		    	predicates.add(cb.equal(person.get("idArchive"), idFaldone));
		    }
		    List<Order> orderList = new ArrayList<Order>();
		    cq.where(predicates.toArray(new Predicate[0]));
		    orderList.add(cb.desc(person.get("date")));
		    orderList.add(cb.desc(person.get("strUid")));
		    cq.orderBy(orderList);
		    data = em.createQuery(cq)
		    		.setFirstResult(firstResult)
		    		.setMaxResults(maxResult)
		    		.getResultList();
				
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error("Exception in method: " + this.getClass().getCanonicalName() + ".list", ex);
			throw new Exception(Messages.getMessage("search.ko"));
		}
		return data;
	}
	
	/*******************************************************************************************************
	 * Metodo utili al DbManagerStandard durante le CRUD OPERATION
	 *******************************************************************************************************/
	@Override
	public boolean checkCampiObbligatori(Protocol object) {
		
		//data del protoccollo obbligatoria
		if (!Utils.isValidDate(object.getDate())) {
			logger.error("Can't persist record invalid field 'date'");
			addMessage(Messages.getMessageFormatted("field.err.mandatory", new Object[] {"Data"}));
			return false;
		}
		//tipo protocollo obbligatorio
		if (Utils.isEmptyString(object.getType())) {
			logger.error("Can't persist record invalid field 'type'");
			addMessage(Messages.getMessageFormatted("field.err.mandatory", new Object[] {"Tipo protocollo (E/U)"}));
			return false;
		}
		if (Utils.isEmptyString(object.getObject())) {
			logger.error("Can't persist record invalid field 'object'");
			addMessage(Messages.getMessageFormatted("field.err.mandatory", new Object[] {"Oggetto del protocollo"}));
			return false;
		}
		
		return true;
	}

	@Override
	public boolean checkObjectForInsert(Protocol object) {
		return true;
	}

	@Override
	public boolean checkObjectForUpdate(Protocol object) {
		return true;
	}

	/* (non-Javadoc)
	 * @see it.vvfriva.managers.DbManagerStandard#afterDbAction(it.vvfriva.enums.DbOperation, java.lang.Object)
	 */
	@Override
	public boolean afterDbAction(DbOperation action, Protocol object) {
		try {
			
			if (action == DbOperation.INSERT) {
				//calc numero strUid whit procedure 
				em.flush();
				em.createNamedStoredProcedureQuery("calc_num_prot").setParameter("id", object.getId())
				.executeUpdate();
			}
			
		} catch (Exception e) {
			log.error("Exception in method: " + this.getClass().getCanonicalName() + ".afterDbAction", e);
			addMessage(e.getMessage());
			return false;
		}
		return true;
	}

	@Override
	public boolean checkObjectForDelete(Protocol object) {
		return true;
	}
}
