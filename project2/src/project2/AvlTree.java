package project2;

public class AvlTree<T extends Comparable<T>> extends BinarySearchTree<T> {
	public boolean printStatements = false;
	/* Function to get height of node */
	public int height(BinaryNode<T> t) {
		return t == null ? -1 : t.height;
	}

	/* Function to max of left/right node */
	public int max(int lhs, int rhs) {
		return lhs > rhs ? lhs : rhs;
	}

	public void printStatements(boolean print){
		printStatements = print;
	}
	public BinaryNode<T> insert(T x, BinaryNode<T> t) {
		if (t == null)
			return new BinaryNode<>(x, null, null);

		int compareResult = x.compareTo(t.element);

		if (compareResult < 0)
			t.left = insert(x, t.left);
		else if (compareResult > 0)
			t.right = insert(x, t.right);
		else
			;
		return balance(t);
	}

	private static final int ALLOWED_IMBALANCE = 1;

	public BinaryNode<T> balance(BinaryNode<T> t) {
		if (t == null)
			return t;
		if (height(t.left) - height(t.right) > ALLOWED_IMBALANCE)
			if (height(t.left.left) >= height(t.left.right))
				t = rotateWithLeftChild(t);
			else
				t = doubleWithLeftChild(t);
		else if (height(t.right) - height(t.left) > ALLOWED_IMBALANCE)
			if (height(t.right.right) >= height(t.right.left))
				t = rotateWithRightChild(t);
			else
				t = doubleWithRightChild(t);

		t.height = max(height(t.left), height(t.right)) + 1;
		return t;
	}

	/**
	 * Rotates the node along the right child.
	 * @param k1 the node to be rotated.
	 * @return the new node that was rotated.
	 */
	public BinaryNode<T> rotateWithRightChild(BinaryNode<T> k1) {
		BinaryNode<T> k2 = k1.right;
		k1.right = k2.left;
		k2.left = k1;
		k1.height = max(height(k1.left), height(k1.right)) + 1;
		k2.height = max(height(k2.right), k1.height) + 1;
		if(printStatements)System.out.println("Single right Rotation: " + k1.getData());
		return k2;
	}

	/**
	 * Rotates the node along the left child.
	 * @param k2 the node to be rotated.
	 * @return the new node that was rotated.
	 */
	public BinaryNode<T> rotateWithLeftChild(BinaryNode<T> k2) {
		BinaryNode<T> k1 = k2.left;
		k2.left = k1.right;
		k1.right = k2;
		k2.height = max(height(k2.left), height(k2.right)) + 1;
		k1.height = max(height(k1.left), k2.height) + 1;
		if(printStatements)System.out.println("Single left Rotation: " + k2.getData());
		return k1;
	}

	/**
	 * Rotates the node along the left child twice.
	 * @param k3 the node to be rotated.
	 * @return the new rotation.
	 */
	public BinaryNode<T> doubleWithLeftChild(BinaryNode<T> k3) {
		k3.left = rotateWithRightChild(k3.left);
		if(printStatements)System.out.println("Double left-right Rotation: " + k3.getData());
		return rotateWithLeftChild(k3);
	}

	/**
	 * Rotates the node along the right child twice.
	 * @param k4 the node to be rotated.
	 * @return the new rotation.
	 */
	public BinaryNode<T> doubleWithRightChild(BinaryNode<T> k1) {
		k1.right = rotateWithLeftChild(k1.right);
		if(printStatements)System.out.println("Double right-left Rotation: " + k1.getData());
		return rotateWithRightChild(k1);
	}

	/**
	 * Removes the node with the data requested
	 * @param x the data
	 * @param t node to search below
	 * @return a balanced tree after removal
	 */
	public BinaryNode<T> remove(T x, BinaryNode<T> t) {
		if (t == null)
			return t;

		int compareResult = x.compareTo(t.element);

		if (compareResult < 0)
			t.left = remove(x, t.left);
		else if (compareResult > 0)
			t.right = remove(x, t.right);
		else if (t.left != null && t.right != null) {
			t.element = findMin(t.right).element;
			t.right = remove(t.element, t.right);
		} else
			t = (t.left != null) ? t.left : t.right;
		return balance(t);
	}
	
	/**
	 * Finds the node with minimum value
	 * @param t node to search below
	 * @return the minimum value node
	 */
	private BinaryNode<T> findMin(BinaryNode<T> t) {
		if (t == null)
			return null;
		else if (t.left == null)
			return t; // found the leftmost node
		return findMin(t.left);
	}

}
