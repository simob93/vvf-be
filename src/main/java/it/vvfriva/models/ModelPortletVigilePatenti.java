package it.vvfriva.models;

import java.util.Date;

/**
 * 
 * @author simone
 *
 */
public class ModelPortletVigilePatenti {
	
	private Integer idVigileLicense;
	private String licenseFormatted;
	private Date date;
	/**
	 * 
	 * @param idVigile
	 * @param licenseFormatted
	 * @param date
	 */
	public ModelPortletVigilePatenti(Integer idVigile, String licenseFormatted, Date date) {
		this.idVigileLicense = idVigile;
		this.licenseFormatted = licenseFormatted;
		this.date = date;
	}
	
	public String getLicenseFormatted() {
		return licenseFormatted;
	}
	public void setLicenseFormatted(String licenseFormatted) {
		this.licenseFormatted = licenseFormatted;
	}

	public Integer getIdVigilePatente() {
		return idVigileLicense;
	}

	public void setIdVigilePatente(Integer idVigilePatente) {
		this.idVigileLicense = idVigilePatente;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
