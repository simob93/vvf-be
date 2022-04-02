package it.vvfriva.models;

import java.util.Date;

import it.vvfriva.enums.AlertScadenzaArticoli;
import it.vvfriva.utils.Utils;

/**
 *  Author: Simone
 */
public class ArticoliScadenzeDto {

	private Integer scadenzaId;
    private Integer articoloId;
    private Integer tipoScadenza;
    private String descrArticolo;
    private String note;
    private String descrTipoScadenza;
    private Date dataScadenza;

    public ArticoliScadenzeDto(Integer scadenzaId, Integer idArticolo, String descrArticolo, Date dataScadenza, String note, String descrTipoScadenza, Integer tipoScadenza) {
        this.articoloId = idArticolo;
        this.descrArticolo = descrArticolo;
        this.dataScadenza = dataScadenza;
        this.scadenzaId = scadenzaId;
        this.note = note;
        this.descrTipoScadenza = descrTipoScadenza;
        this.tipoScadenza = tipoScadenza;
    }

    public ArticoliScadenzeDto() {
    }

    

    public String getDescrArticolo() {
        return descrArticolo;
    }

    public void setDescrArticolo( String descrArticolo ) {
        this.descrArticolo = descrArticolo;
    }

    public Date getDataScadenza() {
        return dataScadenza;
    }

    public void setDataScadenza( Date dataScadenza ) {
        this.dataScadenza = dataScadenza;
    }

    public Integer getAlertScadenza() {
        if (dataScadenza == null) {
            return null;
        }
        int warningScadenza = AlertScadenzaArticoli.VALIDO.getTipoAlert(); //valido
        Date oggi = Utils.startOfDay( new Date());
        if ( Utils.dateCompareTo(dataScadenza, oggi) <= 0) {
            warningScadenza = AlertScadenzaArticoli.SCADUTO.getTipoAlert(); // scaduto
        } else if (Utils.dateCompareTo(Utils.addMonths(dataScadenza, -3), oggi) <= 0) {
            warningScadenza= AlertScadenzaArticoli.IN_SCADENZA.getTipoAlert();; // in scadenza
        }
        return warningScadenza;
    }


	/**
	 * @return the scadenzaId
	 */
	public Integer getScadenzaId() {
		return scadenzaId;
	}

	/**
	 * @param scadenzaId the scadenzaId to set
	 */
	public void setScadenzaId(Integer scadenzaId) {
		this.scadenzaId = scadenzaId;
	}

	/**
	 * @return the articoloId
	 */
	public Integer getArticoloId() {
		return articoloId;
	}

	/**
	 * @param articoloId the articoloId to set
	 */
	public void setArticoloId(Integer articoloId) {
		this.articoloId = articoloId;
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

	/**
	 * @return the descrTipoScadenza
	 */
	public String getDescrTipoScadenza() {
		return descrTipoScadenza;
	}

	/**
	 * @param descrTipoScadenza the descrTipoScadenza to set
	 */
	public void setDescrTipoScadenza(String descrTipoScadenza) {
		this.descrTipoScadenza = descrTipoScadenza;
	}

	/**
	 * @return the tipoScadenza
	 */
	public Integer getTipoScadenza() {
		return tipoScadenza;
	}

	/**
	 * @param tipoScadenza the tipoScadenza to set
	 */
	public void setTipoScadenza(Integer tipoScadenza) {
		this.tipoScadenza = tipoScadenza;
	}


}
