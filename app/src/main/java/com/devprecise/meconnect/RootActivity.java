package com.devprecise.meconnect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import com.devprecise.meconnect.adapter.DrawerViewAdapter;
import com.devprecise.meconnect.database.SofitelDbHelper;
import com.devprecise.meconnect.fragment.AdminDashboardFragment;
import com.devprecise.meconnect.fragment.CollectionTypeListFragment;
import com.devprecise.meconnect.fragment.MemberListFragment;
import com.devprecise.meconnect.fragment.PersonCollectionFragment;
import com.devprecise.meconnect.utils.Service;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class RootActivity extends ActionBarActivity {
	
	int mPosition = -1;	
	String mTitle = "";
	
	// Array of strings storing country names
    String[] mCountries ;
    
    // Array of integers points to images stored in /res/drawable-ldpi/
    int[] mFlags = new int[]{
                0,
               0,0,0,0,0,0
               
    };
    public Stack<Fragment> fragmentStack;
    // Array of strings to initial counts
    String[] mCount = new String[]{
        "", "", "", "", "", 
        "", "", "", "", "","" };
    SofitelDbHelper db;
	private DrawerLayout mDrawerLayout;	
	private ListView mDrawerList;	
	private ActionBarDrawerToggle mDrawerToggle;	
	private LinearLayout mDrawer ;	
	private List<HashMap<String,String>> mList ;	
	private SimpleAdapter mAdapter;	
	final private String COUNTRY = "country";	
	final private String FLAG = "flag";	
	final private String COUNT = "count";
	Typeface custom_fontbold,custom_fontregular,custom_fontmedium;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_root);
		
		
		 custom_fontbold= Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/GothamRnd-Bold.otf");
		 custom_fontregular= Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/GothamRnd-Light.otf");
		 custom_fontmedium= Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/GothamRnd-Medium.otf");
		ActionBar bar =   getSupportActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1fa4e5")));
		
		
		int titleId = getResources().getIdentifier("action_bar_title", "id",
	            "android");
	    TextView appname = (TextView) findViewById(titleId);
	    appname.setTextColor(getResources().getColor(R.color.black));
	    appname.setTypeface(custom_fontmedium);


        fragmentStack = new Stack<Fragment>();

        db=new SofitelDbHelper(this);
	    
	    TextView txtUserName=(TextView)findViewById(R.id.txt_user_name_drawer);
	    txtUserName.setTypeface(custom_fontmedium);
	    txtUserName.setText(MyApp.getInstance().getSetting().getString("username", ""));
	    
	    TextView txtEmail=(TextView)findViewById(R.id.txt_user_lastname_drawer);
	    txtEmail.setTypeface(custom_fontregular);
	    txtEmail.setText(MyApp.getInstance().getSetting().getString("email", ""));
	    
	    ImageView ImgDrawer=(ImageView)findViewById(R.id.ImgDrawer);
	    
	    ImgDrawer.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				 // Creating a fragment object
		        Fragment cFragment =null;
		      
		       cFragment= new AdminDashboardFragment();

		       // Creating a Bundle object
		        Bundle data = new Bundle();

		        // Setting the index of the currently selected item of mDrawerList
		        data.putInt("position", 0);

		        // Setting the position to the fragment
		        cFragment.setArguments(data);
		        // Getting reference to the FragmentManager
		        FragmentManager fragmentManager  = getSupportFragmentManager();

		        // Creating a fragment transaction
		        FragmentTransaction ft = fragmentManager.beginTransaction();

		        // Adding a fragment to the fragment transaction
		        ft.replace(R.id.content_frame, cFragment);
		    
		        // Committing the transaction
		        ft.commit();
		    	mDrawerLayout.closeDrawer(mDrawer);		
			}
		});
	    
		
		// Getting an array of country names
		mCountries = getResources().getStringArray(R.array.countries);
		
		// Title of the activity
		mTitle = (String)getTitle();
		
		// Getting a reference to the drawer listview
		mDrawerList = (ListView) findViewById(R.id.drawer_list);
		
		// Getting a reference to the sidebar drawer ( Title + ListView )
		mDrawer = ( LinearLayout) findViewById(R.id.drawer);
		
		// Each row in the list stores country name, count and flag
        mList = new ArrayList<HashMap<String,String>>();

        
        for(int i=0;i<7;i++){
            HashMap<String, String> hm = new HashMap<String,String>();
            hm.put(COUNTRY, mCountries[i]);
            hm.put(COUNT, mCount[i]);
            hm.put(FLAG, Integer.toString(mFlags[i]) );
            mList.add(hm);
        }

        // Keys used in Hashmap
        String[] from = { FLAG,COUNTRY,COUNT };

        // Ids of views in listview_layout
        int[] to = { R.id.flag , R.id.txtItemName , R.id.count};

        // Instantiating an adapter to store each items
        // R.layout.drawer_layout defines the layout of each item
        mAdapter = new DrawerViewAdapter(this, mList, R.layout.drawer_layout, from, to);
        
        // Getting reference to DrawerLayout
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);      
        
       
        
        // Creating a ToggleButton for NavigationDrawer with drawer event listener
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer , R.string.drawer_open,R.string.drawer_close){
        	
        	 /** Called when drawer is closed */
            public void onDrawerClosed(View view) {               
            	highlightSelectedCountry();            		
                supportInvalidateOptionsMenu();       
            }

            /** Called when a drawer is opened */
            public void onDrawerOpened(View drawerView) {            	
                getSupportActionBar().setTitle("Dashboard");            	
            	supportInvalidateOptionsMenu();                
            }
        };
        
        // Setting event listener for the drawer
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        
        // ItemClick event handler for the drawer items
        mDrawerList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				
				// Increment hit count of the drawer list item
				incrementHitCount(position);			
				 
				if(position < 7) { // Show fragment for countries : 0 to 4
					showFragment(position);
				}else{ // Show message box for countries : 5 to 9				
					Toast.makeText(getApplicationContext(), mCountries[position], Toast.LENGTH_LONG).show();
				}



				// Closing the drawer
				mDrawerLayout.closeDrawer(mDrawer);		
			}
		});
        
        
        // Enabling Up navigation
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);     
        
        getSupportActionBar().setDisplayShowHomeEnabled(true);        

        // Setting the adapter to the listView
        mDrawerList.setAdapter(mAdapter);   
        showFragment(0);
        
	}
