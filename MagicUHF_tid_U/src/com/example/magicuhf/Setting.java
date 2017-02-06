package com.example.magicuhf;

import java.lang.reflect.Array;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.hardware.uhf.magic.reader;

public class Setting extends Activity {
	Button m_btnReadPower, m_btnSetPower, m_btnReadPam, m_btnSetPam;
	EditText m_editPower, m_editMixer, m_editAMP, m_editThrd;
	TextView m_result;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);
		m_btnReadPower = (Button) findViewById(R.id.BtnReadPower);
		m_btnSetPower = (Button) findViewById(R.id.BtnSetPower);
		m_btnReadPam = (Button) findViewById(R.id.BtnGetPam);
		m_btnSetPam = (Button) findViewById(R.id.BtnSetPam);
		m_editPower = (EditText) findViewById(R.id.power);
		m_editMixer = (EditText) findViewById(R.id.Mixer);
		m_editAMP = (EditText) findViewById(R.id.AFAMP);
		m_editThrd = (EditText) findViewById(R.id.Thrd);
		m_result = (TextView) findViewById(R.id.resultView);
		m_btnReadPower.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {

				int npower = reader.GetTransmissionPower();
				//m_editPower.setText("" + npower);
				if (npower != 0x11) {
					m_editPower.setText("" + npower);
					m_result.setText("读取发射功率成功");
				} else
					m_result.setText("读取发射功率失败");
				// reader.RestartUhf();
			}
		});
		m_btnSetPower.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if("".equals(m_editPower.getText().toString())){
					m_result.setText("请输入发射功率");
					return;
				}
				m_result.setText("");
				int npower = Integer.valueOf(m_editPower.getText().toString()
						.trim());
				if (reader.SetTransmissionPower(npower) != 0x11) {
					m_result.setText("设置发射功率成功");
				} else {
					m_result.setText("设置发射功率失败");
					//reader.RestartUhf();
				}
			}
		});
		m_btnReadPam.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				byte[] buf = new byte[4];
				int nret = reader.GetParameter(buf);
				if (nret != 0x11) {
					m_editMixer.setText("" + buf[0]);
					m_editAMP.setText("" + buf[1]);
					int nthrd = reader.byteToInt(buf, 2, 2);
					m_editThrd.setText("" + nthrd);
					m_result.setText("读取接收调节器参数成功");
				} else
					m_result.setText("读取接收调节器参数失败");

			}
		});
		m_btnSetPam.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if ("".equals(m_editMixer.getText().toString().trim())) {
					m_result.setText("请检查数据");
					return;
				}
				if ("".equals(m_editAMP.getText().toString().trim())) {
					m_result.setText("请检查数据");
					return;
				}
				if ("".equals(m_editThrd.getText().toString().trim())) {
					m_result.setText("请检查数据");
					return;
				}
				m_result.setText("");
				int nmixer = Integer.valueOf(m_editMixer.getText().toString()
						.trim());

				int nIf_x = Integer.valueOf(m_editAMP.getText().toString()
						.trim());
				int nthrd = Integer.valueOf(m_editThrd.getText().toString()
						.trim());

				if (reader.SetParameter((byte) nmixer, (byte) nIf_x, nthrd) != 0x11) {
					m_result.setText("设置接收调节器参数成功");
				} else {
					m_result.setText("设置接收调节器参数失败");
					//reader.RestartUhf();
				}
			}
		});
	}

}
