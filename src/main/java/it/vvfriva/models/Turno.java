package it.vvfriva.models;

import java.util.Date;

/**
 * model turnario 
 * @author simone
 *
 */
public class Turno {
	private Integer idTurnoEvent;
	private Integer idVigile;
	private Integer idServizio;
	private Integer idSquadra;
	private Integer giornoSettimana;
	private String descrGiornoSettimana;
	private String letteraVigile;
	private String descrVigile;
	private Date dataTurno;
	private String descrGrado;
	private String codiceTelefono;
	private String noteServizio;
	private String descrSquadra; 
	private String periodo;
	private boolean saltoTurno = false;
	
	
	/**
	 * @return the descrSquadra
	 */
	public String getDescrSquadra() {
		return descrSquadra;
	}
	/**
	 * @param descrSquadra the descrSquadra to set
	 */
	public void setDescrSquadra(String descrSquadra) {
		this.descrSquadra = descrSquadra;
	}
	/**
	 * @return the codiceTelefono
	 */
	public String getCodiceTelefono() {
		return codiceTelefono;
	}
	/**
	 * @return the noteServizio
	 */
	public String getNoteServizio() {
		return noteServizio;
	}
	/**
	 * @param noteServizio the noteServizio to set
	 */
	public void setNoteServizio(String noteServizio) {
		this.noteServizio = noteServizio;
	}
	/**
	 * @param codiceTelefono the codiceTelefono to set
	 */
	public void setCodiceTelefono(String codiceTelefono) {
		this.codiceTelefono = codiceTelefono;
	}
	/**
	 * @return the idVigile
	 */
	public Integer getIdVigile() {
		return idVigile;
	}
	/**
	 * @return the descrGrado
	 */
	public String getDescrGrado() {
		return descrGrado;
	}
	/**
	 * @param descrGrado the descrGrado to set
	 */
	public void setDescrGrado(String descrGrado) {
		this.descrGrado = descrGrado;
	}
	/**
	 * @param idVigile the idVigile to set
	 */
	public void setIdVigile(Integer idVigile) {
		this.idVigile = idVigile;
	}
	/**
	 * @return the idServizio
	 */
	public Integer getIdServizio() {
		return idServizio;
	}
	/**
	 * @param idServizio the idServizio to set
	 */
	public void setIdServizio(Integer idServizio) {
		this.idServizio = idServizio;
	}
	/**
	 * @return the giornoSettimana
	 */
	public Integer getGiornoSettimana() {
		return giornoSettimana;
	}
	/**
	 * @param giornoSettimana the giornoSettimana to set
	 */
	public void setGiornoSettimana(Integer giornoSettimana) {
		this.giornoSettimana = giornoSettimana;
	}
	/**
	 * @return the descrGiornoSettimana
	 */
	public String getDescrGiornoSettimana() {
		return descrGiornoSettimana;
	}
	/**
	 * @param descrGiornoSettimana the descrGiornoSettimana to set
	 */
	public void setDescrGiornoSettimana(String descrGiornoSettimana) {
		this.descrGiornoSettimana = descrGiornoSettimana;
	}
	/**
	 * @return the letteraVigile
	 */
	public String getLetteraVigile() {
		return letteraVigile;
	}
	/**
	 * @param letteraVigile the letteraVigile to set
	 */
	public void setLetteraVigile(String letteraVigile) {
		this.letteraVigile = letteraVigile;
	}
	/**
	 * @return the descrVigile
	 */
	public String getDescrVigile() {
		return descrVigile;
	}
	/**
	 * @param descrVigile the descrVigile to set
	 */
	public void setDescrVigile(String descrVigile) {
		this.descrVigile = descrVigile;
	}
	/**
	 * @return the dataTurno
	 */
	public Date getDataTurno() {
		return dataTurno;
	}
	/**
	 * @param dataTurno the dataTurno to set
	 */
	public void setDataTurno(Date dataTurno) {
		this.dataTurno = dataTurno;
	}
	
	/**
	 * @return the periodo
	 */
	public String getPeriodo() {
		return periodo;
	}
	/**
	 * @param periodo the periodo to set
	 */
	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}
	/**
	 * @return the saltoTurno
	 */
	public boolean isSaltoTurno() {
		return saltoTurno;
	}
	/**
	 * @param saltoTurno the saltoTurno to set
	 */
	public void setSaltoTurno(boolean saltoTurno) {
		this.saltoTurno = saltoTurno;
	}
	/**
	 * @return the idSquadra
	 */
	public Integer getIdSquadra() {
		return idSquadra;
	}
	/**
	 * @param idSquadra the idSquadra to set
	 */
	public void setIdSquadra(Integer idSquadra) {
		this.idSquadra = idSquadra;
	}
	/**
	 * @return the idTurnoEvent
	 */
	public Integer getIdTurnoEvent() {
		return idTurnoEvent;
	}
	/**
	 * @param idTurnoEvent the idTurnoEvent to set
	 */
	public void setIdTurnoEvent(Integer idTurnoEvent) {
		this.idTurnoEvent = idTurnoEvent;
	}
}
