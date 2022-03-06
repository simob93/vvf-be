package it.vvfriva.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import it.vvfriva.managers.DbManagerStandard;
import it.vvfriva.models.JsonResponse;

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
		T data = this.getManager().getObjById(id);
		return new JsonResponse<T>(true,  data);
	}
	
	/** 
	 * 
	 * @param object
	 * @param action
	 * @return
	 */
	public JsonResponse<T> save(T object) {
		this.getManager().save(object);
		return new JsonResponse<T>(true, object);
	}
	/**
	 * 
	 * @param object
	 * @return
	 */
	public JsonResponse<T> update(T object) {
		this.getManager().update(object);
		return new JsonResponse<T>(true, null);
	}
	/**
	 * 
	 * @param id
	 * @param action
	 * @return
	 */
	public JsonResponse<T> delete(Integer id) {
		this.getManager().delete(this.getManager().getObjById(id));
		return new JsonResponse<T>(true, null);
	}
}
