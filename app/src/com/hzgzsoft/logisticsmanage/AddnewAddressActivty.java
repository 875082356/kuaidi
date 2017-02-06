package com.hzgzsoft.logisticsmanage;


import javax.security.auth.PrivateCredentialPermission;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



/**
 * Created by a on 2016/7/19.
 */
public class AddnewAddressActivty extends AppCompatActivity implements OnClickListener   {

	String phoneNumber;
	// 收件
	private EditText mReceiveName;
	private EditText mReceivePhone;
	private EditText mReceivePost;

	// 寄
	private EditText mSendName;
	private EditText mSendPhone;
	private EditText mSendPost;

	private EditText mSendsheng,mSendshi,mSendxian,mSendzheng,mSendcun
	,mReceivesheng,mReceiveshi,mReceivexian,mReceivezheng,mReceivecun;

	private Button mSendyes;
	private Button mReceiveyes;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



		/*********************************************************************/
		Intent i = getIntent();
		phoneNumber = i.getStringExtra("PhoneNumber");
		if (i.getStringExtra("tagert").equals("寄件人")) {
			setContentView(R.layout.addnew_sender);

			SP_Utils.putSP_String(getApplicationContext(), "isSender", "是");



			android.support.v7.app.ActionBar actionBar = this
					.getSupportActionBar();





			actionBar.setSubtitle("联系电话：" + phoneNumber.toString());

			actionBar.setTitle("新增寄件人地址");
			actionBar.setBackgroundDrawable(new ColorDrawable(Color
					.parseColor("#0D9C4A")));

			//mSendPhone = (EditText) findViewById(R.id.sendPhone);

			 mSendPhone= (EditText) findViewById(R.id.sendPhone);
			mSendName = (EditText) findViewById(R.id.sendName);
			mSendPost = (EditText) findViewById(R.id.sendPost);
			sethintsize(mSendPost);
			mSendsheng = (EditText) findViewById(R.id.sheng);

		      sethintsize(mSendsheng);


		//	InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		//	imm.hideSoftInputFromWindow(mSendsheng.getWindowToken(), 0);
			mSendsheng.setInputType(0);
			mSendshi = (EditText) findViewById(R.id.shi);
			sethintsize(mSendshi);

			mSendxian = (EditText) findViewById(R.id.xian);

			sethintsize(mSendxian);

			mSendzheng = (EditText) findViewById(R.id.zheng);
			mSendcun = (EditText) findViewById(R.id.cun);


			mSendyes = (Button) findViewById(R.id.yes);
			mSendPhone.setText(phoneNumber);
			setClincklistener();

	mSendzheng.setOnTouchListener(new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {

			mSendzheng.setHint(null);
			return false;
		}
	});
	mSendcun.setOnTouchListener(new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {

			mSendcun.setHint(null);
			return false;
		}
	});
			mSendyes.setOnTouchListener(new Button.OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					if (event.getAction() == MotionEvent.ACTION_DOWN) {
						// v.setBackgroundResource(R.drawable.btn_down);
						v.setBackgroundColor(0xff2F679A);
						((Button) v).setTextColor(0xffffffff);
					} else if (event.getAction() == MotionEvent.ACTION_UP) {
						// v.setBackgroundResource(R.drawable.btn_up);
						v.setBackgroundColor(0xff4089CC);
						((Button) v).setTextColor(0xffE4E4E4);
					}
					return false;
				}
			});

			mSendyes.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					String phone=mSendPhone.getText().toString();
					String name = mSendName.getText().toString();
					String sheng = mSendsheng.getText().toString();
					String shi = mSendshi.getText().toString();
					String xian = mSendxian.getText().toString();
					String zheng = mSendzheng.getText().toString();
					String cun = mSendcun.getText().toString();

					String post = mSendPost.getText().toString();
					if (TextUtils.isEmpty(phone)) {
						Animation shake = AnimationUtils.loadAnimation(
								getApplicationContext(), R.anim.shake);
						mSendPhone.startAnimation(shake);
					}
					if (TextUtils.isEmpty(name)) {
						Animation shake = AnimationUtils.loadAnimation(
								getApplicationContext(), R.anim.shake);
						mSendName.startAnimation(shake);
					}
					if (TextUtils.isEmpty(post)) {
						Animation shake = AnimationUtils.loadAnimation(
								getApplicationContext(), R.anim.shake);
						mSendPost.startAnimation(shake);
					}
					if (TextUtils.isEmpty(sheng)) {
						Animation shake = AnimationUtils.loadAnimation(
								getApplicationContext(), R.anim.shake);
						mSendsheng.startAnimation(shake);
					}
					if (TextUtils.isEmpty(shi)) {
						Animation shake = AnimationUtils.loadAnimation(
								getApplicationContext(), R.anim.shake);
						mSendshi.startAnimation(shake);
					}
					if (TextUtils.isEmpty(xian)) {
						Animation shake = AnimationUtils.loadAnimation(
								getApplicationContext(), R.anim.shake);
						mSendxian.startAnimation(shake);
					}
					if (TextUtils.isEmpty(zheng)) {
						Animation shake = AnimationUtils.loadAnimation(
								getApplicationContext(), R.anim.shake);
						mSendzheng.startAnimation(shake);
					}
					if (TextUtils.isEmpty(cun)) {
						Animation shake = AnimationUtils.loadAnimation(
								getApplicationContext(), R.anim.shake);
						mSendcun.startAnimation(shake);
					}
					if (!TextUtils.isEmpty(phone)&&!TextUtils.isEmpty(name) && !TextUtils.isEmpty(post)
							&& !TextUtils.isEmpty(sheng)&& !TextUtils.isEmpty(shi)&& !TextUtils.isEmpty(xian)&& !TextUtils.isEmpty(zheng)
							&& !TextUtils.isEmpty(cun)) {
						Intent intent = new Intent();
						intent.putExtra("phone", phone);
						intent.putExtra("name", name);
						intent.putExtra("post", post);
						intent.putExtra("sheng", sheng);
						intent.putExtra("shi", shi);
						intent.putExtra("xian", xian);
						intent.putExtra("zheng", zheng);
						intent.putExtra("cun", cun);
						setResult(2, intent);
						finish();
					}
				}
			});

		} else {
			setContentView(R.layout.addnew_receiver);
			SP_Utils.putSP_String(getApplicationContext(), "isSender", "否");
			android.support.v7.app.ActionBar actionBar = this
					.getSupportActionBar();
			actionBar.setTitle("新增收件人地址");
			actionBar.setSubtitle("联系电话：" + phoneNumber.toString());
			actionBar.setBackgroundDrawable(new ColorDrawable(Color
					.parseColor("#0D3896")));
			mReceivePhone = (EditText) findViewById(R.id.receivePhone);
			mReceiveName = (EditText) findViewById(R.id.receiveName);

			mReceivePost = (EditText) findViewById(R.id.receivePost);
			sethintsize(mReceivePost);
			mReceivesheng = (EditText) findViewById(R.id.sheng1);
			sethintsize(mReceivesheng);
			mReceiveshi = (EditText) findViewById(R.id.shi1);
			sethintsize(mReceiveshi);
			mReceivexian = (EditText) findViewById(R.id.xian1);
			sethintsize(mReceivexian);
			mReceivezheng = (EditText) findViewById(R.id.zheng1);
			mReceivecun = (EditText) findViewById(R.id.cun1);
			mReceiveyes = (Button) findViewById(R.id.yes);
			mReceivePhone.setText(phoneNumber);
           setclicklisetner();

       	mReceivezheng.setOnTouchListener(new OnTouchListener() {

    		@Override
    		public boolean onTouch(View v, MotionEvent event) {

    			mReceivezheng.setHint(null);
    			return false;
    		}
    	});
    	mReceivecun.setOnTouchListener(new OnTouchListener() {

    		@Override
    		public boolean onTouch(View v, MotionEvent event) {

    			mReceivecun.setHint(null);
    			return false;
    		}
    	});

			mReceiveyes.setOnTouchListener(new Button.OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					if (event.getAction() == MotionEvent.ACTION_DOWN) {
						// v.setBackgroundResource(R.drawable.btn_down);
						v.setBackgroundColor(0xff2F679A);
						((Button) v).setTextColor(0xffffffff);
					} else if (event.getAction() == MotionEvent.ACTION_UP) {
						// v.setBackgroundResource(R.drawable.btn_up);
						v.setBackgroundColor(0xff4089CC);
						((Button) v).setTextColor(0xffE4E4E4);
					}
					return false;
				}
			});
			mReceiveyes.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					String name = mReceiveName.getText().toString();

					String post = mReceivePost.getText().toString();

					String receivephone=mReceivePhone.getText().toString();
					String sheng = mReceivesheng.getText().toString();
					String shi = mReceiveshi.getText().toString();
					String xian = mReceivexian.getText().toString();
					String zheng = mReceivezheng.getText().toString();
					String cun = mReceivecun.getText().toString();
					if (TextUtils.isEmpty(receivephone)) {
						Animation shake = AnimationUtils.loadAnimation(
								getApplicationContext(), R.anim.shake);
						mReceivePhone.startAnimation(shake);
					}
					if (TextUtils.isEmpty(name)) {
						Animation shake = AnimationUtils.loadAnimation(
								getApplicationContext(), R.anim.shake);
						mReceiveName.startAnimation(shake);
					}
					if (TextUtils.isEmpty(post)) {
						Animation shake = AnimationUtils.loadAnimation(
								getApplicationContext(), R.anim.shake);
						mReceivePost.startAnimation(shake);
					}
					if (TextUtils.isEmpty(sheng)) {
						Animation shake = AnimationUtils.loadAnimation(
								getApplicationContext(), R.anim.shake);
						mReceivesheng.startAnimation(shake);
					}
					if (TextUtils.isEmpty(shi)) {
						Animation shake = AnimationUtils.loadAnimation(
								getApplicationContext(), R.anim.shake);
						mReceiveshi.startAnimation(shake);
					}
					if (TextUtils.isEmpty(xian)) {
						Animation shake = AnimationUtils.loadAnimation(
								getApplicationContext(), R.anim.shake);
						mReceivexian.startAnimation(shake);
					}
					if (TextUtils.isEmpty(zheng)) {
						Animation shake = AnimationUtils.loadAnimation(
								getApplicationContext(), R.anim.shake);
						mReceivezheng.startAnimation(shake);
					}
					if (TextUtils.isEmpty(cun)) {
						Animation shake = AnimationUtils.loadAnimation(
								getApplicationContext(), R.anim.shake);
						mReceivecun.startAnimation(shake);
					}
					if (!TextUtils.isEmpty(receivephone)&&!TextUtils.isEmpty(name) && !TextUtils.isEmpty(post)
							&& !TextUtils.isEmpty(sheng)&& !TextUtils.isEmpty(shi)&& !TextUtils.isEmpty(xian)&& !TextUtils.isEmpty(zheng)&& !TextUtils.isEmpty(cun)) {
						Intent intent = new Intent();
						intent.putExtra("phone", receivephone);
						intent.putExtra("name", name);
						intent.putExtra("post", post);
						intent.putExtra("sheng", sheng);
						intent.putExtra("shi", shi);
						intent.putExtra("xian", xian);
						intent.putExtra("zheng", zheng);
						intent.putExtra("cun", cun);
						setResult(2, intent);
						finish();
					}
				}
			});
		}




	}

