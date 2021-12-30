package it.vvfriva.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import it.vvfriva.managers.DbManagerStandard;
import it.vvfriva.models.JsonResponse;
import it.vvfriva.utils.CustomException;
import it.vvfriva.utils.Messages;
import it.vvfriva.utils.ResponseMessage;

/**
 * 
 * @author simone
 *
 * @param <T>
 */
@Service
public abstract class DbServiceStandard<T> {
	
	final Logger logger = LoggerFactory.getLogger(this.getClass());
	

	public DbManagerStandard<T> getManager() {
		return null;
	};
	/**
	 * 
	 * @param id
	 * @return
	 */
	public JsonResponse<T> getObjectById(Integer id) {
		Boolean success = true;
		T data = null;
		List<ResponseMessage> message = new ArrayList<>();
		JsonResponse<T> result = null;
		try {
			data = this.getManager().getObjById(id);
		} catch (Exception e) {
			success = false;
			e.printStackTrace();
			logger.error("Excpetion in function: " + this.getClass().getCanonicalName() + ".saveOrUpdate", e);
			e.printStackTrace();
		} finally {
			result = new JsonResponse<T>(success, message, data);
		}
		return result;
	}
	
	/** 
	 * 
	 * @param object
	 * @param action
	 * @return
	 */
	public JsonResponse<T> save(T object) {
		Boolean success = true;
		List<ResponseMessage> message = new ArrayList<>();
		JsonResponse<T> result = null;
		try {
			this.getManager().save(object);
			message.add(new ResponseMessage(ResponseMessage.ERRORE, Messages.getMessage("operation.ok")));
		}catch (CustomException ex) {
			success = false;
			object = null;
			message = ex.getErrorList();
			ex.printStackTrace();
			logger.error("Excpetion in function: " + this.getClass().getCanonicalName() + ".saveOrUpdate", ex);
			ex.printStackTrace();
		} catch (Exception e) {
			success = false;
			object = null;
			message.add(new ResponseMessage(ResponseMessage.ERRORE, e.getMessage()));
			e.printStackTrace();
			logger.error("Excpetion in function: " + this.getClass().getCanonicalName() + ".saveOrUpdate", e);
			e.printStackTrace();
		}finally {
			result = new JsonResponse<T>(success, message, object);
		}
		return result;
	}
	
	public JsonResponse<T> update(T object) {
		Boolean success = true;
		List<ResponseMessage> message = new ArrayList<>();
		JsonResponse<T> result = null;
		try {
			this.getManager().update(object);
			message.add(new ResponseMessage(ResponseMessage.NESSUNO,Messages.getMessage("operation.ok")));

		}catch (CustomException ex) {
			success = false;
			object = null;
			message = ex.getErrorList();
			ex.printStackTrace();
			logger.error("Excpetion in function: " + this.getClass().getCanonicalName() + ".saveOrUpdate", ex);
			ex.printStackTrace();
		} catch (Exception e) {
			success = false;
			object = null;
			message.add(new ResponseMessage(ResponseMessage.ERRORE, e.getMessage()));
			e.printStackTrace();
			logger.error("Excpetion in function: " + this.getClass().getCanonicalName() + ".saveOrUpdate", e);
			e.printStackTrace();
		}finally {
			result = new JsonResponse<T>(success, message, object);
		}
		return result;
	}
	/**
	 * 
	 * @param id
	 * @param action
	 * @return
	 */
	public JsonResponse<T> delete(Integer id) {
		
		Boolean success = true;
		List<ResponseMessage> message = new ArrayList<>();
		JsonResponse<T> result = null;
		try {
			this.getManager().delete(this.getManager().getObjById(id));
			message.add(new ResponseMessage(ResponseMessage.NESSUNO, Messages.getMessage("operation.ok")));
		}catch (CustomException ex) {
			success = false;
			message = ex.getErrorList();
			ex.printStackTrace();
			logger.error("Excpetion in function: " + this.getClass().getCanonicalName() + ".saveOrUpdate", ex);
			ex.printStackTrace();
		} catch (Exception e) {
			success = false;
			message.add(new ResponseMessage(ResponseMessage.ERRORE, e.getMessage()));
			e.printStackTrace();
			logger.error("Excpetion in function: " + this.getClass().getCanonicalName() + ".saveOrUpdate", e);
			e.printStackTrace();
		}finally {
			result = new JsonResponse<T>(success, message, null);
		}
		return result;
		
	}
	
	
}
