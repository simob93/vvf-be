package it.vvfriva.enums;

import java.util.Arrays;

import it.vvfriva.utils.Utils;

/**
 *  enums turni possibili vvf
 * @author simone
 *
 */
public enum EnumsTurni {
	
	LUNEDI(0, "Lunedi"),
	MARTEDI(1, "Martedi"),
	MERCOLEDI(2, "Mercoledi"),
	GIOVEDI(3, "Giovedi"),
	VENERDI(4, "Venerdi"),
	SABATO(5, "Sabato"),
	DOMENICA(6, "Domenica"),
	SABATO_MATTINA(7, "Sabato (Mattina)"),
	SABATO_POMERIGGIO(8, "Sabato (Pomeriggio)"),
	DOMENICA_MATTINA(9, "Domenica (Mattina)"),
	DOMENICA_POMERIGGIO(10, "Domenica (Pomeriggio)");
	
	private Integer id;
	private String descr;
	
	EnumsTurni(Integer id, String descr) {
		this.id = id;
		this.descr = descr;
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

	public static String getById(Integer idTurno) {
		
		if (Utils.geZero(idTurno)) {
			return Arrays.asList(EnumsTurni.values()).stream().filter(turno -> turno.getId().compareTo(idTurno) == 0).map(turno -> turno.getDescr()).findFirst().get();
		}
		return null;
	}
	
	
	
	
}
