package it.vvfriva.models;

import java.util.Date;

public class KeyValuePeriodo extends KeyValue {

	
	private Date dal;
	private Date al;
	
	public KeyValuePeriodo(Integer codice, String valore, String extra) {
		super(codice, valore, extra);
	}
	
	public KeyValuePeriodo(Integer codice, String valore, String extra, Date dal, Date al) {
		super(codice, valore, extra);
		this.dal = dal;
		this.al = al;
	}
	
	public KeyValuePeriodo(Date dal, Date al) {
		super(null, null, null);
		this.dal = dal;
		this.al = al;
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
}
