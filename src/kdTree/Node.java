package kdTree;

import java.util.Arrays;

/**
 * @author Henrik Järnbrand 
 * @author Tomas Sandberg 
 * 
 * 
 * Node is used as nodes in the KDTree.
 * An arary of Comparable-objects represent the keys stored in the node.
 * 
 * @param <T>
 * - the type of the value in the node.
 */
class Node<T>{

	T value;
	Comparable[] keys; //No type inferred because polymorphism is wanted
	Node<T> leftChild;
	Node<T> rightChild;
	
	
	Node(Comparable[] keys, T value){
		if(keys.length == 0)
			throw new IllegalArgumentException("Keys can't be empty");
		if(value == null)
			throw new NullPointerException("Value can't be null");
		
		this.keys = keys;
		this.value = value;
		leftChild = null;
		rightChild = null;
	}
	
	Node<T> getLeftChild(){
		return leftChild;
	}
	
	Node<T> getRightChild(){
		return rightChild;
	}
	
	void setLeftChild(Comparable[] keys, T value){
		leftChild = new Node<T>(keys, value);
	}
	
	void setRightChild(Comparable[] keys, T value){
		rightChild = new Node<T>(keys, value);
	}
	
	Comparable getKey(int i){
		if(i < 0)
			throw new IndexOutOfBoundsException("Index can't be negative");
		return keys[i];
	}
	
	Comparable[] getAllKeys(){
		return Arrays.copyOf(keys, keys.length);
	}
	
	T getValue(){
		return value;
	}
	
}


