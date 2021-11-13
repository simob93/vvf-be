package it.vvfriva.models;

public class VigileModel {
	private Integer id;
	private Integer idAssenza;
	private Integer idServizio;
	private String firstName;
	private String lastName;
	private String lettera;
	
	public VigileModel() {}
	
	/**
	 * 
	 * @param firstName
	 * @param lastName
	 * 
	 */
	public VigileModel(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}
	

	/**
	 * @return the idAssenza
	 */
	public Integer getIdAssenza() {
		return idAssenza;
	}

	/**
	 * @param idAssenza the idAssenza to set
	 */
	public void setIdAssenza(Integer idAssenza) {
		this.idAssenza = idAssenza;
	}

	
	/**
	 * @return the lettera
	 */
	public String getLettera() {
		return lettera;
	}

	/**
	 * @param lettera the lettera to set
	 */
	public void setLettera(String lettera) {
		this.lettera = lettera;
	}

	
	public Integer getIdServizio() {
		return idServizio;
	}

	public void setIdServizio(Integer idServizio) {
		this.idServizio = idServizio;
	}
	public String getFirstName() {
		return firstName;
	}
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getNominativoVigile() {
		return this.firstName + " " + this.lastName;
	}

}
