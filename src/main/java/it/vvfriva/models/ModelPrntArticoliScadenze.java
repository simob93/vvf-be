package it.vvfriva.models;

import java.util.Date;

import it.vvfriva.utils.Utils;

public class ModelPrntArticoliScadenze {
	public String descrArticolo; 
	public String dataScadenza;
	public String note;
	
	/**
	 * 
	 * @param descrArticolo
	 * @param dataRinnovo
	 * @param dataScadenza
	 * @param note
	 */
	public ModelPrntArticoliScadenze(String descrArticolo, Date dataScadenza, String note) {
		this.descrArticolo = descrArticolo;
		this.dataScadenza = Utils.parseDate(dataScadenza);
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
	 * @return the dataScadenza
	 */
	public String getDataScadenza() {
		return dataScadenza;
	}
	/**
	 * @param dataScadenza the dataScadenza to set
	 */
	public void setDataScadenza(String dataScadenza) {
		this.dataScadenza = dataScadenza;
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
