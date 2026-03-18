package br.gov.caixa.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author c101482
 * 
 * @param <T>
 */
public class TreeNode<T extends Serializable> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private final T data;
	private TreeNode<T> parent;
	private final List<TreeNode<T>> children;

	public TreeNode(T data) {
		this.data = data;
		this.children = new ArrayList<TreeNode<T>>();
	}

	public void addChild(TreeNode<T> t) {
		t.parent = this;
		this.children.add(t);
	}

	public T getData() {
		return data;
	}

	public TreeNode<T> getParent() {
		return parent;
	}

	public List<TreeNode<T>> getChildren() {
		return children;
	}
	
	public Boolean isFolha() {
		return children.isEmpty();
	}

}
