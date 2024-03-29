package kdTree;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

/**
 * The tree sorts values by K number of keys, also referred to as dimensions. The 
 * number of dimensions have to be specified when a new KDTree is created.
 * 
 * The arrays containing the keys are required to be the same length as the 
 * number of dimensions. The objects in the key arrays have to implement the 
 * interface Comparable. Objects at the same index but in different keys also 
 * have to be comparable with each other. For instance, the object at index 0 
 * in one key array have to be comparable with all other objects at index 0 in 
 * all other key arrays. 
 * 
 * It's recommended to implement equals for any value-objects, since it's used 
 * to avoid duplicate key-value pairs.
 *
 * @param <T>
 * - the type of the values in the kd-tree.
 * 
 * @author Henrik Järnbrand 
 * @author Tomas Sandberg
 */
public class KDTree<T> {
	
	private final int DIMENSIONS;
	private Node<T> root;
	
	private int size = 0;
	
	/**
	 * Creates a new KDTree object with the supplied number of dimensions.
	 * 
	 * @param dimensions
	 * - the number of dimensions of the KD-Tree. Can not be less than 2.
	 * 
	 * @throws IllegalArgumetnException if dimensions is less than 2.
	 */
	public KDTree(int dimensions){
		if (dimensions < 2){
			throw new IllegalArgumentException("Less than two dimensions makes this data structure useless.");
		}
		
		DIMENSIONS = dimensions;
	}
	
	/**
	 * Returns the size of the KD-Tree. The size is equal to the number of 
	 * elements in the KD-Tree. 
	 * 
	 * @return
	 * - the size of the KD-Tree.
	 */
	public int size(){
		return size;
	}
	
	/**
	 * Returns true if both the arrays have the same keys in the same indices. 
	 * Equality is based on compareTo.
	 * 
	 * @param lhs
	 * - one of the arrays to compare. The array have to be an array of Comparable.
	 * @param rhs
	 * - one of the arrays to compare. The array have to be an array of Comparable.
	 * @return
	 * - true if both the arrays have the same keys.
	 */
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
	 * The root node.
	 */
	Node<T> getRoot(){
		return root;
	}
	
