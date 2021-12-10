package it.vvfriva.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import it.vvfriva.interfaces.EntityInfo;

@Entity
@Table(name = "provincie")
public class Provincie implements Serializable, EntityInfo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1140093060602082172L;
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name="istat")
	private Integer istat;
	
	@Column(name="name")
	private String name;
	
	public Provincie() {}
	
	/**
	 * 
	 * @param istat
	 * @param name
	 */
	public Provincie(Integer istat, String name) {
		this.istat = istat;
		this.name = name;
	}

	/**
	 * @return the istat
	 */
	public Integer getIstat() {
		return istat;
	}

	/**
	 * @param istat the istat to set
	 */
	public void setIstat(Integer istat) {
		this.istat = istat;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
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
	
	
	
}
