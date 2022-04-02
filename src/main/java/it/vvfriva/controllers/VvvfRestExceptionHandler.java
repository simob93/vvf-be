package it.vvfriva.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import it.vvfriva.exception.UserFriendlyException;
import it.vvfriva.models.JsonResponse;

@ControllerAdvice
public class VvvfRestExceptionHandler extends ResponseEntityExceptionHandler {
	@SuppressWarnings("rawtypes")
	@ExceptionHandler(UserFriendlyException.class)
    public ResponseEntity<JsonResponse> handleException(UserFriendlyException ex) {
		JsonResponse<Object> resp = new JsonResponse<Object>(false, ex.getReason(), null);
		return new ResponseEntity<JsonResponse>(
	    		resp, new HttpHeaders(), ex.getStatus());
			    
    }
	/**
	 * Gestione di tutte le exception al di fuori di {@code UserFriendlyException}
	 * @param ex
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@ExceptionHandler(Exception.class)
    public ResponseEntity<JsonResponse> handleGenericException(Exception ex) {
		JsonResponse<Object> resp = new JsonResponse<Object>(false, ex.getMessage(), null);
		return new ResponseEntity<JsonResponse>(
	    		resp, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
			    
    }
}
