package clueGame;

import java.util.*;

import javax.swing.JOptionPane;

public class ComputerPlayer extends Player {
	private BoardCell justVisitedRoom;
	private Solution suggestion;
	private boolean shouldAccuse;
	public ComputerPlayer() {
		super();
		this.suggestion = new Solution();
		shouldAccuse = false;
	}
	
	public ComputerPlayer(String playerName, int red, int green, int blue, PlayerType playerType, int row, int column) {
		super(playerName, red, green, blue, playerType, row, column);
		this.suggestion = new Solution();
	}
	
	/**
	 * Will select a location for ComputerPlayer to travel to, choosing a room if possible
	 * @param targets
	 * @return
	 */
	public BoardCell pickLocation(Set<BoardCell> targets) {
		ArrayList<BoardCell> walkways = new ArrayList<BoardCell>();
		ArrayList<BoardCell> doors = new ArrayList<BoardCell>();
		
		for (BoardCell cell : targets) {
			if (cell.getDoorDirection().equals(DoorDirection.NONE) || cell.equals(this.justVisitedRoom)) {
				walkways.add(cell);
			}
			else {
				doors.add(cell);
			}
		}
		
		if (doors.size() > 0) {
			return doors.get(new Random().nextInt(doors.size()));
		}
		else {
			return walkways.get(new Random().nextInt(walkways.size()));
		}
	}
	
	/**
	 * Will automatically move player to specified target since its computer player
	 */
	@Override
	public void makeMove(BoardCell target) {
		if (shouldAccuse) {
			makeAccusation();
		}
		setColumn(target.getColumn());
		setRow(target.getRow());
		//if player is inside a room, generate suggestion
		if (target.isRoom()) {
			justVisitedRoom = target;
			char roomChar = target.getInitial();
			createSuggestion(Board.getInstance().getRoomName(roomChar));
			makeSuggestion();
		}
	}
	
	/**
	 * Will send a suggestion to Board, and decide if player should make accusation next turn 
	 */
	public void makeSuggestion() {
		Card cardShown = Board.getInstance().handleSuggestion(this, suggestion);
		if (cardShown == null) {
			//search if player has room card that they are in, if not set a boolean to make accusation next turn
			boolean hasRoomCard = false;
			for (String s: getSeenCards()) {
				if (s.equals(suggestion.getRoom())){
					hasRoomCard = true;
				}
			}
			if (!hasRoomCard) {
				shouldAccuse = true;
			}		
		}
	}
	
	/**
	 * Handles seeing if the computer won when it makes an accusation
	 */
	public void makeAccusation() {
		if (Board.getInstance().checkAccusation(suggestion)) {
			//Player has won
			JOptionPane.showMessageDialog(null, this.getPlayerName()+" just won with guess: "+suggestion.person+", "+suggestion.room+", "+suggestion.weapon+"  Game Over");
			System.exit(0);
		}
		else {
			//Player has incorrectly guessed
			JOptionPane.showMessageDialog(null, this.getPlayerName()+" incorrectly guessed: "+suggestion.person+", "+suggestion.room+", "+suggestion.weapon);
		}
	}
	
	/**
	 * Will generate a suggestion for player based on seen cards 
	 */
	public void createSuggestion(String room) {
		ArrayList<String> unseenPlayers = new ArrayList<>();
		ArrayList<String> unseenWeapons = new ArrayList<>();
		
		for (String playerName : Board.getInstance().getPlayerNames()) {
			if (!(getSeenCards().contains(playerName))) {
					unseenPlayers.add(playerName);
			}
		}
		for (String weaponName : Board.getInstance().getWeapons()) {
			if (!(getSeenCards().contains(weaponName))) {
					unseenWeapons.add(weaponName);				
			}
		}
		Random rand = new Random();
		int randomNum1 = rand.nextInt(unseenPlayers.size());
		int randomNum2 = rand.nextInt(unseenWeapons.size());

		suggestion = new Solution(unseenPlayers.get(randomNum1), room, unseenWeapons.get(randomNum2));		
	}
	
	/**
	 * getter for suggestion variable
	 * @return
	 */
	public Solution getSuggestion() {
		return suggestion;
	}
	
	/**
	 * setter for suggestion variable
	 * @param suggestion
	 */
	public void setSuggestion(Solution suggestion) {
		this.suggestion = suggestion;
	}
	
	/**
	 * setter for justVisistedRoom boolean
	 * @param justVisitedRoom
	 */
	public void setJustVisitedRoom(BoardCell justVisitedRoom) {
		this.justVisitedRoom = justVisitedRoom;
	}
	
	/**
	 * getter for justVisistedRoom boolean
	 * @return
	 */
	public BoardCell getJustVisitedRoom() {
		return this.justVisitedRoom;
	}

	
}
