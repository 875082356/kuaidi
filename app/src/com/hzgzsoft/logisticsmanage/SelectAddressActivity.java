package com.hzgzsoft.logisticsmanage;

import java.util.ArrayList;
import java.util.List;

import JavaBeen.CustomerAddress;
import JavaBeen.CustomerAddressResult;
import JavaBeen.CustomerAddressResult.AddressData;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.style.UpdateAppearance;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fortysevendeg.swipelistview.BaseSwipeListViewListener;
import com.fortysevendeg.swipelistview.SwipeListView;
import com.google.gson.Gson;




public class SelectAddressActivity extends AppCompatActivity
{
	private android.support.v7.app.ActionBar actionBar;
	private String phoneString="";//新增按钮后退后得到的电话参数
	private CustomerAddressResult addressResult;  
	private boolean flag=false;
	private int updataweizhi;
private int shanchuweizhi;
   
     private  String mTemp="";
     private String result="";
    private String phoneNumber;
    private TextView tv;
   private WebClient client ;
    private SwipeListView mSwipeListView ;
    private SwipeAdapter mAdapter ;
    public static int deviceWidth ;
  
    private  final static  String SENDER="sender";
    private  final static  String RECEIVER="receiver";
   private Button mAddnew;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {


    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_select_address);
     client = new WebClient(handler);
    mAddnew= (Button) findViewById(R.id.addNew);
    //获取传递过来的电话号码
    Intent i=getIntent();
    phoneNumber=i.getStringExtra("PhoneNumber");



