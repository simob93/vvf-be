package it.vvfriva.services;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.vvfriva.entity.Protocol;
import it.vvfriva.managers.DbManagerStandard;
import it.vvfriva.managers.ProtocolManager;
import it.vvfriva.models.JsonResponse;
import it.vvfriva.models.JsonResponseTotal;
import it.vvfriva.utils.Messages;
/**
 * 
 * @author simone
 *
 */
@Service
public class ProtocolService extends DbServiceStandard<Protocol> {
	
	final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	ProtocolManager protocolManager;
	
	@Override
	public DbManagerStandard<Protocol> getManager() {
		return protocolManager;
	}

	public JsonResponse<Protocol> getById(Integer id) {
		JsonResponse<Protocol>  result = null;
		boolean success = true;
		Protocol data = null;
		String message = "";
		try {
			data = getManager().getObjById(id);
			message = Messages.getMessage("search.ok");
		} catch (Exception ex) {
			success = false;
			ex.printStackTrace();
			message = Messages.getMessage("search.ko");
			log.error("Exception in method " + this.getClass().getCanonicalName() + ".getById", ex);
		} finally {
			result = new JsonResponse<Protocol>(success, message, data);
		}
		return result;
	}

	public JsonResponseTotal<List<Protocol>> listPaged(Integer firstResult, Integer maxResult, String oggetto, Integer idFaldone, String tipologia, Date from, Date to, String numeroProtocollo) {
		JsonResponseTotal<List<Protocol>>  result = null;
		boolean success = true;
		List<Protocol> data = null;
		String message = "";
		Long total = null;
		try {
			//total = protocolManager.count();
			data = protocolManager.list(firstResult, maxResult, oggetto, idFaldone, tipologia, from, to, numeroProtocollo);
			message = Messages.getMessage("search.ok");
		} catch (Exception ex) {
			success = false;
			ex.printStackTrace();
			message = Messages.getMessage("search.ko");
			log.error("Exception in method " + this.getClass().getCanonicalName() + ".getById", ex);
		} finally {
			result = new JsonResponseTotal<List<Protocol>>(success, message, data, total != null ? total.intValue() : 0);
		}
		return result;
	}

}
