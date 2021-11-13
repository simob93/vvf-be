package it.vvfriva.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.vvfriva.entity.Deposito;
import it.vvfriva.managers.DepositoManager;
import it.vvfriva.models.JsonResponse;
import it.vvfriva.utils.Messages;
import it.vvfriva.utils.Utils;

/**
 * 
 * @author simone
 *
 */
@Service
public class DepositoService extends DbServiceStandard<Deposito> {

	final private static Logger  logger = LoggerFactory.getLogger(DepositoService.class);
	
	@Autowired 
	public DepositoManager depositoManager;
	
	public DepositoManager getManager() {
		return depositoManager;
	}
	/**
	 * 
	 * @param attivi
	 * @return
	 */
	public JsonResponse<List<Deposito>> list(Boolean attivi) {
		List<Deposito> data = null;
		String message = null;
		Boolean success = true;
		JsonResponse<List<Deposito>> result = null;
		try {
			data = this.getManager().list(attivi);
			message = Messages.getMessage("search.ok");
		} catch (Exception e) {
			success = false;
			message = Messages.getMessage("search.ko");
			e.printStackTrace();
			logger.error(Utils.errorInMethod(e.getMessage()));
		} finally {
			result = new JsonResponse<List<Deposito>>(success, message, data);
		}
		return result;
	}
}
