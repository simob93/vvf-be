package it.vvfriva.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import it.vvfriva.interfaces.EntityInfo;

@Entity
@Table(name = "turni_pianifica_salto_turno")
public class TurniPianificaSaltoTurno  implements Serializable, EntityInfo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4700041704981610023L;
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "id_pianificazione")
	private Integer idPianificazione;
	
	@Column(name = "id_vigile")
	private Integer idVigile;
	
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the idPianificazione
	 */
	public Integer getIdPianificazione() {
		return idPianificazione;
	}

	/**
	 * @param idPianificazione the idPianificazione to set
	 */
	public void setIdPianificazione(Integer idPianificazione) {
		this.idPianificazione = idPianificazione;
	}

	/**
	 * @return the id_vigile
	 */
	public Integer getIdVigile() {
		return idVigile;
	}

	/**
	 * @param id_vigile the id_vigile to set
	 */
	public void setIdVigile(Integer id_vigile) {
		this.idVigile = id_vigile;
	}
}
