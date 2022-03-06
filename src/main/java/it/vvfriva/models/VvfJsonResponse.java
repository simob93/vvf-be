package it.vvfriva.models;

import java.util.List;

public class VvfJsonResponse<T> {

	private boolean succcess;
	private T data;
    private String message;
    private List<String> errors;
	
	public VvfJsonResponse() {
		
	}
	
	
	public VvfJsonResponse(boolean succcess, String message, T result) {
		this.succcess = succcess;
		this.message = message;
		this.data = result;
	}


	/**
	 * @return the succcess
	 */
	public boolean isSucccess() {
		return succcess;
	}
	/**
	 * @param succcess the succcess to set
	 */
	public void setSucccess(boolean succcess) {
		this.succcess = succcess;
	}
	/**
	 * @return the result
	 */
	public T getData() {
		return data;
	}
	/**
	 * @param result the result to set
	 */
	public void setData(T result) {
		this.data = result;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the errors
	 */
	public List<String> getErrors() {
		return errors;
	}


	/**
	 * @param errors the errors to set
	 */
	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
}
