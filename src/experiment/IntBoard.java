package experiment;

import java.util.*;

public class IntBoard {
	private BoardCell[][] grid;
	private HashSet<BoardCell> targets;
	
	public IntBoard() {
		this.calcAdjacencies();
	}
	
	public void calcAdjacencies() {
		
	}
	
	public HashSet<BoardCell> getAdjList(BoardCell cell) {
		return grid[cell.getRow()][cell.getColumn()].getAdjacencies();
	}
	
	public void calcTargets(BoardCell startCell, int pathLength) {
		
	}
	
	public HashSet<BoardCell> getTargets() {
		return this.targets;
	}
	
	public BoardCell getCell(int row, int column) {
		return grid[row][column];
	}
}
