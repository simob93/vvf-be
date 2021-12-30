package it.vvfriva.controllers;

import java.util.List;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import it.vvfriva.entity.Servizio;
import it.vvfriva.models.JsonResponse;
import it.vvfriva.services.ServizioService;

@Controller
@RequestMapping(path="/servizio")
public class ServizioController {

	@Autowired ServizioService servizioService;
	
	@GetMapping(path="/list")
	private @ResponseBody JsonResponse<List<Servizio>> list(
			@RequestParam Integer idVigile,
			@RequestParam(value = "dal", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)  LocalDate dal,
			@RequestParam(value = "al", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)  LocalDate al) {
		return servizioService.list(idVigile, dal != null ? dal.toDate() : null, al != null ? al.toDate(): null);
	}
	
	@PostMapping(path="/new")
	public @ResponseBody JsonResponse<Servizio> save(@RequestBody Servizio servizio) {
		return servizioService.save(servizio);
		
	}
	
	@PostMapping(path="/update")
	public @ResponseBody JsonResponse<Servizio> update(@RequestBody Servizio servizio) {
		return servizioService.update(servizio);
		
	}
	@GetMapping(path="/delete")
	private @ResponseBody JsonResponse<Servizio> delete(@RequestParam Integer id) {
		return servizioService.delete(id);
	}
}
