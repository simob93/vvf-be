package it.vvfriva.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.vvfriva.entity.VigileCertificati;
import it.vvfriva.enums.DbOperation;
import it.vvfriva.managers.DbManagerStandard;
import it.vvfriva.managers.VigileCertificatiManager;
import it.vvfriva.models.JsonResponse;
import it.vvfriva.models.ModelPortletVigileCertificati;
import it.vvfriva.utils.Messages;
import it.vvfriva.utils.Utils;

/**
 * 
 * @author simone
 *
 */
@Service
public class VigileCertificatiService extends DbServiceStandard<VigileCertificati> {
	
	@Autowired VigileCertificatiManager vigileCertificatiManager;
	final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 
	 * @param idVigile
	 * @return
	 */
	public JsonResponse<List<VigileCertificati>> list(Integer idVigile) {
		Boolean success = true;
		String message = "";
		JsonResponse<List<VigileCertificati>> result = null;
		List<VigileCertificati> data = null;
		try {
			data = vigileCertificatiManager.list(idVigile, false);
			message = Messages.getMessage("search.ok");
		} catch (Exception e) {
			logger.error("Exception in method: " + this.getClass().getCanonicalName() + ".list", e);
			message = Messages.getMessage("search.ko");
			success = false;
			e.printStackTrace();
		} finally {
			result = new JsonResponse<List<VigileCertificati>>(success, message, data);
		}
		return result;
	}

	public JsonResponse<List<VigileCertificati>> saveOrUpdate(List<VigileCertificati> vigileCertificati, DbOperation action) {
		/*
		 * per ogni certificato eseguo una insert or update in base se il campo id è
		 * avvalorato, i controlli di validità del dato vengono eseguiti all'interno del
		 * dbManager
		 */
		for (VigileCertificati vigileCertificato : vigileCertificati) {
			if (!Utils.isValidId(vigileCertificato.getId())) {
				vigileCertificato.setId(null);
				vigileCertificatiManager.save(vigileCertificato);
			} else {
				vigileCertificatiManager.update(vigileCertificato);
			}
		}
		return new JsonResponse<List<VigileCertificati>>(true, "", null);
	}
	
	public JsonResponse<List<ModelPortletVigileCertificati>> listForPortlet(Integer idVigile) {
		JsonResponse<List<ModelPortletVigileCertificati>> result = null;
		List<ModelPortletVigileCertificati> data = null;
		Boolean success = true;
		String message = "";
		try {
			data = vigileCertificatiManager.listForPortlet(idVigile);
			message = Messages.getMessage("search.ok");
		} catch (Exception e) {
			success = false;
			message = Messages.getMessage("search.ko");
			logger.error("Excepetion in function: " + this.getClass().getCanonicalName() + ".listForPortlet", e);

		} finally {
			result = new JsonResponse<List<ModelPortletVigileCertificati>>(success, message, data);
		}
		return result;
	}

	@Override
	public DbManagerStandard<VigileCertificati> getManager() {
		return this.vigileCertificatiManager;
	}
}
