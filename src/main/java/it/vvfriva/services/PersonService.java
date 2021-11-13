package it.vvfriva.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.vvfriva.entity.Person;
import it.vvfriva.managers.DbManagerStandard;
import it.vvfriva.managers.PersonManager;
import it.vvfriva.models.JsonResponse;
import it.vvfriva.utils.Messages;
import it.vvfriva.utils.Utils;
/**
 * 
 * @author simone
 *
 */
@Service
public class PersonService extends DbServiceStandard<Person> {

	
	@Autowired PersonManager personManager;
	
	final Logger logger = LoggerFactory.getLogger(this.getClass());

	
	public JsonResponse<List<Person>> listByArea (String area) {
		Boolean success = true;
		String message = "";
		List<Person> data = null;
		JsonResponse<List<Person>>  result = null;
		try {
			data = personManager.list(Utils.convertStringToIntegerList(area), null, null, true);
			message = Messages.getMessage("search.ok");
		} catch (Exception e) {
			message = Messages.getMessage("search.ko");
			e.printStackTrace();
			logger.error("Exception in function: "+ this.getClass().getCanonicalName() +".listByArea", e);
		} finally {
			result = new JsonResponse<List<Person>>(success, message, data);
		}
		return result;
	}
	/**
	 * 
	 * @param idArea
	 * @return
	 */
	public JsonResponse<List<Person>> getBy (Integer idArea) {
		Boolean success = true;
		String message = "";
		List<Person> data = null;
		JsonResponse<List<Person>>  result = null;
		try {
			data = personManager.getBy(idArea);
			message = Messages.getMessage("search.ok");
		} catch (Exception e) {
			message = Messages.getMessage("search.ko");
			e.printStackTrace();
			logger.error("Exception in function: "+ this.getClass().getCanonicalName() +".listByArea", e);
		} finally {
			result = new JsonResponse<List<Person>>(success, message, data);
		}
		return result;
	}

	@Override
	public DbManagerStandard<Person> getManager() {
		return personManager;
	}
	
}
