package it.vvfriva.models;

import java.util.Date;

/**
 *
 */
public class ArticoliScadenzaInsertDto {
    private Integer id;
    private Integer articoloId;
    private Integer tipoScadenza;
    private Date dataRinnovo;
    private Date dataScadenza;

    public ArticoliScadenzaInsertDto() {
    	
    }

    /**
     *
     * @param id
     * @param articoloId
     * @param dataRinnovo
     * @param dataScadenza
     */
    public ArticoliScadenzaInsertDto( Integer id, Integer articoloId, Date dataRinnovo, Date dataScadenza, Integer tipoScadenza) {
        this.id = id;
        this.articoloId = articoloId;
        this.dataRinnovo = dataRinnovo;
        this.dataScadenza = dataScadenza;
        this.tipoScadenza = tipoScadenza;
    }

    public Integer getId() {
        return id;
    }

    public void setId( Integer id ) {
        this.id = id;
    }

    public int getArticoloId() {
        return articoloId;
    }

    public void setArticoloId( Integer articoloId ) {
        this.articoloId = articoloId;
    }

    public Date getDataRinnovo() {
        return dataRinnovo;
    }

    public void setDataRinnovo( Date dataRinnovo ) {
        this.dataRinnovo = dataRinnovo;
    }

    public Date getDataScadenza() {
        return dataScadenza;
    }

    public void setDataScadenza( Date dataScadenza ) {
        this.dataScadenza = dataScadenza;
    }

    public int getTipoScadenza() {
        return tipoScadenza;
    }

    public void setTipoScadenza( Integer tipoScadenza ) {
        this.tipoScadenza = tipoScadenza;
    }

}
