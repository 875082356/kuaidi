package com.example.magicuhf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.hardware.uhf.magic.reader;

@SuppressLint("HandlerLeak")
public class EPC extends Activity {
	Button m_btnSearch, m_btnSearchLoop, m_btnStopLoop, m_btnClean,
			m_btnReadtid, m_btnSearchTIDLoop;
	TextView m_result, m_tvCount;
	ArrayAdapter<String> m_adapter;
	// 记录读取标签数据：m_btnXuanding
	private ListView list_read;
	private MuiltSelAdapter adapter;
	private List<readmode> readermodes = new ArrayList<readmode>();
	private Spinner m_spinner, m_spinnerTar, m_spinnerAction, m_spinnerMemBank;
	private Handler mHandler = new MainHandler();
	String m_strresult = "";
	String m_Epcresult = "";
	String m_Tidresult = "";
	int m_nCount = 0;
	CheckBox m_check;
	EditText m_address;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.epc);
		initview();
		android.hardware.uhf.magic.reader.m_handler = mHandler;
		// 读取TID：
		m_btnReadtid.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// 读取TID前，清空数据：
				readermodes.clear();
				adapter = new MuiltSelAdapter(EPC.this, readermodes);
				list_read.setAdapter(adapter);
				android.hardware.uhf.magic.reader.ReadtidLables();
			}
		});
		// 单次读取标签EPC：
		m_btnSearch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				android.hardware.uhf.magic.reader.InventoryLables();
			}
		});
		// 连续读取标签TID
		m_btnSearchTIDLoop.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// if(m_btnSearchTIDLoop.getText().equals("TIDLoop"))
				{
					// 防止重复点击，连续读前先停止：
					reader.StopLoop();
					readermodes.clear();
					adapter = new MuiltSelAdapter(EPC.this, readermodes);
					list_read.setAdapter(adapter);
					android.hardware.uhf.magic.reader.ReadtidLablesLoop();
					// m_btnSearchTIDLoop.setText("TIDStop");
				}
				// else if(m_btnSearchTIDLoop.getText().equals("TIDStop")){
				// m_btnSearchTIDLoop.setText("TIDLoop");
				// android.hardware.uhf.magic.reader.StopLoop();
				// }
			}
		});
		// 连续读取标签EPC
		m_btnSearchLoop.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// if(m_btnSearchLoop.getText().equals("SearchLoop"))
				{
					reader.StopLoop();
					android.hardware.uhf.magic.reader.InventoryLablesLoop();
					// m_btnSearchLoop.setText("SearchStop");
				}
			}
		});
		// 停止连续读取
		m_btnStopLoop.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				android.hardware.uhf.magic.reader.StopLoop();
				// m_btnSearchLoop.setEnabled(true);
			}
		});

		// 选定要操作的标签：
		// m_btnXuanding.setOnClickListener(new OnClickListener() {
		// @Override
		// public void onClick(View arg0) {
		// // int nselpara=0;
		// //
		// nselpara=((m_spinnerTar.getSelectedItemPosition()<<5)|(m_spinnerAction.getSelectedItemPosition()<<2)|m_spinnerMemBank.getSelectedItemPosition());
		// //
		// byte[]epc=reader.stringToBytes(m_spinner.getSelectedItem().toString().substring(2));
		// // byte bTruncate=m_check.isChecked()?(byte)0x80:0x00;
		// // int
		// // nptr=Integer.valueOf(m_address.getText().toString().trim(),16);
		// // reader.Select((byte)nselpara, nptr,(byte)((epc.length)*8),
		// // bTruncate, epc);
		// // reader.m_strPCEPC=m_spinner.getSelectedItem().toString();
		// // 确定几个选定时候需要确定的参数，写为固定值：
		// byte[] epc = reader.stringToBytes(m_spinner.getSelectedItem()
		// .toString().substring(2));
		// reader.Select((byte) 0x01, 16, (byte)((epc.length)*8),
		// (byte) 0, epc);
		// reader.m_strPCEPC = m_spinner.getSelectedItem().toString();
		// }
		// });
		// 清除标签数据：
		m_btnClean.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				m_nCount = 0;
				m_tvCount.setText("标签数量:0");
				readermodes.clear();
				adapter = new MuiltSelAdapter(EPC.this, readermodes);
				list_read.setAdapter(adapter);
				// mScrollView.smoothScrollTo(x,y)则成功了！
			}
		});
		// 初始化界面参数：
		// String
		// []strarray={"S0(000)","S1(001)","S2(010)","S3(011)","SL(100)","RFU(101)",
		// "RFU(110)","RFU(111)"};
		// m_adapter=new
		// ArrayAdapter<String>(EPC.this,android.R.layout.simple_spinner_item,strarray);
		// m_adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		// m_spinnerTar.setAdapter(m_adapter);
		// String []strAct={"000","001","010","011","100","101","110","111"};
		// m_adapter=new
		// ArrayAdapter<String>(EPC.this,android.R.layout.simple_spinner_item,strAct);
		// m_adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		// m_spinnerAction.setAdapter(m_adapter);
		// String []strMem={"RFU","EPC","TID","USER"};
		// m_adapter=new
		// ArrayAdapter<String>(EPC.this,android.R.layout.simple_spinner_item,strMem);
		// m_adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		// m_spinnerMemBank.setAdapter(m_adapter);
		// m_spinnerMemBank.setSelection(1);
	}

	/**
	 * 初始化读卡界面
	 * **/
	private void initview() {
		m_btnSearch = (Button) findViewById(R.id.xunka);
		m_btnSearchLoop = (Button) findViewById(R.id.xunkaloop);
		m_btnSearchTIDLoop = (Button) findViewById(R.id.xunkaTIDloop);
		m_btnStopLoop = (Button) findViewById(R.id.Stoploop);
		// m_btnXuanding = (Button) findViewById(R.id.xuanka);
		m_btnClean = (Button) findViewById(R.id.clean);
		m_result = (TextView) findViewById(R.id.resultView);
		m_tvCount = (TextView) findViewById(R.id.count);
		m_spinner = (Spinner) findViewById(R.id.spinner1);
		// m_spinnerTar=(Spinner)findViewById(R.id.spinnerTarget);
		m_spinnerAction = (Spinner) findViewById(R.id.spinnerAction);
		m_spinnerMemBank = (Spinner) findViewById(R.id.spinnerMembank);
		// m_check=(CheckBox)findViewById(R.id.checkBoxTruncate);
		// m_address=(EditText)findViewById(R.id.Address);
		m_btnReadtid = (Button) findViewById(R.id.readtid);
		list_read = (ListView) findViewById(R.id.listView1);

		adapter = new MuiltSelAdapter(this, readermodes);
		list_read.setAdapter(adapter);
		list_read.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		adapter.notifyDataSetChanged();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		reader.StopLoop();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == 4) {
			reader.StopLoop();
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * @author Administrator 接收标签消息事件;
	 *
	 * ****/
	private class MainHandler extends Handler {
		@SuppressLint("HandlerLeak")
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == reader.msgreadepc) {
				readmode model = new readmode();
				// 在此对数据进行处理：分2中情况进行处理，读取EPC和读取TID：
				String readerdata = (String) msg.obj;
				if (readerdata.contains(":")) {
					String arrString[] = readerdata.split(":");
					model.setEPCNo(arrString[0]);
					model.setTIDNo(arrString[1]);
				} else {
					model.setEPCNo(readerdata);
				}
				// 插入前做判断是否已经读取过该标签：
				IshavaCode(model, 1);
			}
			if (msg.what == reader.readover) {
				Log.e("test", "readerover");
			}
		}
	};

	// 自定义适配器：
	class MuiltSelAdapter extends BaseAdapter {
		// 上下文
		private Context context;
		// 用来控制CheckBox的选中状况
		private HashMap<Integer, Boolean> isSelected;
		// 用来导入布局
		private LayoutInflater inflater = null;
		private List<readmode> models = new ArrayList<readmode>();

		public MuiltSelAdapter(Context context, List<readmode> models) {
			this.context = context;
			this.models = models;
			inflater = LayoutInflater.from(context);
			isSelected = new HashMap<Integer, Boolean>();
			// 初始化数据
			initData(false);
		}

		// 初始化isSelected的数据
		public void initData(boolean flag) {
			for (int i = 0; i < models.size(); i++) {
				isSelected.put(i, flag);
			}
		}

		@Override
		public int getCount() {
			return models.size();
		}

		@Override
		public Object getItem(int arg0) {
			return models.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(final int position, View convertView,
							ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				// 获得ViewHolder对象
				holder = new ViewHolder();
				// 导入布局并赋值给convertview
				convertView = inflater.inflate(R.layout.listviewitem, null);
				holder.tv_EPCNo = (TextView) convertView
						.findViewById(R.id.textEPC);
				holder.tv_TIDNo = (TextView) convertView
						.findViewById(R.id.textTID);
				holder.tv_CountNo = (TextView) convertView
						.findViewById(R.id.textCountNo);
				// 为view设置标签
				convertView.setTag(holder);
			} else {
				// 取出holder
				holder = (ViewHolder) convertView.getTag();
			}
			// 设置list中TextView的显示
			readmode model = readermodes.get(position);
			holder.tv_EPCNo.setText(model.getEPCNo());
			holder.tv_TIDNo.setText(model.getTIDNo());
			holder.tv_CountNo.setText(model.getCountNo());
			return convertView;
		}

		public HashMap<Integer, Boolean> getIsSelected() {
			return isSelected;
		}

		public void setIsSelected(HashMap<Integer, Boolean> isSelected) {
			this.isSelected = isSelected;
		}

		class ViewHolder {
			TextView tv_EPCNo;
			TextView tv_TIDNo;
			TextView tv_CountNo;

		}
	}

	// List<readmode> readermodes
	// 判断是否有改数据，如果有改数据则改变改标签数量，此处的数量如果为读卡，则数量默认为1：
	private Boolean IshavaCode(readmode code, int number) {
		int count = readermodes.size();
		int newcount = 0;
		for (int i = 0; i < count; i++) {
			if (readermodes.get(i).getEPCNo().equals(code.getEPCNo())) {
				newcount = Integer.parseInt(readermodes.get(i).getCountNo());
				readermodes.get(i).setCountNo(
						String.valueOf((newcount + number)));

				adapter = new MuiltSelAdapter(this, readermodes);
				list_read.setAdapter(adapter);
				return true;
			}
		}
		readmode model = new readmode();
		model.setEPCNo(code.getEPCNo());
		model.setTIDNo(code.getTIDNo());
		model.setCountNo(String.valueOf(number));
		readermodes.add(model);
		adapter = new MuiltSelAdapter(this, readermodes);
		list_read.setAdapter(adapter);
		// 在此计算标签的数量：
		int card_num = readermodes.size();
		// 更新卡片数量：
		m_tvCount.setText("标签数量:" + String.valueOf(card_num));
		return false;
	}

}
