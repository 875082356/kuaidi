package com.example.magicuhf;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.hardware.uhf.magic.DevBeep;
import android.hardware.uhf.magic.reader;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MagicMainActivity extends Activity {
	// Environment.getExternalStorageDirectory()getRootDirectory()//获取手机根目录
	// Environment.getExternalStorageDirectory()getExternalStorageDirectory()//获取SD卡根目录
	Button m_setting, m_btnEPC, m_btnread, m_btnLock;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_magic_main);
		Initview();
		m_setting.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intentTo = new Intent();
				intentTo.setClass(MagicMainActivity.this, Setting.class);
				startActivity(intentTo);
				//String s=android.os.Build.MODEL;
			}
		});
		/**
		 * 读取EPC，测试页面跳转：
		 */
		m_btnEPC.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intentTo = new Intent();
				intentTo.setClass(MagicMainActivity.this, EPC.class);
				startActivity(intentTo);
			}
		});
		m_btnread.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intentTo = new Intent();
				intentTo.setClass(MagicMainActivity.this, ReadWrite.class);
				startActivity(intentTo);
			}
		});
		m_btnLock.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0){
				Intent intentTo = new Intent();
				intentTo.setClass(MagicMainActivity.this, Lock.class);
				startActivity(intentTo);
			}
		});
	}
	private void Initview() {
		m_setting = (Button) findViewById(R.id.Setting);
		m_btnEPC =  (Button) findViewById(R.id.ReadEPC);
		m_btnread = (Button) findViewById(R.id.ReadWrite);
		m_btnLock = (Button) findViewById(R.id.Lockkill);
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		//DevBeep.release();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.magic_main, menu);
		return true;
	}
}