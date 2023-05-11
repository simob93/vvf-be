package it.vvfriva.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.vvfriva.entity.ArticoliScadenza;
import it.vvfriva.enums.EnumTrueFalse;
import it.vvfriva.enums.TipoScadenzaArticolo;
import it.vvfriva.exception.UserFriendlyException;
import it.vvfriva.managers.ArticoliScadenzaManager;
import it.vvfriva.models.ArticoliScadenzaInsertDto;
import it.vvfriva.models.ArticoliScadenzaRinnovoInput;
import it.vvfriva.models.ArticoliScadenzaSearch;
import it.vvfriva.models.ArticoliScadenzeDto;
import it.vvfriva.models.JsonResponse;
import it.vvfriva.models.KeyValuePeriodo;
import it.vvfriva.models.VvfJsonResponse;
import it.vvfriva.repository.ArticoliScadenzaRepository;
import it.vvfriva.specifications.ArticoliScadenzaSpecification;
import it.vvfriva.utils.Utils;

/**
 * Author: Simone
 */
@Service
public class ArticoliScadenzaService extends DbServiceStandard< ArticoliScadenza > {

    @Autowired
    ArticoliScadenzaManager articoliScadenzaManager;
    
    @Autowired
	ArticoliScadenzaRepository repository;
    /**
     * 
     * @param input
     * @return
     */
    public JsonResponse<Integer> creaScadenzaArticolo( ArticoliScadenzaInsertDto input) {
        ArticoliScadenza object = new ArticoliScadenza();
        object.setIdArticolo(input.getArticoloId());
        object.setDataScadenza(input.getDataScadenza());
        object.setDataRinnovo(Utils.startOfDay(new Date()));
        object.setNote(input.getNote());
        object.setTipoScadenza(TipoScadenzaArticolo.getById(input.getTipoScadenza()));
        articoliScadenzaManager.save( object );
        return new JsonResponse<Integer>(true, object.getId());
    }
    /**
     * 
     * @return
     */
    public JsonResponse<List<ArticoliScadenzeDto>> getArticoliScadenza(ArticoliScadenzaSearch input) {
		return new JsonResponse<List<ArticoliScadenzeDto>>(true, articoliScadenzaManager.getArticoliScadenza(input));
    }
    /**
     * 
     * @param scadenzaId
     * @param input
     * @return
     */
	public JsonResponse<Integer> modificaScadenzaArticolo(int scadenzaId, ArticoliScadenzaInsertDto input) {
		ArticoliScadenza scadenza = articoliScadenzaManager.getObjById(scadenzaId);
		scadenza.setIdArticolo(input.getArticoloId());
		scadenza.setDataRinnovo(input.getDataRinnovo());
		scadenza.setDataScadenza(input.getDataScadenza());
		scadenza.setNote(input.getNote());
		scadenza.setTipoScadenza(TipoScadenzaArticolo.getById(input.getTipoScadenza()));
		scadenza.setEffettuato(input.getEffettuata());
        articoliScadenzaManager.update(scadenza);
		return new JsonResponse<Integer>(true, scadenza.getId());
	}
	/**
	 * 
	 * @param scadenzaId
	 * @return
	 */
	public VvfJsonResponse<Integer> eliminaScadenzaArticolo(int scadenzaId) {
		articoliScadenzaManager.delete(articoliScadenzaManager.getObjById(scadenzaId));
		return new VvfJsonResponse<Integer>(true, null, null);
	}
	/**
	 * 
	 * @param scadenzaId
	 * @param input
	 * @return
	 */
	public JsonResponse<Integer> rinnovaScadenzaArticolo(int scadenzaId, ArticoliScadenzaRinnovoInput input) {
		return new JsonResponse<Integer>(true, articoliScadenzaManager.rinnovaScadenzaArticolo(scadenzaId, input));
	}
	/**
	 * @param scadenzaId
	 * @return
	 */
	public VvfJsonResponse<Integer> effettuaScadenzaArticolo(int scadenzaId) {
		ArticoliScadenza scadenza = articoliScadenzaManager.getObjById(scadenzaId);
		scadenza.setEffettuato(EnumTrueFalse.T);
		articoliScadenzaManager.update(scadenza);
		return new VvfJsonResponse<Integer>(true, null, null);
	}
	/**
	 * 
	 * @param scadenzaId
	 * @return
	 */
	public VvfJsonResponse<Integer> riattivaScadenzaArticolo(int scadenzaId) {
		ArticoliScadenza scadenza = articoliScadenzaManager.getObjById(scadenzaId);
		
		// riattivo la scadenza se non trovo un altra scadenza attiva 
		List<ArticoliScadenza> scadenzaArticolo = repository.findAll(ArticoliScadenzaSpecification
				.ConIdArticolo(scadenza.getIdArticolo()).and(ArticoliScadenzaSpecification.ConScadenzaAttiva()));
		if (scadenzaArticolo.size() > 0) {
			throw new UserFriendlyException("l'operazione non è concessa, esiste già una scadenza");
		}
		scadenza.setEffettuato(EnumTrueFalse.F);
		articoliScadenzaManager.update(scadenza);
		return new VvfJsonResponse<Integer>(true, null, null);
	}
	/**
	 * 
	 * @param articoloId
	 * @return
	 */
	public VvfJsonResponse<List<KeyValuePeriodo>> getStoricoRinnoviArticolo(int articoloId) {
		return  new VvfJsonResponse<List<KeyValuePeriodo>>(true, null, articoliScadenzaManager.getStoricoRinnoviArticolo(articoloId));
	}
}
