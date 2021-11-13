package it.vvfriva.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.vvfriva.managers.LettereTurniManager;
import it.vvfriva.models.JsonResponse;
import it.vvfriva.utils.Messages;

@Service
public class LettereTurniService {
	
	@Autowired LettereTurniManager lettereTurniManager;
	
	final Logger logger = LoggerFactory.getLogger(this.getClass());


	public JsonResponse<List<String>> list(Integer idSquadra) {
		Boolean success = true;
		String message = "";
		List<String> data = null;
		JsonResponse<List<String>>  result = null;
		try {
			data = lettereTurniManager.list(idSquadra);
			message = Messages.getMessage("search.ok");
		} catch (Exception e) {
			message = Messages.getMessage("search.ko");
			e.printStackTrace();
			logger.error("Exception in function: "+ this.getClass().getCanonicalName() +".list", e);
		} finally {
			result = new JsonResponse<List<String>>(success, message, data);
		}
		return result;
	}
}
