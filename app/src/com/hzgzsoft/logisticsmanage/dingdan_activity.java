package com.hzgzsoft.logisticsmanage;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.magicuhf.MagicMainActivity;
import com.google.gson.JsonObject;


import JavaBeen.CustomerAddress;
import JavaBeen.Info;
import JavaBeen.Info_json_Sender_Receiver;
import JavaBeen.dingdan;
import android.R.integer;
import android.R.string;
import android.app.Activity;
import android.hardware.Camera.Area;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class dingdan_activity extends AppCompatActivity {
	private ArrayList<dingdan> dindanList;
	private ListView liView;
	private ArrayList<Info_json_Sender_Receiver> info_json_Sender_Receivers;
	private ArrayList<Info> Infolist;
private dingdan dingdan;
	private WebClient client;
	private String result="";
	
	private Handler handler=new Handler(){
		
		public void handleMessage(android.os.Message msg) {
			
			switch (msg.what) {
			case 0:
				jiexi_json();
				break;
			case 1:
				Toast.makeText(getApplicationContext(), "网络异常，请刷新", 0).show();
				break;
			case 2:
				Toast.makeText(getApplicationContext(), "获取数据成功", 0).show();
			
				jiexi_json_info();
				break;
			case 3:
				
				break;

			default:
				break;
			}
			
			
		};
		
		
	};

	

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dingdan);
		client = new WebClient(null);
		
		//tbl_Express表根据DeviceID查到SenderID跟RecipientsID在根据SenderID和RecipentsID查到tbl_CustomerAddress表AddressID（寄件人记录和收件人记录）
		Initview();
		Initdata();
		
	//	liView.setAdapter(new Mylistview());
		
	}
	
	
	class Mylistview extends BaseAdapter {

		@Override
		public int getCount() {
			return Infolist.size();
		}

		@Override
		public Object getItem(int position) {
			return Infolist.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {

			}
			return convertView;
		}

	}
	
	public class ViewHolder{
		
		
		
	}

	private void Initview() {

		liView = (ListView) findViewById(R.id.lv);
		
		
	}

	protected void jiexi_json_info() {
		Infolist = new ArrayList<Info>();
		for (int i = 0; i < info_json_Sender_Receivers.size(); i++) {

			Info info = new Info();

			getInfo(info_json_Sender_Receivers.get(i).Sender_info, info, true);// 解析寄件人信息

			getInfo(info_json_Sender_Receivers.get(i).Receiver_info, info,
					false);// 解析收件人信息

			info.time=dindanList.get(i).time;
			Infolist.add(info);

		}
	
		for(int j=0;j<Infolist.size();j++)
    	{
    		
    		System.out.println("第"+j+"个寄件人："+Infolist.get(j).Sender_Name+"第"+j+"个收件人："+Infolist.get(j).Receiver_Name+"时间:"+Infolist.get(j).time);
    		
    	}

	}



	private void getInfo(String result, Info info, boolean temp) {

		try {
			JSONObject jo = new JSONObject(result);

			JSONArray ja = jo.getJSONArray("GetInfo_Sender_ReciverResult");
			
			
				for (int j = 0; j < ja.length(); j++) {
					if (temp)// 解析寄件人json
					{
					JSONObject jo1 = ja.getJSONObject(j);
					info.Sender_Area = jo1.getString("Area");
					info.Sender_County = jo1.getString("County");
					info.Sender_Name = jo1.getString("Name");
					info.Sender_PhoneNumber = jo1.getString("PhoneNumber");
					info.Sender_Postcode = jo1.getString("Postcode");
					info.Sender_Province = jo1.getString("Province");
					info.Sender_Town = jo1.getString("Town");
					info.Sender_Village = jo1.getString("Village");
					}
					else {//解析收件人信息

						JSONObject jo1 = ja.getJSONObject(j);
						info.Receiver_Area = jo1.getString("Area");
						info.Receiver_County = jo1.getString("County");
						info.Receiver_Name = jo1.getString("Name");
						info.Receiver_PhoneNumber = jo1.getString("PhoneNumber");
						info.Receiver_Postcode = jo1.getString("Postcode");
						info.Receiver_Province = jo1.getString("Province");
						info.Receiver_Town = jo1.getString("Town");
						info.Receiver_Village = jo1.getString("Village");
						
					}

				}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	protected void jiexi_json() {
		System.out.println("解析数据。。。。。。。。。。。。。。。");
      
		 dindanList = new ArrayList<dingdan>();
      try {
	JSONObject jo = new JSONObject(result);
		
			JSONArray ja = jo.getJSONArray("GetSenderID_RecipientsIDResult");
			for(int i=0;i<ja.length();i++)
			{
				dingdan = new dingdan();
				JSONObject jo1 = ja.getJSONObject(i);
				dingdan.SenderID=jo1.getString("SenderID");
				dingdan.RecerverID=jo1.getString("RecipientsID");
				 dingdan.time=jo1.getString("StartTime");
				
				dindanList.add(dingdan);
				
				}
			
	/*		for(int i=0;i<dindanList.size();i++)
			{
				
				System.out.println(".............................SenderID:"+dindanList.get(i).SenderID+"    RecipientsID:"+dindanList.get(i).RecerverID+"  时间"+dindanList.get(i).time);
			}*/
			//
			//在根据SenderID和RecipentsID查到tbl_CustomerAddress表AddressID（寄件人记录和收件人记录）
			getInfo_Sender_Receiver(dindanList);
			
			
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		
		
		
	}

	private void getInfo_Sender_Receiver(final ArrayList<dingdan> dindanList) {

		new Thread(new Runnable() {

			@Override
			public void run() {
			info_json_Sender_Receivers=	new ArrayList<Info_json_Sender_Receiver>();
				for (int i = 0; i < dindanList.size(); i++) {
					Info_json_Sender_Receiver info_Sender_Receiver = new Info_json_Sender_Receiver();
					result = client.doGet(C.SERVER_URL
							+ C.Service_GetInfo_Sender_Reciver + "/"
							+ dindanList.get(i).SenderID + "/" + "123456",
							null, handler, true);// 1若数据库没有记录则获取为空2网络连接异常
					
					if (!TextUtils.isEmpty(result)) {
						// 先将数据放入listview
						
						info_Sender_Receiver.Sender_info=result;
						
						result = client.doGet(C.SERVER_URL
								+ C.Service_GetInfo_Sender_Reciver + "/"
								+ dindanList.get(i).RecerverID + "/" + "123456",
								null, handler, true);// 1若数据库没有记录则获取为空2网络连接异常
						if(!TextUtils.isEmpty(result))
						{
							
							info_Sender_Receiver.Receiver_info=result;
							info_json_Sender_Receivers.add(info_Sender_Receiver);
							
						}
						else{
							//未获取到收件人信息
							
							System.out.println("未获取到"+i+"个收件人信息。。。。。。。。。。。。。。。。。。。。。");
						}
						

					}
					else {
						//未获取到寄件人信息
						
						System.out.println("未获取到"+i+"个寄件人信息。。。。。。。。。。。。。。。。。。。。");
						
					}
					
					
					
				}
				handler.sendEmptyMessage(2);
				

			}
		}).start();

	}

	private void Initdata() {
		
		GetSenderID_RecipientsID();
		
		
	}

	private void GetSenderID_RecipientsID() {

		new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				result = client.doGet(C.SERVER_URL
						+ C.Service_GetSenderID_RecipientsID + "/" + "-1"
						+ "/" + "123456", null, handler, true);//根据时间顺序已经拍好序
				//if()//数据库中还未有该设备的订单号
			handler.sendEmptyMessage(0);
				
			}
		}).start();
		
		
		
	}
	
	
}
