package it.vvfriva.models;

import java.util.Date;
/**
 * 
 * @author simone
 *
 */
public class ModelDotazionePortlet {
	private String descrArticolo;
	private Date dataUltimaConsegna;
	/**
	 * 
	 * @param descrArticolo
	 * @param taglia
	 * @param dataUltimaConsegna
	 */
	public ModelDotazionePortlet(String descrArticolo, Date dataUltimaConsegna) {
		this.descrArticolo = descrArticolo;
		this.dataUltimaConsegna = dataUltimaConsegna;
	}
	/**
	 * @return the descrArticolo
	 */
	public String getDescrArticolo() {
		return descrArticolo;
	}
	/**
	 * @param descrArticolo the descrArticolo to set
	 */
	public void setDescrArticolo(String descrArticolo) {
		this.descrArticolo = descrArticolo;
	}
	/**
	 * @return the dataUltimaConsegna
	 */
	public Date getDataUltimaConsegna() {
		return dataUltimaConsegna;
	}
	/**
	 * @param dataUltimaConsegna the dataUltimaConsegna to set
	 */
	public void setDataUltimaConsegna(Date dataUltimaConsegna) {
		this.dataUltimaConsegna = dataUltimaConsegna;
	}
	
}
