/*******************************************************************************
 * Copyright (c) 2014 IBM Corporation and others.
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
function ycoordinates(imagename, type){
	var phases_coordinates = phases.result.phases;
	if (phases_coordinates==null)
		phases_coordinates = "123456";
    if (imagename.indexOf("0phase") > -1) {
        return padding * (0 + manualrows) + shift_automatic;
    }
    else if (imagename.indexOf("1phase") > -1) return padding*(1+manualrows)+shift_automatic;
    else if (imagename.indexOf("2phase") > -1) {
    	if (phases_coordinates.indexOf("2") > -1)
    		return padding*((phases_coordinates.indexOf("2")+1)+manualrows)+shift_automatic;
    	else
    		return padding*(2+manualrows)+shift_automatic;
    }
    else if (imagename.indexOf("3phase") > -1) {
    	if (phases_coordinates.indexOf("3") > -1)
    		return padding*((phases_coordinates.indexOf("3")+1)+manualrows)+shift_automatic;
    	else
    		return padding*(3+manualrows)+shift_automatic;
    }
    else if (imagename.indexOf("4phase") > -1){
    	if (phases_coordinates.indexOf("4") > -1)
    		return padding*((phases_coordinates.indexOf("4")+1)+manualrows)+shift_automatic;
    	else
    		return padding*(4+manualrows)+shift_automatic;
    }
    else if (imagename.indexOf("5phase") > -1){
    	if (phases_coordinates.indexOf("5") > -1)
    		return padding*((phases_coordinates.indexOf("5")+1)+manualrows)+shift_automatic;
    	else
    		return padding*(5+manualrows)+shift_automatic;
    }
    else if (imagename.indexOf("6phase") > -1){
    	if (phases_coordinates.indexOf("6") > -1)
    		return padding*((phases_coordinates.indexOf("6")+1)+manualrows)+shift_automatic;
    	else
    		return padding*(6+manualrows)+shift_automatic;
    }
    else{
        //type is 1 when we are drawing the badge image, 2 the person icon, 3 the number
        var row = 0;
        if (type==1){
            row = Math.floor( manual_images_row / 3);
            manual_images_row++;
        }else if (type==2){
            row = Math.floor( manual_icon_row / 3);
            manual_icon_row++;
        }else if (type==3){
            row = Math.floor( manual_number_row / 3);
            manual_number_row++;
        }
        return (padding * row) + shift_manual ;
    }
}

function xcoordinates(imagename, type){
    if (imagename.indexOf("phase1") > -1) return padding*1;
    else if (imagename.indexOf("phase2") > -1) return padding*2;
    else if (imagename.indexOf("phase3") > -1) return padding*3;
    else {
        var column = 0;
        //type is 1 when we are drawing the badge image, 2 the person icon, 3 the number
        if (type==1){
            column = (manual_images % 3) + 1;
            manual_images ++;
        }else if (type==2){
            column = (manual_icon % 3) + 1;
            manual_icon ++;
        }else if (type==3){
            column = (manual_number % 3) + 1;
            manual_number ++;
        }
        return padding*column
    }
}

function awardedBadges(badge){
    var badgesNumber = 0;
    badges_awarded_array.forEach(function(d) {
        if ((d.jsonBadge.badge.image == badge.jsonBadge.image)&&
            (d.jsonBadge.badge.name == badge.jsonBadge.name)&&
            (d.jsonBadge.badge.description == badge.jsonBadge.description)){
            badgesNumber++;
        }
    });
    return badgesNumber;

}

function awardedBadgesNames(badge){
    var usernames = "";
    badges_awarded_array.forEach(function(d) {
        if ((d.jsonBadge.badge.image == badge.jsonBadge.image)&&
            (d.jsonBadge.badge.name == badge.jsonBadge.name)&&
            (d.jsonBadge.badge.description == badge.jsonBadge.description)){
            users_array.forEach(function(data) {
                var user_id=data.oauthProvider.toLowerCase()+'_'+data.oauthId.toLowerCase();
                if ((d.username.toLowerCase() == user_id)){
                    usernames += data.name+'<br/> ';
                }
            });
        }
    });
    return usernames;

}

function updateManualBadgesValues(){
    for (var i = 0; i < badges_array.length; i++) {
        if ((badges_array[i].jsonBadge.image.indexOf("0phase") == -1)&&
            (badges_array[i].jsonBadge.image.indexOf("1phase") == -1)&&
            (badges_array[i].jsonBadge.image.indexOf("2phase") == -1)&&
            (badges_array[i].jsonBadge.image.indexOf("3phase") == -1)&&
            (badges_array[i].jsonBadge.image.indexOf("4phase") == -1)&&
            (badges_array[i].jsonBadge.image.indexOf("5phase") == -1)&&
            (badges_array[i].jsonBadge.image.indexOf("6phase") == -1)){
            manualbadges_total++;
        }
    }

    if (manualbadges_total>0){
        manualrows = Math.floor( (manualbadges_total-1) / 3)+1;
    }
}

function updateHeightValue(){
    if (badges_array.length<43){
        height = padding * (8+manualrows);
        height += badge_size/5;
    }
}


function trackData(username, verb, object, inquiry, widget_type, originalrequest){
	var context = '"context": { "course": "'+inquiry+'", "phase": "0", "widget_type": "'+widget_type+'" }';
	var data = '';
	if (originalrequest==''){
		data = '{"username":"'+username+'","verb":"'+verb+'","object":"'+object+'","starttime":"'+new Date().toISOString()+'",'+context+'}';
	}else{
		//data = '{"username":"'+username+'","verb":"'+verb+'","object":"'+object+'","starttime":"'+new Date().toISOString()+'",'+context+'}';
		console.log(originalrequest);
		data = '{"username":"'+username+'","verb":"'+verb+'","object":"'+object+'","starttime":"'+new Date().toISOString()+'",'+context+', "originalrequest":'+JSON.stringify(originalrequest)+'}';
	}
	
	$.ajax({
	    type: 'POST',
	    url: 'http://ariadne.cs.kuleuven.be/wespot-ws/eventc',
	    crossDomain: true,
	    data: data,
	    contentType: 'application/json; charset=utf-8',
	    dataType: 'json',
	    success: function(responseData, textStatus, jqXHR) {
	        var value = responseData.someKey;
	        console.log('POST succeed.'+data);
	        console.log('POST succeed.'+JSON.stringify(responseData));
	    },
	    error: function (responseData, textStatus, errorThrown) {
	    	//console.log('POST failed.'+JSON.stringify(responseData));
	    	console.log('POST failed.'+errorThrown);
	    }
	});
}

function removeBadges(){
	for (var i=0; i<badges_array.length; i++){
		for (var j=0; j<phases_array.length;j++){
			var image = badges_array[i].jsonBadge.image;
			if (image.indexOf(phases_array[j]+'phase')>-1){
				//console.log('Removing');
				delete badges_array[i];
				break;
			}
		}
		
	}	
}

