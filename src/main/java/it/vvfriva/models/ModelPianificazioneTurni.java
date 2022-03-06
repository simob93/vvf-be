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

	private String letteraVigile;
	private String descrVigile;
	private boolean capoSquadra = false;
	private String descrSquadra;
	private String descrGrado;
	private String descrGiornoSettimana;
	private Boolean saltoTurno = false;
	private String codiceTelefono;
	private String note;
	private Date dal;
	private Date al;
	private Date data;
	
	public ModelPianificazioneTurni() {
		
	}

	public String getLetteraVigile() {
		return letteraVigile;
	}

	public void setLetteraVigile(String letteraVigile) {
		this.letteraVigile = letteraVigile;
	}

	public String getDescrVigile() {
		return descrVigile;
	}

	public void setDescrVigile(String descrVigile) {
		this.descrVigile = descrVigile;
	}

	public boolean isCapoSquadra() {
		return capoSquadra;
	}

	public void setCapoSquadra(boolean capoSquadra) {
		this.capoSquadra = capoSquadra;
	}

	public String getDescrSquadra() {
		return descrSquadra;
	}

	public void setDescrSquadra(String descrSquadra) {
		this.descrSquadra = descrSquadra;
	}

	public String getDescrGrado() {
		return descrGrado;
	}

	public void setDescrGrado(String descrGrado) {
		this.descrGrado = descrGrado;
	}

	public String getDescrGiornoSettimana() {
		return descrGiornoSettimana;
	}

	public void setDescrGiornoSettimana(String descrGiornoSettimana) {
		this.descrGiornoSettimana = descrGiornoSettimana;
	}

	public Boolean getSaltoTurno() {
		return saltoTurno;
	}

	public void setSaltoTurno(Boolean saltoTurno) {
		this.saltoTurno = saltoTurno;
	}

	public String getCodiceTelefono() {
		return codiceTelefono;
	}

	public void setCodiceTelefono(String codiceTelefono) {
		this.codiceTelefono = codiceTelefono;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Date getDal() {
		return dal;
	}

	public void setDal(Date dal) {
		this.dal = dal;
	}

	public Date getAl() {
		return al;
	}

	public void setAl(Date al) {
		this.al = al;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}
}
