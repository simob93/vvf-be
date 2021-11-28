package it.vvfriva.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.vvfriva.entity.Articoli;
import it.vvfriva.managers.ArticoliManager;
import it.vvfriva.models.JsonResponse;
import it.vvfriva.utils.Messages;
import it.vvfriva.utils.Utils;

/**
 * 
 * @author simone
 *
 */
@Service
public class ArticoliService extends DbServiceStandard<Articoli> {

	final private static Logger  logger = LoggerFactory.getLogger(ArticoliService.class);
	
	@Autowired 
	public ArticoliManager manager;
	
	public ArticoliManager getManager() {
		return manager;
	}
	/**
	 * 
	 * @param idCategoria 
	 * @param idDeposito 
	 * @param descrizione 
	 * @return
	 */
	public JsonResponse<List<Articoli>> list(String descrizione, Integer idDeposito, Integer idCategoria) {
		List<Articoli> data = null;
		String message = null;
		Boolean success = true;
		JsonResponse<List<Articoli>> result = null;
		try {
			data = this.getManager().list(descrizione, idDeposito, idCategoria);
			message = Messages.getMessage("search.ok");
		} catch (Exception e) {
			success = false;
			message = Messages.getMessage("search.ko");
			e.printStackTrace();
			logger.error(Utils.errorInMethod(e.getMessage()));
		} finally {
			result = new JsonResponse<List<Articoli>>(success, message, data);
		}
		return result;
	}
}
