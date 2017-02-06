package JavaBeen;

import java.util.ArrayList;

import android.R.integer;

public class CustomerAddressResult {



	public ArrayList<AddressData> GetCustomerAddressResult;

	public  class AddressData {
		
		@Override
		public String toString() {
			return "AddressData [AccountName=" + AccountName + "]";
		}
		public String AccountName;
		public int AddrID;
		public String Area;
		public String County;
		public String Name;
		public String OrderID;
		public String PhoneNumber;
		public String Postcode;
		public String Province;
		public String Town;
		public String Village;

		/*
		 * "AccountName": "1111", "AddrID": 26, "Area": "金华", "County": "东阳",
		 * "Name": "", "OrderID": "2016/5/23 15:45:00", "PhoneNumber":
		 * "13588228755", "Postcode": "322100", "Province": "浙江", "Town": "歌山镇",
		 * "Village": "象塘村
		 */
	}

}
