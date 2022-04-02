package it.vvfriva.models;

public class ArticoliScadenzaSearch {

	private Integer articoloId;
	private boolean storico = false;

	/**
	 * @return the articoloId
	 */
	public Integer getArticoloId() {
		return articoloId;
	}

	/**
	 * @param articoloId the articoloId to set
	 */
	public void setArticoloId(Integer articoloId) {
		this.articoloId = articoloId;
	}

	/**
	 * @return the storico
	 */
	public boolean isStorico() {
		return storico;
	}

	/**
	 * @param storico the storico to set
	 */
	public void setStorico(boolean storico) {
		this.storico = storico;
	}
}
