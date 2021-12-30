package it.vvfriva.enums;

import it.vvfriva.utils.Utils;

/**
 * Enums gestione taglie
 * @author simone
 *
 */
public enum EnumsTaglie {
	
	Taglia_UNICA("0", "UNICA"),
	Taglia_36("36"),
	Taglia_37("37"),
	Taglia_38("38"),
	Taglia_39("39"),
	Taglia_40("40"),
	Taglia_41("41"),
	Taglia_42("42"),
	Taglia_43("43"),
	Taglia_44("44"),
	Taglia_45("45"),
	Taglia_46("46"),
	Taglia_47("47"),
	Taglia_48("48"),
	Taglia_49("49"),
	Taglia_50("50"),
	Taglia_51("51"),
	Taglia_52("52"),
	Taglia_53("53"),
	Taglia_54("54"),
	Taglia_55("55"),
	Taglia_56("56"),
	Taglia_XS("XS"),
	Taglia_S("S"),
	Taglia_M("M"),
	Taglia_L("L"),
	Taglia_XL("XL"),
	Taglia_XXL("XXL");
	
	private String codice;
	private String descr;
	
	
	EnumsTaglie(String codice, String descr) {
		this.codice = codice;
		this.descr = descr;
	}
	EnumsTaglie(String codice) {
		this.codice = codice;
		this.descr = codice;
	}
	/**
	 * @return the id
	 */
	public String getCodice() {
		return codice;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.codice = id;
	}
	/**
	 * @return the descr
	 */
	public String getDescr() {
		return descr;
	}
	/**
	 * @param descr the descr to set
	 */
	public void setDescr(String descr) {
		this.descr = descr;
	}
	
	public static String getDescrizioneTaglia(String codiceTaglia) {
		String tagliaOut = null;
		if (!Utils.isEmptyString(codiceTaglia)) {
			for(EnumsTaglie taglia: EnumsTaglie.values()) {
				if (Utils.stringEguals(codiceTaglia, taglia.getCodice())) {
					tagliaOut = taglia.getDescr();
					break;
				}
			}
		}
		return tagliaOut;
		
	}
}
