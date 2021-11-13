package it.vvfriva.utils;

import java.io.Serializable;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class Paged  implements Pageable, Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1499071396714359495L;
	private Integer skip;
	private Integer limit;
	private Sort sort;
	
	public Paged(Integer skip, Integer limit) {
		this(skip, limit, Sort.unsorted());
	}

	
	public Paged(Integer skip, Integer limit, Sort.Direction direction, String... property) {
		this(skip, limit, new Sort(direction, property));
	}

	
	public Paged(Integer skip, Integer limit, Sort sort) {
		this.skip = skip;
		this.limit = limit;
		this.sort = sort;
	}
	

	@Override
	public int getPageNumber() {
		return 0;
	}

	@Override
	public int getPageSize() {
		return getLimit();
	}

	@Override
	public long getOffset() {
		return getSkip();
	}

	@Override
	public Sort getSort() {
		return this.sort;
	}

	@Override
	public Pageable next() {
		return null;
	}

	@Override
	public Pageable previousOrFirst() {
		return null;
	}

	@Override
	public Pageable first() {
		return null;
	}

	@Override
	public boolean hasPrevious() {
		return false;
	}

	public Integer getSkip() {
		return skip;
	}

	public void setSkip(Integer skip) {
		this.skip = skip;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	
	

}
