package it.vvfriva.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import it.vvfriva.entity.Protocol;
import it.vvfriva.models.JsonResponse;
import it.vvfriva.models.JsonResponseTotal;
import it.vvfriva.services.ProtocolService;

@Controller    
@RequestMapping(path="/protocol")
public class ProtocolController {
	
	
	
	@Autowired 
	ProtocolService protocolService;
	
	@GetMapping(path = "/listpaged")
	public @ResponseBody JsonResponseTotal<List<Protocol>> listPaged(
			@RequestParam Integer firstResult, 
			@RequestParam Integer maxResult,
			@RequestParam(required = false, name = "oggetto") String oggetto,
			@RequestParam(required = false, name = "tipologia") String tipologia,
			@RequestParam(required = false, name = "idFaldone") Integer idFaldone,
			@RequestParam(required = false, name = "numeroProtocollo") String numeroProtocollo,
			@RequestParam(required = false, name= "dal") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)  Date from,
			@RequestParam(required = false, name="al") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)  Date to) {
		return protocolService.listPaged(firstResult, maxResult, oggetto, idFaldone, tipologia, from, to, numeroProtocollo);

	}
	
	@GetMapping(path = "/{id}")
	public @ResponseBody JsonResponse<Protocol> getById(@PathVariable Integer id) {
		return protocolService.getById(id);

	}
	
	@PostMapping(path = "/new")
	public @ResponseBody JsonResponse<Protocol> save(@RequestBody Protocol object) {
		return protocolService.save(object);
	}
	
	@PostMapping(path = "/update")
	public @ResponseBody JsonResponse<Protocol> update(@RequestBody Protocol object) {
		return protocolService.update(object);
	}
	
	@GetMapping(path = "/{id}/delete")
	public @ResponseBody JsonResponse<Protocol> delete(@PathVariable("id") Integer id) {
		return protocolService.delete(id);
	}
	
}
