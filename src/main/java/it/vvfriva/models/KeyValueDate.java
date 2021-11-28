package it.vvfriva.models;

import java.util.Date;

/**
 * 
 * @author simone
 *
 */
public class KeyValueDate extends KeyValue {

	private Date data; 
	
	/**
	 * 
	 * @param codice
	 * @param valore
	 * @param extra
	 */
	public KeyValueDate(Integer codice, String valore, String extra) {
		super(codice, valore, extra);
	}
	/**
	 * 
	 * @param codice
	 * @param valore
	 * @param extra
	 * @param data
	 */
	public KeyValueDate(Integer codice, String valore, String extra, Date data) {
		super(codice, valore, extra);
		this.data = data;
	}

	/**
	 * @return the data
	 */
	public Date getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(Date data) {
		this.data = data;
	}
	
	
}
