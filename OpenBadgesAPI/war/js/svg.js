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
function createSVG(){
    return d3.select("body")
        .append("svg")
        .attr("width", width)
        .attr("height", height);
}

function createTooltip(){
    return d3.select("body")
        .append("div")
        .style("position", "absolute")
        .style("z-index", "10")
        .style("visibility", "hidden")
        .html("A simple");

}

function writeExplanations(){
    if (manualbadges_total>0){
        var manual_badges_message = "The following badges are awarded manually by the teacher (numbers = students who got the badge)";

        svg.append("text")
            .attr("x", function (d){
                return padding;
            })
            .attr("y", function (d){
                return (badge_size/5);
            })
            .attr('font-family', 'sans-serif')
            .attr('font-style', 'normal')
            .attr('fill', 'black')
            .attr('font-size', function(d) { return size_text;} )
            .attr("opacity", 0.7)
            .text(function(d) {
                shift_manual+= badge_size/3;
                shift_automatic+= shift_manual;
                return manual_badges_message;
            });
    }

    var automatic_badges_message = "The following badges are awarded automatically (numbers = students who got the badge)";

    svg.append("text")
        .attr("x", function (d){
            return padding;
        })
        .attr("y", function (d){
            if (manualbadges_total>0){
                return (padding*manualrows*1.1)+(shift_automatic*0.5);
            }else{
                return (badge_size/5);
            }
        })
        .attr('font-family', 'sans-serif')
        .attr('font-style', 'normal')
        .attr('fill', 'black')
        .attr('font-size', function(d) { return size_text;} )
        .attr("opacity", 0.7)
        .text(function(d) {
            shift_automatic+= badge_size/3;
            return automatic_badges_message;
        });
}

function calculateopacity(badge){
	var opac = 0.3
	badges_awarded_array.forEach(function(d) {
		if ((d.jsonBadge.badge.image == badge.jsonBadge.image)&&
				(d.jsonBadge.badge.name == badge.jsonBadge.name)&&
				(d.jsonBadge.badge.description == badge.jsonBadge.description)&&
				(d.username.toLowerCase() == user_elgg)){
				opac = 1;		
		}
	});	
	return opac;
}


function generateBadgeImagesStudent(){
    svg.selectAll("img")
        .data(badges_array.sort(function(a,b) {
            return d3.ascending(a.jsonBadge.image, b.jsonBadge.image);
        }))
        .enter()
        .append("image")
        .attr("xlink:href", function(d) {
            return d.jsonBadge.image;
        })
        .attr("y", function (d){
            return ycoordinates(d.jsonBadge.image, 1);
        })
        .attr("x", function (d){
            return xcoordinates(d.jsonBadge.image, 1);
        })
        .attr("width", badge_size)
        .attr("height", badge_size)
        .attr("opacity", function (d) {
				return calculateopacity(d);
		})
        .on("mouseover", function (d) {
            d3.select(this)
                .attr("width", badge_size*1.05)
                .attr("height", badge_size*1.05)
                .attr("opacity", "1");

            tooltip.html('<div><table id="badgeinfo" class="table3"><tbody><tr><td id="badgecolumn" scope="row">Name</td><td>'+d.jsonBadge.name+'</td></tr><tr><td scope="row">Description</td><td>'+d.jsonBadge.description+'</td></tr><tr><td scope="row">Criteria</td><td>'+d.jsonBadge.criteria+'</td></tr></tbody></table>');
            tooltip.style("visibility", "visible");
            var x_badge = parseInt(d3.select(this).attr("x"))+parseInt(badge_size);
            //var y_badge = parseInt(d3.select(this).attr("y"))+(parseInt(badge_size)/2);
            var y_badge = parseInt(d3.select(this).attr("y"))+height_header;
            //tooltip.style("top", (y_badge)+"px").style("left",(x_badge)+"px");
            var tooltip_position = ((badge_size+padding)*2 + (padding/1.5));
            tooltip.style("top", (shift_automatic)+"px").style("left",tooltip_position+"px");
            d3.select("body").select("#badgeinfo").style("width", (width-tooltip_position)+"px").style('font-size', function(d) { return size_text;} );
            d3.select("body").select("#badgecolumn").style("width", (size_text*5)+"px");

        })
        .on("mouseout", function (d) {
            d3.select(this)
                .attr("opacity", function (d) {
	            			return calculateopacity(d);
	            		})
                .attr("width", badge_size)
                .attr("height", badge_size);
            tooltip.style("visibility", "hidden");});
}

