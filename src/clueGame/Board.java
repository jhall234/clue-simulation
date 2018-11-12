/**
 * @author Carl Schader
 * @author Josh Hallinan
 */

package clueGame;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.*;
import java.util.*;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Board extends JPanel {
	private BoardCell[][] board;
	private HashSet<BoardCell> targets;
	private HashSet<BoardCell> visited;
	private int numRows;
	private int numColumns;

	public static final int MAX_BOARD_SIZE = 50;
	public static final int NUM_PLAYERS = 6;
	private HashMap<Character, String> legend;
	private String boardConfigFile;	// Board Layout 
	private String roomConfigFile;	// Legend file
	private String weaponConfigFile; // Weapon file
	private String playerConfigFile; // Player file
	private static Board theInstance = new Board(50,50);
	
	private Solution solution;
	private ArrayList<Card> deck;
	private ArrayList<Player> players;
	private ArrayList<String> playerNames;
	private ArrayList<String> rooms;
	private ArrayList<String> weapons;

	/**
	 * Constructor
	 */
	private Board(int numRows, int numCols) {
		this.numRows = numRows;
		this.numColumns = numCols;
		this.legend = new HashMap<>();
		this.targets = new HashSet<>();
		this.visited = new HashSet<>();	
		this.solution = new Solution();
		this.deck= new ArrayList<Card>();
		this.players = new ArrayList<>(6);
		this.rooms = new ArrayList<String>();
		this.weapons = new ArrayList<String>();
		this.playerNames = new ArrayList<>();
	}

	/**
	 * sets the string variables for the config files
	 * @param boardConfigFile
	 * @param roomConfigFile
	 * @param weaponConfigFile
	 * @param playerConfigFile
	 */
	public void setConfigFiles(String boardConfigFile, String roomConfigFile, String weaponConfigFile, String playerConfigFile) {
		this.boardConfigFile = boardConfigFile;
		this.roomConfigFile = roomConfigFile;
		this.weaponConfigFile = weaponConfigFile;
		this.playerConfigFile = playerConfigFile;
	}
	
	/**
	 * initialize the Board
	 */
	public void initialize() throws FileNotFoundException, BadConfigFormatException{
		this.loadRoomConfig();
		this.loadBoardConfig();
		this.loadPlayerConfig();
		this.loadWeaponConfig();
		this.dealCards();
	}

	/**
	 * loads room config
	 */
	public void loadRoomConfig() throws FileNotFoundException, BadConfigFormatException {
		FileReader file = new FileReader(this.roomConfigFile);
		Scanner in = new Scanner(file);
		while (in.hasNext()) {
			String[] list = in.nextLine().split(", ");
			if (list.length != 3 || list[0].length() != 1) {
				throw new BadConfigFormatException("Imporper room config file format.");
			}
			if (!(list[2].equals("Other") || list[2].equals("Card"))) {
				throw new BadConfigFormatException("All legend entries must be of type Other or Card.");
			}
			this.legend.put(list[0].charAt(0), list[1]); // value could be: list[1] + " " + list[2] to include if it's a card or other
			if (list[2].equals("Card")) {
				deck.add(new Card(list[1], CardType.ROOM)); // add room card to the deck
				rooms.add(list[1]); //add name to list of rooms
			}
		}
		in.close();
	}

	/**
	 * loads board configuration and creates the board
	 */
	public void loadBoardConfig() throws FileNotFoundException, BadConfigFormatException {
		ArrayList<String[]> file_array = new ArrayList<String[]>();
		int row = 0;
		int columns = 0;
				
		FileReader file = new FileReader(this.boardConfigFile);
		Scanner sc = new Scanner(file);
		while (sc.hasNext()) {
			String[] file_row = sc.nextLine().split(",");
			file_array.add(file_row);
			if (row == 0) {
				columns = file_row.length;
			}
			row++;
		}
		sc.close();
		
		// create new board with correct dimensions
		this.numRows = row;
		this.numColumns = columns;
		this.board = new BoardCell[row][columns];
		for (int i=0; i<row; i++) {
			for (int j=0; j<columns; j++) {
				this.board[i][j] = new BoardCell(i, j);
			}
		}
		row = 0; // reset row counter
		for (String[] list : file_array) {
			if (columns != list.length) {
				throw new BadConfigFormatException("Inconsitent number of columns in each row.");
			}		
			for (int col = 0; col < list.length; col++) { // iterator for columns
				if (!this.legend.containsKey(list[row].charAt(0))) { // If the initial isn't in the legend. 
					throw new BadConfigFormatException("At cell " + row + "," + col+ " room initial isn't in legend.");
				}
				board[row][col].setInitial(list[col].charAt(0));
				if (list[col].length() > 1) {
					switch (list[col].charAt(1)) {
					case 'U':
						board[row][col].setDoorDirection(DoorDirection.UP);
						break;
					case 'D':
						board[row][col].setDoorDirection(DoorDirection.DOWN);
						break;
					case 'L':
						board[row][col].setDoorDirection(DoorDirection.LEFT);
						break;
					case 'R':
						board[row][col].setDoorDirection(DoorDirection.RIGHT);
						break;
					case 'N':
						board[row][col].setDrawRoomName(true);
						break;
					}
				}
				else {
					board[row][col].setDoorDirection(DoorDirection.NONE);
				}
			}
			row++;			
		}
		this.calcAdjacencies();
	}
	
	/**
	 * loads player config file and adds players and their respective cards to the board
	 * @throws FileNotFoundException
	 * @throws BadConfigFormatException
	 */
	public void loadPlayerConfig() throws FileNotFoundException, BadConfigFormatException {
		FileReader file = new FileReader(playerConfigFile);
		Scanner in = new Scanner(file);
		int num_lines = 0;
		while (in.hasNext()) {
			if (num_lines > NUM_PLAYERS) {
				throw new BadConfigFormatException("Too many players. Only " + NUM_PLAYERS + " allowed.");
			}
			String[] line = in.nextLine().split(", ");
			if (line.length != 7) {
				throw new BadConfigFormatException("Improper file format. Each line must contain 7 values");
			}
			String playerName = line[0];
			int red = Integer.parseInt(line[1]);
			int green = Integer.parseInt(line[2]);
			int blue = Integer.parseInt(line[3]);
			int row = Integer.parseInt(line[5]);
			int column = Integer.parseInt(line[6]);
			PlayerType playerType;
			switch (line[4]) {
				case "computer":
					playerType = PlayerType.COMPUTER;
					players.add(new ComputerPlayer(playerName, red, green, blue, playerType, row, column));
					playerNames.add(playerName);
					break;
				case "human":
					playerType = PlayerType.HUMAN;
					players.add(new HumanPlayer(playerName, red, green, blue, playerType, row, column));
					playerNames.add(playerName);
					break;
				default:
					throw new BadConfigFormatException("5th element of each line must be either 'computer' or 'human'");
			}
			
			deck.add(new Card(playerName, CardType.PERSON)); //each player corresponds to a card
			num_lines++;
		}
		in.close();
	}
	
	/**
	 * loads weapon config file and add weapons cards to the Board  
	 * @throws FileNotFoundException
	 * @throws BadConfigFormatException
	 */
	public void loadWeaponConfig() throws FileNotFoundException, BadConfigFormatException {
		FileReader file = new FileReader(weaponConfigFile);
		Scanner in = new Scanner(file);		
		while (in.hasNext()) {
			String weapon_name = in.nextLine();
			deck.add(new Card(weapon_name, CardType.WEAPON));
			weapons.add(weapon_name); // add weapon to list of weapon names
		}
		in.close();
	}
	
	/**
	 * Will assign randomly an even number of cards to each player. 
	 */
	public void dealCards() {
		ArrayList<Card> already_seen = new ArrayList<>();
		boolean need_room = true;
		boolean need_person = true;
		boolean need_weapon = true;	
		//Choose a room, player and weapon for the solution		
		while (need_room || need_person || need_weapon) {
			Card random_card = deck.get(new Random().nextInt(deck.size()));
			switch (random_card.getCardType()) {
			case ROOM:
				if (need_room) {
					solution.setRoom(random_card.getCardName());
					already_seen.add(random_card);
					need_room = false;
				}				
				break;
			case PERSON:
				if (need_person) {
					solution.setPerson(random_card.getCardName());
					already_seen.add(random_card);
					need_person = false;
				}				
				break;
			case WEAPON:
				if (need_weapon) {
						solution.setWeapon(random_card.getCardName());
						already_seen.add(random_card);
						need_weapon = false;						
				}				
				break;
			}
		}
		//Assign the other cards to the players
		int min_cards_per_player = (deck.size()-3)/players.size();
		for (Player player : players) {
			int cards_dealt = 0; 
			while (cards_dealt < min_cards_per_player) {
				Card random_card = deck.get(new Random().nextInt(deck.size()));
				if (!already_seen.contains(random_card)) {
					ArrayList<Card> new_list = player.getMyCards();
					new_list.add(random_card);
					player.setMyCards(new_list);
					already_seen.add(random_card);
					cards_dealt++;
				}				
			}			
		}
		//Distribute any remaining cards
		int player_index = 0;
		while (already_seen.size() != deck.size()) {
			Card random_card = deck.get(new Random().nextInt(deck.size()));
			if (!already_seen.contains(random_card)) {
				ArrayList<Card> new_list = players.get(player_index).getMyCards();
				new_list.add(random_card);
				players.get(player_index).setMyCards(new_list);
				already_seen.add(random_card);
				player_index++;				
			}
		}			
	}
	/**
	 * assigns the adjacency cell sets
	 */
	public void calcAdjacencies() {
		for (int row=0; row<numRows; row++) {
			for (int column=0; column<numColumns; column++) {
				HashSet<BoardCell> set = new HashSet<BoardCell>();
				//a room piece (sans doorway) will have no adjacencies
				if (board[row][column].getDoorDirection() == DoorDirection.UP) {
					if (board[row-1][column].isWalkway()) {
						set.add(board[row-1][column]);
					}
				}
				else if (board[row][column].getDoorDirection() == DoorDirection.LEFT) {
					if (board[row][column-1].isWalkway()) {
						set.add(board[row][column-1]);
					}
				}
				else if (board[row][column].getDoorDirection() == DoorDirection.DOWN) {
					if (board[row+1][column].isWalkway()) {
						set.add(board[row+1][column]);
					}
				}
				else if (board[row][column].getDoorDirection() == DoorDirection.RIGHT) {
					if (board[row][column+1].isWalkway()) {
						set.add(board[row][column+1]);
					}
				}
				else if (board[row][column].isWalkway()) {
					if (row + 1 < numRows) {
						if (board[row+1][column].isWalkway() || (board[row+1][column].getDoorDirection() == DoorDirection.UP)) {
							set.add(board[row+1][column]);
						}
					}
					if (column + 1 < numColumns) {
						if (board[row][column+1].isWalkway() || (board[row][column+1].getDoorDirection() == DoorDirection.LEFT)) {
							set.add(board[row][column+1]);
						}
					}
					if (row - 1 >= 0) {
						if (board[row-1][column].isWalkway() || (board[row-1][column].getDoorDirection() == DoorDirection.DOWN)) {
							set.add(board[row-1][column]);
						}					
					}
					if (column - 1 >= 0) {
						if (board[row][column-1].isWalkway() || (board[row][column-1].getDoorDirection() == DoorDirection.RIGHT)) {
							set.add(board[row][column-1]);
						}
					}
				}
				board[row][column].setAdjacency(set);
			}
		}
	}

	public void selectAnswer() {
		
	}
	
	/**
	 * Will get one of the players to reveal a card to the accuser
	 * @return Card that corresponds to the accusation
	 */
	public Card handleSuggestion(Player suggester, Solution suggestion) {
		int startIndex = players.indexOf(suggester) + 1;
		Card disprovingClue;
		//Loop over every player once, if player is accuser, they can't answer 
		for (int playersQuestioned = 0; playersQuestioned< players.size(); playersQuestioned++) {
			startIndex = startIndex % players.size();
			Player disprover = players.get(startIndex);
			if (!disprover.equals(suggester)) {
					disprovingClue = disprover.disproveSuggestion(suggestion);
					if (disprovingClue != null) {
						return disprovingClue;
					}
			}
			startIndex++;
		}
		return null;
	}
	
	public boolean checkAccusation(Solution accusation) {
		return this.solution.isEqual(accusation);
	}

	/**
	 * public method to call to assign the targets given a path length and cell
	 * @param startCell
	 * @param pathLength
	 */
	public void calcTargets(int row, int column, int pathLength) {
		this.visited.clear();
		this.targets.clear();
		this.visited.add(board[row][column]);
		this.findAllTargets(board[row][column], pathLength);	
	}

	

	/**
	 * recursive function used inside calcTargets
	 * @param startCell
	 * @param pathLength
	 */
	private void findAllTargets(BoardCell startCell, int pathLength) {
		for (BoardCell cell : this.getAdjList(startCell.getRow(), startCell.getColumn())) {
			if (this.visited.contains(cell)) {
				continue;
			}
			

			this.visited.add(cell);
			if (pathLength == 1) {
				this.targets.add(cell);
			}
			else if (cell.isDoorway()) {
				this.targets.add(cell);
			}
			else {
				this.findAllTargets(cell, pathLength - 1);
			}

			this.visited.remove(cell);
		}
	}

	
	
	/**
	 * gets the list of targets
	 * @return targets
	 */
	public HashSet<BoardCell> getTargets() {
		return this.targets;
	}

	/**
	 * gets a particular cell
	 * @param row
	 * @param column
	 * @return board[row][column]
	 */
	public BoardCell getCellAt(int row, int column) {
		return this.board[row][column];
	}

	/**
	 * gets the entire board of cells
	 * @return board
	 */
	public BoardCell[][] getBoard() {
		return this.board;
	}
	
	/**
	 * sets the entire board of cells
	 * 
	 */
	public void setBoard(BoardCell[][] board) {
		this.board = board;
	}

	/**
	 * gets the singleton board
	 * @return theInstance
	 */
	public static Board getInstance() {
		return theInstance;
	}

	/**
	 * gets the legend map
	 * @return legend
	 */
	public HashMap<Character, String> getLegend() {
		return this.legend;
	}

	/**
	 * gets the number of rows
	 * @return numRows
	 */
	public int getNumRows() {
		return this.numRows;
	}

	/**
	 * gets the number of columns numColumns
	 * @return numColumns
	 */
	public int getNumColumns() {
		return this.numColumns;
	}
	
	/**
	 * getter for the adjacent set for a specific cell
	 * @param row
	 * @param column
	 * @return
	 */
	public Set<BoardCell> getAdjList(int row, int column) {
		return board[row][column].getAdjacencies();
	}
	
	/**
	 * returns the deck of cards available in the game
	 * @return ArrayList of type Card 
	 */
	public ArrayList<Card> getDeck() {
		return deck;
	}
	
	
	public ArrayList<Player> getPlayers() {
		return players;
	}
	
	/**
	 * Will search list of players for a player with the passed playerName
	 * @param playerName
	 * @return Player 
	 */
	public Player getPlayer(String playerName) {
		for (Player player : players) {
			if (player.getPlayerName().equals(playerName)) {
				return player; 
			}
		}
		//If not in array list, return a blank player
		Player default_player = new Player();
		return default_player;		
	}
	

	public Solution getSolution() {
		return solution;
	}

	public void setSolution(Solution solution) {
		this.solution = solution;
	}	

	public ArrayList<String> getRooms() {
		return rooms;
	}

	public void setRooms(ArrayList<String> rooms) {
		this.rooms = rooms;
	}

	public ArrayList<String> getWeapons() {
		return weapons;
	}

	public void setWeapons(ArrayList<String> weapons) {
		this.weapons = weapons;
	}
	
	public ArrayList<String> getPlayerNames() {
		return playerNames;
	}

	public void setPlayerNames(ArrayList<String> playerNames) {
		this.playerNames = playerNames;
	}
	
	public Card getCard(String cardName) {
		for (Card card : deck) {
			if (card.getCardName().equals(cardName)) {
				return card;
			}
		}
		return null;
	}
	/**
	 * Will be responsible for drawing all of the board cells on to the JFrame
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		for (int row = 0; row < numRows; row++) {
			for (int column = 0; column < numColumns; column++) {
				board[row][column].draw(g2);
			}
		}
	}
	
	public String getRoomName(char roomLetter) {
		return legend.get(Character.valueOf(roomLetter));
	}

	public static void main(String[] args) throws FileNotFoundException {
		
	}	
}
