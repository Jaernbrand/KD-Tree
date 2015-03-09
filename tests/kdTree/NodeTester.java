package kdTree;

import static org.junit.Assert.*;

import org.junit.Test;

public class NodeTester {

	@Test(expected=NullPointerException.class)
	public void testCreateNodeNullKey() {
		Node<String> node = new Node<String>(null, "A");
	}
	
	@Test(expected=NullPointerException.class)
	public void testCreateNodeNullValue() {
		Integer[] arr = {1, 2, 3};
		Node<String> node = new Node<String>(arr, null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCreateNodeToSmallKeyArray() {
		Integer[] arr = new Integer[0];
		Node<String> node = new Node<String>(arr, "A");
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void testGetKeyNull() {
		Integer[] arr = {1, 2, 3};
		Node<String> node = new Node<String>(arr, "A");
		node.getKey(-1);
	}

}
