package it.vvfriva.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;

import com.fasterxml.jackson.annotation.JsonFormat;

import it.vvfriva.interfaces.EntityInfo;
/**
 * 
 * @author simone
 *
 */
@Entity
@Table(name ="serv")
public class Servizio implements EntityInfo {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="date_start")
	private Date dateStart;
	
	@Column(name="date_end")
	private Date dateEnd;
	
	@Column(name="letter")
	private String letter;
	
	@Column(name="id_team")
	private Integer idTeam;
	
	@Column(name="id_vigile")
	private Integer idVigile;
	
	@Column(name="note")
	private String note;
	
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

	@Formula("(select p.name from person p where p.id = id_team limit 1)")
	private String teamFormatted;

	@Column(name="created_on")
	@JsonFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
	private Date createdOn = new Date();
	/**
	 *  ultimo grado per il servizio visualizzato
	 */
	@Formula("(select gr.grado from gradi gr where gr.id_servizio = id order by gr.dal desc limit 1 )")
	private Integer grado;
	
	@Formula("(select per.name from person per where per.id = (select gr.grado from gradi gr where gr.id_servizio = id order by gr.dal desc limit 1) limit 1 )")
	private String gradoFormatted;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDateStart() {
		return dateStart;
	}

	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}

	public Date getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}

	public String getLetter() {
		return letter;
	}

	public void setLetter(String letter) {
		this.letter = letter;
	}

	public Integer getIdTeam() {
		return idTeam;
	}

	public void setIdTeam(Integer idTeam) {
		this.idTeam = idTeam;
	}

	public Integer getIdVigile() {
		return idVigile;
	}

	public void setIdVigile(Integer idVigile) {
		this.idVigile = idVigile;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	
	
	public String getTeamFormatted() {
		return teamFormatted;
	}

	public void setTeamFormatted(String teamFormatted) {
		this.teamFormatted = teamFormatted;
	}

	public Integer getGrado() {
		return grado;
	}

	public void setGrado(Integer grado) {
		this.grado = grado;
	}

	public String getGradoFormatted() {
		return gradoFormatted;
	}

	public void setGradoFormatted(String gradoFormatted) {
		this.gradoFormatted = gradoFormatted;
	}
}	
