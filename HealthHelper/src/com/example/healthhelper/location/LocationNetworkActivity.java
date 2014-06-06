package com.example.healthhelper.location;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.example.healthhelper.R;
import com.example.healthhelper.util.AMapUtil;
import com.example.healthhelper.util.ToastUtil;


public class LocationNetworkActivity extends Activity implements
		AMapLocationListener, Runnable {
	private LocationManagerProxy aMapLocManager = null;
	private TextView myLocation;
	private AMapLocation aMapLocation;
	private Handler handler = new Handler();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.locationnetwork_activity);
		myLocation = (TextView) findViewById(R.id.myLocation);
		aMapLocManager = LocationManagerProxy.getInstance(this);
		
		aMapLocManager.requestLocationUpdates(
				LocationProviderProxy.AMapNetwork, 2000, 10, this);
		handler.postDelayed(this, 12000);
	}

	@Override
	protected void onPause() {
		super.onPause();
		stopLocation();
	}


	private void stopLocation() {
		if (aMapLocManager != null) {
			aMapLocManager.removeUpdates(this);
			aMapLocManager.destroy();
		}
		aMapLocManager = null;
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
			this.aMapLocation = location;
			Double geoLat = location.getLatitude();
			Double geoLng = location.getLongitude();
			String cityCode = "";
			String desc = "";
			Bundle locBundle = location.getExtras();
			if (locBundle != null) {
				cityCode = locBundle.getString("citycode");
				desc = locBundle.getString("desc");
			}
			String str = ("定位成功:(" + geoLng + "," + geoLat + ")"
					+ "\n精 度   :" + location.getAccuracy() + "米"
					+ "\n定位方式:" + location.getProvider() + "\n定位时间:"
					+ AMapUtil.convertToTime(location.getTime()) + "\n城市编码:"
					+ cityCode + "\n位置描述:" + desc + "\n省"
					+ location.getProvince() + "\n市" + location.getCity()
					+ "\n区（县）:" + location.getDistrict() + "\n区域编码:" + location
					.getAdCode());
			myLocation.setText(str);
		}
	}

	@Override
	public void run() {
		if (aMapLocation == null) {
			ToastUtil.show(this, "12秒内还没有定位成功，停止定位");
			myLocation.setText("12秒内还没有定位成功，停止定位");
			stopLocation();
		}
	}
}
