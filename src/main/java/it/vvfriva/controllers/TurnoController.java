package it.vvfriva.controllers;

import it.vvfriva.managers.PersonManager;
import it.vvfriva.models.*;
import it.vvfriva.services.TurnoEventoService;
import it.vvfriva.utils.ReportService;
import it.vvfriva.utils.Utils;
import net.sf.jasperreports.engine.JRException;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(path="/turni")
public class TurnoController {
	
	@Autowired TurnoEventoService turnoService; 
	@Autowired PersonManager personManager;

	
	/**
	 * calcolo del turnario 
	 * @param dal
	 * @param al
	 * @return
	 */
	@GetMapping(path="/calc")
	public @ResponseBody JsonResponse<List<ModelPianificazioneTurni>> calcolaTurnario(
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dal , 
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate al) { 
		return turnoService.calcolaTurnario(dal != null ? dal.toDate() : null, al != null ? al.toDate() : null);
	}
	/**
	 * stampa del turnario
	 * @param response
	 * @param dal
	 * @param al
	 * @throws Exception
	 */
	@PostMapping(path="/report") 
	public @ResponseBody void  report(HttpServletResponse response, 
			@RequestParam("dal") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dal,
			@RequestParam("al") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate al) throws Exception {
		

		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition","attachment;filename=\"turni.pdf\"");
		
		OutputStream out = response.getOutputStream();
		 
		 Map<String, Object> parameters = new HashMap<String, Object>();
	
		try {
			 JsonResponse<List<ModelPianificazioneTurni>> respTurni = turnoService.calcolaTurnario(dal != null ? dal.toDate() : null, al != null ? al.toDate() : null);
			 if (respTurni != null && respTurni.getSuccess() == true) {
				 
				 List<ModelPianificazioneTurni> listTurni = respTurni.getData();
				 TurnoRptTe turnoTe = new TurnoRptTe();
				 String prevSquadra = null;
				 
				 List<TurnoRpt> dettaglioTurni = new ArrayList<TurnoRpt>();
				 List<Object> listForReport = new ArrayList<Object>();
				 List<KeyValue> turniFestivi = new ArrayList<KeyValue>();
				 HashMap<Long, String> mappaFestivita = new HashMap<Long, String>();
				 
				 for(ModelPianificazioneTurni turno : listTurni) {
					 
					 
					 
					 if (!Utils.stringEguals(turno.getDescrSquadra(), prevSquadra)) {
						 
						 turnoTe = new TurnoRptTe();
						 dettaglioTurni = new ArrayList<TurnoRpt>();
						 turniFestivi = new ArrayList<KeyValue>();
						 
						 turnoTe.setDal(Utils.parseDate(turno.getDal()));
						 turnoTe.setAl(Utils.parseDate(turno.getAl()));
						 turnoTe.setDescrSquadra(turno.getDescrSquadra());
						 
						 turnoTe.setTurni(dettaglioTurni);
						 turnoTe.setTurniFestivi(turniFestivi);
						 listForReport.add(turnoTe);
					 }
					 if (turno.isCapoSquadra()) {
						 turnoTe.setDescrCapoSquadra(turno.getDescrVigile());
					 }
					
					 if (Utils.isFestivo(turno.getData())) {
						 if (!mappaFestivita.containsKey(turno.getData().getTime())) {
							 mappaFestivita.put(turno.getData().getTime(), null);
							 KeyValue turnoFestivo = new KeyValue(null, Utils.parseDate(turno.getData()));
							 turnoTe.getTurniFestivi().add(turnoFestivo);
						 }
					 }
					 dettaglioTurni.add(new TurnoRpt(turno));
					 prevSquadra = turno.getDescrSquadra();
				 }
				 ReportService.printReport("VVF_TurniTe", listForReport, parameters, out);
			 
			 }

		} catch (JRException e) {
			e.printStackTrace();
		} finally {
			response.flushBuffer();
		}
	}

}
