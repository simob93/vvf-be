package it.vvfriva.models;

public class KeyValueTipiScadenza extends KeyValue {
	
	private Integer idVigile;
	private Integer idPerson;

	public KeyValueTipiScadenza(Integer codice, String valore, String extra) {
		super(codice, valore, extra);
	}

	public KeyValueTipiScadenza(Integer codice, String valore, String extra, Integer idVigile, Integer idPerson) {
		super(codice, valore, extra);
		this.idVigile = idVigile;
		this.idPerson = idPerson;
	}

	public Integer getIdVigile() {
		return idVigile;
	}

	public void setIdVigile(Integer idVigile) {
		this.idVigile = idVigile;
	}

	public Integer getIdPerson() {
		return idPerson;
	}

	public void setIdPerson(Integer idPerson) {
		this.idPerson = idPerson;
	}

}
