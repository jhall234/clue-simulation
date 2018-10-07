package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.DoorDirection;

class Tests {
	
	public static final int LEGEND_SIZE = 11;
	public static final int NUM_ROWS = 23;
	public static final int NUM_COLUMNS = 24;
	public static final int NUM_DOORS = 20;
	
	private static Board board = Board.getInstance();
	
	@BeforeClass
	public static void setUp() {
		board.setConfigFiles("ClueLayout.csv", "ClueLegend.txt");		
		board.initialize();
	}
	
	@Test
	void testLegend() {
		HashMap<Character, String> legend = board.getLegend();
		
		assertEquals(LEGEND_SIZE, legend.size());
		
		assertEquals("Movie Theater", legend.get('M'));
		assertEquals("Kitchen", legend.get('K'));
		assertEquals("Art Gallery", legend.get('A'));
		assertEquals("Walkway", legend.get('W'));
	}
	
	@Test
	void testBoardDimensions() {
		assertEquals(NUM_ROWS, board.getNumRows());
		assertEquals(NUM_COLUMNS, board.getNumColumns());
	}
	
	@Test
	void testDoorDirections() {
		BoardCell room = board.getCell(2, 4);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.RIGHT, room.getDoorDirection());
		
		room = board.getCell(8, 5);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.UP, room.getDoorDirection());
		
		room = board.getCell(3, 12);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.DOWN, room.getDoorDirection());
		
		room = board.getCell(3, 8);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.LEFT, room.getDoorDirection());
		
		room = board.getCell(4, 6);
		assertFalse(room.isDoorway());
		
		room = board.getCell(4, 2);
		assertFalse(room.isDoorway());
	}
	
	@Test
	void testNumberDoors() {
		int num_doors = 0;
		for (int row=0; row<board.getNumRows(); row++) {
			for (int column=0; column<board.getNumColumns(); column++) {
				if (board.getCell(row, column).isDoorway()) {
					num_doors++;
				}
			}
		}
		assertEquals(NUM_DOORS, num_doors);
	}
	
	@Test
	void testCellInitials() {
		BoardCell room = board.getCell(0, 0);
		assertEquals(Character.valueOf('M'), room.getInitial());
		
		room = board.getCell(8, 23);
		assertEquals(Character.valueOf('W'), room.getInitial());
		
		room = board.getCell(14, 16);
		assertEquals(Character.valueOf('X'), room.getInitial());
		
		room = board.getCell(22, 19);
		assertEquals(Character.valueOf('K'), room.getInitial());
	}

}
