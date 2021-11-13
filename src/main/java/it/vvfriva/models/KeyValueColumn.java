package it.vvfriva.models;
/**
 * 
 * @author simone
 *
 */
public class KeyValueColumn extends KeyValue {

	public KeyValueColumn(Integer codice, String valore, String extra) {
		super(codice, valore, extra);
	}
		
	private Integer idArea;
	private String name;
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
	 * @return the idArea
	 */
	public Integer getIdArea() {
		return idArea;
	}
	/**
	 * @param idArea the idArea to set
	 */
	public void setIdArea(Integer idArea) {
		this.idArea = idArea;
	}
	

}
