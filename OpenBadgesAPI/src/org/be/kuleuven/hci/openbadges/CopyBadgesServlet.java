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
package org.be.kuleuven.hci.openbadges;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Scanner;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import org.be.kuleuven.hci.openbadges.badgestore.UploadWeSPOT;
import org.be.kuleuven.hci.openbadges.mailnotification.Mail;
import org.be.kuleuven.hci.openbadges.model.AwardedBadge;
import org.be.kuleuven.hci.openbadges.model.Badge;
import org.be.kuleuven.hci.openbadges.persistanceLayer.PersistanceLayerAwardedBadge;
import org.be.kuleuven.hci.openbadges.persistanceLayer.PersistanceLayerBadge;

import com.google.appengine.api.datastore.Text;
import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

@SuppressWarnings("serial")
public class CopyBadgesServlet extends HttpServlet {
	
	private static final Logger log = Logger.getLogger(CopyBadgesServlet.class.getName());
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		String[] badgesIds = req.getParameterValues("checkbox-badges");
		String[] inquiryIds = req.getParameterValues("checkbox-inquiries");
		String context = req.getParameter("context");
		log.warning("Hemos entrado");
		log.warning("badges Ids:"+badgesIds.length);
		log.warning("inquiry Ids:"+badgesIds.length);

		for (String inquiryId: inquiryIds){
			log.warning("inquiry:"+inquiryId);
			try {
				JSONArray badges_array = new JSONArray(PersistanceLayerBadge.getbadgeByContext(inquiryId, ""));				
				for (String badgeId: badgesIds){
					Badge badge = PersistanceLayerBadge.getbadgeById(badgeId);
					boolean found = false;
					for (int i=0; i<badges_array.length();i++){
						if (badge.getJSON().getJSONObject("jsonBadge").getString("name").compareTo(badges_array.getJSONObject(i).getJSONObject("jsonBadge").getString("name"))==0 
								&& badge.getJSON().getJSONObject("jsonBadge").getString("description").compareTo(badges_array.getJSONObject(i).getJSONObject("jsonBadge").getString("description"))==0){
							found=true;
						}
					}
					log.warning("found:"+found);
					if (found==false){
						Badge badgeCopy = new Badge();
						badgeCopy.setContext(inquiryId);
						badgeCopy.setjsonBadge(badge.getjsonBadge());
						badgeCopy.setNameApp(badge.getNameApp());
						log.warning(badgeCopy.getContext());
						PersistanceLayerBadge.saveBadge(badgeCopy);
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		//req.getRequestDispatcher("/menu.jsp?context="+req.getParameter("context")+"&inquiryserver="+req.getParameter("inquiryserver")).forward(req, resp);
		
		resp.sendRedirect("/menu.jsp?context="+req.getParameter("context")+"&inquiryserver="+req.getParameter("inquiryserver")+"&userid="+req.getParameter("userid"));
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
		doGet(req, resp);
	}
}
