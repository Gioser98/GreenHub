package GreenHub;

public class User {

	private String username;
	private int greenPointsBalance;
	private int type;
	private String name;
	private String surname;
	private Location location;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getGreenPointsBalance() {
		return greenPointsBalance;
	}
	public void setGreenPointsBalance(int greenPointsBalance) {
		this.greenPointsBalance = greenPointsBalance;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}

}