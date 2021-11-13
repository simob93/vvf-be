package it.vvfriva.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import it.vvfriva.models.JsonResponse;
import it.vvfriva.models.ModelNotification;
import it.vvfriva.services.NotificationService;

@Controller    
@RequestMapping(path="/notify")
public class NotificationController {
	@Autowired NotificationService notificationService;
	/**
	 * return the list of notification
	 * @return
	 */
	@GetMapping(path="/list")
	private @ResponseBody JsonResponse<List<ModelNotification>> list() {
		return notificationService.list();
	}
	

}
