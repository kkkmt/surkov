package com.taxi.alfa.net;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.taxi.alfa.objects.HttpClientFactory;

public class RequestThreadURL extends RequestThread{
	
	
	protected String url;
	protected int code;
	public RequestThreadURL(ResponseCallBack callBack, String url, int code) {
		super(callBack);
		this.url = url;
		this.code = code;
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		try {
			publishProgress(new Integer(RequestThread.CORRECT),commonRequest(url));
		} catch (IOException e) {
			e.printStackTrace();
			publishProgress(new Integer(RequestThread.UNCORRECT),e);
		} catch (JSONException e) {
			e.printStackTrace();
			publishProgress(new Integer(RequestThread.UNCORRECT),e);
		}
		return super.doInBackground(params);
	}
	
	protected JSONObject commonRequest(String url) throws IOException, JSONException {
        HttpClient httpClient = HttpClientFactory.getThreadSafeClient();
        HttpGet httpGet = new HttpGet(url);
        HttpResponse execute = httpClient.execute(httpGet);
        return new JSONObject(EntityUtils.toString(execute.getEntity()));
		
    }
	
	@Override
	protected void onProgressUpdate(Object... values) {
		super.onProgressUpdate(values);
		try {
			if(((Integer)values[0]).intValue() == RequestThread.CORRECT){
			JSONObject json = (JSONObject)values[1];
			
				if(json.getString("type").equals(ResponseCallBack.VARIABLE))
					callBack.callBackVariable(json, code);
				else
					if(json.getString("type").equals(ResponseCallBack.ERROR))
						callBack.callBackError(json, code);
					else
						callBack.callBack(json, code);
				Log.d("RequestThread", json.toString()+"");
		}else
			if(((Integer)values[0]).intValue() == RequestThread.UNCORRECT){
				callBack.exception((Exception)values[1], code);
			}
		} catch (JSONException e) {
				callBack.exception((Exception)values[1], code);
				e.printStackTrace();
		}		
	}
}
