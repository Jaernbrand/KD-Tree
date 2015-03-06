package kdTree;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.junit.Test;

import java.util.Arrays;
import java.util.Random;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;

public class KDTreeTester {

	KDTree<String> triDTree = new KDTree<String>(3);
	
	@Test(expected=IllegalArgumentException.class)
	public void testToSmallK() {
		KDTree<Integer> toSmall = new KDTree<Integer>(1);
	}
	
	
	@Test
	public void testInsertOneElement3DTree(){
		Integer[] keys = {4, 5, 6};
		triDTree.insert( keys, "A");
		assertTrue( triDTree.contains(keys) );
		
		Set<String> oracle = new HashSet<String>();
		oracle.add("A");
		
		assertEquals(oracle, triDTree.get(keys) );
		assertEquals( 1, triDTree.size() );
	}
	
	@Test
	public void testInsertSameValueTwice(){
		Integer[] keys = {4, 5, 6};
		triDTree.insert( keys, "A");
		assertTrue( triDTree.contains(keys) );
		
		Set<String> oracle = new HashSet<String>();
		oracle.add("A");
		
		assertEquals(oracle, triDTree.get(keys) );
		assertEquals( 1, triDTree.size() );
		
		triDTree.insert( keys, "A");
		assertTrue( triDTree.contains(keys) );
		
		assertEquals(oracle, triDTree.get(keys) );
		assertEquals( 1, triDTree.size() );
	}
	
	@Test
	public void testInsertSameKeyTwice(){
		Integer[] keys = {4, 5, 6};
		triDTree.insert( keys, "A");
		assertTrue( triDTree.contains(keys) );
		
		Set<String> oracle = new HashSet<String>();
		oracle.add("A");
		oracle.add("B");
		
		assertEquals(oracle, triDTree.get(keys) );
		assertEquals( 1, triDTree.size() );
		
		triDTree.insert( keys, "B");
		assertTrue( triDTree.contains(keys) );
		
		assertEquals(oracle, triDTree.get(keys) );
		assertEquals( 2, triDTree.size() );
	}
	
	@Test
	public void testContainsAndGet(){
		assertEquals(0, triDTree.size());
		Integer[] keys = {4, 5, 6};
		assertFalse(triDTree.contains(keys));
		assertEquals( 0, triDTree.get(keys).size() );
	}
	
	@Test
	public void testMultipleRandomInserts3DTree(){
		Random rnd = new Random();
		KDTree<Integer> triIntegerTree = new KDTree<Integer>(3);
		Map<Integer[], Integer> oracle = new HashMap<Integer[], Integer>();
		ArrayList<Integer[]> oracleList = new ArrayList<Integer[]>();
		
		Integer[] tmpArray = new Integer[3];
		for (int i=0; i < 1000; ++i){
			for (int j=0; j < tmpArray.length; ++j){
				tmpArray[j] = rnd.nextInt(1000);
			}
			int value = rnd.nextInt();
			triIntegerTree.insert( tmpArray, value );
			oracleList.add( Arrays.copyOf(tmpArray, tmpArray.length) );
			oracle.put(Arrays.copyOf(tmpArray, tmpArray.length), value);
		}
		
		assertEquals(oracleList.size(), triIntegerTree.size() );
		
		for (Integer[] arr : oracleList ){
			assertTrue( triIntegerTree.contains(arr) );
			//assertEquals(oracle.get(arr), triIntegerTree.get(arr) );
		}
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
		KDTree<String> tree = new KDTree<String>(3);

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
			tree.insert(myKeys, values[i]);
		}//for
		return tree;
	}
	
	@Test
	public void testInsertABCDE(){
		KDTree<String> KDtree = buildTestTree();
		
		//A
		Node<String> root = KDtree.getRoot();
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
	
	@Test
	public void testRangeSearch(){
		KDTree<String> KDtree = buildTestTree();
		Integer[] low = {40, 40, 40};
		Integer[] high = {60, 60, 60};
		Set<String> withinRange = KDtree.range(low, high);
		assertEquals(withinRange.size(),1);
		assertTrue(withinRange.contains("E"));
	}
	
	
}
