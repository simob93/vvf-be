package it.vvfriva.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.vvfriva.managers.NotificationManager;
import it.vvfriva.models.JsonResponse;
import it.vvfriva.models.ModelNotification;
import it.vvfriva.utils.Messages;

/**
 * 
 * @author simone
 *
 */
@Service
public class NotificationService {
	
	final Logger log = LoggerFactory.getLogger(NotificationService.class);
	
	@Autowired NotificationManager notificationManager;

	public JsonResponse<List<ModelNotification>> list() {
		boolean success = true;
		String message = "";
		List<ModelNotification> data = null;
		JsonResponse<List<ModelNotification>> response = null;
		try {
			data = notificationManager.list();
			message = Messages.getMessage("search.ok");
		} catch (Exception ex) {
			success = false;
			log.error("Exception in function:" + new Object(){}.getClass().getEnclosingMethod().getName(),  ex);
			message = Messages.getMessage("search.ko");
		} finally {
			response = new JsonResponse<List<ModelNotification>>(success, message, data);
		}
		return response;
	}

}
