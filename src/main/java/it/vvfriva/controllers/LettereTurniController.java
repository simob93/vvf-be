package it.vvfriva.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import it.vvfriva.models.JsonResponse;
import it.vvfriva.services.LettereTurniService;

@Controller
@RequestMapping(path="/lettere")
public class LettereTurniController {
	
	@Autowired LettereTurniService lettereTurniService;
	/**
	 * 
	 * @param idSquadra
	 * @return
	 */
	@GetMapping(path="/list")
	public @ResponseBody JsonResponse<List<String>> list(@RequestParam(defaultValue = "-1", required = false) Integer idSquadra) {
		return lettereTurniService.list(idSquadra);
	}
}
