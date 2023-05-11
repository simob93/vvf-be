package it.vvfriva.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import it.vvfriva.entity.Area;
import it.vvfriva.entity.Comuni;
import it.vvfriva.entity.Provincie;
import it.vvfriva.entity.SettingScadenze;
import it.vvfriva.models.JsonResponse;
import it.vvfriva.services.GeneralService;
@Controller    
@RequestMapping(path="/general")
public class GeneralController {
	
	@Autowired GeneralService generalService;
	
	@GetMapping(path="/versione")
	private @ResponseBody JsonResponse<String> getVersioneProgramma() {
		return generalService.getVersioneProgramma();
	}
	
	@GetMapping(path="/provincie")
	private @ResponseBody JsonResponse<List<Provincie>> listCity() {
		return generalService.listCity();
	}
	
	@GetMapping(path="/comuni")
	private @ResponseBody JsonResponse<List<Comuni>> listTown(@RequestParam(name="idCity", defaultValue = "-1") Integer istatProvincia) {
		return generalService.listTown(istatProvincia);
	}
	
	@GetMapping(path="/expiry/freq")
	private @ResponseBody JsonResponse<List<SettingScadenze>> listFrequenze() {
		return generalService.listFrequenze();
	}
	
	@GetMapping(path="/aree")
	private @ResponseBody JsonResponse<List<Area>> listAree(@RequestParam(name = "area", defaultValue = "") List<Integer> aree ) {
		return generalService.listAree(aree);
	}
	
	@GetMapping(path="/aree/expiry")
	private @ResponseBody JsonResponse<List<Area>> listAreeExpiry() {
		return generalService.listAreeExpiry();
	}

}
