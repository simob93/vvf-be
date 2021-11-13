package it.vvfriva.models;

public class JsonResponseTotal<T> extends JsonResponse<T> {
	
	public JsonResponseTotal(Boolean success, String message, T data, Integer total) {
		super(success, message, data);
		this.total = total;
	}

	private Integer total;

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}
}
