package project2;

public class BinaryNode<T> {
	T element;
	BinaryNode<T> left;
	BinaryNode<T> right;
	int height;

	// constructors
	BinaryNode(T theElement) {
		this(theElement, null, null);
	}

	BinaryNode(T theElement, BinaryNode<T> lt, BinaryNode<T> rt) {
		element = theElement;
		left = lt;
		right = rt;
	}

	public T getData() {
		return element;
	}

	public BinaryNode<T> getLeft() {
		return left;
	}

	public BinaryNode<T> getRight() {
		return right;
	}

	public int getHeight() {
		return height;
	}
	
}
