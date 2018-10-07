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

	public HashSet<BoardCell> getAdjList(BoardCell cell) {
		return board[cell.getRow()][cell.getColumn()].getAdjacencies();
	}

	public void calcTargets(BoardCell startCell, int pathLength) {
		this.visited.clear();
		this.targets.clear();
		this.visited.add(startCell);
		this.findAllTargets(startCell, pathLength);
	}

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
	
	public void initialize() {
		
	}
	
	public void loadRoomConfig() {
		
	}
	
	public void loadBoardConfig() {
		
	}
	
	public void setConfigFiles(String boardLayout, String legend) {
		
	}

	public HashSet<BoardCell> getTargets() {
		return this.targets;
	}

	public BoardCell getCell(int row, int column) {
		return this.board[row][column];
	}
	
	public BoardCell[][] getBoard() {
		return this.board;
	}
	
	public static Board getInstance() {
		return theInstance;
	}
	
	public HashMap<Character, String> getLegend() {
		return this.legend;
	}
	
	public int getNumRows() {
		return this.numRows;
	}
	
	public int getNumColumns() {
		return this.numColumns;
	}
}
