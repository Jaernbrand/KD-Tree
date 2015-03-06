package kdTree;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class KDTree<T> {
	
	private final int DIMENSIONS;
	private Node<T> root;
	
	public KDTree(int dimensions){
		if (dimensions < 2){
			throw new IllegalArgumentException("USE A BINARY TREE! BITCH!");
		}
		
		DIMENSIONS = dimensions;
	}
	
	private void addNodeToStack(Stack<Node<T>> theStack, Node<T> toAdd){
		if (toAdd != null){
			theStack.push(toAdd);
		}
	}

	private int incrementDimension(int dimension){
		int newDimension = ++dimension;
		if (newDimension >= DIMENSIONS){
			newDimension = 0;
		}
		return newDimension;
	}
	
	private void insertInTree(Comparable[] keys, T value){
		Node<T> currNode = root;
		int currDimension = 0;
		
		Stack<Node<T>> travelStack = new Stack<Node<T>>();
		Stack<Integer> travelDimension = new Stack<Integer>();
		
		boolean done = false;
		while (!done){
			
			Comparable currKey = currNode.getKey(currDimension);
			if ( currKey.compareTo(keys[currDimension]) < 0){
				if (currNode.getLeftChild() == null){
					currNode.setLeftChild(keys, value);
					done = true;
				} else {
					//addNodeToStack(travelStack, currNode.getLeftChild());
					travelStack.push( currNode.getLeftChild() );
					travelDimension.push( incrementDimension(currDimension) );
				}
				
			} else {
				if (currNode.getRightChild() == null){
					currNode.setRightChild(keys, value);
					done = true;
				} else {
					//addNodeToStack(travelStack, currNode.getRightChild());
					travelStack.push( currNode.getRightChild() );
					travelDimension.push( incrementDimension(currDimension) );
				}
			}
			
			if ( !travelStack.isEmpty() ){
				currNode = travelStack.pop();
				currDimension = travelDimension.pop();
			}
		}
	} // insertInTree
	
	public void insert(Comparable[] keys, T value){
		if (keys == null || value == null){
			throw new NullPointerException("Arguments can't be null.");
		}
		if (keys.length != DIMENSIONS){
			throw new IllegalArgumentException();
		}
		
		if (root == null){
			root = new Node<T>(keys, value);
		} else {
			insertInTree(keys, value);
		}
	} // insert
	
	public Set<T> range(Comparable[] lowest, Comparable[] highest){
		if(root == null){
			throw new NullPointerException("Root can't be null");
		}
		
		Stack<Node<T>> toVisit = new Stack<Node<T>>();
		Set<T> correctVals = new HashSet<T>();
		int currentLevel = 0;
		
		toVisit.push(root);
		while(!toVisit.isEmpty()){
			
			
			
		}
			
		return correctVals;
	}
}
