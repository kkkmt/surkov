package com.taxi.alfa.activitys;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.taxi.alfa.R;
import com.taxi.alfa.net.RequestThread;
import com.taxi.alfa.net.RequestThreadURL;
import com.taxi.alfa.net.ResponseCallBack;
import com.taxi.alfa.tools.SaveManager;

public class LoginActivity extends Activity implements ResponseCallBack, OnClickListener{
	
	private ProgressBar progress;
	private View layaut;
	private Button login;
	private EditText loginField;
	private EditText passwordField;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		SaveManager.init(getPreferences(MODE_PRIVATE));
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        if(!SaveManager.getToken().equals("") && getIntent().getBooleanExtra("exit", true)){
        	String s = RequestThread.URL+"check.php?token="+SaveManager.getToken();
        	Log.d("Tag", s);
			RequestThreadURL threadUrl = new RequestThreadURL(this, s, ResponseCallBack.CHECK_TOKEN);
			threadUrl.execute();
		}else{
			progress.setVisibility(View.INVISIBLE);
			layaut.setVisibility(View.VISIBLE);
		}
			
	}

	private void initView(){
		progress = (ProgressBar) findViewById(R.id.progressBarCheckLogin);
		loginField = (EditText) findViewById(R.id.editTextLogin);
		passwordField = (EditText) findViewById(R.id.editTextPassword);
		layaut = findViewById(R.id.layautLoginProgress);
		login = (Button) findViewById(R.id.buttonLogin);
		login.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if(!loginField.getText().equals("")&&!passwordField.getText().equals("")){
			String s = RequestThread.URL+"token.php?login="+loginField.getText().toString()+"&password="+passwordField.getText().toString();
			RequestThreadURL threadUrl = new RequestThreadURL(this, s, ResponseCallBack.GET_TOKEN);
			Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
			Log.d("Test", s+"");
			layaut.setVisibility(View.INVISIBLE);
			progress.setVisibility(View.VISIBLE);
			threadUrl.execute();
		}
	}

	
	private void succes(int i) {
		Toast.makeText(this, "Succes: "+i, Toast.LENGTH_SHORT).show();
		finish();
		startActivity(new Intent(this, MainActivity.class));
		
	}

	public void error(int code){
		Toast.makeText(this, "Error "+code, Toast.LENGTH_SHORT).show();
		progress.setVisibility(View.INVISIBLE);
		layaut.setVisibility(View.VISIBLE);
	}
	
	public void errorNetwork(){
		Toast.makeText(this, "Network error", Toast.LENGTH_SHORT).show();
		progress.setVisibility(View.INVISIBLE);
		layaut.setVisibility(View.VISIBLE);
	}

	@Override
	public void callBackVariable(JSONObject jobj, int req) {
		try {
			if(req == ResponseCallBack.CHECK_TOKEN){
					succes(0);
			}else
				if(req == ResponseCallBack.GET_TOKEN){
					SaveManager.setToken(jobj.getString("value"));
					succes(1);
				}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void callBackError(JSONObject jobj, int req) {
		try {
			error(jobj.getInt("code"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void callBack(JSONObject jobj, int req) {
		toast("JSONObject: OtherObject" );
		
	}

	@Override
	public void exception(Exception e, int req) {
		toast("Exception: "+e.getMessage());
		errorNetwork();
	}
	
	private void toast(String s){
		Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
	}
}
