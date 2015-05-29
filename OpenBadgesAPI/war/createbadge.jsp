<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
    <head>
        <title>Upload Test</title>
        <link rel="stylesheet" href="css/jquery.mobile-1.4.2.css">
		<script src="http://code.jquery.com/jquery-2.1.0.min.js"></script>
		<script src="http://code.jquery.com/mobile/1.4.2/jquery.mobile-1.4.2.min.js"></script>
		<script src="js/utils.js"></script>
		<script>
		var user = "<%=request.getParameter("userid")%>";
		trackData(user, "access", "teacher_create_interface", "<%=request.getParameter("context")%>", "badges", "");//request.getParameter("context")
		
		function validateForm() {
			    var x = document.forms["myForm"]["name"].value;
			    var y = document.forms["myForm"]["description"].value;
			    var z = document.forms["myForm"]["criteria"].value;
			    if (x == null || x == "" || y == null || y == "" || z == null || z == "") {
			        if (x==null || x == "" ){			        		
			        	$("#messagename").html("<p>Please, add a name</p>");
			        }else
			        	$("#messagename").html("");
			        if (y==null || y == ""){			        	
			        	$("#messagedesc").html("<p>Please, add a description...</p>");
			        }else
			        	$("#messagedesc").html("");
			        if (z==null || z == ""){
			        	$("#messagecri").html("<p>Please, add a criteria...</p>");
			        }else
			        	$("#messagecri").html("");
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
		<div data-role="header" > 
			<a href="javascript:location.replace('/menu.jsp?context=<%=request.getParameter("context")%>&inquiryserver=<%=request.getParameter("inquiryserver")%>&userid=<%=request.getParameter("userid")%>')" data-icon="back" class="ui-btn-left" title="Go back">Back</a>
			<h1>Create a new badge</h1>
		</div>
        <form name ="myForm" action="/uploadwespot" onsubmit="return validateForm()" method="get" enctype="multipart/form-data" data-ajax="false">
          <div class="ui-field-contain">
            <input type="hidden" id="context" name="context" value="<%=request.getParameter("context")%>">
            <input type="hidden" id="file" name="file" value="http://openbadges-wespot.appspot.com/badges/7generic.png">
            <input type="hidden" id="inquiryserver" name="inquiryserver" value="<%=request.getParameter("inquiryserver")%>">
            <input type="hidden" id="userid" name="userid" value="<%=request.getParameter("userid")%>">
            <label for="name">Name:</label><input type="text" id="name" name="name"><br/>
            <div id="messagename" class="error" style="text-align: center;"></div>
            <label for="description">Description:</label><input type="text" id="description" name="description"><br/>
            <div id="messagedesc" class="error" style="text-align: center;"></div>            
            <label for="criteria">Criteria:</label><input type="text" id="criteria" name="criteria"><br/>            
            <div id="messagecri" class="error" style="text-align: center;"></div>
            <input type="submit" value="Submit" data-icon="check" data-iconpos="right" data-inline="true">
           </div>
        </form>
    </body>
</html>
