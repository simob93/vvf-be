package it.vvfriva.models;

import java.math.BigDecimal;
/**
 * 
 * @author simone
 *
 */
public class ModelPrntDotazioneVigile {
	private String descrArticolo;
	private BigDecimal quantita;
	private String taglia;
	private String dataConsegna;
	private String note;
	/**
	 * 
	 * @param descrArticolo
	 * @param quantita
	 * @param taglia
	 * @param dataConsegna
	 * @param note
	 */
	public ModelPrntDotazioneVigile(String descrArticolo, BigDecimal quantita, String taglia, String dataConsegna,
			String note) {
		this.descrArticolo = descrArticolo;
		this.quantita = quantita;
		this.taglia = taglia;
		this.dataConsegna = dataConsegna;
		this.note = note;
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
	 * @return the quantita
	 */
	public BigDecimal getQuantita() {
		return quantita;
	}
	/**
	 * @param quantita the quantita to set
	 */
	public void setQuantita(BigDecimal quantita) {
		this.quantita = quantita;
	}
	/**
	 * @return the taglia
	 */
	public String getTaglia() {
		return taglia;
	}
	/**
	 * @param taglia the taglia to set
	 */
	public void setTaglia(String taglia) {
		this.taglia = taglia;
	}
	/**
	 * @return the dataConsegna
	 */
	public String getDataConsegna() {
		return dataConsegna;
	}
	/**
	 * @param dataConsegna the dataConsegna to set
	 */
	public void setDataConsegna(String dataConsegna) {
		this.dataConsegna = dataConsegna;
	}
	/**
	 * @return the note
	 */
	public String getNote() {
		return note;
	}
	/**
	 * @param note the note to set
	 */
	public void setNote(String note) {
		this.note = note;
	}
	
}
