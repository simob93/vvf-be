package it.vvfriva.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import it.vvfriva.utils.Utils;

@Entity
public class Person {
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="id_area")
	private Integer idArea;
	
	@Column(name="name")
	private String name;
	
	@Column(name="flag")
	private String flag;
	
	@Column(name="extra")
	private String extra;
	
	@Column(name="enabled_expiry")
	private Integer enabledExpiry;
	
		
//	@OneToMany(cascade=CascadeType.ALL)
//	@JoinColumn(name="id_person")
//	private Set<SettingScadenze> scadenze = new HashSet<>();

	@Transient
	private SettingScadenze scadenza;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdArea() {
		return idArea;
	}

	public void setIdArea(Integer idArea) {
		this.idArea = idArea;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public SettingScadenze getScadenza() {
		return scadenza;
	}

	public void setScadenza(SettingScadenze scadenza) {
		this.scadenza = scadenza;
	}

	public boolean getEnabledExpiry() {
		return Utils.isTrueOrFalse(enabledExpiry);
	}

	public void setEnabledExpiry(boolean enabledExpiry) {
		this.enabledExpiry = Utils.getIntegerBoolValue(enabledExpiry);
	}

	/**
	 * @return the extra
	 */
	public String getExtra() {
		return extra;
	}

	/**
	 * @param extra the extra to set
	 */
	public void setExtra(String extra) {
		this.extra = extra;
	}

	/**
	 * @return the flag
	 */
	public String getFlag() {
		return flag;
	}

	/**
	 * @param flag the flag to set
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

}
