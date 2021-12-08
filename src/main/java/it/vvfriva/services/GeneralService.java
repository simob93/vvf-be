package it.vvfriva.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.vvfriva.entity.Area;
import it.vvfriva.entity.Comuni;
import it.vvfriva.entity.Provincie;
import it.vvfriva.entity.SettingScadenze;
import it.vvfriva.enums.EnumsTaglie;
import it.vvfriva.managers.AreaManager;
import it.vvfriva.managers.ComuneManager;
import it.vvfriva.managers.PersonScadenzeManager;
import it.vvfriva.managers.ProvinciaManager;
import it.vvfriva.models.JsonResponse;
import it.vvfriva.models.KeyValue;
import it.vvfriva.utils.Configs;
import it.vvfriva.utils.Messages;
import it.vvfriva.utils.Utils;

@Service
public class GeneralService {

	final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired ComuneManager townManager;
	@Autowired ProvinciaManager districtManager;
	@Autowired PersonScadenzeManager personScadenzeManager;
	@Autowired AreaManager areaManager;

	/**
	 * 
	 * @param istatProvincia
	 * @return lista comuni filtrata per codice provinciale
	 */
	public JsonResponse<List<Comuni>> listTown(Integer istatProvincia) {
		Boolean success = true;
		String message = "";
		List<Comuni> data = null;
		JsonResponse<List<Comuni>> result = null;
		try {
			data = townManager.list(istatProvincia);
			message = Messages.getMessage("search.ok");
		} catch (Exception e) {
			message = Messages.getMessage("search.ko");
			e.printStackTrace();
			logger.error("Exception in function: " + this.getClass().getCanonicalName() + ".listTown", e);
		} finally {
			result = new JsonResponse<List<Comuni>>(success, message, data);
		}
		return result;
	}
	/**
	 * 
	 * @return lista delle citta 
	 */
	public JsonResponse<List<Provincie>> listCity() {
		Boolean success = true;
		String message = "";
		List<Provincie> data = null;
		JsonResponse<List<Provincie>> result = null;
		try {
			data = districtManager.listCity();
			message = Messages.getMessage("search.ok");
		} catch (Exception e) {
			message = Messages.getMessage("search.ko");
			e.printStackTrace();
			logger.error("Exception in function: " + this.getClass().getCanonicalName() + ".listCity", e);
		} finally {
			result = new JsonResponse<List<Provincie>>(success, message, data);
		}
		return result;
	}
	/**
	 * 
	 * @return lista delle voci personalizzate con scadenze 
	 */
	public JsonResponse<List<SettingScadenze>> listFrequenze() {
		Boolean success = true;
		String message = "";
		List<SettingScadenze> data = null;
		JsonResponse<List<SettingScadenze>> result = null;
		try {
			data = personScadenzeManager.listFrequenze();
			message = Messages.getMessage("search.ok");
		} catch (Exception e) {
			message = Messages.getMessage("search.ko");
			e.printStackTrace();
			logger.error("Exception in function: " + this.getClass().getCanonicalName() + ".listFrequenze", e);
		} finally {
			result = new JsonResponse<List<SettingScadenze>>(success, message, data);
		}
		return result;
	}
	/**
	 * 
	 * @param aree
	 * @return lista di tutte le aree (voci pre configurate a DB)
	 */
	public JsonResponse<List<Area>> listAree(List<Integer> aree) {
		Boolean success = true;
		String message = "";
		List<Area> data = null;
		JsonResponse<List<Area>> result = null;
		try {
			data = areaManager.list(aree, null);
			message = Messages.getMessage("search.ok");
		} catch (Exception e) {
			message = Messages.getMessage("search.ko");
			e.printStackTrace();
			logger.error("Exception in function: " + this.getClass().getCanonicalName() + ".listAree", e);
		} finally {
			result = new JsonResponse<List<Area>>(success, message, data);
		}
		return result;
	}
	/**
	 * lista delle sole aree con gestione scadenze (voci pre configurate a DB)
	 * @return
	 */
	public JsonResponse<List<Area>> listAreeExpiry() {
		Boolean success = true;
		String message = "";
		List<Area> data = null;
		JsonResponse<List<Area>> result = null;
		try {
			data = areaManager.list(null, true);
			message = Messages.getMessage("search.ok");
		} catch (Exception e) {
			message = Messages.getMessage("search.ko");
			e.printStackTrace();
			logger.error("Exception in function: " + this.getClass().getCanonicalName() + ".listAree", e);
		} finally {
			result = new JsonResponse<List<Area>>(success, message, data);
		}
		return result;
	}
	
	public JsonResponse<String> getVersioneProgramma() {
		Boolean success = true;
		String message = "";
		String data = null;
		JsonResponse<String> result = null;
		try {
			data = Configs.getVERSIONE_PROGRAMMA();
			message = Messages.getMessage("operation.ok");
		} catch (Exception e) {
			success = false;
			message = Messages.getMessage("operation.ko");
			e.printStackTrace();
			logger.error(Utils.errorInMethod("Can't find 'versione programma'"));
		} finally {
			result = new JsonResponse<String>(success, message, data);
		}
		return result;
	}
	/**
	 * Ritorna la lista di tutte le taglie 
	 * @return
	 */
	public JsonResponse<List<KeyValue>> listTaglie() {
		Boolean success = true;
		String message = "";
		List<KeyValue> data = null;
		JsonResponse<List<KeyValue>> result = null;
		try {
			data = new ArrayList<KeyValue>();
			for (EnumsTaglie taglia : EnumsTaglie.values()) {
				data.add(new KeyValue(null, taglia.getDescr(), taglia.getCodice()));
			}
			message = Messages.getMessage("search.ok");
		} catch (Exception e) {
			success = false;
			message = Messages.getMessage("search.ko");
			e.printStackTrace();
			logger.error("Exception in Method: " + this.getClass().getCanonicalName() + ".listTaglie" , e.getMessage());
		} finally {
			result = new JsonResponse<List<KeyValue>>(success, message, data);
		}
		return result;
	}
}
