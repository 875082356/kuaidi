package com.hzgzsoft.logisticsmanage;

import java.security.PublicKey;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.gsm.SmsManager;
import android.text.StaticLayout;
import android.widget.TextView;
import android.app.Activity;
import android.content.Context;

public class Gps {

	public double longitude;// 经度
	public double latitude;// 纬度

	/**
	 * @param context
	 */
	public Gps(Context context) {
		LocationManager lm = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);

		// 以最优的方式获取经纬度
		Criteria criteria = new Criteria();
		
		criteria.setCostAllowed(true);// 允许使用流量来获取
		criteria.setAccuracy(Criteria.ACCURACY_COARSE);// 获取经纬度的精确度
		String bestProvider = lm.getBestProvider(criteria, true);// 最优的方式

		// 第一个参数是以哪种形式去定位 （基站 gps 网络） 后两个参数为多久以及多远距离获得经纬度
		lm.requestLocationUpdates(bestProvider, 0, 0, new LocationListener() {

			@Override
			public void onStatusChanged(String provider, int status,
					Bundle extras) {
				// gps状态发生变化事事件监听

			}

			@Override
			public void onProviderEnabled(String provider) {

				// gps开启时事件监听
			}

			@Override
			public void onProviderDisabled(String provider) {
				// gps结束事件监听

			}

			@Override
			public void onLocationChanged(Location location) {
				System.out.println("1111111111111111");
				 longitude = location.getLongitude();// 经度
				 latitude = location.getLatitude();// 纬度
				System.out.println("经度:" + longitude + "," + "纬度:" + latitude);

			}
		});

	}

	/**
	 * @return 获取经度
	 */
	public double getlongitude() {

		return longitude;
	}

	/**
	 * @return 获取纬度
	 */
	public double getlatitude() {
		return latitude;
	}

}
