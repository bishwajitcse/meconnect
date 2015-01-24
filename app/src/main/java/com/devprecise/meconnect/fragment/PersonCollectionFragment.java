package com.devprecise.meconnect.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.devprecise.meconnect.AddCollectionActivity;
import com.devprecise.meconnect.MyApp;
import com.devprecise.meconnect.R;
import com.devprecise.meconnect.RootActivity;
import com.devprecise.meconnect.adapter.MemberAdapter;
import com.devprecise.meconnect.beanclass.MemberBean;
import com.devprecise.meconnect.database.SofitelDbHelper;
import com.devprecise.meconnect.model.dbTbCollection;
import com.devprecise.meconnect.model.dbTbMemberInfo;
import com.devprecise.meconnect.utils.Service;
import com.devprecise.meconnect.utils.Service.ResultJSONArray;



import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class PersonCollectionFragment extends Fragment{
	
	Typeface custom_fontbold,custom_fontregular,custom_fontmedium;
	Context mcontext;
	ListView lv;
	ListAdapter adapter;
	ArrayList<HashMap<String, String>> assignment = new ArrayList<HashMap<String, String>>();
	 Spinner spMember,spType,spMonth,spYear;
    TextView txtAmt;
	 private ArrayList<MemberBean> memberlist= new ArrayList<MemberBean>();

    SofitelDbHelper db;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		// Retrieving the currently selected item number
		
		mcontext=container.getContext();
		db=new SofitelDbHelper(mcontext);
		((RootActivity)getActivity()).getSupportActionBar().setTitle("Collection");
		// Creating view correspoding to the fragment
		View v = inflater.inflate(R.layout.personalcollection_layout, container, false);
		
		
		 custom_fontbold= Typeface.createFromAsset(mcontext.getAssets(), "fonts/GothamRnd-Bold.otf");
		 custom_fontregular= Typeface.createFromAsset(mcontext.getAssets(), "fonts/GothamRnd-Light.otf");
		 custom_fontmedium= Typeface.createFromAsset(mcontext.getAssets(), "fonts/GothamRnd-Medium.otf");

        txtAmt=(TextView)v.findViewById(R.id.txtAmt1);

			lv = (ListView) v.findViewById(R.id.listView1);

        String id= MyApp.getInstance().getSetting().getString("churchid","");
			new Service(mcontext).getRecords("api.php?f=getAllCollection2&mid=&year&month=&id="+id, new ResultJSONArray() {

				public void completed(JSONArray records) throws Exception {
					// TODO Auto-generated method stub
					addlistview(records);
				}
			});



		// SetHasOptionsMenu(true);
		
		return v;
	}
	


	public void ordereditDialog() {

		// Create custom dialog object
		final Dialog dialog = new Dialog(mcontext);
		// Include dialog.xml file
		dialog.setContentView(R.layout.collection_search_dialog);
		// Set dialog title
		dialog.setTitle("Collection Search");
		((TextView)dialog.findViewById(android.R.id.title)).setTypeface(custom_fontmedium);

		dialog.show();
		Button btnSave = (Button) dialog.findViewById(R.id.button1);
		btnSave.setTypeface(custom_fontmedium);
		
		
		   spMember=(Spinner)dialog.findViewById(R.id.spMember);
		     loadMemberData();
		     spYear=(Spinner)dialog.findViewById(R.id.spYear);
		     spMonth=(Spinner)dialog.findViewById(R.id.spMonth);
		     
		     
		//final TextView txtType=(TextView)dialog.findViewById(R.id.editText1);
		//txtType.setTypeface(custom_fontregular);
		
		//Button btnCancel=(Button)dialog.findViewById(R.id.button2);
		
		///btnCancel.setTypeface(custom_fontmedium);
		btnSave.setTypeface(custom_fontmedium);
		
		
		
		
		// if decline button is clicked, close the custom dialog
		btnSave.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// Close dialog
		
		
				String memberid=memberlist.get((int)spMember.getSelectedItemId()).getId();
				String months=spMonth.getSelectedItemId()==0?"":spMonth.getSelectedItem().toString();
				String syears=spYear.getSelectedItemId()==0?"":spYear.getSelectedItem().toString();

                String id= MyApp.getInstance().getSetting().getString("churchid","");

				new Service(mcontext,false).getRecords("api.php?f=getAllCollection2&mid=&year&month=&id="+id+"&mid="+memberid+"&month="+months+"&year="+syears, new ResultJSONArray() {

					public void completed(JSONArray records) throws Exception {
						// TODO Auto-generated method stub
						addlistview(records);
						((BaseAdapter) lv.getAdapter()).notifyDataSetChanged();
						
					}
				});
				dialog.dismiss();
			}
				
		});
	
	}
	public void loadMemberData(){
		new Service(mcontext,false).getRecords("api.php?f=getallmembers", new ResultJSONArray() {

			public void completed(JSONArray records) throws Exception {
				// TODO Auto-generated method stub
				memberData(records);
			}
		});
		
		
	}
	public void memberData(JSONArray records) throws Exception {
		int j = 0;
		List<String> listimage = new ArrayList<String>();
		List<String> listimage2 = new ArrayList<String>();
		
		   
        if(memberlist.size()>0)
        	memberlist.clear();
		for (int i = 0; i < records.length(); i++) {
			
			j++;
			JSONObject record = records.getJSONObject(i);
			MemberBean cat;
			if(i==0){
				 cat = new MemberBean(0+"","All Members");
				 memberlist.add(cat);
				}
			
				  cat = new MemberBean(record.getString("MemberID").toString(),record.getString("FullName").toString());
			
			
			 memberlist.add(cat);
			 
			 
		}
	
		populateSpinner();
	
		
		//lv.onLoadMoreComplete();
	}
    private void populateSpinner() {
        List<String> lables = new ArrayList<String>();
         
      
   
 
        for (int i = 0; i < memberlist.size(); i++) {
        	
            lables.add(memberlist.get(i).getName());
          
        }
 
        // Creating adapter for spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(mcontext,
                android.R.layout.simple_spinner_item, lables);
 
        // Drop down layout style - list view with radio button
        spinnerAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
 
        // attaching data adapter to spinner
        spMember.setAdapter(spinnerAdapter);
        

    }

	  /*  @Override
	  public void onResume() {
	     Log.i("DEBUG", "onResume of HomeFragment");
          String id= MyApp.getInstance().getSetting().getString("churchid","");
	     new Service(mcontext,false).getRecords("api.php?f=getAllCollection2&mid=&year&month=&id="+id, new ResultJSONArray() {

				public void completed(JSONArray records) throws Exception {
					// TODO Auto-generated method stub
					addlistview(records);
					((BaseAdapter) lv.getAdapter()).notifyDataSetChanged();
				}
			});
	     super.onResume();
	  }
	  */

	  @Override
	  public void onPause() {
	     Log.i("DEBUG", "OnPause of HomeFragment");
	     super.onPause();
	  }
	  
	public void addlistview(JSONArray records) throws Exception {
        int j = 0;
        double sum = 0.0;
        List<String> listimage = new ArrayList<String>();
        List<String> listimage2 = new ArrayList<String>();

        if (assignment.size() > 0)
            assignment.clear();
        db.deleteAllCollection();

        for (int i = 0; i < records.length(); i++) {
            j++;
            JSONObject record = records.getJSONObject(i);

            dbTbCollection tb = new dbTbCollection();

            HashMap ass = new HashMap();

            tb.setID(i + 1);
            ass.put("id", Service.get(record, "CollectionID") + "");

            tb.setCollectionID(Service.get(record, "CollectionID"));

            ass.put("name", Service.get(record, "FullName") + "");
            tb.setMemberName(Service.get(record, "FullName"));
            ass.put("phone", Service.get(record, "Phone") + "");
            ass.put("email", Service.get(record, "Email") + "");
            ass.put("date", Service.get(record, "CreateDate") + "");
            tb.setDate(Service.get(record, "CreateDate"));
            ass.put("collectiontype", Service.get(record, "Name") + "");
            tb.setCollectionName(Service.get(record, "Name"));
            ass.put("amount", Service.get(record, "Amount") + "");
            sum += Float.parseFloat(Service.get(record, "Amount"));
            tb.setAmount(Service.get(record, "Amount"));
            ass.put("month", Service.get(record, "CMonth") + "");
            ass.put("year", Service.get(record, "CYear") + "");

            assignment.add(ass);

            db.createCollection(tb);


        }



        txtAmt.setText("Total Amount: "+sum+"");

		adapter = new MemberAdapter(mcontext, assignment,R.layout.personalcollection_list,
		new String[] { "name","amount"}, new int[] { R.id.textView5,R.id.textView7});

	   lv.setAdapter(adapter);
	    
	    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

	        @Override
	        public void onItemClick(AdapterView<?> parent, final View view,
	            int position, long id) {
	        	 Intent intent = new Intent(mcontext,AddCollectionActivity.class);
	             intent.putExtra("id", assignment.get(position).get("id"));
	             intent.putExtra("name", assignment.get(position).get("name"));
	             intent.putExtra("year", assignment.get(position).get("year"));
	             intent.putExtra("month", assignment.get(position).get("month"));
	             intent.putExtra("collectiontype", assignment.get(position).get("collectiontype"));
	             intent.putExtra("amount", assignment.get(position).get("amount"));
	           
	             startActivity(intent);
	            
			}
		});

		//lv.onLoadMoreComplete();
	}
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Auto-generated method stub
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.add_new_member, menu);

        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) mcontext.getSystemService(Context.SEARCH_SERVICE);

        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        // searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        // Assumes current activity is the searchable activity
        //  searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        // searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                String id= MyApp.getInstance().getSetting().getString("churchid","");
                List<dbTbCollection> allCollection=db.getAllCollection(id,s);
                double sum=0;
                if (assignment.size() > 0)
                    assignment.clear();

                for(int i=0;i<allCollection.size();i++){
                    HashMap ass = new HashMap();


                    System.out.println(allCollection.get(i).getAmount());

                    ass.put("id", allCollection.get(i).getCollectionID());



                    ass.put("name", allCollection.get(i).getMemberName());


                    ass.put("date",allCollection.get(i).getDate()+ "");

                    ass.put("collectiontype",allCollection.get(i).getCollectionName()+ "");

                    ass.put("amount", allCollection.get(i).getAmount() + "");
                    sum += Float.parseFloat(allCollection.get(i).getAmount());

                    assignment.add(ass);
                }
                txtAmt.setText("Total Amount: "+sum+"");
               // JSONArray records=new JSONArray();
                //addlistview(records,0);
                //addListData(allmember);
                adapter = new MemberAdapter(mcontext, assignment,R.layout.personalcollection_list,
                        new String[] { "name","amount"}, new int[] { R.id.textView5,R.id.textView7});

                lv.setAdapter(adapter);
               ( (BaseAdapter)lv.getAdapter()).notifyDataSetChanged();
               lv.invalidateViews();
              //  lv.refreshDrawableState();

                //Toast.makeText(mcontext,s,Toast.LENGTH_SHORT).show();
                return false;
            }
        });



    }
}