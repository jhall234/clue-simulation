//	Carl Schader
//	Josh Halinan

package experiment;

import java.util.*;

public class IntBoard {
	private BoardCell[][] grid;
	private HashSet<BoardCell> targets;
	private HashSet<BoardCell> visited;
	private final int ROWS = 4;
	private final int COLUMNS = 4;

	public IntBoard() {
		this.grid = new BoardCell[ROWS][COLUMNS];
		this.targets = new HashSet<BoardCell>();
		this.visited = new HashSet<BoardCell>();
		for (int row=0; row<ROWS; row++) {
			for (int column=0; column<COLUMNS; column++) {
				this.grid[row][column] = new BoardCell(row, column);
			}
		}
		this.calcAdjacencies();
	}

	public void calcAdjacencies() {
		for (int row=0; row<ROWS; row++) {
			for (int column=0; column<COLUMNS; column++) {
				HashSet<BoardCell> set = new HashSet<BoardCell>();
				if (row + 1 < ROWS) {
					set.add(grid[row+1][column]);
				}
				if (column + 1 < COLUMNS) {
					set.add(grid[row][column+1]);
				}
				if (row - 1 >= 0) {
					set.add(grid[row-1][column]);
				}
				if (column - 1 >= 0) {
					set.add(grid[row][column-1]);
				}
				grid[row][column].setAdjacency(set);
			}
		}
	}

	public HashSet<BoardCell> getAdjList(BoardCell cell) {
		return grid[cell.getRow()][cell.getColumn()].getAdjacencies();
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

	public HashSet<BoardCell> getTargets() {
		return this.targets;
	}

	public BoardCell getCell(int row, int column) {
		return this.grid[row][column];
	}

	public static void main(String[] args) {
		IntBoard board = new IntBoard();
//		for (int row = 0; row < 4; row++) {
//			for (int column = 0; column < 4; column++) {
//				System.out.println("\n\t" + row + "," + column + ":");
//				for (BoardCell adj : board.getAdjList(board.getCell(row, column))) {
//					System.out.println(adj.toString());
//				}
//			}
//		}
		
		
		BoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 2);
		for (BoardCell target : board.getTargets()) {
			System.out.println("\n" + target);
		}
	}
}
