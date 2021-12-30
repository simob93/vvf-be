package it.vvfriva.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import it.vvfriva.entity.Gradi;
import it.vvfriva.models.JsonResponse;
import it.vvfriva.services.GradiService;

@Controller
@RequestMapping(path = "/gradi")
public class GradiController {

	@Autowired
	private GradiService gradiService;

	/**
	 * 
	 * @param idSrevizio
	 * @return
	 */
	@GetMapping(path = "/listby")
	private @ResponseBody JsonResponse<List<Gradi>> listBy(
			@RequestParam(name = "idServizio", defaultValue = "-1") Integer idSrevizio) {
		return gradiService.listBy(idSrevizio);
	}

	/**
	 * 
	 * @param assenza
	 * @return
	 */
	@PostMapping(path = "/new")
	public @ResponseBody JsonResponse<Gradi> save(@RequestBody Gradi grado) {
		return gradiService.save(grado);
	}

	/**
	 * 
	 * @param assenza
	 * @return
	 */
	@PostMapping(path = "/update")
	public @ResponseBody JsonResponse<Gradi> update(@RequestBody Gradi grado) {
		return gradiService.update(grado);
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping(path = "/delete")
	public @ResponseBody JsonResponse<Gradi> delete(@RequestParam(name = "id") Integer id) {
		return gradiService.delete(id);
	}

}
