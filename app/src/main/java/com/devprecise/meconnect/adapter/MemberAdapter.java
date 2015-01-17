package com.devprecise.meconnect.adapter;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.devprecise.meconnect.R;
import com.devprecise.meconnect.utils.FontHelper;

public class MemberAdapter extends SimpleAdapter {
    private int[] colors = new int[] { 0x3089a85, 0x30a8678 };
    Context mcontext;
    View view;
    Typeface custom_fontbold,custom_fontregular,custom_fontmedium;
    public MemberAdapter(Context context, List<HashMap<String, String>> items, int resource, String[] from, int[] to) {
        super(context, items, resource, from, to);
        mcontext=context;
    }
 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    	view = super.getView(position, convertView, parent);
     int colorPos = position % colors.length;
      view.setBackgroundColor(colors[colorPos]);


        FontHelper.applyFont(mcontext, view.findViewById(R.id.layMain), "fonts/GothamRnd-Light.otf");

        return view;
    }
    
 
}