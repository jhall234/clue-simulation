package clueGame;

public class Solution {
	public String person;
	public String room;
	public String weapon;
	
	public Solution() {
		this.person = "";
		this.room = "";
		this.weapon = "";
	}
	
	public Solution(String person, String room, String weapon) {
		this.person = person;
		this.room = room;
		this.weapon = weapon;
	}

	public String getPerson() {
		return person;
	}

	public void setPerson(String person) {
		this.person = person;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public String getWeapon() {
		return weapon;
	}

	public void setWeapon(String weapon) {
		this.weapon = weapon;
	}
	
	
}