/*
	@Override
	public void onBackPressed(){
	    final FragmentManager fm = getSupportFragmentManager();
	    if (fm.getBackStackEntryCount() > 0) {
	        Log.i("MainActivity", "popping backstack");
	        fm.popBackStack();
	    } else {
	        Log.i("MainActivity", "nothing on backstack, calling super");
	        super.onBackPressed();  
	    }
	 
	}*/
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (mDrawerToggle.onOptionsItemSelected(item)) {
	          return true;
	    }
		return super.onOptionsItemSelected(item);
	}	
			
	
	public void incrementHitCount(int position){
		HashMap<String, String> item = mList.get(position);
		String count = item.get(COUNT);
		item.remove(COUNT);
		if(count.equals("")){
			count = "  1  ";
		}else{
			int cnt = Integer.parseInt(count.trim());
			cnt ++;
			count = "  " + cnt + "  ";
		}				
		item.put(COUNT, count);				
		mAdapter.notifyDataSetChanged();
	}
	
	public void showFragment(int position){
		
		//Currently selected country
        mTitle = mCountries[position];	

        // Creating a fragment object
        Fragment cFragment =null;

       FragmentManager mFragmentManager = getSupportFragmentManager();
        if(position==0)
        	cFragment= new AdminDashboardFragment();
        else if(position==1)
        	cFragment= new MemberListFragment();
        else if(position==2)
        	cFragment= new CollectionTypeListFragment();
        else if(position==3)
        	cFragment= new PersonCollectionFragment();
        else if(position==6){

            MyApp.getInstance().setSetting("uflag", "");
            MyApp.getInstance().setSetting("username", "");
            MyApp.getInstance().setSetting("churchid", "");
            MyApp.getInstance().setSetting("email", "");

            db.delleteAllMember("1");
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        else
        	cFragment= new AdminDashboardFragment();



        //mFragmentManager.beginTransaction().replace(R.id.content_frame, mFragment).commit();

        if(cFragment!=null){

            FragmentTransaction ft = mFragmentManager.beginTransaction();

            if(fragmentStack.size()>0){
                ft.hide(fragmentStack.lastElement());
            }

            ft.add(R.id.content_frame, cFragment);
            fragmentStack.push(cFragment);
            ft.commit();
        }


	}
	
	// Highlight the selected country : 0 to 4
	public void highlightSelectedCountry(){
		int selectedItem = mDrawerList.getCheckedItemPosition();
    	
    	if(selectedItem > 4)
    		mDrawerList.setItemChecked(mPosition, true);
    	else
    		mPosition = selectedItem;
    	
    	if(mPosition!=-1)
    		getSupportActionBar().setTitle(mCountries[mPosition]);
	}

    @Override
    public void onBackPressed() {



        if (fragmentStack.size() > 1) {
            FragmentManager mFragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = mFragmentManager.beginTransaction();

            fragmentStack.lastElement().onPause();
            ft.remove(fragmentStack.pop());

            fragmentStack.lastElement().onResume();
            ft.show(fragmentStack.lastElement());
            Log.i("bbb",fragmentStack.lastElement()+"");
            ft.commit();


        } else {
            super.onBackPressed();
        }
    }

}