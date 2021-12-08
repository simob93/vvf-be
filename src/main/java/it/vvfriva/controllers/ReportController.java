package it.vvfriva.controllers;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import it.vvfriva.entity.Dotazione;
import it.vvfriva.managers.DotazioneManager;
import it.vvfriva.models.ModelPrntDotazioneVigile;
import it.vvfriva.utils.ReportService;
import it.vvfriva.utils.Utils;
import net.sf.jasperreports.engine.JRException;
/**
 * 
 * @author simone
 *
 */
@Controller    
@RequestMapping("/rpt")
public class ReportController {
	
	@Autowired private DotazioneManager dotazioneVigileManger; 

	@PostMapping("/dotazione") 
	public @ResponseBody void  report(HttpServletResponse response, @RequestParam("idVigile") Integer idVigile) throws Exception {
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition","attachment;filename=\"dotazioneVigile.pdf\"");
		try {
			OutputStream out = response.getOutputStream();
			 Map<String, Object> parameters = new HashMap<String, Object>();
			 List<Dotazione> dotazioniVigile = dotazioneVigileManger.list(idVigile);
			 List<Object> listForReport = new ArrayList<Object>();
			 if (!Utils.isEmptyList(dotazioniVigile)) {
				 for(Dotazione dotazione: dotazioniVigile) {
						listForReport
								.add(new ModelPrntDotazioneVigile(dotazione.getDescrArticolo(), dotazione.getQuantita(),
										dotazione.getTaglia(), Utils.parseDate(dotazione.getDataConsegna()), dotazione.getNote()));
				 }
			 }
			 parameters.put("PAR_ListaDotazioni", listForReport);
			 ReportService.printReport("VVF_DotazioneVigile", listForReport, parameters, out, idVigile);
		} catch (JRException e) {
			e.printStackTrace();
		} finally {
			response.flushBuffer();
		}
	}
	
}
