package com.hzgzsoft.logisticsmanage;

import android.app.Activity;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;



/**
 * 五个标签页的基类
 * 
 * @author 
 * @date 
 */
public class BasePager {
	//................................................................
	public  TextWatcher textwatcher1,textwatcher2;
	public TextView rFID;
	public TextView mSendName, mSendPost, mSendAddress, mReceiveName,
	mReceivePost, mReceiveAddress;
	public EditText mSendPhoneNumber, mReceivePhoneNumber;
	public int SendAddrID;
	public int ReceiverAddrID;
	//...................................................
	public Activity mActivity;

	public FrameLayout flContent;// 空的帧布局对象, 要动态添加布局
	
	

	public View mRootView;// 当前页面的布局对象

	public BasePager(Activity activity) {
		mActivity = activity;
		mRootView = initView();
	}

	// 初始化布局
	public View initView() {
		View view = View.inflate(mActivity, R.layout.base_pager, null);
		flContent=(FrameLayout) view.findViewById(R.id.fl_content);


	

		return view;
	}



	// 初始化数据
	public void initData() {

	}
}
