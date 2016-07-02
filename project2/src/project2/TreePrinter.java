package project2;

import java.util.Random;
import java.util.Scanner;

/**
 * BST and AVL tree printer for Integer nodes.
 *
 * CS 146 Data Structures and Algorithms Summer 2015 Department of Computer
 * Science San Jose State University
 * 
 * @author Ron Mak
 *
 */
public class TreePrinter {
	private static final int MAX_LEVELS = 6;

	private BinarySearchTree<Integer> tree; // the tree
	private int height; // its height

	// Powers of 2
	private static int POWERS_OF_2[] = new int[MAX_LEVELS + 2];
	static {
		int power = 1;
		for (int i = 0; i < MAX_LEVELS + 2; i++) {
			POWERS_OF_2[i] = power;
			power *= 2;
		}
	}

	/**
	 * Constructor
	 * 
	 * @param tree
	 *            the tree to print.
	 */
	public TreePrinter(BinarySearchTree<Integer> tree) {
		this.tree = tree;
		this.height = tree.height();
	}

	/**
	 * Print the tree using a level-order traversal.
	 * 
	 * @param label
	 *            the label to print before the tree.
	 */
	public void print(String label) {
		System.out.println(label);

		// Queue of nodes at this level.
		BinaryNode<Integer> thisLevelNodes[] = (BinaryNode<Integer>[]) new BinaryNode[1];

		int offset = POWERS_OF_2[(height + 1)] - 1;

		thisLevelNodes[0] = tree.getRoot();

		// Loop to print each level of nodes.
		for (int level = 0; level <= height; level++) {
			if (level > MAX_LEVELS) {
				System.out.println("*** Too many levels to print. ***");
				break;
			}

			// Print node data.
			printData(level, offset, thisLevelNodes);

			if (level != height) {

				// Print outgoing pointers /\ from each parent node.
				printOutgoingPointers(level, offset, thisLevelNodes);

				offset = POWERS_OF_2[height - level] - 1;

				// Print connecting dashes ----
				if (level < height - 1) {
					printConnectingDashes(level, offset, thisLevelNodes);
				}

				// Print incoming pointers / and \ to each child node.
				printIncomingPointers(level, offset, thisLevelNodes);

				// Prepare the next level of nodes.
				thisLevelNodes = nextLevel(level, thisLevelNodes);
			}
		}
	}

	/**
	 * Print node data.
	 * 
	 * @param level
	 *            the current level
	 * @param offset
	 *            the current offset
	 * @param levelNodes
	 *            the current level of nodes
	 */
	private void printData(int level, int offset,
			BinaryNode<Integer> levelNodes[]) {
		printSpaces(offset);

		int k = POWERS_OF_2[level];
		for (int i = 0; i < k; i++) {
			BinaryNode<Integer> node = levelNodes[i];

			if (node != null) {
				System.out.printf("%3d ", node.getData());
			} else {
				System.out.print("    ");
			}

			// Space over to the next node in this level.
			if (i < k - 1)
				printSpaces(2 * offset - 2);
		}

		System.out.println();
	}

	/**
	 * Print outgoing pointers /\ from each non-null parent node.
	 * 
	 * @param level
	 *            the current level
	 * @param offset
	 *            the current offset
	 * @param levelNodes
	 *            the current level of nodes
	 */
	private void printOutgoingPointers(int level, int offset,
			BinaryNode<Integer> levelNodes[]) {
		printSpaces(offset);

		int k = POWERS_OF_2[level];
		for (int i = 0; i < k; i++) {
			BinaryNode<Integer> node = levelNodes[i];

			// Has left child: print /
			if ((node != null) && (node.getLeft() != null)) {
				System.out.print(" /");
			}

			// No left child: print space
			else {
				System.out.print("  ");
			}

			// Has right child: print \
			if ((node != null) && (node.getRight() != null)) {
				System.out.print("\\ ");
			}

			// No right child: print space
			else {
				System.out.print("  ");
			}

			// Space over to the next node in this level.
			if (i < k - 1)
				printSpaces(2 * offset - 2);
		}

		System.out.println();
	}

	/**
	 * Print the connecting dashes between an outgoing pointer and an incoming
	 * pointer.
	 * 
	 * @param level
	 *            the current level
	 * @param offset
	 *            the current offset
	 * @param levelNodes
	 *            the current level of nodes
	 */
	private void printConnectingDashes(int level, int offset,
			BinaryNode<Integer> levelNodes[]) {
		if (offset > 1)
			printSpaces(offset);

		int k = POWERS_OF_2[level];
		for (int i = 0; i < k; i++) {
			BinaryNode<Integer> node = levelNodes[i];

			// Has left child: print dashes
			if ((node != null) && (node.getLeft() != null)) {
				printSpaces(3);
				printDashes(offset - 1);
			}

			// No left child: print spaces
			else {
				printSpaces(offset + 2);
			}

			// Has right child: print dashes
			if ((node != null) && (node.getRight() != null)) {
				printSpaces(2);
				printDashes(offset - 1);
			}

			// No right child: print spaces
			else {
				printSpaces(offset + 1);
			}

			// Space over to the next node in this level.
			if (i < k - 1)
				printSpaces(2 * offset + 1);
		}

		System.out.println();
	}

