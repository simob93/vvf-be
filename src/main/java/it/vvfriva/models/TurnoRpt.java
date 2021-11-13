package it.vvfriva.models;

import java.util.Date;

import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;

import it.vvfriva.utils.Utils;

/**
 * model turnario per report  
 * @author simone
 *
 */
public class TurnoRpt  {
	
	private String descrGiornoSettimana;
	private String descrVigile;
	private String descrGrado;
	private String codiceTelefono;
	private String noteServizio;
	private boolean saltoTurno = false;
	private String dataTurnoFormatted;
	private Boolean provaSelettiva;
	
	public TurnoRpt(ModelPianificazioneTurni turno) {
		setDescrVigile(turno.getDescrVigile());
		setDescrGiornoSettimana(turno.getDescrGiornoSettimana());
		setCodiceTelefono(turno.getCodiceTelefono());
		setNoteServizio(turno.getNote());
		if (turno.getSaltoTurno() != null && !turno.getSaltoTurno().booleanValue()) {
			setDataTurnoFormatted(Utils.parseDate(turno.getData()));
		} 
		turno.setSaltoTurno(turno.getSaltoTurno());
		setDescrGrado(turno.getDescrGrado());
		if (Utils.isValidDate(turno.getData())) {
			Date primoGGMese = Utils.getFirstDayOfMonth(turno.getData());
			LocalDate day = new LocalDate(primoGGMese);
			Date dataSelettiva = Utils.getFriday(day.toDate());
			if (day.getDayOfWeek() > DateTimeConstants.FRIDAY) {
				dataSelettiva = Utils.getFriday(day.plusWeeks(1).toDate());
			}
			if (Utils.isValidDate(dataSelettiva) && 
					dataSelettiva.compareTo(turno.getData()) == 0) {
				setProvaSelettiva(true);
			}
		}
	}

	/**
	 * @return the dataTurnoFormatted
	 */
	public String getDataTurnoFormatted() {
		return dataTurnoFormatted;
	}
	/**
	 * @param dataTurnoFormatted the dataTurnoFormatted to set
	 */
	public void setDataTurnoFormatted(String dataTurnoFormatted) {
		this.dataTurnoFormatted = dataTurnoFormatted;
	}
	/**
	 * @return the provaSelettiva
	 */
	public Boolean getProvaSelettiva() {
		return provaSelettiva;
	}

	/**
	 * @param provaSelettiva the provaSelettiva to set
	 */
	public void setProvaSelettiva(Boolean provaSelettiva) {
		this.provaSelettiva = provaSelettiva;
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
		return descrGrado;
	}

	/**
	 * @param descrGrado the descrGrado to set
	 */
	public void setDescrGrado(String descrGrado) {
		this.descrGrado = descrGrado;
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

}
	