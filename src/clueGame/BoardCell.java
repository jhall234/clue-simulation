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
		this.initial = 'Z';
		this.direction = DoorDirection.NONE;
	}

	public BoardCell(int row, int column) {
		this.row = row;
		this.column = column;
		this.adjacencies = new HashSet<BoardCell>();
		this.initial = 'Z';
		this.direction = DoorDirection.NONE;
	}

	public boolean isWalkway() {
		if (this.initial == 'W') {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean isRoom() {
		if (this.initial != 'W') {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean isDoorway() {
		if (this.direction == DoorDirection.NONE) {
			return false;
		}
		else {
			return true;
		}
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
	
	public DoorDirection getDoorDirection() {
		return this.direction;
	}
	
	public Character getInitial() {
		return this.initial;
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
