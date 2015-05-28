package org.be.kuleuven.hci.openbadges.utils;

public class weSPOTBadgesConstants {
	public static final String URL_OPENBADGESAPI = "http://openbadgesapi.appspot.com";
	//public static final String URL_OPENBADGESAPI = "http://localhost:8888";
	public static final String URL_CREATEBADGES = URL_OPENBADGESAPI+"/rest/badges";
	public static final String BADGESINCACHE = "awardedbadges";
	public static final String NAMEAPP = "wespot_automatic_badges";
	public static final String KEYOPENBADGES = "{openbadges}"; //App Engine key
	public static final String KEYELGG = "{keyelgg}"; //ELGG Api Key
	//public static final String KEYOPENBADGES = "oolktt4imuci897pjpvsha9fds"; //Localhost key
	//===================PEERS AND NUMBER CONTRIBUTIONS===========================
	
	public static final String PEERS_CONTRIBUTIONS = "5";
	public static final int CONTRIBUTIONS_1_LEVEL = 10;
	public static final int CONTRIBUTIONS_2_LEVEL = 10;
	public static final int CONTRIBUTIONS_3_LEVEL = 5;
	
	//===================DESCRIPTIONS AND CRITERIA===========================
	public static final String BADGE_DESCRIPTION_LEVEL1 = "You have done enough actions to get this badge. Try that others contribute to your work. You are ready to achieve the second level - Go Ahead!";
	public static final String BADGE_DESCRIPTION_LEVEL2 = "You got enough reactions from your peers to get this badge. Try that others rate your work positively!. You are ready to achieve the final level - Go Ahead!";
	public static final String BADGE_DESCRIPTION_LEVEL3 = "You nailed it! You have the complete badge";
	public static final String BADGE_CRITERIA_LEVEL1 = "The students has generated at least "+CONTRIBUTIONS_1_LEVEL+" contributions.";
	public static final String BADGE_CRITERIA_LEVEL2 = PEERS_CONTRIBUTIONS+" peers have to react to your contributions: Commenting, rating, liking, etc (number of reactions "+CONTRIBUTIONS_2_LEVEL+")";
	public static final String BADGE_CRITERIA_LEVEL3 = PEERS_CONTRIBUTIONS+" peers have to rate your contributions positively (more than 3 stars).";
	
	
	//===================1 phase BADGES============================
	public static final String PHASE1_BADGE_NAME_LEVEL1 = "Black Badge - Fist Level ";
	public static final String PHASE1_BADGE_NAME_LEVEL2 = "Black Badge - Second Level";
	public static final String PHASE1_BADGE_NAME_LEVEL3 = "Black Badge - Completed";
	public static final String PHASE1_BADGE_FILE_LEVEL1 = "1phase1.png";
	public static final String PHASE1_BADGE_FILE_LEVEL2 = "1phase2.png";
	public static final String PHASE1_BADGE_FILE_LEVEL3 = "1phase3.png";

	//===================2 phase BADGES============================
	public static final String PHASE2_BADGE_NAME_LEVEL1 = "Red Badge - Fist Level ";
	public static final String PHASE2_BADGE_NAME_LEVEL2 = "Red Badge - Second Level";
	public static final String PHASE2_BADGE_NAME_LEVEL3 = "Red Badge - Completed";
	public static final String PHASE2_BADGE_FILE_LEVEL1 = "2phase1.png";
	public static final String PHASE2_BADGE_FILE_LEVEL2 = "2phase2.png";
	public static final String PHASE2_BADGE_FILE_LEVEL3 = "2phase3.png";
	
	//===================3 phase BADGES============================
	public static final String PHASE3_BADGE_NAME_LEVEL1 = "Green Badge - Fist Level ";
	public static final String PHASE3_BADGE_NAME_LEVEL2 = "Green Badge - Second Level";
	public static final String PHASE3_BADGE_NAME_LEVEL3 = "Green Badge - Completed";
	public static final String PHASE3_BADGE_FILE_LEVEL1 = "3phase1.png";
	public static final String PHASE3_BADGE_FILE_LEVEL2 = "3phase2.png";
	public static final String PHASE3_BADGE_FILE_LEVEL3 = "3phase3.png";
	
	//===================4 phase BADGES============================
	public static final String PHASE4_BADGE_NAME_LEVEL1 = "Blue Badge - Fist Level ";
	public static final String PHASE4_BADGE_NAME_LEVEL2 = "Blue Badge - Second Level";
	public static final String PHASE4_BADGE_NAME_LEVEL3 = "Blue Badge - Completed";
	public static final String PHASE4_BADGE_FILE_LEVEL1 = "4phase1.png";
	public static final String PHASE4_BADGE_FILE_LEVEL2 = "4phase2.png";
	public static final String PHASE4_BADGE_FILE_LEVEL3 = "4phase3.png";
	
	//===================5 phase BADGES============================
	public static final String PHASE5_BADGE_NAME_LEVEL1 = "Yellow Badge - Fist Level ";
	public static final String PHASE5_BADGE_NAME_LEVEL2 = "Yellow Badge - Second Level";
	public static final String PHASE5_BADGE_NAME_LEVEL3 = "Yellow Badge - Completed";
	public static final String PHASE5_BADGE_FILE_LEVEL1 = "5phase1.png";
	public static final String PHASE5_BADGE_FILE_LEVEL2 = "5phase2.png";
	public static final String PHASE5_BADGE_FILE_LEVEL3 = "5phase3.png";
	
	//===================4 phase BADGES============================
	public static final String PHASE6_BADGE_NAME_LEVEL1 = "Brown Badge - Fist Level ";
	public static final String PHASE6_BADGE_NAME_LEVEL2 = "Brown Badge - Second Level";
	public static final String PHASE6_BADGE_NAME_LEVEL3 = "Brown Badge - Completed";
	public static final String PHASE6_BADGE_FILE_LEVEL1 = "6phase1.png";
	public static final String PHASE6_BADGE_FILE_LEVEL2 = "6phase2.png";
	public static final String PHASE6_BADGE_FILE_LEVEL3 = "6phase3.png";
	
	//===================4 phase BADGES============================
	public static final String INQUIRY_BADGE_NAME_GOLD = "Inquiry Badge - Gold medal ";
	public static final String INQUIRY_BADGE_NAME_SILVER = "Inquiry Badge - Silver medal ";
	public static final String INQUIRY_BADGE_NAME_BRONZE = "Inquiry Badge - Bronze medal ";
	public static final String INQUIRY_BADGE_FILE_GOLD = "0phase3.png";
	public static final String INQUIRY_BADGE_FILE_SILVER = "0phase2.png";
	public static final String INQUIRY_BADGE_FILE_BRONZE = "0phase1.png";
	public static final String BADGE_DESCRIPTION_GOLD = "You have already achieved all the possible badges. Congratulations!";
	public static final String BADGE_DESCRIPTION_SILVER = "You have already achieved the second level in all the possible badges. Go agead and get the gold medal!";
	public static final String BADGE_DESCRIPTION_BRONZE = "You have already achieved the first level in all the possible badges. Go agead and get the gold silver!";
	public static final String BADGE_CRITERIA_GOLD = "The user has achieved the third level in all the different badges";
	public static final String BADGE_CRITERIA_SILVER = "The user has achieved the second level in all the different badges";
	public static final String BADGE_CRITERIA_BRONZE = "The user has achieved the first level in all the different badges";
	

	
	

}