	/**
	 * Finds and returns all values that have the supplied array keys as key.
	 * @param keys
	 * - the array containing the keys to search for. The array have to be an 
	 * array of Comparable.
	 * 
	 * @return
	 * - a set containing all values with the array keys as key. 
	 * 
	 * @throws NullPointerException if keys is null.
	 * @throws IllegalArgumentException if the length of keys isn't equal to 
	 * the number of dimensions in the KD-Tree.
	 */
	public Set<T> get(Comparable[] keys){
		if (keys == null)
			throw new NullPointerException();
		if (keys.length != DIMENSIONS)
			throw new IllegalArgumentException();
		
		Set<T> retValue = new HashSet<T>();
			
		Stack<Node<T>> travelStack = new Stack<Node<T>>();
		Stack<Integer> dimensionStack = new Stack<Integer>();
		
		int currDimension = 0;
		
		Node<T> currNode = root;
		
		while( currNode != null ){
			Comparable currKey = currNode.getKey(currDimension);
			
			// The search key is less than current node's key.
			if (currKey.compareTo( keys[currDimension] ) > 0){
				Node<T> leftChild = currNode.getLeftChild();
				if ( leftChild != null ){
					travelStack.push( leftChild );
					dimensionStack.push( incrementDimension(currDimension) );
				}
				
			// The search key is greater than or equal to current node's key.	
			} else {
				if ( isSameKeys(keys, currNode.getAllKeys()) ){
					retValue.add(currNode.getValue());
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
		} // while-loop
		return retValue;
	}
	
	/**
	 * Returns true if the KD-Tree contains an element with the all the keys 
	 * in the keys array.
	 * 
	 * @param keys
	 * - the keys to search for in the KD-Tree. The array has to be an array of 
	 * objects implementing the comparable interface.
	 * 
	 * @return
	 * - true if at least one element in the tree contains the supplied key.
	 * 
	 * @throws NullPointerException if keys is null.
	 * @throws IllegalArgumentException if the length of keys isn't equal to 
	 * the number of dimensions in the KD-Tree.
	 */
	public boolean contains(Comparable[] keys){
		Set<T> value = get(keys);
		if (value.size() > 0){
			return true;
		}
		return false;
	}

	/**
	 * Increments the supplied int with one. The new value is set to zero if 
	 * it's greater than or equal to the KD-Tree's dimension size.
	 * 
	 * @param dimension
	 * - an int representing one of the KD-Tree's dimensions.
	 * 
	 * @return
	 * - the supplied dimension value incremented by one or 0 if the incremented
	 * value is greater than or equal to the number of dimensions in the tree.
	 */
	private int incrementDimension(int dimension){
		int newDimension = ++dimension;
		if (newDimension >= DIMENSIONS){
			newDimension = 0;
		}
		return newDimension;
	}
	
	/**
	 * Inserts the key-value pair in the kd-tree. This method is called from 
	 * the public insert-method and does most of the work for the insert-method 
	 * when a value is inserted. The same restrictions does of course apply. 
	 * Two different values may have identical keys, but two equal values may 
	 * not have identical key. Equality is checked by the equals method.
	 * 
	 * This method assumes the root isn't null. 
	 * 
	 * @param keys
	 * - the keys with which to search for the value. It has to be an array of 
	 * objects implementing the comparable interface. Keys also has to be
	 * as long as the number of dimensions in the tree.
	 * 
	 * @param value
	 * - the value to insert in the tree.
	 */
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
				
			// The insert key is greater than or equal to current node's key.
			} else {
				
				// Checks if the new keys and the new value is identical to the 
				// keys and values of the current node. This is to avoid duplicates.
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
	
	/**
	 * Inserts the key-value pair in the kd-tree. Two different values may have
	 * identical keys. However, two equal values may not have identical keys and
	 * a key-value pair will not be added to the KD-tree in such cases. 
	 * Equality is checked by the equals method.
	 * 
	 * @param keys
	 * - the keys with which to search for the value. It has to be an array of 
	 * objects implementing the comparable interface. Keys also has to be
	 * as long as the number of dimensions in the tree.
	 * 
	 * @param value
	 * - the value to insert in the tree.
	 * 
	 * @throws NullPointerException if keys or value are null.
	 * @throws IllegalArgumentException if the length of keys isn't the same
	 * as the kd-tree's dimension size.
	 */
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
	 * Checks that all keys in the base array is less than or equal to the
	 * corresponding key in the other array.
	 *  
	 * @param base
	 * - the array that is the base for the comparison.
	 * @param other
	 * - the array to compare the base array with. 
	 * @return
	 * - true if all keys in base is less than or equals to the corresponding
	 * key in other
	 */
	private boolean isNotLarger(Comparable[] base, Comparable[] other){
		for (int i=0; i < DIMENSIONS; ++i){
			if (base[i].compareTo( other[i] ) <= 0){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Retrieves all the values that are within the given range. 
	 * The range is kept between values that are found at the same index in the
	 * argument arrays highest and lowest.
	 * 
	 * @param lowest
	 * An array of the lower bound values.
	 * 
	 * @param highest
	 * An array of the higher bound values.
	 * 
	 * @return
	 * A set containing all the values within the range. If the root is empty an empty set is returned. 
	 * If the root is empty it returns an empty set.
	 * 
	 * @throws NullPointerException if any of the arguments are null.
	 * 
	 * @throws IllegalArgumentException if the argument arrays have
	 * to few or many elements.
	 */
	public Set<T> range(Comparable[] lowest, Comparable[] highest){
		if (lowest == null || highest == null){
			throw new NullPointerException();
		}
		if(lowest.length != DIMENSIONS || highest.length != DIMENSIONS){
			throw new IllegalArgumentException("The input arrays must have as many elements as there are dimensions.");
		}
		if ( isNotLarger(lowest, highest) ){
			throw new IllegalArgumentException("The keys in highest can't be less than lowest.");
		}
		if(root == null){
			return new HashSet<T>();
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
					allKeysWithinRange = false; //found value outside range
				}else{
					tempLevel = incrementDimension(tempLevel);
					currKey = currNode.getKey(tempLevel);
				}
			}while(tempLevel != level && allKeysWithinRange); //Goes through all keys in current node,  
															  //checks if they are valid
			if(allKeysWithinRange){
				correctVals.add(currNode.getValue());
			}
			
			if(currNode.getLeftChild() != null && roomForSmallerKeys(currNode, level, lowest[level])){ 
				nodesToVisit.push(currNode.getLeftChild());	
				stackNodeLevel.push(incrementDimension(level));
			}
			if(currNode.getRightChild() != null && roomForBiggerKeys(currNode, level, highest[level])){
				nodesToVisit.push(currNode.getRightChild());
				stackNodeLevel.push(incrementDimension(level));
			}
			
		}//while stack isn't empty
		
		return correctVals;
		
	}//range
	
	
	
	/**
	 * Checks if the current node's current key leaves room for additional values
	 * that are smaller but still within the given low bound.
	 * 
	 * @param node
	 * The current node that is analyzed.
	 * 
	 * @param level
	 * The global level that is compared.
	 * 
	 * @param lowBound
	 * The lowest allowed value that's allowed within the defined range.
	 * 
	 * @return
	 * True if there can exist addition allowed values, false if it can't.
	 */
	private boolean roomForSmallerKeys(Node<T> node, int level, Comparable lowBound){
		return node.getKey(level).compareTo(lowBound) > 0;
	}
	
	
	/**
	 * Checks if the current node's current key leaves room for additional values
	 * that are greater but still within the given high bound.
	 * 
	 * @param node
	 * The current node that is analyzed.
	 * 
	 * @param level
	 * The global level that is compared.
	 * 
	 * @param highBound
	 * The highest allowed value that's allowed within the defined range.
	 * 
	 * @return
	 * True if there can exist addition allowed values, false if it can't.
	 */
	private boolean roomForBiggerKeys(Node<T> node, int level, Comparable highBound){
		return node.getKey(level).compareTo(highBound) <= 0;
	}
		  
				
}

