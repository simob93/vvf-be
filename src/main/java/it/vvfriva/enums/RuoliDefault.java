package it.vvfriva.enums;

public enum RuoliDefault {
	
	ADMIN(1, "ADMIN"),
	USER(2, "USER");
	
	private Integer id;
	private String descrizione;
	/**
	 * 
	 * @param id
	 * @param descrizione
	 */
	RuoliDefault(Integer id, String descrizione) {
		this.id = id;
		this.descrizione = descrizione;
	}
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
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
