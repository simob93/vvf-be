package it.vvfriva.utils;

import java.util.regex.Pattern;

import net.sf.jasperreports.crosstabs.JRCrosstab;
import net.sf.jasperreports.engine.JRBreak;
import net.sf.jasperreports.engine.JRChart;
import net.sf.jasperreports.engine.JRComponentElement;
import net.sf.jasperreports.engine.JRElementGroup;
import net.sf.jasperreports.engine.JREllipse;
import net.sf.jasperreports.engine.JRFrame;
import net.sf.jasperreports.engine.JRGenericElement;
import net.sf.jasperreports.engine.JRImage;
import net.sf.jasperreports.engine.JRLine;
import net.sf.jasperreports.engine.JRRectangle;
import net.sf.jasperreports.engine.JRStaticText;
import net.sf.jasperreports.engine.JRSubreport;
import net.sf.jasperreports.engine.JRTextField;
import net.sf.jasperreports.engine.JRVisitor;

public class VVFJRVisitor implements JRVisitor  {
	
	private String path;
	public VVFJRVisitor(String path) {
		this.path = path;
	}

	@Override
	public void visitBreak(JRBreak arg0) {
		
	}

	@Override
	public void visitChart(JRChart arg0) {
		
	}

	@Override
	public void visitComponentElement(JRComponentElement arg0) {
		
	}

	@Override
	public void visitCrosstab(JRCrosstab arg0) {
		
	}

	@Override
	public void visitElementGroup(JRElementGroup arg0) {
		
	}

	@Override
	public void visitEllipse(JREllipse arg0) {
		
	}

	@Override
	public void visitFrame(JRFrame arg0) {
		
	}

	@Override
	public void visitGenericElement(JRGenericElement arg0) {
		
	}

	@Override
	public void visitImage(JRImage arg0) {
		
	}

	@Override
	public void visitLine(JRLine arg0) {
		
	}

	@Override
	public void visitRectangle(JRRectangle arg0) {
		
	}

	@Override
	public void visitStaticText(JRStaticText arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visitSubreport(JRSubreport subreport) {
		try{
			String subReportName = subreport.getExpression().getText();
			Pattern pattern = Pattern.compile(".jasper");
			if (pattern.matcher(subReportName).find()){
				subReportName = subReportName.replaceAll(".jasper", "").trim();
			}
			if (subReportName.contains("/")) {
				int pos = subReportName.indexOf("/") + 1;
				subReportName = subReportName.substring(pos, subReportName.length() -1).trim();
			}
			ReportService.compileReport(this.getPath(), subReportName);
			//sostituisco l'eventuale estensione compilata con quella da compilare ..
			
			//Esegue ricorsivamente la compilazione del sub-report
		} catch(Throwable e) {
			e.printStackTrace();
		}
	}
	@Override
	public void visitTextField(JRTextField arg0) {
		
	}

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}
	
}
