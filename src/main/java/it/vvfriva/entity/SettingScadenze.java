package it.vvfriva.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import it.vvfriva.interfaces.EntityInfo;
import it.vvfriva.utils.CostantiVVF;
import it.vvfriva.utils.Utils;

/**
 * 
 * @author simone
 *
 */
@Entity
@Table(name="setting_scadenze")
public class SettingScadenze implements EntityInfo {
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="id_person")
	private Integer idPerson;
	
	@Column(name="expiration_every")
	private Integer expirationEvery;
	
	@Column(name="exipration_type")
	private String expirationType;
	
	@Transient
	private String expirationTypeFormatted;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdPerson() {
		return idPerson;
	}

	public void setIdPerson(Integer idPerson) {
		this.idPerson = idPerson;
	}

	public Integer getExpirationEvery() {
		
		return expirationEvery;
	}

	public void setExpirationEvery(Integer expirationEvery) {
		this.expirationEvery = expirationEvery;
	}

	public String getExpirationType() {
		return expirationType;
	}

	public void setExpirationType(String expirationType) {
		this.expirationType = expirationType;
	}

	public String getExpirationTypeFormatted() {
		if (Utils.stringCompareTo(expirationType, CostantiVVF.ANNUALE) == 0) {
			return "Annuale";
		} else if (Utils.stringCompareTo(expirationType, CostantiVVF.MENSILE) == 0) {
			return "Mensile";
		}
		return null;
	}

	public void setExpirationTypeFormatted(String expirationTypeFormatted) {
		this.expirationTypeFormatted = expirationTypeFormatted;
	}
	
}
