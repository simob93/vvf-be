package it.vvfriva.services;

import java.util.List;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.vvfriva.entity.ArticoliCategorie;
import it.vvfriva.managers.ArticoliCategorieManager;
import it.vvfriva.models.JsonResponse;
import it.vvfriva.models.VvfJsonResponse;
import it.vvfriva.utils.Messages;
import it.vvfriva.utils.Utils;

/**
 * 
 * @author simone
 *
 */
@Service
public class ArticoliCategorieService extends DbServiceStandard<ArticoliCategorie> {

	final private static Logger  logger = LoggerFactory.getLogger(ArticoliCategorieService.class);
	
	@Autowired EntityManager em;
	
	@Autowired 
	public ArticoliCategorieManager manager;
	
	public ArticoliCategorieManager getManager() {
		return manager;
	}
	
	
	
	/**
	 * 
	 * @return
	 */
	public JsonResponse<List<ArticoliCategorie>> list(Integer idArticolo) {
		List<ArticoliCategorie> data = null;
		String message = null;
		Boolean success = true;
		JsonResponse<List<ArticoliCategorie>> result = null;
		try {
			data = this.getManager().list(idArticolo);
			message = Messages.getMessage("search.ok");
		} catch (Exception e) {
			success = false;
			message = Messages.getMessage("search.ko");
			e.printStackTrace();
			logger.error(Utils.errorInMethod(e.getMessage()));
		} finally {
			result = new JsonResponse<List<ArticoliCategorie>>(success, message, data);
		}
		return result;
	}
	/**
	 * 
	 * @param listArtCat
	 * @return
	 */
	@Transactional
	public VvfJsonResponse<Boolean> saveAll(List<ArticoliCategorie> listArtCat) {
		for (ArticoliCategorie cat: listArtCat) {
			if ((cat.getEliminare() != null) && (cat.getEliminare().booleanValue())) {
				this.manager.delete(cat);
			}
			else if (!Utils.isValidId(cat.getId())) {
				this.manager.save(cat);
			} else {
				this.manager.update(cat);
			}
		}
		return new VvfJsonResponse<Boolean>(true, null, null);
	}
}
