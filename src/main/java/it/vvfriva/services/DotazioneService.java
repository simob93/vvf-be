package it.vvfriva.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.vvfriva.entity.Dotazione;
import it.vvfriva.managers.DotazioneManager;
import it.vvfriva.models.DotazioneDto;
import it.vvfriva.models.JsonResponse;
import it.vvfriva.models.KeyValueDate;
import it.vvfriva.models.ModelDotazionePortlet;
import it.vvfriva.utils.Messages;

/**
 * 
 * @author simone
 *
 */
@Service
public class DotazioneService extends DbServiceStandard<Dotazione> {

	final private static Logger logger = LoggerFactory.getLogger(DotazioneService.class);

	@Autowired
	public DotazioneManager dotazioneManager;

	public DotazioneManager getManager() {
		return dotazioneManager;
	}

	/**
	 * 
	 * @param idVigile
	 * @return
	 * @throws Exception
	 */
	public JsonResponse<List<KeyValueDate>> listCbox(Integer idVigile) {
		JsonResponse<List<KeyValueDate>> result = null;
		Boolean success = true;
		String message = "";
		List<KeyValueDate> data = null;
		try {
			data = this.getManager().listKeyValueDate(idVigile);
			message = Messages.getMessage("search.ok");
		} catch (Exception e) {
			message = Messages.getMessage("search.ko");
			success = false;
			e.printStackTrace();
			logger.error("Exception in method: " + this.getClass().getCanonicalName() + ".listCbox ", e.getMessage());
		} finally {
			result = new JsonResponse<List<KeyValueDate>>(success, message, data);
		}
		return result;
	}

	public JsonResponse<List<ModelDotazionePortlet>> listForPortlet(Integer idVigile) {
		JsonResponse<List<ModelDotazionePortlet>> result = null;
		Boolean success = true;
		String message = "";
		List<ModelDotazionePortlet> data = null;
		try {
			data = this.getManager().listForPortlet(idVigile);
			message = Messages.getMessage("search.ok");
		} catch (Exception e) {
			message = Messages.getMessage("search.ko");
			success = false;
			e.printStackTrace();
			logger.error("Exception in method: " + this.getClass().getCanonicalName() + ".listForPortlet ", e.getMessage());
		} finally {
			result = new JsonResponse<List<ModelDotazionePortlet>>(success, message, data);
		}
		return result;
	}
	
	public JsonResponse<DotazioneDto> getDotazione(Integer id) {
		JsonResponse<DotazioneDto> result = null;
		Boolean success = true;
		String message = "";
		DotazioneDto data = null;
		try {
			data = this.getManager().getDotazione(id);
			message = Messages.getMessage("search.ok");
		} catch (Exception e) {
			message = Messages.getMessage("search.ko");
			success = false;
			e.printStackTrace();
			logger.error("Exception in method: " + this.getClass().getCanonicalName() + ".listForPortlet ", e.getMessage());
		} finally {
			result = new JsonResponse<DotazioneDto>(success, message, data);
		}
		return result;
	}

}