	/**
	 * Print incoming pointers / or \ to each non-null child node.
	 * 
	 * @param level
	 *            the current level
	 * @param offset
	 *            the current offset
	 * @param levelNodes
	 *            the current level of nodes
	 */
	private void printIncomingPointers(int level, int offset,
			BinaryNode<Integer> levelNodes[]) {
		printSpaces(offset);

		int k = POWERS_OF_2[level];
		for (int i = 0; i < k; i++) {
			BinaryNode<Integer> node = levelNodes[i];

			// Left child: print /
			if ((node != null) && (node.getLeft() != null)) {
				System.out.print("  /");
			}

			// No left child: print spaces
			else {
				printSpaces(3);
			}

			// Right child: print \
			if ((node != null) && (node.getRight() != null)) {
				printSpaces(2 * offset);
				System.out.print("\\");
			}

			// No right child: print spaces
			else {
				printSpaces(2 * offset + 1);
			}

			// Space over to the next node in this level.
			if (i < k - 1)
				printSpaces(2 * offset);
		}

		System.out.println();
	}

	/**
	 * Prepare the next level of nodes.
	 * 
	 * @param level
	 *            the current level
	 * @param levelNodes
	 *            the current level of nodes
	 * @return the next level of nodes.
	 */
	private BinaryNode<Integer>[] nextLevel(int level,
			BinaryNode<Integer> levelNodes[]) {
		BinaryNode<Integer> nextLevel[] = (BinaryNode<Integer>[]) new BinaryNode[POWERS_OF_2[level + 1]];

		for (int i = 0; i < POWERS_OF_2[level]; i++) {
			BinaryNode<Integer> node = levelNodes[i];

			// Queue the left child nodes of each non-null parent node.
			nextLevel[2 * i] = (node != null) && (node.getLeft() != null) ? node
					.getLeft() : null;

			// Queue the right child nodes of each non-null parent node.
			nextLevel[2 * i + 1] = (node != null) && (node.getRight() != null) ? node
					.getRight() : null;
		}

		return nextLevel;
	}

	/**
	 * Print spaces.
	 * 
	 * @param count
	 *            the number of spaces.
	 */
	private void printSpaces(int count) {
		for (int i = 0; i < count; i++)
			System.out.print(" ");
	}

	/**
	 * Print dashes.
	 * 
	 * @param count
	 *            the number of dashes.
	 */
	private void printDashes(int count) {
		for (int i = 0; i < count; i++)
			System.out.print("-");
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		Random rand = new Random();
		BinarySearchTree<Integer> tree = null;
		System.out.println("Would you like to check Part(1) or Part(2)? (Type 1 or 2 in the console)");
		while(in.hasNextInt()){
			int part = in.nextInt();
			in.nextLine();
			if(part==1) {
				System.out.println("AVL or BST? (A for AVL, B for BST, anything else would not work)");
				while(in.hasNextLine()){
					String s = in.nextLine();
					if(s.toUpperCase().equals("A")){ //AVL Tree
						tree = new AvlTree<>();
						for (int i = 0; i < 35; i++) {
							int n = rand.nextInt(90) + 10;
							tree.insert(n);
							TreePrinter print = new TreePrinter(tree);
							print.print("AVL Tree");
						}

						while (tree.getRoot() != null) {
							tree.remove(tree.getRoot().getData());
							TreePrinter print = new TreePrinter(tree);
							print.print("REMOVE AVL Tree");

						}
						break;
					}
					else if(s.toUpperCase().equals("B")){ //BST Tree
						tree = new BinarySearchTree<>();
						while(tree.height() != 5) {
							int n = rand.nextInt(90) + 10;
							tree.insert(n);
						}
						TreePrinter print = new TreePrinter(tree);
						print.print("BST TREE");

						while(tree.getRoot() != null)
						{
							tree.remove(tree.getRoot().getData());
							print.print("REMOVE BST Tree");
						}
						break;
					}
					else System.out.println("Incorrect input. Try again.");
				}
				
				break;
			}
			else if (part==2){
				tree = new AvlTree<>();
				BinarySearchTree<Integer> tree2 = new BinarySearchTree<>();
				System.out.println("Provide the value of n nodes (Give value above 1000): ");
				int n = 0;
				while(n == 0){
				n = in.nextInt();
				if(n == 0) System.out.println("n needs to be more than 1/input an integer");
				}
				long timeStart = System.currentTimeMillis();
				for (int i = 0; i < n; i++) {
					int value = rand.nextInt(90) + 10;
					tree2.insert(value);
				}
				long timeEnd = System.currentTimeMillis();
				System.out.println("Time taken to make BST tree: "+
						(double)(timeEnd-timeStart)/1000 + " seconds");
				timeStart = System.currentTimeMillis();
				for (int i = 0; i < n; i++) {
					int value = rand.nextInt(90) + 10;
					tree.insert(value);
				}
				timeEnd = System.currentTimeMillis();
				System.out.println("Time taken to make AVL tree: "+
						(double)(timeEnd-timeStart)/1000 + " seconds");
			}
		}
		

		
	}
}
