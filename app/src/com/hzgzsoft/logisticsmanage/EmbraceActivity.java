package com.hzgzsoft.logisticsmanage;

//下单时的ExpressID还未处理，，，，，，，，，，，，，，，，，，，，，，，，，，，，，，，，，，，，，，，，，
import JavaBeen.CustomerAddress;
import JavaBeen.CustomerAddressResult;
import JavaBeen.Express;
import android.R.bool;
import android.R.integer;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.hardware.uhf.magic.reader;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.ClientCertRequest;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.magicuhf.MagicMainActivity;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
//在快递员揽件时，PDA会把ExpressID、寄件人邮编、收件人邮编和寄件状态写入RFID中。
//其中ExpressID用于中间节点（设备）对快件的快速注册，后三个字段用于实现快速分拣。

public class EmbraceActivity extends AppCompatActivity {
	boolean isWritingSuccessful = true;
	boolean IsGetExpressIDSuccessful = true;
	private String sendpost;
	private String receivepost;
	private String expressID;
	private RadioButton xieyifoubButton, daofushiButton;
	private boolean ischongzhi = false;
	private WebClient client;
	private String result = "";
	private int SendAddrID;
	private int ReceiverAddrID;
	private boolean temp;
	private EditText mSendPhoneNumber, mReceivePhoneNumber,
			qianshourenEditText;
	private TextView mSendName, mSendPost, mSendAddress, mReceiveName,
			mReceivePost, mReceiveAddress;
	private Button queding;
	private Button bindBox;
	private TextView rFID;
	private Handler mHandler = new MainHandler();
	// private Handler mHandler1 = new MainHandler1();
	String m_strresult = "";
	// String m_strresult1;
	private EditText leixing;
	private EditText baofei;
	private EditText mingcheng;
	private EditText shuliang;
	private EditText zhongliang;
	private EditText tiji;
	private EditText feiyong;

	private Handler handler = new Handler() {

		public void handleMessage(Message msg) {

			switch (msg.what) {
			case 0:

				if (result.length() != 31) {

					// System.out.println("result....................."+result);
					jiexi_json(result);

				} else {

					Toast.makeText(getApplicationContext(), "该号码没有记录,请重置", 0)
							.show();
					if (!temp) {
						// 寄件人的数据库信息
						mSendName.setText("");
						mSendPost.setText("");

						mSendAddress.setText("");
					} else {
						mReceiveName.setText("");
						mReceivePost.setText("");

						mReceiveAddress.setText("");

					}
				}

				break;
			case 1:
				Toast.makeText(getApplicationContext(), "联网失败，请检查网络", 0).show();
				break;
			case 2:
				// 获取ExpressID成功
				// Toast.makeText(getApplicationContext(), "获取ExpressID成功",
				// 0).show();
				jiexi_ExpressID_json(result);
				break;
			case 3:

				break;

			default:
				break;
			}

		};

	};
	private Handler xiadanHandler = new Handler() {

		public void handleMessage(Message msg) {

			switch (msg.what) {
			case 0:
				Toast.makeText(getApplicationContext(), "因网络异常导致下单失败，请检查网络", 0)
						.show();
				break;
			case 1:
				// Toast.makeText(getApplicationContext(), "下单成功", 0).show();
				break;
			case 2:
				Toast.makeText(getApplicationContext(), "下单失败，请重试", 0).show();

				break;
			case 3:
				
				
				break;
			case 4:

				break;

			default:
				break;
			}

		};
	};

	private class MainHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {

