package it.vvfriva.controllers;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import it.vvfriva.entity.Comuni;
import it.vvfriva.entity.Dotazione;
import it.vvfriva.entity.Scadenze;
import it.vvfriva.entity.Vigile;
import it.vvfriva.entity.VigileCertificati;
import it.vvfriva.entity.VigilePatenti;
import it.vvfriva.enums.DbOperation;
import it.vvfriva.managers.VigileManager;
import it.vvfriva.models.DotazioneDto;
import it.vvfriva.models.JsonResponse;
import it.vvfriva.models.KeyValueDate;
import it.vvfriva.models.KeyValueTipiScadenza;
import it.vvfriva.models.ModelInfoVigili;
import it.vvfriva.models.ModelPrntVigili;
import it.vvfriva.models.VigileModel;
import it.vvfriva.services.DotazioneService;
import it.vvfriva.services.ScadenzeService;
import it.vvfriva.services.VigileCertificatiService;
import it.vvfriva.services.VigilePatentiService;
import it.vvfriva.services.VigileService;
import it.vvfriva.utils.Utils;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

@Controller    
@RequestMapping(path="/vigili")
public class VigileController {
	
	@Autowired VigileService vigileService;
	@Autowired VigilePatentiService vigilePatentiService;
	@Autowired VigileCertificatiService vigileCertificatiService;
	@Autowired ScadenzeService scadenzeService;
	@Autowired VigileManager vigileManager;
	@Autowired DotazioneService dotazioneService;
	@Autowired
	private ResourceLoader resourceLoader;
	
	/***********************************************************************************************************
	 * API GESTIONE VIGILE
	 ***********************************************************************************************************/
	
	/**
	 * 
	 * @return
	 */
	@GetMapping(path = "/list")
	public @ResponseBody JsonResponse<List<VigileModel>> list(
			@RequestParam(value = "assenti", defaultValue = "false") Boolean assenti,
			@RequestParam(value = "nonAttivi", defaultValue = "false") Boolean nonAttivi,
			@RequestParam(value = "idSquadra", required = false, defaultValue = "-1") Integer idSquadra) {
		assenti = assenti != null ? assenti : false;
		return vigileService.list(assenti, nonAttivi, idSquadra);
	}
	/**
	 * 
	 * @return
	 */
	@GetMapping(path = "/info")
	public @ResponseBody JsonResponse<ModelInfoVigili> getInfoVigili() {
		return vigileService.getInfoVigili();
	}
	/**
	 * 
	 * @param start
	 * @param limit
	 * @return
	 */
	@GetMapping(path = "/listpaged")
	public @ResponseBody JsonResponse<List<Vigile>> listPaged(
			@RequestParam Integer start,
			@RequestParam Integer limit, 
			@RequestParam(value = "srcText", required = false) String srcText) {
		return vigileService.listPaged(start, limit, srcText);
	}
	/**
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping(path = "/{id}")
	public @ResponseBody JsonResponse<Vigile> getById(@PathVariable("id") Integer id) {
		return vigileService.getById(id);
	}
	/**
	 * 
	 * @param vigile
	 * @return
	 */
	@PostMapping(path = "/new")
	public @ResponseBody JsonResponse<Vigile> save(@RequestBody Vigile vigile) {
		return vigileService.save(vigile);
	}
	
