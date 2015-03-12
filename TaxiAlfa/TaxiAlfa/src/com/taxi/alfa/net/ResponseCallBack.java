package com.taxi.alfa.net;

import org.json.JSONObject;

public interface ResponseCallBack {
	
	public static final int CHECK_TOKEN = 0;
	public static final int GET_TOKEN = 1;
	public static final int DEMON = 2;
	
	public static final String VARIABLE = "variable";
	public static final String ERROR = "error";
	
	public static final String ORDERS = "orders";
	public static final String ORDER = "order";
	
	void callBackVariable(JSONObject jobj, int req);
	void callBackError(JSONObject jobj, int req);
	void callBack(JSONObject jobj, int req);
	void exception(Exception e, int req);
}
