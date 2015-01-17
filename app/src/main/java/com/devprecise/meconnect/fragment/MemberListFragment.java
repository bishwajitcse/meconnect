package com.devprecise.meconnect.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.devprecise.meconnect.AddNewMemberActivity;
import com.devprecise.meconnect.MemberDetailsActivity;
import com.devprecise.meconnect.MyApp;
import com.devprecise.meconnect.R;
import com.devprecise.meconnect.RootActivity;
import com.devprecise.meconnect.adapter.MemberAdapter;
import com.devprecise.meconnect.database.SofitelDbHelper;
import com.devprecise.meconnect.model.dbTbMemberInfo;
import com.devprecise.meconnect.utils.FontHelper;
import com.devprecise.meconnect.utils.Service;
import com.devprecise.meconnect.utils.Service.ResultJSONArray;
import com.devprecise.meconnect.utils.Utils;


import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;

public class MemberListFragment extends Fragment{
	
	Typeface custom_fontbold,custom_fontregular,custom_fontmedium;
	Context mcontext;
	ListView lv;
	ListAdapter adapter;
	ArrayList<HashMap<String, String>> assignment = new ArrayList<HashMap<String, String>>();

    SofitelDbHelper db;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	
		// Retrieving the currently selected item number
		
		mcontext=container.getContext();
        db=new SofitelDbHelper(mcontext);
		((RootActivity)getActivity()).getSupportActionBar().setTitle("Members");
		// Creating view correspoding to the fragment
		View v = inflater.inflate(R.layout.memberlist_layout, container, false);
		
		
		 custom_fontbold= Typeface.createFromAsset(mcontext.getAssets(), "fonts/GothamRnd-Bold.otf");
		 custom_fontregular= Typeface.createFromAsset(mcontext.getAssets(), "fonts/GothamRnd-Light.otf");
		 custom_fontmedium= Typeface.createFromAsset(mcontext.getAssets(), "fonts/GothamRnd-Medium.otf");
		
		 Button btnAdd=(Button)v.findViewById(R.id.btnAdd);
		 
		 btnAdd.setTypeface(custom_fontmedium);
		 
			lv = (ListView) v.findViewById(R.id.listView1);
        FontHelper.applyFont(mcontext, v.findViewById(R.id.laymain), "fonts/GothamRnd-Medium.otf");


        String id= MyApp.getInstance().getSetting().getString("churchid","");
        List<dbTbMemberInfo> allmember=db.getAllMember(id,"");
        if(allmember.size()>0){

            addListData(allmember);


        }else {
            new Service(mcontext).getRecords("api.php?f=getallmembers&id="+id, new ResultJSONArray() {

                public void completed(JSONArray records) throws Exception {
                    // TODO Auto-generated method stub

                    insertDb(records);
                    //addlistview(records);
                }
            });
        }


			
		 
		 btnAdd.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
                Intent intent = new Intent(mcontext,AddNewMemberActivity.class);
                intent.putExtra("name", "");
                intent.putExtra("email", "");
                intent.putExtra("phone","");
                intent.putExtra("gender","male");
                intent.putExtra("photo", "");
                intent.putExtra("child", "");
                intent.putExtra("spouse", "");
                intent.putExtra("marriedstatus","");
                intent.putExtra("dob","");
                intent.putExtra("district","");
                intent.putExtra("curlocation","");
                intent.putExtra("occupation","");
                intent.putExtra("work","");
                intent.putExtra("Idno","");
                intent.putExtra("nominee","");
                intent.putExtra("relationship","");
                intent.putExtra("address","");
                intent.putExtra("contact","");
                intent.putExtra("pimage", "");
                intent.putExtra("fimage", "");
                intent.putExtra("mid", "");

