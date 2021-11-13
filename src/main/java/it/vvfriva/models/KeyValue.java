package it.vvfriva.models;

/**
 * 
 * @author simone
 *
 * @param <T>
 */
public class KeyValue {

	private Integer codice;
	private String valore;
	private String extra;

	public KeyValue(Integer codice, String valore, String extra) {
		this.codice = codice;
		this.valore = valore;
		this.extra = extra;
	}
	
	public KeyValue(Integer codice, String valore) {
		this.codice = codice;
		this.valore = valore;
	}

	public Integer getCodice() {
		return codice;
	}

	public void setCodice(Integer codice) {
		this.codice = codice;
	}

	public String getValore() {
		return valore;
	}

	public void setValore(String valore) {
		this.valore = valore;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}
}