			if (msg.what == reader.msgreadwrireepc) {// 读标签

				if (msg.obj != null) {
					m_strresult = (String) msg.obj;
					if (m_strresult.length() != 24) {
						System.out.println("错误tid长度：" + m_strresult.length()
								+ "错误tid:" + m_strresult);
						Toast.makeText(getApplicationContext(), "读取失败，请重试", 0)
								.show();
					} else {
						System.out.println("TID长度为........"
								+ m_strresult.length());
						System.out.println("TID............." + m_strresult);
						rFID.setText(m_strresult);
						Toast.makeText(getApplicationContext(), "绑定成功", 0)
								.show();

						// System.out.println("绑定按钮里的EPC信息" +
						// reader.m_strPCEPC);

					}

				}
			}
			if (msg.what == reader.msgreadwrite) {// 写标签
				// if (m_strresult.indexOf((String) msg.obj) <0)
				if (msg.obj != null) {
					m_strresult = (String) msg.obj;

					if (m_strresult.equals("OK")) {
						// TODO
						// 写入标签成功
						// 传数据到后台
						// data2back();
						Toast.makeText(getApplicationContext(), "下单成功", 0)
								.show();

						isWritingSuccessful = true;
						// TODO
						// 跳转到订单记录页面
						startActivity( new Intent(getApplicationContext(),dingdan_activity.class));
						
					} else {
						Toast.makeText(getApplicationContext(),
								"下单失败（信息未写入标签），请重试", 0).show();
						isWritingSuccessful = false;// 写入信息失败就重新下单
						return;
					}
				}
			}

			if (msg.what == reader.msgreadepc) {// 读取到标签EPC：
				String readerdata = (String) msg.obj;

				System.out.println("epc信息........." + readerdata);

				reader.m_strPCEPC = readerdata;

				if (TextUtils.isEmpty(reader.m_strPCEPC)) {
					Toast.makeText(getApplicationContext(), "未识别卡，请重试", 0)
							.show();

					return;

				} else {

					// TODO
					byte[] passw = reader.stringToBytes("00000000"); // 默认密码可自己设置

					byte[] epc = reader.stringToBytes(reader.m_strPCEPC);// EPC信息

					byte btMemBank = (byte) 2;// 0-RFU存储区块 1-EPC存储区块
												// 2-TID存储区块(唯一标示) 3-USER存储区块
					int nadd = Integer.valueOf("0");// 开始地址
					int ndatalen = Integer.valueOf("6");// 读出6个字节数24位

					reader.ReadLables(passw, epc.length, epc, (byte) btMemBank,
							nadd, ndatalen);

				}

			}

		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_embrace);

		client = new WebClient(null);
		leixing = (EditText) findViewById(R.id.leixing);
		baofei = (EditText) findViewById(R.id.baofei);
		mingcheng = (EditText) findViewById(R.id.mingcheng);
		shuliang = (EditText) findViewById(R.id.shuliang);
		zhongliang = (EditText) findViewById(R.id.zhongliang);
		tiji = (EditText) findViewById(R.id.tiji);
		feiyong = (EditText) findViewById(R.id.feiyong);
		// xieyishiButton=(RadioButton) findViewById(R.id.xieyishi);
		xieyifoubButton = (RadioButton) findViewById(R.id.xieyifou);
		daofushiButton = (RadioButton) findViewById(R.id.daofushi);
		qianshourenEditText = (EditText) findViewById(R.id.qianshoren);

		reader.m_handler = mHandler;
		rFID = (TextView) findViewById(R.id.RFID);
		bindBox = (Button) findViewById(R.id.bindBox);
		queding = (Button) findViewById(R.id.queding);
		// 寄件人
		mSendName = (TextView) findViewById(R.id.sendName);
		mSendPost = (TextView) findViewById(R.id.sendPost);
		mSendAddress = (TextView) findViewById(R.id.sendAddress);
		mSendPhoneNumber = (EditText) findViewById(R.id.sendPhone);
		/*******************************************/
		// 收件人
		mReceiveName = (TextView) findViewById(R.id.receiveName);
		mReceivePost = (TextView) findViewById(R.id.receivePost);
		mReceiveAddress = (TextView) findViewById(R.id.receiveAddress);
		mReceivePhoneNumber = (EditText) findViewById(R.id.receivePhone);
		/****************************************/

