package com.pf.commons.api.model;

import com.pf.commons.lang.Paginator;

import java.io.Serializable;
import java.util.List;

//@XmlRootElement
public class ModelPageList<E> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3059141426350234487L;
	private Paginator page;
	private List<E> list;
	
	public Paginator getPage() {
		return page;
	}
	public void setPage(Paginator page) {
		this.page = page;
	}
	public List<E> getList() {
		return list;
	}
	public void setList(List<E> list) {
		this.list = list;
	}
}