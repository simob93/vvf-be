package it.vvfriva.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import it.vvfriva.interfaces.EntityInfo;
/**
 * 
 * @author simone
 *
 */
@Table(name = "vigile_dotazione")
@Entity
public class Dotazione implements EntityInfo {
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="id_vigile")
	private Integer idVigile;
	
	@Column(name="id_articolo")
	private Integer idArticolo;
	
	@Column(name="data_consegna")
	private Date dataConsegna;
	
	@Column(name="quantita")
	private BigDecimal quantita;
	
	@Column(name="taglia")
	private String taglia;
	
	@Column(name="note")
	private String note;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_articolo", referencedColumnName="id", insertable = false, updatable = false)
	Articoli articolo;
	
	@Transient
	private String descrArticolo;
	
	public Dotazione() {
		
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
	 * @return the idVigile
	 */
	public Integer getIdVigile() {
		return idVigile;
	}

	/**
	 * @param idVigile the idVigile to set
	 */
	public void setIdVigile(Integer idVigile) {
		this.idVigile = idVigile;
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
	 * @return the qta
	 */
	public BigDecimal getQuantita() {
		return quantita;
	}

	/**
	 * @param qta the qta to set
	 */
	public void setQuantita(BigDecimal qta) {
		this.quantita = qta;
	}

	/**
	 * @return the note
	 */
	public String getNote() {
		return note;
	}

	/**
	 * @param note the note to set
	 */
	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * @return the dataConsegna
	 */
	public Date getDataConsegna() {
		return dataConsegna;
	}

	/**
	 * @param dataConsegna the dataConsegna to set
	 */
	public void setDataConsegna(Date dataConsegna) {
		this.dataConsegna = dataConsegna;
	}
	/**
	 * @return the taglia
	 */
	public String getTaglia() {
		return taglia;
	}

	/**
	 * @param taglia the taglia to set
	 */
	public void setTaglia(String taglia) {
		this.taglia = taglia;
	}

	/**
	 * @return the articolo
	 */
	public Articoli getArticolo() {
		return articolo;
	}

	/**
	 * @param articolo the articolo to set
	 */
	public void setArticolo(Articoli articolo) {
		this.articolo = articolo;
	}

	/**
	 * @return the descrArticolo
	 */
	public String getDescrArticolo() {
		return descrArticolo;
	}

	/**
	 * @param descrArticolo the descrArticolo to set
	 */
	public void setDescrArticolo(String descrArticolo) {
		this.descrArticolo = descrArticolo;
	}
	
}
