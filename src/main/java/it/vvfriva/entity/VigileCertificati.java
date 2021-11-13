package it.vvfriva.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Formula;
/**
 * gestione certificati/ autorizzazioni 
 * @author simone
 *
 */
@Entity
@Table(name="vigile_certified")
public class VigileCertificati {
	
	public VigileCertificati() {
	
	}
	/**
	 * 
	 * @param id
	 * @param idVigile
	 * @param idPerson
	 * @param date
	 * @param dateExpiration
	 */
	public VigileCertificati(Integer id, Integer idVigile, Integer idPerson, Date date, String descrPerson) {
		this.id = id;
		this.idVigile = idVigile;
		this.idPerson = idPerson;
		this.date = date;
		this.descrPerson = descrPerson;
	}
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "id_vigile")
	private Integer idVigile;
	
	@Column(name = "id_person")
	private Integer idPerson;
	
	@Column(name = "date")
	private Date date;
	
	@Formula("(select p.name from person p where p.id = id_person)")
	private String descrPerson;
	
	/**
	 *  questo campo transient server per calcolare in 
	 *  automatico il record all'interno della tabella 
	 *  Scadenza.java
	 */
	@Transient
	private Date dateExpiration;

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

	public Integer getIdPerson() {
		return idPerson;
	}

	public void setIdPerson(Integer idPerson) {
		this.idPerson = idPerson;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getDescrPerson() {
		return descrPerson;
	}

	public void setDescrPerson(String descrPerson) {
		this.descrPerson = descrPerson;
	}

	/**
	 * @return the dateExipration
	 */
	public Date getDateExpiration() {
		return dateExpiration;
	}

	/**
	 * @param dateExipration the dateExipration to set
	 */
	public void setDateExpiration(Date dateExipration) {
		this.dateExpiration = dateExipration;
	}
}
