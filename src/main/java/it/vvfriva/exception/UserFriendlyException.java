package it.vvfriva.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserFriendlyException extends ResponseStatusException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3903344615174436976L;
	/**
	 * 
	 */
	public UserFriendlyException(String reason) {
		super(HttpStatus.INTERNAL_SERVER_ERROR, reason);
	}
}
