package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.Player;

class gameActionTests {
	
	private static Board board = Board.getInstance();
	
//	Setup and initialize the board, players, and cards
	@BeforeAll
	public static void setUp() throws FileNotFoundException, BadConfigFormatException {
		board.setConfigFiles("ClueLayout.csv", "ClueLegend.txt", "ClueWeapons.txt", "CluePlayers.txt");
		board.initialize();
	}

	@Test
	void selectATarget() {
		Player player = board.getPlayer("Plum");
		
	}

}
