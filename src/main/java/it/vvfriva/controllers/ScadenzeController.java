package it.vvfriva.controllers;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import it.vvfriva.entity.Scadenze;
import it.vvfriva.managers.ScadenzeManager;
import it.vvfriva.managers.VigileCertificatiManager;
import it.vvfriva.models.JsonResponse;
import it.vvfriva.models.KeyValueColumn;
import it.vvfriva.models.ModelPrntAutorizzazioni;
import it.vvfriva.services.ScadenzeService;
import it.vvfriva.utils.CostantiVVF;
import it.vvfriva.utils.Utils;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Controller
@RequestMapping(path="/expiry")
public class ScadenzeController {
	
	@Autowired ScadenzeService scadenzeService;
	@Autowired ScadenzeManager scadenzeManager;
	@Autowired VigileCertificatiManager vigileCertificatiManager;
	
	 @Autowired
	 private ResourceLoader resourceLoader;
	/**
	 * api for save class scadenza
	 * @param scadenza
	 * @return
	 */
	@PostMapping(path = "/new")
	public @ResponseBody JsonResponse<Scadenze> save(@RequestBody Scadenze scadenza) {
		return scadenzeService.save(scadenza);
	}
	/**
	 * api for update class scadenza
	 * @param scadenza
	 * @return
	 */
	@PostMapping(path = "/update")
	public @ResponseBody JsonResponse<Scadenze> update(@RequestBody Scadenze scadenza) {
		return scadenzeService.update(scadenza);

	}
	/**
	 * api for delete class scadenza
	 * @param scadenza
	 * @return
	 */
	@GetMapping(path = "/{id}/delete")
	public @ResponseBody JsonResponse<Scadenze> delete(@PathVariable Integer id) {
		return scadenzeService.delete(id);

	}
	@GetMapping(path = "/cal/columns")
	public @ResponseBody JsonResponse<List<KeyValueColumn>> getColumnsTable(@RequestParam(required = false) String cmp) {
		return scadenzeService.getColumnsTable(cmp);

	}
	
	@PostMapping(path="/report") 
	public @ResponseBody void  report(HttpServletResponse response) throws Exception {
		
		JasperPrint jasperPrint = null;

		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition","attachment;filename=\"users.pdf\"");
		
		OutputStream out = response.getOutputStream();

		 String path = resourceLoader.getResource("classpath:rpt/VVF_Scadenze.jrxml").getURI().getPath();
		 JasperReport jasperReport;
		 Map<String, Object> parameters = new HashMap<String, Object>();
		 Date dateTo = Utils.dateAdd(new Date(), Calendar.MONTH, 6);
		 JsonResponse<List<Scadenze>> respScadenze = scadenzeService.listForReport(new Date(), 
				 Utils.endOfDay(dateTo),
				 Arrays.asList(CostantiVVF.AREA_AUTORIZZAZIONI, CostantiVVF.AREA_PATENTI_SERVIZIO));
		 
		 if (respScadenze != null && respScadenze.getSuccess() == true) {
			 
			 List<Scadenze> newScadenze = new ArrayList<Scadenze>();
			 if (!Utils.isEmptyList(respScadenze.getData())) {
				 newScadenze = respScadenze.getData().stream()
						 .sorted(Comparator.comparing((Scadenze sc) -> sc.getDescrVigile()))
						 .collect(Collectors.toList());

			 }

			try {
				jasperReport = JasperCompileManager.compileReport(path);
				jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JRBeanCollectionDataSource(newScadenze));
				JasperExportManager.exportReportToPdfStream(jasperPrint, out);

			} catch (JRException e) {
				e.printStackTrace();
			} finally {
				response.flushBuffer();
			}
		 }

	}
	
	@PostMapping(path="/report/certified") 
	public @ResponseBody void  reportAutorizzazioni(HttpServletResponse response) throws Exception {
		
		JasperPrint jasperPrint = null;

		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition","attachment;filename=\"users.pdf\"");
		
		OutputStream out = response.getOutputStream();

		 String path = resourceLoader.getResource("classpath:rpt/VVF_Autorizzazioni.jrxml").getURI().getPath();
		 JasperReport jasperReport;
		 Map<String, Object> parameters = new HashMap<String, Object>();
		 List<ModelPrntAutorizzazioni> listAutorizzazioni = vigileCertificatiManager.listForReport();
		 
		 parameters.put("listAutorizzazioni", listAutorizzazioni);
		
//		 List<Scadenze> newList = new ArrayList<Scadenze>();
//		 if (!Utils.isEmptyList(listScadenze)) {
//			 newList = listScadenze.stream()
//					 .sorted(Comparator.comparing((Scadenze sc) -> sc.getDescrVigile()))
//					 .collect(Collectors.toList());
//
//		 }

		try {
			jasperReport = JasperCompileManager.compileReport(path);
			jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JRBeanCollectionDataSource(listAutorizzazioni));
			JasperExportManager.exportReportToPdfStream(jasperPrint, out);

		} catch (JRException e) {
			e.printStackTrace();
		} finally {
			response.flushBuffer();
		}
	}
	
	
	
	/**
	 * api for delete class scadenza
	 * @param scadenza
	 * @return
	 */
	@GetMapping(path = "/cal")
	public @ResponseBody JsonResponse<String> cal() {
		return scadenzeService.cal();

	}
	
}
