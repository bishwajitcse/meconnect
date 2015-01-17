package com.devprecise.meconnect.fragment;

import com.devprecise.meconnect.R;
import com.devprecise.meconnect.R.array;
import com.devprecise.meconnect.R.id;
import com.devprecise.meconnect.R.layout;
import com.devprecise.meconnect.RootActivity;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class AdminDashboardFragment extends Fragment{
	
	Typeface custom_fontbold,custom_fontregular,custom_fontmedium;
	Context mcontext;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	
		// Retrieving the currently selected item number
		//int position = getArguments().getInt("position");
		mcontext=container.getContext();
		((RootActivity)getActivity()).getSupportActionBar().setTitle("DashBoard");
		 //mcontext.getSupportActionBar().setTitle("Dashboard");
		// List of rivers
		String[] countries = getResources().getStringArray(R.array.countries);
		
		// Creating view correspoding to the fragment
		View v = inflater.inflate(R.layout.admindashboard_layout, container, false);


		 custom_fontbold= Typeface.createFromAsset(mcontext.getAssets(), "fonts/GothamRnd-Bold.otf");
		 custom_fontregular= Typeface.createFromAsset(mcontext.getAssets(), "fonts/GothamRnd-Light.otf");
		 custom_fontmedium= Typeface.createFromAsset(mcontext.getAssets(), "fonts/GothamRnd-Medium.otf");
		
		Button btnMembers=(Button)v.findViewById(R.id.btnMembers);
		btnMembers.setTypeface(custom_fontregular);
		Button btnConsultancy=(Button)v.findViewById(R.id.btnConsultancy);
		btnConsultancy.setTypeface(custom_fontregular);
		Button btnCollection=(Button)v.findViewById(R.id.btnCollection);
		btnCollection.setTypeface(custom_fontregular);
		Button btnteacher=(Button)v.findViewById(R.id.btnteacher);
		btnteacher.setTypeface(custom_fontregular);
		
		
		btnMembers.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				   // Creating a fragment object
		        MemberListFragment cFragment = new MemberListFragment();
		        // Getting reference to the FragmentManager
		        FragmentManager fragmentManager  = getFragmentManager();




                FragmentTransaction ft = fragmentManager.beginTransaction();

                ft.add(R.id.content_frame, cFragment);
                if(((RootActivity)getActivity()).fragmentStack.size()>0){
                    ft.hide(((RootActivity)getActivity()).fragmentStack.lastElement());
                }
                ((RootActivity)getActivity()).fragmentStack.push(cFragment);
                ft.commit();
			}
		});
		
		btnCollection.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				   // Creating a fragment object
				CollectionFragment cFragment = new CollectionFragment();
		        // Getting reference to the FragmentManager
		        FragmentManager fragmentManager  = getFragmentManager();

                FragmentTransaction ft = fragmentManager.beginTransaction();

                ft.add(R.id.content_frame, cFragment);
                if(((RootActivity)getActivity()).fragmentStack.size()>0){
                    ft.hide(((RootActivity)getActivity()).fragmentStack.lastElement());
                }
                ((RootActivity)getActivity()).fragmentStack.push(cFragment);
                ft.commit();
			}
		});
	
		
		return v;
	}
}