package it.vvfriva.models;

public class ArticoliSearch {
	
	private Integer depositoId;
	private Integer categoriaId;
	private String descrizione;
	private boolean conGestScadenza = false;
	
	public ArticoliSearch() {
		
	}

	/**
	 * @return the depositoId
	 */
	public Integer getDepositoId() {
		return depositoId;
	}
	/**
	 * @param depositoId the depositoId to set
	 */
	public void setDepositoId(Integer depositoId) {
		this.depositoId = depositoId;
	}
	/**
	 * @return the categoriaId
	 */
	public Integer getCategoriaId() {
		return categoriaId;
	}
	/**
	 * @param categoriaId the categoriaId to set
	 */
	public void setCategoriaId(Integer categoriaId) {
		this.categoriaId = categoriaId;
	}

	/**
	 * @return the conGestScadenza
	 */
	public boolean isConGestScadenza() {
		return conGestScadenza;
	}

	/**
	 * @param conGestScadenza the conGestScadenza to set
	 */
	public void setConGestScadenza(boolean conGestScadenza) {
		this.conGestScadenza = conGestScadenza;
	}

	/**
	 * @return the descrizione
	 */
	public String getDescrizione() {
		return descrizione;
	}

	/**
	 * @param descrizione the descrizione to set
	 */
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
}
