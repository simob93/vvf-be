package it.vvfriva.controllers;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import it.vvfriva.entity.Scadenze;
import it.vvfriva.entity.Servizio;
import it.vvfriva.models.JsonResponse;
import it.vvfriva.models.ModelPortletVigileCertificati;
import it.vvfriva.models.ModelPortletVigilePatenti;
import it.vvfriva.services.ScadenzeService;
import it.vvfriva.services.ServizioService;
import it.vvfriva.services.VigileCertificatiService;
import it.vvfriva.services.VigilePatentiService;
import it.vvfriva.utils.Utils;
@Controller    
@RequestMapping(path="/portlet")
public class PortletController {
	
	@Autowired
	VigilePatentiService vigilePatentiService;
	@Autowired
	VigileCertificatiService vigileCertificatiService;
	@Autowired
	ScadenzeService scadenzeService;
	@Autowired
	ServizioService servizioService;
	/**
	 * 
	 * @param idVigile
	 * @return
	 */
	@GetMapping(path="/{idVigile}/patenti")
	private @ResponseBody JsonResponse<List<ModelPortletVigilePatenti>> listPatenti(@PathVariable("idVigile") Integer idVigile) {
		return vigilePatentiService.listForPortlet(idVigile);
	}
	/**
	 * 
	 * @param idVigile
	 * @return
	 */
	@GetMapping(path="/{idVigile}/certified")
	private @ResponseBody JsonResponse<List<ModelPortletVigileCertificati>> listCertificati(@PathVariable("idVigile") Integer idVigile) {
		return vigileCertificatiService.listForPortlet(idVigile);
	}
	/**
	 * 
	 * @param idVigile
	 * @return
	 */
	@GetMapping(path="/{idVigile}/services")
	private @ResponseBody JsonResponse<List<Servizio>> listServizi(@PathVariable("idVigile") Integer idVigile) {
		return servizioService.list(idVigile, null, null);
	}
	/**
	 * api for list scadenze by vigile
	 * @param scadenza
	 * @return
	 */
	@GetMapping(path = "/expiry")
	public @ResponseBody JsonResponse<List<Scadenze>> listAll(
			@RequestParam("idVigile") Integer idVigile, 
			@RequestParam(value="from", required=false) Date from, 
			@RequestParam(value="to", required=false) Date to,
			@RequestParam(value="idArea", required=false) Integer idArea) {
		
		Date dateFrom = Utils.encodeDate(1899, 12, 31);  
		Date dateTo = Utils.startOfDay(to);  
		
		List<Integer> listArea = idArea != null ? Arrays.asList(idArea) : null;
		
		return scadenzeService.listAll(idVigile, dateFrom, dateTo, listArea, null);
		

	}
	
}
