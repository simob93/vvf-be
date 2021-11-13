package it.vvfriva.models;

/**
 * 
 * @author simone
 *
 * @param <T>
 */
public class KeyValueInt extends KeyValue {

	public KeyValueInt(Integer codice, String valore, String extra) {
		super(codice, valore, extra);
	}
	
	public KeyValueInt(Integer codice, String valore, String extra, Integer val1) {
		super(codice, valore, extra);
		this.val1 = val1;
	}
	
	public KeyValueInt(Integer codice, String valore, Integer val1) {
		super(codice, valore, null);
		this.val1 = val1;
	}
	
	public KeyValueInt(Integer codice, Integer val1, Integer val2) {
		super(codice, null, null);
		this.val1 = val1;
		this.val2 = val2;
	}
	private Integer val1;
	private Integer val2;
	/**
	 * @return the val1
	 */
	public Integer getVal1() {
		return val1;
	}
	/**
	 * @param val1 the val1 to set
	 */
	public void setVal1(Integer val1) {
		this.val1 = val1;
	}
	/**
	 * @return the val2
	 */
	public Integer getVal2() {
		return val2;
	}
	/**
	 * @param val2 the val2 to set
	 */
	public void setVal2(Integer val2) {
		this.val2 = val2;
	}
	
	
}
