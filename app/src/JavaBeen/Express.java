package JavaBeen;

import android.R.bool;

public class Express {

	public int ExpressID;// /
							// 快件的内部单号，可用于快速查询某个快件。但考虑到安全性，此单号不直接提供给外部客户进行快件查询。外部客户可利用加密后的ExpressID作为快件号在网站和手机终端上查询。
							// /在快递员揽件时，PDA会把ExpressID、寄件人邮编、收件人邮编和寄件状态写入RFID中。其中ExpressID用于中间节点（设备）对快件的快速注册，后三个字段用于实现快速分拣。
	public String RFIDID; // / 快递箱的RFID的ID号，此ID是RFID内建的TID，不可修改
	public int DeviceID; // /
							// 箱子当前绑定于某一节点（或设备）的ID。当寄件动作结束后，此字段值为-1，表示没有绑定在任何节点或设备上
	public int SenderID; // / 寄件人地址ID，为tbl_CustomerAddress.AddrID字段值
	public int RecipientsID; // / 收件人地址ID，为tbl_CustomerAddress.AddrID字段值

	public int State; // / 快件状态：1.正在正常寄件；0.寄件结束；-1.退件；-2.遗失或破损，快件无法给收件人。

	public String GPSPath; // / 登记GPS轨迹
	public String GPSEndTime;// / 最后采集GPS的时间
	public String StartTime; // / 揽件时间
	public String EndTime; // / 送达时间

	public String Trace;// / 用于记录历次节点（设备）的注册信息（设备ID和注册时间）

	public String GoodsType; // / 物品类型：文件、物品等

	public String GoodsName;// / 物品名称：手机、数码相机等

	public int GoodsNumber;// / 物品数量

	public float GoodsWeight;// / 物品重量

	public String GoodsBulk;// / 物品体积

	public float Amount;// / 快递费用。-1表示还未支付快递费。

	public boolean IsAgreedSettlement; // / 是否协议结算。默认为0，表示不是；1表示协议结算

	public float Premiun;// / 保费

	public boolean IsReversedPay;// / 是否邮费到付，默认为0，表示非到付；1表示到付

	public String RecipientsName;// / 签收人或代签人姓名

	
	

}
