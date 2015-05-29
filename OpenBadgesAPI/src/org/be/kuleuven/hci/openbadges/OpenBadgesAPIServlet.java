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
import java.util.Scanner;

import javax.servlet.http.*;

import org.be.kuleuven.hci.openbadges.mailnotification.Mail;

@SuppressWarnings("serial")
public class OpenBadgesAPIServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		resp.getWriter().println("sending email");
		
		String content = new Scanner(new File("template.html")).useDelimiter("\\Z").next();
		//String content = "<div style=\"color:red;\">BRIDGEYE</div>";
		Mail.sendmail("test",content, "joseluis.santos.cs@gmail.com");
		
		
		//System.out.println(content);
	}
}
