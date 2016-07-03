package project2;

public class AvlNode<T extends Comparable<T>> extends BinarySearchTree<T> {
	T element;
	AvlNode<T> left;
	AvlNode<T> right;
	int height;

	/**
	 * Constructor for AvlNode
	 * @param theElement the element itself
	 */
	AvlNode(T theElement) {
		this(theElement, null, null);
	}

	/**
	 * Second constructor fo AvlNOde
	 * @param theElement the element itself
	 * @param lt left node data
	 * @param rt right node data
	 */
	AvlNode(T theElement, AvlNode<T> lt, AvlNode<T> rt) {
		element = theElement;
		left = lt;
		right = rt;
	}
	
	/**
	 * Inserts a value into a Node's child
	 * @param x element itself
	 * @param t the node to have the element placed under
	 * @return a balanced tree after insertion
	 */
	private AvlNode<T> insert(T x, AvlNode<T> t) {
		if (t == null)
			return new AvlNode<>(x, null, null);

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

	/**
	 * Returns the height of the node
	 * @param t the node
	 * @return the height of the node
	 */
	private int height(AvlNode<T> t) {
		return t == null ? -1 : t.height;
	}

	/**
	 * Balances the Tree below the node
	 * @param t the node
	 * @return the node with a balanced tree below the node
	 */
	private AvlNode<T> balance(AvlNode<T> t) {
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

		t.height = Math.max(height(t.left), height(t.right)) + 1;
		return t;
	}

	/**
	 * Rotates the node along the right child.
	 * @param k1 the node to be rotated.
	 * @return the new node that was rotated.
	 */
	private AvlNode<T> rotateWithRightChild(AvlNode<T> k1) {
		AvlNode<T> k2 = k1.right;
		k1.right = k2.left;
		k1.left = k2;
		k1.height = Math.max(height(k1.left), height(k1.right)) + 1;
		k2.height = Math.max(height(k2.left), k1.height) + 1;
		return k2;
	}

	/**
	 * Rotates the node along the left child.
	 * @param k2 the node to be rotated.
	 * @return the new node that was rotated.
	 */
	private AvlNode<T> rotateWithLeftChild(AvlNode<T> k2) {
		AvlNode<T> k1 = k2.left;
		k2.left = k1.right;
		k1.right = k2;
		k2.height = Math.max(height(k2.left), height(k2.right)) + 1;
		k1.height = Math.max(height(k1.left), k2.height) + 1;
		return k1;
	}

	/**
	 * Rotates the node along the left child twice.
	 * @param k3 the node to be rotated.
	 * @return the new rotation.
	 */
	private AvlNode<T> doubleWithLeftChild(AvlNode<T> k3) {
		k3.left = rotateWithRightChild(k3.left);
		return rotateWithLeftChild(k3);
	}


	/**
	 * Rotates the node along the right child twice.
	 * @param k4 the node to be rotated.
	 * @return the new rotation.
	 */
	private AvlNode<T> doubleWithRightChild(AvlNode<T> k4) {
		k4.right = rotateWithLeftChild(k4.right);
		return rotateWithRightChild(k4);
	}
	
	/**
	 * Removes the node with the data requested
	 * @param x the data
	 * @param t node to search below
	 * @return a balanced tree after removal
	 */
	private AvlNode<T> remove(T x, AvlNode<T> t)
	{
		if(t == null)
			return t;
		
		int compareResult = x.compareTo(t.element);
		
		if(compareResult < 0)
			t.left = remove(x, t.left);
		else if( compareResult > 0)
			t.right = remove(x, t.right);
		else if(t.left != null && t.right != null)
		{
			t.element = findMin(t.right).element;
			t.right = remove(t.element, t.right);
		}
		else
			t = (t.left !=null ) ? t.left : t.right;
		return balance(t);
			
	}
	
	/**
	 * Finds the node with minimum value
	 * @param t node to search below
	 * @return the minimum value node
	 */
	private AvlNode<T> findMin(AvlNode<T> t) {
		if (t == null)
			return null;
		else if (t.left == null)
			return t; // found the leftmost node
		return findMin(t.left);
	}

}
