package com.hzgzsoft.logisticsmanage;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by a on 2016/7/20.
 */
public class Edit_activity extends AppCompatActivity implements OnClickListener {

    //收件
    private  EditText mReceiveName;
    private  TextView mReceivePhone;
    private  EditText mReceivePost;
    private  EditText mReceivesheng,mReceiveshi,mReceivexian,mReceivezheng,mReceivecun;
    //寄
    private  EditText mSendName;
    private  TextView mSendPhone;
    private  EditText mSendPost;
    private  EditText mSendsheng,mSendshi,mSendxian,mSendzheng,mSendcun;

    private  Button mSendyes;
    private  Button mReceiveyes;


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String phone = getIntent().getStringExtra("phone");
        String name = getIntent().getStringExtra("name");
        String Province = getIntent().getStringExtra("Province");
        String Area = getIntent().getStringExtra("Area");
        String country = getIntent().getStringExtra("country");
        String Town = getIntent().getStringExtra("Town");
        String Village = getIntent().getStringExtra("Village");
        
        String postnumber = getIntent().getStringExtra("postnumber");
         final  int position = getIntent().getIntExtra("position",0);//没取到为0
        
        
        
        String sp_string = SP_Utils.getSP_String(getApplicationContext(), GlobalConstants.FLAG, "");
        System.out.println("............................"+sp_string);
        if (sp_string.equals("1")) {//1表示寄件人
            setContentView(R.layout.edit_sender);
            SP_Utils.putSP_String(getApplicationContext(), "isEditsender", "yes");
            android.support.v7.app.ActionBar actionBar = this.getSupportActionBar();

            actionBar.setSubtitle("联系电话："+phone.toString());

            actionBar.setTitle("修改寄件人地址");
            actionBar .setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0D9C4A")));

            mSendPhone= (TextView) findViewById(R.id.editsendPhone);
            mSendName= (EditText) findViewById(R.id.editsendName);

            mSendPost= (EditText) findViewById(R.id.editsendPost);
            mSendsheng= (EditText) findViewById(R.id.editsheng);
            mSendshi= (EditText) findViewById(R.id.editshi);
            mSendxian= (EditText) findViewById(R.id.editxian);
            mSendzheng= (EditText) findViewById(R.id.editzheng);
            mSendcun= (EditText) findViewById(R.id.editcun);
       
            mSendyes= (Button) findViewById(R.id.edityes);

            mSendPhone.setText(phone);
            mSendName.setText(name);
            mSendPost.setText(postnumber);
            mSendsheng.setText(Province);
            mSendshi.setText(Area);
            mSendxian.setText(country);
            mSendzheng.setText(Town);
            mSendcun.setText(Village);
           
          
            setClickerlistener();
            mSendyes.setOnTouchListener(new Button.OnTouchListener()
            {
                @Override
                public boolean onTouch(View v, MotionEvent event)
                {
                    if (event.getAction() == MotionEvent.ACTION_DOWN)
                    {
                        //v.setBackgroundResource(R.drawable.btn_down);
                        v.setBackgroundColor(0xff2F679A);
                        ((Button)v).setTextColor(0xffffffff);
                    }
                    else if (event.getAction() == MotionEvent.ACTION_UP)
                    {
                        //v.setBackgroundResource(R.drawable.btn_up);
                        v.setBackgroundColor(0xff4089CC);
                        ((Button)v).setTextColor(0xffE4E4E4);
                    }
                    return false;
                }
            });

            mSendyes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                	String sendphone=mSendPhone.getText().toString();
                    String name = mSendName.getText().toString();
                    String sheng = mSendsheng.getText().toString();
                    String shi = mSendshi.getText().toString();
                    String xian = mSendxian.getText().toString();
                    String zheng = mSendzheng.getText().toString();
                    String cun = mSendcun.getText().toString();
                   
                    String post = mSendPost.getText().toString();
                    if(TextUtils.isEmpty(name))
                    {
                        Animation shake = AnimationUtils.loadAnimation(
                                getApplicationContext(), R.anim.shake);
                        mSendName.startAnimation(shake);
                    }
                    if(TextUtils.isEmpty(post))
                    {
                        Animation shake = AnimationUtils.loadAnimation(
                                getApplicationContext(), R.anim.shake);
                        mSendPost.startAnimation(shake);
                    }
                    if(TextUtils.isEmpty(sheng))
                    {
                        Animation shake = AnimationUtils.loadAnimation(
                                getApplicationContext(), R.anim.shake);
                        mSendsheng.startAnimation(shake);
                    }
                    if(TextUtils.isEmpty(shi))
                    {
                    	Animation shake = AnimationUtils.loadAnimation(
                    			getApplicationContext(), R.anim.shake);
                    	mSendshi.startAnimation(shake);
                    }
                    if(TextUtils.isEmpty(xian))
                    {
                    	Animation shake = AnimationUtils.loadAnimation(
                    			getApplicationContext(), R.anim.shake);
                    	mSendxian.startAnimation(shake);
                    }
                    if(TextUtils.isEmpty(zheng))
                    {
                    	Animation shake = AnimationUtils.loadAnimation(
                    			getApplicationContext(), R.anim.shake);
                    	mSendzheng.startAnimation(shake);
                    }
                    if(TextUtils.isEmpty(cun))
                    {
                    	Animation shake = AnimationUtils.loadAnimation(
                    			getApplicationContext(), R.anim.shake);
                    	mSendcun.startAnimation(shake);
                    }
                    if(!TextUtils.isEmpty(name)&&!TextUtils.isEmpty(post)&&!TextUtils.isEmpty(sheng)&&!TextUtils.isEmpty(shi)&&!TextUtils.isEmpty(xian)&&!TextUtils.isEmpty(zheng)&&!TextUtils.isEmpty(cun)) {
                        Intent intent = new Intent();
                        intent.putExtra("phone", sendphone);
                        intent.putExtra("name",name);
                        intent.putExtra("post",post);
                        intent.putExtra("sheng",sheng);
                        intent.putExtra("shi",shi);
                        intent.putExtra("xian",xian);
                        intent.putExtra("zheng",zheng);
                        intent.putExtra("cun",cun);
                        intent.putExtra("position", position);
                        setResult(6,intent);
                        finish();
                    }
                }
            });


        }
        else if(sp_string.equals("2")){//2表示收件人
            setContentView(R.layout.edit_receiver);
            SP_Utils.putSP_String(getApplicationContext(), "isEditsender", "no");
            android.support.v7.app.ActionBar actionBar = this.getSupportActionBar();
            actionBar.setTitle("修改收件人地址");
            actionBar.setSubtitle("联系电话："+phone.toString());
            actionBar .setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0D3896")));
            mReceivePhone = (TextView) findViewById(R.id.editreceivePhone);
            mReceiveName= (EditText) findViewById(R.id.editreceiveName);

            mReceivePost= (EditText) findViewById(R.id.editreceivePost);
           
            mReceivesheng= (EditText) findViewById(R.id.editsheng);
            mReceiveshi= (EditText) findViewById(R.id.editshi);
            mReceivexian= (EditText) findViewById(R.id.editxian);
            mReceivezheng= (EditText) findViewById(R.id.editzheng);
            mReceivecun= (EditText) findViewById(R.id.editcun);
          //  mReceiveAddress= (EditText) findViewById(R.id.receiveAddress);
            mReceiveyes= (Button) findViewById(R.id.edityesreceiver);
            mReceivePhone.setText(phone);
            mReceiveName.setText(name);
            mReceivesheng.setText(Province);
            mReceivePost.setText(postnumber);
            mReceiveshi.setText(Area);
            mReceivexian.setText(country);
            mReceivezheng.setText(Town);
            mReceivecun.setText(Village);
          
setclicklistener();
            mReceiveyes.setOnTouchListener(new Button.OnTouchListener()
            {
                @Override
                public boolean onTouch(View v, MotionEvent event)
                {
                    if (event.getAction() == MotionEvent.ACTION_DOWN)
                    {
                        //v.setBackgroundResource(R.drawable.btn_down);
                        v.setBackgroundColor(0xff2F679A);
                        ((Button)v).setTextColor(0xffffffff);
                    }
                    else if (event.getAction() == MotionEvent.ACTION_UP)
                    {
                        //v.setBackgroundResource(R.drawable.btn_up);
                        v.setBackgroundColor(0xff4089CC);
                        ((Button)v).setTextColor(0xffE4E4E4);
                    }
                    return false;
                }
            });
            mReceiveyes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                	String receivephone=mReceivePhone.getText().toString();
                    String name = mReceiveName.getText().toString();
                    String sheng = mReceivesheng.getText().toString();
                    String shi = mReceiveshi.getText().toString();
                    String xian = mReceivexian.getText().toString();
                    String zheng = mReceivezheng.getText().toString();
                    String cun = mReceivecun.getText().toString();
                    String post = mReceivePost.getText().toString();
                    
                    if(TextUtils.isEmpty(name))
                    {
                        Animation shake = AnimationUtils.loadAnimation(
                                getApplicationContext(), R.anim.shake);
                        mReceiveName.startAnimation(shake);
                    }
                    if(TextUtils.isEmpty(post))
                    {
                        Animation shake = AnimationUtils.loadAnimation(
                                getApplicationContext(), R.anim.shake);
                        mReceivePost.startAnimation(shake);
                    }
                    if(TextUtils.isEmpty(sheng))
                    {
                        Animation shake = AnimationUtils.loadAnimation(
                                getApplicationContext(), R.anim.shake);
                        mReceivesheng.startAnimation(shake);
                    }
                    if(TextUtils.isEmpty(shi))
                    {
                    	Animation shake = AnimationUtils.loadAnimation(
                    			getApplicationContext(), R.anim.shake);
                    	mReceiveshi.startAnimation(shake);
                    }
                    if(TextUtils.isEmpty(xian))
                    {
                    	Animation shake = AnimationUtils.loadAnimation(
                    			getApplicationContext(), R.anim.shake);
                    	mReceivexian.startAnimation(shake);
                    }
                    if(TextUtils.isEmpty(zheng))
                    {
                    	Animation shake = AnimationUtils.loadAnimation(
                    			getApplicationContext(), R.anim.shake);
                    	mReceivezheng.startAnimation(shake);
                    }
                    if(TextUtils.isEmpty(cun))
                    {
                    	Animation shake = AnimationUtils.loadAnimation(
                    			getApplicationContext(), R.anim.shake);
                    	mReceivecun.startAnimation(shake);
                    }
                    if(!TextUtils.isEmpty(name)&&!TextUtils.isEmpty(post)&&!TextUtils.isEmpty(sheng)&&!TextUtils.isEmpty(shi)&&!TextUtils.isEmpty(xian)&&!TextUtils.isEmpty(zheng)&&!TextUtils.isEmpty(cun)) {
                    	System.out.println("我是edit里的内容");
                        Intent intent = new Intent();
                        intent.putExtra("phone", receivephone);
                        intent.putExtra("name",name);
                        intent.putExtra("post",post);
                        intent.putExtra("sheng",sheng);
                        intent.putExtra("shi",shi);
                        intent.putExtra("xian",xian);
                        intent.putExtra("zheng",zheng);
                        intent.putExtra("cun",cun);
                        intent.putExtra("position", position);
                        setResult(6,intent);
                        finish();
                    }
                }
            });
        }










     










    }
    private void setclicklistener() {
    	mReceivePost.setOnClickListener(this);
    	mReceivesheng.setOnClickListener(this);
    	mReceiveshi.setOnClickListener(this);
    	mReceivexian.setOnClickListener(this);		
	}
	private void setClickerlistener() {
    	mSendsheng.setOnClickListener(this);
		mSendshi.setOnClickListener(this);
		mSendxian.setOnClickListener(this);
		mSendPost.setOnClickListener(this);		
	}
	@Override
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(10,intent);
        super.onBackPressed();
    }
	@Override
	public void onClick(View v) {

		
		Intent intent = new Intent(getApplicationContext(),address_activity.class);
		startActivityForResult(intent, 0);
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		switch (resultCode){
		
		case 1://寄件人地址选择
			
	if(SP_Utils.getSP_String(getApplicationContext(), "isEditsender", "").equals("yes"))
	{
		
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
