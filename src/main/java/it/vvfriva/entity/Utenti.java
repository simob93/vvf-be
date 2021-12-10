package it.vvfriva.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
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
@Table(name = "utenti")
public class Utenti implements Serializable, EntityInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1085751844581254761L;
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="nome")
	private String nome;
	
	@Column(name="cognome")
	private String cognome;
	
	@Column(name="email")
	private String email;
	
	@Column(name="username")
	private String username;
	
	@Column(name="pwd")
	private String pwd;
	
	@Column(name="abilitato")
	private Boolean abilitato;
	
	@Column(name="primo_accesso")
	private Boolean primoAcesso;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@Fetch(FetchMode.SELECT)
	@JoinColumn(name = "id_utente", insertable = true, updatable = true)
	@NotFound(action = NotFoundAction.IGNORE)
	public List<UtentiRuoli> ruolo;
		
	public Utenti() {
		
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
	 * @return the name
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param name the name to set
	 */
	public void setNome(String name) {
		this.nome = name;
	}

	/**
	 * @return the cognome
	 */
	public String getCognome() {
		return cognome;
	}

	/**
	 * @param cognome the cognome to set
	 */
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the pwd
	 */
	@JsonIgnore
	public String getPwd() {
		return pwd;
	}

	/**
	 * @param pwd the pwd to set
	 */
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	/**
	 * @return the abilitato
	 */
	public Boolean getAbilitato() {
		return abilitato;
	}

	/**
	 * @param abilitato the abilitato to set
	 */
	public void setAbilitato(Boolean abilitato) {
		this.abilitato = abilitato;
	}

	/**
	 * @return the primoAcesso
	 */
	public Boolean getPrimoAcesso() {
		return primoAcesso;
	}

	/**
	 * @param primoAcesso the primoAcesso to set
	 */
	public void setPrimoAcesso(Boolean primoAcesso) {
		this.primoAcesso = primoAcesso;
	}


	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}


	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}


	/**
	 * @return the ruolo
	 */
	public List<UtentiRuoli> getRuolo() {
		return ruolo;
	}


	/**
	 * @param ruolo the ruolo to set
	 */
	public void setRuolo(List<UtentiRuoli> ruolo) {
		this.ruolo = ruolo;
	}
}
