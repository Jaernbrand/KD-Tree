package kdTree;

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
	
	Comparable getKey(int i){
		if(i < 0)
			throw new IndexOutOfBoundsException("Index can't be negative");
		return keys[i];
	}
	
	
	
}
