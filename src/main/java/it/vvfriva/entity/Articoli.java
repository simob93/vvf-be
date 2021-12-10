package it.vvfriva.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import it.vvfriva.interfaces.EntityInfo;
/**
 * 
 * @author simone
 *
 */
@Entity
@Table(name = "articoli")
public class Articoli implements EntityInfo {
	
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
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY)
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumn(name = "id_articolo",insertable = false, updatable = false)
	private List<ArticoliDepositi> depositi;

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY)
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumn(name = "id_articolo", insertable = false, updatable = false)
	private List<ArticoliCategorie> categorie;
	
	
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

	/**
	 * @return the depositi
	 */
	public List<ArticoliDepositi> getDepositi() {
		return depositi;
	}

	/**
	 * @param depositi the depositi to set
	 */
	public void setDepositi(List<ArticoliDepositi> depositi) {
		this.depositi = depositi;
	}

	/**
	 * @return the categorie
	 */
	public List<ArticoliCategorie> getCategorie() {
		return categorie;
	}

	/**
	 * @param categorie the categorie to set
	 */
	public void setCategorie(List<ArticoliCategorie> categorie) {
		this.categorie = categorie;
	}
}
