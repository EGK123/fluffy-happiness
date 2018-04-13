/////////////////////////////////////////////////////////////////////////////
// Semester: CS400 Spring 2018
// PROJECT: X-Team Exercise #4, Dictionary Graph
// FILES: Graph.java
// GraphTest.java
// GraphProcessor.java
// GraphProcessorTest.java
// WordProcessor.java
//
// Authors: Zach Kremer, Ege Kula
// Due date: 10:00 PM on Monday, April 16th
// Outside sources: None
//
// Instructor: Deb Deppeler (deppeler@cs.wisc.edu)
// Bugs: No known bugs
//
//////////////////////////// 80 columns wide //////////////////////////////////
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Junit test class to test class @see GraphProcessor
 *
 * @author
 */
public class GraphProcessorTest {
	
	  
	  private GraphProcessor gp;

	 



	  String expected = null;
	  
	  int expectedInt;
	  
	  int actualInt;

	  String actual = null;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		gp= new GraphProcessor();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		this.gp = null;
	}
	@Test
	public final void test1PopulateGraphWithlistTest() {
		String wordList = "listTest";
		expectedInt = 7 ;
		actualInt = gp.populateGraph(wordList);
		System.out.println(actualInt);
		if(actualInt != expectedInt) {
			fail("expected: "+expectedInt+ " actual: "+actualInt);
		}
		
	}
	@Test
	public final void test2ShortestPath() {
		String wordList = "listTest";
		 gp.populateGraph(wordList);
		 gp.shortestPathPrecomputation();
		ArrayList<String> expectedPath = new ArrayList<String>();
		expectedPath.add("CAT");
		expectedPath.add("HAT");
		expectedPath.add("HEAT");
		expectedPath.add("WHEAT");
		int count = 4;
		List<String> actualPath = gp.getShortestPath("CAT", "WHEAT");
		for(int i =0; i< count; i++) {
			expected = expectedPath.get(i);
			actual = actualPath.get(i);
			if(!(actualPath.get(i).equals(expectedPath.get(i)))){
				fail("expected: "+expected+ " actual: "+actual);
			}
		}
		
	}
	@Test
	public final void test3ShortestPathSameWord() {
		String wordList = "listTest";
		expected="Rat";
		actual ="";
		 gp.populateGraph(wordList);
		 gp.shortestPathPrecomputation();
		 ArrayList<String> expectedPath = new ArrayList<String>();
			
			int count = 1;
			List<String> actualPath = gp.getShortestPath("RAT", "RAT");
		actual = actualPath.get(0);
				if(!(actualPath.contains("RAT"))){
					fail("expected: "+expected+ " actual: "+actual);
				}
			}
	
	@Test
	public final void test4ShortestDistance() {
		String wordList = "listTest";
		 gp.populateGraph(wordList);
		 gp.shortestPathPrecomputation();
		expectedInt = 3;
		
		int actualInt = gp.getShortestDistance("CAT", "WHEAT");
	
			if(!(actualInt == expectedInt)){
				fail("expected: "+expectedInt+ " actual: "+actualInt);
			}
		}
	
	@Test
	public final void test5ShortestDistanceSameWord() {
		String wordList = "listTest";
		 gp.populateGraph(wordList);
		 gp.shortestPathPrecomputation();
		expectedInt = 0;
		
		int actualInt = gp.getShortestDistance("CAT", "CAT");
	
			if(!(actualInt == expectedInt)){
				fail("expected: "+expectedInt+ " actual: "+actualInt);
			}
		}
	
	@Test
	public final void test6ShortestPathPrecomputation() {
		String wordList = "shortTest";
		ArrayList<String> paths = new ArrayList<String>();
		paths.add("(CAT->CAT): CAT");
		paths.add("(CAT->RAT): CAT,RAT");
		paths.add("(RAT->CAT): RAT,CAT");
		paths.add("(RAT->RAT): RAT");
		paths.add("(RAT->RIT): RAT,RIT");
		paths.add("(RIT->RIT): RIT");
		paths.add("(CAT->RIT): CAT,RAT,RIT");
		
		gp.populateGraph(wordList);
		gp.shortestPathPrecomputation();
		expectedInt = 0;
		int i=0;
		for (ArrayList<String> a : gp.paths) {
		      for (String s : a) {
		    	  expected=s;
		        System.out.println(s);
		        if(!(s.contains(paths.get(i)))) {
		        	actual = "path not found";
					fail("expected: "+expected+ " actual: "+actual);
		        }
		        i++;
		      }
		    }
	
		}
	
}
