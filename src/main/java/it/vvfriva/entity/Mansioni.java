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
public class Mansioni {

	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="id_vigile")
	private Integer idVigile;
	
	@Column(name="dal")
	private Date dal;
	
	@Column(name="al")
	private Date al;
	
	@Column(name="tipo")
	private Integer tipo; 
	

	@Column(name="note")
	private String note;
	
	@Formula("( select per.name from person per where per.id = tipo  limit 1)")
	private String tipoFormatted;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdVigile() {
		return idVigile;
	}

	public void setIdVigile(Integer idVigile) {
		this.idVigile = idVigile;
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

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
	public String getTipoFormatted() {
		return tipoFormatted;
	}

	public void setTipoFormatted(String tipoFormatted) {
		this.tipoFormatted = tipoFormatted;
	}

}
