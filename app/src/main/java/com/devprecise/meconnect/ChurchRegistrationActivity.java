package com.devprecise.meconnect;

import org.json.JSONObject;

import com.devprecise.meconnect.utils.Service;


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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ChurchRegistrationActivity extends ActionBarActivity {
		EditText txtResturant,txtTown,txtuserName,txtPassword,txtPhone,txtAddress;
		 Typeface custom_fontbold,custom_fontregular,custom_fontmedium ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	     getSupportActionBar().setDisplayHomeAsUpEnabled(true);     
		setContentView(R.layout.activity_church_registration);
		
	     custom_fontmedium= Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/GothamRnd-Medium.otf");
		 	int titleId = getResources().getIdentifier("action_bar_title", "id",
		            "android");
		    TextView appname = (TextView) findViewById(titleId);
		    appname.setTextColor(getResources().getColor(R.color.black));
		    appname.setTypeface(custom_fontmedium);
		    
		
		 custom_fontbold= Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/GothamRnd-Bold.otf");
		 custom_fontregular= Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/GothamRnd-Light.otf");
		 txtResturant=(EditText)findViewById(R.id.editText1);
		 txtResturant.setTypeface(custom_fontregular);
		 txtPhone=(EditText)findViewById(R.id.editText2);
		 txtPhone.setTypeface(custom_fontregular);
		 txtuserName=(EditText)findViewById(R.id.editText3);
		 txtuserName.setTypeface(custom_fontregular);
		 txtPassword=(EditText)findViewById(R.id.editText4);
		 txtPassword.setTypeface(custom_fontregular);
		 txtTown=(EditText)findViewById(R.id.editText5);
		 txtTown.setTypeface(custom_fontregular);
		 
		 TextView txtTitle=(TextView)findViewById(R.id.textView1);
		 txtTitle.setTypeface(custom_fontbold);
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
	
	public boolean isnull(){
		boolean flag=false;
		String msg="";
		if(!txtResturant.getText().toString().trim().equalsIgnoreCase("")){
			if(!txtPhone.getText().toString().trim().equalsIgnoreCase("")){
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
				msg="Enter Phone Number!";
			}
			
		}
		else{
			msg="Enter Church Name!";
		}
		if (!flag)
			MyApp.getInstance().alert(ChurchRegistrationActivity.this, msg);
		return flag;
		
	}
	public void sendData(){
		String resturantname = txtResturant.getText().toString().trim();
		String town = txtTown.getText().toString().trim();
		String password = txtPassword.getText().toString().trim();
		String user = txtuserName.getText().toString().trim();
		String phone=txtPhone.getText().toString();
		

		new Service(ChurchRegistrationActivity.this).churchregistration("api.php?f=chuchregistration", resturantname, town,
				user,password,phone,
				new Service.ResultJSONObject() {
					public void completed(JSONObject json) {
						finish();
						startActivity(new Intent(ChurchRegistrationActivity.this, SplashActivity.class));
					}
				});
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

}
