package org.be.kuleuven.hci.openbadges;

import java.io.IOException;

import javax.servlet.http.*;

import org.be.kuleuven.hci.openbadges.awardingbadges.rules.weSPOTInquiryRules;


@SuppressWarnings("serial")
public class WeSPOTInquiryServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		for (int i=1; i<4;i++){
			weSPOTInquiryRules.awardInquiriesBadges(i);
		}
	}
}
