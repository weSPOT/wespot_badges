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
package org.be.kuleuven.hci.openbadges.model;

import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

public class Issuer {
	//"issuer": { "origin": "http://openeducation.us/", "name": "Course Lecturer", "org": "Not a Real Badge, 2012", "contact": "issuer_email@hawaii.edu" } } }
	
	public static final String NAME = "WeSPOT";
	public static final String ORIGIN = "http://wespot.net/";
	public static final String ORG = "weSPOT aims at propagating scientific inquiry as the approach for science learning and teaching in combination with today's curricula and teaching practices. It lowers the threshold for linking everyday life with science teaching in schools by technology.";
	public static final String CONTACT = "info@wespot.net";
	
	public static JSONObject getIssuer() throws JSONException {
		JSONObject issuer = new JSONObject();
		issuer.put("name", Issuer.NAME);
		issuer.put("origin", Issuer.ORIGIN);
		issuer.put("org", Issuer.ORG);
		issuer.put("contact", Issuer.CONTACT);
		return issuer;
	}

}
