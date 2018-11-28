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
	
	/**
	 * Returns true if the solution passed is equal to THIS
	 * @param solution
	 * @return
	 */
	public boolean isEqual(Solution solution) {
		return this.person.equals(solution.getPerson()) && this.room.equals(solution.getRoom()) && this.weapon.equals(solution.getWeapon());
	}

	/**
	 * Getter for person
	 * @return
	 */
	public String getPerson() {
		return person;
	}
	
	/**
	 * Setter for person
	 * @param person
	 */
	public void setPerson(String person) {
		this.person = person;
	}
	
	/**
	 * Getter for room
	 * @return
	 */
	public String getRoom() {
		return room;
	}
	
	/**
	 * Setter for room
	 * @param room
	 */
	public void setRoom(String room) {
		this.room = room;
	}
	
	/**
	 * Getter for weapon
	 * @return
	 */
	public String getWeapon() {
		return weapon;
	}
	
	/**
	 * Setter for weapon
	 * @param weapon
	 */
	public void setWeapon(String weapon) {
		this.weapon = weapon;
	}
	
	
}