private void setclicklisetner() {

	mReceivePost.setOnClickListener(this);
	mReceivesheng.setOnClickListener(this);
	mReceiveshi.setOnClickListener(this);
	mReceivexian.setOnClickListener(this);


	}

private void setClincklistener() {
		mSendsheng.setOnClickListener(this);
		mSendshi.setOnClickListener(this);
		mSendxian.setOnClickListener(this);
		mSendPost.setOnClickListener(this);
		//mSendzheng.setOnClickListener(this);
		//mSendcun.setOnClickListener(this);



	}

	private void sethintsize(EditText  editText ) {
		editText.setFocusable(false);//失去焦点，然后获取点击事件
        SpannableString ss = new SpannableString("请选择");
		// // 新建一个属性对象,设置文字的大小
		AbsoluteSizeSpan ass = new AbsoluteSizeSpan(13, true);

		// 附加属性到文本
		ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

		// 设置hint
		editText.setHint(new SpannedString(ss)); // 一定要进行转换,否则属性会消失

	}

	@Override
	public void onBackPressed() {
		Intent intent = new Intent();
		setResult(4, intent);

		super.onBackPressed();
	}

@Override
	public void onClick(View v) {
	/*switch (v.getId()) {
	case R.id.zheng:
		mSendzheng.setHint(null);
		
		break;
	case R.id.cun:
		mSendcun.setHint(null);
		break;
	case R.id.zheng1:
		mReceivezheng.setHint(null);
		break;
	case R.id.cun1:
		mReceivecun.setHint(null);
		break;

	default:*/
		Intent intent = new Intent(getApplicationContext(),address_activity.class);
		startActivityForResult(intent, 0);
	//	break;
	//}







	}
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	super.onActivityResult(requestCode, resultCode, data);

	switch (resultCode){

	case 1://寄件人地址选择

if(SP_Utils.getSP_String(getApplicationContext(), "isSender", "").equals("是"))
{
		//System.out.println("dsfdsssssssssssssssssssssssssssssssssssssssssss");
		mSendsheng.setText(data.getStringExtra("mCurrentProviceName"));
		mSendPost.setText(data.getStringExtra("mCurrentZipCode"));
		mSendshi.setText(data.getStringExtra("mCurrentCityName"));
		mSendxian.setText(data.getStringExtra("mCurrentDistrictName"));
}
else{

	mReceivesheng.setText(data.getStringExtra("mCurrentProviceName"));
	mReceivePost.setText(data.getStringExtra("mCurrentZipCode"));
	mReceiveshi.setText(data.getStringExtra("mCurrentCityName"));
	mReceivexian.setText(data.getStringExtra("mCurrentDistrictName"));

}
	break;

	}


}
}
