package GreenHub;

public class GreenHub {
	
	public static void main(String[] args ) {
		System.out.println("Test123!");
		
		User User1 = new User();
		Location loc1 = new Location();
		User User2 = new User("Skabboz", 0, 0, "Alessio", "DiSanto", loc1);
		
		System.out.println(User1.getUsername());
		System.out.println(User2.getName());
		
		chargeNow();
	}

	public static void chargeNow() {
		System.out.println("chargeNow");
	}

	public static void reserveSpot() {
		System.out.println("reserveSpot");
	}

}