	@PostMapping(path = "/uploadFoto", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public @ResponseBody JsonResponse<Vigile> save(
			@RequestParam("idVigile") Integer idVigile,
			@RequestParam("base64") String base64) {
		return vigileService.uploadFoto(idVigile, base64);
	}
	
	/**
	 * 
	 * @param vigile
	 * @return
	 */
	@PostMapping(path = "/update")
	public @ResponseBody JsonResponse<Vigile> update(@RequestBody Vigile vigile) {
		return vigileService.update(vigile);

	}
	/**
	 * 
	 * @param vigile
	 * @return
	 */
	@GetMapping(path = "/{id}/delete")
	public @ResponseBody JsonResponse<Vigile> update(@PathVariable Integer id) {
		return vigileService.delete(id);

	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping(path = "/{id}/licenses")
	public @ResponseBody JsonResponse<List<VigilePatenti>> getPatentiServizio(@PathVariable("id") Integer idVigile) {
		return vigilePatentiService.list(idVigile);
	}
	/**
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping(path = "/{id}/certified")
	public @ResponseBody JsonResponse<List<VigileCertificati>> getCertificati(@PathVariable("id") Integer idVigile) {
		return vigileCertificatiService.list(idVigile);
	}
	/**
	 * 
	 * @param id
	 * @param from
	 * @param to
	 * @return
	 */
	@GetMapping(path = "/{id}/expiry")
	public @ResponseBody JsonResponse<List<Scadenze>> getExpiry(@PathVariable Integer id, 	
			@RequestParam(value="from", required=false)@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)  LocalDate from, 
			@RequestParam(value="to", required=false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
			@RequestParam(value="idArea", required=false) Integer idArea,
			@RequestParam(value="storico", required=false, defaultValue="false")  Boolean storico) {
//		Date dateFrom = from != null ? Utils.startOfDay(from.toDate()) : new Date();
//		Date dateTo = to !=null ? Utils.endOfDay(to.toDate()) : new Date();
//		
//		 if (storico == true) 
//			 dateFrom = Utils.encodeDate(1899, 12, 31);
		 
		 List<Integer> listAree = idArea != null ? Arrays.asList(idArea) : null;
		return scadenzeService.listAll(id, null, null, listAree, storico);
	}
	/**
	 * 
	 * @param idVigile
	 * @param tipo
	 * @return
	 */
	@GetMapping(path = "/{id}/expiry/person")
	public @ResponseBody JsonResponse<List<KeyValueTipiScadenza>> getCboxPerson(@PathVariable("id") Integer idVigile, 
			@RequestParam Integer idArea) {
		return scadenzeService.getCboxTipiScadenza(idVigile, idArea);
	}
	
	/***********************************************************************************************************
	 * API GESTIONE PATENTI 
	 ***********************************************************************************************************/
	/**
	 * 
	 * @param vigileExtraInfo
	 * @return
	 */
	@PostMapping(path = "/patenti/new")
	public @ResponseBody JsonResponse<List<VigilePatenti>> save(@RequestBody List<VigilePatenti> vigileExtraInfo) {
		return vigilePatentiService.saveOrUpdate(vigileExtraInfo, DbOperation.INSERT);
	}
	/**
	 * 
	 * @param vigileExtraInfo
	 * @return
	 */
	@PostMapping(path = "/patenti/update")
	public @ResponseBody JsonResponse<List<VigilePatenti>> update(@RequestBody List<VigilePatenti> vigileExtraInfo) {
		return vigilePatentiService.saveOrUpdate(vigileExtraInfo, DbOperation.UPDATE);
	}
	/**
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping(path = "/patenti/{id}/delete")
	public @ResponseBody JsonResponse<VigilePatenti> delete(@PathVariable("id") Integer id) {
		return vigilePatentiService.delete(id);
	}
	
	
	@PostMapping(path="/report") 
	public @ResponseBody void  report(HttpServletResponse response) throws Exception {
		
		JasperPrint jasperPrint = null;

		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition","attachment;filename=\"users.pdf\"");
		
		OutputStream out = response.getOutputStream();

		 String path = resourceLoader.getResource("classpath:rpt/VVF_Organico.jrxml").getURI().getPath();
		 JasperReport jasperReport;
		 Map<String, Object> parameters = new HashMap<String, Object>();
		 List<Vigile> listVigili = vigileManager.listPaged(0, 500, null);
		 
		 if (!Utils.isEmptyList(listVigili)) {
			 
			 List<ModelPrntVigili> listModelPrntVigili = new ArrayList<ModelPrntVigili>();
			 
			 SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			 for(Vigile vigile: listVigili) {
				 
				 Comuni citta = vigile.getComune();
				 
				 listModelPrntVigili.add(new ModelPrntVigili(
						 (vigile.getLastName() + " " + vigile.getFirstName()).toUpperCase(), 
						 (vigile.getBirthday() != null ? sdf.format(vigile.getBirthday()) : ""), 
						 vigile.getAddress(), (citta != null ? citta.getName() : "ND"),  vigile.getPhone(), vigile.getCodPhone()));
			 }
			 parameters.put("listaVigili", listModelPrntVigili);
		 }


		try {
			jasperReport = JasperCompileManager.compileReport(path);
			jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
			JasperExportManager.exportReportToPdfStream(jasperPrint, out);

		} catch (JRException e) {
			e.printStackTrace();
		} finally {
			response.flushBuffer();
		}
	}
	
	/***********************************************************************************************************
	 * API GESTIONE CERTIFICATI
	 ***********************************************************************************************************/

	@PostMapping(path = "/certified/new")
	public @ResponseBody JsonResponse<List<VigileCertificati>> saveCertificati(
			@RequestBody List<VigileCertificati> vigileCertificati) {
		return vigileCertificatiService.saveOrUpdate(vigileCertificati, DbOperation.INSERT);
	}

	@PostMapping(path = "/certified/update")
	public @ResponseBody JsonResponse<List<VigileCertificati>> updateCertificati(
			@RequestBody List<VigileCertificati> vigileCertificati) {
		return vigileCertificatiService.saveOrUpdate(vigileCertificati, DbOperation.UPDATE);
	}

	@GetMapping(path = "/certified/{id}/delete")
	public @ResponseBody JsonResponse<VigileCertificati> deleteCertificato(@PathVariable("id") Integer id) {
		return vigileCertificatiService.delete(id);
	}
	
	/***********************************************************************************************************
	 * API GESTIONE DOTAZIONI VIGILE  
	 ***********************************************************************************************************/
	
	@GetMapping("/dotazione/cbox")
	public @ResponseBody JsonResponse<List<KeyValueDate>> listCbox(@RequestParam("idVigile") Integer idVigile) {
		return dotazioneService.listCbox(idVigile);
	}
	
	@GetMapping("/dotazione/get")
	public @ResponseBody JsonResponse<DotazioneDto> get(@RequestParam("id") Integer id) {
		return dotazioneService.getDotazione(id);
	}
	/**
	 * 
	 * @param dotazione
	 * @return
	 */
	@PostMapping("/dotazione/save")
	public @ResponseBody JsonResponse<Dotazione> saveDotazione(@RequestBody Dotazione dotazione) {
		return dotazioneService.save(dotazione);
	}
	/**
	 * 
	 * @param dotazione
	 * @return
	 */
	@PostMapping("/dotazione/update")
	public @ResponseBody JsonResponse<Dotazione> updateDotazione(@RequestBody Dotazione dotazione) {
		return dotazioneService.update(dotazione);
	}
	/**
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/dotazione/delete")
	public @ResponseBody JsonResponse<Dotazione> deleteDotazione(@RequestParam("id") Integer id) {
		return dotazioneService.delete(id);
	}
	
}
