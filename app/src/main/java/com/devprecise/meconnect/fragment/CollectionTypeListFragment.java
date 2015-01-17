package com.devprecise.meconnect.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.devprecise.meconnect.MyApp;
import com.devprecise.meconnect.R;
import com.devprecise.meconnect.adapter.CollectionAdapter;
import com.devprecise.meconnect.utils.Service;
import com.devprecise.meconnect.utils.Service.ResultJSONArray;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class CollectionTypeListFragment extends Fragment{
	
	Typeface custom_fontbold,custom_fontregular,custom_fontmedium;
	Context mcontext;
	ListView lv;
	ListAdapter adapter;
	ArrayList<HashMap<String, String>> assignment = new ArrayList<HashMap<String, String>>();
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	
		// Retrieving the currently selected item number
		
		mcontext=container.getContext();
		
	
		// Creating view correspoding to the fragment
		View v = inflater.inflate(R.layout.collectionlist_layout, container, false);
		
		
		 custom_fontbold= Typeface.createFromAsset(mcontext.getAssets(), "fonts/GothamRnd-Bold.otf");
		 custom_fontregular= Typeface.createFromAsset(mcontext.getAssets(), "fonts/GothamRnd-Light.otf");
		 custom_fontmedium= Typeface.createFromAsset(mcontext.getAssets(), "fonts/GothamRnd-Medium.otf");
		
		 TextView txtHead=(TextView)v.findViewById(R.id.textView1);
		 txtHead.setTypeface(custom_fontmedium);
		 Button btnAdd=(Button)v.findViewById(R.id.btnAdd);
		 
		 btnAdd.setTypeface(custom_fontmedium);
		 
			lv = (ListView) v.findViewById(R.id.listView1);
        String id= MyApp.getInstance().getSetting().getString("churchid","");

			new Service(mcontext).getRecords("api.php?f=getallcolletiontype&id="+id, new ResultJSONArray() {

				public void completed(JSONArray records) throws Exception {
					// TODO Auto-generated method stub
					addlistview(records);
				}
			});
			
		 
		 btnAdd.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ordereditDialog("","");
				//startActivity(new Intent(mcontext, AddNewMemberActivity.class));
				//mcontext.overridePendingTransition(R.anim.activity_open_translate,R.anim.activity_close_scale);
				
			}
		});
		
	
		
		return v;
	}

	public void ordereditDialog(final String id,String value) {

		// Create custom dialog object
		final Dialog dialog = new Dialog(mcontext);
		// Include dialog.xml file
		dialog.setContentView(R.layout.collection_dialog);
		// Set dialog title
		dialog.setTitle("Collection Type");
		((TextView)dialog.findViewById(android.R.id.title)).setTypeface(custom_fontmedium);

		dialog.show();
		Button btnSave = (Button) dialog.findViewById(R.id.button1);
		btnSave.setTypeface(custom_fontmedium);
		
		final TextView txtType=(TextView)dialog.findViewById(R.id.editText1);
		txtType.setTypeface(custom_fontregular);
		
		Button btnCancel=(Button)dialog.findViewById(R.id.button2);
		
		btnCancel.setTypeface(custom_fontmedium);
		btnSave.setTypeface(custom_fontmedium);
		
		txtType.setText(value);
		
		btnCancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		
		// if decline button is clicked, close the custom dialog
		btnSave.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// Close dialog
				String msg = "";
				boolean flag = false;
				if (!txtType.getText().toString().trim().equalsIgnoreCase("")) {
					if(id.equalsIgnoreCase("")){
                        String id= MyApp.getInstance().getSetting().getString("churchid","");
						new Service(mcontext).addCollectionType("api.php?f=addcollectiontype", txtType.getText().toString(),
								new Service.ResultJSONObject() {
									public void completed(JSONObject json) {
										dialog.dismiss();
									}
								});
						
						new Service(mcontext,false).getRecords("api.php?f=getallcolletiontype&id="+id, new ResultJSONArray() {

							public void completed(JSONArray records) throws Exception {
								// TODO Auto-generated method stub
								addlistview(records);
								((BaseAdapter) lv.getAdapter()).notifyDataSetChanged();
							}
						});
					}
					else{
						new Service(mcontext).updateCollectionType("api.php?f=updatecollectiontype", txtType.getText().toString(),id,
								new Service.ResultJSONObject() {
									public void completed(JSONObject json) {
										dialog.dismiss();
									}
								});
						new Service(mcontext,false).getRecords("api.php?f=getallcolletiontype&id="+id, new ResultJSONArray() {

							public void completed(JSONArray records) throws Exception {
								// TODO Auto-generated method stub
								addlistview(records);
								((BaseAdapter) lv.getAdapter()).notifyDataSetChanged();
							}
						});
					}
				
					flag=true;
					
				} else {
					msg = "Please Enter Valid Type!";
				}
				if (!flag)
					MyApp.getInstance().alert(mcontext, msg);
		
			}
		});
	
	}
	public void addlistview(JSONArray records) throws Exception {
		int j = 0;
		List<String> listimage = new ArrayList<String>();
		List<String> listimage2 = new ArrayList<String>();
		
		
		if(assignment.size()>0)
			assignment.clear();
		
		for (int i = 0; i < records.length(); i++) {
			j++;
			JSONObject record = records.getJSONObject(i);

		
			HashMap ass = new HashMap();
			ass.put("title", Service.get(record, "Name") + "");
			ass.put("typeid", Service.get(record, "CollectTypeID") + "");
			assignment.add(ass);
		}

		adapter = new CollectionAdapter(mcontext, assignment,R.layout.collection_list,
			new String[] { "title"}, new int[] { R.id.txtCollection});

	    lv.setAdapter(adapter);
	    
	    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

	        @Override
	        public void onItemClick(AdapterView<?> parent, final View view,
	            int position, long id) {
	        	
	        	ordereditDialog(assignment.get(position).get("typeid"),assignment.get(position).get("title"));
	            
			}
		});

		//lv.onLoadMoreComplete();
	}
	
	public boolean nullCheck() {
		boolean flag = false;
		String msg = "";
		
		return flag;
	}
}