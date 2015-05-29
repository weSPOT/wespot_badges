<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.be.kuleuven.hci.openbadges.persistanceLayer.*"%>
<%@ page import="com.google.appengine.labs.repackaged.org.json.JSONObject"%>
<%@ page import="com.google.appengine.labs.repackaged.org.json.JSONArray"%>
<%@ page import="org.be.kuleuven.hci.openbadges.utils.RestClient"%>
<html>
<head>


<link rel="stylesheet" href="css/jquery.mobile-1.4.2.css">
		<script src="http://code.jquery.com/jquery-2.1.0.min.js"></script>
		<script src="http://code.jquery.com/mobile/1.4.2/jquery.mobile-1.4.2.min.js"></script>
		<script src="js/utils.js"></script>
		<script>
			$.mobile.selectmenu.prototype.options.hidePlaceholderMenuItems = false;		
			var user = "<%=request.getParameter("userid")%>";
			
			trackData(user, "access", "teacher_copy_interface", "<%=request.getParameter("context")%>", "badges", "");//request.getParameter("context")
			function validateForm() {//TODO
				var badge_selected=false;
				var inquiry_selected=false;
				var badges = document.getElementsByName("checkbox-badges");
			    for(var iterator = 0; iterator < badges.length; iterator++) {
			    	//alert(students[iterator].checked);
			    	if (badges[iterator].checked==true){
			    		badge_selected=true;
			    		break;
			    	}
			    }
			    var inquiries = document.getElementsByName("checkbox-inquiries");
			    for(var iterator = 0; iterator < inquiries.length; iterator++) {
			    	//alert(students[iterator].checked);
			    	if (inquiries[iterator].checked==true){
			    		inquiry_selected=true;
			    		break;
			    	}
			    }
			    alert();
			    
			    if (inquiry_selected == false || badge_selected == false) {			        
			        if (inquiry_selected == false){		        	
			        	$("#messagebadge").html("<p>Please, select one or more badges...</p>");
			        }else
			        	$("#messagebadge").html("");
			        if (badge_selected == false){		        	
			        	$("#messageinquiry").html("<p>Please, select one or more inquiries...</p>");
			        }else
			        	$("#messageinquiry").html("");
			        alert();
			        return false;
			    }
			    return true;
			}
		</script>
		
<style>
		
div.error { 
	float: center; 
	color: red; 
	padding-top: .5em; 
	vertical-align: top; 
	font-weight:bold
}
</style>
</head>
<body>
<div data-role="header"> 
			<a href="javascript:location.replace('/menu.jsp?context=<%=request.getParameter("context")%>&inquiryserver=<%=request.getParameter("inquiryserver")%>&userid=<%=request.getParameter("userid")%>')" data-icon="back" class="ui-btn-left" title="Go back">Back</a>
			<h1>Copy badges to other inquiries</h1>
</div>
<form name ="myForm" action="/copybadges" onsubmit="return validateForm()" method="get" enctype="multipart/form-data" data-ajax="false">
<div class="ui-field-contain">
<input type="hidden" name="context" value="<%=request.getParameter("context")%>">
            <input type="hidden" id="inquiryserver" name="inquiryserver" value="<%=request.getParameter("inquiryserver")%>">
            <input type="hidden" id="userid" name="userid" value="<%=request.getParameter("userid")%>">
            
<%
String username = request.getParameter("userid");
String split[] = username.split("_");
String authid= split[1];
String authprovider=split[0];
if (split.length>2){
	for (int i=2; i<split.length; i++){
		authid= authid+"_" + split[i];
	}
}

JSONObject result = new JSONObject(RestClient.doGet("http://"+request.getParameter("inquiryserver")+"/services/api/rest/json/?method=user.inquiriesAdmin&api_key={key}&oauthId="+authid+"&oauthProvider="+authprovider));

JSONArray inquiry_list = result.getJSONArray("result");
JSONArray badges_array = new JSONArray(PersistanceLayerBadge.getbadgeByContext(request.getParameter("context"), ""));
%>

<div data-role="fieldcontain">
    <fieldset data-role="controlgroup">
    	<legend>Choose one or more badges:</legend>
<%
    
    for (int i=0;i<badges_array.length();i++){
    	JSONObject badge = badges_array.getJSONObject(i);
    	if (!badge.getJSONObject("jsonBadge").has("nameApp") || !badge.getJSONObject("jsonBadge").getString("nameApp").contains("wespot_automatic_badges")){
%>
		<label><input id="<%=badge.getString("id")%>" type="checkbox" name="checkbox-badges" value="<%=badge.getString("id")%>"/> <%=badge.getJSONObject("jsonBadge").getString("name")%> </label>

<%
    	}
    }
%>
	</fieldset>
</div>
<div id="messagebadge" class="error" style="text-align: center;"></div>
<div data-role="fieldcontain">
    <fieldset data-role="controlgroup">
    	<legend>Choose one or more inquiries:</legend>
<%
	
    
    for (int i=0;i<inquiry_list.length();i++){
    	JSONObject user = inquiry_list.getJSONObject(i);
%>
		<label><input id="<%=user.getString("inquiryId").toLowerCase()%>" type="checkbox" name="checkbox-inquiries" value="<%=user.getString("inquiryId").toLowerCase()%>"/> <%=user.getString("title")%> </label>

<%
    }
%>
	</fieldset>
</div>
<div id="messageinquiry" class="error" style="text-align: center;"></div>
<br/><br/>
	<div style="text-align: right;">
		<input type="submit" value="Copy badges"  data-icon="check" data-iconpos="right" data-inline="true">
	</div>
</div>

</form>

</body>
</html>