package com.taxi.alfa.activitys;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.taxi.alfa.R;
import com.taxi.alfa.fragments.MapFragment;
import com.taxi.alfa.fragments.OrdersFragment;
import com.taxi.alfa.net.DemonThread;
import com.taxi.alfa.net.RequestThread;
import com.taxi.alfa.net.ResponseCallBack;
import com.taxi.alfa.objects.MyAdapter;
import com.taxi.alfa.tools.Order;
import com.taxi.alfa.tools.SaveManager;

public class MainActivity extends Activity implements TabListener, ResponseCallBack{

	private OrdersFragment orderFragment;
	private MapFragment mapFragment;
	
	private static final String MAP = "map";
	private static final String ORDERS = "orders";
	
	private int selectFragment = 0;
	private int nowIdConteiner = R.id.container;
	public static DemonThread demonUpdate;
	private TextView debug;
	
	private ArrayList<Order> oldOrders = new ArrayList<Order>();
	private ArrayList<Order> orders = new ArrayList<Order>();
	
	private MyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        debug = (TextView) findViewById(R.id.textViewDebug);
        adapter = new MyAdapter(orders, getLayoutInflater());
        initFragments();
        initTabs2();
        startDemon();
    }
    
    @Override
    protected void onDestroy() {
    	if(demonUpdate != null)
    		demonUpdate.stop();
    	super.onDestroy();
    }
    
   @Override
	public void onBackPressed() {
	   	if(demonUpdate != null)
	   		demonUpdate.stop();
		super.onBackPressed();
	}
   
    private void startDemon(){
    	newDemon();
    }
    
    @Override
    public Object onRetainNonConfigurationInstance() {
    	// TODO Auto-generated method stub
    	return demonUpdate;
    }
    
    private void newDemon(){
    	demonUpdate = (DemonThread) getLastNonConfigurationInstance();
        if (demonUpdate == null) {
        	String s = RequestThread.URL+"orders.php?token="+SaveManager.getToken();
        	demonUpdate = new DemonThread(this, s, ResponseCallBack.DEMON);
        	demonUpdate.execute();
        }
        demonUpdate.setCallBack(this);
    }
   
    private void initFragments(){
		orderFragment = new OrdersFragment(adapter);
		mapFragment = new MapFragment();
    }
    
    private void initTabs2() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		ActionBar.Tab tab1 = actionBar.newTab();
		tab1.setText("Карта");
		tab1.setTabListener(this);
		
		ActionBar.Tab tab2 = actionBar.newTab();
		tab2.setText("Заказы");
		tab2.setTabListener(this);
		
		actionBar.addTab(tab1);
		actionBar.addTab(tab2);
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		FragmentTransaction fragmentTransaction = getFragmentManager()
				.beginTransaction();
		selectFragment = tab.getPosition();
		if(selectFragment== 0){
			fragmentTransaction.replace(nowIdConteiner, mapFragment);
			nowIdConteiner = mapFragment.getId();
		}
        else{
        	fragmentTransaction.replace(nowIdConteiner, orderFragment);
        	nowIdConteiner = orderFragment.getId();
        }
		fragmentTransaction.commit();
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		
	}

	@Override
	public void callBackVariable(JSONObject jobj, int req) {
		try {
			if(jobj.getString("name").equals(ResponseCallBack.ORDER))
				setOrder(jobj);
			else
				if(jobj.getString("name").equals(ResponseCallBack.ORDERS))
					setOrders(jobj);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void callBackError(JSONObject jobj, int req) {
		toast("JSONObject: ErrorObject");
		try {
			if(jobj.getInt("code")==2){
				toast("Error 2 Token Incorect");
				finish();
				Intent in = new Intent(this, LoginActivity.class);
				in.putExtra("exit", false);
				startActivity(in);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void callBack(JSONObject jobj, int req) {
		try {
			if(jobj.getString("type").equals(ResponseCallBack.ORDERS))
				setOrders(jobj);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void setOrders(JSONObject json) throws JSONException{
		
		JSONArray array = json.getJSONArray("value");
		orders.clear();
		for(int i = 0; i<array.length(); i++){
			orders.add(new Order(array.getString(i)));
		}
		toast("JSONObject: OtherObjectOrders "+orders.size());
		adapter.notifyDataSetChanged();
	}
	
	private void setOrder(JSONObject json) throws JSONException{
		toast("JSONObject: OtherObjectOrder "+ json.getJSONObject("value").getString("from_name"));
	}
	
	@Override
	public void exception(Exception e, int req) {
		toast("Exception: "+e.getMessage());
		
	}
	
	private void toast(String s){
		Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
	}

}
