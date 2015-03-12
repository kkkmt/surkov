package com.taxi.alfa.fragments;

import com.taxi.alfa.R;
import com.taxi.alfa.objects.MyAdapter;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class OrdersFragment extends Fragment{
	
	private ListView list;
	private MyAdapter adapter;
	
	public OrdersFragment(MyAdapter adapter) {
		this.adapter = adapter;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.order_fragment, null);
		list = (ListView) view.findViewById(R.id.listViewOrders);
		list.setAdapter(adapter);
		return view;
	}
}
