package it.vvfriva.services;

import java.util.List;

import org.jfree.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.vvfriva.entity.Vigile;
import it.vvfriva.managers.DbManagerStandard;
import it.vvfriva.managers.VigileManager;
import it.vvfriva.models.JsonResponse;
import it.vvfriva.models.JsonResponseTotal;
import it.vvfriva.models.ModelInfoVigili;
import it.vvfriva.models.VigileModel;
import it.vvfriva.repository.VigileRepository;
import it.vvfriva.utils.Messages;


@Service
public class VigileService extends DbServiceStandard<Vigile> {
	
	
	@Autowired VigileManager vigileManager;
	@Autowired VigileRepository rep;

	final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 
	 * @return
	 */
	public JsonResponse<List<VigileModel>> list(Boolean assenti, Boolean soloInServizio, Integer idSquadra) {
		JsonResponse<List<VigileModel>> result = null;
		String message = null;
		Boolean success = true;
		List<VigileModel> data = null;
		try {
			data = vigileManager.list(assenti, idSquadra, soloInServizio);
			message = Messages.getMessage("search.ok");
		} catch (Exception e) {
			message = Messages.getMessage("search.ko");
			success = false;
			e.printStackTrace();
			logger.error("Exeption in function: " + this.getClass().getCanonicalName() + ".list", e);
		} finally {
			result = new JsonResponse<List<VigileModel>>(success, message, data);
		}
		return result;
	}
	/**
	 * 
	 * @param start
	 * @param limit
	 * @return
	 */
	public JsonResponseTotal<List<Vigile>> listPaged(Integer start, Integer limit, String search) {
		
		JsonResponseTotal<List<Vigile>> result = null;
		String message = null;
		Boolean success = true;
		List<Vigile> data = null;
		Integer total = null;
		try {
			
			total = vigileManager.listPaged(0, 5000, search).size();
			data = vigileManager.listPaged(start, limit, search);
			
			message = Messages.getMessage("search.ok");
		} catch (Exception e) {
			message = Messages.getMessage("search.ko");
			success = false;
			e.printStackTrace();
			logger.error("Exeption in function: " + this.getClass().getCanonicalName() + ".listPaged", e);
		} finally {
			result = new JsonResponseTotal<List<Vigile>>(success, message, data, total);
		}
		return result;
	}
	/**
	 * 
	 * @param id
	 * @return
	 */
	public JsonResponse<Vigile> getById(Integer id) {
		Boolean success = true;
		String message = null;
		Vigile data = null;
		JsonResponse<Vigile> result = null;
		try {
			data = vigileManager.getById(id);
			message = Messages.getMessage("search.ok");
		} catch (Exception e) {
			success = false;
			message = Messages.getMessage("search.ko");
			e.printStackTrace();
			logger.error("Excpetion in function: " + this.getClass().getCanonicalName() + ".getById", e);
		} finally {
			result = new JsonResponse<Vigile>(success, message, data);
		}
		return result;
	}
	/**
	 * 
	 * @return
	 */
	public JsonResponse<ModelInfoVigili> getInfoVigili() {
		Boolean success = true;
		String message = null;
		ModelInfoVigili data = null;
		JsonResponse<ModelInfoVigili> result = null;
		try {
			data = vigileManager.getInfoVigili();
			message = Messages.getMessage("search.ok");
		} catch (Exception e) {
			success = false;
			message = Messages.getMessage("search.ko");
			e.printStackTrace();
			logger.error("Excpetion in function: " + this.getClass().getCanonicalName() + ".getById", e);
		} finally {
			result = new JsonResponse<ModelInfoVigili>(success, message, data);
		}
		return result;
	}

	@Override
	public DbManagerStandard<Vigile> getManager() {
		return vigileManager;
	}
	/**
	 * 
	 * @param idVigile
	 * @param base64
	 * @return
	 */
	public JsonResponse<Vigile> uploadFoto(Integer idVigile, String base64) {
		Boolean success = true;
		String message = null;
		JsonResponse<Vigile> resp = null;
		Vigile data = null;
		try {
			data = this.getManager().getObjById(idVigile);
			data.setFoto(base64);
			this.getManager().update(data);
			message = Messages.getMessage("operation.ok");
		} catch (Exception e) {
			success = false;
			message = Messages.getMessage("operation.ko");
			Log.error("Exception in Method: " + this.getClass().getCanonicalName() + ".uploadFoto", e);
			e.printStackTrace();
		} finally {
			resp = new JsonResponse<Vigile>(success, message, data);
		}
		return resp;
	}


}
