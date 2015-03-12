package com.taxi.alfa.tools;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SaveManager {
	
	private static SharedPreferences sPref;
	
	public static final String LOGIN = "login";
	public static final String PASSWORD = "password";
	public static final String TOKEN = "token";
	
	public static void init(SharedPreferences pref){
		sPref = pref;
	}
	
	public static String getToken(){
		if(sPref == null)
			throw new NullPointerException("SharedPreferences is null");
		return sPref.getString(TOKEN, "");
	}
	
	public static String getLogin(){
		if(sPref == null)
			throw new NullPointerException("SharedPreferences is null");
		return sPref.getString(LOGIN, "");
	}
	
	public static String getPassword(){
		if(sPref == null)
			throw new NullPointerException("SharedPreferences is null");
		return sPref.getString(PASSWORD, "");
	}
	
	public static void setToken(String token){
		if(sPref == null)
			throw new NullPointerException("SharedPreferences is null");
		Editor ed = sPref.edit();
	    ed.putString(TOKEN, token);
	    ed.commit();
	}
	
	public static void setLogin(String login){
		if(sPref == null)
			throw new NullPointerException("SharedPreferences is null");
		Editor ed = sPref.edit();
	    ed.putString(LOGIN, login);
	    ed.commit();
	}
	
	public static void setPassword(String password){
		if(sPref == null)
			throw new NullPointerException("SharedPreferences is null");
		Editor ed = sPref.edit();
	    ed.putString(PASSWORD, password);
	    ed.commit();
	}
}
