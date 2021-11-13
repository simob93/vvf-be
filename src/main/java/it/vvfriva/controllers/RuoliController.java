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

import it.vvfriva.entity.Ruoli;
import it.vvfriva.enums.DbOperation;
import it.vvfriva.models.JsonResponse;
import it.vvfriva.models.KeyValue;
import it.vvfriva.models.TreeNodeMenu;
import it.vvfriva.services.RuoliPermessiService;
import it.vvfriva.services.RuoliService;

@Controller    
@RequestMapping(path="/ruoli")
public class RuoliController {
	
	@Autowired RuoliService ruoliService;
	@Autowired RuoliPermessiService ruoliPermessiService;

	/**
	 * 
	 * @return
	 */
	@GetMapping("/list")
	private @ResponseBody JsonResponse<List<Ruoli>> list() {
		return ruoliService.list();
	}
	
	@GetMapping("/get")
	private @ResponseBody JsonResponse<Ruoli> get(@RequestParam("id") Integer idRuolo) {
		return ruoliService.getById(idRuolo);
	}
	/**
	 * 
	 * @return
	 */
	@GetMapping("/cbox")
	private @ResponseBody JsonResponse<List<KeyValue>> cbox() {
		return ruoliService.cbox();
	}
	
	@PostMapping("/new")
	private @ResponseBody JsonResponse<Ruoli> saveRuolo(@RequestBody Ruoli ruolo) {
		return ruoliService.saveOrUpdate(ruolo, DbOperation.INSERT);
	}
	/**
	 * modifica account utente
	 * @param utente
	 * @return
	 */
	@PostMapping("/update")
	private @ResponseBody JsonResponse<Ruoli> updateRuolo(@RequestBody Ruoli ruolo) {
		return ruoliService.saveOrUpdate(ruolo, DbOperation.UPDATE);
	}
	/**
	 * eliminazione account utente
	 * @param id
	 * @return
	 */
	@GetMapping("/delete")
	private @ResponseBody JsonResponse<Ruoli> deleteUtente(@RequestParam("id") Integer id) {
		return ruoliService.delete(id);
	}
	/**
	 * 
	 * @param idRuolo
	 * @return
	 */
	@GetMapping("/permessi/cbox")
	private @ResponseBody JsonResponse<List<KeyValue>> getById(@RequestParam("idRuolo") Integer idRuolo) {
		return ruoliPermessiService.getPermissiByRuoloCbox(idRuolo);
	}
	/**
	 * 
	 * @param idPermesso
	 * @param nuovoPermesso
	 * @return
	 */
	@GetMapping("/permessi/change")
	private @ResponseBody JsonResponse<String> getById(@RequestParam("idPermesso") Integer idPermesso, @RequestParam("permesso")  String nuovoPermesso) {
		return ruoliPermessiService.changePermesso(idPermesso, nuovoPermesso);
	}
	/**
	 * 
	 * @param idRuolo
	 * @return
	 */
	@GetMapping("/permessi/tree")
	private @ResponseBody JsonResponse<TreeNodeMenu> getRuoloPermssoForUser(@RequestParam("idRuolo") Integer idRuolo) {
		return ruoliPermessiService.getTreePermissiByRuolo(idRuolo);
	}
		
}
