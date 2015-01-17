package com.devprecise.meconnect;

import org.json.JSONObject;

import com.devprecise.meconnect.utils.Service;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {
	 Typeface custom_fontbold,custom_fontregular ;
		Activity activity = this;
		int loginflage=0;
		 EditText txtPassword,txtEmail;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		 custom_fontbold= Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/GothamRnd-Bold.otf");
		 custom_fontregular= Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/GothamRnd-Light.otf");
		 Button btnLogin=(Button)findViewById(R.id.button1);
		 btnLogin.setTypeface(custom_fontbold);
		  txtEmail=(EditText)findViewById(R.id.txtEmail);
		 txtEmail.setTypeface(custom_fontregular);
		  txtPassword=(EditText)findViewById(R.id.txtPassword);
		 txtPassword.setTypeface(custom_fontregular);
		 
		 TextView txtRegister=(TextView)findViewById(R.id.txtReg);
		 txtRegister.setTypeface(custom_fontbold);
		 
		 Button btnChurch=(Button)findViewById(R.id.button2);
		 Button btnUser=(Button)findViewById(R.id.button3);
		 
		 btnChurch.setTypeface(custom_fontregular);
		 btnUser.setTypeface(custom_fontregular);
		 
		 
		 btnUser.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent(activity, AdminRegistrationActivity.class));
				overridePendingTransition(R.anim.activity_open_translate,R.anim.activity_close_scale);
			}
		});
		 btnChurch.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					startActivity(new Intent(activity, ChurchRegistrationActivity.class));
					overridePendingTransition(R.anim.activity_open_translate,R.anim.activity_close_scale);
				}
			});
			 
		 
		 btnLogin.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(nullCheck()){
					loginSucess();
						
					
				
				}
		
			}
		});
		 
	}
	public void loginSucess(){
		
		/*if(txtEmail.getText().toString().equals("admin@gmail.com") && txtPassword.getText().toString().equals("321")){
			return true;
		}
		*/
		final String username = ((EditText)findViewById(R.id.txtEmail)).getText().toString();
		final String password = ((EditText)findViewById(R.id.txtPassword)).getText().toString();



		 


		new Service(this).login(username, password, new Service.ResultJSONObject () {
			public void completed(JSONObject json) {



				MyApp.getInstance().setSetting("uflag", 1+"");			
				MyApp.getInstance().setSetting("username", Service.get(json, "username"));
				MyApp.getInstance().setSetting("churchid", Service.get(json, "churchid"));
				MyApp.getInstance().setSetting("email", Service.get(json, "email"));
			
				String type=Service.get(json, "usertype").toString();
				
				if( type.equalsIgnoreCase("superadmin")){
					activity.finish();
					startActivity(new Intent(activity, RootActivity.class));
					overridePendingTransition(R.anim.activity_open_translate,R.anim.activity_close_scale);
					

				}
				else{
					Toast.makeText(getApplicationContext(), "This Feature comming soon..."+"", Toast.LENGTH_SHORT).show();
				}
				
				//Toast.makeText(getApplicationContext(), "in"+"", Toast.LENGTH_SHORT).show();

				/*MyApp.getInstance().setSetting("user_id", Service.get(json, "user_id"));
	    				MyApp.getInstance().setSetting("name", Service.get(json, "name"));
	    				MyApp.getInstance().setSetting("class_id", Service.get(json, "class_id"));
	    				MyApp.getInstance().setSetting("section_id", Service.get(json, "section_id"));*/

				
			}
		});
		
		
	}
	public boolean nullCheck() {
		boolean flag = false;
		String msg = "";
		if (!txtEmail.getText().toString().trim().equalsIgnoreCase("")) {
			if (!txtPassword.getText().toString().trim().equalsIgnoreCase("")) {
				
				return true;
			} else {
				msg = "Please Enter Valid Password!";
			}
			
		} else {
			msg = "Please Enter Valid Mail!";
		}
		if (!flag)
			MyApp.getInstance().alert(this, msg);
		return flag;
	}



}