    mAddnew.setOnClickListener(new View.OnClickListener() {//新增按钮
        @Override
        public void onClick(View view) {
            if(mTemp.equals(SENDER)) {
            	
                Intent intent = new Intent(getApplicationContext(), AddnewAddressActivty.class);
                intent.putExtra("tagert","寄件人");
                intent.putExtra("PhoneNumber",phoneNumber);
                startActivityForResult(intent, 0);
               
            }
            else if(mTemp.equals(RECEIVER)){
            	
                Intent intent = new Intent(getApplicationContext(), AddnewAddressActivty.class);
                intent.putExtra("tagert","收件人");
                intent.putExtra("PhoneNumber",phoneNumber);
                startActivityForResult(intent, 0);
            }
        }
    });
    mAddnew.setOnTouchListener(new Button.OnTouchListener()//新增按钮状态选择器
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
    actionBar = this.getSupportActionBar();
    actionBar.setSubtitle("联系电话："+phoneNumber.toString());
    if(i.getStringExtra("Target").equals("寄件人"))
    {
        actionBar.setTitle("寄件人地址列表");
        actionBar .setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0D9C4A")));
            SP_Utils.putSP_String(getApplicationContext(),GlobalConstants.FLAG,"1");
        System.out.println("zzzzzzzzzzzzzzzzzzzzzzzzzzzzz");


        mTemp=SENDER;
        System.out.println("mtemp"+mTemp);
    }
    else
    {
        actionBar.setTitle("收件人地址列表");
        actionBar .setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0D3896")));
        SP_Utils.putSP_String(getApplicationContext(),GlobalConstants.FLAG,"2");
        mTemp=RECEIVER;
    }


    /////绑定并显示列表
    mSwipeListView = (SwipeListView) findViewById(R.id.example_lv_list);
   
 //   Toast.makeText(getApplicationContext(), "联网失败，请检查网络", 0).show();
     getData();//联网获取数据通过handler发送*****************************************************************************************
  



    //返回按钮(取消按钮)
    findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            if(mTemp.equals(SENDER)) {
                Intent i = new Intent();
                i.putExtra("PhoneNumber", phoneNumber.toString());
                setResult(1, i);
                finish();

            }
            else if(mTemp.equals(RECEIVER)) {
                Intent i = new Intent();
                i.putExtra("PhoneNumber", phoneNumber.toString());
                setResult(0, i);
                finish();
            }


        }
    });
    //改变返回按钮颜色
    findViewById(R.id.cancel).setOnTouchListener(new Button.OnTouchListener()
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
}

    
    
     
    public void jiexi_json(String result){
    	
    	Gson gson = new Gson();
		  addressResult = gson.fromJson(result, CustomerAddressResult.class);
		  System.out.println("json解析结果"+addressResult);
		  
		  if(addressResult!=null)
		  { //数据适配
		      mAdapter = new SwipeAdapter(phoneNumber,this, R.layout.list_item, addressResult.GetCustomerAddressResult,mSwipeListView);
		      mSwipeListView.setAdapter(mAdapter);
		  }
		      //拿到设备宽度
		      deviceWidth = getResources().getDisplayMetrics().widthPixels;
		      
		      //设置事件监听
		      mSwipeListView.setSwipeListViewListener( new TestBaseSwipeListViewListener());
		      reload();
		  

    	
    	
    }
    
   private CustomerAddressResult.AddressData addressData1,addressData2;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);//后退按钮时把上个activity的参数回传过来
        if(resultCode==4)
        {
            System.out.println("resultCode"+resultCode);
        }
        if(resultCode==6)
        {//编辑按钮后退时获得的参数
        	
        	System.out.println("resultCode"+resultCode);
        	
/*       	AddressMessage addressMessage = new AddressMessage();
        	addressMessage.setName(data.getStringExtra("name"));
            addressMessage.setPostNumber(data.getStringExtra("post"));
            addressMessage.setAdrs(data.getStringExtra("address"));*/
        	int addrID = addressResult.GetCustomerAddressResult.get(data.getIntExtra("position", 0)).AddrID;
         //  addressResult.GetCustomerAddressResult.remove(data.getIntExtra("position", 0));//默认的是位置零
          // addressResult.GetCustomerAddressResult.add(data.getIntExtra("position", 0), addressMessage);
      //     addressResult.GetCustomerAddressResult.add(data.getIntExtra("position", 0), addressMessage);
            //addressData.remove(data.getIntExtra("position", 0));
          //  addressData.add(data.getIntExtra("position", 0), addressMessage);
            System.out.println("addrID:"+addrID);
            addressData1= new CustomerAddressResult().new AddressData();
           addressData1.Name=data.getStringExtra("name");
           addressData1.Postcode=data.getStringExtra("post");
           addressData1.Province=data.getStringExtra("sheng");
           addressData1.Area=data.getStringExtra("shi");
           addressData1.County=data.getStringExtra("xian");
           addressData1.Town=data.getStringExtra("zheng");
           addressData1.Village=data.getStringExtra("cun");
           addressData1.AddrID=addrID;
          updataweizhi= data.getIntExtra("position", 0);
          System.out.println("updataweizhi。。。"+updataweizhi);
  /*         addressResult.GetCustomerAddressResult.add(data.getIntExtra("position", 0), addressData1);
           
           mAdapter.notifyDataSetChanged();*/
      
           
           final String time = C.getTime();
           CustomerAddress customerAddress = new CustomerAddress();//数据放到数据库中的
		
			customerAddress.setAccountName("1111");
			customerAddress.setPhoneNumber(data.getStringExtra("phone"));
			customerAddress.setName(data.getStringExtra("name"));
			customerAddress.setPostcode(data.getStringExtra("post"));
			customerAddress.setProvince(data.getStringExtra("sheng"));
			customerAddress.setArea(data.getStringExtra("shi"));
			customerAddress.setCounty(data.getStringExtra("xian"));
			customerAddress.setTown(data.getStringExtra("zheng"));
			customerAddress.setVillage(data.getStringExtra("cun"));
			customerAddress.setAddrID(addrID);
			customerAddress.setOrderID(time);	
			
           UpdateAddress(customerAddress,"123456");
        	
        	
        }
        if(resultCode==10)
        {
        	
        }
        if(resultCode==2) {
        	//新增地址后退后得到的数据*******************************
            System.out.println("resultCode"+resultCode);
            phoneString = data.getStringExtra("phone");
         
            
            
            
             addressData2= new CustomerAddressResult().new AddressData();
            addressData2.Name=data.getStringExtra("name");
            addressData2.Postcode=data.getStringExtra("post");
            addressData2.Province=data.getStringExtra("sheng");
            addressData2.Area=data.getStringExtra("shi");
            addressData2.County=data.getStringExtra("xian");
            addressData2.Town=data.getStringExtra("zheng");
            addressData2.Village=data.getStringExtra("cun");
            System.out.println("flag:"+flag);
          /*  if(addressResult==null&&!flag)//flag=false
            {
            	Toast.makeText(getApplicationContext(), "网络未连接，新增地址失败", 0).show();
            }*/
           {
        /*    addressResult.GetCustomerAddressResult.add(0, addressData2);
            mAdapter.notifyDataSetChanged();*/
            
            // 新增加地址
            
            
            
            final String time = C.getTime();
            CustomerAddress customerAddress = new CustomerAddress();
 			//customerAddress.setAddrID(21);
 			customerAddress.setAccountName("1111");
 			customerAddress.setPhoneNumber(data.getStringExtra("phone"));
 			System.out.println("phone"+data.getStringExtra("phone"));
 			customerAddress.setName(data.getStringExtra("name"));
 			customerAddress.setPostcode(data.getStringExtra("post"));
 			customerAddress.setProvince(data.getStringExtra("sheng"));
 			customerAddress.setArea(data.getStringExtra("shi"));
 			customerAddress.setCounty(data.getStringExtra("xian"));
 			customerAddress.setTown(data.getStringExtra("zheng"));
 			customerAddress.setVillage(data.getStringExtra("cun"));
 			customerAddress.setOrderID(time);	
 		
            AddAddress(customerAddress,"123456");
            }
            
            
            
            
        }


    }

    private void AddAddress(final CustomerAddress customerAddress, String string) {//新增地址
		
    	new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				
				String result1=client.doPost(C.SERVER_URL+C.Service_AddCustomerAddress, customerAddress, "application/json");
				System.out.println("新增地址时的result:  "+result1);
				if(result1.contains("true"))//增加地址成功
				{
					handler.sendEmptyMessage(2);
				}
				else if(result1.contains("false"))
				{
					handler.sendEmptyMessage(3);
				}
				
			}
		}).start();
    	
    	
    	
    	
	}


	private void UpdateAddress(final CustomerAddress customerAddress, String string) {//更新一条数据

    	
    	new Thread(new Runnable() {
			
			@Override
			public void run() {
				
			String result=client.doPost(C.SERVER_URL+C.Service_UpdateCustomerAddress, customerAddress, "application/json");
			
			if(result.contains("true"))
			{
				
				handler.sendEmptyMessage(8);
			}
			else if(result.contains("false"))
			{
				handler.sendEmptyMessage(9);
			}
				
				
			}
		}).start();
    	
    	
    	
	}


	@Override
    public void onBackPressed() {


       if(mTemp.equals(SENDER)) {
            Intent i = new Intent();
            i.putExtra("PhoneNumber", phoneNumber.toString());
            setResult(1, i);

        }
        else if(mTemp.equals(RECEIVER)) {
            Intent i = new Intent();
            i.putExtra("PhoneNumber", phoneNumber.toString());
            setResult(0, i);


    }
       super.onBackPressed();
    }


    //用来接收http传回的数据
