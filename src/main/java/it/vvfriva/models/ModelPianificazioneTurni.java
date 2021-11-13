package it.vvfriva.models;

import java.util.Date;

import it.vvfriva.enums.EnumsTurni;
import it.vvfriva.utils.Utils;
/**
 * 
 * @author simone
 *
 */
public class ModelPianificazioneTurni {
	
	private Integer idPianificazione;
	private Integer idEvento;
	private Integer idSquadra;
	private Integer idVigile;
	private Integer idServizio;
	private Integer idTurno;
	private Integer grado;
	private Integer idTe;
	private String letteraVigile;
	private String descrVigile;
	private boolean capoSquadra = false;
	private String descrSquadra;
	private String descrGrado;
	private String descrGiornoSettimana;
	private Boolean saltoTurno;
	private String codiceTelefono;
	private String note;
	private Date dal;
	private Date al;
	private Date data;
	
	public ModelPianificazioneTurni() {
		
	}
	
	/**
	 * @return the idPianificazione
	 */
	public Integer getIdPianificazione() {
		return idPianificazione;
	}
	/**
	 * @param idPianificazione the idPianificazione to set
	 */
	public void setIdPianificazione(Integer idPianificazione) {
		this.idPianificazione = idPianificazione;
	}
	/**
	 * @return the idEvento
	 */
	public Integer getIdEvento() {
		return idEvento;
	}
	/**
	 * @param idEvento the idEvento to set
	 */
	public void setIdEvento(Integer idEvento) {
		this.idEvento = idEvento;
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
	 * @return the idTurno
	 */
	public Integer getIdTurno() {
		return idTurno;
	}
	/**
	 * @param idTurno the idTurno to set
	 */
	public void setIdTurno(Integer idTurno) {
		this.idTurno = idTurno;
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
	 * @return the descrGrado
	 */
	public String getDescrGrado() {
		return this.descrGrado;
	}
	/**
	 * @return the descrGiornoSettimana
	 */
	public String getDescrGiornoSettimana() {
		if( Utils.geZero(idTurno)) {
			this.descrGiornoSettimana = EnumsTurni.getById(idTurno);
		}
		return this.descrGiornoSettimana;
	}
	/**
	 * @return the dal
	 */
	public Date getDal() {
		return dal;
	}
	/**
	 * @param dal the dal to set
	 */
	public void setDal(Date dal) {
		this.dal = dal;
	}
	/**
	 * @return the al
	 */
	public Date getAl() {
		return al;
	}
	/**
	 * @param al the al to set
	 */
	public void setAl(Date al) {
		this.al = al;
	}
	/**
	 * @return the data
	 */
	public Date getData() {
		return data;
	}
	/**
	 * @param data the data to set
	 */
	public void setData(Date data) {
		this.data = data;
	}

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
	 * @param codiceTelefono the codiceTelefono to set
	 */
	public void setCodiceTelefono(String codiceTelefono) {
		this.codiceTelefono = codiceTelefono;
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
	 * @return the grado
	 */
	public Integer getGrado() {
		return grado;
	}

	/**
	 * @param grado the grado to set
	 */
	public void setGrado(Integer grado) {
		this.grado = grado;
	}

	/**
	 * @param descrGrado the descrGrado to set
	 */
	public void setDescrGrado(String descrGrado) {
		this.descrGrado = descrGrado;
	}

	/**
	 * @param descrGiornoSettimana the descrGiornoSettimana to set
	 */
	public void setDescrGiornoSettimana(String descrGiornoSettimana) {
		this.descrGiornoSettimana = descrGiornoSettimana;
	}

	/**
	 * @return the saltoTurno
	 */
	public Boolean getSaltoTurno() {
		return saltoTurno;
	}

	/**
	 * @param saltoTurno the saltoTurno to set
	 */
	public void setSaltoTurno(Boolean saltoTurno) {
		this.saltoTurno = saltoTurno;
	}

	/**
	 * @return the group
	 */
	public String getPeriodo() {
		return Utils.parseDate(this.dal) + " " + Utils.parseDate(this.al);
	}

	/**
	 * @return the idTe
	 */
	public Integer getIdTe() {
		return idTe;
	}

	/**
	 * @param idTe the idTe to set
	 */
	public void setIdTe(Integer idTe) {
		this.idTe = idTe;
	}
	
	/**
	 * @return the capoSquadra
	 */
	public boolean isCapoSquadra() {
		return capoSquadra;
	}

	/**
	 * @param capoSquadra the capoSquadra to set
	 */
	public void setCapoSquadra(boolean capoSquadra) {
		this.capoSquadra = capoSquadra;
	}
}
