package kdTree;

import static org.junit.Assert.*;

import java.util.Random;

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
	
//	@Test
//	public void testRandomInsertions(){
//		KDTree<String> fuknTree = new KDTree<String>(3);
//
//		Integer keys[] = new Integer[100];
//		for(int i = 0; i < keys.length; ++i)
//			keys[i] = i;
//		
//		Random rand = new Random();
//		String values[] = {"A","B","C","D","E"};
//		
//		for(int i = 0; i < values.length; ++i){
//			Integer myKeys[] = new Integer[3];
//			for(int j = 0; j < 3; ++j){
//				myKeys[j] = rand.nextInt(99);
//			}//for
//			
//			fuknTree.insert(myKeys, values[i]);
//		}//for
//		
//	}
	
	
	private KDTree<String> buildTestTree(){
		KDTree<String> fuknTree = new KDTree<String>(3);

		Integer keys[] = {45,60,32,20,40,15,56,78,9,80,55,11,43,42,60};
		
		String values[] = {"A","B","C","D","E"};
		
		int j = 0;
		int lapNr = 1;
		for(int i = 0; i < values.length; ++i){
			Integer myKeys[] = new Integer[3];
			int x = 0;
			for( ; j < lapNr*3; ++j){
				myKeys[x] = keys[j];
				x++;
			}//for
			x = 0;
			lapNr++;
			fuknTree.insert(myKeys, values[i]);
		}//for
		return fuknTree;
	}
	
	@Test
	public void testInsertABCDE(){
		KDTree<String> fuknTree = buildTestTree();
		
		//A
		Node<String> root = fuknTree.getRoot();
		assertEquals(root.getValue(),"A");
		Integer OracleA[] = {45,60,32};
		Comparable keysForA[] = root.getAllKeys();
		for(int i = 0; i < 3; ++i)
			assertEquals(OracleA[i], keysForA[i]);
		
		//B
		Node<String> leftChildNode = root.getLeftChild();
		assertEquals(leftChildNode.getValue(),"B");
		Integer OracleB[] = {20,40,15};
		Comparable keysForB[] = leftChildNode.getAllKeys();
		for(int i = 0; i < 3; ++i)
			assertEquals(OracleB[i], keysForB[i]);
		
		//C
		Node<String> rightChildNode = root.getRightChild();
		assertEquals(rightChildNode.getValue(), "C");
		Integer OracleC[] = {56,78,9};
		Comparable keysForC[] = rightChildNode.getAllKeys();
		for(int i = 0; i < 3; ++i)
			assertEquals(OracleC[i], keysForC[i]);
		
		//D
		Node<String> rightLeftChildNode = rightChildNode.getLeftChild();
		assertEquals(rightLeftChildNode.getValue(), "D");
		Integer OracleD[] = {80,55,11};
		Comparable keysForD[] = rightLeftChildNode.getAllKeys();
		for(int i = 0; i < 3; ++i)
			assertEquals(OracleD[i], keysForD[i]);
		
		//E
		Node<String> leftRightChildNode = leftChildNode.getRightChild();
		assertEquals(leftRightChildNode.getValue(),"E");
		Integer OracleE[] = {43,42,60};
		Comparable keysForE[] = leftRightChildNode.getAllKeys();
		for(int i = 0; i < 3; ++i)
			assertEquals(OracleE[i], keysForE[i]);
		
	}
	
	
}