		Init();

		// 监听联系电话输入的电话长度
		mSendPhoneNumber.addTextChangedListener(new TextWatcher() {// 事实查询监听文本框输入变化(上个activity返回该activity先走该方法然后走onActivityResult)

					@Override
					public void onTextChanged(CharSequence s, int start,
							int before, int count) {

					}

					@Override
					public void beforeTextChanged(CharSequence s, int start,
							int count, int after) {

					}

					@Override
					public void afterTextChanged(Editable s) {
						temp = false;
						// 判断输入的电话号码的位数
						// 手机号码11位开始，手机短号6位数
						// 本地号码7位开始
						System.out.println("输入的电话号码的位数"
								+ mSendPhoneNumber.getText().toString()
										.length());
						// 7位8位3+8位4+8位
						if (mSendPhoneNumber.getText().toString().length() > 10
								&& SP_Utils.getSP_Int(getApplicationContext(),
										"sendlength", -1) != mSendPhoneNumber
										.getText().toString().length()) {
							// 输入的电话号码为11位时联网查询数据库

							FirstgetdatafromNet(mSendPhoneNumber.getText()
									.toString());

						} else {
							mSendName.setText("");
							mSendPost.setText("");

							mSendAddress.setText("");
						}
						if (ischongzhi) {
							ischongzhi = false;
							System.out.println("重置了。。。。。。。。。。。。。。。。。。。。");
							SP_Utils.putSP_Int(getApplicationContext(),
									"sendlength", -1);
						}
					}
				});
		mReceivePhoneNumber.addTextChangedListener(new TextWatcher() {// 事实查询监听文本框输入变化(上个activity返回该activity先走该方法然后走onActivityResult)

					@Override
					public void onTextChanged(CharSequence s, int start,
							int before, int count) {

					}

					@Override
					public void beforeTextChanged(CharSequence s, int start,
							int count, int after) {

					}

					@Override
					public void afterTextChanged(Editable s) {
						temp = true;

						System.out.println("输入的电话号码的位数"
								+ mReceivePhoneNumber.getText().toString()
										.length()
								+ "SP_Utils值:"
								+ SP_Utils.getSP_Int(getApplicationContext(),
										"receiverlength", -1));
						// 判断输入的电话号码的位数
						// 手机号码11位开始，手机短号6位数
						// 本地号码7位开始
						/*
						 * System.out.println("输入的电话号码的位数" +
						 * SP_Utils.getSP_String(getApplicationContext(),
						 * "length", ""));
						 */
						// 7位8位3+8位4+8位
						if (mReceivePhoneNumber.getText().toString().length() > 10
								&& SP_Utils.getSP_Int(getApplicationContext(),
										"receiverlength", -1) != mReceivePhoneNumber
										.getText().toString().length()) {

							// 输入的电话号码为11位时联网查询数据库 n

							FirstgetdatafromNet(mReceivePhoneNumber.getText()
									.toString());

						} else {

							mReceiveName.setText("");
							mReceivePost.setText("");

							mReceiveAddress.setText("");
						}
						if (ischongzhi) {
							ischongzhi = false;
							System.out.println("重置了。。。。。。。。。。。。。。。。。。。。");
							SP_Utils.putSP_Int(getApplicationContext(),
									"receiverlength", -1);
						}

					}
				});

		bindBox.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				System.out.println("绑定");

