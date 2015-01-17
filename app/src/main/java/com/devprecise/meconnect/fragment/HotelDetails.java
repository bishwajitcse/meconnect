/*
 * Copyright 2013 Javier Tarazaga
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.devprecise.meconnect.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.devprecise.meconnect.R;
import com.devprecise.meconnect.adapter.HotelDetailsAdapter;
import com.devprecise.meconnect.utils.FontHelper;
import com.devprecise.meconnect.utils.Utils;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class HotelDetails extends Activity {
    DisplayImageOptions options;
	private RelativeLayout mStickyView;
	private View mPlaceholderView;
	private ListView mListView;
	private ImageView mItemTop;
    String photo,phone,email,gender,child,spouse,married,dob,district,curlocation,occupation,work;
    String idno,nominee,relationship,address,contact,mid,fimage;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_hotel_details);

        Typeface  custom_fontmedium= Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/GothamRnd-Medium.otf");
        int titleId = getResources().getIdentifier("action_bar_title", "id",
                "android");
        TextView appname = (TextView) findViewById(titleId);
        appname.setTextColor(getResources().getColor(R.color.black));
        appname.setTypeface(custom_fontmedium);


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

		mStickyView = (RelativeLayout) findViewById(R.id.sticky);

		mListView = (ListView) findViewById(R.id.listView);
		mItemTop =(ImageView) findViewById(R.id.itemTop);



		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(R.layout.top_layout, null);

        Intent intent= getIntent();
        String name = intent.getStringExtra("name");
        email = intent.getStringExtra("email");
        photo = intent.getStringExtra("photo");
         gender = intent.getStringExtra("gender");
         phone = intent.getStringExtra("phone");
         child = intent.getStringExtra("child");



         spouse = intent.getStringExtra("spouse");
         married=intent.getStringExtra("marriedstatus");
         dob=  intent.getStringExtra("dob");
         district= intent.getStringExtra("district");
         curlocation=  intent.getStringExtra("curlocation");
         occupation=  intent.getStringExtra("occupation");
         work= intent.getStringExtra("work");
         idno= intent.getStringExtra("Idno");
         nominee= intent.getStringExtra("nominee");
         relationship= intent.getStringExtra("relationship");
         address= intent.getStringExtra("address");
         contact= intent.getStringExtra("contact");
         mid= intent.getStringExtra("mid");

        fimage= intent.getStringExtra("fimage");

        TextView txtMemberName=(TextView)findViewById(R.id.txtMemberName);
        txtMemberName.setText(name);

        FontHelper.applyFont(this, txtMemberName, "fonts/GothamRnd-Light.otf");

		mPlaceholderView = v.findViewById(R.id.placeholder);
		mListView.addHeaderView(v);

		mListView.getViewTreeObserver().addOnGlobalLayoutListener(
				new ViewTreeObserver.OnGlobalLayoutListener() {
					@SuppressLint("NewApi")
					@SuppressWarnings("deprecation")
					@Override
					public void onGlobalLayout() {
						onScrollChanged();

						ViewTreeObserver obs = mListView.getViewTreeObserver();
						if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
							obs.removeOnGlobalLayoutListener(this);
						} else {
							obs.removeGlobalOnLayoutListener(this);
						}
					}
				});

		mListView.setOnScrollListener(new AbsListView.OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				onScrollChanged();
			}
		});


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==0){
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) mItemTop.getDrawable();
                    Bitmap yourBitmap = bitmapDrawable.getBitmap();
                    categoryAdd(yourBitmap);
                }
            }
        });


        ImageLoader.getInstance().displayImage(Utils.url+photo, mItemTop, options,new SimpleImageLoadingListener() {
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

		// Create and set the adapter for the listView.
		SimpleAdapter simpleAdpt = new HotelDetailsAdapter(this, createListViewData(),createData(), R.layout.list_item, new String[] {"phone","email","gender"}, new int[] {R.id.txtPhone,R.id.txtlistEmail,R.id.txtlistGender});
		mListView.setAdapter(simpleAdpt);



	}

	/**
	 * Function used to calculate the position of the sticky view according to the position of the first item in the ListView.
	 */
	private void onScrollChanged() {
		View v = mListView.getChildAt(0);
		int top = (v == null) ? 0 : v.getTop();

		// This check is needed because when the first element reaches the top of the window, the top values from top are not longer valid. 
		if (mListView.getFirstVisiblePosition() == 0) {
			mStickyView.setTranslationY(
					Math.max(0, mPlaceholderView.getTop() + top));

			// Set the image to scroll half of the amount scrolled in the ListView.
			mItemTop.setTranslationY(top / 2);
		}			
	}

    public void categoryAdd(Bitmap yourBitmap) {

        // Create custom dialog object
        final Dialog dialog = new Dialog(this);
        // Include dialog.xml file
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.image_popup);
        // Set dialog title
        //dialog.setTitle("Category Add");

        ImageView imgView=(ImageView)dialog.findViewById(R.id.imageView1);


        Button btnClose=(Button)dialog.findViewById(R.id.button2);

       // btnClose.setTypeface(custom_font);

        btnClose.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dialog.cancel();
            }
        });

        imgView.setImageBitmap(yourBitmap);

        dialog.show();


    }

	/**
	 * Populate the ListView with example data.
	 * @return
	 */
	private List<Map<String, String>> createListViewData() {
		List<Map<String, String>> itemList = new ArrayList<Map<String,String>>();


			itemList.add(createItem("phone", phone));

		return itemList;
	}
    private HashMap<String, String> createData() {

        HashMap item=new HashMap();

        item.put("phone", phone);
        item.put("email", email);
        item.put("gender", gender);

        item.put("child", child);
        item.put("spouse", spouse);
        item.put("married", married);

        item.put("dob", dob);
        item.put("email", email);
        item.put("district", district);

        item.put("curlocation", curlocation);
        item.put("occupation", occupation);
        item.put("work", work);

        item.put("idno", idno);
        item.put("nominee", nominee);
        item.put("work", work);
        item.put("relationship", relationship);
        item.put("address", address);
        item.put("contact", contact);
        item.put("mid", mid);

        item.put("fimage", fimage);

        return item;
    }
	/**
	 * Function used to create the HashMap needed for ListView item using Simple Adapter.
	 * @param key
	 * @param name
	 * @return
	 */
	private HashMap<String, String> createItem(String key, String name) {
		HashMap<String, String> item = new HashMap<String, String>();
		item.put(key, name);

		return item;
	}

}
