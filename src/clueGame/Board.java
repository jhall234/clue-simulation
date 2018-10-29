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
	private String boardConfigFile;	// Board Layout .csv?
	private String roomConfigFile;	// Legend file?
	private static Board theInstance = new Board(50,50);

	/**
	 * Constructor
	 */
	private Board(int numRows, int numCols) {
		this.numRows = numRows;
		this.numColumns = numCols;
		this.legend = new HashMap<Character, String>();
		this.targets = new HashSet<BoardCell>();
		this.visited = new HashSet<BoardCell>();
	}

	/**
	 * assigns the adjacent cell sets
	 */
	public void calcAdjacencies() {
		for (int row=0; row<numRows; row++) {
			for (int column=0; column<numColumns; column++) {
				HashSet<BoardCell> set = new HashSet<BoardCell>();
				//a room piece (sans doorway) will have no adjacencies
				if (board[row][column].isDoorway()) {
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
				}
				if (board[row][column].isWalkway()) {
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

	/**
	 * getter for the adjacent set for a specific cell
	 * @param cell
	 * @return cell.adjacencies
	 */
	public HashSet<BoardCell> getAdjList(BoardCell cell) {
		return board[cell.getRow()][cell.getColumn()].getAdjacencies();
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
		for (BoardCell cell : this.getAdjList(startCell)) {
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
		String line = "";
		String[] file_row;
		ArrayList<String[]> file_array = new ArrayList<String[]>();
		int row = 0;
		int columns = 0;
				
		FileReader file = new FileReader(this.boardConfigFile);
		Scanner sc = new Scanner(file);
		while (sc.hasNext()) {
			line = sc.nextLine();
			file_row = line.split(",");
			file_array.add(file_row);
			if (row == 0) {
				columns = file_row.length;
			}
			row++;
		}
		sc.close();
		//System.out.println("Rows: " + row + "Columns: " + columns); // test to make sure it counted right 
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
	
//	public void loadBoardConfig() throws FileNotFoundException, BadConfigFormatException{
//		String line = "";
//		String[] list;
//		FileReader file = new FileReader(this.boardConfigFile);
//		Scanner sc = new Scanner(file);
//		int row = 0;
//		int columns = 0;
//		while (sc.hasNext()) {
//			line = sc.nextLine();
//			list = line.split(",");
//			if (row == 0) {
//				columns = list.length;
//			}
//			else if (columns != list.length) {
//				throw new BadConfigFormatException("Inconsitant number of columns in each row.");
//			}
//			for (int i = 0; i < list.length; i++) {
//				if (!this.legend.containsKey(list[i].charAt(0))) { // If the initial isn't in the legend. 
//					throw new BadConfigFormatException("At cell " + row + "," + i + " room initial isn't in legend.");
//				}
//				board[row][i].setInitial(list[i].charAt(0));
//				if (list[i].length() > 1) {
//					switch (list[i].charAt(1)) {
//					case 'U':
//						board[row][i].setDoorDirection(DoorDirection.UP);
//						break;
//					case 'D':
//						board[row][i].setDoorDirection(DoorDirection.DOWN);
//						break;
//					case 'L':
//						board[row][i].setDoorDirection(DoorDirection.LEFT);
//						break;
//					case 'R':
//						board[row][i].setDoorDirection(DoorDirection.RIGHT);
//						break;
//					}
//				}
//				else {
//					board[row][i].setDoorDirection(DoorDirection.NONE);
//				}
//			}
//			row++;	
//		}
//		sc.close();
//	
//	}

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

	public Set<BoardCell> getAdjList(int i, int j) {
		return board[i][j].getAdjacencies();
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
		
//		for (HashMap.Entry<Character, String> entry : board.legend.entrySet()) {
//			System.out.println(entry.getKey() + " " + entry.getValue());
//		}
//		System.out.println();
//		for (int row=0; row<board.getNumRows(); row++) {
//			for (int column=0; column<board.getNumColumns(); column++) {
//				System.out.print(board.getCellAt(row, column).getInitial() + " " + board.getCellAt(row, column).getDoorDirection() + "   ");
//			}
//			System.out.println();
//		}
//		System.out.println(board.getLegend().size());
		
//		System.out.println(board.getNumRows() + "," + board.getN);
	}	
}
