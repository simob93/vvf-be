package it.vvfriva.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import it.vvfriva.interfaces.EntityInfo;
/**
 * 
 * @author simone
 *
 */
@Entity
@Table(name = "utenti_ruoli")
public class UtentiRuoli implements Serializable, EntityInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1085751844581254761L;
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="id_utente")
	private Integer idUtente;
	
	@Column(name="id_ruolo")
	private Integer idRuolo;
	

	public UtentiRuoli() {
		
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
	 * @return the idUtente
	 */
	public Integer getIdUtente() {
		return idUtente;
	}


	/**
	 * @param idUtente the idUtente to set
	 */
	public void setIdUtente(Integer idUtente) {
		this.idUtente = idUtente;
	}


	/**
	 * @return the idRuolo
	 */
	public Integer getIdRuolo() {
		return idRuolo;
	}


	/**
	 * @param idRuolo the idRuolo to set
	 */
	public void setIdRuolo(Integer idRuolo) {
		this.idRuolo = idRuolo;
	}
	
}
