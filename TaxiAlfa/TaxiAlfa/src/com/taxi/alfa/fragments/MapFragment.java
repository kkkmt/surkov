package com.taxi.alfa.fragments;

import ru.yandex.yandexmapkit.MapController;
import ru.yandex.yandexmapkit.MapView;
import ru.yandex.yandexmapkit.OverlayManager;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.taxi.alfa.R;


public class MapFragment extends Fragment{
	private View v;
	private MapView mapView;
	MapController mMapController;
    OverlayManager mOverlayManager;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(v == null){
			v = inflater.inflate(R.layout.map_fragment, null);
			mapView = (MapView) v.findViewById(R.id.map);
	        mapView.getMapController().setJamsVisible(true);
	        mapView.showBuiltInScreenButtons(true);   
		}
		return v;
	}
	
}
