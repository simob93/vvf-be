package it.vvfriva.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.vvfriva.entity.Articoli;
import it.vvfriva.managers.ArticoliManager;
import it.vvfriva.models.ArticoliSearch;
import it.vvfriva.models.JsonResponse;

/**
 * 
 * @author simone
 *
 */
@Service
public class ArticoliService extends DbServiceStandard<Articoli> {

	@Autowired 
	public ArticoliManager manager;
	
	public ArticoliManager getManager() {
		return manager;
	}
	
	public JsonResponse<List<Articoli>> list(ArticoliSearch input) {
		return new JsonResponse<List<Articoli>>(true, getManager().list(input));
	}
}
