package it.vvfriva.models;

import java.math.BigDecimal;
import java.util.Date;

import it.vvfriva.entity.Dotazione;
/**
 * 
 * @author simone
 *
 */
public class DotazioneDto {
	private Integer id;
	private Integer idVigile;
	private Integer idArticolo;
	private Date dataConsegna;
	private BigDecimal quantita;
	private String taglia;
	private String note;
	private String descrArticolo;
	
	/**
	 * 
	 * @param dotazione
	 */
	public DotazioneDto(Dotazione dotazione) {
		setDataConsegna(dotazione.getDataConsegna());
    	setDescrArticolo(dotazione.getArticolo().getDescrizione());
    	setId(dotazione.getId());
    	setIdArticolo(dotazione.getIdArticolo());
    	setIdVigile(dotazione.getIdVigile());
    	setNote(dotazione.getNote());
    	setQuantita(dotazione.getQuantita());
    	setTaglia(dotazione.getTaglia());
	}
	
	public DotazioneDto(Integer id, Integer idVigile, Integer idArticolo, Date dataConsegna, BigDecimal quantita,
			String taglia, String note, String descrArticolo) {
		this.id = id;
		this.idVigile = idVigile;
		this.idArticolo = idArticolo;
		this.dataConsegna = dataConsegna;
		this.quantita = quantita;
		this.taglia = taglia;
		this.note = note;
		this.descrArticolo = descrArticolo;
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
	 * @return the quantita
	 */
	public BigDecimal getQuantita() {
		return quantita;
	}
	/**
	 * @param quantita the quantita to set
	 */
	public void setQuantita(BigDecimal quantita) {
		this.quantita = quantita;
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
