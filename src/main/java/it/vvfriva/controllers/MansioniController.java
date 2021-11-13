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

import it.vvfriva.entity.Mansioni;
import it.vvfriva.enums.DbOperation;
import it.vvfriva.models.JsonResponse;
import it.vvfriva.services.MansioniService;

@Controller
@RequestMapping(path = "/carriera")
public class MansioniController {

	@Autowired
	private MansioniService carrieraService;

	/**
	 * 
	 * @param istatProvincia
	 * @return
	 */
	@GetMapping(path = "/listby")
	private @ResponseBody JsonResponse<List<Mansioni>> listBy(
			@RequestParam(name = "idVigile", defaultValue = "-1") Integer idVigile,
			@RequestParam(value = "dal", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)  LocalDate dal,
			@RequestParam(value = "al", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)  LocalDate al) {
		return carrieraService.listBy(idVigile, dal != null ? dal.toDate() : null, al != null ? al.toDate() : null);
	}

	/**
	 * 
	 * @param assenza
	 * @return
	 */
	@PostMapping(path = "/new")
	public @ResponseBody JsonResponse<Mansioni> save(@RequestBody Mansioni carriera) {
		return carrieraService.saveOrUpdate(carriera, DbOperation.INSERT);
	}

	/**
	 * 
	 * @param assenza
	 * @return
	 */
	@PostMapping(path = "/update")
	public @ResponseBody JsonResponse<Mansioni> update(@RequestBody Mansioni carriera) {
		return carrieraService.saveOrUpdate(carriera, DbOperation.INSERT);
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping(path = "/delete")
	public @ResponseBody JsonResponse<Mansioni> delete(@RequestParam(name = "id") Integer id) {
		return carrieraService.delete(id);
	}

}
