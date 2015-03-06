package kdTree;

import java.util.Set;

public class KDTree<T> {
	
	private int dimensions;
	private Node<T> root;
	
	public KDTree(int dimensions){
		if (dimensions < 2){
			throw new IllegalArgumentException("USE A BINARY TREE! BITCH!");
		}
		
		this.dimensions = dimensions;
	}

	public void insert(Comparable[] keys, T value){
		if (keys == null || value == null){
			throw new NullPointerException("Arguments can't be null.");
		}
		if (keys.length != dimensions){
			throw new IllegalArgumentException();
		}
		
		if (root == null){
			root = new Node<T>(keys, value);
		}
	}
	
	public Set<T> range(Comparable[] lowest, Comparable[] highest){
		
	}
}
