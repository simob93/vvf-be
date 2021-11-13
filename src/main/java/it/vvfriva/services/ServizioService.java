package it.vvfriva.services;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.vvfriva.entity.Servizio;
import it.vvfriva.managers.DbManagerStandard;
import it.vvfriva.managers.ServizioManager;
import it.vvfriva.models.JsonResponse;
import it.vvfriva.utils.Messages;

@Service
public class ServizioService extends DbServiceStandard<Servizio> {
	
	final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired ServizioManager servizioManager;

	public JsonResponse<List<Servizio>> list(Integer idVigile, Date dal, Date al) {
		Boolean success = true;
		String message = "";
		List<Servizio> data = null;
		JsonResponse<List<Servizio>>  result = null;
		try {
			data = servizioManager.list(idVigile, dal, al, null, null, null);
			message = Messages.getMessage("search.ok");
		} catch (Exception e) {
			message = Messages.getMessage("search.ko");
			e.printStackTrace();
			logger.error("Exception in function: "+ this.getClass().getCanonicalName() +".list", e);
		} finally {
			result = new JsonResponse<List<Servizio>>(success, message, data);
		}
		return result;
	}


	@Override
	public DbManagerStandard<Servizio> getManager() {
		return servizioManager;
	}

}
