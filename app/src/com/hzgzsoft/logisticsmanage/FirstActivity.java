package com.hzgzsoft.logisticsmanage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import java.util.ArrayList;

import four_pages.dingdan;
import four_pages.huodong;
import four_pages.wode;
import four_pages.xiadan;
import view.NoScrollViewPager;

/**
 * 主页面
 * 
 * @author
 * @date
 */
public class FirstActivity extends Activity {
	private Activity mActivity;
	private NoScrollViewPager mViewPager;
	private RadioGroup rgGroup;

	private ArrayList<BasePager> mPagers;// 4个标签页的集合

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题,
		// 必须在setContentView之前调用
		setContentView(R.layout.main_content);
		mActivity = FirstActivity.this;
		initView();
		initData();
	}

	private void initView() {
		mViewPager = (NoScrollViewPager) findViewById(R.id.vp_content);
		rgGroup = (RadioGroup) findViewById(R.id.rg_group);

	}

	private void initData() {
		mPagers = new ArrayList<BasePager>();

		// 添加五个标签页
		mPagers.add(new xiadan(mActivity));
		mPagers.add(new huodong(mActivity));
		mPagers.add(new dingdan(mActivity));
		mPagers.add(new wode(mActivity));

		mViewPager.setAdapter(new ContentAdapter());
		rgGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {

				switch (checkedId) {
				case R.id.rb_xiadan:
					mViewPager.setCurrentItem(0, false);
					//
					break;
				case R.id.rb_huodong:
					mViewPager.setCurrentItem(1, false);
					break;
				case R.id.rb_dingdan:
					mViewPager.setCurrentItem(2, false);
					break;
				case R.id.rb_wode:
					mViewPager.setCurrentItem(3, false);
					break;

				default:
					break;
				}

			}
		});

		mViewPager.addOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {

				
				mPagers.get(position).initData();
			if(position==0)
				{
				mPagers.get(0).mSendPhoneNumber.removeTextChangedListener(mPagers.get(0).textwatcher1);
				mPagers.get(0).mReceivePhoneNumber.removeTextChangedListener(mPagers.get(0).textwatcher2);
					getdata();
                    
					
					
				}
			mPagers.get(0).mSendPhoneNumber.addTextChangedListener(mPagers.get(0).textwatcher1);
			mPagers.get(0).mReceivePhoneNumber.addTextChangedListener(mPagers.get(0).textwatcher2);
			
				
				System.out.println("选择第"+position+"个pages");
				
				
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

				
				//System.out.println("onPageScrolled:"+arg0+"  "+arg1+"  "+arg2);
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
		mPagers.get(0).initData();//一进来就加载第一页数据
		System.out.println("一进来就加载第一页数据");
	}

	public void put_Senderdata() {
		// 将数据存储

		SP_Utils.putSP_String(mActivity, "mSendPhoneNumber",
				mPagers.get(0).mSendPhoneNumber.getText().toString());

		SP_Utils.putSP_String(mActivity, "mSendName", mPagers.get(0).mSendName
				.getText().toString());

		SP_Utils.putSP_String(mActivity, "mSendPost", mPagers.get(0).mSendPost
				.getText().toString());

		SP_Utils.putSP_String(mActivity, "mSendAddress",
				mPagers.get(0).mSendAddress.getText().toString());

	}
	private void put_Receiverdata() {

		SP_Utils.putSP_String(mActivity, "mReceivePhoneNumber",
				mPagers.get(0).mReceivePhoneNumber.getText().toString());

		SP_Utils.putSP_String(mActivity, "mReceiveName", mPagers.get(0).mReceiveName
				.getText().toString());

		SP_Utils.putSP_String(mActivity, "mReceivePost", mPagers.get(0).mReceivePost
				.getText().toString());

		SP_Utils.putSP_String(mActivity, "mReceiveAddress",
				mPagers.get(0).mReceiveAddress.getText().toString());
		
		
	}

	protected void getdata() {

		mPagers.get(0).rFID.setText(SP_Utils.getSP_String(mActivity, "rfid", ""));
		mPagers.get(0).mSendPhoneNumber.setText(SP_Utils.getSP_String(mActivity, "mSendPhoneNumber", ""));
		mPagers.get(0).mSendName.setText(SP_Utils.getSP_String(mActivity, "mSendName", ""));
		mPagers.get(0).mSendPost.setText(SP_Utils.getSP_String(mActivity, "mSendPost", ""));
		mPagers.get(0).mSendAddress.setText(SP_Utils.getSP_String(mActivity, "mSendAddress", ""));
		
		
		mPagers.get(0).mReceivePhoneNumber.setText(SP_Utils.getSP_String(mActivity, "mReceivePhoneNumber", ""));
		mPagers.get(0).mReceiveName.setText(SP_Utils.getSP_String(mActivity, "mReceiveName", ""));
		mPagers.get(0).mReceivePost.setText(SP_Utils.getSP_String(mActivity, "mReceivePost", ""));
		mPagers.get(0).mReceiveAddress.setText(SP_Utils.getSP_String(mActivity, "mReceiveAddress", ""));
		
		
		
	}

	class ContentAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return mPagers.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {

			View view = mPagers.get(position).mRootView;
			//mPagers.get(position).initData();
			container.addView(view);
			return view;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			
			container.removeView((View) object);
           
            Log.e("TAG", "destroyItem: "+position );
        }

	}
	@Override
	public void onBackPressed() {
		SP_Utils.Delete_String(mActivity);
		super.onBackPressed();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// requestCode由startActivityForResult函数确定；resultCode由setResult函数确定。
		switch (resultCode) {
		case 0:// receiver的取消按钮
			
			mPagers.get(0).mReceivePhoneNumber.setText(data.getStringExtra("PhoneNumber"));
			
			System.out.println("onActivityResult");
			break;
		case 1:// sender的取消按钮
			mPagers.get(0).mSendPhoneNumber.setText(data.getStringExtra("PhoneNumber"));
			System.out.println("onActivityResult");
			break;
		case 2:
			// 双击后返回得到的寄件人信息
			mPagers.get(0).mSendPhoneNumber.setText(data.getStringExtra("PhoneNumber"));
			mPagers.get(0).mSendName.setText(data.getStringExtra("name"));
			mPagers.get(0).mSendPost.setText(data.getStringExtra("PostNumber"));
			mPagers.get(0).mSendAddress.setText(data.getStringExtra("address"));
			mPagers.get(0).SendAddrID = data.getIntExtra("SendrtAddrID", 0);// 默认是0
			put_Senderdata();
			System.out.println("双击后返回得到的寄件人信息");
			break;
		case 3:
			// 双击后返回得到的收件人信息
			mPagers.get(0).mReceivePhoneNumber.setText(data.getStringExtra("PhoneNumber"));
			mPagers.get(0).mReceiveName.setText(data.getStringExtra("name"));
			mPagers.get(0).mReceivePost.setText(data.getStringExtra("PostNumber"));
			mPagers.get(0).mReceiveAddress.setText(data.getStringExtra("address"));
			mPagers.get(0).ReceiverAddrID = data.getIntExtra("RceciverAddrID", 0);
			put_Receiverdata();
			System.out.println("双击后返回得到的收件人信息");

			break;
		case 4:
			break;
		}

		{

		}

	}




}
