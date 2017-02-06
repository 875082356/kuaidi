package com.hzgzsoft.logisticsmanage;



import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;
import android.content.Intent;
import android.graphics.Outline;
import android.os.Bundle;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class address_activity extends BaseActivity implements  OnWheelChangedListener ,OnClickListener {
	private WheelView mViewProvince;
	private WheelView mViewCity;
	private WheelView mViewDistrict;
	private Button mBtnConfirm;
	private Button buttonNO;
	private TextView textViewyoubian;
	private LinearLayout layout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select_address);
		setUpViews();
		setUpListener();
		setUpData();
	}
	
	private void setUpViews() {
		mViewProvince = (WheelView) findViewById(R.id.id_province);
		mViewCity = (WheelView) findViewById(R.id.id_city);
		mViewDistrict = (WheelView) findViewById(R.id.id_district);
		mBtnConfirm = (Button) findViewById(R.id.btn_confirm);
		buttonNO = (Button) findViewById(R.id.no);
		textViewyoubian = (TextView) findViewById(R.id.youbian);
		layout = (LinearLayout) findViewById(R.id.li);
	}
	
	private void setUpListener() {
    	
    	mViewProvince.addChangingListener(this);
    	
    	mViewCity.addChangingListener(this);
    	
    	mViewDistrict.addChangingListener(this);
    	
    	mBtnConfirm.setOnClickListener(this);
    	buttonNO.setOnClickListener(this);
    }
	
	private void setUpData() {
		initProvinceDatas();
layout.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		finish();
	}
});
		mViewProvince.setViewAdapter(new ArrayWheelAdapter<String>(address_activity.this, mProvinceDatas));
		
		mViewProvince.setVisibleItems(7);
		mViewCity.setVisibleItems(7);
		mViewDistrict.setVisibleItems(7);
		updateCities();
		updateAreas();
	}

	@Override
	public void onChanged(WheelView wheel, int oldValue, int newValue) {
		// TODO Auto-generated method stub
		if (wheel == mViewProvince) {
			updateCities();
			mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);//每次有变动都更新邮编
			textViewyoubian.setText("当前邮编："+mCurrentZipCode);
		} else if (wheel == mViewCity) {
			updateAreas();
			mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
			textViewyoubian.setText("当前邮编："+mCurrentZipCode);
		} else if (wheel == mViewDistrict) {
			
    updataDistrict();
			
			
			//mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[newValue];
			mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
			textViewyoubian.setText("当前邮编："+mCurrentZipCode);
		}
	}
	
	private void updateCities() {
		int pCurrent = mViewProvince.getCurrentItem();
		mCurrentProviceName = mProvinceDatas[pCurrent];
		String[] cities = mCitisDatasMap.get(mCurrentProviceName);
		if (cities == null) {
			cities = new String[] { "" };
		}
		mViewCity.setViewAdapter(new ArrayWheelAdapter<String>(this, cities));
		mViewCity.setCurrentItem(0);
		updateAreas();
	}
	
	private void updateAreas() {
		int pCurrent = mViewCity.getCurrentItem();
		mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
		String[] areas = mDistrictDatasMap.get(mCurrentCityName);

		if (areas == null) {
			areas = new String[] { "" };
		}
		mViewDistrict.setViewAdapter(new ArrayWheelAdapter<String>(this, areas));
		mViewDistrict.setCurrentItem(0);
		updataDistrict();
	}




	private void updataDistrict()
	{
		int pCurrent = mViewDistrict.getCurrentItem();
		mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[pCurrent];
	}

	
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_confirm:
			showSelectedResult();
			break;
		case R.id.no:
		finish();
			break;
		default:
			break;
		}
	}

	private void showSelectedResult() {
		Toast.makeText(address_activity.this, "当前选中:"+mCurrentProviceName+","+mCurrentCityName+","
				+mCurrentDistrictName+",邮编："+mCurrentZipCode, Toast.LENGTH_SHORT).show();
		
        Intent i = new Intent();
        i.putExtra("mCurrentProviceName", mCurrentProviceName);
        i.putExtra("mCurrentCityName", mCurrentCityName);
        i.putExtra("mCurrentDistrictName", mCurrentDistrictName);
        i.putExtra("mCurrentZipCode", mCurrentZipCode);
        setResult(1, i);
        finish();
	}
}
