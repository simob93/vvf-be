package it.vvfriva.services;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import it.vvfriva.entity.Ruoli;
import it.vvfriva.managers.DbManagerStandard;
import it.vvfriva.managers.RuoliManager;
import it.vvfriva.models.JsonResponse;
import it.vvfriva.models.KeyValue;
import it.vvfriva.utils.Messages;
import it.vvfriva.utils.Utils;

@Service
@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class RuoliService extends DbServiceStandard<Ruoli> {
	final Logger logger = LoggerFactory.getLogger(RuoliService.class);
	
	
	
	@Autowired RuoliManager ruoliManager; 
	/**
	 * 
	 * @return
	 */
	public JsonResponse<List<Ruoli>> list() {
		boolean success = true;
		String message = null;
		JsonResponse<List<Ruoli>> result = null;
		List<Ruoli> data = null;
		try {
			data = this.ruoliManager.list();
			message = Messages.getMessage("search.ok");
		} catch (Exception e) {
			success = false;
			message = Messages.getMessage("search.ko");
			logger.error(Utils.errorInMethod(e.getMessage()));
			e.printStackTrace();
		} finally {
			result = new JsonResponse<List<Ruoli>>(success, message, data);
		}
		return result;
	}
	/**
	 * 
	 * @return
	 */
	public JsonResponse<List<KeyValue>> cbox() {
		boolean success = true;
		String message = null;
		JsonResponse<List<KeyValue>> result = null;
		List<KeyValue> data = null;
		try {
			List<Ruoli> listRuoli = this.ruoliManager.list();
			if (!Utils.isEmptyList(listRuoli)) {
				data = listRuoli.stream()
						.map(ruolo -> new KeyValue(ruolo.getId(), ruolo.getDescrizione()))
						.collect(Collectors.toList());
			}
		} catch (Exception e) {
			success = false;
			message = Messages.getMessage("search.ko");
			logger.error(Utils.errorInMethod(e.getMessage()));
			e.printStackTrace();
		} finally {
			result = new JsonResponse<List<KeyValue>>(success, message, data);
		}
		return result;
	}
	/**
	 * 
	 * @param idRuolo
	 * @return
	 */
	public JsonResponse<Ruoli> getById(Integer idRuolo) {
		boolean success = true;
		String message = null;
		JsonResponse<Ruoli> result = null;
		Ruoli data = null;
		try {
			data = this.ruoliManager.getObjById(idRuolo);
		} catch (Exception e) {
			success = false;
			message = Messages.getMessage("search.ko");
			logger.error(Utils.errorInMethod(e.getMessage()));
			e.printStackTrace();
		} finally {
			result = new JsonResponse<Ruoli>(success, message, data);
		}
		return result;
	}
	
	@Override
	public DbManagerStandard<Ruoli> getManager() {
		return ruoliManager;
	}

}
