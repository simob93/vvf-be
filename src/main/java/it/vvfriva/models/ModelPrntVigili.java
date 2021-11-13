package it.vvfriva.models;

public class ModelPrntVigili {
	
	private String nominativo;
	private String dataNascitaFormatted;
	private String indirizzo;
	private String citta;
	private String telefono;
	private String codiceTel;
	
	
	/**
	 * 
	 * @param nominativo
	 * @param dataNascitaFormatted
	 * @param indirizzo
	 * @param citta
	 * @param telefono
	 * @param codiceTel
	 */
	public ModelPrntVigili(String nominativo, String dataNascitaFormatted, String indirizzo, String citta,
			String telefono, String codiceTel) {
		this.nominativo = nominativo;
		this.dataNascitaFormatted = dataNascitaFormatted;
		this.indirizzo = indirizzo;
		this.citta = citta;
		this.telefono = telefono;
		this.codiceTel = codiceTel;
	}
	/**
	 * @return the nominativo
	 */
	public String getNominativo() {
		return nominativo;
	}
	/**
	 * @param nominativo the nominativo to set
	 */
	public void setNominativo(String nominativo) {
		this.nominativo = nominativo;
	}
	/**
	 * @return the dataNascitaFormatted
	 */
	public String getDataNascitaFormatted() {
		return dataNascitaFormatted;
	}
	/**
	 * @param dataNascitaFormatted the dataNascitaFormatted to set
	 */
	public void setDataNascitaFormatted(String dataNascitaFormatted) {
		this.dataNascitaFormatted = dataNascitaFormatted;
	}
	/**
	 * @return the indirizzo
	 */
	public String getIndirizzo() {
		return indirizzo;
	}
	/**
	 * @param indirizzo the indirizzo to set
	 */
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	/**
	 * @return the telefono
	 */
	public String getTelefono() {
		return telefono;
	}
	/**
	 * @param telefono the telefono to set
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	/**
	 * @return the codiceTel
	 */
	public String getCodiceTel() {
		return codiceTel;
	}
	/**
	 * @param codiceTel the codiceTel to set
	 */
	public void setCodiceTel(String codiceTel) {
		this.codiceTel = codiceTel;
	}
	/**
	 * @return the citta
	 */
	public String getCitta() {
		return citta;
	}
	/**
	 * @param citta the citta to set
	 */
	public void setCitta(String citta) {
		this.citta = citta;
	}

}
