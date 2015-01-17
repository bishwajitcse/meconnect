package com.devprecise.meconnect;



import java.io.File;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import com.devprecise.meconnect.utils.Utils;




import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MemberDetailsActivity extends ActionBarActivity {
	Typeface custom_fontbold,custom_fontregular,custom_fontmedium;
	Bitmap image;
	String imgPath="";
	private static final int IMAGE_PICK 	= 1;
	private String selectedImagePath;
	ImageView imgGallery;
	 int i=0;
	 TextView txtname,txtEmail,txtPhone,txtEditSpouse,txtChild,txtgendar;
	ProgressDialog dialogp;
	CheckBox chMale,chFeMale;
	String gender="Male";
	DisplayImageOptions options;
	String ph;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ActionBar bar =   getSupportActionBar();
		//bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1fa4e5")));
		
		
		setContentView(R.layout.activity_member_details);
		
		custom_fontbold= Typeface.createFromAsset(this.getAssets(), "fonts/GothamRnd-Bold.otf");
		custom_fontregular= Typeface.createFromAsset(this.getAssets(), "fonts/GothamRnd-Light.otf");
		custom_fontmedium= Typeface.createFromAsset(this.getAssets(), "fonts/GothamRnd-Medium.otf");

		//getActionBar().setDisplayHomeAsUpEnabled(true);
	     getSupportActionBar().setDisplayHomeAsUpEnabled(true);     
		
		Intent i= getIntent();
		
		String name = i.getStringExtra("name");
		String email = i.getStringExtra("email");
		ph = i.getStringExtra("photo");
		String gender = i.getStringExtra("gender");
		String phone = i.getStringExtra("phone");
		String child = i.getStringExtra("child");
		
		txtname=(TextView)findViewById(R.id.txtName);
		txtname.setText("Name: "+name);
		txtname.setTypeface(custom_fontmedium);
		
		txtgendar=(TextView)findViewById(R.id.txtGendar);
		txtgendar.setText("Gender: "+gender);
		txtgendar.setTypeface(custom_fontmedium);
		
		txtEmail=(TextView)findViewById(R.id.txtEmail);
		txtEmail.setText("Email: "+email);
		txtEmail.setTypeface(custom_fontmedium);
		
		txtPhone=(TextView)findViewById(R.id.txtPhone);
		txtPhone.setText("Phone: "+phone);
		txtPhone.setTypeface(custom_fontmedium);
		
		txtChild=(TextView)findViewById(R.id.txtChild);
		txtChild.setText("Children: "+child);
		txtChild.setTypeface(custom_fontmedium);

	
		options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.preloader)
		.showImageForEmptyUri(R.drawable.preloader)
		.showImageOnFail(R.drawable.preloader)
		.cacheInMemory(true)
		.cacheOnDisk(true)
		.considerExifParams(true)
		
		.build();
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
		.threadPriority(Thread.NORM_PRIORITY - 2)
		.denyCacheImageMultipleSizesInMemory()
		.diskCacheFileNameGenerator(new Md5FileNameGenerator())
		.diskCacheSize(50 * 1024 * 1024) // 50 Mb
		.tasksProcessingOrder(QueueProcessingType.LIFO)
		.writeDebugLogs() // Remove for release app
		.build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);




		
		
		
		ImageView hotelImage = (ImageView)findViewById(R.id.imageView1);

		Button btnSave=(Button)findViewById(R.id.button1);
		btnSave.setTypeface(custom_fontmedium);
		
		btnSave.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i= getIntent();
				String name = i.getStringExtra("name");
				String email = i.getStringExtra("email");
				ph = i.getStringExtra("photo");
				String gender = i.getStringExtra("gender");
				String phone = i.getStringExtra("phone");
				String child = i.getStringExtra("child");
				String spouse = i.getStringExtra("spouse");
				
				 String married=i.getStringExtra("marriedstatus");
				 String dob=  i.getStringExtra("dob");
				 String district= i.getStringExtra("district");
				 String curlocation=  i.getStringExtra("curlocation");
				 String occupation=  i.getStringExtra("occupation");
				 String work= i.getStringExtra("work");
				 String idno= i.getStringExtra("Idno");
				 String nominee= i.getStringExtra("nominee");
				 String relationship= i.getStringExtra("relationship");
				 String address= i.getStringExtra("address");
				 String contact= i.getStringExtra("contact");
				 String mid= i.getStringExtra("mid");
				 
				 Intent intent = new Intent(MemberDetailsActivity.this,AddNewMemberActivity.class);
	             intent.putExtra("name", name);
	             intent.putExtra("email", email);
	             intent.putExtra("phone",phone);
	             intent.putExtra("gender",gender);
	             intent.putExtra("photo", ph);
	             intent.putExtra("child", child);
	             intent.putExtra("spouse", spouse);
	             intent.putExtra("marriedstatus",married);
				 intent.putExtra("dob",dob);
				 intent.putExtra("district",district);
				 intent.putExtra("curlocation",curlocation);
				 intent.putExtra("occupation",occupation);
				 intent.putExtra("work",work);
				 intent.putExtra("Idno",idno);
				 intent.putExtra("nominee",nominee);
				 intent.putExtra("relationship",relationship);
				 intent.putExtra("address",address);
				 intent.putExtra("contact",contact);
				 intent.putExtra("pimage", Utils.url+ph);
				 intent.putExtra("fimage", Utils.url+i.getStringExtra("fimage"));
		          intent.putExtra("mid", mid);
		          finish();
	             startActivity(intent);
			}
		});
		ImageLoader.getInstance().displayImage(Utils.url+ph, hotelImage, options,new SimpleImageLoadingListener() {
			@Override
			public void onLoadingStarted(String imageUri, View view) {
				
			}

			@Override
			public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
				
			}

			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
				
			}
		}, new ImageLoadingProgressListener() {
			@Override
			public void onProgressUpdate(String imageUri, View view, int current, int total) {
			
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
