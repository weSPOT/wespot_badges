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
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

@SuppressWarnings("serial")
public class AwardBadgeServlet extends HttpServlet {
	
	private static final Logger log = Logger.getLogger(AwardBadgeServlet.class.getName());
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		String[] studentIds = req.getParameterValues("checkbox-students");
		//System.out.println("Student identifier: "+studentId);
		String badgeId = req.getParameter("badge");
		System.out.println("Badge identifier: "+badgeId);
		String context = req.getParameter("context");
		String evidence = req.getParameter("evidence");
		//AwardedBadge awardedBadge = new AwardedBadge();
		Badge badge = PersistanceLayerBadge.getbadgeById(badgeId);
		for (String studentId: studentIds){
			AwardedBadge awardedBadge = new AwardedBadge();
			awardedBadge.setContext(badge.getContext());
			awardedBadge.setBadgeId(badgeId);
			awardedBadge.setusername(studentId);
			awardedBadge.setStarttime(Calendar.getInstance().getTime());				
			awardedBadge.setEventsRelated(new Text(evidence));
			JSONObject completedBadge = new JSONObject();
			try {
				completedBadge.put("badge", new JSONObject(badge.getjsonBadge().getValue()));
				completedBadge.put("recipient", studentId);
				completedBadge.put("issued_on", awardedBadge.getStarttime().toString());
				completedBadge.put("evidence", evidence);				
				System.out.println("badge: "+badge.getjsonBadge().getValue());
								
				awardedBadge.setAwardedJSONBadge(new Text(completedBadge.toString()));
				System.out.println("completed: "+completedBadge.toString());
				System.out.println("awarded: "+awardedBadge.getJSON().toString());
				awardedBadge.setNameApp("editor");
				System.out.println(awardedBadge.getJSON().toString());
				String id = PersistanceLayerAwardedBadge.saveAwardedBadge(awardedBadge);
				String content = new Scanner(new File("template.html")).useDelimiter("\\Z").next();
				//Mail.sendmail("test",content, "joseluis.santos.cs@gmail.com");
				content = content.replaceAll("nameofbadge", "7generic.png");
				content = content.replaceAll("Titleofthebadge", completedBadge.getJSONObject("badge").getString("name"));
				content = content.replaceAll("DescriptionOftheBadge", completedBadge.getJSONObject("badge").getString("description"));
				content = content.replaceAll("definitionofinquiry", badge.getContext());
				Mail.sendmail(Mail.getSubject(), content, Mail.getEmail(studentId));
			} catch (JSONException e) {	
				log.severe(e.toString());				
			}
		}
		//req.getRequestDispatcher("/menu.jsp?context="+req.getParameter("context")+"&inquiryserver="+req.getParameter("inquiryserver")).forward(req, resp);
		
		resp.sendRedirect("/menu.jsp?context="+req.getParameter("context")+"&inquiryserver="+req.getParameter("inquiryserver")+"&userid="+req.getParameter("userid"));
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
		doGet(req, resp);
	}
}
