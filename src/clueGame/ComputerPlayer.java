package clueGame;

import java.util.*;

public class ComputerPlayer extends Player {
	private BoardCell justVisitedRoom;
	private Solution suggestion;
	
	public ComputerPlayer() {
		super();
		this.suggestion = new Solution();
	}
	
	public ComputerPlayer(String playerName, int red, int green, int blue, PlayerType playerType, int row, int column) {
		super(playerName, red, green, blue, playerType, row, column);
		this.suggestion = new Solution();
	}
	
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
	
	public void makeAccusation() {
		
	}
	
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
	
	public Solution getSuggestion() {
		return suggestion;
	}

	public void setSuggestion(Solution suggestion) {
		this.suggestion = suggestion;
	}

	public void setJustVisitedRoom(BoardCell justVisitedRoom) {
		this.justVisitedRoom = justVisitedRoom;
	}
	
	public BoardCell getJustVisitedRoom() {
		return this.justVisitedRoom;
	}
}
