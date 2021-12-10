package it.vvfriva.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import it.vvfriva.interfaces.EntityInfo;
/**
 * 
 * @author simone
 *
 */
@Entity
@Table(name = "articoli_depositi")
public class ArticoliDepositi implements EntityInfo {
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="id_articolo")
	private Integer idArticolo;
	
	@Column(name="id_deposito")
	private Integer idDeposito;	
	
	@Transient
	private Boolean eliminare; 
	
	public ArticoliDepositi() {
		
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
	 * @return the idArticolo
	 */
	public Integer getIdArticolo() {
		return idArticolo;
	}


	/**
	 * @param idArticolo the idArticolo to set
	 */
	public void setIdArticolo(Integer idArticolo) {
		this.idArticolo = idArticolo;
	}

	/**
	 * @return the eliminare
	 */
	public Boolean getEliminare() {
		return eliminare;
	}

	/**
	 * @param eliminare the eliminare to set
	 */
	public void setEliminare(Boolean eliminare) {
		this.eliminare = eliminare;
	}

	/**
	 * @return the idDeposito
	 */
	public Integer getIdDeposito() {
		return idDeposito;
	}

	/**
	 * @param idDeposito the idDeposito to set
	 */
	public void setIdDeposito(Integer idDeposito) {
		this.idDeposito = idDeposito;
	}
	
}
