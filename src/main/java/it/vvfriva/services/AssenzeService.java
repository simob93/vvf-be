package it.vvfriva.services;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.vvfriva.entity.Assenze;
import it.vvfriva.managers.AssenzeManager;
import it.vvfriva.models.JsonResponse;
import it.vvfriva.utils.Messages;

/**
 * 
 * @author simone
 *
 */
@Service
public class AssenzeService extends DbServiceStandard<Assenze> {

	final private static Logger  logger = LoggerFactory.getLogger(AssenzeService.class);
	
	@Autowired 
	public AssenzeManager assenzeManager;
	
	public AssenzeManager getManager() {
		return assenzeManager;
	}

	public JsonResponse<List<Assenze>> listBy(Integer idVigile) {
		Boolean success = true;
		String message = "";
		List<Assenze> data = null;
		 JsonResponse<List<Assenze>>  result = null;
		try {
			data = getManager().listBy(idVigile);
			message = Messages.getMessage("search.ok");
		} catch (Exception e) {
			logger.error("Exception in method: " + this.getClass().getCanonicalName() + ".listBy", e);
			success = false;
			message = Messages.getMessage("search.ko");
			e.printStackTrace();
		} finally {
			result = new JsonResponse<List<Assenze>>(success, message, data);
		}
		return result;
	}
	/**
	 * 
	 * @param idVigile
	 * @param data
	 * @return
	 */
	public JsonResponse<Assenze> getLast(Integer idVigile, Date data) {
		Boolean success = true;
		String message = "";
		Assenze assenza = null;
		 JsonResponse<Assenze>  result = null;
		try {
			assenza = getManager().getLast(idVigile, data);
			message = Messages.getMessage("search.ok");
		} catch (Exception e) {
			logger.error("Exception in method: " + this.getClass().getCanonicalName() + ".listBy", e);
			success = false;
			message = Messages.getMessage("search.ko");
			e.printStackTrace();
		} finally {
			result = new JsonResponse<Assenze>(success, message, assenza);
		}
		return result;
	}

}
