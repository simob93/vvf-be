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
import org.springframework.stereotype.Service;

import it.vvfriva.entity.Provincie;
import it.vvfriva.repository.ProvincieRepository;
import it.vvfriva.utils.Messages;
/**
 * Manager per la gestione dell provincine
 * @author simone
 *
 */
@Service
@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ProvinciaManager {
    
	final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired ProvincieRepository repository;
	@Autowired EntityManager em;
	
	/**
	 * 
	 * @return lista di provincie
	 * @throws Exception
	 */
	public List<Provincie> listCity() throws Exception {
		List<Provincie> data = null;
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
		    CriteriaQuery<Provincie> cq = cb.createQuery(Provincie.class);
		    Root<Provincie> scad = cq.from(Provincie.class);
		    cq.orderBy(cb.asc(scad.get("name")));
			data = em.createQuery(cq).getResultList();

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception in function: " + this.getClass().getCanonicalName() + ".list", e);
			throw new Exception(Messages.getMessage("search.ko"));
		}
		return data;
	}
	
}
