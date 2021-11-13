package it.vvfriva.models;

import java.util.Date;

/**
 * 
 * @author simone
 *
 */
public class ModelNotification {
	private Integer idRif;
	private Integer type; //idArea
	private String typeFormatted;
	private String nominativo;
	private Integer idVigile;
	private Date dateExpiry;
	
	public ModelNotification() {
		
	}
		
	public ModelNotification(Integer idRif, Integer type, String nominativo, Integer idVigile, String typeFormatted) {
		this.idRif = idRif;
		this.type = type;
		this.nominativo = nominativo;
		this.idVigile = idVigile;
		this.typeFormatted = typeFormatted;
	}

	/**
	 * @return the idRif
	 */
	public Integer getIdRif() {
		return idRif;
	}
	/**
	 * @param idRif the idRif to set
	 */
	public void setIdRif(Integer idRif) {
		this.idRif = idRif;
	}
	/**
	 * @return the type
	 */
	public Integer getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * @return the nominativo
	 */
	public String getNominativo() {
		return nominativo;
	}
	/**
	 * @param nominativo the nominativo to set
	 */
	public void setNominativo(String nominativo) {
		this.nominativo = nominativo;
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
	 * @return the typeFormatted
	 */
	public String getTypeFormatted() {
		return typeFormatted;
	}

	/**
	 * @param typeFormatted the typeFormatted to set
	 */
	public void setTypeFormatted(String typeFormatted) {
		this.typeFormatted = typeFormatted;
	}

	/**
	 * @return the dateExpiry
	 */
	public Date getDateExpiry() {
		return dateExpiry;
	}

	/**
	 * @param dateExpiry the dateExpiry to set
	 */
	public void setDateExpiry(Date dateExpiry) {
		this.dateExpiry = dateExpiry;
	}
	
	
	
}

