/**
 * @author Carl Schader
 * @author Josh Hallinan
 */

package clueGame;

import java.util.*;

public class Board {
	private BoardCell[][] board;
	private HashSet<BoardCell> targets;
	private HashSet<BoardCell> visited;
	private int numRows;
	private int numColumns;
	
	public static final int MAX_BOARD_SIZE = 50; 
	private HashMap<Character, String> legend;
	private String boardConfigFile;
	private String roomConfigFile;
	private static Board theInstance = new Board();

	/**
	 * Constructor
	 */
	private Board() {
		this.numRows = 50;
		this.numColumns = 50;
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
		
	}
	
	/**
	 * loads room config
	 */
	public void loadRoomConfig() {
		
	}
	
	/**
	 * loads board config
	 */
	public void loadBoardConfig() {
		
	}
	
	/**
	 * Allows user to input config file names
	 * @param boardLayout
	 * @param legend
	 */
	public void setConfigFiles(String boardLayout, String legend) {
		
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
}
