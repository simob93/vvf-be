package it.vvfriva.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.vvfriva.entity.Scadenze;
import it.vvfriva.managers.DbManagerStandard;
import it.vvfriva.managers.ScadenzeManager;
import it.vvfriva.managers.VigileManager;
import it.vvfriva.models.JsonResponse;
import it.vvfriva.models.KeyValueColumn;
import it.vvfriva.models.KeyValueTipiScadenza;
import it.vvfriva.models.VigileModel;
import it.vvfriva.utils.Messages;

@Service
public class ScadenzeService extends DbServiceStandard<Scadenze> {
	
	final Logger log = LoggerFactory.getLogger(ScadenzeService.class);
	

	@Autowired ScadenzeManager scadenzeManager;
	@Autowired VigileManager vigileManager;
	/**
	 * 
	 * @param idVigile
	 * @param idArea
	 * @return
	 */
	public JsonResponse<List<KeyValueTipiScadenza>> getCboxTipiScadenza(Integer idVigile, Integer idArea) {
		Boolean success = true;
		String message = null;
		List<KeyValueTipiScadenza> data = null;
		JsonResponse<List<KeyValueTipiScadenza>> result = null;
		try {
			data = scadenzeManager.getCboxTipiScadenza(idVigile, idArea);
			message = Messages.getMessage("search.ok");
		} catch (Exception e) {
			success = false;
			message = Messages.getMessage("search.ko");
			e.printStackTrace();
			logger.error("Excpetion in function: " + this.getClass().getCanonicalName() + ".getCboxPerson", e);
		} finally {
			result = new JsonResponse<List<KeyValueTipiScadenza>>(success, message, data);
		}
		return result;
	}
	/**
	 * 
	 * @param idVigile
	 * @param from
	 * @param to
	 * @param idArea
	 * @param storico
	 * @return
	 */
	public JsonResponse<List<Scadenze>> listAll(Integer idVigile, Date from, Date to, List<Integer> idArea, Boolean storico) {
		Boolean success = true;
		String message = null;
		List<Scadenze> data = null;
		JsonResponse<List<Scadenze>> result = null;
		try {
			data = scadenzeManager.listAll(idVigile, from, to, idArea, storico);
			message = Messages.getMessage("search.ok");
		} catch (Exception e) {
			success = false;
			message = Messages.getMessage("search.ko");
			e.printStackTrace();
			e.printStackTrace();
		}finally {
			result = new JsonResponse<List<Scadenze>>(success, message, data);
		}
		
		return result;
	}
	/**
	 * 
	 * @param from
	 * @param to
	 * @param asList
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	public JsonResponse<List<Scadenze>> listForReport(Date from, Date to, List<Integer> asList) throws Exception {
		List<Scadenze> data = null;
		JsonResponse<List<Scadenze>> result = null;
		Boolean success = true;
		String message = null;
		try {
			List<VigileModel> listaVigili = vigileManager.list(false, null, true);
			List<Integer> idVigile = new ArrayList<Integer>();
			for (VigileModel vigile: listaVigili) {
				idVigile.add(vigile.getId());
			}
			data = scadenzeManager.listForReport(from, to, asList, idVigile);
		} catch (Exception e) {
			success = false;
			message = Messages.getMessage("search.ko");
			e.printStackTrace();
			log.error("Exception in function" + this.getClass().getCanonicalName() + "listForReport", e);
		} finally {
			result = new JsonResponse<List<Scadenze>>(success, message, data);
		}
		return result;
	}
	

	@Override
	public DbManagerStandard<Scadenze> getManager() {
		return scadenzeManager;
	}

	public JsonResponse<String> cal() {
		Boolean success = true;
		String message = null;
		String data = null;
		JsonResponse<String> result = null;
		try {
			data = scadenzeManager.cal(); //calcolo delle scadenze
			message = Messages.getMessage("search.ok");
		} catch (Exception e) {
			success = false;
			message = Messages.getMessage("search.ko");
			e.printStackTrace();
		}finally {
			result = new JsonResponse<String>(success, message, data);
		}
		return result;
	}

	public JsonResponse<List<KeyValueColumn>> getColumnsTable(String cmp) {
		Boolean success = true;
		String message = null;
		List<KeyValueColumn> data = null;
		JsonResponse<List<KeyValueColumn>> result = null;
		try {
			data = scadenzeManager.getColumnsTabel(cmp);
			message = Messages.getMessage("search.ok");
		} catch (Exception e) {
			success = false;
			message = Messages.getMessage("search.ko");
			e.printStackTrace();
		}finally {
			result = new JsonResponse<List<KeyValueColumn>>(success, message, data);
		}
		return result;
	}
	
}
