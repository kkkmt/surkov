package com.taxi.alfa.net;

import java.io.IOException;

import org.json.JSONException;

import android.util.Log;

public class DemonThread extends RequestThreadURL{

	private boolean started = false;
	int i = 0;
	
	public DemonThread(ResponseCallBack callBack, String url, int code) {
		super(callBack,url, code);
		
	}

	@Override
	protected void onPreExecute() {
		if(callBack == null)
			throw new NullPointerException("Callback is null");
		started = true;
		Log.d("AsyncTask", "Started");
		super.onPreExecute();
	}
	@Override
	protected Void doInBackground(Void... params) {
		while(started){
			try {
				publishProgress(new Integer(RequestThread.CORRECT),commonRequest(url));
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				publishProgress(new Integer(RequestThread.UNCORRECT),e);
				e.printStackTrace();
			} catch (IOException e) {
				publishProgress(new Integer(RequestThread.UNCORRECT),e);
				e.printStackTrace();
			} catch (JSONException e) {
				publishProgress(new Integer(RequestThread.UNCORRECT),e);
				e.printStackTrace();
			}
			
		}
		return null;
	}

	@Override
	protected void onProgressUpdate(Object... values) {
		super.onProgressUpdate(values);
		Log.d("Demon", "Iteration");
	}
	
	@Override
	protected void onPostExecute(Void result) {
		started = false;
		super.onPostExecute(result);
	}
	
	public boolean isStarted(){
		return started;
	}

	public void setCallBack(ResponseCallBack callBack) {
		this.callBack = callBack;
	}
	public void stop() {
		started = false;
		Log.d("AsyncTask", "Stoped");
	}
}
