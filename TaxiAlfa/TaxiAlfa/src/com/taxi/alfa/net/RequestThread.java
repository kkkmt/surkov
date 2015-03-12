package com.taxi.alfa.net;

import android.os.AsyncTask;
import android.util.Log;

public class RequestThread extends AsyncTask<Void, Object, Void>{

//	public static final String URL = "http://10.0.3.2/denwer/test/";
	
	public static final String URL = "http://alfa.wc.lt/";
	
	public static final int CORRECT = 0;
	public static final int UNCORRECT = 1;
	
	protected ResponseCallBack callBack;
	public RequestThread(ResponseCallBack callBack) {
		this.callBack = callBack;
	}
	
	@Override
	protected void onPreExecute() {
		Log.d("AsyncTask", "Started");
		super.onPreExecute();
	}
	@Override
	protected Void doInBackground(Void... params) {
		return null;
	}

	@Override
	protected void onProgressUpdate(Object... values) {
		super.onProgressUpdate(values);
	}
	
	@Override
	protected void onPostExecute(Void result) {
		Log.d("AsyncTask", "Close");
		super.onPostExecute(result);
	}
	

	public void setCallBack(ResponseCallBack callBack) {
		this.callBack = callBack;
	}
}
