/**
 * @author Carl Schader
 * @author Josh Hallinan
 */

package clueGame;

import java.io.*;
import java.util.*;

public class Board {
	private BoardCell[][] board;
	private HashSet<BoardCell> targets;
	private HashSet<BoardCell> visited;
	private int numRows;
	private int numColumns;

	public static final int MAX_BOARD_SIZE = 50; 
	private HashMap<Character, String> legend;
	private String boardConfigFile;	// Board Layout 
	private String roomConfigFile;	// Legend file?
	private String weaponConfigFile;
	private String playerConfigFile;
	private static Board theInstance = new Board(50,50);
	
	private Solution solution;
	private ArrayList<Card> deck;
	private ArrayList<Player> players;

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
		this.deck= new ArrayList<>();
		this.players = new ArrayList<>(6);
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
	public Card handleSuggestion() {
		Card default_card = new Card();
		return default_card;
	}
	
	public boolean checkAccusation(Solution Accusation) {
		return false;
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
	 * initialize the Board
	 */
	public void initialize() throws FileNotFoundException, BadConfigFormatException{
		this.loadRoomConfig();
		this.loadBoardConfig();
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
				throw new BadConfigFormatException("Inconsitant number of columns in each row.");
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
		FileReader file = new FileReader(this.playerConfigFile);
		Scanner in = new Scanner(file);
		while (in.hasNext()) {
			Player p = new Play
			String[] line = in.nextLine().split(", ");
			if (line.length != 7) {
				throw new BadConfigFormatException("Improper player config file format.");
			}
			
		}
		in.close();
	}
	
	/**
	 * loads weapon config file and add weapons cards to the Board  
	 * @throws FileNotFoundException
	 * @throws BadConfigFormatException
	 */
	public void loadWeaponConfig() throws FileNotFoundException, BadConfigFormatException {
	
	}
	
	/**
	 * Allows user to input config file names
	 * @param boardLayout
	 * @param legend
	 */
	public void setConfigFiles(String boardConfigFile, String roomConfigFile) {
		this.boardConfigFile = boardConfigFile;
		this.roomConfigFile = roomConfigFile;
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
		Player default_player = new Player();
		return default_player;
	}

	public static void main(String[] args) throws FileNotFoundException {
		Board board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueLegend.txt");
		try {
			board.initialize();
		}
		catch (BadConfigFormatException e) {
			System.out.println(e.toString());
		}
	}	
}