                startActivity(intent);

			}
		});

        setHasOptionsMenu(true);


        return v;
	}

    public void addListData(  List<dbTbMemberInfo> allmember){
        if(assignment.size()>0)
            assignment.clear();
        for(int i=0;i<allmember.size();i++){

           // Log.i("dob",allmember.get(i).getDob());
            HashMap ass = new HashMap();
            ass.put("mid", allmember.get(i).getId()+"");
            ass.put("title", allmember.get(i).getName());
            ass.put("phone", allmember.get(i).getMobile());
            ass.put("gender", allmember.get(i).getGender());
            ass.put("spouse", allmember.get(i).getSpouse() + "");

            ass.put("email", allmember.get(i).getEmail()+ "");
            ass.put("photo",allmember.get(i).getProfileimage() + "");
            ass.put("children", allmember.get(i).getChild() + "");

            ass.put("marriedstatus",allmember.get(i).getMarrite()+ "");
            ass.put("dob", allmember.get(i).getDob()+ "");
            ass.put("district", allmember.get(i).getResidence() + "");
            ass.put("curlocation", allmember.get(i).getDistrictofbirth() + "");
            ass.put("occupation", allmember.get(i).getOcc()+ "");
            ass.put("work", allmember.get(i).getPlaceofwork() + "");

            ass.put("Idno", allmember.get(i).getIdno() + "");
            ass.put("nominee", allmember.get(i).getNominee() + "");
            ass.put("relationship", allmember.get(i).getRelation()+ "");
            ass.put("contact", allmember.get(i).getContact() + "");
            ass.put("address", allmember.get(i).getAddress() + "");
            ass.put("familyimage", allmember.get(i).getFamilyimage() + "");
            assignment.add(ass);


        }



        adapter = new MemberAdapter(mcontext, assignment,R.layout.member_list,
                new String[] { "title","phone"}, new int[] { R.id.textView4,R.id.textView5});



        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                Intent intent = new Intent(mcontext,HotelDetails.class);
                intent.putExtra("name", assignment.get(position).get("title"));
                intent.putExtra("email", assignment.get(position).get("email"));
                intent.putExtra("phone", assignment.get(position).get("phone"));
                intent.putExtra("gender", assignment.get(position).get("gender"));
                intent.putExtra("photo", assignment.get(position).get("photo"));
                intent.putExtra("child", assignment.get(position).get("children"));
                intent.putExtra("spouse", assignment.get(position).get("spouse"));


                intent.putExtra("marriedstatus", assignment.get(position).get("marriedstatus"));
                intent.putExtra("dob", assignment.get(position).get("dob"));
                intent.putExtra("district", assignment.get(position).get("district"));
                intent.putExtra("curlocation", assignment.get(position).get("curlocation"));
                intent.putExtra("occupation", assignment.get(position).get("occupation"));
                intent.putExtra("work", assignment.get(position).get("work"));
                intent.putExtra("Idno", assignment.get(position).get("Idno"));
                intent.putExtra("nominee", assignment.get(position).get("nominee"));
                intent.putExtra("relationship", assignment.get(position).get("relationship"));
                intent.putExtra("address", assignment.get(position).get("address"));
                intent.putExtra("contact", assignment.get(position).get("contact"));
                intent.putExtra("mid", assignment.get(position).get("mid").toString());
                intent.putExtra("fimage", assignment.get(position).get("familyimage"));

                startActivity(intent);

            }
        });

        lv.setOnItemLongClickListener(new OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {
                // TODO Auto-generated method stub

                // Toast.makeText(mcontext, "text", 2000).show();

                return true;
            }
        });

    }

    public void insertDb(JSONArray records){
        String id= MyApp.getInstance().getSetting().getString("churchid","");

        db.delleteAllMember(id);

        try{
            for (int i = 0; i < records.length(); i++) {

                JSONObject record = records.getJSONObject(i);

                dbTbMemberInfo tb = new dbTbMemberInfo();

                tb.setId(Integer.parseInt(Service.get(record, "MemberID").toString()));
                tb.setName(Service.get(record, "FullName") + "");
                tb.setMobile( Service.get(record, "Phone") + "");
                tb.setGender(Service.get(record,"Gender") + "");
                tb.setSpouse(Service.get(record, "Spouse") + "");

                tb.setEmail( Service.get(record, "Email") + "");
                tb.setProfileimage(Service.get(record, "Photo") + "");
                tb.setChild( Service.get(record, "Children") + "");

                tb.setMarrite( Service.get(record, "MarriedStatus") + "");
                tb.setDob( Service.get(record, "Dob") + "");
                //tb.setDob( "dob");
                tb.setDistrictofbirth( Service.get(record, "District") + "");
                tb.setResidence(Service.get(record, "CurLocation") + "");
               // tb.setResidence("res");
                tb.setOcc( Service.get(record, "Occupation") + "");
               // tb.setOcc("occ");
                tb.setPlaceofwork(Service.get(record, "work") + "");

                tb.setIdno( Service.get(record, "Idno") + "");
                //tb.setIdno("id");
                tb.setNominee( Service.get(record, "nominee") + "");
                //tb.setNominee("nominee");
                tb.setRelation( Service.get(record, "relationship") + "");
                tb.setContact( Service.get(record, "contact") + "");
                tb.setAddress(Service.get(record, "address") + "");
                tb.setFamilyimage(Service.get(record, "familyimage") + "");
                tb.setChurchid(id);
                long tag1_id = db.createHotelInfo(tb);
            }
        }
        catch (Exception e){

        }


    }
	
	/*  @Override
	  public void onResume() {
	     Log.i("DEBUG", "onResume of HomeFragment");
          String id= MyApp.getInstance().getSetting().getString("churchid","");
	     new Service(mcontext,false).getRecords("api.php?f=getallmembers&id="+id, new ResultJSONArray() {

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
                List<dbTbMemberInfo> allmember=db.getAllMember(id,s);

                for(int i=0;i<allmember.size();i++){
                    Log.i("search:",allmember.get(i).getName());
                }

               addListData(allmember);
                ( (BaseAdapter)lv.getAdapter()).notifyDataSetChanged();
                lv.invalidateViews();
                lv.refreshDrawableState();

                //Toast.makeText(mcontext,s,Toast.LENGTH_SHORT).show();
                return false;
            }
        });



    }

	  @Override
	  public void onPause() {
	     Log.i("DEBUG", "OnPause of HomeFragment");
	     super.onPause();
	  }
	  
	public void addlistview(JSONArray records) throws Exception {
		int j = 0;
		List<String> listimage = new ArrayList<String>();
		List<String> listimage2 = new ArrayList<String>();
		

		
		for (int i = 0; i < records.length(); i++) {
			j++;
			JSONObject record = records.getJSONObject(i);

		
			HashMap ass = new HashMap();
			ass.put("mid", Service.get(record, "MemberID") + "");
			ass.put("title", Service.get(record, "FullName") + "");
			ass.put("phone", Service.get(record, "Phone") + "");
			ass.put("gender", Service.get(record, "Gender") + "");
			ass.put("spouse", Service.get(record, "Spouse") + "");
			
			ass.put("email", Service.get(record, "Email") + "");
			ass.put("photo", Service.get(record, "Photo") + "");
			ass.put("children", Service.get(record, "Children") + "");
			
			ass.put("marriedstatus", Service.get(record, "MarriedStatus") + "");
			ass.put("dob", Service.get(record, "Dob") + "");
			ass.put("district", Service.get(record, "District") + "");
			ass.put("curlocation", Service.get(record, "CurLocation") + "");
			ass.put("occupation", Service.get(record, "Occupation") + "");
			ass.put("work", Service.get(record, "work") + "");
			
			ass.put("Idno", Service.get(record, "Idno") + "");
			ass.put("nominee", Service.get(record, "nominee") + "");
			ass.put("relationship", Service.get(record, "relationship") + "");
			ass.put("contact", Service.get(record, "contact") + "");
			ass.put("address", Service.get(record, "address") + "");
			ass.put("familyimage", Service.get(record, "familyimage") + "");
			assignment.add(ass);

		
		}

		adapter = new MemberAdapter(mcontext, assignment,R.layout.member_list,
			new String[] { "title","dob"}, new int[] { R.id.textView4,R.id.textView5});

	    lv.setAdapter(adapter);
	    
	    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

	        @Override
	        public void onItemClick(AdapterView<?> parent, final View view,
	            int position, long id) {
	        	 Intent intent = new Intent(mcontext,HotelDetails.class);
	             intent.putExtra("name", assignment.get(position).get("title"));
	             intent.putExtra("email", assignment.get(position).get("email"));
	             intent.putExtra("phone", assignment.get(position).get("phone"));
	             intent.putExtra("gender", assignment.get(position).get("gender"));
	             intent.putExtra("photo", assignment.get(position).get("photo"));
	             intent.putExtra("child", assignment.get(position).get("children"));
	             intent.putExtra("spouse", assignment.get(position).get("spouse"));
	      
	             
	             intent.putExtra("marriedstatus", assignment.get(position).get("marriedstatus"));
	             intent.putExtra("dob", assignment.get(position).get("dob"));
	             intent.putExtra("district", assignment.get(position).get("district"));
	             intent.putExtra("curlocation", assignment.get(position).get("curlocation"));
	             intent.putExtra("occupation", assignment.get(position).get("occupation"));
	             intent.putExtra("work", assignment.get(position).get("work"));
	             intent.putExtra("Idno", assignment.get(position).get("Idno"));
	             intent.putExtra("nominee", assignment.get(position).get("nominee"));
	             intent.putExtra("relationship", assignment.get(position).get("relationship"));
	             intent.putExtra("address", assignment.get(position).get("address"));
	             intent.putExtra("contact", assignment.get(position).get("contact"));
	             intent.putExtra("mid", assignment.get(position).get("mid"));
	             intent.putExtra("fimage", assignment.get(position).get("familyimage"));
	             
	             startActivity(intent);
	            
			}
		});
	    
	    lv.setOnItemLongClickListener(new OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                    int pos, long id) {
                // TODO Auto-generated method stub

              // Toast.makeText(mcontext, "text", 2000).show();

                return true;
            }
        });

		//lv.onLoadMoreComplete();
	}



}