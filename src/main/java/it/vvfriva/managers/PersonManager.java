package it.vvfriva.managers;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.vvfriva.entity.Person;
import it.vvfriva.entity.SettingScadenze;
import it.vvfriva.repository.PersonRepository;
import it.vvfriva.utils.CostantiVVF;
import it.vvfriva.utils.Messages;
import it.vvfriva.utils.ResponseMessage;
import it.vvfriva.utils.Utils;

/**
 * Manager per la gestione delle voci personalizzabili 
 * @author simone
 *
 */
@Service
public class PersonManager extends DbManagerStandard<Person> {

	@Autowired
	PersonRepository personRepository;
	@Autowired
	PersonScadenzeManager personScadenzeManager;
	
	@PersistenceContext
	EntityManager em;

	final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 
	 * @param idAreas        -lista di idarea
	 * @param listStrId      -lista di voci
	 * @param gestScadenza   - prende le voci che hanno la gestione scadenze
	 * @param loadScadenze   - esegue una query per caricane i dettagli della scadenze 
	 * @return
	 * @throws Exception
	 */
	public List<Person> list(List<Integer> idAreas, String listStrId, Boolean gestScadenza, boolean loadScadenze) {
		List<Person> data = null;
		CriteriaBuilder cb = em.getCriteriaBuilder();
	    CriteriaQuery<Person> cq = cb.createQuery(Person.class);
	 
	    Root<Person> person = cq.from(Person.class);
	    List<Predicate> predicates = new ArrayList<Predicate>();
	    
	    if (!Utils.isEmptyList(idAreas)) {
	    	In<Integer> inClause = cb.in(person.get("idArea"));
	    	for (Integer idArea : idAreas) {
	    		inClause.value(idArea);
	    	}
	        predicates.add(inClause);
	        
	    } else if (!Utils.isEmptyString(listStrId)) {
	    	In<Integer> inClause = cb.in(person.get("id"));
	    	List<Integer>ids = Utils.convertStringToIntegerList(listStrId);
	    	for (Integer idArea : ids) {
	    		inClause.value(idArea);
	    	}
	        predicates.add(inClause);
	    }
	    if (gestScadenza != null && gestScadenza) {
	    	predicates.add(cb.equal(person.get("enabledExpiry"), CostantiVVF.TRUE));
	    }
	    cq.where(predicates.toArray(new Predicate[0]));
	    cq.orderBy(cb.asc(person.get("name")));
	    data = em.createQuery(cq).getResultList();
	    
	    if (!Utils.isEmptyList(data) && loadScadenze) {
			for(Person p: data) {
				if (p.getEnabledExpiry()) {
					SettingScadenze scadenza = personScadenzeManager.getByIdPerson(p.getId());
					if (scadenza != null) {
						p.setScadenza(scadenza);
					}
				}
			}
		}
	    return data;
	}
	/**
	 * filtra per la sola  idArea passata a parametro 
	 * @param idArea
	 * @return ritorna una lista di voci personalizzabili apparteneti ad una area specificata
	 * @throws Exception
	 */
	public List<Person> getBy(Integer idArea) throws Exception {

		if (!Utils.isValidId(idArea)) {
			logger.error("idArea in function: 'getBy' invalid field idArea");
			StringBuilder sb = new StringBuilder();
			sb.append(Messages.getMessage("search.ko")).append(": ");
			sb.append(Messages.getMessageFormatted("field.err.mandatory",
					new Object[] { "idArea" }));
			throw new Exception(sb.toString());
		}

		List<Person> data = null;
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
		    CriteriaQuery<Person> cq = cb.createQuery(Person.class);
		 
		    Root<Person> person = cq.from(Person.class);
		    List<Predicate> predicates = new ArrayList<Predicate>();
		    predicates.add(cb.equal(person.get("idArea"),  idArea));
		    cq.where(predicates.toArray(new Predicate[0]));
		    
		    if (Utils.integerCompareTo(idArea, CostantiVVF.AREA_FALDONI) == 0) {
		    	cq.orderBy(cb.asc(person.get("extra")));
		    }
		    data = em.createQuery(cq).getResultList();	 
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception in functiion: " + this.getClass().getCanonicalName() + ".listByArea", e);
			throw new Exception(Messages.getMessage("search.ko"));
		}
		return data;
	}

	@Override
	public boolean controllaCampiObbligatori(Person object, List<ResponseMessage> msg) {
		// campi obbligatori
		if (!Utils.isValidId(object.getIdArea())) {
			logger.warn("Can't persist  record Person invalid filed idArea");
			msg.add(new ResponseMessage(Messages.getMessageFormatted("field.err.mandatory", new String[] { "idArea" })));
			return false;
		}
		if (Utils.isEmptyString(object.getName())) {
			logger.warn("Can't persist  record Person invalid filed name");
			msg.add(new ResponseMessage(Messages.getMessageFormatted("field.err.mandatory", new String[] { "Descrizione" })));
			return false;
		}
		
		if (object.getEnabledExpiry()) {
			if ( object.getScadenza() == null) {
				logger.warn("Can't persist  record Person invalid filed scadenza");
				msg.add(new ResponseMessage(Messages.getMessageFormatted("field.err.mandatory", new String[] { "Scadenza non compilata!" })));
				return false;
			} else {
				
				if (!Utils.isValidId(object.getScadenza().getExpirationEvery()) ) {
					logger.warn("Can't persist  record Person invalid filed expiration every");
					msg.add(new ResponseMessage(Messages.getMessageFormatted("field.err.mandatory", new String[] { "frequenza non impostata" })));
					return false;
				}
				
				if ( Utils.isEmptyString(object.getScadenza().getExpirationType())) {
					logger.warn("Can't persist  record Person invalid filed expiration every");
					msg.add(new ResponseMessage(Messages.getMessageFormatted("field.err.mandatory", new String[] { "Ogni quanto non impostato" })));
					return false;
				}
			}
		}
		return true;
	}
	
	@Override
	public void operazioneDopoInserimento(Person object)  {
		if (object.getEnabledExpiry()) {
			this.personScadenzeManager.update(object.getScadenza());
		} else {
			SettingScadenze settScadenza = personScadenzeManager.getByIdPerson(object.getId());
			if (settScadenza != null) {
				personScadenzeManager.delete(settScadenza);
			}
		}
	}
	@Override
	public void operazionePrimaDiInserire(Person object) {
		if (object.getScadenza() != null && object.getEnabledExpiry()) {
			object.getScadenza().setIdPerson(object.getId());
		} 
	}
	@Override
	public void operazioneDopoModifica(Person object) {
		if (object.getEnabledExpiry()) {
			this.personScadenzeManager.update(object.getScadenza());
		} else {
			SettingScadenze settScadenza = personScadenzeManager.getByIdPerson(object.getId());
			if (settScadenza != null) {
				personScadenzeManager.delete(settScadenza);
			}
		}
	}
	@Override
	public void operazionePrimaDiModificare(Person object) {
		if (object.getScadenza() != null && object.getEnabledExpiry()) {
			object.getScadenza().setIdPerson(object.getId());
		}
	}

}
