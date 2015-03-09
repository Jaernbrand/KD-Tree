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
	
	@Test(expected=NullPointerException.class)
	public void testNullGet(){
		triDTree.get(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testGetArgumentToSmall(){
		Integer[] arr = {1, 2};
		triDTree.get(arr);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testGetArgumentToLarge(){
		Integer[] arr = {1, 2, 3, 4};
		triDTree.get(arr);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testInsertArgumentToSmall(){
		Integer[] arr = {1, 2};
		triDTree.insert(arr, "A");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testInsertArgumentToLarge(){
		Integer[] arr = {1, 2, 3, 4};
		triDTree.insert(arr, "A");
	}
	
	@Test(expected=NullPointerException.class)
	public void testInsertNull(){
		triDTree.insert(null, "A");
	}
	
	@Test(expected=NullPointerException.class)
	public void testRangeLowestNull(){
		Integer[] arr = {1, 2, 3};
		triDTree.range(null, arr);
	}
	
	@Test(expected=NullPointerException.class)
	public void testRangeHighestNull(){
		Integer[] arr = {1, 2, 3};
		triDTree.range(arr, null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testRangeArgumentToLarge(){
		Integer[] arr = {1, 2, 3, 4};
		Integer[] arr2 = {4, 5, 6};
		triDTree.range(arr, arr2);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testRangeArgumentToSmall(){
		Integer[] arr = {1, 2};
		Integer[] arr2 = {4, 5, 6};
		triDTree.range(arr, arr2);
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
		
		triDTree.insert( keys, "B");
		
		Set<String> oracle = new HashSet<String>();
		oracle.add("A");
		oracle.add("B");
		
		assertEquals(oracle, triDTree.get(keys) );
		assertEquals( 2, triDTree.size() );

		triDTree.insert( keys, "A");
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
		
		ArrayList<Integer[]> oracleList = new ArrayList<Integer[]>();
		ArrayList<Set<Integer>> oracleValueList = new ArrayList<Set<Integer>>();
		
		Integer[] tmpArray = new Integer[3];
		for (int i=0; i < 2; ++i){
			for (int j=0; j < tmpArray.length; ++j){
				tmpArray[j] = rnd.nextInt(1000);
			}
			int value = rnd.nextInt();
			triIntegerTree.insert( tmpArray, value );
			
			int oracleValueIndex = -1;
			for (int j=0; j < oracleList.size(); ++j){ // Henrik kommer gråta om han ser det här...
				if ( Arrays.deepEquals(oracleList.get(j), tmpArray) ){
					oracleValueIndex = j;
					break;
				}
			}
			
			if (oracleValueIndex < 0){
				Set<Integer> newOracleValue = new HashSet<Integer>();
				newOracleValue.add(value);
				oracleValueList.add( newOracleValue );
				oracleList.add( Arrays.copyOf(tmpArray, tmpArray.length) );
			} else {
				oracleValueList.get(oracleValueIndex).add(value);
			}
		}
		
		assertEquals(oracleList.size(), triIntegerTree.size() );
		
		for (Integer[] arr : oracleList ){
			assertTrue( triIntegerTree.contains(arr) );
			
			int idx = -1;
			for (int j=0; j < oracleList.size(); ++j){
				if ( Arrays.deepEquals(oracleList.get(j), arr) ){ // ... och det här.
					idx = j;
					break;
				}
			}
			
			assertEquals(oracleValueList.get(idx), triIntegerTree.get(arr));
		}
	} // testMultipleRandomInserts3DTree
	
	private <T> ArrayList<T> convertArrayToArrayList(T[] array){
		ArrayList<T> arrList = new ArrayList<T>();
		for (int i=0; i < array.length; ++i){
			arrList.add(array[i]);
		}
		return arrList;
	}
	
	private Integer[] constructRndIntegerArray(Random rnd, int ceiling){
		Integer[] tmpArray = new Integer[3];
		for (int j=0; j < tmpArray.length; ++j){
			tmpArray[j] = rnd.nextInt(ceiling);
		}
		return tmpArray;
	}
	
	private boolean isInRange(Comparable[] oracleKey, Comparable[] lowest, Comparable[] highest, int dimensions){
		for (int i=0; i < dimensions; ++i){
			if (oracleKey[i].compareTo(lowest[i]) < 0 || oracleKey[i].compareTo(highest[i]) > 0){
				return false;
			}
		}
		
		return true;
	}
	
	@Test
	public void testMultipleRandomOperations(){
		Random rnd = new Random();
		KDTree<Integer> tree = new KDTree<Integer>(3);
		Map<ArrayList<Integer>, Set<Integer>> oracle = new HashMap<ArrayList<Integer>, Set<Integer>>();
		
		for (int i=0; i < 1000; ++i){
			Integer[] tmpArray = constructRndIntegerArray(rnd, 1000);
			
			Integer newValue = rnd.nextInt(2000);
			tree.insert( tmpArray, newValue);
			
			Set<Integer> values = oracle.get(convertArrayToArrayList(tmpArray));
			if (values == null){
				values = new HashSet<Integer>();
			}
			values.add(newValue);
			
			oracle.put(convertArrayToArrayList(tmpArray), values);
			/*
			while (tree.size() > 0 && rnd.nextBoolean() ){
				tree.remove(); // remove random element
			}*/
			
			if (rnd.nextBoolean()){
				Integer[] lowest = constructRndIntegerArray(rnd, 1000);
				Integer[] highest = constructRndIntegerArray(rnd, 1000);
				Set<Integer> range = tree.range(lowest, highest);
				
				assertTrue(range.size() <= tree.size());
				for (ArrayList<Integer> li : oracle.keySet()){
					Comparable[] oracleKey = new Comparable[3];
					li.toArray(oracleKey);
					if ( isInRange(oracleKey, lowest, highest, 3) ){
						Set<Integer> vv = oracle.get(li);
						for (Integer v : vv)
							assertTrue(range.contains( v ));
					}
				}
				
			}
		} // for-loop
		
		assertTrue( oracle.keySet().size() <= tree.size() );
		
		for (ArrayList<Integer> li : oracle.keySet() ){
			Comparable[] currKey = new Comparable[3];
			li.toArray(currKey);
			assertTrue( tree.contains( currKey ) );
			
			assertEquals(oracle.get(li), tree.get(currKey) );
		}
	} // testMultipleRandomOperations
	
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
	public void testRangeSearch40to60(){
		KDTree<String> KDtree = buildTestTree();
		Integer[] low = {40, 40, 40};
		Integer[] high = {60, 60, 60};
		Set<String> withinRange = KDtree.range(low, high);
		assertEquals(withinRange.size(),1);
		assertTrue(withinRange.contains("E"));
	}
	
	@Test
	public void testRangeSearch2(){
		KDTree<String> KDtree = buildTestTree();
		Integer[] low = {40, 40, 10};
		Integer[] high = {80, 60, 40};
		Set<String> withinRange = KDtree.range(low, high);
		assertEquals(withinRange.size(),2);
		assertTrue(withinRange.contains("A"));
		assertTrue(withinRange.contains("D"));
	}
	
}




