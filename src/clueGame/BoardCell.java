//	Carl Schader
//	Josh Hallinan

package clueGame;

import java.util.*;

public class BoardCell {
	private int row;
	private int column;
	private HashSet<BoardCell> adjacencies;

	private Character initial;
	private DoorDirection direction;

	public BoardCell() {
		this.row = 0;
		this.column = 0;
		this.adjacencies = new HashSet<BoardCell>();
	}

	public BoardCell(int row, int column) {
		this.row = row;
		this.column = column;
		this.adjacencies = new HashSet<BoardCell>();
	}

	public boolean isWalkway() {

	}

	public boolean isRoom() {

	}

	public boolean isDoorway() {
		
	}

	public HashSet<BoardCell> getAdjacencies() {
		return this.adjacencies;
	}

	public int getRow() {
		return this.row;
	}

	public int getColumn() {
		return this.column;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public void setAdjacency(HashSet<BoardCell> adjacencies) {
		this.adjacencies = adjacencies;
	}

	@Override
	public String toString() {
		return "row: " + Integer.toString(this.row) + ", column: " + Integer.toString(this.column); 
	}
}
