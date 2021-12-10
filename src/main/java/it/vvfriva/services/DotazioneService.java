package it.vvfriva.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.vvfriva.entity.Dotazione;
import it.vvfriva.enums.DbOperation;
import it.vvfriva.managers.DotazioneManager;
import it.vvfriva.models.JsonResponse;
import it.vvfriva.models.KeyValueDate;
import it.vvfriva.models.ModelDotazionePortlet;
import it.vvfriva.utils.Messages;
import it.vvfriva.utils.Utils;

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
			List<Dotazione> listDotazioni = this.getManager().list(idVigile);
			if (!Utils.isEmptyList(listDotazioni)) {
				data = listDotazioni.stream().map(dotazione -> new KeyValueDate(dotazione.getId(),
						dotazione.getDescrArticolo(), null, dotazione.getDataConsegna())).collect(Collectors.toList());
			}
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
    /**
     * Inseriscie, modifica e cancella eventuali dotazioni vigile 
     * @param dotazioni
     * @return
     */
	@Transactional
	public JsonResponse<Dotazione> insert(List<Dotazione> dotazioni) {
		JsonResponse<Dotazione> response = null;
		Boolean success = true;
		String message = "";
		try {
			
			if (Utils.isEmptyList(dotazioni)) {
				logger.warn("list articoli empty in method: " + this.getClass().getCanonicalName() + ".insert");
				throw new Exception(Messages.getMessageFormatted("field.list.empty",
						new Object[] { Messages.getMessage("field.list.articoli") }));
			}
			
			for (Dotazione dotazioneVigile : dotazioni) {
				if ((dotazioneVigile.getDaEliminare() != null) && (dotazioneVigile.getDaEliminare().booleanValue())) {
					this.delete(dotazioneVigile.getId());
				} else {
					if (!Utils.isValidId(dotazioneVigile.getId()))
						this.saveOrUpdate(dotazioneVigile, DbOperation.INSERT);
					else
						this.saveOrUpdate(dotazioneVigile, DbOperation.UPDATE);
				}
			}
			message = Messages.getMessage("operation.ok");
		} catch (Exception e) {
			success = false;
			message = Messages.getMessage("operation.ko");
			e.printStackTrace();
			logger.error("Exception in method: " + this.getClass().getCanonicalName() + ".insert", e);
		} finally {
			response = new JsonResponse<Dotazione>(success, message, null);
		}
		return response;
	}

	public JsonResponse<List<ModelDotazionePortlet>> listForPortlet(Integer idVigile) {
		JsonResponse<List<ModelDotazionePortlet>> result = null;
		Boolean success = true;
		String message = "";
		List<ModelDotazionePortlet> data = null;
		try {
			List<Dotazione> listDotazioni = this.getManager().listForPortlet(idVigile);
			if (!Utils.isEmptyList(listDotazioni)) {
				data = new ArrayList<ModelDotazionePortlet>();
				for (Dotazione dotazione : listDotazioni) {
					data.add(new ModelDotazionePortlet(dotazione.getDescrArticolo(), dotazione.getDataConsegna()));
				}
			}
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

}
