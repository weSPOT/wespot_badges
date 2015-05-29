<%@ page import="org.be.kuleuven.hci.openbadges.persistanceLayer.*"%>
<%@ page import="com.google.appengine.labs.repackaged.org.json.JSONObject"%>
<%@ page import="com.google.appengine.labs.repackaged.org.json.JSONArray"%>
<%@ page import="org.be.kuleuven.hci.openbadges.utils.RestClient"%>
<html>
<head>

<link rel="stylesheet" href="http://code.jquery.com/mobile/1.4.2/jquery.mobile-1.4.2.min.css">
<link rel="stylesheet" type="text/css" href="http://jlsantos.tel.unir.net/d3/css/styles.css"/>
<link rel="stylesheet" href="css/styletable.css" type="text/css" media="screen"/>
<link rel="stylesheet" href="css/jquery.mobile-1.4.2.css"> 
<script language="javascript" type="text/javascript" src="http://code.jquery.com/jquery-2.1.0.min.js"></script>
<script language="javascript" type="text/javascript" src="http://code.jquery.com/mobile/1.4.2/jquery.mobile-1.4.2.min.js"></script>
<script language="javascript" type="text/javascript" src="http://d3js.org/d3.v3.min.js"></script>
<script language="javascript" type="text/javascript" src="js/utils.js"></script>
<script language="javascript" type="text/javascript" src="js/svg.js"></script>
</head>
<body>

<div id="header" data-role="header"> 
<%if (request.getParameter("admin")!=null) {%>
	<a href="javascript:location.replace('/menu.jsp?context=<%=request.getParameter("context")%>&inquiryserver=<%=request.getParameter("inquiryserver")%>&userid=<%=request.getParameter("userid")%>')" data-icon="back" class="ui-btn-left" title="Go back">Back</a>
<%}%>
	<h1>Badges</h1>
<%if (request.getParameter("admin")==null) {%>
	<div data-role="controlgroup" data-type="horizontal" class="ui-btn-right">
		<select id="inquiry" name="inquiry" > <option>Choose one inquiry...</option></select>
	</div>
<%}%>
</div>

<div data-role="body"> 
		<p/>
<script>

	<%
	String[] parts = request.getParameter("userid").toLowerCase().split("_");
	String inquiriesEnrolled = RestClient.doGet("http://"+request.getParameter("inquiryserver")+"/services/api/rest/json/?method=user.inquiries&api_key={key}&oauthId="+parts[1]+"&oauthProvider="+parts[0]);
	%>
	var user = "<%=request.getParameter("userid")%>";
	trackData(user, "access", "student_interface", "<%=request.getParameter("context")%>", "badges", "");//request.getParameter("context")
	
	var inquiry_enrolled = <%=inquiriesEnrolled%>
<%if (request.getParameter("admin")==null) {%>
	for (var i = 0; i < inquiry_enrolled.result.length; i++) {
		if (inquiry_enrolled.result[i].inquiryId=='<%=request.getParameter("context")%>'){
			$('#inquiry').append('<option value="'+inquiry_enrolled.result[i].inquiryId+'" selected>"'+inquiry_enrolled.result[i].title+'"</option>');
		}else{
			$('#inquiry').append('<option value="'+inquiry_enrolled.result[i].inquiryId+'">"'+inquiry_enrolled.result[i].title+'"</option>');
		}
	}
	
	$("#inquiry").bind( "change", function(event, ui) {
		var inquiry=$("#inquiry").val();
		location.replace('/listAwardedBadgesPerUser.jsp?context='+inquiry+'&inquiryserver=<%=request.getParameter("inquiryserver")%>&userid=<%=request.getParameter("userid")%>');
		});
<%}%>

	<%
	JSONArray badges_array = new JSONArray(PersistanceLayerBadge.getbadgeByContext(request.getParameter("context"), ""));
	JSONArray badges = new JSONArray();	
	JSONObject phases_object = new JSONObject(RestClient.doGet("http://"+request.getParameter("inquiryserver")+"/services/api/rest/json/?method=inquiry.phases&api_key=27936b77bcb9bb67df2965c6518f37a77a7ab9f8&inquiry_id="+request.getParameter("context")));
	JSONArray phases_array = phases_object.getJSONObject("result").getJSONArray("details");
	if (!phases_object.getJSONObject("result").getString("phases").contains("null")){
		for (int i=0; i<badges_array.length(); i++){
			String image = badges_array.getJSONObject(i).getJSONObject("jsonBadge").getString("image");
			boolean similar = false;
			for (int j=0; j<phases_array.length();j++){				
				if (image.indexOf(phases_array.getJSONArray(j).getString(0)+"phase")>-1){
					similar= true;
				}
			}
			if (similar||!image.contains("phase")||image.contains("0phase"))
				badges.put(badges_array.getJSONObject(i));
		}	
	}else{
		badges = badges_array;
	}
	%>	
	var badges_array = <%=badges%>;
	var user_elgg = '<%=request.getParameter("userid").toLowerCase()%>';
	if (badges_array.length>0){
		var badges_awarded_array = <%=PersistanceLayerAwardedBadge.getAwardedBadgeByContext(request.getParameter("context"),"1354615295762", "")%>;
		var users = <%=RestClient.doGet("http://"+request.getParameter("inquiryserver")+"/services/api/rest/json/?method=inquiry.users&api_key=27936b77bcb9bb67df2965c6518f37a77a7ab9f8&inquiryId="+request.getParameter("context"))%>;
		var users_array = users.result;
		var phases = <%=phases_object%>;
		var phases_array = phases.result.as_array;
		var count = 0;
		var width = $(window).width();
		var badge_size = width/15;
		var padding = badge_size + (badge_size/10);
		var height = 0;
		var manualrows = 0;
		var manualbadges_total = 0; 
		var manual_images = 0;
        var manual_icon = 0;
        var manual_number= 0;
        var manual_images_row = 0;
        var manual_icon_row = 0;
        var manual_number_row= 0;
		var shift_manual = 0;
		var shift_automatic = 0;
		updateManualBadgesValues();
		updateHeightValue();	
	

		var manualbadges = 0;
	
		var svg = createSVG();
	
		var height_header =  parseInt(d3.select("#header").style("height").replace("px",""));
		var x = 0;
		var y = 0;
		var oppacity = 0.3; 
		var tooltip = createTooltip();
		
		var height_title = badge_size / 4;
		//var size_text = badge_size / 7;
		var size_text = 12;
		writeExplanations();
		
		generateBadgeImagesStudent();
		
		generateBadgeIcons();
		
		generateBadgeNumbers();
		
	}else{
		$("p").text("Please select an inquiry");
	}
</script>
</div>

</body>
</html>