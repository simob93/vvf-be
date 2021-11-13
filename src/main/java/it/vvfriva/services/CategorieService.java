package it.vvfriva.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.vvfriva.entity.Categorie;
import it.vvfriva.managers.CategorieManager;
import it.vvfriva.models.JsonResponse;
import it.vvfriva.utils.Messages;
import it.vvfriva.utils.Utils;

/**
 * 
 * @author simone
 *
 */
@Service
public class CategorieService extends DbServiceStandard<Categorie> {

	final private static Logger  logger = LoggerFactory.getLogger(CategorieService.class);
	
	@Autowired 
	public CategorieManager manager;
	
	public CategorieManager getManager() {
		return manager;
	}
	/**
	 * 
	 * @return
	 */
	public JsonResponse<List<Categorie>> list() {
		List<Categorie> data = null;
		String message = null;
		Boolean success = true;
		JsonResponse<List<Categorie>> result = null;
		try {
			data = this.getManager().list();
			message = Messages.getMessage("search.ok");
		} catch (Exception e) {
			success = false;
			message = Messages.getMessage("search.ko");
			e.printStackTrace();
			logger.error(Utils.errorInMethod(e.getMessage()));
		} finally {
			result = new JsonResponse<List<Categorie>>(success, message, data);
		}
		return result;
	}
}
