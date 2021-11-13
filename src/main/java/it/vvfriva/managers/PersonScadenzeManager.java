package it.vvfriva.managers;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import it.vvfriva.entity.SettingScadenze;
import it.vvfriva.repository.PersonScadenzeRepository;
import it.vvfriva.utils.Messages;
import it.vvfriva.utils.Utils;

@Service
@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class PersonScadenzeManager extends DbManagerStandard<SettingScadenze> {

	final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired PersonScadenzeRepository repository;
	
	@Autowired EntityManager em; 
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<SettingScadenze> listFrequenze() throws Exception {
		List<SettingScadenze> data = null;
		try {
			
			CriteriaBuilder cb = em.getCriteriaBuilder();
		    CriteriaQuery<SettingScadenze> cq = cb.createQuery(SettingScadenze.class);
		    Root<SettingScadenze> person = cq.from(SettingScadenze.class);
		    data = em.createQuery(cq).getResultList();
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception in functiion: " + this.getClass().getCanonicalName() +".getByIdPerson", e);
			throw new Exception(Messages.getMessage("search.ko"));
		}
		return data;
	}
	
	/**
	 * 
	 * @param idPerson
	 * @return
	 * @throws Exception
	 */
	public SettingScadenze getByIdPerson(Integer idPerson) throws Exception {
		
		if (!Utils.isValidId(idPerson)) {
			logger.error("Exception in function: 'getByIdPerson' invalid field idPerson");
			StringBuilder sb = new StringBuilder();
			sb.append(Messages.getMessage("search.ko")).append(": ");
			sb.append(Messages.getMessageFormatted("field.err.mandatory", new Object[] { "idPerson" }));
		}
		List<SettingScadenze> data = null;
		try {
			
			CriteriaBuilder cb = em.getCriteriaBuilder();
		    CriteriaQuery<SettingScadenze> cq = cb.createQuery(SettingScadenze.class);
		    Root<SettingScadenze> person = cq.from(SettingScadenze.class);
		    cq.where(cb.equal(person.get("idPerson"), idPerson));
		    data = em.createQuery(cq).getResultList();
		    
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception in functiion: " + this.getClass().getCanonicalName() +".getByIdPerson", e);
			throw new Exception(Messages.getMessage("search.ko"));
		}
		return !Utils.isEmptyList(data) ? data.get(0) : null;
	}
	
	@Override
	public CrudRepository<SettingScadenze, Integer> getRepository() {
		return repository;
	}

	@Override
	public boolean checkCampiObbligatori(SettingScadenze object) {
		return true;
	}

	@Override
	public boolean checkObjectForInsert(SettingScadenze object) {
		return true;
	}

	@Override
	public boolean checkObjectForUpdate(SettingScadenze object) {
		return true;
	}

	@Override
	public boolean checkObjectForDelete(SettingScadenze object) {
		return true;
	}

}
