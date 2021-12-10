package it.vvfriva.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;

import it.vvfriva.interfaces.EntityInfo;
/**
 * 
 * @author simone
 *
 */
@Entity
@Table(name = "assenze")
public class Assenze implements EntityInfo {
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="id_vigile")
	private Integer idVigile;
	
	@Column(name="dal")
	private Date dal;
	
	@Column(name="al")
	private Date al;
	

	@Column(name="motivo")
	private Integer motivo;
	
	@Column(name="note")
	private String note;
	
	@Formula("( select per.name from person per where per.id = motivo limit 1)")
	private String motivoFormatted;
	
	public Assenze() {
		
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
	public Assenze(Integer id, Integer idVigile, Date dal, Date al, Date dalle, Date alle) {
		this.id = id;
		this.idVigile = idVigile;
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
	public Integer getMotivo() {
		return motivo;
	}

	/**
	 * @param motivo the motivo to set
	 */
	public void setMotivo(Integer motivo) {
		this.motivo = motivo;
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
	public String getMotivoFormatted() {
		return motivoFormatted;
	}

	/**
	 * @param motivoFormatted the motivoFormatted to set
	 */
	public void setMotivoFormatted(String motivoFormatted) {
		this.motivoFormatted = motivoFormatted;
	}
}
