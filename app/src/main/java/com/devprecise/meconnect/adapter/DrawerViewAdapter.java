package com.devprecise.meconnect.adapter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.devprecise.meconnect.R;
import com.devprecise.meconnect.R.id;


import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class DrawerViewAdapter extends SimpleAdapter {


	private int[] colors = new int[] { 0x3089a85, 0x30a8678 };
	View view;
	
	private List<String> mImageUrls;
	private List<String> outletimage;

	ImageView imgDiscount,ivUparrow;
	RelativeLayout relativeLayout1;
	FragmentManager fm;
	Context mContext;
	Typeface custom_fontbold,custom_fontregular,custom_fontmedium;
	
	
	public DrawerViewAdapter(Context context,
			List<? extends Map<String, ?>> data, int resource, String[] from,
			int[] to) {
		super(context, data, resource, from, to);
		// TODO Auto-generated constructor stub
		
		mContext=context;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {


		 custom_fontbold= Typeface.createFromAsset(mContext.getAssets(), "fonts/GothamRnd-Bold.otf");
		 custom_fontregular= Typeface.createFromAsset(mContext.getAssets(), "fonts/GothamRnd-Light.otf");
		 custom_fontmedium= Typeface.createFromAsset(mContext.getAssets(), "fonts/GothamRnd-Medium.otf");

		View view = super.getView(position, convertView, parent);
		 TextView txtItem=(TextView)view.findViewById(R.id.txtItemName);
	        
	        txtItem.setTypeface(custom_fontregular);
	      
		
		return view;

		/*	view = super.getView(position, convertView, parent);
        int colorPos = position % colors.length;
         view.setBackgroundColor(colors[colorPos]);
         return view;*/

	}


}