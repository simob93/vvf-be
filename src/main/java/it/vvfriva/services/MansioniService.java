package it.vvfriva.services;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.vvfriva.entity.Mansioni;
import it.vvfriva.managers.MansioniManager;
import it.vvfriva.models.JsonResponse;
import it.vvfriva.utils.Messages;

/**
 * 
 * @author simone
 *
 */
@Service
public class MansioniService extends DbServiceStandard<Mansioni> {

	final private static Logger  logger = LoggerFactory.getLogger(MansioniService.class);
	
	@Autowired 
	public MansioniManager carrieraManager;
	
	public MansioniManager getManager() {
		return carrieraManager;
	}

	public JsonResponse<List<Mansioni>> listBy(Integer idVigile, Date dal, Date al) {
		Boolean success = true;
		String message = "";
		List<Mansioni> data = null;
		JsonResponse<List<Mansioni>>  result = null;
		try {
			data = getManager().listBy(idVigile, dal, al, null);
			message = Messages.getMessage("search.ok");
		} catch (Exception e) {
			logger.error("Exception in method: " + this.getClass().getCanonicalName() + ".listBy", e);
			success = false;
			message = Messages.getMessage("search.ko");
			e.printStackTrace();
		} finally {
			result = new JsonResponse<List<Mansioni>>(success, message, data);
		}
		return result;
	}

}
