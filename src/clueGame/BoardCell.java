/**
 * @author Carl Schader
 * @author Josh Hallinan
 */

package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.*;

public class BoardCell {
	private int row;
	private int column;
	private HashSet<BoardCell> adjacencies;
	private Character initial;
	private DoorDirection direction;
	private int x;
	private int y;
	private static final int WIDTH = 26;
	private static final int HEIGHT = 26;
	private boolean drawRoomName = false;
	

	/**
	 * Default Constructor
	 */
	public BoardCell() {
		this.row = 0;
		this.column = 0;
		this.adjacencies = new HashSet<>();
		this.initial = 'Z';
		this.direction = DoorDirection.NONE;
	}

	/**
	 * Parameterized Constructor
	 * @param row
	 * @param column
	 */
	public BoardCell(int row, int column) {
		this.row = row;
		this.column = column;
		this.adjacencies = new HashSet<BoardCell>();
		this.initial = 'Z';
		this.direction = DoorDirection.NONE;
		this.x = column*WIDTH;
		this.y = row*HEIGHT;
	}

	/**
	 * Boolean to determine if cell is a walkway
	 * @return boolean
	 */
	public boolean isWalkway() {
		return (this.initial == 'W');
	}

	/**
	 * boolean for if the cell is in a room
	 * @return boolean
	 */
	public boolean isRoom() {
		return(this.initial != 'W');
	}

	/**
	 * boolean to determine if cell is a doorway
	 * @return boolean
	 */
	public boolean isDoorway() {
		return (this.direction != DoorDirection.NONE);
	}

	/**
	 * getter for the list of adjacencies
	 * @return adjacencies
	 */
	public HashSet<BoardCell> getAdjacencies() {
		return this.adjacencies;
	}

	/**
	 * getter for the row number
	 * @return row
	 */
	public int getRow() {
		return this.row;
	}

	/**
	 * getter for the column number
	 * @return column
	 */
	public int getColumn() {
		return this.column;
	}
	
	/**
	 * getter for the door direction
	 * @return direction
	 */
	public DoorDirection getDoorDirection() {
		return this.direction;
	}
	
	/**
	 * getter for the cell initial
	 * @return initial
	 */
	public char getInitial() {
		return this.initial;
	}
	
	/**
	 * getter for the cell initial
	 * 
	 */
	public void setInitial(Character initial) {
		this.initial = initial;
	}
	
	/**
	 * getter for the cell doorDirection
	 *
	 */
	public void setDoorDirection(DoorDirection doorDirection) {
		this.direction = doorDirection;
	}

	/**
	 * setter for the row number
	 * @param row
	 */
	public void setRow(int row) {
		this.row = row;
	}

	/**
	 * setter for the column number
	 * @param column
	 */
	public void setColumn(int column) {
		this.column = column;
	}

	/**
	 * setter for adjacency list
	 * @param adjacencies
	 */
	public void setAdjacency(HashSet<BoardCell> adjacencies) {
		this.adjacencies = adjacencies;
	}

	/**
	 * To string method for testing
	 */
	@Override
	public String toString() {
		return "row: " + Integer.toString(this.row) + ", column: " + Integer.toString(this.column); 
	}
	
	public void setDrawRoomName(boolean drawRoomName) {
		this.drawRoomName = drawRoomName;
	}

	/**
	 * Will handle drawing a single board cell
	 * @param g
	 */
	public void draw(Graphics2D g) {
		
		if (this.isRoom()) {
		//Fill square with grey
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(x, y, WIDTH, HEIGHT);
			switch (getDoorDirection()) {
				case UP:
					//draw a blue line ontop of the box
					break;
				case DOWN:
					//draw a blue line on the bottom of the box
					break;
				case LEFT:
					//draw a blue line on the left of the box
					break;
				case RIGHT:
					//draw a blue line on the left of the box
					break;
				case NONE:
					if (drawRoomName) {	
						g.setColor(Color.BLUE);
						//NOTE: y distance is decremented by 3 px to allow room for chars w/ descenders 
					    g.drawString(Board.getInstance().getRoomName(initial), x, y-3); 
					}
				    break;
			}
			//g.setBackground(Color.BLUE);
			
		}
		else {
			//Fill square with yellow
			g.setColor(Color.YELLOW);
			g.fillRect(x, y, WIDTH, HEIGHT);
			//Outline Square
			g.setColor(Color.BLACK);
			g.drawRect(x, y, WIDTH, HEIGHT);
		}
		
		
		
		
	}
}
