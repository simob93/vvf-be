package it.vvfriva.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import it.vvfriva.interfaces.EntityInfo;
/**
 * 
 * @author simone
 *
 */
@Entity
@Table(name = "turni_pianifica_evento")
public class TurniPianificaEvento implements Serializable, EntityInfo {
	
	private static final long serialVersionUID = 4708490867771786894L;
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "data")
	private Date data;
	
	@Column(name = "id_pianificazione")
	private Integer idPianificazione;
	
	@Column(name = "id_turno")
	private Integer idTurno;
	
	@Column(name = "id_vigile")
	private Integer idVigile;
	
	public TurniPianificaEvento() {
	}
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
	/**
	 * @return the idTurno
	 */
	public Integer getIdTurno() {
		return idTurno;
	}
	/**
	 * @param idTurno the idTurno to set
	 */
	public void setIdTurno(Integer idTurno) {
		this.idTurno = idTurno;
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