				// m_strresult1 = "";
				// rFID.setText(m_strresult1);
				// 先获取epc信息
				android.hardware.uhf.magic.reader.InventoryLables();

			}
		});

		queding.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				System.out.println("确定按钮");

				

				if (rFID.getText().toString().length() != 24) {

					Toast.makeText(getApplicationContext(), "未绑定包装箱，请绑定", 0)
							.show();
				}

				else {
					sendpost = mSendPost.getText().toString();
					receivepost = mReceivePost.getText().toString();

		

					// TODO 先传数据到后台，然后在请求网络获取expressid 在写入信息到标签中
					if(IsGetExpressIDSuccessful)
					{
					if (isWritingSuccessful) {
						data2back();
					}// 传数据到后台
					else {

						writeinformation(sendpost, receivepost);// 该情况是表面下单已经成功且获得了ExpressID的值但是写入标签失败
					}
					}
					else {
						GetExpressID(SendAddrID);//传数据成功获取ExpressID失败
					}
					

					/*
					 * // 获取gps信息 Gps gps = new Gps(getApplicationContext()); //
					 * System.out.println("经度："+gps.getlatitude());
					 * gps.getlatitude();// 经度 gps.getlongitude();// 纬度
					 */
				}

			}
		});

		findViewById(R.id.sendReset).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) { // （寄）重置按钮
						String SendPhoneNumber = mSendPhoneNumber.getText()
								.toString();
						if (TextUtils.isEmpty(SendPhoneNumber)) {

							// 抖动
							Animation shake = AnimationUtils.loadAnimation(
									getApplicationContext(), R.anim.shake);
							mSendPhoneNumber.startAnimation(shake);
						} else {
							if (SendPhoneNumber.length() > 12) {
								Toast.makeText(getApplicationContext(),
										"请输入正确的电话号码", Toast.LENGTH_SHORT)
										.show();
							} else {
								Intent intent = new Intent(
										EmbraceActivity.this,
										SelectAddressActivity.class);
								intent.putExtra("PhoneNumber", mSendPhoneNumber
										.getText().toString());
								intent.putExtra("Target", "寄件人");
								ischongzhi = true;
								SP_Utils.putSP_Int(getApplicationContext(),
										"sendlength", mSendPhoneNumber
												.getText().toString().length());
								startActivityForResult(intent, 0);// requestCode=1
							}

						}

					}

				});
		findViewById(R.id.receiveReset).setOnClickListener(
				new View.OnClickListener() {// （收）重置按钮
					@Override
					public void onClick(View view) {

						String ReceivePhoneNumber = mReceivePhoneNumber
								.getText().toString();
						if (TextUtils.isEmpty(ReceivePhoneNumber)) {

							// 抖动
							Animation shake = AnimationUtils.loadAnimation(
									getApplicationContext(), R.anim.shake);
							mReceivePhoneNumber.startAnimation(shake);
						} else {
							if (ReceivePhoneNumber.length() > 12) {
								Toast.makeText(getApplicationContext(),
										"请输入正确的电话号码", Toast.LENGTH_SHORT)
										.show();
							} else {
								Intent intent = new Intent(
										EmbraceActivity.this,
										SelectAddressActivity.class);
								intent.putExtra("PhoneNumber",
										mReceivePhoneNumber.getText()
												.toString());
								intent.putExtra("Target", "收件人");
								ischongzhi = true;
								SP_Utils.putSP_Int(getApplicationContext(),
										"receiverlength", mReceivePhoneNumber
												.getText().toString().length());
								startActivityForResult(intent, 0);// requestCode=1
							}

						}

					}
				});

	}

	protected void jiexi_ExpressID_json(String result) {

		try {
			JSONObject jO = new JSONObject(result);
			JSONArray ja = jO.getJSONArray("GetExpressIDResult");

			JSONObject jo1 = ja.getJSONObject(0);// 获得的ExpressID已经拍好序第一个最大，下单时ExpressID越来越大
			expressID = jo1.getString("expressid");
			System.out.println("EXPRESSID。。。。。。。。。。。。。。。。。。。。。。。。" + expressID);
			// 传数据跟获得ExpressID ok
			// 写入数据到标签(如果写入不成功则要重新下单，注意避免重传数据到数据库)
			writeinformation(sendpost, receivepost);

		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	public int shuliang1;
	public float zhongliang1;
	public float feiyong1;

	public void data2back() {

		String leixing1 = leixing.getText().toString();
		String mingcheng1 = mingcheng.getText().toString();
		if (!TextUtils.isEmpty(shuliang.getText().toString())) {
			shuliang1 = Integer.parseInt(shuliang.getText().toString());
		}
		if (!TextUtils.isEmpty(zhongliang.getText().toString())) {
			zhongliang1 = Float.parseFloat(zhongliang.getText().toString());
		}
		String tiji1 = tiji.getText().toString();
		if (!TextUtils.isEmpty(feiyong.getText().toString())) {
			feiyong1 = Integer.parseInt(feiyong.getText().toString());
		}
		String qianshouren = qianshourenEditText.getText().toString();

		// 提交数据时判断寄件人SendAddrID和收件人ReceiverAddrID（该数据存在表tbl_CustomerAddress中AddrID）是否为空
		if (SendAddrID == 0 || ReceiverAddrID == 0) {
			Toast.makeText(getApplicationContext(), "请先完善收寄件人信息", 0).show();
		}

		else {

			// 准备要提交的数据
			final String time = C.getTime();
			final Express express = new Express();

			// DeviceID应该是根据手持设备的人的名字从数据库表中tbl_NodeDevice获取的（即设备注册时自动生成的id）
			express.ExpressID = -1;// 该id是由数据库中表tbl_Express自动生成的，快件的内部单号，可用于快速查询某个快件。但考虑到安全性，此单号不直接提供给外部客户进行快件查询。外部客户可利用加密后的ExpressID作为快件号在网站和手机终端上查询。
									// /在快递员揽件时，PDA会把ExpressID、寄件人邮编、收件人邮编和寄件状态写入RFID中。其中ExpressID用于中间节点（设备）对快件的快速注册，后三个字段用于实现快速分拣。
			express.RFIDID = rFID.getText().toString();// /
														// 快递箱的RFID的ID号，此ID是RFID内建的TID，不可修改
			express.DeviceID = 666;// 该id是由数据库表中tbl_NodeDevice自动生成的，
									// 箱子当前绑定于某一节点（或设备）的ID。当寄件动作结束后，此字段值为-1，表示没有绑定在任何节点或设备上
			express.SenderID = SendAddrID;// /
											// 寄件人地址ID，为tbl_CustomerAddress.AddrID字段值
			express.RecipientsID = ReceiverAddrID;// /
													// 收件人地址ID，为tbl_CustomerAddress.AddrID字段值
			express.State = 1;// / 快件状态：1.正在正常寄件；0.寄件结束；-1.退件；-2.遗失或破损，快件无法给收件人。
			express.GPSPath = 120.321321 + "," + 23.321312 + ";";// / 登记GPS轨迹
			express.GPSEndTime = "2016-04-03 17:17:00";// / 最后采集GPS的时间
			express.StartTime = time;// / 揽件时间
			express.EndTime = "2016-04-06 17:17:00";// / 送达时间
			express.Trace = "666,2015-3-2 12:13 00:00;";// /
														// 用于记录历次节点（设备）的注册信息（设备ID和注册时间）
			express.GoodsType = leixing1;// leixing1;/// 物品类型：文件、物品等
			express.GoodsName = mingcheng1;// mingcheng1;/// 物品名称：手机、数码相机等
			express.GoodsNumber = shuliang1;// shuliang1;/// 物品数量
			express.GoodsWeight = zhongliang1;// zhongliang1;/// 物品重量
			express.GoodsBulk = tiji1;// tiji1;/// 物品体积
			express.Amount = feiyong1;// feiyong1;
			if (xieyifoubButton.isChecked()) {// 协议否
				System.out.println("xieyifoubButton已经被选中");// /
															// 是否协议结算。默认为0，表示不是；1表示协议结算
				express.IsAgreedSettlement = false;
			} else {
				System.out.println("xieyifoubButton未被选中");
				express.IsAgreedSettlement = true;

			}
			if (daofushiButton.isChecked()) {
				express.IsReversedPay = true;
				System.out.println("是到付");
			} else {
				express.IsReversedPay = false;
				System.out.println("不是到付");
			}
			express.RecipientsName = qianshouren;

			// 提交数据到服务器*******************************************************************

			new Thread(new Runnable() {

				@Override
				public void run() {

					// client.doGet(C.SERVER_URL);

					// AddExpress 此函数用于快递员下单，里面会修改发件人地址的最后使用时间，表明这个地址刚被使用过
					// 先要查出寄收件人电话对应的数据库中的Addrid(要判断是直接输入电话后得出的地址还是重置后返回得到的地址)
					String resultsString = client.xiadan(C.SERVER_URL
							+ C.Service_AddExpress, express,
							"application/json", xiadanHandler);
					// TODO
					System.out.println("快递员下单时的result:  " + resultsString);
					System.out.println("......寄件人SendAddrID:" + SendAddrID
							+ "      收件人ReceiverAddrID:" + ReceiverAddrID);
					if (resultsString.contains("true"))// 传数据到数据库成功
					{

						// TODO
						// 开子线程访问网络获取EXpressID
						GetExpressID(SendAddrID);

						

						
					} else if (resultsString.contains("false")) {
						xiadanHandler.sendEmptyMessage(2);// 下单失败
					}

				}
			}).start();

		}

	}

	// 获取表中ExpressID数据
	protected void GetExpressID(final int SendAddrID) {

		new Thread(new Runnable() {

			@Override
			public void run() {
				// 查询tbl_Express表获得ExpressID
				// ExpressID是自然增长数，因此拿到最大的数就是刚下单的ExpressID

				try {

					result = client.doGet(C.SERVER_URL + C.Service_GetExpressID
							+ "/" + SendAddrID + "/" + "123456", null, handler,
							true);//
					// TODO

					if (!TextUtils.isEmpty(result)) {
						IsGetExpressIDSuccessful=true;
						handler.sendEmptyMessage(2);// 通知主线程数据库表查询完毕
						
					}
					else{
						//获取ExpressID失败
						IsGetExpressIDSuccessful=false;
						xiadanHandler.sendEmptyMessage(2);// 获取ExpressID失败
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}).start();

	}

	protected void writeinformation(String sendpost, String receivepost) {

		// 标签里写入信息
		// ExpressID、寄件人邮编、收件人邮编和寄件状态写入RFID中

		byte[] passw = reader.stringToBytes("00000000"); // 默认密码可自己设置

		byte[] epc = reader.stringToBytes(reader.m_strPCEPC);// EPC信息

		// 写入类型user
		byte btMemBankuser = (byte) 3;// 0-RFU存储区块 1-EPC存储区块
		// 2-TID存储区块(唯一标示) 3-USER存储区块
		// 起始地址
		int nadduser = Integer.valueOf("0");
		// 写入长度
		int ndatalenuser = Integer.valueOf("6");// 写入6个 24位

		byte[] pwrite = new byte[ndatalenuser * 2];
		// 写入的内容
		String dataE = expressID + "F" + sendpost + "F" + receivepost;
		int nadd = Integer.valueOf("0");// 开始地址
		byte[] myByte = reader.stringToBytes(dataE);
		System.arraycopy(myByte, 0, pwrite, 0,
				myByte.length > ndatalenuser * 2 ? ndatalenuser * 2
						: myByte.length);

		reader.Writelables(passw, epc.length, epc, btMemBankuser, (byte) nadd,
				(byte) ndatalenuser * 2, pwrite);

	}

	private CustomerAddressResult addressResult;

	protected void jiexi_json(String result) {

		// 解析json
		Gson gson = new Gson();
		addressResult = gson.fromJson(result, CustomerAddressResult.class);


		if (!temp) {
			// 寄件人的数据库信息
			mSendName
					.setText(addressResult.GetCustomerAddressResult.get(0).Name);
			mSendPost
					.setText(addressResult.GetCustomerAddressResult.get(0).Postcode);
			SendAddrID = addressResult.GetCustomerAddressResult.get(0).AddrID;
			mSendAddress
					.setText(addressResult.GetCustomerAddressResult.get(0).Province
							+ addressResult.GetCustomerAddressResult.get(0).Area
							+ addressResult.GetCustomerAddressResult.get(0).County
							+ addressResult.GetCustomerAddressResult.get(0).Town
							+ addressResult.GetCustomerAddressResult.get(0).Village);
		} else {
			mReceiveName
					.setText(addressResult.GetCustomerAddressResult.get(0).Name);
			mReceivePost
					.setText(addressResult.GetCustomerAddressResult.get(0).Postcode);
			ReceiverAddrID = addressResult.GetCustomerAddressResult.get(0).AddrID;
			mReceiveAddress.setText(addressResult.GetCustomerAddressResult
					.get(0).Province
					+ addressResult.GetCustomerAddressResult.get(0).Area
					+ addressResult.GetCustomerAddressResult.get(0).County
					+ addressResult.GetCustomerAddressResult.get(0).Town
					+ addressResult.GetCustomerAddressResult.get(0).Village);

		}

	}

	// 输入电话号码时从数据库获取数据
	protected void FirstgetdatafromNet(final String phoneNumber) {

		// 开启一个子线程，进行网络操作，等待有返回结果，使用handler通知UI
		// new Thread(networkTask).start();
		// 13588228755
		new Thread(new Runnable() {

			public void run() {

				try {

					result = client.doGet(C.SERVER_URL
							+ C.Service_GetCustomerAddress + "/" + phoneNumber
							+ "/" + "123456", null, handler, true);// 1若数据库没有记录则获取为空2网络连接异常

					if (!TextUtils.isEmpty(result)) {
						handler.sendEmptyMessage(0);
					}// 通知主线程数据库表查询完毕

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}).start();

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// requestCode由startActivityForResult函数确定；resultCode由setResult函数确定。
		switch (resultCode) {
		case 0:// receiver的取消按钮
			mReceivePhoneNumber.setText(data.getStringExtra("PhoneNumber"));
			System.out.println("onActivityResult");
			break;
		case 1:// sender的取消按钮
			mSendPhoneNumber.setText(data.getStringExtra("PhoneNumber"));
			System.out.println("onActivityResult");
			break;
		case 2:
			// 双击后返回得到的寄件人信息
			mSendPhoneNumber.setText(data.getStringExtra("PhoneNumber"));
			mSendName.setText(data.getStringExtra("name"));
			mSendPost.setText(data.getStringExtra("PostNumber"));
			mSendAddress.setText(data.getStringExtra("address"));
			SendAddrID = data.getIntExtra("SendrtAddrID", 0);// 默认是0
			System.out.println("onActivityResult");
			break;
		case 3:
			// 双击后返回得到的收件人信息
			mReceivePhoneNumber.setText(data.getStringExtra("PhoneNumber"));
			mReceiveName.setText(data.getStringExtra("name"));
			mReceivePost.setText(data.getStringExtra("PostNumber"));
			mReceiveAddress.setText(data.getStringExtra("address"));
			ReceiverAddrID = data.getIntExtra("RceciverAddrID", 0);
			System.out.println("onActivityResult");

			break;
		case 4:
			break;
		}

		{

		}

	}

	
	void Init() {
		Thread thread = new Thread(new Runnable() {
			public void run() {
				android.hardware.uhf.magic.reader.init("/dev/ttyMT1");
				android.hardware.uhf.magic.reader.Open("/dev/ttyMT1");
				Log.e("7777777777", "111111111111111111111111111111111111");
				if (reader.SetTransmissionPower(1950) != 0x11) {
					if (reader.SetTransmissionPower(1950) != 0x11) {
						reader.SetTransmissionPower(1950);
					}
				}
			}
		});
		thread.start();
	}

}
