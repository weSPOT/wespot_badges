package org.be.kuleuven.hci.openbadges.model;

import java.util.Date;

import com.google.appengine.api.datastore.Text;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class Badge {

	@Id Long id;
	@Index String context;
	Text jsonBadge;
	@Index String nameApp;
	
	public Badge(){
		
	}
	
	public void setContext(String context){
		this.context=context;
	}
	
	public void setjsonBadge(Text jsonBadge){
		this.jsonBadge=jsonBadge;
	}
	
	public void setNameApp(String nameApp){
		this.nameApp=nameApp;
	}
	
	public String getContext(){
		return this.context;
	}
	
	public Text getjsonBadge(){
		return this.jsonBadge;
	}
	
	public String getNameApp(){
		return this.nameApp;
	}
	
	public JSONObject getJSON() throws JSONException{
		JSONObject result = new JSONObject();
		result.put("id", this.id);
		result.put("context",this.context);
		result.put("jsonBadge",new JSONObject(this.jsonBadge.getValue()));
		result.put("nameApp",this.nameApp);		
		return result;
	}

	

}
