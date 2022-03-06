package it.vvfriva.models;

import java.util.Date;

/**
 * Author: Simone
 */
public class ArticoliScadenzaRinnovoInput {
	
	private Date dataScadenza;
	private Integer tipoScadenza;
	
	public ArticoliScadenzaRinnovoInput() {
		
	}

	public Date getDataScadenza() {
		return dataScadenza;
	}

	public void setDataScadenza(Date dataScadenza) {
		this.dataScadenza = dataScadenza;
	}

	/**
	 * @return the tipoScadenza
	 */
	public Integer getTipoScadenza() {
		return tipoScadenza;
	}

	/**
	 * @param tipoScadenza the tipoScadenza to set
	 */
	public void setTipoScadenza(Integer tipoScadenza) {
		this.tipoScadenza = tipoScadenza;
	}
}
