package project2;

import java.util.*;
import java.util.Comparator;

public class BinarySearchTree<T extends Comparable<T>> {

	private BinaryNode<T> root;
	private Comparator<T> cmp;

	/**
	 * construct the class
	 */
	public BinarySearchTree() {
		this(null);
	}

	public BinarySearchTree(Comparator<T> c) {
		root = null;
		cmp = c;
	}

	public void makeEmpty() {
		root = null;
	}

	public boolean isEmpty() {
		return root == null;
	}

	public BinaryNode<T> getRoot() {
		return root;
	}

	public int height() {
		if (this.isEmpty()) {
			return -1;
		} else {
			return height(root);
		}
	}

	private int height(BinaryNode<T> aNode) {
		if (aNode == null)
			return -1;
		else
			return 1 + Math.max(height(aNode.left), height(aNode.right));

	}

	public T findMin() throws UnderflowException {
		if (isEmpty()) {
			throw new UnderflowException("There are no Node");
		}
		return findMin(root).element;
	}

	public T findMax() throws UnderflowException {
		if (isEmpty())
			throw new UnderflowException("There are no Node");
		return findMax(root).element;
	}

	private int myCompare(T lhs, T rhs) {
		if (cmp != null)
			return cmp.compare(lhs, rhs);
		else
			return ((Comparable) lhs).compareTo(rhs);
	}

	public void insert(T x) {
		root = insert(x, root);
	}

	public void remove(T x) {
		root = remove(x, root);
	}

	private BinaryNode<T> insert(T x, BinaryNode<T> t) {
		if (t == null) {
			return new BinaryNode<>(x, null, null);
		}

		int compareResult = x.compareTo(t.element);

		if (compareResult < 0)
			t.left = insert(x, t.left);
		else if (compareResult > 0)
			t.right = insert(x, t.right);
		else
			;
		return t;
	}

	private BinaryNode<T> findMin(BinaryNode<T> t) {
		if (t == null)
			return null;
		else if (t.left == null)
			return t; // found the leftmost node
		return findMin(t.left);
	}

	private BinaryNode<T> findMax(BinaryNode<T> t) {
		if (t != null)
			while (t.right != null)
				t = t.right; // found the rightmost node
		return t;
	}

	private BinaryNode<T> remove(T x, BinaryNode<T> t) {
		if (t == null)
			return t;

		int compareResult = x.compareTo(t.element);
		if (compareResult < 0) {
			t.left = remove(x, t.left);
		} else if (compareResult > 0) {
			t.right = remove(x, t.right);
		} else if (t.left != null && t.right != null) {
			t.element = findMin(t.right).element;
			t.right = remove(t.element, t.right);
		} else {
			t = (t.left != null) ? t.left : t.right;
		}
		return t;
	}
	


}
