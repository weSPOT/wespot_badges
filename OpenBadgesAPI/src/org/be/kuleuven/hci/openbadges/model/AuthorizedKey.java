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
package org.be.kuleuven.hci.openbadges.model;

import java.util.Date;

import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class AuthorizedKey {

	@Id Long id;
	@Index String name;
	@Index String keyApp;
	
	public AuthorizedKey(){
		
	}
	
	public void setName(String name){
		this.name=name;
	}
	
	public void setKeyApp(String keyApp){
		this.keyApp=keyApp;
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getKeyApp(){
		return this.keyApp;
	}

	public JSONObject getJSON(){
		return this.getJSON();
	}
}
