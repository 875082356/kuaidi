package com.example.magicuhf;

import android.app.Activity;
import android.hardware.uhf.magic.reader;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ReadWrite extends Activity {
	Button m_btnDubiaoqian, m_btnXiebiaoqian, m_readepc;
	EditText m_editAddress, m_editLength, m_editInput, m_editmima;
	TextView m_result, m_textepc;
	String m_strresult = "";
	ArrayAdapter<String> m_adapter;
	Spinner m_spinner;
	private Handler mHandler = new MainHandler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.readwrite);
		initview();
		reader.m_handler = mHandler;
		/**
		 *
		 * 读取标签EPC：
		 *
		 */
		m_readepc.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				android.hardware.uhf.magic.reader.InventoryLables();
			}
		});
		m_btnDubiaoqian.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// m_strresult = "";
				// m_result.setText(m_strresult);
				if ("".equals(reader.m_strPCEPC)) {
					Toast.makeText(ReadWrite.this, "请先读取标签EPC",
							Toast.LENGTH_SHORT).show();
					return;
				}
				// 读取类型
				byte btMemBank = (byte) m_spinner.getSelectedItemPosition();
				// 读取类型

				// 起始地址
				String stradd = m_editAddress.getText().toString().trim();
				int nadd = Integer.valueOf(stradd);
				// 起始地址
				// 读取长度
				String strdatalength = m_editLength.getText().toString().trim();
				int ndatalen = Integer.valueOf(strdatalength);
				// 读取长度
				// 读取密码：
				String mimaStr = m_editmima.getText().toString().trim();
				if (mimaStr == null || mimaStr.equals("")) {
					m_strresult += "Please enter your 8 - digit password!!\n";
					m_result.setText(m_strresult);
					return;
				}
				byte[] passw = reader.stringToBytes(mimaStr);
				// 读取密码
				byte[] epc = reader.stringToBytes(reader.m_strPCEPC);
				if (null != epc) {
					if (btMemBank == 1) {
						reader.ReadLables(passw, epc.length, epc,
								(byte) btMemBank, nadd, ndatalen);
					} else {
						reader.ReadLables(passw, epc.length, epc,
								(byte) btMemBank, nadd, ndatalen);
					}
				}
			}
		});
		m_btnXiebiaoqian.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if ("".equals(reader.m_strPCEPC)) {
					Toast.makeText(ReadWrite.this, "请先读取标签EPC",
							Toast.LENGTH_SHORT).show();
					return;
				}
				// 写入类型
				byte btMemBank = (byte) m_spinner.getSelectedItemPosition();
				// 起始地址
				String stradd = m_editAddress.getText().toString().trim();

				int nadd = Integer.valueOf(stradd);
				// 写入长度
				String strdatalength = m_editLength.getText().toString().trim(); // 87784441
				int ndatalen = Integer.valueOf(strdatalength);
				// 写入密码：
				String mimaStr = m_editmima.getText().toString().trim();
				if (mimaStr == null || mimaStr.equals("")) {
					m_strresult += "Please enter your 8 - digit password!!\n";
					m_result.setText(m_strresult);
					return;
				}
				byte[] passw = reader.stringToBytes(mimaStr);

				byte[] pwrite = new byte[ndatalen * 2];

				String dataE = m_editInput.getText().toString().trim();
				// if (dataE.length() % 2!= 0) {
				// dataE = dataE + "0";
				// }
				byte[] myByte = reader.stringToBytes(dataE);
				System.arraycopy(myByte, 0, pwrite, 0,
						myByte.length > ndatalen * 2 ? ndatalen * 2
								: myByte.length);
				byte[] epc = reader.stringToBytes(reader.m_strPCEPC);
				{
					reader.Writelables(passw, epc.length, epc, btMemBank,
							(byte) nadd, (byte) ndatalen * 2, pwrite);
				}

			}
		});
	}


	/**
	 * 初始化
	 *
	 * @author Administrator
	 * */
	private void initview() {

		// 新添加：
		m_readepc = (Button) findViewById(R.id.readepc);
		m_textepc = (TextView) findViewById(R.id.textEPC);

		m_btnDubiaoqian = (Button) findViewById(R.id.dubiaoqian);
		m_btnXiebiaoqian = (Button) findViewById(R.id.xiebiaoqian);
		m_editAddress = (EditText) findViewById(R.id.address);
		m_editLength = (EditText) findViewById(R.id.datalength);
		m_editInput = (EditText) findViewById(R.id.inputdata);
		m_editmima = (EditText) findViewById(R.id.password);
		m_result = (TextView) findViewById(R.id.resultView);
		m_spinner = (Spinner) findViewById(R.id.spinner1);
		String[] str = { "RFU", "EPC", "TID", "USER" };
		m_adapter = new ArrayAdapter<String>(ReadWrite.this,
				android.R.layout.simple_spinner_item, str);
		m_adapter
				.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		m_spinner.setAdapter(m_adapter);
		m_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
									   int arg2, long arg3) {
				if (arg2 == 1) {
					m_editAddress.setText("2");
				}
				if (arg2 == 3) {
					m_editAddress.setText("0");
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}

		});
	}

	/**
	 * 接收读写返回消息：
	 *
	 * @author Administrator
	 * */
	private class MainHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			// 修改EPC返回
			if (msg.what == reader.editepcsmsg) {
				m_result.setText((String) msg.obj);
			}
			if (msg.what == reader.msgreadwrireepc) {//读标签
				// if (m_strresult.indexOf((String) msg.obj) < 0)
				if (msg.obj != null) {
					m_strresult = (String) msg.obj;
					m_result.setText(m_strresult);
				}
			}
			if (msg.what == reader.msgreadwrite) {//读标签
				// if (m_strresult.indexOf((String) msg.obj) <0)
				if (msg.obj != null) {
					m_strresult = (String) msg.obj;
					m_result.setText(m_strresult);
				}
			}
			if (msg.what == reader.msgreadepc) {// 读取标签EPC：
				// 读取到标签EPC：
				String readerdata = (String) msg.obj;
				m_textepc.setText(readerdata);
				reader.m_strPCEPC = readerdata;
			}

		}
	};
}
