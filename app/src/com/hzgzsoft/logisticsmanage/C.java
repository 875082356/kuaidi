package com.hzgzsoft.logisticsmanage;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.text.format.Time;

public class C {
	
		public static final String SERVER_URL="http://192.168.212.40:81/kuaidiTEST/LogisticsService.svc";
		////客户地址操作方法
		public static final String Service_GetCustomerAddress = "/GetCustomerAddress";//快递员PDA快速查询用户地址
		public static final String Service_UpdateCustomerAddress = "/UpdateCustomerAddress";//用于更新客户地址，可能更新的时刻：PDA修改了地址信息，包括电话号码（所以PDA中需要有一个独立的功能修改客户地址）；web网站中客户自己修改了地址
		public static final String Service_AddCustomerAddress = "/AddCustomerAddress";//用于添加客户地址，可能添加的时刻：PDA添加地址信息；web网站中客户自己添加了地址
		public static final String Service_DeleteCustomerAddress = "/DeleteCustomerAddress";//用于删除客户地址，可能删除的时刻：web网站中客户自己删除了地址
		
		 //快件操作方法
		public static final String Service_AddExpress = "/AddExpress";//用于快递员下单，里面会修改发件人地址的最后使用时间，表明这个地址刚被使用过
		public static final String Service_RegisterDevice = "/RegisterDevice";//用于把设备注册到某个快件中
		public static final String Service_RegisterGPSPoint = "/RegisterGPSPoint";//用于登记GPS信息（异步方式），修改快件的GPS路径和设备的当前位置
		public static final String Service_SetExpressState = "/SetExpressState";//设置快件的状态,快件状态，0表示送达；-1表示退件；-2表示作废；-3表示遗失，其中标记为作废和遗失的功能不能随意向快递员的PDA开放
		public static final String Service_GetExpress = "/GetExpress";//获取快件信息
		public static final String Service_UpdatePhoneNumber = "/UpdatePhoneNumber";//编辑按钮时若有修改电话号码则调用该接口
		public static final String Service_GetExpressID = "/GetExpressID";//获得Tbl_Express表的ExpressID
		public static final String Service_GetSenderID_RecipientsID = "/GetSenderID_RecipientsID";//获得Tbl_Express表的ExpressID
		public static final String Service_GetInfo_Sender_Reciver = "/GetInfo_Sender_Reciver";//根据AddrID获得tbl_CustomerAddress表的收寄件人信息

	public static String getTime(){
		SimpleDateFormat  formatter=  new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss  ");       
		Date  curDate=new Date(System.currentTimeMillis());//获取当前时间       
		 String  str=formatter.format(curDate); 
		 System.out.println("当前时间"+str);
		 return str;
	}


}
