package com.taxi.alfa.tools;

import org.json.JSONException;
import org.json.JSONObject;

public class Order {
	public int id;
	
	public double from_lat;
	public double from_lon;
	public double to_lat;
	public double to_lon;
	
	public String from_name;
	public String to_name;
	public String time_start;
	
	public Order(String sjson) {
		try {
			JSONObject json = new JSONObject(sjson);
			id = json.getInt("id");
			
			from_lat = json.getDouble("from_lat");
			from_lon = json.getDouble("from_lon");
			to_lat = json.getDouble("to_lat");
			to_lon= json.getDouble("to_lon");
			
			from_name = json.getString("from_name");
			to_name = json.getString("to_name");
			
			time_start = json.getString("time_start");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
