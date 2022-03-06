package it.vvfriva.managers;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.jfree.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import it.vvfriva.entity.Utenti;
import it.vvfriva.entity.UtentiRuoli;
import it.vvfriva.enums.RuoliDefault;
import it.vvfriva.repository.UtentiRepository;
import it.vvfriva.utils.Messages;
import it.vvfriva.utils.ResponseMessage;
import it.vvfriva.utils.Utils;
/**
 * 
 * @author simone
 *
 */
@Service
public class UtentiManager extends DbManagerStandard<Utenti> implements UserDetailsService {
	
	private final Logger logger = LoggerFactory.getLogger(UtentiManager.class);

	@Autowired UtentiRepository repository;
	@Autowired PasswordEncoder bcryptEncoder;
	@PersistenceContext EntityManager em;
	@Autowired UtentiRuoliManager utentiRuoliManager;
	
	
	public List<Utenti> listUtenti() throws Exception {
		List<Utenti> data = null;
		try {

			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Utenti> cq = cb.createQuery(Utenti.class);
			Root<Utenti> ut = cq.from(Utenti.class);
			List<Predicate> predicates = new ArrayList<Predicate>();
			cq.where(predicates.toArray(new Predicate[0]));
			data = em.createQuery(cq).getResultList();

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(Utils.errorInMethod(e.getMessage()));
			throw new Exception(Messages.getMessage("search.ko"));
		}
		return data;
	}
	/**
	 * 
	 * @param username
	 * @return
	 * @throws Exception 
	 */
	public Utenti getByUserName(String username) throws Exception {
		
		if (Utils.isEmptyString(username)) {
			StringBuilder sbuf = new StringBuilder();
			sbuf.append(Messages.getMessage("search.ko")).append(": ")
			.append(Messages.getMessageFormatted("field.err.mandatory", new String[] {"Username"}));
			throw new Exception(sbuf.toString());
		}
		Utenti data = null;
		try {
			
			CriteriaBuilder cb = em.getCriteriaBuilder();
		    CriteriaQuery<Utenti> cq = cb.createQuery(Utenti.class);
		 
		    Root<Utenti> ut = cq.from(Utenti.class);
		    List<Predicate> predicates = new ArrayList<Predicate>();
		    predicates.add(cb.equal(cb.upper(ut.get("username")), username.toUpperCase()));
		    cq.where(predicates.toArray(new Predicate[0]));
		    List<Utenti> listUtenti = em.createQuery(cq)
		    		.setMaxResults(1)
		    		.getResultList();
		    
		    if (!Utils.isEmptyList(listUtenti)) {
		    	data = listUtenti.get(0);
		    }
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(Messages.getMessage("search.ko"));
		}
		return data;
		
	}
	
	/**
	 * Controlla la presenza di un record duplicato, avente stesso username
	 * 
	 * @param utente
	 * @return true (se record duplicato) false (se il record non è gia stato inserito)
	 * @throws Exception
	 */
	private boolean checkRecordDuplicato(Utenti utente) throws Exception {
		List<Utenti> data = null;
		try {
			
			CriteriaBuilder cb = em.getCriteriaBuilder();
		    CriteriaQuery<Utenti> cq = cb.createQuery(Utenti.class);
		 
		    Root<Utenti> ut = cq.from(Utenti.class);
		    List<Predicate> predicates = new ArrayList<Predicate>();
		    predicates.add(cb.equal(ut.get("username"), utente.getUsername()));
		    if (Utils.isValidId(utente.getId())) {
		    	predicates.add(cb.notEqual(ut.get("id"), utente.getId()));	
		    }
		    cq.where(predicates.toArray(new Predicate[0]));
		    data = em.createQuery(cq)
		    		.setMaxResults(1)
		    		.getResultList();
			
		} catch (Exception e) {
			e.printStackTrace();
			Log.error(Utils.errorInMethod(e.getMessage()));
			throw new Exception(Messages.getMessage("search.ko"));
		} 
		return !Utils.isEmptyList(data);
	}
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			List<SimpleGrantedAuthority> roles= new ArrayList<SimpleGrantedAuthority>();
			Utenti user = this.getByUserName(username);
			if (user == null) {
				throw new UsernameNotFoundException(Messages.getMessage("login.username.not.found"));
			}
			return new User(user.getUsername(), user.getPwd(), roles);
		} catch (Exception e) {
			e.printStackTrace();
			throw new UsernameNotFoundException(e.getMessage());
		}
	}
	
	@Override
	public boolean controllaCampiObbligatori(Utenti object, List<ResponseMessage> msg) {
		if (Utils.isEmptyString(object.getNome())) {
			logger.error(Utils.errorInMethod("Cant't persist record Utenti invalid field nome"));
			msg.add(new ResponseMessage(Messages.getMessageFormatted("field.err.mandatory", new String[] {"Nome"})));
			return false;
		}
		if (Utils.isEmptyString(object.getCognome())) {
			logger.error(Utils.errorInMethod("Cant't persist record Utenti invalid field cognome"));
			msg.add(new ResponseMessage(Messages.getMessageFormatted("field.err.mandatory", new String[] {"Cognome"})));
			return false;
		}
		if (Utils.isEmptyString(object.getEmail())) {
			logger.error(Utils.errorInMethod("Cant't persist record Utenti invalid field email"));
			msg.add(new ResponseMessage(Messages.getMessageFormatted("field.err.mandatory", new String[] {"Email"})));
			return false;
		}
		if (Utils.isEmptyString(object.getUsername())) {
			logger.error(Utils.errorInMethod("Cant't persist record Utenti invalid field username"));
			msg.add(new ResponseMessage(Messages.getMessageFormatted("field.err.mandatory", new String[] {"Username"})));
			return false;
		}
		
		//controllo utente duplicato
		boolean exists = false;
		try {
			exists = checkRecordDuplicato(object);
			if (exists) {
				logger.error(Utils.errorInMethod("record already exists!!"));
				msg.add(new ResponseMessage(Messages.getMessage("register.user.exists")));
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(Utils.errorInMethod(e.getMessage()));
			msg.add(new ResponseMessage(e.getMessage() != null ? e.getMessage() : Messages.getMessage("operation.ko")));
		}
		return true;
	}
	
	
	
	@Override
	public void operazionePrimaDiModificare(Utenti object) {
		Utenti utente = this.getObjById(object.getId());
		if (utente != null) {
			object.setPwd(utente.getPwd());
		}
	}
	@Override
	public void operazionePrimaDiInserire(Utenti object) {
		if (Utils.isEmptyList(object.getRuolo())) {
			//creazione ruolo utenti di default;
			UtentiRuoli ruolo = new UtentiRuoli();
			ruolo.setId(null);
			ruolo.setIdRuolo(RuoliDefault.USER.getId());
			List<UtentiRuoli> listRoles = new ArrayList<UtentiRuoli>();
			listRoles.add(ruolo);
			object.setRuolo(listRoles);
		}
	}
}
