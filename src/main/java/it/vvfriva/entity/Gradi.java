package it.vvfriva.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.Formula;
/**
 * 
 * @author simone
 *
 */
@Entity
public class Gradi {
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="id_servizio")
	private Integer idServizio;
	
	@Column(name="dal")
	private Date dal;
	
	@Column(name="al")
	private Date al;
	
	@Column(name="grado")
	private Integer grado;
	
	@Column(name="note")
	private String note;
	
	@Formula("( select per.name from person per where per.id = grado limit 1)")
	private String gradoFormatted;
	
	public Gradi() {
		
	}
	
	/**
	 * 
	 * @param id
	 * @param idVigile
	 * @param dal
	 * @param al
	 * @param dalle
	 * @param alle
	 */
	public Gradi(Integer id, Date dal, Date al, Date dalle, Date alle) {
		this.id = id;
		this.dal = dal;
		this.al = al;
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
	 * @return the motivo
	 */
	public Integer getGrado() {
		return grado;
	}

	/**
	 * @param motivo the motivo to set
	 */
	public void setGrado(Integer grado) {
		this.grado = grado;
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
	 * @return the motivoFormatted
	 */
	public String getGradoFormatted() {
		return gradoFormatted;
	}

	/**
	 * @param motivoFormatted the motivoFormatted to set
	 */
	public void setMotivoFormatted(String motivoFormatted) {
		this.gradoFormatted = motivoFormatted;
	}

	public Integer getIdServizio() {
		return idServizio;
	}

	public void setIdServizio(Integer idServizio) {
		this.idServizio = idServizio;
	}

	public void setGradoFormatted(String gradoFormatted) {
		this.gradoFormatted = gradoFormatted;
	}
}
