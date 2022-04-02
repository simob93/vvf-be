package it.vvfriva.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import it.vvfriva.enums.EnumTrueFalse;
import it.vvfriva.enums.TipoScadenzaArticolo;
import it.vvfriva.interfaces.EntityInfo;

/**
 * 
 * @author simone
 *
 */
@Entity
@Table(name = "articoli_scadenza")
public class ArticoliScadenza implements EntityInfo {

	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "id_articolo")
	private Integer idArticolo;
	
	@Column(name = "data_rinnovo")
	private Date dataRinnovo;

	@Column(name = "data_scadenza")
	private Date dataScadenza;
	
	@Column(name = "note")
	private String note;

	@Column(name = "tipo_scadenza")
	@Enumerated(EnumType.STRING)
	private TipoScadenzaArticolo tipoScadenza;
	
	@Column(name = "effettuato")
	@Enumerated(EnumType.STRING)
	private EnumTrueFalse effettuato;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_articolo", referencedColumnName="id", insertable = false, updatable = false)
	private Articoli articolo;
	

	@Override
	public Integer getId() {
		return id;
	}

	public void setId( Integer id ) {
		this.id = id;
	}

	public Integer getIdArticolo() {
		return idArticolo;
	}

	public void setIdArticolo( Integer idArticolo ) {
		this.idArticolo = idArticolo;
	}

	public Date getDataScadenza() {
		return dataScadenza;
	}

	public void setDataScadenza( Date dataScadenza ) {
		this.dataScadenza = dataScadenza;
	}

	public Date getDataRinnovo() {
		return dataRinnovo;
	}

	public void setDataRinnovo( Date dataRinnovo ) {
		this.dataRinnovo = dataRinnovo;
	}

	public TipoScadenzaArticolo getTipoScadenza() {
		return tipoScadenza;
	}

	public void setTipoScadenza( TipoScadenzaArticolo tipoScadenza ) {
		this.tipoScadenza = tipoScadenza;
	}

	public ArticoliScadenza() {
		
	}

	/**
	 * @return the articolo
	 */
	public Articoli getArticolo() {
		return articolo;
	}

	/**
	 * @param articolo the articolo to set
	 */
	public void setArticolo(Articoli articolo) {
		this.articolo = articolo;
	}

	/**
	 * @return the effettuato
	 */
	public EnumTrueFalse getEffettuato() {
		return effettuato;
	}

	/**
	 * @param effettuato the effettuato to set
	 */
	public void setEffettuato(EnumTrueFalse effettuato) {
		this.effettuato = effettuato;
	}

	/**
	 * @return the note
	 */
	public String getNote() {
		return note;
	}

	/**
	 * @param note the note to set
	 */
	public void setNote(String note) {
		this.note = note;
	}

}
