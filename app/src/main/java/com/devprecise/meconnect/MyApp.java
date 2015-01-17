package com.devprecise.meconnect;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONObject;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;



import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Application;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.widget.Toast;

public final class MyApp extends Application {

	
    public void showAbout(Context context) {
    	alert(context,"Name: Sajib Barua\n\nEmail: meet.sajib@gmail.com");
    }
    
    /*public String getGalleryPath() {
    	return "http://peladeschools.com.ng/images/mobile/bb/";
    }*/

    private static MyApp _this = null;
    public static String APP_NAME = null;
    
    Hashtable<String, Bitmap> _imgCache = new  Hashtable<String, Bitmap>();
    
	public Hashtable<String, Bitmap> getImgCache() {
		return _imgCache;
	}
	
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	private static final SimpleDateFormat timeFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm aa");
	private static final SimpleDateFormat dateDBFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static String getDateFormat(String dbDate) {
		try {
			Date date = dateDBFormat.parse(dbDate);
			return dateFormat.format(date);
		} catch (ParseException e) {
			return dbDate;
		}
	    
	}
	
	public static String getDBDate(Date date) {
		return dateDBFormat.format(date);
	}
	

    public void alert(Context context, String message) {
    	AlertDialog alertDialog = new AlertDialog.Builder(context).create();  
        alertDialog.setTitle("meConnect");  
        alertDialog.setMessage(message);    
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {  
          @Override
		public void onClick(DialogInterface dialog, int which) {  
            return;  
        } });  
        alertDialog.show();
    }

    public SharedPreferences getSetting() {
        return getSharedPreferences(APP_NAME, 0);
    }
    
    public void setSetting(String key, String value) {
        SharedPreferences settings = getSharedPreferences(APP_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        editor.commit();
    }
    
	public MyApp() {
		// TODO Auto-generated constructor stub

	}

    @Override
	public void onCreate(){
        super.onCreate();
        _this = this;
        APP_NAME = getResources().getString(R.string.app_name);
  
      
    }
  
  

    public static MyApp getInstance() {
        return _this;
    }
    
    
    
  /*  public void logout(Context context) {
		MyApp.getInstance().setSetting("group", "");
		context.startActivity(new Intent(context, LoginActivity.class));
		PollServer.stopPolling(context);
    }
    */
    public void setNotification() {
    	NotificationCompat.Builder mBuilder =
    	        new NotificationCompat.Builder(this)
    	        .setSmallIcon(R.drawable.ic_launcher)
    	        .setContentTitle("My Resturant")
    	        .setContentText("New Message");
    	
    	// Creates an explicit intent for an Activity in your app
    	Intent resultIntent = new Intent(this, SplashActivity.class);

    	// The stack builder object will contain an artificial back stack for the
    	// started Activity.
    	// This ensures that navigating backward from the Activity leads out of
    	// your application to the Home screen.
    	TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
    	// Adds the back stack for the Intent (but not the Intent itself)
    	stackBuilder.addParentStack(SplashActivity.class);
    	// Adds the Intent that starts the Activity to the top of the stack
    	stackBuilder.addNextIntent(resultIntent);
    	PendingIntent resultPendingIntent =
    	        stackBuilder.getPendingIntent(
    	            0,
    	            PendingIntent.FLAG_UPDATE_CURRENT
    	        );
    	mBuilder.setContentIntent(resultPendingIntent);
    	NotificationManager mNotificationManager =
    	    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    	// mId allows you to update the notification later on.
    	mNotificationManager.notify(0, mBuilder.build());
    }
    
    public void removeNotification() {
    	NotificationManager mNotificationManager =
        	    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    	mNotificationManager.cancel(0);
    }
    
}
