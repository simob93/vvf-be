package it.vvfriva.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
/**
 * 
 * @author simone
 *
 */
@Entity
@Table(name = "articoli_categorie")
public class ArticoliCategorie {
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="id_articolo")
	private Integer idArticolo;
	
	@Column(name="id_categoria")
	private Integer idCategoria;	
	
	@Transient
	private Boolean eliminare; 
	
	public ArticoliCategorie() {
		
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
	 * @return the idCategoria
	 */
	public Integer getIdCategoria() {
		return idCategoria;
	}


	/**
	 * @param idCategoria the idCategoria to set
	 */
	public void setIdCategoria(Integer idCategoria) {
		this.idCategoria = idCategoria;
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
	
}
