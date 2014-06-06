package com.example.healthhelper.location;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.example.healthhelper.R;
import com.example.healthhelper.util.AMapUtil;

public class LocationGPSActivity extends Activity implements
		AMapLocationListener {
	private LocationManagerProxy locationManager;
	private TextView myLocation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.locationnetwork_activity);
		init();
	}

	private void init() {
		myLocation = (TextView) findViewById(R.id.myLocation);
		locationManager = LocationManagerProxy
				.getInstance(LocationGPSActivity.this);
		
		locationManager.requestLocationUpdates(
				LocationManagerProxy.GPS_PROVIDER, 2000, 10, this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (locationManager != null) {
			locationManager.removeUpdates(this);
			locationManager.destroy();
		}
		locationManager = null;
	}

	@Override
	protected void onDestroy() {
		if (locationManager != null) {
			locationManager.removeUpdates(this);
			locationManager.destroy();
		}
		locationManager = null;
		super.onDestroy();
	}

	@Override
	public void onLocationChanged(Location location) {

	}

	@Override
	public void onProviderDisabled(String provider) {

	}

	@Override
	public void onProviderEnabled(String provider) {

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {

	}

	@Override
	public void onLocationChanged(AMapLocation location) {
		if (location != null) {
			Double geoLat = location.getLatitude();
			Double geoLng = location.getLongitude();
			String str = ("定位成功:(" + geoLng + "," + geoLat + ")"
					+ "\n精  度   :" + location.getAccuracy() + "米"
					+ "\n定位方式:" + location.getProvider() + "\n定位时间:" + AMapUtil
					.convertToTime(location.getTime()));
			myLocation.setText(str);
		}
	}
}
