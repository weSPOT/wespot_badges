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
			
			trackData(user, "access", "teacher_award_interface", "<%=request.getParameter("context")%>", "badges", "");//request.getParameter("context")
			function validateForm() {
				var student_selected = false;
			    var x = document.forms["myForm"]["badge"].value;
			    var z = document.forms["myForm"]["evidence"].value;
			    var students = document.getElementsByName("checkbox-students");
			    for(var iterator = 0; iterator < students.length; iterator++) {
			    	//alert(students[iterator].checked);
			    	if (students[iterator].checked==true){
			    		student_selected=true;
			    		break;
			    	}
			    }
			    //alert();
			    
			    if (x == null || x == "" || x == "Choose one badge..." || student_selected == false || z == null || z == "") {
			        if (x==null || x == "" || x == "Choose one badge..."){			        		
			        	$("#messagebadge").html("<p>Please, choose one badge</p>");
			        }else
			        	$("#messagebadge").html("");
			        if (student_selected == false){		        	
			        	$("#messagestudent").html("<p>Please, select one student at least...</p>");
			        }else
			        	$("#messagestudent").html("");
			        if (z==null || z == ""){
			        	$("#messageevidence").html("<p>Please, add an explanation...</p>");
			        }else
			        	$("#messageevidence").html("");
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
			<h1>Award a badge</h1>
</div>
<form name ="myForm" action="/awardbadges" onsubmit="return validateForm()" method="get" enctype="multipart/form-data" data-ajax="false">
<div class="ui-field-contain">
<input type="hidden" name="context" value="<%=request.getParameter("context")%>">
            <input type="hidden" id="inquiryserver" name="inquiryserver" value="<%=request.getParameter("inquiryserver")%>">
            <input type="hidden" id="userid" name="userid" value="<%=request.getParameter("userid")%>">
            
<%

JSONObject result = new JSONObject(RestClient.doGet("http://"+request.getParameter("inquiryserver")+"/services/api/rest/json/?method=inquiry.users&api_key={key}&inquiryId="+request.getParameter("context")));
JSONArray users_list = result.getJSONArray("result");
JSONArray badges_array = new JSONArray(PersistanceLayerBadge.getbadgeByContext(request.getParameter("context"), ""));
%>

<select id="badge" name="badge"> <option>Choose one badge...</option>
<% 
    for (int i=0;i<badges_array.length();i++){
    	JSONObject badge = badges_array.getJSONObject(i);
    	if (!badge.getJSONObject("jsonBadge").has("nameApp") || !badge.getJSONObject("jsonBadge").getString("nameApp").contains("wespot_automatic_badges")){
%>
 			<option value="<%=badge.getString("id")%>"><%=badge.getJSONObject("jsonBadge").getString("name")%></option>
<%
    	}
    }
%>
</select>
<div id="messagebadge" class="error" style="text-align: center;"></div>
<div data-role="fieldcontain">
    <fieldset data-role="controlgroup">
    	<legend>Choose one or more students:</legend>
<%
	
    
    for (int i=0;i<users_list.length();i++){
    	JSONObject user = users_list.getJSONObject(i);
%>
		<label><input id="<%=user.getString("oauthProvider").toLowerCase()%>_<%=user.getString("oauthId").toLowerCase()%>" type="checkbox" name="checkbox-students" value="<%=user.getString("oauthProvider").toLowerCase()%>_<%=user.getString("oauthId").toLowerCase()%>"/> <%=user.getString("name")%> </label>

<%
    }
%>
	</fieldset>
</div>
<div id="messagestudent" class="error" style="text-align: center;"></div>
<br/>
<label for="evidence">Justify why you award the badge:</label><input type="text" id="evidence" name="evidence">
<div id="messageevidence" class="error" style="text-align: center;"></div>
<br/><br/>
	<div style="text-align: right;">
		<input type="submit" value="Award the badge"  data-icon="check" data-iconpos="right" data-inline="true">
	</div>
</div>

</form>
<script>
	var badges = <%=badges_array%>;
	//$('#google_107159181208333521690').prop('disabled', true);
	//$('#google_107159181208333521690').parent().hide();
	var awarded_badges=<%=PersistanceLayerAwardedBadge.getAwardedBadgeByContext(request.getParameter("context"), "1354615295762", "")%>
	var users = <%=users_list%>;
	$( "#badge" )
		.change(function () {			
			  var $this = $(this),
		      val = $this.val();
			  for(var i in users){
				 $('#'+(users[i].oauthProvider).toLowerCase()+'_'+users[i].oauthId).show();
				 $('#'+(users[i].oauthProvider).toLowerCase()+'_'+users[i].oauthId).parent().show();
			  }
			  for(var i in badges){
				  if (val == badges[i].id){
					  var badge_obj = badges[i];
					  for(var j in awarded_badges){
						  if (badge_obj.jsonBadge.name == awarded_badges[j].jsonBadge.badge.name && badge_obj.jsonBadge.description == awarded_badges[j].jsonBadge.badge.description && badge_obj.jsonBadge.criteria == awarded_badges[j].jsonBadge.badge.criteria){
							  console.log("value of the badge: "+val);
							  $("#"+awarded_badges[j].username.toLowerCase()).hide();
							  $("#"+awarded_badges[j].username.toLowerCase()).parent().hide();
						  }
					  }
				  }
			  }				
		  
		});
</script>
</body>
</html>