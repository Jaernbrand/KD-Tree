package kdTree;

import static org.junit.Assert.*;

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
	
}
