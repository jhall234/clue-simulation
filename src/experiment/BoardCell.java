package experiment;

import java.util.*;

public class BoardCell {
	private int row;
	private int column;
	private HashSet<BoardCell> adjacencies;
	
//	public BoardCell() {
//		this.row = 0;
//		this.column = 0;
//		this.adjacencies = new HashSet<BoardCell>();
//	}
//	
//	public BoardCell(int row, int column, HashSet<BoardCell> adjacencies) {
//		this.row = row;
//		this.column = column;
//		this.adjacencies = adjacencies;
//	}
	
	public HashSet<BoardCell> getAdjacencies() {
		return this.adjacencies;
	}
	
	public int getRow() {
		return this.row;
	}
	
	public int getColumn() {
		return this.column;
	}
}
