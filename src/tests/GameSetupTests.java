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
	
//	Setup and initialize the board, players, and cards
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
		assertEquals(new Color(196, 47, 47), first.getColor());
		assertEquals(new Color(51, 88, 198), third.getColor());
		assertEquals(new Color(255, 255, 255), last.getColor());
		assertEquals(new Color(20, 188, 43), human.getColor());
		assertEquals(new Color(198, 169, 23), computer.getColor());
		
		//Test starting location
		assertEquals(0, first.getRow());
		assertEquals(5, first.getColumn());
		
		assertEquals(15, third.getRow());
		assertEquals(23, third.getColumn());
		
		assertEquals(7, last.getRow());
		assertEquals(0, last.getColumn());
		
		assertEquals(22, human.getRow());
		assertEquals(20, human.getColumn());
		
		assertEquals(22, computer.getRow());
		assertEquals(10, computer.getColumn());
		
	}
	
	//will make sure that the deck is loaded correctly from config file
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
				break;
			}
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
		assertEquals(21, deck.size());
		//test that there is correct # of each type of card
		
		assertEquals(9, numRooms);
		assertEquals(6, numPeople);
		assertEquals(6, numWeapons);
		
		//chose a name of a person, room and weapon and make sure that they are in the deck
		assertTrue(foundRoom);
		assertTrue(foundPerson);
		assertTrue(foundWeapon);
		
	}
	
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
		assertEquals(18, total_cards_dealt);
		
		//Cards should be even distributed. Player with most cards 
		//should have at most 1 more card than player with least
		//number of cards
		assertTrue(least_num_cards + 1 >= most_num_cards);
		
		//All players should have unique cards, in other words,
		//a card should be dealt only once
		assertFalse(duplicates_found);
	}
}
