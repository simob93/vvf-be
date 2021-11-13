package it.vvfriva.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.vvfriva.entity.Gradi;
import it.vvfriva.managers.GradiManager;
import it.vvfriva.models.JsonResponse;
import it.vvfriva.utils.Messages;

/**
 * 
 * @author simone
 *
 */
@Service
public class GradiService extends DbServiceStandard<Gradi> {

	final private static Logger  logger = LoggerFactory.getLogger(GradiService.class);
	
	@Autowired 
	public GradiManager gradiManager;
	
	public GradiManager getManager() {
		return gradiManager;
	}

	public JsonResponse<List<Gradi>> listBy(Integer idServizio) {
		Boolean success = true;
		String message = "";
		List<Gradi> data = null;
		 JsonResponse<List<Gradi>>  result = null;
		try {
			data = getManager().listBy(null, null, null, idServizio);
			message = Messages.getMessage("search.ok");
		} catch (Exception e) {
			logger.error("Exception in method: " + this.getClass().getCanonicalName() + ".listBy", e);
			success = false;
			message = Messages.getMessage("search.ko");
			e.printStackTrace();
		} finally {
			result = new JsonResponse<List<Gradi>>(success, message, data);
		}
		return result;
	}

}
