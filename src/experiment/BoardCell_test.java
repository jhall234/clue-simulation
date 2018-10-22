//	Carl Schader
//	Josh Hallinan

package experiment;

import java.util.*;

public class BoardCell_test {
	private int row;
	private int column;
	private HashSet<BoardCell_test> adjacencies;
	
	public BoardCell_test() {
		this.row = 0;
		this.column = 0;
		this.adjacencies = new HashSet<BoardCell_test>();
	}
	
	public BoardCell_test(int row, int column) {
		this.row = row;
		this.column = column;
		this.adjacencies = new HashSet<BoardCell_test>();
	}
	
	public HashSet<BoardCell_test> getAdjacencies() {
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
	
	public void setAdjacency(HashSet<BoardCell_test> adjacencies) {
		this.adjacencies = adjacencies;
	}
	
	@Override
	public String toString() {
		return "row: " + Integer.toString(this.row) + ", column: " + Integer.toString(this.column); 
	}
}
