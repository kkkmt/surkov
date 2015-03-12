package com.taxi.alfa.objects;

import java.util.ArrayList;

import com.taxi.alfa.R;
import com.taxi.alfa.tools.Order;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter{
	
	private ArrayList<Order> orders;
	private LayoutInflater inflater;
	public MyAdapter(ArrayList<Order>orders, LayoutInflater inflater) {
		this.orders = orders;
		this.inflater = inflater;
	}
	
	@Override
	public int getCount() {
		
		return orders.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return orders.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = inflater.inflate(R.layout.order_item, null);
		Order order = (Order)getItem(position);
			((TextView)v.findViewById(R.id.textViewFrom)).setText(order.from_name);
			((TextView)v.findViewById(R.id.textViewTo)).setText(order.to_name);
			((TextView)v.findViewById(R.id.textViewTime)).setText(order.time_start);
		return v;
	}

}
