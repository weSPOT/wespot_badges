/*******************************************************************************
 * Copyright (c) 2014 KU Leuven
 * This library is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *  
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *  
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with this library.  If not, see <http://www.gnu.org/licenses/>.
 *  
 * Contributors:
 *     Jose Luis Santos
 *******************************************************************************/
package org.be.kuleuven.hci.openbadges.utils;

import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

public class JSONExamples {

	public static String createBadgeExample(){
		JSONObject badgeExample = new JSONObject();
		try {
			badgeExample.put("context", "123");
			badgeExample.put("version", "1.0.0");
			badgeExample.put("name", "Explorer");
			badgeExample.put("image", "http://urlofyourbadge.com/testbadgeimage.png");
			badgeExample.put("description", "Has displayed expertise in hand-made badge creation.");
			badgeExample.put("criteria", "http://etec.hawaii.edu");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return badgeExample.toString();
	}
	
	public static String awardBadgeExample(){
		JSONObject badgeExample = new JSONObject();
		try {
			badgeExample.put("badgeId", "4001");
			badgeExample.put("recipient", "learner.name@gmail.com");
			badgeExample.put("evidence", "http://google.com");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return badgeExample.toString();
	}
	
	
}
