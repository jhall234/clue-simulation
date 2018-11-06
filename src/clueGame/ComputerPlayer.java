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
	
	public Solution createSuggestion(String room) {
//		String playerName = "";
//		String weaponName = "";
		ArrayList<String> seenPlayers = new ArrayList<String>();
		ArrayList<String> seenWeapons = new ArrayList<String>();
		
		for (Card card : this.getSeenCards()) {
			if (card.getCardType().equals(CardType.PERSON)) {
				seenPlayers.add(card.getCardName());
			}
			if (card.getCardType().equals(CardType.WEAPON)) {
				seenWeapons.add(card.getCardName());
			}
		}
		
//		int numPlayers = Board.getInstance().getPlayers().size();
//		int numWeapons = Board.getInstance().getWeapons().size();
		
		ArrayList<String> unseenPlayers = new ArrayList<String>();
		ArrayList<String> unseenWeapons = new ArrayList<String>();
		
		for (Player player : Board.getInstance().getPlayers()) {
			if (!seenPlayers.contains(player.getPlayerName())) {
				unseenPlayers.add(player.getPlayerName());
			}
		}
		for (String weapon : Board.getInstance().getWeapons()) {
			if (!seenWeapons.contains(weapon)) {
				unseenWeapons.add(weapon);
			}
		}
		
		
		return new Solution(unseenPlayers.get(new Random().nextInt(unseenPlayers.size())), room, unseenWeapons.get(new Random().nextInt(unseenWeapons.size())));
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
