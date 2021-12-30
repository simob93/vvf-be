package it.vvfriva.managers;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import it.vvfriva.entity.VigileCertificati;
import it.vvfriva.models.KeyValueTipiScadenza;
import it.vvfriva.models.ModelPortletVigileCertificati;
import it.vvfriva.models.ModelPrntAutorizzazioni;
import it.vvfriva.repository.VigileCertificatiRepository;
import it.vvfriva.utils.CostantiVVF;
import it.vvfriva.utils.CustomException;
import it.vvfriva.utils.Messages;
import it.vvfriva.utils.ResponseMessage;
import it.vvfriva.utils.Utils;
/**
 * 
 * @author simone
 *
 */
@Service
@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class VigileCertificatiManager extends DbManagerStandard<VigileCertificati> {

	final Logger log = LoggerFactory.getLogger(this.getClass());

	
	@Autowired VigileCertificatiRepository repository;
	@Autowired ScadenzeManager scadenzeManager;
	@Autowired EntityManager em;
	
	/**
	 * 
	 * @param idVigile
	 * @return
	 * @throws Exception
	 */
	public List<VigileCertificati> list(Integer idVigile) throws Exception {
		return this.list(idVigile, true);
	}
	/**
	 * 
	 * @param idVigile
	 * @param onlyEnableExpiry
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<VigileCertificati> list(Integer idVigile, Boolean onlyEnableExpiry) throws Exception {
		
		if (!Utils.isValidId(idVigile)) {
			StringBuilder sb = new StringBuilder();
			sb.append(Messages.getMessage("search.ko")).append(": ")
			.append(Messages.getMessageFormatted("field.err.mandatory",  new String[] {"idVigile"}));
			throw new Exception(sb.toString());
		}
		List<VigileCertificati> data = null;
		try {
			String query = "select new it.vvfriva.entity.VigileCertificati(c.id, c.idVigile, c.idPerson, c.date, p.name) From VigileCertificati c join Person p on p.id = c.idPerson and c.idVigile = :idVigile";
			if (onlyEnableExpiry) {
				query += " and  p.enabledExpiry = :enable";
			}
			Query cb = em.createQuery(query);
			cb.setParameter("idVigile", idVigile);

			if (onlyEnableExpiry) {
				cb.setParameter("enable", CostantiVVF.ACTIVE);
			}
			data = cb.getResultList();
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Exception in function: " + this.getClass().getCanonicalName()+ ".list", e);
		}
		return data;
		
	}
	public List<KeyValueTipiScadenza> getCbox(Integer idVigile) throws Exception {
		return getCbox(idVigile, true);
	}
	/**
	 * 
	 * @param idVigile
	 * @return
	 * @throws Exception
	 */
	public List<KeyValueTipiScadenza> getCbox(Integer idVigile, Boolean onlyEnableExpiry) throws Exception {
		List<KeyValueTipiScadenza> data = new ArrayList<KeyValueTipiScadenza>();
		try {
			List<VigileCertificati> listVigileCertificati = this.list(idVigile, onlyEnableExpiry);
			if (!Utils.isEmptyList(listVigileCertificati)) {
				listVigileCertificati.forEach(patente -> {
					data.add(new KeyValueTipiScadenza(patente.getId(), patente.getDescrPerson(), null,
							patente.getIdVigile(), patente.getIdPerson()));
				});
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.error("Exception in function: " + this.getClass().getCanonicalName() + ".getCbox ", e);
			throw new CustomException(Messages.getMessage("search.ko"));
		}
		return data;
	}
	
	/**
	 * 
	 * @param idVigile
	 * @return
	 * @throws CustomException 
	 */
	public List<ModelPortletVigileCertificati> listForPortlet(Integer idVigile) throws CustomException {
		if (!Utils.isValidId(idVigile)) {
			log.error("Exception in function: " + this.getClass().getCanonicalName()+ ".listForPortlet invalid filed idVigile");
			StringBuilder sb = new StringBuilder(Messages.getMessage("search.ko"))
			.append(": ").append(Messages.getMessageFormatted("field.err.mandatory", new Object[] {"idVigile"}));
			throw new CustomException(sb.toString());
		}
		List<ModelPortletVigileCertificati> data = null;
		try {
	        data =  repository.listForPortlet(idVigile);
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Exception in function: " + this.getClass().getCanonicalName()+ ".listForPortlet invalid filed idVigile");
			throw new CustomException(Messages.getMessage("search.ko"));
		}
		return data;
	}
	/**
	 * 
	 * @param idVigile
	 * @return
	 * @throws CustomException
	 */
	public List<ModelPrntAutorizzazioni> listForReport() throws CustomException {
		
		List<ModelPrntAutorizzazioni> data = null;
		try {
	        data =  repository.listForReport(Sort.by(Order.asc("v.lastName")));
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Exception in function: " + this.getClass().getCanonicalName()+ ".listForPortlet invalid filed idVigile");
			throw new CustomException(Messages.getMessage("search.ko"));
		}
		return data;
	}

	/**
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	private boolean hasRecordDoppi(VigileCertificati object) throws Exception {
		List<VigileCertificati> data = null;
		try {
			
			CriteriaBuilder cb = em.getCriteriaBuilder();
		    CriteriaQuery<VigileCertificati> cq = cb.createQuery(VigileCertificati.class);
		 
		    Root<VigileCertificati> vigileCert = cq.from(VigileCertificati.class);
		    List<Predicate> predicates = new ArrayList<Predicate>();
		    
		    		    
		    predicates.add(cb.equal(vigileCert.get("idPerson"), object.getIdPerson()));
		    predicates.add(cb.equal(vigileCert.get("idVigile"), object.getIdVigile()));
		    
		    
		    if (Utils.isValidId(object.getId())) {
		    	 predicates.add(cb.notEqual(vigileCert.get("id"), object.getId()));
		    }
		    
		    cq.where(predicates.toArray(new Predicate[0]));
		    data =  em.createQuery(cq).setMaxResults(1).getResultList();	    
			if (!Utils.isEmptyList(data)) {
				return true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Exception in function " + this.getClass().getCanonicalName() + ".hasRecordDoppi", e);
			throw new Exception(Messages.getMessage("search.ko"));
		}
		return false;
	}


	@Override
	public boolean controllaCampiObbligatori(VigileCertificati object, List<ResponseMessage> msg)
			throws CustomException, Exception {
		/*
		 * sono neccessari al fine di un insermiento ok! i campi numero patente
		 * solitamente alfanumerico data di svolgimento patente e tipologia di patete
		 */
		if (!Utils.isValidDate(object.getDate())) {
			log.warn("Can't persist record invalid field date");
			msg.add(new ResponseMessage(Messages.getMessageFormatted("field.err.mandatory", new Object[] {"Data di inizio"})));
			return false;
		}
		if (!Utils.isValidId(object.getIdPerson())) {
			log.warn("Can't persist record invalid field id person");
			msg.add(new ResponseMessage(Messages.getMessageFormatted("field.err.mandatory", new Object[] {"Tipo patente"})));
			return false;
		}
		//controllo record doppi
		try {
			boolean exists = hasRecordDoppi(object);
			if (exists) {
				msg.add(new ResponseMessage(Messages.getMessage("record.already.exists")));
				return false;
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
			msg.add(new ResponseMessage(ex.getMessage()));
			return false;
		}
		return true;
	}
	@Override
	public void operazioneDopoInserimento(VigileCertificati object) throws Exception, CustomException {
		if (Utils.isValidDate(object.getDateExpiration())) {
			scadenzeManager.insertExp(object.getId(), object.getDate(), 
					object.getDateExpiration(), CostantiVVF.AREA_AUTORIZZAZIONI, object.getIdVigile());
		}
	}
}
