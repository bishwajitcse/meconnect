package com.devprecise.meconnect;

import org.json.JSONObject;




import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class SplashActivity extends Activity {
	Activity activity = this;

	static boolean showSplash = true;
	Typeface custom_fontbold;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_splash);
		custom_fontbold= Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/GothamRnd-Bold.otf");
		TextView txtcmp=(TextView)findViewById(R.id.textView1);
		txtcmp.setTypeface(custom_fontbold);

		if(!showSplash && isNetworkAvilable())
			start(); 
		else
			new Handler().postDelayed(new Runnable(){
				@Override
				public void run() {
					showSplash = false;
					if(isNetworkAvilable())
						start();
					else
						networkDialog();
				}
			}, 4000);
	}




	private void networkDialog(){

		AlertDialog.Builder alertDialog = new AlertDialog.Builder(SplashActivity.this);

		// Setting Dialog Title
		alertDialog.setTitle("Network Information...");

		// Setting Dialog Message
		alertDialog.setMessage("Internet not available, Cross check your internet connectivity and try again");

		// Setting Icon to Dialog
		alertDialog.setIcon(R.drawable.ic_launcher);

		alertDialog.setCancelable(false);
		alertDialog.setPositiveButton("RETRY", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int which) {

				// Write your code here to invoke YES event
				if(isNetworkAvilable())
					start();
				else
					networkDialog();
			}
		});

		alertDialog.setNegativeButton("QUIT", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				finish();
			}
		});

		alertDialog.show();
	}
	private boolean isNetworkAvilable(){
		System.out.println("OK Net");
		ConnectivityManager cm=(ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
		NetworkInfo info=cm.getActiveNetworkInfo();
		return(info!=null);
	}

	public void start() {

		
		activity.finish();
		if(MyApp.getInstance().getSetting().getString("username", "").equalsIgnoreCase("")){
			startActivity(new Intent(activity, LoginActivity.class));
			overridePendingTransition(R.anim.activity_open_translate,R.anim.activity_close_scale);
		}
		else{
			startActivity(new Intent(activity, RootActivity.class));
			overridePendingTransition(R.anim.activity_open_translate,R.anim.activity_close_scale);
		}
		

	}



}
