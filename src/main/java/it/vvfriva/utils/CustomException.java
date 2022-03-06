package it.vvfriva.utils;

import java.util.List;

public class CustomException extends Exception {
	
	private List<ResponseMessage> errorList;
	/**
	 * 
	 */
	private static final long serialVersionUID = 5871854842879225766L;
	
	public CustomException(List<ResponseMessage> strings) {
		super();
		this.errorList = strings;
	}

	public CustomException(String msg) {
		super(msg);
	}
	public List<ResponseMessage> getErrorList() {
		return errorList;
	}

	public void setErrorList(List<ResponseMessage> errorList) {
		this.errorList = errorList;
	}
	
	@Override
	public String getMessage() {
		String msg = "";
		for(ResponseMessage resp: this.errorList) {
			msg += resp.getTesto();
		}
		return msg;
	}

}
