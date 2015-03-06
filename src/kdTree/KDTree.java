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
					travelStack.push( currNode.getLeftChild() );
					travelDimension.push( incrementDimension(currDimension) );
				}
				
			} else {
				if (currNode.getRightChild() == null){
					currNode.setRightChild(keys, value);
					done = true;
				} else {
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
	
	
	/**
	 * Retrieves all the values that are within the given range.
	 * @param lowest
	 * An array of the lower bound values.
	 * @param highest
	 * An array of the higher bound values.
	 * @return
	 * A set containing all the values within the range.
	 */
	public Set<T> range(Comparable[] lowest, Comparable[] highest){
		if(root == null){
			throw new NullPointerException("Root can't be null");
		}
		Stack<Node<T>> toVisit = new Stack<Node<T>>();
		Set<T> correctVals = new HashSet<T>();
		int level = 0;
		toVisit.push(root);
		
		while(!toVisit.isEmpty()){
			Node<T> currNode = toVisit.pop();
			Comparable currKey = currNode.getKey(level);
			int tempLevel = level;
			boolean allKeysWithinRange = true;
			do{
				if(currKey.compareTo(lowest[tempLevel]) < 0 && currKey.compareTo(highest[tempLevel]) > 0){ 
					allKeysWithinRange = false; //if outside range
				}
			}while(incrementDimension(tempLevel) != level && allKeysWithinRange);
			
			if(allKeysWithinRange){
				correctVals.add(currNode.getValue());
			}
			analyzeChildren(currNode, lowest[level], highest[level], level, toVisit);
			
		}//while
		
		return correctVals;
	}//range
	
	
	
	/**
	 * Determines if a child of the current node is within a given range.
	 * @param node
	 * The parent node.
	 * @param lowest
	 * The lowest value the child node i allowed to have.
	 * @param highest
	 * The highest value the child node i allowed to have.
	 * @param level
	 * The current level that we want to look at.
	 * @return
	 * Returns true if the child is within the given range. 
	 */
	private boolean childValid(Node<T> node, Comparable lowest, Comparable highest, int level){
		return ((node.getKey(level).compareTo(lowest) >= 0) && 
				(node.getKey(level).compareTo(highest) <= 0));
	}
	
	
	/**
	 * Determines if any of the child nodes are worth visiting later on.
	 * If so, adds it to a stack.
	 * 
	 * @param node
	 * The parent node.
	 * @param lowest
	 * The lowest value the child node i allowed to have.
	 * @param highest
	 * The highest value the child node i allowed to have.
	 * @param level
	 * The current level that we want to look at.
	 * @param toVisit
	 * The stack that the child node is added to if it is within the given range. 
	 */
	private void analyzeChildren(Node<T> node, Comparable lowest, 
			Comparable highest, int level, Stack<Node<T>> toVisit){
		
		if(node.getLeftChild() != null && childValid(node.getLeftChild(), lowest, highest, level)){
			toVisit.push(node.getLeftChild());
		}
		if(node.getLeftChild() != null && childValid(node.getLeftChild(), lowest, highest, level)){
			toVisit.push(node.getLeftChild());
		}
		
	}//analyzeChildren
		  
				
}





