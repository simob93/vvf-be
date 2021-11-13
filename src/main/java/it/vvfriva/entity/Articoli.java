package it.vvfriva.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
/**
 * 
 * @author simone
 *
 */
@Entity
public class Articoli {
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="descrizione")
	private String descrizione;
	
	@Column(name="codice")
	private String codice;
	
	@Column(name="abilita_scadenza")
	private Boolean abilitaScadenza;
	
	@Column(name="abilita_taglia")
	private Boolean abilitaTaglia;
	
	public Articoli() {
		
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

	/**
	 * @return the codice
	 */
	public String getCodice() {
		return codice;
	}

	/**
	 * @param codice the codice to set
	 */
	public void setCodice(String codice) {
		this.codice = codice;
	}

	/**
	 * @return the abilitaScadenza
	 */
	public Boolean getAbilitaScadenza() {
		return abilitaScadenza;
	}

	/**
	 * @param abilitaScadenza the abilitaScadenza to set
	 */
	public void setAbilitaScadenza(Boolean abilitaScadenza) {
		this.abilitaScadenza = abilitaScadenza;
	}

	/**
	 * @return the abilitaTaglie
	 */
	public Boolean getAbilitaTaglia() {
		return abilitaTaglia;
	}

	/**
	 * @param abilitaTaglie the abilitaTaglie to set
	 */
	public void setAbilitaTaglia(Boolean abilitaTaglie) {
		this.abilitaTaglia = abilitaTaglie;
	}
}
