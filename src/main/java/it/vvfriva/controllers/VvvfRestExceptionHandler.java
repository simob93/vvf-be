package it.vvfriva.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import it.vvfriva.exception.UserFriendlyException;
import it.vvfriva.models.JsonResponse;
import it.vvfriva.utils.Utils;

@ControllerAdvice
public class VvvfRestExceptionHandler extends ResponseEntityExceptionHandler {
	@SuppressWarnings("rawtypes")
	@ExceptionHandler(UserFriendlyException.class)
    public ResponseEntity<JsonResponse> handleException(UserFriendlyException ex) {
		JsonResponse<Object> resp = new JsonResponse<Object>();
		resp.setSuccess(false);
		resp.setData(null);
		if( !Utils.isEmptyString(ex.getReason())) {
			resp.createArrayMsg(ex.getReason());
		}
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
		JsonResponse<Object> resp = new JsonResponse<Object>();
		resp.setSuccess(false);
		resp.setData(null);
		resp.setData(null);
		if( !Utils.isEmptyString(ex.getMessage())) {
			resp.createArrayMsg(ex.getMessage());
		}
		return new ResponseEntity<JsonResponse>(
	    		resp, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
			    
    }
}
