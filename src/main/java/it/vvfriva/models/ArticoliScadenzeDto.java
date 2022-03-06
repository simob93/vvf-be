package it.vvfriva.models;

import java.util.Date;

import it.vvfriva.enums.AlertScadenzaArticoli;
import it.vvfriva.utils.Utils;

/**
 *  Author: Simone
 */
public class ArticoliScadenzeDto {

	private Integer scadenzaId;
    private Integer idArticolo;
    private String descrArticolo;
    private Date dataScadenza;
    private Date dataRinnovo;

    public ArticoliScadenzeDto(Integer scadenzaId, Integer idArticolo, String descrArticolo, Date dataScadenza, Date dataRinnovo) {
        this.idArticolo = idArticolo;
        this.descrArticolo = descrArticolo;
        this.dataScadenza = dataScadenza;
        this.dataRinnovo = dataRinnovo;
        this.scadenzaId = scadenzaId;
    }

    public ArticoliScadenzeDto() {
    }

    public Integer getIdArticolo() {
        return idArticolo;
    }

    public void setIdArticolo( Integer idArticolo ) {
        this.idArticolo = idArticolo;
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
	 * @return the dataRinnovo
	 */
	public Date getDataRinnovo() {
		return dataRinnovo;
	}

	/**
	 * @param dataRinnovo the dataRinnovo to set
	 */
	public void setDataRinnovo(Date dataRinnovo) {
		this.dataRinnovo = dataRinnovo;
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


}
