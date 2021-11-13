package it.vvfriva.models;

import java.util.Arrays;
import java.util.List;

import it.vvfriva.utils.Configs;

public class MailObject {
	private String object;
	private String from;
	private List<String> to;
	private String content;
	
	public MailObject() {
		
	}
	
	/**
	 * 
	 * @param object
	 * @param from
	 * @param to
	 * @param content
	 */
	public MailObject(String object, String from, List<String> to, String content) {
		this.object = object;
		this.from = from;
		this.to = to;
		this.content = content;
	}
	/**
	 * 
	 * @param object
	 * @param from
	 * @param to
	 * @param content
	 */
	public MailObject(String object, String to, String content) {
		this.object = object;
		this.to = Arrays.asList(to);
		this.from = Configs.getMailFROM();
		this.content = content;
	}
	/**
	 * 
	 * @param object
	 * @param from
	 * @param to
	 * @param content
	 */
	public MailObject(String object, String from , String to, String content) {
		this.object = object;
		this.from = from;
		this.content = content;
		this.to = Arrays.asList(to);
	}
	
	/**
	 * @return the object
	 */
	public String getObject() {
		return object;
	}
	/**
	 * @param object the object to set
	 */
	public void setObject(String object) {
		this.object = object;
	}
	/**
	 * @return the from
	 */
	public String getFrom() {
		return from;
	}
	/**
	 * @param from the from to set
	 */
	public void setFrom(String from) {
		this.from = from;
	}
	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}
	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * @return the to
	 */
	public List<String> getTo() {
		return to;
	}
	/**
	 * @param to the to to set
	 */
	public void setTo(List<String> to) {
		this.to = to;
	}
}
