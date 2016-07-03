package project2;

public class BinaryNode<T> {
	T element;
	BinaryNode<T> left;
	BinaryNode<T> right;
	int height;

	
	/**
	 * Constructor for BinaryNode
	 * @param theElement the data in the node
	 */
	BinaryNode(T theElement) {
		this(theElement, null, null);
	}

	/**
	 * Second constructor for BinaryNode
	 * @param theElement the data in the node
	 * @param lt left child of the node
	 * @param rt right child of the node
	 */
	BinaryNode(T theElement, BinaryNode<T> lt, BinaryNode<T> rt) {
		element = theElement;
		left = lt;
		right = rt;
	}

	/**
	 * Returns the data contained in the node
	 * @return the data contained in the node
	 */
	public T getData() {
		return element;
	}

	/**
	 * Returns the left child of the node
	 * @return the left child of the node
	 */
	public BinaryNode<T> getLeft() {
		return left;
	}

	/**
	 * Returns the right child of the node
	 * @return the left child of the node
	 */
	public BinaryNode<T> getRight() {
		return right;
	}

	/**
	 * Returns the height of the node
	 * @return the height of the node
	 */
	public int getHeight() {
		return height;
	}
	
}
