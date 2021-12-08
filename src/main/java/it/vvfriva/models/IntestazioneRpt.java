package it.vvfriva.models;

import it.vvfriva.entity.Vigile;
import it.vvfriva.utils.Utils;
/**
 * 
 * @author simone
 *
 */
public class IntestazioneRpt {
	private String nome;
	private String cognome;
	private String dataDiNascita;
	private String indrizzo;
	private String email;
	private String telefono;
	
	/**
	 * 
	 * @param vigile
	 */
	public IntestazioneRpt(Vigile vigile) {
		setNome(vigile.getFirstName());
		setCognome(vigile.getLastName());
		setDataDiNascita(Utils.parseDate(vigile.getBirthday()));
		setEmail(vigile.getMail());
		setTelefono(vigile.getPhone());
		String via = "";
		if (!Utils.isEmptyString(vigile.getAddress())) {
			via += vigile.getAddress();
			if (Utils.isValidId(vigile.getPostalCode())) {
				via += ", " + vigile.getPostalCode();
			}
			if (vigile.getProvincia() != null) {
				via += "," + vigile.getProvincia().getName(); 
			}
			if (vigile.getComune() != null) {
				via += "," + vigile.getComune().getName();
			}
		}
		setIndrizzo(via);
	}
	
	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}
	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}
	/**
	 * @return the cognome
	 */
	public String getCognome() {
		return cognome;
	}
	/**
	 * @param cognome the cognome to set
	 */
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	/**
	 * @return the dataDiNascita
	 */
	public String getDataDiNascita() {
		return dataDiNascita;
	}
	/**
	 * @param dataDiNascita the dataDiNascita to set
	 */
	public void setDataDiNascita(String dataDiNascita) {
		this.dataDiNascita = dataDiNascita;
	}
	/**
	 * @return the via
	 */
	public String getIndrizzo() {
		return indrizzo;
	}
	/**
	 * @param via the via to set
	 */
	public void setIndrizzo(String via) {
		this.indrizzo = via;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the telfono
	 */
	public String getTelefono() {
		return telefono;
	}
	/**
	 * @param telfono the telfono to set
	 */
	public void setTelefono(String telfono) {
		this.telefono = telfono;
	}
	
	
}
