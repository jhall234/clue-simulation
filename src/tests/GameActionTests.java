package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.*;

class GameActionTests {
	
	private static Board board = Board.getInstance();
	
//	Setup and initialize the board, players, and cards
	@BeforeAll
	public static void setUp() throws FileNotFoundException, BadConfigFormatException {
		board.setConfigFiles("ClueLayout.csv", "ClueLegend.txt", "ClueWeapons.txt", "CluePlayers.txt");
		board.initialize();
	}

	@Test
	void selectRoomNotVisited() {
		ComputerPlayer player = new ComputerPlayer();
		
		//at this location player can only go into the Dining Room or Kitchen
		board.calcTargets(19, 16, 4);
		
		// player should select the room that is not stored in the Just VisitedRoom variable
		BoardCell correctRoom = board.getCellAt(19, 17);
		BoardCell justVisitedRoom = new BoardCell(20,15);
		
		// run multiple times to make sure computer didn't get lucky
		for (int i = 0; i < 25; i++) {
			player.setJustVisitedRoom(justVisitedRoom);
			assertEquals(correctRoom, player.pickLocation(board.getTargets()));
		}
	}
	
	@Test 
	void randomTargetSelection() {
		ComputerPlayer player = new ComputerPlayer();
		
		// choose cell and distance that does not include any rooms 
		board.calcTargets(20, 20, 2);
		boolean cell_22_20 = false;
		boolean cell_21_21 = false;
		boolean cell_18_20 = false;
		
		// Run random selection a large number of times
		for (int i=0; i<100; i++) {
			BoardCell selected = player.pickLocation(board.getTargets());
			if (selected == board.getCellAt(22, 20)) {
				cell_22_20 = true;
			}
			else if (selected == board.getCellAt(21, 21)) {
				cell_21_21 = true;				
			}
			else if (selected == board.getCellAt(18, 20)) {
				cell_18_20 = true;
			}
			else 
				fail("Invalid target selected");
		}
		// Make sure that each possible target was chosen at least once
		assertTrue(cell_22_20);
		assertTrue(cell_21_21);
		assertTrue(cell_18_20);		
	}
	
	// Will test if there is only one room to choose and that room was last visited
	// The room selection in this test should be random between the room and any possible walkway cells
	@Test
	void roomIsLastVisited() {
		ComputerPlayer player = new ComputerPlayer();
		
		// choose cell & distance that can reach only one room
		board.calcTargets(3, 15, 2);
		boolean cell_2_14 = false;
		boolean cell_2_16 = false;
		boolean cell_3_17 = false;
		BoardCell justVisitedRoom = new BoardCell(2,14);
		
		// Run random selection a large number of times
		for (int i=0; i<100; i++) {
			// Set just visited room to the only room in the target list 
			player.setJustVisitedRoom(justVisitedRoom);
			
			BoardCell selected = player.pickLocation(board.getTargets());
			if (selected == board.getCellAt(2, 14)) {
				cell_2_14 = true;
			}
			else if (selected == board.getCellAt(2, 16)) {
				cell_2_16 = true;				
			}
			else if (selected == board.getCellAt(3, 17)) {
				cell_3_17 = true;
			}
			else 
				fail("Invalid target selected");
		}
		
		// Make sure that each possible target was chosen at least once
				assertTrue(cell_2_14);
				assertTrue(cell_2_16);
				assertTrue(cell_3_17);
	}
	
	// Will test the checkAccusation method in Board
	void checkAccustaion() {
		
		
		board.setSolution(new Solution("Scarlett", "Library", "Rope"));
		
		assertTrue(board.checkAccusation(new Solution("Scarlett", "Library", "Rope")));
		
		//Test each: incorrect person, room and weapon
		assertFalse(board.checkAccusation(new Solution("Plum", "Library", "Rope")));
		assertFalse(board.checkAccusation(new Solution("Scarlett", "Dining Room", "Rope")));
		assertFalse(board.checkAccusation(new Solution("Scarlett", "Library", "Candlestick")));
	}
}
