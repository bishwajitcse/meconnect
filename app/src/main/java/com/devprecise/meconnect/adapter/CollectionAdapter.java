package com.devprecise.meconnect.adapter;
import java.util.HashMap;
import java.util.List;

import com.devprecise.meconnect.R;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class CollectionAdapter extends SimpleAdapter {
	private int[] colors = new int[] { 0x3089a85, 0x30a8678 };
	View view;
	Context context;
	Typeface custom_fontbold,custom_fontregular,custom_fontmedium;
	public CollectionAdapter(Context context, List<HashMap<String, String>> items, int resource, String[] from, int[] to) {

		super(context, items, resource, from, to);
		this.context=context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		view = super.getView(position, convertView, parent);
		int colorPos = position % colors.length;
		view.setBackgroundColor(colors[colorPos]);
		custom_fontbold= Typeface.createFromAsset(context.getAssets(), "fonts/GothamRnd-Bold.otf");
		custom_fontregular= Typeface.createFromAsset(context.getAssets(), "fonts/GothamRnd-Light.otf");
		custom_fontmedium= Typeface.createFromAsset(context.getAssets(), "fonts/GothamRnd-Medium.otf");

		TextView txtCollection=(TextView)view.findViewById(R.id.txtCollection);
		txtCollection.setTypeface(custom_fontregular);

		return view;
	}


}