package it.vvfriva.services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.vvfriva.entity.ArticoliDepositi;
import it.vvfriva.enums.DbOperation;
import it.vvfriva.managers.ArticoliDepositiManager;
import it.vvfriva.models.JsonResponse;
import it.vvfriva.utils.Messages;
import it.vvfriva.utils.ResponseMessage;
import it.vvfriva.utils.Utils;

/**
 * 
 * @author simone
 *
 */
@Service
public class ArticoliDepositiService extends DbServiceStandard<ArticoliDepositi> {

	final private static Logger  logger = LoggerFactory.getLogger(ArticoliDepositiService.class);
	
	@Autowired EntityManager em;
	
	@Autowired 
	public ArticoliDepositiManager manager;
	
	public ArticoliDepositiManager getManager() {
		return manager;
	}
	
	/**
	 * 
	 * @return
	 */
	public JsonResponse<List<ArticoliDepositi>> list(Integer idArticolo) {
		List<ArticoliDepositi> data = null;
		String message = null;
		Boolean success = true;
		JsonResponse<List<ArticoliDepositi>> result = null;
		try {
			data = this.getManager().list(idArticolo);
			message = Messages.getMessage("search.ok");
		} catch (Exception e) {
			success = false;
			message = Messages.getMessage("search.ko");
			e.printStackTrace();
			logger.error(Utils.errorInMethod(e.getMessage()));
		} finally {
			result = new JsonResponse<List<ArticoliDepositi>>(success, message, data);
		}
		return result;
	}
	/**
	 * 
	 * @param listArtCat
	 * @return
	 */
	@Transactional
	public JsonResponse<Boolean> saveAll(List<ArticoliDepositi> listArtDepositi) {
		String message = null;
		Boolean success = true;
		List<ResponseMessage> msg = new ArrayList<ResponseMessage>();
		JsonResponse<Boolean> result = null;
		if (Utils.isEmptyList(listArtDepositi)) {
			return new JsonResponse<Boolean>(false, Messages.getMessageFormatted("field.list.empty",
					new String[] { Messages.getMessage("field.categorie") }), null);
		}
		try {
			for (ArticoliDepositi cat: listArtDepositi) {
				if ((cat.getEliminare() != null) && (cat.getEliminare().booleanValue())) {
					this.manager.dbManager(DbOperation.DELETE, cat.getId());
				}
				else if (!Utils.isValidId(cat.getId())) {
					this.manager.dbManager(DbOperation.INSERT, cat);
				} else {
					this.manager.dbManager(DbOperation.UPDATE, cat);
				}
			}
			message = Messages.getMessage("operation.ok");
		} catch (Exception e) {
			success = false;
			message = Messages.getMessage("operation.ko");
			e.printStackTrace();
			msg.add(new ResponseMessage(ResponseMessage.MSG_TYPE_LOUD, message));
			logger.error(Utils.errorInMethod(e.getMessage()));
		} finally {
			if (success) {
				msg.add(new ResponseMessage(ResponseMessage.MSG_TYPE_LOUD, message));
			}
			result = new JsonResponse<Boolean>(success, msg, null);
		}
		return result;
	}
}
