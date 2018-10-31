package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.*;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.*;

class GameSetupTests {

	private static Board board = Board.getInstance();
	
//	Setup and initialize the board
	@BeforeAll
	public static void setUp() throws FileNotFoundException, BadConfigFormatException {
		board.setConfigFiles("ClueLayout.csv", "ClueLegend.txt", "ClueWeapons.txt", "CluePlayers.txt");
		board.initialize();
	}
	
	@Test
	void testLoadPeople() {
		Player first = board.getPlayer("Scarlett");
		Player third = board.getPlayer("Peacock");
		Player last = board.getPlayer("White");
		Player human = board.getPlayer("Green");
		Player computer = board.getPlayer("Mustard");
	
		//Test that the player's names are set correctly 
		assertEquals("Scarlett", first.getPlayerName());
		assertEquals("Peacock", third.getPlayerName());
		assertEquals("White", last.getPlayerName());
		assertEquals("Green", human.getPlayerName());
		assertEquals("Mustard", computer.getPlayerName());
		
		//Test color is set correctly
		assertEquals(first.getColor(), new Color(196, 47, 47));
		assertEquals(third.getColor(), new Color(51, 88, 198));
		assertEquals(last.getColor(), new Color(255, 255, 255));
		assertEquals(human.getColor(), new Color(20, 188, 43));
		assertEquals(computer.getColor(), new Color(198, 169, 23));
		
		//Test starting location
		assertEquals(first.getRow(), 0);
		assertEquals(first.getColumn(), 5);
		
		assertEquals(third.getRow(), 15);
		assertEquals(third.getColumn(), 23);
		
		assertEquals(last.getRow(), 7);
		assertEquals(last.getColumn(), 0);
		
		assertEquals(human.getRow(), 22);
		assertEquals(human.getColumn(), 20);
		
		assertEquals(computer.getRow(), 22);
		assertEquals(computer.getColumn(), 10);
		
	}
	
	//will make sure that the deck is loaded correctly from config file
//	@Test
//	void testLoadDeck() { 
//		ArrayList<Card> deck = board.getDeck();
//		int numRooms = 0;
//		int numPeople = 0;
//		int numWeapons = 0;
//		boolean foundPerson = false;
//		boolean foundWeapon = false;
//		boolean foundRoom = false;
//		for (Card card : deck) {
//			switch (card.getCardType()) {
//			case ROOM:
//				numRooms++;
//				break;
//			case PERSON:
//				numPeople++;
//				break;
//			case WEAPON:
//				numWeapons++;
//				break;
//			}
//			if (card.getCardName().equals("Observatory")) {
//				foundRoom = true;
//			}
//			if (card.getCardName().equals("Revolver")) {
//				foundWeapon = true;
//			}
//			if (card.getCardName().equals("Peacock")) {
//				foundPerson = true;
//			}
//		}
//		
//		//test that there is the correct number of cards in the deck
//		// 9 rooms, 6 people, 6 weapons = 21 total cards
//		assertEquals(deck.size(), 21);
//		//test that there is correct # of each type of card
//		
//		assertEquals(numRooms, 9);
//		assertEquals(numPeople, 6);
//		assertEquals(numWeapons, 6);
//		
//		//chose a name of a person, room and weapon and make sure that they are in the deck
//		assertTrue(foundPerson);
//		assertTrue(foundWeapon);
//		assertTrue(foundRoom);
//	}
	
	//make sure that cards are dealt to the players correctly
	@Test
	void testDealDeck() {
		int total_cards_dealt = 0;
		boolean duplicates_found = false;
		int least_num_cards = 2147483647; // max value for int
		int most_num_cards = 0;
		ArrayList<Card> seen_cards = new ArrayList<>();
		for (Player player : board.getPlayers()) {
			int cards_per_player = 0;
			for (Card card : player.getMyCards()) {
				cards_per_player++;
				if (!seen_cards.contains(card)) {
					seen_cards.add(card);
				}
				else {
					duplicates_found = true;
				}				
			}
			if (cards_per_player < least_num_cards) {
				least_num_cards = cards_per_player;
			}
			if (cards_per_player > most_num_cards) {
				most_num_cards = cards_per_player;
			}
			total_cards_dealt += cards_per_player;
		}
		//Total number of cards dealt to the players should be total 
		//number of cards - 3 (3 cards used for solution)
		assertEquals(total_cards_dealt, 18);
		
		//Cards should be even distributed. Player with most cards 
		//should have at most 1 more card than player with least
		//number of cards
		assertTrue(least_num_cards + 1 >= most_num_cards);
		
		//All players should have unique cards, in other words,
		//a card should be dealt only once
		assertFalse(duplicates_found);
	}
}
