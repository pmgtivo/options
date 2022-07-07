package optionselling;

public class Test {

	public static void main(String[] args) {
		double d = 35657.89;
		int i = (int) d;
		double roundDbl = Math.round(i / 100.0) * 100;
		System.out.println("Rounded Double value: " + roundDbl);
	}

}
