package it.vvfriva.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.vvfriva.entity.VigilePatenti;
import it.vvfriva.enums.DbOperation;
import it.vvfriva.managers.DbManagerStandard;
import it.vvfriva.managers.VigilePatentiManager;
import it.vvfriva.models.JsonResponse;
import it.vvfriva.models.ModelPortletVigilePatenti;
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
public class VigilePatentiService extends DbServiceStandard<VigilePatenti> {
	
	@Autowired
	VigilePatentiManager vigilePatentiManager;
	
	@Override
	public DbManagerStandard<VigilePatenti> getManager() {
		return vigilePatentiManager;
	}
	
	final Logger logger = LoggerFactory.getLogger(this.getClass());
	/**
	 * 
	 * @param idVigile
	 * @return
	 */
	public JsonResponse<List<VigilePatenti>> list(Integer idVigile) {
		Boolean success = true;
		String message = "";
		JsonResponse<List<VigilePatenti>> result = null;
		List<VigilePatenti> data = null;
		try {
			data = vigilePatentiManager.list(idVigile);
			message = Messages.getMessage("search.ok");
		} catch (Exception e) {
			message = Messages.getMessage("search.ko");
			success = false;
			e.printStackTrace();
		} finally {
			result = new JsonResponse<List<VigilePatenti>>(success, message, data);
		}
		return result;
	}
	/**
	 * 
	 * @param vigilePatenti
	 * @param action
	 * @return
	 */
	public JsonResponse<List<VigilePatenti> > saveOrUpdate(List<VigilePatenti>  vigilePatenti, DbOperation action) {
		
		Boolean success = true;
		List<ResponseMessage> message = new ArrayList<>();
		JsonResponse<List<VigilePatenti>> result = null;
		try {
			/*
			 * per ogni patente eseguo una insert or update in base se il campo id è
			 * avvalorato i controlli di validità del dato vengono eseguiti all'interno del
			 * dbManager
			 */
			for (VigilePatenti vigilePatente: vigilePatenti) {
				if (!Utils.isValidId(vigilePatente.getId())) {
					vigilePatente.setId(null);
				}
				vigilePatentiManager.dbManager(action, vigilePatente);
			}
			message.add(new ResponseMessage(ResponseMessage.MSG_TYPE_LOUD, Messages.getMessage("operation.ok")));
			
		}catch (CustomException ex) {
			success = false;
			message = ex.getErrorList();
			ex.printStackTrace();
			logger.error("Excepetion in function: " + this.getClass().getCanonicalName() + ".saveOrUpdate", ex);
			ex.printStackTrace();
		} catch (Exception e) {
			success = false;
			message.add(new ResponseMessage(ResponseMessage.MSG_TYPE_LOUD, e.getMessage()));
			e.printStackTrace();
			logger.error("Excepetion in function: " + this.getClass().getCanonicalName() + ".saveOrUpdate", e);
			e.printStackTrace();
		}finally {
			result = new JsonResponse<List<VigilePatenti> >(success, message, vigilePatenti);
		}
		return result;
	}
	/**
	 * 
	 * @param idVigile
	 * @return
	 */
	public JsonResponse<List<ModelPortletVigilePatenti>> listForPortlet(Integer idVigile) {
		JsonResponse<List<ModelPortletVigilePatenti>> result = null;
		List<ModelPortletVigilePatenti> data = null;
		Boolean success = true;
		String message = "";
		try {
			data = vigilePatentiManager.listForPortlet(idVigile);
			message = Messages.getMessage("search.ok");
		} catch (Exception e) {
			success = false;
			message = Messages.getMessage("search.ko");
			logger.error("Excepetion in function: " + this.getClass().getCanonicalName() + ".listForPortlet", e);

		} finally {
			result = new JsonResponse<List<ModelPortletVigilePatenti>>(success, message, data);
		}
		return result;
	}
}
