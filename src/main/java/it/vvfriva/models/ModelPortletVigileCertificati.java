package it.vvfriva.models;

import java.util.Date;
/**
 * 
 * @author simone
 *
 */
public class ModelPortletVigileCertificati {
	
	private Integer idVigileCertificato;
	private String certifiedFormatted;
	private Date date;
	
	public ModelPortletVigileCertificati(Integer idVigileCertificato, String certifiedFormatted, Date date) {
		this.idVigileCertificato = idVigileCertificato;
		this.certifiedFormatted = certifiedFormatted;
		this.date = date;
	}
	public Integer getIdVigileCertificato() {
		return idVigileCertificato;
	}
	public void setIdVigileCertificato(Integer idVigileCertificato) {
		this.idVigileCertificato = idVigileCertificato;
	}
	public String getCertifiedFormatted() {
		return certifiedFormatted;
	}
	public void setCertifiedFormatted(String certifiedFormatted) {
		this.certifiedFormatted = certifiedFormatted;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}

}
