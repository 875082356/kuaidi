package com.hzgzsoft.logisticsmanage;

import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import JavaBeen.CustomerAddress;
import JavaBeen.Express;
import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

public class WebClient {
	private Handler handler;
	private String encode;
	private HttpClient httpClient;
	private HttpParams httpParams;
	private int timeout;
	private int bufferSize;
	private static final String TAG = "WebClient";
	private Context context;

	public WebClient(Handler handler) {
		this.context = context;
		this.handler = handler;
		timeout = 5 * 1000;
		bufferSize = 8192;
		encode = "UTF-8";
		httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, timeout);
		HttpConnectionParams.setSoTimeout(httpParams, timeout);
		HttpConnectionParams.setSocketBufferSize(httpParams, bufferSize);
		HttpClientParams.setRedirecting(httpParams, true);
		httpClient = new DefaultHttpClient(httpParams);
	}

	public String doGet(String url) throws Exception {
		return doGet(url, null, null, false);
	}

	public String doPost(String url) throws Exception {
		return doPost(url, null);
	}




	public String doGet(String url, Map<String, String> params,
			Handler mhandler, boolean flag) {

		String strResp = "";
		// 添加QueryString
		String paramStr = "";
		if (params != null) {
			Iterator<Entry<String, String>> iter = params.entrySet().iterator();
			while (iter.hasNext()) {
				Entry<String, String> entry = (Entry<String, String>) iter
						.next();
				try {
					paramStr += "&" + entry.getKey() + "="
							+ URLEncoder.encode(entry.getValue(), encode);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			if (paramStr.length() > 0)
				paramStr.replaceFirst("&", "?");
			url += paramStr;
		}
		// 创建HttpGet对象
		HttpGet get = new HttpGet(url);
		// Log.v(TAG, "HttpGet URL" + url);
		System.out.println("HttpGet URL" + url);
		try {

			// 发起请求
			Log.v(TAG, "doGet:" + url);
			HttpResponse resp = httpClient.execute(get);
			if (resp.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_OK) {
				strResp = EntityUtils.toString(resp.getEntity());
				//System.out.println("strResp: " + strResp);
				// return strResp;
			}
			/*
			 * else{ // 如果返回的StatusCode不是OK则抛异常 Toast.makeText(context,
			 * "联网失败，请检查网络", 0).show(); throw new Exception("Error Response:" +
			 * resp.getStatusLine().toString());}
			 */

		} catch (Exception e) {
			e.printStackTrace();
			if (!flag) {
				handler.sendEmptyMessage(1);// handler是构造函数传进来的
			} else {
				mhandler.sendEmptyMessage(1);
			}

		}

		finally {
			get.abort();
		}
		return strResp;
	}

	public String doPost(String url, Map<String, String> params)
			throws Exception {
		// POST参数组装
		List<NameValuePair> data = new ArrayList<NameValuePair>();
		if (params != null) {
			Iterator<Entry<String, String>> iter = params.entrySet().iterator();
			while (iter.hasNext()) {
				Entry<String, String> entry = (Entry<String, String>) iter
						.next();
				data.add(new BasicNameValuePair(entry.getKey(), entry
						.getValue()));
			}
		}
		HttpPost post = new HttpPost(url);
		try {
			// 添加请求参数到请求对象
			if (params != null)
				post.setEntity(new UrlEncodedFormEntity(data, HTTP.UTF_8));
			// 发起请求
			Log.v(TAG, "doPost: " + url);
			Log.v(TAG, "HttpPost: " + post);
			Log.v(TAG, "data: " + data.toString());
			HttpResponse resp = httpClient.execute(post);
			String strResp = "";
			if (resp.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_OK) {
				strResp = EntityUtils.toString(resp.getEntity());
				Log.v(TAG, "strResp: " + strResp);
			}

			else
				// 如果返回的StatusCode不是OK则抛异常
				throw new Exception("Error Response:"
						+ resp.getStatusLine().toString());
			return strResp;
		} finally {
			post.abort();
		}
	}

	/**
	 * @param url
	 *            - 需要访问的address
	 * @param data
	 *            - Request的内容字符串
	 * @param contentType
	 *            - Request的ContentType
	 * @return Response的字符串
	 * @throws Exception
	 */
	/*
	 * public String doPost(String url, String data, String contentType) throws
	 * Exception { HttpPost post = new HttpPost(url); try { // 添加请求参数到请求对象
	 * StringEntity se = new StringEntity(data, HTTP.UTF_8);
	 * se.setContentType(contentType); post.setEntity(se); // 发起请求 Log.v(TAG,
	 * "doPost: " + url); // Log.v(TAG, "HttpPost: " + post); // Log.v(TAG +
	 * "  data: ", data.toString()); HttpResponse resp =
	 * httpClient.execute(post); String strResp = ""; if
	 * (resp.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_OK) {
	 * strResp = EntityUtils.toString(resp.getEntity()); Log.v(TAG, "strResp: "
	 * + strResp); } else // 如果返回的StatusCode不是OK则抛异常 throw new
	 * Exception("Error Response:" + resp.getStatusLine().toString()); return
	 * strResp; } finally { post.abort(); } }
	 */

	/**
	 * 含参
	 * 
	 * @param url
	 *            服务的完整地址
	 * @param data
	 *            数据对象
	 * @param contentType
	 *            Request的ContentType
	 * @return 返回的json字符串
	 */
	public String doPost(String url, CustomerAddress Addr, String contentType) {

		String strResp = "";
		HttpPost request = new HttpPost(url);
		request.setHeader("Accept", "application/json");

		request.setHeader("Content-type", "application/json");

		// request.setHeader("Content-type", "text/xml");
		// request.addHeader("Content-Type", "application/json");

		// 组织json
		JSONStringer vehicle;
		try {
			vehicle = new JSONStringer().object().key("Addr").object()
					.key("AddrID").value(Addr.getAddrID()).key("AccountName")
					.value(Addr.getAccountName()).key("PhoneNumber")
					.value(Addr.getPhoneNumber()).key("Name")
					.value(Addr.getName()).key("Postcode")
					.value(Addr.getPostcode()).key("Province")
					.value(Addr.getProvince()).key("Area")
					.value(Addr.getArea()).key("County")
					.value(Addr.getCounty()).key("Town").value(Addr.getTown())
					.key("Village").value(Addr.getVillage()).key("OrderID")
					.value(Addr.getOrderID()).endObject().key("AuthCode")
					.value("123456").endObject();
			StringEntity entity = new StringEntity(vehicle.toString(), encode);

			request.setEntity(entity);
			// Log.v(TAG, "vehicle: " + vehicle);
			System.out.println("vehicle: " + vehicle);
			System.out.println("doPost: " + url);
			// Log.v(TAG, "doPost: " + url);

			// 向WCF服务发送请求
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpResponse response = httpClient.execute(request);

			// 判断是否成功
			if (response.getStatusLine().getStatusCode() == 200) {
				strResp = EntityUtils.toString(response.getEntity(), encode);

				// Log.v(TAG, "strResp: " + strResp);
				System.out.println("strResp: " + strResp);

			}
			/*
			 * else{
			 * System.out.println("111111"+response.getStatusLine().getStatusCode
			 * ()); // 如果返回的StatusCode不是OK则抛异常 throw new
			 * Exception("Error Response:" +
			 * response.getStatusLine().toString()); }
			 */

		} catch (Exception e) {
			e.printStackTrace();

			handler.sendEmptyMessage(6);

		}

		return strResp;

	}

	/**
	 * post方式带多个参数
	 * 
	 */
	public String postDelete(String Url, int AddrID, String AuthCode) {
		String strResp = "";
		try {
			HttpPost request = new HttpPost(Url);
			request.setHeader("Accept", "application/json");
			request.setHeader("Content-type", "application/json");
			// 先封装一个 JSON 对象
			JSONObject param = new JSONObject();
			param.put("AddrID", AddrID);
			param.put("AuthCode", AuthCode);
			// 绑定到请求 Entry
			StringEntity se = new StringEntity(param.toString());

			request.setEntity(se);
			Log.v(TAG, "post: " + Url);

			// 发送请求
			HttpResponse response = new DefaultHttpClient().execute(request);
			if (response.getStatusLine().getStatusCode() == 200) {
				// String a = retrieveInputStream(response.getEntity());
				strResp = EntityUtils.toString(response.getEntity());
				// Log.v(TAG, "strResp: " + strResp);
				System.out.println("strResp: " + strResp);
			}
		} catch (Exception e) {
			e.printStackTrace();
			handler.sendEmptyMessage(7);
		}
		return strResp;
	}

	/**
	 * 快递员下单时请求网络的操作
	 */
	public String xiadan(String url, Express express, String contentType,
			Handler handler) {

		String strResp = "";
		HttpPost request = new HttpPost(url);
		request.setHeader("Accept", "application/json");

		request.setHeader("Content-type", "application/json");

		// request.setHeader("Content-type", "text/xml");
		// request.addHeader("Content-Type", "application/json");

		// 组织json
		JSONStringer vehicle;
		try {
			vehicle = new JSONStringer().object().key("Exp").object()
					.key("ExpressID").value(express.ExpressID).key("RFIDID")
					.value(express.RFIDID).key("DeviceID")
					.value(express.DeviceID).key("SenderID")
					.value(express.SenderID).key("RecipientsID")
					.value(express.RecipientsID).key("State")
					.value(express.State).key("GPSPath").value(express.GPSPath)
					.key("GPSEndTime").value(express.GPSEndTime)
					.key("StartTime").value(express.StartTime).key("EndTime")
					.value(express.EndTime).key("Trace").value(express.Trace)
					.key("GoodsType").value(express.GoodsType).key("GoodsName")
					.value(express.GoodsName).key("GoodsNumber")
					.value(express.GoodsNumber).key("GoodsWeight")
					.value(express.GoodsWeight).key("GoodsBulk")
					.value(express.GoodsBulk).key("Amount")
					.value(express.Amount).key("IsAgreedSettlement")
					.value(express.IsAgreedSettlement).key("Premiun")
					.value(express.Premiun).key("IsReversedPay")
					.value(express.IsReversedPay).key("RecipientsName")
					.value(express.RecipientsName).endObject().key("AuthCode")
					.value("123456").endObject();
			StringEntity entity = new StringEntity(vehicle.toString(), encode);

			request.setEntity(entity);
			// Log.v(TAG, "vehicle: " + vehicle);
			System.out.println("vehicle: " + vehicle);
			System.out.println("doPost: " + url);
			// Log.v(TAG, "doPost: " + url);

			// 向WCF服务发送请求
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpResponse response = httpClient.execute(request);

			// 判断是否成功
			if (response.getStatusLine().getStatusCode() == 200) {
				strResp = EntityUtils.toString(response.getEntity(), encode);

				// Log.v(TAG, "strResp: " + strResp);
				System.out.println("strResp: " + strResp);

			}
			/*
			 * else{
			 * System.out.println("111111"+response.getStatusLine().getStatusCode
			 * ()); // 如果返回的StatusCode不是OK则抛异常 throw new
			 * Exception("Error Response:" +
			 * response.getStatusLine().toString()); }
			 */

		} catch (Exception e) {
			e.printStackTrace();

			handler.sendEmptyMessage(0);

		}

		return strResp;

	}

}