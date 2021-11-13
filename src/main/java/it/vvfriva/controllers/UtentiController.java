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

import it.vvfriva.entity.Utenti;
import it.vvfriva.enums.DbOperation;
import it.vvfriva.models.ChangePasswordModel;
import it.vvfriva.models.JsonResponse;
import it.vvfriva.services.UtentiService;
/**
 * 
 * @author simone
 *
 */
@Controller
@RequestMapping(path="/user")
public class UtentiController {
	
	@Autowired private UtentiService utentiService;
	
	/******************************************************************
	 *  OPERAZIONI BASE GESTIONE UTENTI SOFTWARE
	 ******************************************************************/
	
	@GetMapping("/list")
	private @ResponseBody JsonResponse<List<Utenti>> listUtenti() {
		return utentiService.listUtenti();
	}
	
	@GetMapping("/get")
	private @ResponseBody JsonResponse<Utenti> getById(@RequestParam("id") Integer id) {
		return utentiService.getById(id);
	}
	
	/**
	 * Inserimento account utente
	 * @param utente
	 * @return
	 */
	@PostMapping("/new")
	private @ResponseBody JsonResponse<Utenti> saveUtente(@RequestBody Utenti utente) {
		return utentiService.save(utente);
	}
	/**
	 * modifica account utente
	 * @param utente
	 * @return
	 */
	@PostMapping("/update")
	private @ResponseBody JsonResponse<Utenti> updateUtente(@RequestBody Utenti utente) {
		return utentiService.saveOrUpdate(utente, DbOperation.UPDATE);
	}
	/**
	 * eliminazione account utente
	 * @param id
	 * @return
	 */
	@GetMapping("/delete")
	private @ResponseBody JsonResponse<Utenti> deleteUtente(@RequestParam("id") Integer id) {
		return utentiService.delete(id);
	}
	/**
	 * 
	 * @param changePasswordModel
	 * @return
	 */
	@PostMapping("/changepassword")
	private @ResponseBody JsonResponse<Boolean> changePassword(@RequestBody ChangePasswordModel changePasswordModel) {
		return utentiService.changePassword(changePasswordModel);
	}
	/**
	 * abilita l'utente all'utilizzo della piattaforma
	 * @param id
	 * @return
	 */
	@GetMapping("/enable")
	private @ResponseBody JsonResponse<Utenti> enableUser(@RequestParam("id") Integer id) {
		return utentiService.enableDisable(id, true);
	}
	/**
	 * disabilita l'utente all'utilizzo della piattaforma
	 * @param id
	 * @return
	 */
	@GetMapping("/disable")
	private @ResponseBody JsonResponse<Utenti> disableUser(@RequestParam("id") Integer id) {
		return utentiService.enableDisable(id, false);
	}
	/**
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/resetpassword")
	private @ResponseBody JsonResponse<Utenti> resetPassword(@RequestParam("id") Integer id) {
		return utentiService.resetPassword(id);
	}
	/**
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/recoverpassword")
	private @ResponseBody JsonResponse<Boolean> recoverPassword(@RequestParam("username") String username, @RequestParam("email") String email) {
		return utentiService.recoverPassword(username, email);
	}
	
}
