package org.be.kuleuven.hci.openbadges.test;

public class generictest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String badge_id = "263681phase1.pngGoogle_109002798505335212351";
		int position = badge_id.indexOf("1phase1.png");
		System.out.println(badge_id.substring(0, position));
		System.out.println(badge_id.substring(position+"1phase1.png".length()));
		String key = "26368:Google_109002798505335212351";
		String[] split = key.split(":");
		System.out.println(split[0]);
		System.out.println(split[1]);
	}

}
