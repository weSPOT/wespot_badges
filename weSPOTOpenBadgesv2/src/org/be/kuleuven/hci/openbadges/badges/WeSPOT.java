package org.be.kuleuven.hci.openbadges.badges;

import java.util.ArrayList;

import org.be.kuleuven.hci.openbadges.model.AuthorizedKey;
import org.be.kuleuven.hci.openbadges.model.AwardedBadges;
import org.be.kuleuven.hci.openbadges.model.Badge;
import org.be.kuleuven.hci.openbadges.model.Issuer;
import org.be.kuleuven.hci.openbadges.persistanceLayer.PersistanceLayerAwardedBadges;
import org.be.kuleuven.hci.openbadges.utils.OpenBadgeAPI;
import org.be.kuleuven.hci.openbadges.utils.weSPOTBadgesConstants;

import com.google.appengine.api.datastore.Text;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

public class WeSPOT {
	
	public static Badge weSPOTBadge(String inquiryId, AuthorizedKey authorizedKey, int phase, int level){
		Badge badge = new Badge();
		badge.setContext(inquiryId);
		badge.setNameApp("wespot_automatic_badges");
		JSONObject badgejson;
		try {
			String name,file,description,criteria;
			name=file=description=criteria="";
			if (phase>0){
				if (level==1){
					description = weSPOTBadgesConstants.BADGE_DESCRIPTION_LEVEL1;
					criteria = weSPOTBadgesConstants.BADGE_CRITERIA_LEVEL1;
				}else if (level==2){
					description = weSPOTBadgesConstants.BADGE_DESCRIPTION_LEVEL2;
					criteria = weSPOTBadgesConstants.BADGE_CRITERIA_LEVEL2;
				}else if (level==3){
					description = weSPOTBadgesConstants.BADGE_DESCRIPTION_LEVEL3;
					criteria = weSPOTBadgesConstants.BADGE_CRITERIA_LEVEL3;
				}
				if (phase==1){
					if (level==1){
						name = weSPOTBadgesConstants.PHASE1_BADGE_NAME_LEVEL1;
						file = weSPOTBadgesConstants.PHASE1_BADGE_FILE_LEVEL1;								
					}else if (level==2){
						name = weSPOTBadgesConstants.PHASE1_BADGE_NAME_LEVEL2;
						file = weSPOTBadgesConstants.PHASE1_BADGE_FILE_LEVEL2;												
					}else if (level==3){
						name = weSPOTBadgesConstants.PHASE1_BADGE_NAME_LEVEL3;
						file = weSPOTBadgesConstants.PHASE1_BADGE_FILE_LEVEL3;
					}
				}else if (phase==2){
					if (level==1){
						name = weSPOTBadgesConstants.PHASE2_BADGE_NAME_LEVEL1;
						file = weSPOTBadgesConstants.PHASE2_BADGE_FILE_LEVEL1;								
					}else if (level==2){
						name = weSPOTBadgesConstants.PHASE2_BADGE_NAME_LEVEL2;
						file = weSPOTBadgesConstants.PHASE2_BADGE_FILE_LEVEL2;												
					}else if (level==3){
						name = weSPOTBadgesConstants.PHASE2_BADGE_NAME_LEVEL3;
						file = weSPOTBadgesConstants.PHASE2_BADGE_FILE_LEVEL3;
					}
				}else if (phase==3){
					if (level==1){
						name = weSPOTBadgesConstants.PHASE3_BADGE_NAME_LEVEL1;
						file = weSPOTBadgesConstants.PHASE3_BADGE_FILE_LEVEL1;								
					}else if (level==2){
						name = weSPOTBadgesConstants.PHASE3_BADGE_NAME_LEVEL2;
						file = weSPOTBadgesConstants.PHASE3_BADGE_FILE_LEVEL2;												
					}else if (level==3){
						name = weSPOTBadgesConstants.PHASE3_BADGE_NAME_LEVEL3;
						file = weSPOTBadgesConstants.PHASE3_BADGE_FILE_LEVEL3;
					}
				}else if (phase==4){
					if (level==1){
						name = weSPOTBadgesConstants.PHASE4_BADGE_NAME_LEVEL1;
						file = weSPOTBadgesConstants.PHASE4_BADGE_FILE_LEVEL1;								
					}else if (level==2){
						name = weSPOTBadgesConstants.PHASE4_BADGE_NAME_LEVEL2;
						file = weSPOTBadgesConstants.PHASE4_BADGE_FILE_LEVEL2;												
					}else if (level==3){
						name = weSPOTBadgesConstants.PHASE4_BADGE_NAME_LEVEL3;
						file = weSPOTBadgesConstants.PHASE4_BADGE_FILE_LEVEL3;
					}
				}else if (phase==5){
					if (level==1){
						name = weSPOTBadgesConstants.PHASE5_BADGE_NAME_LEVEL1;
						file = weSPOTBadgesConstants.PHASE5_BADGE_FILE_LEVEL1;								
					}else if (level==2){
						name = weSPOTBadgesConstants.PHASE5_BADGE_NAME_LEVEL2;
						file = weSPOTBadgesConstants.PHASE5_BADGE_FILE_LEVEL2;												
					}else if (level==3){
						name = weSPOTBadgesConstants.PHASE5_BADGE_NAME_LEVEL3;
						file = weSPOTBadgesConstants.PHASE5_BADGE_FILE_LEVEL3;
					}
				}else if (phase==6){
					if (level==1){
						name = weSPOTBadgesConstants.PHASE6_BADGE_NAME_LEVEL1;
						file = weSPOTBadgesConstants.PHASE6_BADGE_FILE_LEVEL1;								
					}else if (level==2){
						name = weSPOTBadgesConstants.PHASE6_BADGE_NAME_LEVEL2;
						file = weSPOTBadgesConstants.PHASE6_BADGE_FILE_LEVEL2;												
					}else if (level==3){
						name = weSPOTBadgesConstants.PHASE6_BADGE_NAME_LEVEL3;
						file = weSPOTBadgesConstants.PHASE6_BADGE_FILE_LEVEL3;
					}
				}
			}else if (phase==0){
				if (level==1){
					name = weSPOTBadgesConstants.INQUIRY_BADGE_NAME_BRONZE;
					file = weSPOTBadgesConstants.INQUIRY_BADGE_FILE_BRONZE;
					description = weSPOTBadgesConstants.BADGE_DESCRIPTION_BRONZE;
					criteria = weSPOTBadgesConstants.BADGE_CRITERIA_BRONZE;
				}else if (level==2){
					name = weSPOTBadgesConstants.INQUIRY_BADGE_NAME_SILVER;
					file = weSPOTBadgesConstants.INQUIRY_BADGE_FILE_SILVER;
					description = weSPOTBadgesConstants.BADGE_DESCRIPTION_SILVER;
					criteria = weSPOTBadgesConstants.BADGE_CRITERIA_SILVER;											
				}else if (level==3){
					name = weSPOTBadgesConstants.INQUIRY_BADGE_NAME_GOLD;
					file = weSPOTBadgesConstants.INQUIRY_BADGE_FILE_GOLD;
					description = weSPOTBadgesConstants.BADGE_DESCRIPTION_GOLD;
					criteria = weSPOTBadgesConstants.BADGE_CRITERIA_GOLD;
				}

			}
			if (phase>=0){
				badgejson = new JSONObject(Utils.openBadgeString(name, file, description, criteria));
				badgejson.put("issuer", Issuer.getIssuer());
				badge.setjsonBadge(new Text(badgejson.toString()));
				return badge;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return null;
	}
	
	public static String getFileName(int phase, int level){		
		if (phase==1){
			if (level==1) return weSPOTBadgesConstants.PHASE1_BADGE_FILE_LEVEL1;
			else if (level==2) return weSPOTBadgesConstants.PHASE1_BADGE_FILE_LEVEL2;
			else if (level==3) return weSPOTBadgesConstants.PHASE1_BADGE_FILE_LEVEL3;
		}else if (phase==2){
			if (level==1) return weSPOTBadgesConstants.PHASE2_BADGE_FILE_LEVEL1;
			else if (level==2) return weSPOTBadgesConstants.PHASE2_BADGE_FILE_LEVEL2;
			else if (level==3) return weSPOTBadgesConstants.PHASE2_BADGE_FILE_LEVEL3;
		}else if (phase==3){
			if (level==1) return weSPOTBadgesConstants.PHASE3_BADGE_FILE_LEVEL1;
			else if (level==2) return weSPOTBadgesConstants.PHASE3_BADGE_FILE_LEVEL2;
			else if (level==3) return weSPOTBadgesConstants.PHASE3_BADGE_FILE_LEVEL3;
		}else if (phase==4){
			if (level==1) return weSPOTBadgesConstants.PHASE4_BADGE_FILE_LEVEL1;
			else if (level==2) return weSPOTBadgesConstants.PHASE4_BADGE_FILE_LEVEL2;
			else if (level==3) return weSPOTBadgesConstants.PHASE4_BADGE_FILE_LEVEL3;	
		}else if (phase==5){
			if (level==1) return weSPOTBadgesConstants.PHASE5_BADGE_FILE_LEVEL1;
			else if (level==2) return weSPOTBadgesConstants.PHASE5_BADGE_FILE_LEVEL2;
			else if (level==3) return weSPOTBadgesConstants.PHASE5_BADGE_FILE_LEVEL3;
		}else if (phase==6){
			if (level==1) return weSPOTBadgesConstants.PHASE6_BADGE_FILE_LEVEL1;
			else if (level==2) return weSPOTBadgesConstants.PHASE6_BADGE_FILE_LEVEL2;
			else if (level==3) return weSPOTBadgesConstants.PHASE6_BADGE_FILE_LEVEL3;
		}else if (phase==0){
			if (level==1) return weSPOTBadgesConstants.INQUIRY_BADGE_FILE_BRONZE;
			else if (level==2) return weSPOTBadgesConstants.INQUIRY_BADGE_FILE_SILVER;
			else if (level==3) return weSPOTBadgesConstants.INQUIRY_BADGE_FILE_GOLD;
		}
		
		return "";			
	}
	
}
