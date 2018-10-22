
package tests;

import java.io.FileNotFoundException;

/**
 * @author Carl Schader
 * @author Josh Hallinan
 */
/*
 * This program tests that adjacencies and targets are calculated correctly.
 */

import java.util.Set;

//Doing a static import allows me to write assertEquals rather than
//assertEquals
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.BoardCell;

public class BoardAdjTargetTests {
	// We make the Board static because we can load it one time and 
	// then do all the tests. 
	private static Board board;
	@BeforeClass
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueLegend.txt");		
		// Initialize will load BOTH config files 
		try {
			board.initialize();
		} catch (FileNotFoundException | BadConfigFormatException e) {
			e.toString();
		}
	}
	
	// Locations within rooms 
	// Ensure that player does not move around within room
	// Marked ORANGE on the planning spreadsheet
	@Test
	public void testAdjacenciesInsideRooms()
	{
		// Test a corner of the board, also is edge piece test
		Set<BoardCell> testList = board.getAdjList(22, 0);
		assertEquals(0, testList.size());
		// Test another corner of the board, also is edge piece test
		testList = board.getAdjList(0, 23);
		assertEquals(0, testList.size());
		// Test room cell which has walkway underneath
		testList = board.getAdjList(5, 1);
		assertEquals(0, testList.size());
		// Test room cell with walkway above
		testList = board.getAdjList(5, 12);
		assertEquals(0, testList.size());
		// Test cell in middle of room
		testList = board.getAdjList(9, 3);
		assertEquals(0, testList.size());
		// Test room cell beside a door
		testList = board.getAdjList(3, 9);
		assertEquals(0, testList.size());
		// Test room cell in a corner of room
		testList = board.getAdjList(6, 8);
		assertEquals(0, testList.size());
	}

	// Ensure that the adjacency list from a doorway is only the
	// walkway.  
	// Marked PURPLE on the planning spreadsheet
	@Test
	public void testAdjacencyRoomExit()
	{
		// TEST DOORWAY RIGHT 
		Set<BoardCell> testList = board.getAdjList(2, 4);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(2, 5)));
		// TEST DOORWAY LEFT 
		testList = board.getAdjList(2, 19);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(2, 18)));
		//TEST DOORWAY DOWN
		testList = board.getAdjList(7, 21);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(8, 21)));
		//TEST DOORWAY UP
		testList = board.getAdjList(8, 5);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(7, 5)));
		//TEST DOORWAY RIGHT, WITH A WALKWAY BELOW
		testList = board.getAdjList(17, 19);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(17, 20)));
		
	}
	
	// Test adjacency at entrance to rooms
	// Marked GREEN in planning spreadsheet
	@Test
	public void testAdjacencyDoorways()
	{
		// Test beside a door direction RIGHT
		Set<BoardCell> testList = board.getAdjList(3, 5);
		assertTrue(testList.contains(board.getCellAt(3, 4)));
		assertTrue(testList.contains(board.getCellAt(2, 5)));
		assertTrue(testList.contains(board.getCellAt(3, 6)));
		assertTrue(testList.contains(board.getCellAt(4, 5)));
		assertEquals(4, testList.size());
		// Test beside a door direction DOWN
		testList = board.getAdjList(8, 21);
		assertTrue(testList.contains(board.getCellAt(7, 21)));
		assertTrue(testList.contains(board.getCellAt(8, 20)));
		assertTrue(testList.contains(board.getCellAt(8, 22)));
		assertEquals(3, testList.size());
		// Test beside a door direction LEFT
		testList = board.getAdjList(19, 16);
		assertTrue(testList.contains(board.getCellAt(18, 16)));
		assertTrue(testList.contains(board.getCellAt(20, 16)));
		assertTrue(testList.contains(board.getCellAt(19, 17)));
		assertEquals(3, testList.size());
		// Test beside a door direction UP
		testList = board.getAdjList(7, 5);
		assertTrue(testList.contains(board.getCellAt(6, 5)));
		assertTrue(testList.contains(board.getCellAt(8, 5)));
		assertTrue(testList.contains(board.getCellAt(7, 4)));
		assertTrue(testList.contains(board.getCellAt(7, 6)));
		assertEquals(4, testList.size());
	}

	// Test a variety of walkway scenarios
	// These tests are LIGHT PURPLE on the planning spreadsheet
	@Test
	public void testAdjacencyWalkways()
	{
		// Test on top edge of board
		Set<BoardCell> testList = board.getAdjList(0, 6);
		assertTrue(testList.contains(board.getCellAt(0, 5)));
		assertTrue(testList.contains(board.getCellAt(0, 7)));
		assertTrue(testList.contains(board.getCellAt(1, 6)));
		assertEquals(3, testList.size());
		
		// Test on left edge of board, next to 1 room piece
		testList = board.getAdjList(7, 0);
		assertTrue(testList.contains(board.getCellAt(6, 0)));
		assertTrue(testList.contains(board.getCellAt(7, 1)));
		assertEquals(2, testList.size());

		// Test between two rooms, walkways right and left
		testList = board.getAdjList(8, 22);
		assertTrue(testList.contains(board.getCellAt(8, 21)));
		assertTrue(testList.contains(board.getCellAt(8, 23)));
		assertEquals(2, testList.size());

		// Test only walkways adjacent
		testList = board.getAdjList(18,6);
		assertTrue(testList.contains(board.getCellAt(18, 5)));
		assertTrue(testList.contains(board.getCellAt(18, 7)));
		assertTrue(testList.contains(board.getCellAt(17, 6)));
		assertTrue(testList.contains(board.getCellAt(19, 6)));
		assertEquals(4, testList.size());
		
		// Test on bottom edge of board, next to 1 room piece
		testList = board.getAdjList(10, 22);
		assertTrue(testList.contains(board.getCellAt(9, 22)));
		assertTrue(testList.contains(board.getCellAt(10, 21)));
		assertEquals(2, testList.size());
		
		// Test on right edge of board
		testList = board.getAdjList(14, 23);
		assertTrue(testList.contains(board.getCellAt(14, 22)));
		assertTrue(testList.contains(board.getCellAt(13, 23)));
		assertTrue(testList.contains(board.getCellAt(15, 23)));
		assertEquals(3, testList.size());

		// Test on walkway next to  door that is not in the needed
		// direction to enter
		testList = board.getAdjList(7, 20);
		assertTrue(testList.contains(board.getCellAt(7, 19)));
		assertTrue(testList.contains(board.getCellAt(6, 20)));
		assertTrue(testList.contains(board.getCellAt(8, 20)));
		assertEquals(3, testList.size());
	}
	
	
	// Tests of walkway targets, 1 step, when on edge of board
	// and beside room
	// Only testing two edges of the board
	// Marked LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsOneStep() {
		board.calcTargets(22, 20, 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCellAt(22, 21)));
		assertTrue(targets.contains(board.getCellAt(21, 20)));	
		
		board.calcTargets(0, 17, 1);
		targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCellAt(0, 18)));
		assertTrue(targets.contains(board.getCellAt(1, 17)));		
	}
	
	// Tests of walkway targets, 2 steps
	// Marked LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsTwoSteps() {
		board.calcTargets(3, 18, 2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(2, 18)));
		assertTrue(targets.contains(board.getCellAt(4, 18)));
		assertTrue(targets.contains(board.getCellAt(3, 17)));
		
		board.calcTargets(10, 8, 2);
		targets= board.getTargets();
		assertEquals(7, targets.size());
		assertTrue(targets.contains(board.getCellAt(8, 8)));
		assertTrue(targets.contains(board.getCellAt(9, 7)));
		assertTrue(targets.contains(board.getCellAt(11, 7)));
		assertTrue(targets.contains(board.getCellAt(12, 8)));	
		assertTrue(targets.contains(board.getCellAt(11, 9)));
		assertTrue(targets.contains(board.getCellAt(10, 10)));	
		assertTrue(targets.contains(board.getCellAt(9, 9)));
	}
	
	// Tests of just walkways, 4 steps
	// Marked LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsFourSteps() {
		board.calcTargets(1, 7, 4);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCellAt(1, 5)));
		assertTrue(targets.contains(board.getCellAt(3, 5)));
		assertTrue(targets.contains(board.getCellAt(2, 6)));
		assertTrue(targets.contains(board.getCellAt(4, 6)));
		assertTrue(targets.contains(board.getCellAt(3, 7)));
		assertTrue(targets.contains(board.getCellAt(5, 7)));
		
		// Includes a path that doesn't have enough length
		board.calcTargets(6, 1, 4);
		targets= board.getTargets();
		assertEquals(5, targets.size());
		assertTrue(targets.contains(board.getCellAt(6, 5)));
		assertTrue(targets.contains(board.getCellAt(7, 4)));
		assertTrue(targets.contains(board.getCellAt(6, 3)));	
		assertTrue(targets.contains(board.getCellAt(7, 2)));	
		assertTrue(targets.contains(board.getCellAt(7, 0)));	
	}	
	
	// Tests of just walkways plus one door, 6 steps
	// Marked LIGHT BLUE on the planning spreadsheet

	@Test
	public void testTargetsSixSteps() {
		board.calcTargets(6, 1, 6);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(8, targets.size());
		assertTrue(targets.contains(board.getCellAt(6, 3)));
		assertTrue(targets.contains(board.getCellAt(7, 4)));	
		assertTrue(targets.contains(board.getCellAt(4, 5)));	
		assertTrue(targets.contains(board.getCellAt(6, 5)));	
		assertTrue(targets.contains(board.getCellAt(8, 5)));	
		assertTrue(targets.contains(board.getCellAt(5, 6)));	
		assertTrue(targets.contains(board.getCellAt(7, 6)));
		assertTrue(targets.contains(board.getCellAt(6, 7)));
	}	
	
	// Test getting into a room
	// Marked LIGHT BLUE on the planning spreadsheet

	@Test 
	public void testTargetsIntoRoom()
	{
		// One room is exactly 2 away
		board.calcTargets(13, 4, 2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(4, targets.size());
		// into room (Down->Left)
		assertTrue(targets.contains(board.getCellAt(14, 3)));
		// walkways
		assertTrue(targets.contains(board.getCellAt(15, 4)));
		assertTrue(targets.contains(board.getCellAt(11, 4)));		
		assertTrue(targets.contains(board.getCellAt(12, 5)));
	}
	
	// Test getting into room, doesn't require all steps
	// Marked LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsIntoRoomShortcut() 
	{
		board.calcTargets(18, 9, 3);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(7, targets.size());
		// shortcut room
		assertTrue(targets.contains(board.getCellAt(18, 11)));
		// into room in 3 steps
		assertTrue(targets.contains(board.getCellAt(19, 11)));
		// walkway combinations
		assertTrue(targets.contains(board.getCellAt(15, 9)));
		assertTrue(targets.contains(board.getCellAt(16, 10)));
		assertTrue(targets.contains(board.getCellAt(18, 10)));
		assertTrue(targets.contains(board.getCellAt(20, 10)));
		assertTrue(targets.contains(board.getCellAt(21, 9)));
	}

	// Test getting out of a room
	// Marked LIGHT BLUE on the planning spreadsheet
	@Test
	public void testRoomExit()
	{
		// Take one step, essentially just the adj list
		board.calcTargets(16, 7, 1);
		Set<BoardCell> targets= board.getTargets();
		// Ensure doesn't exit through the wall
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCellAt(15, 7)));
		// Take two steps
		board.calcTargets(16, 7, 2);
		targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCellAt(14, 7)));
		assertTrue(targets.contains(board.getCellAt(15, 8)));
	}

}
