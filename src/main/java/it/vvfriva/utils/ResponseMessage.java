package it.vvfriva.utils;
/**
 * 
 * @author simone
 *
 */
public class ResponseMessage {
	
	public static final Integer MSG_TYPE_SILENCE = 0; //mesaggio silenzioso trapsarente alla gui
	public static final Integer MSG_TYPE_LOUD = 1; //viene mostrato popup
	
	private Integer type;
	private String testo;
	/**
	 * 
	 * @param type
	 * @param testo
	 */
	public ResponseMessage(Integer type, String testo) {
		this.type = type;
		this.testo = testo;
	}
	
	public ResponseMessage(String testo) {
		this.type = MSG_TYPE_SILENCE;
		this.testo = testo;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getTesto() {
		return testo;
	}
	public void setTesto(String testo) {
		this.testo = testo;
	}
}
