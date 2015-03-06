package kdTree;

import java.util.Set;
import java.util.Stack;

public class KDTree<T> {
	
	private int dimensions;
	private Node<T> root;
	
	public KDTree(int dimensions){
		if (dimensions < 2){
			throw new IllegalArgumentException("USE A BINARY TREE! BITCH!");
		}
		
		this.dimensions = dimensions;
	}
	
	private void addNodeToStack(Stack<Node<T>> theStack, Node<T> toAdd){
		if (toAdd != null){
			theStack.push(toAdd);
		}
	}

	public void insert(Comparable[] keys, T value){
		if (keys == null || value == null){
			throw new NullPointerException("Arguments can't be null.");
		}
		if (keys.length != dimensions){
			throw new IllegalArgumentException();
		}
		
		Node<T> currNode = null;
		if (root == null){
			root = new Node<T>(keys, value);
		} else {
			currNode = root;
		}
		
		Stack<Node<T>> travelStack = new Stack<Node<T>>();
		while (currNode != null){
			currNode.getLeftChild();
			addNodeToStack();
		}
	} // insert
	
	public Set<T> range(Comparable[] lowest, Comparable[] highest){
		
	}
}
