package it.vvfriva.models;

import java.util.List;

public class TurnoRptTe {
	
	private String dal;
	private String al;
	private Boolean stampaFestivita;
	private Integer idSquadra;
	private String descrSquadra;
	private String descrCapoSquadra;
	private List<TurnoRpt> turni;
	private List<KeyValue> turniFestivi; 
	/**
	 * @return the dal
	 */
	public String getDal() {
		return dal;
	}
	/**
	 * @param dal the dal to set
	 */
	public void setDal(String dal) {
		this.dal = dal;
	}
	/**
	 * @return the al
	 */
	public String getAl() {
		return al;
	}
	/**
	 * @param al the al to set
	 */
	public void setAl(String al) {
		this.al = al;
	}
	/**
	 * @return the stampaFestivita
	 */
	public Boolean getStampaFestivita() {
		return stampaFestivita;
	}
	/**
	 * @param stampaFestivita the stampaFestivita to set
	 */
	public void setStampaFestivita(Boolean stampaFestivita) {
		this.stampaFestivita = stampaFestivita;
	}
	/**
	 * @return the idSquadra
	 */
	public Integer getIdSquadra() {
		return idSquadra;
	}
	/**
	 * @param idSquadra the idSquadra to set
	 */
	public void setIdSquadra(Integer idSquadra) {
		this.idSquadra = idSquadra;
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
	/**
	 * @return the descrCapoSquadra
	 */
	public String getDescrCapoSquadra() {
		return descrCapoSquadra;
	}
	/**
	 * @param descrCapoSquadra the descrCapoSquadra to set
	 */
	public void setDescrCapoSquadra(String descrCapoSquadra) {
		this.descrCapoSquadra = descrCapoSquadra;
	}
	/**
	 * @return the turni
	 */
	public List<TurnoRpt> getTurni() {
		return turni;
	}
	/**
	 * @param turni the turni to set
	 */
	public void setTurni(List<TurnoRpt> turni) {
		this.turni = turni;
	}
	/**
	 * @return the turniFestivi
	 */
	public List<KeyValue> getTurniFestivi() {
		return turniFestivi;
	}
	/**
	 * @param turniFestivi the turniFestivi to set
	 */
	public void setTurniFestivi(List<KeyValue> turniFestivi) {
		this.turniFestivi = turniFestivi;
	} 
}
