/**
 * @author Carl Schader
 * @author Josh Hallinan
 */

package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
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
	private static Board theInstance = new Board(23,24);

	/**
	 * Constructor
	 */
	private Board(int numRows, int numCols) {
		this.numRows = numRows;
		this.numColumns = numCols;
		this.legend = new HashMap<Character, String>();
		
		this.board = new BoardCell[numRows][numColumns];
		this.targets = new HashSet<BoardCell>();
		this.visited = new HashSet<BoardCell>();
		
		for (int row=0; row<numRows; row++) {
			for (int column=0; column<numColumns; column++) {
				this.board[row][column] = new BoardCell(row, column);
			}
		}
		this.calcAdjacencies();
	}
	
	/**
	 * assigns the adjacent cell sets
	 */
	public void calcAdjacencies() {
		for (int row=0; row<numRows; row++) {
			for (int column=0; column<numColumns; column++) {
				HashSet<BoardCell> set = new HashSet<BoardCell>();
				if (row + 1 < numRows) {
					set.add(board[row+1][column]);
				}
				if (column + 1 < numColumns) {
					set.add(board[row][column+1]);
				}
				if (row - 1 >= 0) {
					set.add(board[row-1][column]);
				}
				if (column - 1 >= 0) {
					set.add(board[row][column-1]);
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
	public void calcTargets(BoardCell startCell, int pathLength) {
		this.visited.clear();
		this.targets.clear();
		this.visited.add(startCell);
		this.findAllTargets(startCell, pathLength);
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
			else {
				this.findAllTargets(cell, pathLength - 1);
			}
			
			this.visited.remove(cell);
		}
	}
	
	/**
	 * initialize the Board
	 */
	public void initialize() {
		this.loadBoardConfig();
		this.loadRoomConfig();
	}
	
	/**
	 * loads room config
	 */
	private void loadRoomConfig() {
		String line = "";
		String[] list;
		try {
			FileReader file = new FileReader(this.roomConfigFile);
			Scanner in = new Scanner(file);
			while (in.hasNext()) {
				line = in.nextLine();
				list = line.split(", ");
				this.legend.put(list[0].charAt(0), list[1]); // value could be: list[1] + " " + list[2] to include if it's a card or other
			}
			in.close();
		}
		catch (FileNotFoundException e) {
			System.out.println("Legend file not found.");
		}
	}
	
	/**
	 * loads board config
	 */
	private void loadBoardConfig() {
		String line = "";
		String[] list;
		try {
			FileReader file = new FileReader(this.boardConfigFile);
			Scanner in = new Scanner(file);
			int row = 0;
			while (in.hasNext()) {
				line = in.nextLine();
				list = line.split(",");
				for (int i = 0; i < list.length; i++) {
					board[row][i].setInitial(list[i].charAt(0));
					if (list[i].length() > 1) {
						switch (list[i].charAt(1)) {
						case 'U':
							board[row][i].setDoorDirection(DoorDirection.UP);
							break;
						case 'D':
							board[row][i].setDoorDirection(DoorDirection.DOWN);
							break;
						case 'L':
							board[row][i].setDoorDirection(DoorDirection.LEFT);
							break;
						case 'R':
							board[row][i].setDoorDirection(DoorDirection.RIGHT);
							break;
						}
					}
					else {
						board[row][i].setDoorDirection(DoorDirection.NONE);
					}
				}
				row++;
			}
			in.close();
		}
		catch (FileNotFoundException e) {
			System.out.println("Board .csv file not found.");
		}
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
	public BoardCell getCell(int row, int column) {
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
	
	public static void main(String[] args) {
		Board board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueLegend.txt");
		board.initialize();
		for (HashMap.Entry<Character, String> entry : board.legend.entrySet()) {
			System.out.println(entry.getKey() + " " + entry.getValue());
		}
		System.out.println();
		for (int row=0; row<board.getNumRows(); row++) {
			for (int column=0; column<board.getNumColumns(); column++) {
				System.out.print(board.getCell(row, column).getInitial() + " " + board.getCell(row, column).getDoorDirection() + "   ");
			}
			System.out.println();
		}
		System.out.println(board.getLegend().size());
	}
}
