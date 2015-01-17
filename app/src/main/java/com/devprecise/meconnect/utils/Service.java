package com.devprecise.meconnect.utils;

import java.io.ByteArrayOutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.devprecise.meconnect.MyApp;





import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

/**
 * Async task class to get json by making HTTP call
 * */
public class Service extends AsyncTask<Void, Void, String> {
	public static String SERVER = Utils.url;
	public static final String NULL = "null";

	Context _context;

	HttpRequestBase _httpRequest;
	Result _result;
	boolean _showProgress;
	String jsonStr;
	ProgressDialog progressDialog = null;
	String url = "";
	
	
	// private static String TAG = MyService.class.getSimpleName();
	 
	    public boolean isRunning = false;
	
	
	public Service(Context context) {
		_context = context;
		_showProgress = true;
	}

	public Service(Context context, boolean showProgress) {
		_context = context;
		_showProgress = showProgress;
	}

	public static class Result {
		public void failed(Context context, String message) {
			Toast.makeText(context, message, Toast.LENGTH_LONG).show();
			Log.i("failed: ", message + "");
		}
	}

	public static abstract class ResultMessage extends Result {
		public abstract void completed(String message) throws Exception;
	}

	public static abstract class ResultString extends Result {
		public abstract void completed(String message) throws Exception;
	}

	public static abstract class ResultJSONObject extends Result {
		public abstract void completed(JSONObject message) throws Exception;
	}

	public static abstract class ResultJSONArray extends Result {
		public abstract void completed(JSONArray records) throws Exception;
	}

	public static abstract class ResultBitmap extends Result {
		public abstract void completed(Bitmap bitmap) throws Exception;
	}

	public static String get(JSONObject obj, String key) {
		try {
			String value = obj.getString(key);
			if (value.equals(NULL))
				value = "";
			return value;
		} catch (JSONException e) {
			Log.i("jsonparse error: ", e + "");
			return "";
		}
	}

	@Override
	protected void onPreExecute() {
		
		if(_showProgress)
			progressDialog = ProgressDialog.show(_context, "", "Loading...", true, false);
	}

	@Override
	protected String doInBackground(Void... arg0) {

		ServiceHandler sh = new ServiceHandler();

	

	
		// Making a request to url and getting response
		jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);
		url="";
		Log.d("Response: ", "> " + jsonStr);

