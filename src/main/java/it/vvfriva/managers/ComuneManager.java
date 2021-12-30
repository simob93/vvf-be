package it.vvfriva.managers;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.vvfriva.entity.Comuni;
import it.vvfriva.repository.ComuniRepository;
import it.vvfriva.utils.CustomException;
import it.vvfriva.utils.Messages;
import it.vvfriva.utils.Utils;
/**
 * Manager per la gestione dei comuni, 
 * solitamente i comnuni vengono aggiornati tramite script
 * 
 * @author simone
 *
 */
@Service
public class ComuneManager  {

	final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired ComuniRepository repository;
	@Autowired EntityManager em;
	
	/**
	 * filtra la lista dei comunnica in base al codice provicinale passato a parametro
	 * @param istatProvincia
	 * @return
	 * @throws Exception
	 */
	public List<Comuni> list(Integer istatProvincia) throws Exception {
		
		if (!Utils.isValidId(istatProvincia)) {
			log.error("Exception in function: " + this.getClass().getCanonicalName()+ ".list invalid filed istatProvincia");
			StringBuilder sb = new StringBuilder(Messages.getMessage("search.ko"))
			.append(": ").append(Messages.getMessageFormatted("field.err.mandatory", new Object[] {"istatProvincia"}));
			throw new CustomException(sb.toString());
		}
		List<Comuni> data = null;
		try {
			
			CriteriaBuilder cb = em.getCriteriaBuilder();
		    CriteriaQuery<Comuni> cq = cb.createQuery(Comuni.class);
		    Root<Comuni> comune = cq.from(Comuni.class);
		    cq.where(cb.equal(comune.get("istatProvicina"), istatProvincia));
		    cq.orderBy(cb.asc(comune.get("name")));
			data = em.createQuery(cq).getResultList();

		} catch (Exception e) {
			e.printStackTrace();
			log.error("Exception in function: " + this.getClass().getCanonicalName() + ".list", e);
			throw new Exception(Messages.getMessage("search.ko"));
		}
		return data;
	}
}
