package JavaBeen;

public class CustomerAddress {

	public int AddrID;//地址记录的内部ID

	public String AccountName;//账户名，对没注册用户来说此字段为空

	public String PhoneNumber;//手机号或固话号，有且只有一个

	public String Name; //姓名或公司名

	public String Postcode; //邮政编码

	public String Province; // 省

	public String Area;// 市（地区）

	public String County; //县

	public String Town; // 乡镇/街道
    

	public String Village;// 村或小区（包括门牌号）

	public String OrderID; // 用于同一用户或同一电话号码下可能有多个地址时的排序，日期越大越靠前，在选择发货地址和收货地址后可能修改该值，修改规则：传回被使用的地址的AddrID，修改其OrderID为现在的时间

	public int getAddrID() {
		return AddrID;
	}

	public void setAddrID(int addrID) {
		AddrID = addrID;
	}

	public String getAccountName() {
		return AccountName;
	}

	public void setAccountName(String accountName) {
		AccountName = accountName;
	}

	public String getPhoneNumber() {
		return PhoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		PhoneNumber = phoneNumber;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getPostcode() {
		return Postcode;
	}

	public void setPostcode(String postcode) {
		Postcode = postcode;
	}

	public String getProvince() {
		return Province;
	}

	public void setProvince(String province) {
		Province = province;
	}

	public String getArea() {
		return Area;
	}

	public void setArea(String area) {
		Area = area;
	}

	public String getCounty() {
		return County;
	}

	public void setCounty(String county) {
		County = county;
	}

	public String getTown() {
		return Town;
	}

	public void setTown(String town) {
		Town = town;
	}

	public String getVillage() {
		return Village;
	}

	public void setVillage(String village) {
		Village = village;
	}

	public String getOrderID() {
		return OrderID;
	}

	public void setOrderID(String orderID) {
		OrderID = orderID;
	}

}
