package kdTree;

import static org.junit.Assert.*;

import org.junit.Test;

public class KDTreeTester {

	KDTree<String> triDTree = new KDTree<String>(3);
	
	@Test(expected=IllegalArgumentException.class)
	public void testToSmallK() {
		KDTree<Integer> toSmall = new KDTree<Integer>(1);
	}
	
	
	@Test
	public void testInsertOneElement(){
		Integer[] keys = {4, 5, 6};
		triDTree.insert( keys, "A");
		assertTrue( triDTree.contains(keys) );
		assertEquals("A", triDTree.get(keys) );
	}
	
}
