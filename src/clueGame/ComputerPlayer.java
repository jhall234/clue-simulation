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
		String playerName = "";
		String weaponName = "";
		Set<String> seenPlayers = new HashSet<String>();
		Set<String> seenWeapons = new HashSet<String>();
		
//		for (Card card : this.getSeenCards()) {
//			if (card.getCardType().equals(CardType.PERSON)) {
//				seenPlayers.add(card.getCardName());
//			}
//			if (card.getCardType().equals(CardType.WEAPON)) {
//				seenWeapons.add(card.getCardName());
//			}
//		}
//		
//		int numPlayers = Board.getInstance().getPlayers().size();
//		int numWeapons = Board.getInstance().getWeapons().size();
//		
//		while (true) {
//			playerName = Board.getInstance().getPlayers().get(new Random().nextInt(numPlayers)).getPlayerName();
//			if (!seenPlayers.contains(playerName)) {
//				break;
//			}
//		}
//		while (true) {
//			weaponName = Board.getInstance().getPlayers().get(new Random().nextInt(numWeapons)).getPlayerName();
//			if (!seenWeapons.contains(weaponName)) {
//				break;
//			}
//		}
		
		return new Solution(playerName, room, weaponName);
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
