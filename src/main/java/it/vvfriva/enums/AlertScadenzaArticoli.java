package it.vvfriva.enums;

public enum AlertScadenzaArticoli {

    SCADUTO(-1),
    IN_SCADENZA(0),
    VALIDO(1);

    private  int tipoAlert;

    AlertScadenzaArticoli(int tipoAlert) {
        this.tipoAlert = tipoAlert;
    }

    public int getTipoAlert() {
        return tipoAlert;
    }

    public void setTipoAlert( int tipoAlert ) {
        this.tipoAlert = tipoAlert;
    }
}
