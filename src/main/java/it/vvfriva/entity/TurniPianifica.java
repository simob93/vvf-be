package it.vvfriva.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;

import it.vvfriva.interfaces.EntityInfo;

@Entity
@Table(name = "turni_pianifica")
public class TurniPianifica  implements Serializable, EntityInfo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4700041704981610023L;
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "dal")
	private Date dal;
	
	@Column(name = "al")
	private Date al;
	
	@Column(name = "id_squadra")
	private Integer idSquadra;
	
	
	@Formula("(select p.name from person p where p.id = id_squadra)")
	private String descrSquadra;
	

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
	 * @return the id_squadra
	 */
	public Integer getIdSquadra() {
		return idSquadra;
	}

	/**
	 * @param id_squadra the id_squadra to set
	 */
	public void setIdSquadra(Integer id_squadra) {
		this.idSquadra = id_squadra;
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
}
