package it.vvfriva.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import it.vvfriva.entity.Assenze;
import it.vvfriva.models.JsonResponse;
import it.vvfriva.services.AssenzeService;
import it.vvfriva.types.SimpleDate;

@Controller
@RequestMapping(path = "/assenze")
public class AssenzeController {

	@Autowired
	private AssenzeService assenzeService;

	/**
	 * 
	 * @param istatProvincia
	 * @return
	 */
	@GetMapping(path = "/listby")
	private @ResponseBody JsonResponse<List<Assenze>> listBy(
			@RequestParam(name = "idVigile", defaultValue = "-1") Integer idVigile) {
		return assenzeService.listBy(idVigile);
	}
	
	@GetMapping(path = "/getlast")
	private @ResponseBody JsonResponse<Assenze> getLast(
			@RequestParam(name = "idVigile", defaultValue = "-1") Integer idVigile,
			@RequestParam(name = "data") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)  SimpleDate data) {
		return assenzeService.getLast(idVigile, data.toDate());
	}

	/**
	 * 
	 * @param assenza
	 * @return
	 */
	@PostMapping(path = "/new")
	public @ResponseBody JsonResponse<Assenze> save(@RequestBody Assenze assenza) {
		return assenzeService.save(assenza);
	}

	/**
	 * 
	 * @param assenza
	 * @return
	 */
	@PostMapping(path = "/update")
	public @ResponseBody JsonResponse<Assenze> update(@RequestBody Assenze assenza) {
		return assenzeService.update(assenza);
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping(path = "/delete")
	public @ResponseBody JsonResponse<Assenze> delete(@RequestParam(name = "id") Integer id) {
		return assenzeService.delete(id);
	}

}
