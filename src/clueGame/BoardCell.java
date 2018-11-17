/**
 * @author Carl Schader
 * @author Josh Hallinan
 */

package clueGame;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.*;

public class BoardCell {
	private static final int WIDTH = 26;
	private static final int HEIGHT = 26;
	
	private int row;
	private int column;
	private HashSet<BoardCell> adjacencies;
	private Character initial;
	private DoorDirection doorDirection;
	private int x;
	private int y;	
	private boolean drawRoomName = false;
	private boolean highlightTarget = false;
	
	/**
	 * Default Constructor
	 */
	public BoardCell() {
		this.row = 0;
		this.column = 0;
		this.adjacencies = new HashSet<>();
		this.initial = 'Z';
		this.doorDirection = DoorDirection.NONE;
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
		this.doorDirection = DoorDirection.NONE;
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
		return (this.doorDirection != DoorDirection.NONE);
	}

	/**
	 * To string method for testing
	 */
	@Override
	public String toString() {
		return "row: " + Integer.toString(this.row) + ", column: " + Integer.toString(this.column); 
	}

	/**
	 * Will handle drawing a single board cell. Can draw, a room(not doorway), doorway and walkway pieces 
	 * @param g (Graphics2D object)
	 */
	public void draw(Graphics2D g) {
		
		if (this.isRoom()) {
		//Fill square with grey
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(x, y, WIDTH, HEIGHT);
			Stroke defaultStroke = g.getStroke();
			switch (getDoorDirection()) {
				//NOTE: All numbers added in the drawLine() methods are used for alignment purposes 
				case UP:
					g.setColor(Color.BLUE);
					g.setStroke(new BasicStroke(4));
					g.drawLine(x+1, y+2, x+WIDTH, y+2);
					g.setStroke(defaultStroke);
					break;
				case DOWN:
					g.setColor(Color.BLUE);
					g.setStroke(new BasicStroke(4));
					g.drawLine(x+2, y+HEIGHT-2, x+WIDTH, y+HEIGHT-2);
					g.setStroke(defaultStroke);
					break;
				case LEFT:
					g.setColor(Color.BLUE);
					g.setStroke(new BasicStroke(4));
					g.drawLine(x+2, y+2, x+2, y+HEIGHT);
					g.setStroke(defaultStroke);
					break;
				case RIGHT:
					g.setColor(Color.BLUE);
					g.setStroke(new BasicStroke(4));
					g.drawLine(x+WIDTH-2, y+2, x+WIDTH-2, y+HEIGHT);
					g.setStroke(defaultStroke);
					break;
				case NONE:
					if (drawRoomName) {	
						g.setColor(Color.BLUE);
						//NOTE: y distance is decremented by 3 px to allow room for chars w/ descenders 
					    g.drawString(Board.getInstance().getRoomName(initial), x, y-3); 
					}
				    break;
			}			
		}
		else {
			if (highlightTarget) {
				// Color green to highlight valid target
				g.setColor(new Color(104, 255, 107));
			}
			else {
				//Fill square with yellow
				g.setColor(new Color(255, 234, 168));
			}
			
			// Fill with selected color and outline
			g.fillRect(x, y, WIDTH, HEIGHT);
			//Outline Square
			g.setColor(Color.BLACK);
			g.drawRect(x, y, WIDTH, HEIGHT);
		}		
	}

//************************ Instance Variable Getters & Setters **********************
	
	/**
	 * Returns the set constant for board width
	 * @return WIDTH
	 */
	public static int getWidth() {
		return WIDTH;
	}
	
	/**
	 * Retuens the set constant for board height
	 * @return HEIGHT
	 */
	public static int getHeight() {
		return HEIGHT;
	}

	/**
	 * getter for the row number
	 * @return row
	 */
	public int getRow() {
		return this.row;
	}

	/**
	 * setter for the row number
	 * @param row
	 */
	public void setRow(int row) {
		this.row = row;
	}

	/**
	 * getter for the column number
	 * @return column
	 */
	public int getColumn() {
		return this.column;
	}
	
	/**
	 * setter for the column number
	 * @param column
	 */
	public void setColumn(int column) {
		this.column = column;
	}

	/**
	 * getter for the list of adjacencies
	 * @return adjacencies
	 */
	public HashSet<BoardCell> getAdjacencies() {
		return this.adjacencies;
	}

	/**
	 * setter for adjacency list
	 * @param adjacencies
	 */
	public void setAdjacency(HashSet<BoardCell> adjacencies) {
		this.adjacencies = adjacencies;
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
	 * getter for the door direction
	 * @return direction
	 */
	public DoorDirection getDoorDirection() {
		return this.doorDirection;
	}
	
	/**
	 * setter for the cell doorDirection
	 *
	 */
	public void setDoorDirection(DoorDirection doorDirection) {
		this.doorDirection = doorDirection;
	}

	/**
	 * Setter for boolean DrawRoomName
	 * @param drawRoomName
	 */
	public void setDrawRoomName(boolean drawRoomName) {
		this.drawRoomName = drawRoomName;
	}

	
	/**
	 * Setter for highlightTarget
	 * @param highlightTarget
	 */
	public void setHighlightTarget(boolean highlightTarget) {
		this.highlightTarget = highlightTarget;
	}
	
	
}
