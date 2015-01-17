package com.devprecise.meconnect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.devprecise.meconnect.beanclass.CollectionTypeBean;
import com.devprecise.meconnect.utils.Service;
import com.devprecise.meconnect.utils.Service.ResultJSONArray;


import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class AdminRegistrationActivity extends ActionBarActivity {
		EditText txtResturant,txtEmail,txtuserName,txtPassword,txtPhone,txtAddress;
		 Typeface custom_fontbold,custom_fontregular,custom_fontmedium ;
		 Spinner spChurch;
		 private ArrayList<HashMap> hmList= new ArrayList<HashMap>();
		 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	     getSupportActionBar().setDisplayHomeAsUpEnabled(true);  
	     
	     custom_fontmedium= Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/GothamRnd-Medium.otf");
	 	int titleId = getResources().getIdentifier("action_bar_title", "id",
	            "android");
	    TextView appname = (TextView) findViewById(titleId);
	    appname.setTextColor(getResources().getColor(R.color.black));
	    appname.setTypeface(custom_fontmedium);
	    
	     
		setContentView(R.layout.activity_admin_registration);
		 custom_fontbold= Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/GothamRnd-Bold.otf");
		 custom_fontregular= Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/GothamRnd-Light.otf");
		 txtResturant=(EditText)findViewById(R.id.editText1);
		 txtResturant.setTypeface(custom_fontregular);
		 txtEmail=(EditText)findViewById(R.id.editText2);
		 txtEmail.setTypeface(custom_fontregular);
		 txtuserName=(EditText)findViewById(R.id.editText3);
		 txtuserName.setTypeface(custom_fontregular);
		 txtPassword=(EditText)findViewById(R.id.editText4);
		 txtPassword.setTypeface(custom_fontregular);
		 txtPhone=(EditText)findViewById(R.id.editText5);
		 txtPhone.setTypeface(custom_fontregular);
		 TextView txtTitle=(TextView)findViewById(R.id.textView1);
		 txtTitle.setTypeface(custom_fontbold);
		 spChurch=(Spinner)findViewById(R.id.spChurch);
		 loadCollectionType();
		 
		Button btnSave=(Button)findViewById(R.id.button1);
		
		btnSave.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
		
				if(isnull()){
					sendData();
				}
			}
		});
	}
	public void loadCollectionType(){
		new Service(this,false).getRecords("api.php?f=getAllChurch", new ResultJSONArray() {

			public void completed(JSONArray records) throws Exception {
				// TODO Auto-generated method stub
				addChurchList(records);
			}
		});
		
		
	}
	public void addChurchList(JSONArray records) throws Exception {
		int j = 0;
		List<String> listimage = new ArrayList<String>();
		List<String> listimage2 = new ArrayList<String>();
		   
        if(hmList.size()>0)
        	hmList.clear();
		for (int i = 0; i < records.length(); i++) {
			HashMap hm=new HashMap();
			j++;
			JSONObject record = records.getJSONObject(i);
			hm.put("chid",record.getString("churchID").toString());
			hm.put("name",record.getString("name").toString());
			 hmList.add(hm);
		}
		populateSpinner2();
		//lv.onLoadMoreComplete();
	}
	 private void populateSpinner2() {
	      List<String> lables = new ArrayList<String>();
	       
	    

	      for (int i = 0; i < hmList.size(); i++) {
	          lables.add(hmList.get(i).get("name").toString());
	      }

	      // Creating adapter for spinner
	      ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,
	              android.R.layout.simple_spinner_item, lables);

	      // Drop down layout style - list view with radio button
	      spinnerAdapter
	              .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

	      // attaching data adapter to spinner
	      spChurch.setAdapter(spinnerAdapter);
	     // spChurch.setSelection(spinnerAdapter.getPosition(getIntent().getStringExtra("collectiontype").toString()));
	  }
	public boolean isnull(){
		boolean flag=false;
		String msg="";
		if(!txtResturant.getText().toString().trim().equalsIgnoreCase("")){
			if(!txtEmail.getText().toString().trim().equalsIgnoreCase("")){
				if(!txtuserName.getText().toString().trim().equalsIgnoreCase("")){
					if(!txtPassword.getText().toString().trim().equalsIgnoreCase("")){
						return true;
					}
					else{
						msg="Enter Password!";
					}
				}
				else{
					msg="Enter Username!";
				}
			}
			else{
				msg="Enter Mail ID!";
			}
			
		}
		else{
			msg="Enter Resturant Name!";
		}
		if (!flag)
			MyApp.getInstance().alert(AdminRegistrationActivity.this, msg);
		return flag;
		
	}
	public boolean onOptionsItemSelected(MenuItem item) {
		// Take appropriate action for each action item click
		switch (item.getItemId()) {
		case android.R.id.home:
			this.finish();
			
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void sendData(){
		String resturantname = txtResturant.getText().toString().trim();
		String email = txtEmail.getText().toString().trim();
		String password = txtPassword.getText().toString().trim();
		String user = txtuserName.getText().toString().trim();
		String phone=txtPhone.getText().toString();
		
		int chid=(int)spChurch.getSelectedItemId();
		
		String churchid=hmList.get(chid).get("chid").toString();

		new Service(AdminRegistrationActivity.this).cmpregistration("api.php?f=cmpregistration", churchid,resturantname, email,
				user,password,phone,
				new Service.ResultJSONObject() {
					public void completed(JSONObject json) {
						finish();
						startActivity(new Intent(AdminRegistrationActivity.this, SplashActivity.class));
					}
				});
	}


}