		return jsonStr;

	}

	@Override
	protected void onPostExecute(String result) {

		// Dismiss the progress dialog

		if (_showProgress)
			progressDialog.dismiss();
		try {

			if (!result.equalsIgnoreCase("")) {

				Log.i("a", "a");
				String strResult = result;

				Log.i("a", strResult);

				if (_result instanceof ResultString) {
					ResultString resultString = (ResultString) _result;
					resultString.completed(strResult);
				} else if (_result instanceof ResultMessage) {
					ResultMessage resultMessage = (ResultMessage) _result;
					try {
						JSONObject json = new JSONObject(strResult);
						if (json.getInt("error") == 0) {
							resultMessage.completed(null);
						} else
							resultMessage.failed(_context,
									json.getString("message"));
					} catch (Exception e) {
						resultMessage.failed(_context, e.getMessage());
					}
				} else if (_result instanceof ResultJSONObject) {
					ResultJSONObject resultJSONObject = (ResultJSONObject) _result;
					try {
						JSONObject json = new JSONObject(strResult);
						if (json.getInt("error") == 0) {
							resultJSONObject.completed(json);
						} else
							resultJSONObject.failed(_context,
									json.getString("message"));
					} catch (Exception e) {
						resultJSONObject.failed(_context, e.getMessage());
					}
				} else if (_result instanceof ResultJSONArray) {

					ResultJSONArray resultJSONArray = (ResultJSONArray) _result;

					try {
						JSONObject json = new JSONObject(strResult);
						if (json.getInt("error") == 0) {
							JSONArray records = json.getJSONArray("records");
							resultJSONArray.completed(records);
						} else
							resultJSONArray.failed(_context,
									json.getString("message"));
					} catch (Exception e) {
						resultJSONArray.failed(_context, e.getMessage());
					}
				}

			}

		} catch (Exception e) {
			// System.out.println(e.getMessage());
			Log.e("error: ", e.getMessage() + "");
		}

	}

	public void getString(String url, ResultString result) {
		_result = result;
		try {
			_httpRequest = new HttpGet(SERVER + url);
			execute();
		} catch (Exception e) {
		}
	}

	public void getRecord(String url, ResultJSONObject result) {
		_result = result;
		try {
			//_httpRequest = new HttpGet(SERVER + url);
		
			this.url=SERVER + url;
			execute();
		} catch (Exception e) {
		}
	}

	public void getRecords(String url, ResultJSONArray result) {
		_result = result;
		try {
			//_httpRequest = new HttpGet(SERVER + url);
			this.url=SERVER + url;
			Log.e("url: ", SERVER + url + "");
			execute();
		} catch (Exception e) {
		}
	}
	public void getBitmap(String url, ResultBitmap result) {
		_result = result;
		try {
			if (!url.startsWith("http"))
				url = SERVER + url;
			_httpRequest = new HttpGet(url);
			execute();
		} catch (Exception e) {
		}
	}
	public void login(String page,String email, String password,ResultJSONObject result) {
		_result = result;
		try {
			String data="";
			data="&email="+URLEncoder.encode(email, "ISO-8859-1");
			data+="&pass="+URLEncoder.encode(password, "ISO-8859-1");
			
			this.url=SERVER+page+data;		
			Log.e("url: ", url  + "");
			execute();
		} catch (Exception e) {
			
			Log.d("execption: ", e+"");
		}
	}
	public void registration(String page,String fname, String lname,String email,String password,ResultJSONObject result){
		_result = result;
		try {
			String data="";
			data="&fname="+URLEncoder.encode(fname, "ISO-8859-1");
			data+="&lname="+URLEncoder.encode(lname, "ISO-8859-1");
			data+="&email="+URLEncoder.encode(email, "ISO-8859-1");
			data+="&pass="+URLEncoder.encode(password, "ISO-8859-1");
			this.url=SERVER+page+data;		
			Log.e("url: ", url  + "");
			execute();
		} catch (Exception e) {
			
			Log.d("execption: ", e+"");
		}
	}
	
	public void offerAccept(String page,String userid, String offerid,String reedemcode,ResultJSONObject result){
		_result = result;
		try {
			String data="";
			data="&userid="+URLEncoder.encode(userid, "ISO-8859-1");
			data+="&offerid="+URLEncoder.encode(offerid, "ISO-8859-1");
			data+="&reedemcode="+URLEncoder.encode(reedemcode, "ISO-8859-1");
	
			this.url=SERVER+page+data;		
			Log.e("url: ", url  + "");
			execute();
		} catch (Exception e) {
			
			Log.d("execption: ", e+"");
		}
	}

	public void addCollectionType(String page,String type,ResultJSONObject result){
		_result = result;
		try {
			String data="";
			data="&type="+URLEncoder.encode(type, "ISO-8859-1");
			data+="&chid="+URLEncoder.encode(MyApp.getInstance().getSetting().getString("churchid", ""), "ISO-8859-1");
			this.url=SERVER+page+data;		
			Log.e("url: ", url  + "");
			execute();
		} catch (Exception e) {
			
			Log.d("execption: ", e+"");
		}
	}
	public void addCollection(String page,String mpesa,String memberid,String typeid,String amount,String month,String year,ResultJSONObject result){
		_result = result;
		try {
			String data="";
			data="&mid="+URLEncoder.encode(memberid, "ISO-8859-1");
			data+="&mpesa="+URLEncoder.encode(mpesa, "ISO-8859-1");
			data+="&tid="+URLEncoder.encode(typeid, "ISO-8859-1");
			data+="&amt="+URLEncoder.encode(amount, "ISO-8859-1");
			data+="&cmonth="+URLEncoder.encode(month, "ISO-8859-1");
			data+="&cyear="+URLEncoder.encode(year, "ISO-8859-1");
			data+="&chid="+URLEncoder.encode(MyApp.getInstance().getSetting().getString("churchid", ""), "ISO-8859-1");
			this.url=SERVER+page+data;		
			Log.e("url: ", url  + "");
			execute();
		} catch (Exception e) {
			
			Log.d("execption: ", e+"");
		}
	}
	
	public void updateCollection(String page,String id,String memberid,String typeid,String amount,String month,String year,ResultJSONObject result){
		_result = result;
		try {
			String data="";
			data="&id="+URLEncoder.encode(id, "ISO-8859-1");
			data+="&mid="+URLEncoder.encode(memberid, "ISO-8859-1");
			data+="&tid="+URLEncoder.encode(typeid, "ISO-8859-1");
			data+="&amt="+URLEncoder.encode(amount, "ISO-8859-1");
			data+="&cmonth="+URLEncoder.encode(month, "ISO-8859-1");
			data+="&cyear="+URLEncoder.encode(year, "ISO-8859-1");
	
			this.url=SERVER+page+data;		
			Log.e("url: ", url  + "");
			execute();
		} catch (Exception e) {
			
			Log.d("execption: ", e+"");
		}
	}

	public void updateCollectionType(String page,String type,String id,ResultJSONObject result){
		_result = result;
		try {
			String data="";
			data="&type="+URLEncoder.encode(type, "ISO-8859-1");
			data+="&id="+URLEncoder.encode(id, "ISO-8859-1");
			this.url=SERVER+page+data;		
			Log.e("url: ", url  + "");
			execute();
		} catch (Exception e) {
			
			Log.d("execption: ", e+"");
		}
	}
	
	public void profileUpdate(String page,String fname, String lname,String email,String password,ResultJSONObject result){
		_result = result;
		try {
			String data="";
			data="&fname="+URLEncoder.encode(fname, "ISO-8859-1");
			data+="&lname="+URLEncoder.encode(lname, "ISO-8859-1");
			data+="&email="+URLEncoder.encode(email, "ISO-8859-1");
			data+="&pass="+URLEncoder.encode(password, "ISO-8859-1");
			this.url=SERVER+page+data;		
			Log.e("url: ", url  + "");
			execute();
		} catch (Exception e) {
			
			Log.d("execption: ", e+"");
		}
	}
	public void cmpregistration(String page,String churchid,String name, String email,String username,String password,String phone,ResultJSONObject result){
		_result = result;
		try {
			String data="";
			
			data="&name="+URLEncoder.encode(name, "ISO-8859-1");
			data+="&email="+URLEncoder.encode(email, "ISO-8859-1");
			data+="&user="+URLEncoder.encode(username, "ISO-8859-1");
			data+="&password="+URLEncoder.encode(password, "ISO-8859-1");
			data+="&phone="+URLEncoder.encode(phone, "ISO-8859-1");
			data+="&chid="+URLEncoder.encode(churchid, "ISO-8859-1");
			this.url=SERVER+page+data;		
			Log.e("url: ", url  + "");
			execute();
		} catch (Exception e) {
			
			Log.d("execption: ", e+"");
		}
	}
	public void churchregistration(String page,String name, String town,String username,String password,String phone,ResultJSONObject result){
		_result = result;
		try {
			String data="";
			data="&name="+URLEncoder.encode(name, "ISO-8859-1");
			data+="&town="+URLEncoder.encode(town, "ISO-8859-1");
			data+="&user="+URLEncoder.encode(username, "ISO-8859-1");
			data+="&password="+URLEncoder.encode(password, "ISO-8859-1");
			data+="&phone="+URLEncoder.encode(phone, "ISO-8859-1");
			
			this.url=SERVER+page+data;		
			Log.e("url: ", url  + "");
			execute();
		} catch (Exception e) {
			
			Log.d("execption: ", e+"");
		}
	}
	public void login(String username, String password,ResultJSONObject result) {
		_result = result;
		try {
			
			username= username;
			password= password;
			this.url=SERVER+"login.php?username="+URLEncoder.encode(username.trim(), "ISO-8859-1")+"&password="+URLEncoder.encode(password.trim(), "ISO-8859-1");
			Log.e("url: ", url  + "");
			execute();
		} catch (Exception e) {
			
			Log.d("execption: ", e+"");
		}
	}
	
	
	///////
	
}
