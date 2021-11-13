package it.vvfriva.models;

import java.util.ArrayList;
import java.util.List;

import it.vvfriva.utils.Utils;

public class TreeNodeStd<T>  {
	private Integer nodId;
	private Integer parentId;
	private String text;
	private List<T> children;
	private boolean isRoot = false;
	public TreeNodeStd() {

	}

	/**
	 * 
	 * @param nodId
	 * @param parentId
	 * @param text
	 * @param children
	 */
	public TreeNodeStd(Integer nodId, Integer parentId, String text, List<T> children) {
		this.nodId = nodId;
		this.parentId = parentId;
		this.text = text;
		this.children = children;
	}

	/**
	 * aggiungie un elemento al {@code children}
	 * 
	 * @param el
	 */
	public void addChild(T el) {
		if (this.children == null) {
			this.children = new ArrayList<T>();
		}
		this.children.add(el);
	}

	/**
	 * @return the nodId
	 */
	public Integer getNodId() {
		return nodId;
	}

	/**
	 * @param nodId the nodId to set
	 */
	public void setNodId(Integer nodId) {
		this.nodId = nodId;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return the children
	 */
	public List<T> getChildren() {
		return children;
	}

	/**
	 * @param children the children to set
	 */
	public void setChildren(List<T> children) {
		this.children = children;
	}

	/**
	 * @return the parentId
	 */
	public Integer getParentId() {
		return parentId;
	}

	/**
	 * @param parentId the parentId to set
	 */
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	/**
	 * @return the isLeaf
	 */
	public boolean isLeaf() {
		return Utils.isEmptyList(this.children);
	}

	/**
	 * @param isLeaf the isLeaf to set
	 */
	public void setLeaf(boolean isLeaf) {
	}

	/**
	 * @return the isRoot
	 */
	public boolean isRoot() {
		return isRoot;
	}

	/**
	 * @param isRoot the isRoot to set
	 */
	public void setRoot(boolean isRoot) {
		this.isRoot = isRoot;
	}

}