function generateBadgeImages(){
    svg.selectAll("img")
        .data(badges_array.sort(function(a,b) {
            return d3.ascending(a.jsonBadge.image, b.jsonBadge.image);
        }))
        .enter()
        .append("image")
        .attr("xlink:href", function(d) {
            return d.jsonBadge.image;
        })
        .attr("y", function (d){
            return ycoordinates(d.jsonBadge.image, 1);
        })
        .attr("x", function (d){
            return xcoordinates(d.jsonBadge.image, 1);
        })
        .attr("width", badge_size)
        .attr("height", badge_size)
        .attr("opacity", oppacity)
        .on("mouseover", function (d) {
            d3.select(this)
                .attr("width", badge_size*1.05)
                .attr("height", badge_size*1.05)
                .attr("opacity", "1");

            tooltip.html('<table id="badgeinfo" class="table3"><tbody><tr><td id="badgecolumn" scope="row">Name</td><td>'+d.jsonBadge.name+'</td></tr><tr><td scope="row">Description</td><td>'+d.jsonBadge.description+'</td></tr><tr><td scope="row">Criteria</td><td>'+d.jsonBadge.criteria+'</td></tr><tr><td scope="row">Awarded <br/>Students</td><td id="studentnames">'+awardedBadgesNames(d)+'</td></tr></tbody></table>');
            tooltip.style("visibility", "visible");
            var x_badge = parseInt(d3.select(this).attr("x"))+parseInt(badge_size);
            //var y_badge = parseInt(d3.select(this).attr("y"))+(parseInt(badge_size)/2);
            var y_badge = parseInt(d3.select(this).attr("y"))+height_header;
            //tooltip.style("top", (y_badge)+"px").style("left",(x_badge)+"px");
            var tooltip_position = ((badge_size+padding)*2 + (padding/1.5));
            tooltip.style("top", (shift_automatic)+"px").style("left",tooltip_position+"px");
            d3.select("body").select("#badgeinfo").style("width", (width-tooltip_position)+"px").style('font-size', function(d) { return size_text;} );
            d3.select("body").select("#badgecolumn").style("width", (size_text*5)+"px");
            d3.select("body").select("#studentnames").style("font-style", "italic");
        	trackData( user , "select", "teacher_interface", "0000000", "badges", d.jsonBadge);//request.getParameter("context")

        })
        .on("mouseout", function (d) {
            d3.select(this)
                .attr("opacity", oppacity)
                .attr("width", badge_size)
                .attr("height", badge_size);
            //tooltip.style("visibility", "hidden");
            });
}

function generateBadgeIcons(){
    svg.selectAll("img")
        .data(badges_array.sort(function(a,b) {
            return d3.ascending(a.jsonBadge.image, b.jsonBadge.image);
        }))
        .enter()
        .append("image")
        .attr("xlink:href", function(d) {
            return 'https://cdn1.iconfinder.com/data/icons/dot/256/man_person_mens_room.png';
        })
        .attr("y", function (d){
            return ycoordinates(d.jsonBadge.image, 2);
        })
        .attr("x", function (d){
            return xcoordinates(d.jsonBadge.image, 2)+badge_size/1.2;
        })
        .attr("width", badge_size/5)
        .attr("height", badge_size/5)
        .attr("opacity", 0.5);
}

function generateBadgeNumbers(){
    svg.selectAll()
        .data(badges_array.sort(function(a,b) {
            return d3.ascending(a.jsonBadge.image, b.jsonBadge.image);
        }))
        .enter()
        .append("text")
        .attr("y", function (d){
           return ycoordinates(d.jsonBadge.image, 3)+badge_size/6;
        })
        .attr("x", function (d){
            return xcoordinates(d.jsonBadge.image, 3)+badge_size;
        })
        .attr('font-family', 'FontAwesome')
        .attr('font-style', 'bold')
        .attr('fill', 'black')
        .attr('font-size', function(d) { return badge_size/5;} )
        .attr("opacity", 0.5)
        .text(function(d) {return awardedBadges(d);});
}