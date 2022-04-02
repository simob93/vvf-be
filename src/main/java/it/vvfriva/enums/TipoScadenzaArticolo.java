package it.vvfriva.enums;

import it.vvfriva.utils.Messages;
import it.vvfriva.utils.Utils;

public enum TipoScadenzaArticolo {
    CONTROLLO(1, "articoli.scadenza.tipo.1"),
    ESTINZIONE(2, "articoli.scadenza.tipo.2");

    private int tipoScadenzaId;
    private String descrizione;

    TipoScadenzaArticolo(int tipoScadenzaId, String descrizione) {
        this.tipoScadenzaId = tipoScadenzaId;
        this.descrizione = descrizione;
    }

    public int getTipoScadenzaId() {
        return tipoScadenzaId;
    }

    public void setTipoScadenzaId( int tipoScadenzaId ) {
        this.tipoScadenzaId = tipoScadenzaId;
    }

    public String getDescrizione() {
        return  Messages.getMessage(descrizione);
    }

    public void setDescrizione( String descrizione ) {
        this.descrizione = descrizione;
    }
    
    public static TipoScadenzaArticolo getById(Integer id) {
    	TipoScadenzaArticolo result = null;
    	for(TipoScadenzaArticolo tipo : TipoScadenzaArticolo.values()) {
    		if (Utils.integerEquals(tipo.getTipoScadenzaId(), id)) {
    			result = tipo;
    			break;
    		}
    	}
    	return result;
    }
}
