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
package org.be.kuleuven.hci.openbadges.badgestore;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.be.kuleuven.hci.openbadges.model.Badge;
import org.be.kuleuven.hci.openbadges.model.Issuer;
import org.be.kuleuven.hci.openbadges.persistanceLayer.PersistanceLayerBadge;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.datastore.Text;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

public class UploadWeSPOT extends HttpServlet {
	
	private static final Logger log = Logger.getLogger(UploadWeSPOT.class.getName());
	
	@Override
    public void doGet(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {
		log.info("File"+req.getParameter("file"));
		log.info("Name"+req.getParameter("name"));
		log.info("Context"+ req.getParameter("context"));
		log.info("Criteria"+req.getParameter("criteria"));
		log.info("Description"+req.getParameter("description"));
    	Badge badge = new Badge();
        String img_path = req.getParameter("file");
        JSONObject badgejson = new JSONObject();
        System.out.println("Entrar-1");
        try {
			badgejson.put("image", img_path);
			//System.out.println("object:"+req.getParameter("context").toString());
			badgejson.put("context", req.getParameter("context"));
            badgejson.put("name", req.getParameter("name"));
            badgejson.put("description", req.getParameter("description"));
            badgejson.put("criteria", req.getParameter("criteria"));  
            badgejson.put("version", "1.0.0");  
            System.out.println("Entrar-2: "+badgejson.toString());
			if (badgejson.has("context")&&badgejson.has("version")&&badgejson.has("name")&&badgejson.has("image")&&badgejson.has("description")&&badgejson.has("criteria")){
				System.out.println("Entrar-3");
				badgejson.put("issuer", Issuer.getIssuer());
				badge.setNameApp("editor");
				badge.setContext(badgejson.getString("context"));
				badgejson.remove("context");
				badge.setjsonBadge(new Text(badgejson.toString()));
				System.out.println("Entrar");
				String id = PersistanceLayerBadge.saveBadge(badge);
			}else
				log.severe("NO ALL FIELDS PRESENT");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			log.severe("error servlet upload"+e.toString());
		}            

        //req.getRequestDispatcher("/menu.jsp?context="+req.getParameter("context")).forward(req, res);
        res.sendRedirect("/menu.jsp?context="+req.getParameter("context")+"&inquiryserver="+req.getParameter("inquiryserver")+"&userid="+req.getParameter("userid"));
    }
}
