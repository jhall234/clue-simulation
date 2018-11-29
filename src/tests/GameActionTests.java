package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;

import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.*;

class GameActionTests {
	
	private static Board board = Board.getInstance();
	private static Card scarlettCard;
	private static Card plumCard;
	private static Card ropeCard;
	private static Card dumbbellCard;
	private static Card diningRoomCard;
	private static Card saunaCard;
	
//	Setup and initialize the board, players, and cards
	@BeforeAll
	public static void setUp() throws FileNotFoundException, BadConfigFormatException {
		board.setConfigFiles("ClueLayout.csv", "ClueLegend.txt");
		board.initialize();
		ControlGUI control  = new ControlGUI();
		board.setControlGUI(control);
	}
	
	@BeforeClass
	public static void createCards() {
		scarlettCard = new Card("Scarlett", CardType.PERSON);
		plumCard = new Card("Plum", CardType.PERSON);		
		ropeCard = new Card("Rope", CardType.WEAPON);		
		dumbbellCard = new Card("Dumbbell", CardType.WEAPON);		
		diningRoomCard = new Card("Dining Room", CardType.ROOM);		
		saunaCard = new Card("Sauna", CardType.ROOM);				
	}

	@Test
	void selectRoomNotVisited() {
		ComputerPlayer player = new ComputerPlayer();
		
		//at this location player can only go into the Dining Room or Kitchen
		board.calcTargets(19, 16, 4);
		
		// player should select the room that is not stored in the Just VisitedRoom variable
		BoardCell correctRoom = board.getCellAt(19, 17);
		BoardCell justVisitedRoom = board.getCellAt(20,15);
		
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
		BoardCell justVisitedRoom = board.getCellAt(2,14);
		
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
	@Test
	void checkAccustaion() {		
		board.setSolution(new Solution("Scarlett", "Library", "Rope"));
		
		assertTrue(board.checkAccusation(new Solution("Scarlett", "Library", "Rope")));
		
		//Test each: incorrect person, room and weapon
		assertFalse(board.checkAccusation(new Solution("Plum", "Library", "Rope")));
		assertFalse(board.checkAccusation(new Solution("Scarlett", "Dining Room", "Rope")));
		assertFalse(board.checkAccusation(new Solution("Scarlett", "Library", "Candlestick")));
	}
	
	// Will test creating a suggestion of weapons from multiple values 
	@Test
	void createRadomWeaponSuggestion() {
		ArrayList<String> seenCards = new ArrayList<>();
		HashSet<Card> excludedCards = new HashSet<>();
		ComputerPlayer player = new ComputerPlayer();
		boolean chooseCandlestick = false;
		boolean chooseLeadPipe = false;
		boolean chooseRope = false;
		
		//Adding all weapons but three to seenCards list. Computer will have to choose one of 
		// the three cards in the suggestion
		excludedCards.add(Board.getInstance().getCard("Candlestick"));
		excludedCards.add(Board.getInstance().getCard("Lead Pipe"));
		excludedCards.add(Board.getInstance().getCard("Rope"));
		
		//Need to add default cards for ComputerPlayer to select to create a suggestion
		excludedCards.add(Board.getInstance().getCard("Dining Room"));
		excludedCards.add(Board.getInstance().getCard("White"));
		
		//Adding all of the non-excluded cards to the seenCards list
		for (Card card : board.getDeck()) {
			if (!excludedCards.contains(card)) {
				seenCards.add(card.getCardName());
			}
		}
		
		//Set the seen cards so player only has 3 choices for the weapon and 1 choice for
		// person and room
		player.setSeenCards(seenCards);
		
		// test random selection multiple times 
		for (int i = 0; i < 100; i++) {
			player.createSuggestion("Dining Room");
			if (player.getSuggestion().getWeapon().equals("Candlestick")) {
				chooseCandlestick = true;				
			}	
			else if (player.getSuggestion().getWeapon().equals("Lead Pipe")) {
				chooseLeadPipe = true;
			}
			else if (player.getSuggestion().getWeapon().equals("Rope")) {
				chooseRope = true;
			}
			else {
				fail("Computer chose weapon that was already seen");
			}			
		}
		
		//Make sure that each possible choice was selected at some point
		assertTrue(chooseCandlestick);
		assertTrue(chooseLeadPipe);
		assertTrue(chooseRope);		
	}
	
	// Will test creating a suggestion of people from multiple values 
	@Test
	void createRandomPersonSuggestion() {
		ArrayList<String> seenCards = new ArrayList<>();
		HashSet<Card> excludedCards = new HashSet<>();
		ComputerPlayer player = new ComputerPlayer();
		boolean chooseScarlett = false;
		boolean choosePlum = false;
		boolean chooseGreen = false;
				
		//Adding all people but three to seenCards list. Computer will have to choose one of 
		// the three cards in its suggestion
		excludedCards.add(Board.getInstance().getCard("Scarlett"));
		excludedCards.add(Board.getInstance().getCard("Plum"));
		excludedCards.add(Board.getInstance().getCard("Green"));
		
		//Need to add default cards for ComputerPlayer to select to create a suggestion
		excludedCards.add(Board.getInstance().getCard("Dining Room"));
		excludedCards.add(Board.getInstance().getCard("Candlestick"));
		
		for (Card card : board.getDeck()) {
			if (!excludedCards.contains(card)) {
				seenCards.add(card.getCardName());
			}
		}
		
		//Set the seen cards so player only has 3 choices for the person and 1 choice for
		// weapon and room
		player.setSeenCards(seenCards);
		
		// test random selection multiple times 
		for (int i = 0; i < 100; i++) {
			player.createSuggestion("Dining Room");
			if (player.getSuggestion().getPerson().equals("Scarlett")) {
				chooseScarlett= true;				
			}	
			else if (player.getSuggestion().getPerson().equals("Plum")) {
				choosePlum = true;
			}
			else if (player.getSuggestion().getPerson().equals("Green")) {
				chooseGreen = true;
			}			
			else {
				fail("Computer chose person that was already seen");
			}			
		}
		
		//Make sure that each possible choice was selected at some point
		assertTrue(chooseScarlett);
		assertTrue(choosePlum);
		assertTrue(chooseGreen);		
	}
	
	// Will test suggestion creation when there is only one option for each category
	@Test
	void checkSuggestionOneItem() {
		ArrayList<String> seenCards = new ArrayList<>();
		HashSet<Card> excludedCards = new HashSet<>();
		ComputerPlayer player = new ComputerPlayer();
				
		// Exclude one weapon, person and room from player's seenCards
		excludedCards.add(Board.getInstance().getCard("Rope"));
		excludedCards.add(Board.getInstance().getCard("White"));
		excludedCards.add(Board.getInstance().getCard("Sauna"));
		
		for (Card card : board.getDeck()) {
			if (!excludedCards.contains(card)) {
				seenCards.add(card.getCardName());
			}
		}
		
		// Set both player's seen lists 
		player.setSeenCards(seenCards);
		
		player.createSuggestion("Sauna");
		//Make sure computer selected the correct weapon, person and room
		assertEquals("Rope", player.getSuggestion().getWeapon());
		assertEquals("White", player.getSuggestion().getPerson());
		assertEquals("Sauna", player.getSuggestion().getRoom());
	}
	
	//Will test disproving an accusation with only one card from suggestion
	@Test
	void disproveSuggestionOneCard() {
		ComputerPlayer player = new ComputerPlayer();
		ArrayList<Card> myCards = new ArrayList<>();
		Card givenCard = new Card("White", CardType.PERSON);
		
		// Create a solution to be passed to the ComputerPlayer
		Solution suggestion = new Solution("White", "Game Room", "Dumbbell");
		
		// Give player only one card that is from the suggestion 
		myCards.add(givenCard);
		player.setMyCards(myCards);	
			
		// player should return the only card in their list of cards
		assertEquals(givenCard, player.disproveSuggestion(suggestion));
	}
	
	//Will test disproving an accusation with multiple cards from suggestion
	@Test
	void disproveSuggestionMultipuleCards() {
		ComputerPlayer player = new ComputerPlayer();
		ArrayList<Card> myCards = new ArrayList<>();
		Card person = new Card("White", CardType.PERSON);
		Card room = new Card("Game Room", CardType.ROOM);
		boolean shownPerson = false;
		boolean shownRoom = false;
		
		// Giving player multiple cards that are part of the suggestion
		myCards.add(person);
		myCards.add(room);
		player.setMyCards(myCards);
		
		// Create a Solution to be passed to the ComputerPlayer
		Solution suggestion = new Solution("White", "Game Room", "Dumbbell");
		
		// Run multiple times to ensure random behavior select all possible outcomes
		for (int i = 0; i < 50; i++) {
			Card shownCard = player.disproveSuggestion(suggestion);
			if (shownCard.equals(person)) {
				shownPerson = true;
			}
			else if (shownCard.equals(room)) {
				shownRoom = true; 
			}
			else {
				fail("Card not in player's hand was shown");
			}
		}
		
		assertTrue(shownPerson);
		assertTrue(shownRoom);		
	}
	
	//Will test disproving an accusation with no cards from suggestion
	@Test
	void disproveSuggestionNoCards() {
		ComputerPlayer player = new ComputerPlayer();
		
		// Create a solution to be passed to the ComputerPlayer
		Solution suggestion = new Solution("White", "Game Room", "Dumbbell");
					
		// player should return null because they dont have any cards 
		assertNull(player.disproveSuggestion(suggestion));
	}
	
	@Test
	void testHandleSuggestion() {
		ArrayList<Card> myCards = new ArrayList<>();
		Player player_0 = board.getPlayer("Scarlett");
		myCards.add(scarlettCard);
		player_0.setMyCards(myCards);
		myCards.clear();
		
		Player player_1 = board.getPlayer("Plum");
		myCards.add(plumCard);
		player_1.setMyCards(myCards);
		myCards.clear();
		
		Player player_2 = board.getPlayer("Peacock");
		myCards.add(ropeCard);
		player_2.setMyCards(myCards);
		myCards.clear();
		
		Player player_3 = board.getPlayer("Green");
		myCards.add(dumbbellCard);
		player_3.setMyCards(myCards);
		myCards.clear();
		
		Player player_4 = board.getPlayer("Mustard");
		myCards.add(diningRoomCard);
		player_4.setMyCards(myCards);
		myCards.clear();
		
		Player player_5 = board.getPlayer("White");
		myCards.add(saunaCard);
		player_5.setMyCards(myCards);
		myCards.clear();
		
		//no one has any of the cards - return null
		Solution noOneHasCard = new Solution("Mustard","Swimming Pool", "Lead Pipe");
		assertNull(board.handleSuggestion(board.getPlayer("Scarlett"), noOneHasCard));
		
		//accuser Plum is the only one that has any parts of the solution - return null
		Solution accuserHasCard = new Solution("Plum", "Swimming Pool", "Lead Pipe");
		assertEquals(plumCard, board.handleSuggestion(board.getPlayer("Plum"), accuserHasCard));
		
		//human has card but is not accuser, return card
		Solution humanHasCard = new Solution("Mustard", "Swimming Pool" , "Dumbbell" );
		assertEquals(dumbbellCard, board.handleSuggestion(board.getPlayer("Plum"), humanHasCard));
		
		//human has card but is accuser, return null
		Solution humanAccuserHasCard = new Solution("Mustard", "Swimming Pool", "Dumbbell");
		assertNull(board.handleSuggestion(board.getPlayer("Green"), humanAccuserHasCard));
		
		//First Player is Scarlett and makes accusation, next two players can disprove
		// make sure that first player in line (Plum) shows their card
		Solution orderedQuery = new Solution("Plum", "Swimming Pool", "Rope");
		assertEquals(plumCard, board.handleSuggestion(board.getPlayer("Scarlett"), orderedQuery));
		
		//First player (Scarlett) makes an accusation, human (Green) and other player(White) can disprove
		// human is first in line so make sure they have to show a card
		Solution humanAndPlayerHaveCards = new Solution("Mustard", "Sauna" ,"Dumbbell");
		assertEquals(dumbbellCard, board.handleSuggestion(board.getPlayer("Scarlett"), humanAndPlayerHaveCards));
	}
}