private Handler handler=new Handler(){
	public void handleMessage(Message msg) {
		
		switch (msg.what) {
		case 0:

			if(result.length()!=31){
				flag=false;
				System.out.println("result....................."+result);
					jiexi_json(result);
			
				
				
			}
			else{
				
				Toast.makeText(getApplicationContext(), "该号码数据库没有记录,请新增", 0).show();
				flag=true;
			}
		
			break;
		case 1:
			Toast.makeText(getApplicationContext(), "联网失败，请检查网络", 0).show();
			break;
		case 2:
			
			
			if(!flag)
			{//表示第一次进来获取过数据listview已经显示过来
				
	            if(phoneString.equals(phoneNumber))
	            {
	            	 //判断原来的电话是否跟返回后得到的电话相同
	          System.out.println("重置返回后是否放数据到listview的第一个"); 
		    addressResult.GetCustomerAddressResult.add(0, addressData2);
            mAdapter.notifyDataSetChanged();
            getData();//重新拿到AddrID
	            }
	            else {
					//两个电话号码不同
	            	addressResult.GetCustomerAddressResult.clear();
	            	 addressResult.GetCustomerAddressResult.add(0, addressData2);
	            	 mAdapter.notifyDataSetChanged();
	            	 //更新actionbar中的联系电话
	            	 
	            	  actionBar.setSubtitle("联系电话："+phoneString.toString());
	            	  phoneNumber=phoneString;
	            	  getData();//重新拿到AddrID
				}
			}
			else{
				//第一次未获取数据（只增加一条数据）(此时数据库中已经增加了一条数据，再次请求网络查询数据库)
				//TODO
			getData();  
				
			}
			Toast.makeText(getApplicationContext(), "增加地址成功", 0).show();
			break;
		case 3:
			Toast.makeText(getApplicationContext(), "增加地址失败，请重试", 0).show();
			break;
		case 4:
			
			addressResult.GetCustomerAddressResult.remove(shanchuweizhi);
			 mAdapter.notifyDataSetChanged();
			System.out.println("shanchuweizhi"+shanchuweizhi);
			Toast.makeText(getApplicationContext(), "删除地址成功", 0).show();
			break;
		case 5:
			Toast.makeText(getApplicationContext(), "删除地址失败，请重试", 0).show();
			break;
		case 6:
			Toast.makeText(getApplicationContext(), "访问网络失败,请重试", 0).show();
			flag=true;
			break;
		case 7:
			Toast.makeText(getApplicationContext(), "访问网络失败无法删除地址", 0).show();
			break;
		case 8:
			
			 addressResult.GetCustomerAddressResult.remove(updataweizhi);//默认的是位置零
	         addressResult.GetCustomerAddressResult.add(updataweizhi, addressData1);
	           
	           mAdapter.notifyDataSetChanged();
	           Toast.makeText(getApplicationContext(), "更新地址成功", 0).show();
			break;
		case 9:
			Toast.makeText(getApplicationContext(), "更新地址失败，请重试", 0).show();
			break;
		case 10:
			//Toast.makeText(getApplicationContext(), "更新地址成功", 0).show();
			break;
		case 11:
			//Toast.makeText(getApplicationContext(), "更新地址成功", 0).show();
			break;
		case 12:
			//Toast.makeText(getApplicationContext(), "更新地址成功", 0).show();
			break;
		case 13:
			//Toast.makeText(getApplicationContext(), "更新地址成功", 0).show();
			break;
		case 14:
			//Toast.makeText(getApplicationContext(), "更新地址成功", 0).show();
			break;

		default:
			break;
		}
		
		
		
		
		
		
	}
};
	
	
	


    public void getData() 
    {
if(!TextUtils.isEmpty(phoneString))
{
	//说明两个号码不一致了
	phoneNumber=phoneString;
}
        // 开启一个子线程，进行网络操作，等待有返回结果，使用handler通知UI
      //  new Thread(networkTask).start();
//13588228755
new Thread( new Runnable() {
	

	public void run() {
		
		
		try {
			
			
			
			result = client.doGet(C.SERVER_URL+C.Service_GetCustomerAddress+"/"+phoneNumber+"/"+"123456");//1若数据库没有记录则获取为空2网络连接异常
			
			if(!TextUtils.isEmpty(result))
			{handler.sendEmptyMessage(0);}//通知主线程数据库表查询完毕
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
				
	}
}).start();
    	
    
    }

    private void reload() {
//		mSwipeListView.setSwipeMode(SwipeListView.SWIPE_MODE_LEFT);
//		mSwipeListView.setSwipeActionLeft(SwipeListView.SWIPE_ACTION_REVEAL);
//		mSwipeListView.setSwipeActionRight(settings.getSwipeActionRight());
        //滑动时向左偏移量，根据设备的大小来决定偏移量的大小
        mSwipeListView.setOffsetLeft(deviceWidth * 1 / 3);
        mSwipeListView.setOffsetRight(deviceWidth * 1 / 3);
//		mSwipeListView.setOffsetRight(convertDpToPixel(settings.getSwipeOffsetRight()));
        //设置动画时间
        mSwipeListView.setAnimationTime(30);
        mSwipeListView.setSwipeOpenOnLongPress(false);
    }

    class TestBaseSwipeListViewListener extends BaseSwipeListViewListener
    {
        private int clickCount;
        private long preClickTime;


        //双击每一项的响应事件
        @Override
        public void onClickFrontView(int position)
        {
            super.onClickFrontView(position);
            if (clickCount == 0)
            {
                preClickTime = System.currentTimeMillis();
                clickCount++;
            }
            else if (clickCount == 1)
            {
                long curTime = System.currentTimeMillis();
                if((curTime - preClickTime) < 500)
                {
                    doubleClick(position);
                    clickCount = 0;
                    preClickTime = 0;
                }
                else
                {
                    clickCount = 0;
                    preClickTime = System.currentTimeMillis();
                    clickCount++;
                }

            }
            else
            {
                clickCount = 0;
                preClickTime = 0;
            }
        }

        //处理双击事件
        private void doubleClick(int position)
        {
            Toast.makeText(getApplicationContext(), "你双击了第"+position+"条记录", Toast.LENGTH_SHORT).show();
                          Intent intent=new Intent();
            //先判断是寄件人还是收件人
            if(mTemp.equals(SENDER)) {
                Intent intent1 = new Intent();
                intent1.putExtra("name", addressResult.GetCustomerAddressResult.get(position).Name);
                intent1.putExtra("PhoneNumber",phoneNumber.toString());
                intent1.putExtra("PostNumber", addressResult.GetCustomerAddressResult.get(position).Postcode);
                intent1.putExtra("address", addressResult.GetCustomerAddressResult.get(position).Province+
                		addressResult.GetCustomerAddressResult.get(position).Area+
                		addressResult.GetCustomerAddressResult.get(position).County+
                		addressResult.GetCustomerAddressResult.get(position).Town+
                		addressResult.GetCustomerAddressResult.get(position).Village);
                intent1.putExtra("SendrtAddrID", addressResult.GetCustomerAddressResult.get(position).AddrID);
                setResult(2, intent1);//requestCode=2
                finish();
            }
            else if(mTemp.equals(RECEIVER)){
                Intent intent1 = new Intent();
                intent1.putExtra("name", addressResult.GetCustomerAddressResult.get(position).Name);
                intent1.putExtra("PhoneNumber",phoneNumber.toString());
                intent1.putExtra("PostNumber", addressResult.GetCustomerAddressResult.get(position).Postcode);
                intent1.putExtra("address", addressResult.GetCustomerAddressResult.get(position).Province+
                		addressResult.GetCustomerAddressResult.get(position).Area+
                		addressResult.GetCustomerAddressResult.get(position).County+
                		addressResult.GetCustomerAddressResult.get(position).Town+
                		addressResult.GetCustomerAddressResult.get(position).Village);
                intent1.putExtra("RceciverAddrID", addressResult.GetCustomerAddressResult.get(position).AddrID);
                setResult(3, intent1);//requestCode=3
                finish();
            }
        }


        //删除事件，向右滑动删除直接调用此事件
        @Override
        public void onDismiss(final int[] reverseSortedPositions)
        {
            System.out.println("sssss");
            new AlertDialog.Builder(mAdapter.getContext()).setTitle("确认删除地址吗？")
            .setIcon(R.drawable.ic_diaog_info)
            .setPositiveButton("确定", new DialogInterface.OnClickListener()
            {

                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    // 点击“确认”后的操作
                    for (int position : reverseSortedPositions)
                    {
                    shanchuweizhi=position;
                    	//这个position是指listview对应的position所以要先删除数据库中的记录然后在list中移除
                    	System.out.println("AddrID....."+addressResult.GetCustomerAddressResult.get(position).AddrID);
                    	DeleteAddress( addressResult.GetCustomerAddressResult.get(position).AddrID);
                    	
                    	System.out.println("position...."+position);
                    	
                    	
                    	//访问网络删除数据库中记录
                    	
                    	
                    }
                   
                   



                }

				private void DeleteAddress(final int AddrID) {//访问网络根据AddrID删除数据库中记录
					
					
					new Thread(new Runnable() {
						
						@Override
						public void run() {
							
							
							String DeleteAddress = client.postDelete(C.SERVER_URL+C.Service_DeleteCustomerAddress, AddrID, "123456");
							if(DeleteAddress.contains("true"))
							{
								handler.sendEmptyMessage(4);
							}
							else if(DeleteAddress.contains("false"))
							{
								handler.sendEmptyMessage(5);
								
							}
							
							
						}
					}).start();
					
					
					
					
					
				}
            })
            .setNegativeButton("取消", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    // 点击“返回”后的操作,这里不设置没有任何操作
                }
            }).show();



        }
        
        
  

  @Override
 public void onedit(int position,String phone,String name,String Province,String Area,String country,String Town,String Village,String postnumber) {
	  
	  
	  Intent intent = new Intent(getApplicationContext(),
				Edit_activity.class);
		intent.putExtra("phone", phone);
		intent.putExtra("name", name);
		intent.putExtra("Province", Province);
		intent.putExtra("Area", Area);
		intent.putExtra("country", country);
		intent.putExtra("Town", Town);
		intent.putExtra("Village", Village);
		
		intent.putExtra("postnumber", postnumber);
		intent.putExtra("position", position);
		startActivityForResult(intent, 3);
}
        
        
        @Override
        public void onselect(int position, String phone, String name,
        		String address,String Postcode) {

        	
			Intent intent = new Intent();
			intent.putExtra("PhoneNumber", phone);
			intent.putExtra("name", name);
			intent.putExtra("address", address);
			intent.putExtra("PostNumber", Postcode);
			intent.putExtra("position", position);
			if(SP_Utils.getSP_String(getApplicationContext(), GlobalConstants.FLAG, "").equals("1"))
			{setResult(2, intent);}
			else if(SP_Utils.getSP_String(getApplicationContext(), GlobalConstants.FLAG, "").equals("2"))
			{setResult(3, intent);}
			finish();
			
    		
    	
        }

    }

}
