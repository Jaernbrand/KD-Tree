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

	
	public void insert(Comparable[] keys, T value){
		if (keys == null || value == null){
			throw new NullPointerException("Arguments can't be null.");
		}
		if (keys.length != DIMENSIONS){
			throw new IllegalArgumentException();
		}
		
		if (root == null){
			root = new Node<T>(keys, value);
		}
	}
	
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
