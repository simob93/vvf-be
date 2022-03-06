package it.vvfriva.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.vvfriva.entity.ArticoliScadenza;
import it.vvfriva.enums.TipoScadenzaArticolo;
import it.vvfriva.managers.ArticoliScadenzaManager;
import it.vvfriva.models.ArticoliScadenzaInsertDto;
import it.vvfriva.models.ArticoliScadenzaRinnovoInput;
import it.vvfriva.models.ArticoliScadenzaSearch;
import it.vvfriva.models.ArticoliScadenzeDto;
import it.vvfriva.models.JsonResponse;
import it.vvfriva.models.VvfJsonResponse;

/**
 * Author: Simone
 */
@Service
public class ArticoliScadenzaService extends DbServiceStandard< ArticoliScadenza > {

    @Autowired
    ArticoliScadenzaManager articoliScadenzaManager;
    /**
     * 
     * @param input
     * @return
     */
    public JsonResponse<Integer> creaScadenzaArticolo( ArticoliScadenzaInsertDto input) {
        ArticoliScadenza object = new ArticoliScadenza();
        object.setIdArticolo(input.getArticoloId());
        object.setDataScadenza(input.getDataScadenza());
        object.setDataRinnovo(input.getDataRinnovo());
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
		ArticoliScadenza object = new ArticoliScadenza();
		object.setId(scadenzaId);
        object.setIdArticolo(input.getArticoloId());
        object.setDataScadenza(input.getDataScadenza());
        object.setDataRinnovo(input.getDataRinnovo());
        object.setTipoScadenza(TipoScadenzaArticolo.getById(input.getTipoScadenza()));
        articoliScadenzaManager.update(object);
		return new JsonResponse<Integer>(true, object.getId());
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
}
