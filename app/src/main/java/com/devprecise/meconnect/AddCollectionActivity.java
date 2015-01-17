package com.devprecise.meconnect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.devprecise.meconnect.adapter.MemberAdapter;
import com.devprecise.meconnect.beanclass.CollectionTypeBean;
import com.devprecise.meconnect.beanclass.MemberBean;
import com.devprecise.meconnect.utils.Service;
import com.devprecise.meconnect.utils.Service.ResultJSONArray;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddCollectionActivity extends ActionBarActivity {
	Typeface custom_fontbold,custom_fontregular,custom_fontmedium;
	
	 private ArrayList<MemberBean> memberlist= new ArrayList<MemberBean>();
	 private ArrayList<CollectionTypeBean> collectiontype= new ArrayList<CollectionTypeBean>();
	 
	 Button btnSave;
	 Spinner spMember,spType,spMonth,spYear;
	 EditText txtAmt,txtmpesa;
	 String id;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_collection);
		custom_fontbold= Typeface.createFromAsset(this.getAssets(), "fonts/GothamRnd-Bold.otf");
		custom_fontregular= Typeface.createFromAsset(this.getAssets(), "fonts/GothamRnd-Light.otf");
		custom_fontmedium= Typeface.createFromAsset(this.getAssets(), "fonts/GothamRnd-Medium.otf");

		//getActionBar().setDisplayHomeAsUpEnabled(true);
	     getSupportActionBar().setDisplayHomeAsUpEnabled(true);     
	     
	     
	     spMember=(Spinner)findViewById(R.id.spMember);
	     spType=(Spinner)findViewById(R.id.spCollection);
	     spYear=(Spinner)findViewById(R.id.spYear);
	     spMonth=(Spinner)findViewById(R.id.spMonth);
	     btnSave=(Button)findViewById(R.id.button1);
	     txtAmt=(EditText)findViewById(R.id.textView1);
	     txtmpesa=(EditText)findViewById(R.id.textView1);
	     if(!getIntent().getStringExtra("name").toString().equalsIgnoreCase("")){
	    	 txtAmt.setText(getIntent().getStringExtra("amount"));
	    	id=getIntent().getStringExtra("id");
	    	 
	    	 String[] month = getResources().getStringArray(R.array.spmonthvalue);
	    	 spMonth.setSelection(Arrays.asList(month).indexOf(getIntent().getStringExtra("month")));
	    	 
	    	 String[] year = getResources().getStringArray(R.array.spyearvalue);
	    	 spYear.setSelection(Arrays.asList(year).indexOf(getIntent().getStringExtra("year")));
	     }
	     
	     
	     
	     btnSave.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				String memberid=memberlist.get((int)spMember.getSelectedItemId()).getId();
				String typeid=collectiontype.get((int)spType.getSelectedItemId()).getId();
				String amount=txtAmt.getText().toString();
				String months=spMonth.getSelectedItem().toString();
				String syears=spYear.getSelectedItem().toString();
			String mpesa=txtmpesa.getText().toString();
				
				//Toast.makeText(AddCollectionActivity.this, memberid,Toast.LENGTH_SHORT).show();

                String id= MyApp.getInstance().getSetting().getString("churchid","");
				if(getIntent().getStringExtra("name").equalsIgnoreCase("")){
					new Service(AddCollectionActivity.this).addCollection("api.php?f=addCollection",mpesa,memberid,typeid,amount,months,syears,
							new Service.ResultJSONObject() {
								public void completed(JSONObject json) {
									finish();
								}
							});
				}
				else{
					new Service(AddCollectionActivity.this).updateCollection("api.php?f=updateCollection",id,memberid,typeid,amount,months,syears,
							new Service.ResultJSONObject() {
								public void completed(JSONObject json) {
									finish();
								}
							});
				}
				
			}
		});
	     
	     
	     loadMemberData();
	     loadCollectionType();
	  
		
	}
	public void loadMemberData(){
        String id= MyApp.getInstance().getSetting().getString("churchid","");
		new Service(this,false).getRecords("api.php?f=getallmembers&id="+id, new ResultJSONArray() {

			public void completed(JSONArray records) throws Exception {
				// TODO Auto-generated method stub
				addlistview(records);
			}
		});
		
		
	}
	public void loadCollectionType(){
        String id= MyApp.getInstance().getSetting().getString("churchid","");
		new Service(this,false).getRecords("api.php?f=getallcolletiontype&id="+id, new ResultJSONArray() {

			public void completed(JSONArray records) throws Exception {
				// TODO Auto-generated method stub
				addlistview2(records);
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
	
	public void addlistview2(JSONArray records) throws Exception {
		int j = 0;
		List<String> listimage = new ArrayList<String>();
		List<String> listimage2 = new ArrayList<String>();
		   
        if(collectiontype.size()>0)
        	collectiontype.clear();
		for (int i = 0; i < records.length(); i++) {
			j++;
			JSONObject record = records.getJSONObject(i);
			 CollectionTypeBean cat = new CollectionTypeBean(record.getString("CollectTypeID").toString(),record.getString("Name").toString());
			 collectiontype.add(cat);
		}
		populateSpinner2();
		//lv.onLoadMoreComplete();
	}
	  
	public void addlistview(JSONArray records) throws Exception {
		int j = 0;
		List<String> listimage = new ArrayList<String>();
		List<String> listimage2 = new ArrayList<String>();
		   
        if(memberlist.size()>0)
        	memberlist.clear();
		for (int i = 0; i < records.length(); i++) {
			j++;
			JSONObject record = records.getJSONObject(i);
			 MemberBean cat = new MemberBean(record.getString("MemberID").toString(),record.getString("FullName").toString());
			 memberlist.add(cat);
			 
			 
		}
		populateSpinner();
		
	
		
		//lv.onLoadMoreComplete();
	}
	
	  /**
     * Adding spinner data
     * */
    private void populateSpinner() {
        List<String> lables = new ArrayList<String>();
         
      
 
        for (int i = 0; i < memberlist.size(); i++) {
            lables.add(memberlist.get(i).getName());
          
        }
 
        // Creating adapter for spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, lables);
 
        // Drop down layout style - list view with radio button
        spinnerAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
 
        // attaching data adapter to spinner
        spMember.setAdapter(spinnerAdapter);
        
        spMember.setSelection(spinnerAdapter.getPosition(getIntent().getStringExtra("name").toString()));
    }
    
	
	  /**
   * Adding spinner data
   * */
  private void populateSpinner2() {
      List<String> lables = new ArrayList<String>();
       
    

      for (int i = 0; i < collectiontype.size(); i++) {
          lables.add(collectiontype.get(i).getName());
      }

      // Creating adapter for spinner
      ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,
              android.R.layout.simple_spinner_item, lables);

      // Drop down layout style - list view with radio button
      spinnerAdapter
              .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

      // attaching data adapter to spinner
      spType.setAdapter(spinnerAdapter);
      spType.setSelection(spinnerAdapter.getPosition(getIntent().getStringExtra("collectiontype").toString()));
  }

}
