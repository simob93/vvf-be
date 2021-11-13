package it.vvfriva.models;

import java.util.ArrayList;
import java.util.List;

import it.vvfriva.utils.ResponseMessage;

public class JsonResponse<T> {

	private Boolean success;
	private List<ResponseMessage> message;
	private T data;
	
	public JsonResponse(Boolean success, String msg, T data) {
		this.success = success;
		this.message = createArrayMsg(msg);
		this.data = data;
	}
	
	public JsonResponse(Boolean success, List<ResponseMessage> msg, T data) {
		this.success = success;
		this.message = msg;
		this.data = data;
	}

	private List<ResponseMessage> createArrayMsg(String msg) {
		List<ResponseMessage> list = new ArrayList<ResponseMessage>();
		if (success == false) {
			list.add(new ResponseMessage(ResponseMessage.MSG_TYPE_LOUD, msg));
		} else {
			list.add(new ResponseMessage(ResponseMessage.MSG_TYPE_SILENCE, msg));
		}
		return list;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public List<ResponseMessage> getMessage() {
		return message;
	}

	public void setMessage(List<ResponseMessage> message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
