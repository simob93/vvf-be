package it.vvfriva.utils;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.ResourceUtils;

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
 * @author simone
 *
 */
public class ReportService {
	/**
	 * 
	 * @param jrxmlDirectory
	 * @param jrxmlFile
	 * @return
	 * @throws JRException
	 */
	public static JasperReport compileReport(String jrxmlDirectory, String jrxmlFile) throws JRException {
		
		JasperDesign jasperDesign = JRXmlLoader.load(jrxmlDirectory + "/" + jrxmlFile + ".jrxml");
		String compiledFileName = jrxmlDirectory +"/"+ jrxmlFile + ".jasper";
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
	public static byte[] printReport(String jrxmlFile, List<Object> listForReport, Map<String, Object> parameters, OutputStream out) throws Exception {
		try {
			
			String jrxmlDirectory = ResourceUtils.getFile("classpath:rpt").getPath();
			jrxmlDirectory = jrxmlDirectory.replace("\\", "/");
			if (parameters == null) {
				parameters = new HashMap<String, Object>();
				
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
