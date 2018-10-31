package tests;

import static org.junit.jupiter.api.Assertions.*;
import java.io.FileNotFoundException;
import java.util.*;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;

import clueGame.*;

class GameSetupTests {
	private static Board board = Board.getInstance();
	
	@BeforeClass
	public static void setUp() throws FileNotFoundException, BadConfigFormatException {
		board.setConfigFiles("ClueLayout.csv", "ClueLegend.txt", "ClueWeapons.txt", "CluePlayers.txt");
		board.initialize();
	}
	
	@Test
	void test() {
		fail("Not yet implemented");
	}
	
	@Test
	void testLoadDeck() { 
		ArrayList<Card> deck = board.getDeck();
		int numRooms = 0;
		int numPeople = 0;
		int numWeapons = 0;
		boolean foundPerson = false;
		boolean foundWeapon = false;
		boolean foundRoom = false;
		for (Card card : deck) {
			switch (card.getCardType()) {
			case ROOM:
				numRooms++;
				break;
			case PERSON:
				numPeople++;
				break;
			case WEAPON:
				numWeapons++;
			};
			if (card.getCardName().equals("Observatory")) {
				foundRoom = true;
			}
			if (card.getCardName().equals("Revolver")) {
				foundWeapon = true;
			}
			if (card.getCardName().equals("Peacock")) {
				foundPerson = true;
			}
		}
		
		//test that there is the correct number of cards in the deck
		// 9 rooms, 6 people, 6 weapons = 21 total cards
		assertEquals(deck.size(), 21);
		//test that there is correct # of each type of card
		
		assertEquals(numRooms, 9);
		assertEquals(numPeople, 6);
		assertEquals(numWeapons, 6);
		
		//chose a name of a person, room and weapon and make sure that they are in the deck
		assertTrue(foundPerson);
		assertTrue(foundWeapon);
		assertTrue(foundRoom);
	}

}
