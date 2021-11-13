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

import it.vvfriva.entity.Articoli;
import it.vvfriva.entity.ArticoliCategorie;
import it.vvfriva.entity.ArticoliDepositi;
import it.vvfriva.entity.Categorie;
import it.vvfriva.entity.Deposito;
import it.vvfriva.enums.DbOperation;
import it.vvfriva.models.JsonResponse;
import it.vvfriva.services.ArticoliCategorieService;
import it.vvfriva.services.ArticoliDepositiService;
import it.vvfriva.services.ArticoliService;
import it.vvfriva.services.CategorieService;
import it.vvfriva.services.DepositoService;

@Controller    
@RequestMapping(path="/mag")
public class MagazzinoController {
	
	@Autowired DepositoService depService;
	@Autowired CategorieService catService;
	@Autowired ArticoliService articoliService;
	@Autowired ArticoliCategorieService articoliCatService;
	@Autowired ArticoliDepositiService articoliDepositiService;
	
	/*************************************************************************************
	 * GESTIONE DEPOSITI
	 *************************************************************************************/
	/**
	 * 
	 * @param attivi
	 * @return
	 */
	@GetMapping("/deposito/list")
	private @ResponseBody JsonResponse<List<Deposito>> listDepositi(@RequestParam(defaultValue = "false") Boolean attivi) {
		return depService.list(attivi);
	}
	/**
	 * 
	 * @param deposito
	 * @return
	 */
	@PostMapping(path = "/deposito/update")
	public @ResponseBody JsonResponse<Deposito> updateDeposito(@RequestBody Deposito deposito) {
		return depService.saveOrUpdate(deposito, DbOperation.UPDATE);
	}
	/**
	 * 
	 * @param deposito
	 * @return
	 */
	@PostMapping(path = "/deposito/new")
	public @ResponseBody JsonResponse<Deposito> insertDeposito(@RequestBody Deposito deposito) {
		return depService.saveOrUpdate(deposito, DbOperation.INSERT);
	}
	/**
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/deposito/delete")
	private @ResponseBody JsonResponse<Deposito> deleteDeposito(@RequestParam("id") Integer id) {
		return depService.delete(id);
	}
	/**
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/deposito/get")
	private @ResponseBody JsonResponse<Deposito> getDepositoById(@RequestParam("id") Integer id) {
		return depService.getObjectById(id);
	}
	
	/*************************************************************************************
	 * GESTIONE ARTICOLI
	 *************************************************************************************/
	
	@GetMapping("/articoli/list")
	private @ResponseBody JsonResponse<List<Articoli>> listArticoli() {
		return articoliService.list();
	}
	
	@GetMapping("/articoli/get")
	private @ResponseBody JsonResponse<Articoli> getArticoloById(@RequestParam("id") Integer id) {
		return articoliService.getObjectById(id);
	}
	/**
	 * 
	 * @param articolo
	 * @return
	 */
	@PostMapping("/articoli/new")
	public @ResponseBody JsonResponse<Articoli> insertArticolo(@RequestBody Articoli articolo) {
		return articoliService.saveOrUpdate(articolo, DbOperation.INSERT);
	}
	/**
	 * 
	 * @param articolo
	 * @return
	 */
	@PostMapping("/articoli/update")
	public @ResponseBody JsonResponse<Articoli> updateArticolo(@RequestBody Articoli articolo) {
		return articoliService.saveOrUpdate(articolo, DbOperation.UPDATE);
	}
	/**
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/articoli/delete")
	private @ResponseBody JsonResponse<Articoli> deleteArticolo(@RequestParam("id") Integer id) {
		return articoliService.delete(id);
	}
	
	/*************************************************************************************
	 * GESTIONE ARTICOLO CATEGORIE
	 *************************************************************************************/
	/**
	 * 
	 * @return
	 */
	@GetMapping("/articoli/categorie/list")
	private @ResponseBody JsonResponse<List<ArticoliCategorie>> listArticoloCategorie(@RequestParam("idArticolo") Integer idArticolo) {
		return articoliCatService.list(idArticolo);
	}
	/**
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/articoli/categorie/get")
	private @ResponseBody JsonResponse<ArticoliCategorie> getArticoloCategoriaById(@RequestParam("id") Integer id) {
		return articoliCatService.getObjectById(id);
	}
	/**
	 * 
	 * @param categoria
	 * @return
	 */
	@PostMapping("/articoli/categorie/new")
	public @ResponseBody JsonResponse<Boolean> insertArticoloCategoria(@RequestBody List<ArticoliCategorie> artCat) {
		return articoliCatService.saveAll(artCat);
	}
	/**
	 * 
	 * @param categoria
	 * @return
	 */
	@PostMapping("/articoli/categorie/update")
	public @ResponseBody JsonResponse<ArticoliCategorie> updateArticoloCategoria(@RequestBody ArticoliCategorie artCat) {
		return articoliCatService.saveOrUpdate(artCat, DbOperation.UPDATE);
	}
	/**
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/articoli/categorie/delete")
	public @ResponseBody JsonResponse<ArticoliCategorie> deleteArticoloCategoria(@RequestParam("id") Integer id) {
		return articoliCatService.delete(id);
	}
	
	/*************************************************************************************
	 * GESTIONE ARTICOLO DEPOSITO
	 *************************************************************************************/
	
	/**
	 * 
	 * @return
	 */
	@GetMapping("/articoli/depositi/list")
	private @ResponseBody JsonResponse<List<ArticoliDepositi>> listDepositiArticoli(@RequestParam("idArticolo") Integer idArticolo) {
		return articoliDepositiService.list(idArticolo);
	}
	/**
	 * 
	 * @param categoria
	 * @return
	 */
	@PostMapping("/articoli/depositi/new")
	public @ResponseBody JsonResponse<Boolean> insertArticoloDepositi(@RequestBody List<ArticoliDepositi> artCat) {
		return articoliDepositiService.saveAll(artCat);
	}
	
	/*************************************************************************************
	 * GESTIONE CATEGORIE
	 *************************************************************************************/
	
	/**
	 * 
	 * @return
	 */
	@GetMapping("/categorie/list")
	private @ResponseBody JsonResponse<List<Categorie>> listCategorie() {
		return catService.list();
	}
	/**
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/categorie/get")
	private @ResponseBody JsonResponse<Categorie> getCategoriaById(@RequestParam("id") Integer id) {
		return catService.getObjectById(id);
	}
	/**
	 * 
	 * @param categoria
	 * @return
	 */
	@PostMapping("/categorie/new")
	public @ResponseBody JsonResponse<Categorie> insertCategoria(@RequestBody Categorie categoria) {
		return catService.saveOrUpdate(categoria, DbOperation.INSERT);
	}
	/**
	 * 
	 * @param categoria
	 * @return
	 */
	@PostMapping("/categorie/update")
	public @ResponseBody JsonResponse<Categorie> updateCategoria(@RequestBody Categorie categoria) {
		return catService.saveOrUpdate(categoria, DbOperation.UPDATE);
	}
	/**
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/categorie/delete")
	public @ResponseBody JsonResponse<Categorie> deleteCategoria(@RequestParam("id") Integer id) {
		return catService.delete(id);
	}
	
	
	
}
