package it.vvfriva.utils;

import java.io.OutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import it.vvfriva.entity.Vigile;
import it.vvfriva.managers.VigileManager;
import it.vvfriva.models.IntestazioneRpt;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRElementsVisitor;
import net.sf.jasperreports.engine.util.JRSaver;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

/**
 * classe statica per utilizzo stampa
 * 
 * @author simone
 *
 */
@Service
public class ReportService {

	private static VigileManager vigileManager;

	@Autowired
	private VigileManager _vigileManager;

	@PostConstruct
	private void initStaticDao() {
		vigileManager = this._vigileManager;
	}

	/**
	 * 
	 * @param jrxmlDirectory
	 * @param jrxmlFile
	 * @return
	 * @throws JRException
	 */
	public static JasperReport compileReport(String jrxmlDirectory, String jrxmlFile) throws JRException {

		JasperDesign jasperDesign = JRXmlLoader.load(jrxmlDirectory + "/" + jrxmlFile + ".jrxml");
		String compiledFileName = jrxmlDirectory + "/" + jrxmlFile + ".jasper";
		JasperReport report = JasperCompileManager.compileReport(jasperDesign);
		JRSaver.saveObject(report, compiledFileName);
		JRElementsVisitor.visitReport(report, new VVFJRVisitor(jrxmlDirectory));
		return report;

	}

	/**
	 * 
	 * @param jrxmlFile
	 * @param listForReport
	 * @param parameters
	 * @param out
	 * @return
	 * @throws Exception
	 */
	public static byte[] printReport(String jrxmlFile, List<Object> listForReport, Map<String, Object> parameters,
			OutputStream out) throws Exception {
		return printReport(jrxmlFile, listForReport, parameters, out, null);
	}

	/**
	 * 
	 * @param jrxmlFile
	 * @param listForReport
	 * @param parameters
	 * @param out
	 * @return
	 * @throws Exception
	 */
	public static byte[] printReport(String jrxmlFile, List<Object> listForReport, Map<String, Object> parameters,
			OutputStream out, Integer idVigile) throws Exception {
		try {

			String jrxmlDirectory = ResourceUtils.getFile("classpath:rpt").getPath();
			jrxmlDirectory = jrxmlDirectory.replace("\\", "/");
			if (parameters == null) {
				parameters = new HashMap<String, Object>();

			}
			IntestazioneRpt intestazione = null;
			if (Utils.isValidId(idVigile)) {
				Vigile vigile = vigileManager.getObjById(idVigile);
				if (vigile != null) {
					intestazione = new IntestazioneRpt(vigile);
				}
				parameters.put("PAR_Intestazione", Arrays.asList(intestazione));
			}
			parameters.put(CostantiVVF.PAR_REPORT_DIR, jrxmlDirectory);
			JRDataSource dataSource = new JRBeanCollectionDataSource(listForReport);
			JasperReport report = compileReport(jrxmlDirectory, jrxmlFile);
			JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, dataSource);
			JasperExportManager.exportReportToPdfStream(jasperPrint, out);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Ci sono stati dei problemi durante la stampa del report: " + e.getMessage());
		}
		return null;
	}
}
