package kdTree;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class KDTree<T> {
	
	private final int DIMENSIONS;
	private Node<T> root;
	
	private int size = 0;
	
	public KDTree(int dimensions){
		if (dimensions < 2){
			throw new IllegalArgumentException("USE A BINARY TREE! BITCH!");
		}
		
		DIMENSIONS = dimensions;
	}
	
	public int size(){
		return size;
	}
	
	private boolean isSameKeys(Comparable[] lhs, Comparable[] rhs){
		for (int i=0; i < lhs.length; ++i){
			if (lhs[i].compareTo(rhs[i]) != 0){
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Only for testing purposes.
	 * @return
	 * The root node obviously.
	 */
	public Node<T> getRoot(){
		return root;
	}
	

	public Set<T> get(Comparable[] keys){
		Set<T> retValue = new HashSet<T>();
			
		Stack<Node<T>> travelStack = new Stack<Node<T>>();
		Stack<Integer> dimensionStack = new Stack<Integer>();
		
		int currDimension = 0;
		
		Node<T> currNode = root;
		boolean foundKeys = false;
		
		while( currNode != null && !foundKeys){
			Comparable currKey = currNode.getKey(currDimension);
			
			if (currKey.compareTo( keys[currDimension] ) > 0){
				Node<T> leftChild = currNode.getLeftChild();
				if ( leftChild != null ){
					travelStack.push( leftChild );
					dimensionStack.push( incrementDimension(currDimension) );
				}
				
			} else {
				if ( isSameKeys(keys, currNode.getAllKeys()) ){
					retValue.add(currNode.getValue());
					foundKeys = true;
				}
				
				Node<T> rightChild = currNode.getRightChild();
				if ( rightChild != null ){
					travelStack.push( rightChild );
					dimensionStack.push( incrementDimension(currDimension) );
				}
			}
			
			if (!travelStack.isEmpty()){
				currNode = travelStack.pop();
				currDimension = dimensionStack.pop();
			} else {
				currNode = null;
			}
		}
		return retValue;
	}
	
	public boolean contains(Comparable[] keys){
		// TODO Kan optimeras.
		Set<T> value = get(keys);
		if (value.size() > 0){
			return true;
		}
		return false;
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
		Stack<Integer> dimensionStack = new Stack<Integer>();
		
		boolean done = false;
		while (!done){
			
			Comparable currKey = currNode.getKey(currDimension);
			if ( currKey.compareTo(keys[currDimension]) > 0){
				if (currNode.getLeftChild() == null){
					currNode.setLeftChild(Arrays.copyOf(keys, keys.length), value);
					++size;
					done = true;
				} else {
					travelStack.push( currNode.getLeftChild() );
					dimensionStack.push( incrementDimension(currDimension) );
				}
				
			// The insert key is greater than or equal to current nodes key.
			} else {
				if ( isSameKeys(keys, currNode.getAllKeys()) && 
						currNode.getValue().equals(value) ){
					
					// Don't insert a new node if an identical node already exists.
					done = true;
					
				} else {
					if (currNode.getRightChild() != null){
						travelStack.push( currNode.getRightChild() );
						dimensionStack.push( incrementDimension(currDimension) );
						
					} else {
						currNode.setRightChild(Arrays.copyOf(keys, keys.length), value);
						++size;
						done = true;
						
					} // check for right child
				} // check if new keys and value is identical to current node.
			} // compare keys
			
			if ( !travelStack.isEmpty() ){
				currNode = travelStack.pop();
				currDimension = dimensionStack.pop();
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
			root = new Node<T>(Arrays.copyOf(keys, keys.length), value);
			++size;
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
			return new HashSet<T>();
		}
		if(lowest.length != DIMENSIONS || highest.length != DIMENSIONS){
			throw new IllegalArgumentException("The input arrays must have as many elements as there are dimensions.");
		}
			
		Stack<Node<T>> nodesToVisit = new Stack<Node<T>>();
		Stack<Integer> stackNodeLevel = new Stack<Integer>();
		Set<T> correctVals = new HashSet<T>();
		int level = 0;
		nodesToVisit.push(root);
		stackNodeLevel.push(level);
		
		while(!nodesToVisit.isEmpty()){
			Node<T> currNode = nodesToVisit.pop();
			level = stackNodeLevel.pop();
			
			Comparable currKey = currNode.getKey(level);
			int tempLevel = level;
			boolean allKeysWithinRange = true;
			do{
				if(currKey.compareTo(lowest[tempLevel]) < 0 || currKey.compareTo(highest[tempLevel]) > 0){ 
					allKeysWithinRange = false; //if outside range
				}else{
					tempLevel = incrementDimension(tempLevel);
					currKey = currNode.getKey(tempLevel);
				}
			}while(tempLevel != level && allKeysWithinRange);
			
			if(allKeysWithinRange){
				correctVals.add(currNode.getValue());
			}
			
			if(currNode.getLeftChild() != null && currNode.getKey(level).compareTo(lowest[level]) > 0){
				nodesToVisit.push(currNode.getLeftChild());	
				stackNodeLevel.push(incrementDimension(level));
			}
			if(currNode.getRightChild() != null && currNode.getKey(level).compareTo(highest[level]) <= 0){
				nodesToVisit.push(currNode.getRightChild());
				stackNodeLevel.push(incrementDimension(level));
			}
			
		}//while
		
		return correctVals;
	}//range
	
	
	
	
	
//	private boolean outsideRange(Node<T> node, Comparable lowest, Comparable highest, int level){
//		return ((node.getKey(level).compareTo(lowest) < 0) && (node.getKey(level).compareTo(highest) <= 0));
//	}
	
	
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
//	private void analyzeChildren(Node<T> node, Comparable lowest, Comparable highest, int level, Stack<Node<T>> toVisit){
//		
//		Node<T> leftChild = node.getLeftChild();
//		if(leftChild != null){
//			if(leftChild.getKey(level).compareTo(lowest) ){
//				
//			}
//			
//			
//			//&& childValid(node.getLeftChild(), lowest, highest, level)){
//			//toVisit.push(node.getLeftChild());
//		}
//		if(node.getLeftChild() != null && childValid(node.getLeftChild(), lowest, highest, level)){
//			toVisit.push(node.getLeftChild());
//		}
//		
//	}//analyzeChildren
		  
				
}





