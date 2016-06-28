package project2;

public class AvlNode<T extends Comparable<T>> extends BinarySearchTree {
	T element;
	AvlNode<T> left;
	AvlNode<T> right;
	int height;

	AvlNode(T theElement) {
		this(theElement, null, null);
	}

	AvlNode(T theElement, AvlNode<T> lt, AvlNode<T> rt) {
		element = theElement;
		left = lt;
		right = rt;
	}

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

	private int height(AvlNode<T> aNode) {
		int heightLeft = 0;
		int heightRight = 0;
		if (aNode.left != null)
			heightLeft = height(aNode.left);
		if (aNode.right != null)
			heightRight = height(aNode.right);
		if (heightLeft > heightRight) {
			return heightLeft + 1;
		} else {
			return heightRight + 1;
		}
	}

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
	
	private AvlNode<T> rotateWithRightChild(AvlNode<T> k1)
	{
		AvlNode<T> k2 = k1.right;
		k1.right = k2.left;
		k1.left = k2;
		k1.height = Math.max(height(k1.left), height(k1.right)) + 1;
		k2.height = Math.max(height(k2.left), k1.height) + 1;
		return k2;
	}

	private AvlNode<T> rotateWithLeftChild(AvlNode<T> k2) {
		AvlNode<T> k1 = k2.left;
		k2.left = k1.right;
		k1.right = k2;
		k2.height = Math.max(height(k2.left), height(k2.right)) + 1;
		k1.height = Math.max(height(k1.left), k2.height) + 1;
		return k1;
	}

	private AvlNode<T> doubleWithLeftChild(AvlNode<T> k3) {
		k3.left = rotateWithRightChild(k3.left);
		return rotateWithLeftChild(k3);
	}
	
	private AvlNode<T> doubleWithRightChild(AvlNode<T> k4)
	{
		k4.right = rotateWithLeftChild(k4.right);
		return rotateWithRightChild(k4);
	}
}
