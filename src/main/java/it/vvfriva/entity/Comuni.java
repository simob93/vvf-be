package it.vvfriva.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Comuni implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2402340945312878979L;

	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="istat")
	private Integer istat;
	
	@Column(name="istat_provincia")
	private Integer istatProvicina;
	
	@Column(name="name")
	private String name;
	
	public Comuni() {}
	
	public Comuni(Integer istat, String name) {
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

	/**
	 * @return the istatProvicina
	 */
	public Integer getIstatProvicina() {
		return istatProvicina;
	}

	/**
	 * @param istatProvicina the istatProvicina to set
	 */
	public void setIstatProvicina(Integer istatProvicina) {
		this.istatProvicina = istatProvicina;
	}
	
	
}
