package com.devprecise.meconnect.adapter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.devprecise.meconnect.AddNewMemberActivity;
import com.devprecise.meconnect.R;
import com.devprecise.meconnect.utils.FontHelper;
import com.devprecise.meconnect.utils.Utils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class HotelDetailsAdapter extends SimpleAdapter {
	private int[] colors = new int[] { 0x30FF0000, 0x300000FF };
	View view;
	Context mContext;
    DisplayImageOptions options;
    HashMap<String, String> item ;
    ImageView mItemTop;
	public HotelDetailsAdapter(Context context,List<Map<String, String>> items,HashMap<String, String> item , int resource, String[] from, int[] to) {

		super(context, items, resource, from, to);
		mContext=context;
        this.item=item;
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.preloader)
                .showImageForEmptyUri(R.drawable.preloader)
                .showImageOnFail(R.drawable.preloader)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)

                .build();

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
        view = super.getView(position, convertView, parent);
        int colorPos = position % colors.length;
      //  view.setBackgroundColor(colors[colorPos]);
        FontHelper.applyFont(mContext, view.findViewById(R.id.layTop), "fonts/GothamRnd-Light.otf");
        TextView txtPhone=(TextView)view.findViewById(R.id.txtPhone);
        txtPhone.setText("Phone: "+ (item.get("phone")==null?" Not Available":item.get("phone")));

        TextView txtEmail=(TextView)view.findViewById(R.id.txtlistEmail);
        txtEmail.setText("Email: "+ (item.get("email")==null?" Not Available":item.get("email")));

        TextView txtgender=(TextView)view.findViewById(R.id.txtlistGender);
        txtgender.setText("Gender: "+ (item.get("gender")==null?" Not Available":item.get("gender")));



        TextView txtOco=(TextView)view.findViewById(R.id.txtOco);
        txtOco.setText("Gender: "+ (item.get("occupation")==null?" Not Available":item.get("occupation")));

        TextView txtOccupation=(TextView)view.findViewById(R.id.txtOco);
        txtOccupation.setText("Occupation: "+ (item.get("occupation")==null?" Not Available":item.get("occupation")));


        TextView txtID=(TextView)view.findViewById(R.id.txtID);
        txtID.setText("ID: "+ (item.get("idno")==null?" Not Available":item.get("idno")));

        TextView txtAddress=(TextView)view.findViewById(R.id.txtAddress);
        txtAddress.setText("Address: "+ (item.get("address")==null?" Not Available":item.get("address")));

        TextView txtdob=(TextView)view.findViewById(R.id.txtdob);
        txtdob.setText("DOB: "+ (item.get("dob")==null?" Not Available":item.get("dob")));

        TextView spouse=(TextView)view.findViewById(R.id.spouse);
        spouse.setText("Spouse: "+ (item.get("spouse")==null?" Not Available":item.get("spouse")));

        TextView txtDistrict=(TextView)view.findViewById(R.id.txtDistrict);
        txtDistrict.setText("District: "+ (item.get("district")==null?" Not Available":item.get("district")));

        TextView txtNominee=(TextView)view.findViewById(R.id.txtNominee);
        txtNominee.setText("Nominee: "+ (item.get("nominee")==null?" Not Available":item.get("nominee")));

        TextView txtRelation=(TextView)view.findViewById(R.id.txtRelationship);
        txtRelation.setText("Relationship: "+ (item.get("relationship")==null?" Not Available":item.get("relationship")));

        TextView txtnContact=(TextView)view.findViewById(R.id.txtnContact);
        txtnContact.setText("Contact: "+ (item.get("contact")==null?" Not Available":item.get("contact")));

        final String photo=item.get("fimage");

         mItemTop=(ImageView)view.findViewById(R.id.txtfimage);


        Button btnEdit=(Button)view.findViewById(R.id.button1);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent i= getIntent();
                String name = item.get("name");
                String email = item.get("email");
                //photo = i.getStringExtra("photo");
                String gender = item.get("gender");
                String phone = item.get("phone");
                String child = item.get("child");
                String spouse = item.get("spouse");

                String married=item.get("marriedstatus");
                String dob=  item.get("dob");
                String district= item.get("district");
                String curlocation=  item.get("curlocation");
                String occupation=  item.get("occupation");
                String work= item.get("work");
                String idno= item.get("Idno");
                String nominee= item.get("nominee");
                String relationship= item.get("relationship");
                String address= item.get("address");
                String contact= item.get("contact");
                String mid= item.get("mid");

                Intent intent = new Intent(mContext,AddNewMemberActivity.class);

                intent.putExtra("name", name);
                intent.putExtra("email", email);
                intent.putExtra("phone",phone);
                intent.putExtra("gender",gender);
                intent.putExtra("photo", photo);
                intent.putExtra("child", child);
                intent.putExtra("spouse", spouse);
                intent.putExtra("marriedstatus",married);
                intent.putExtra("dob",dob);
                intent.putExtra("district",district);
                intent.putExtra("curlocation",curlocation);
                intent.putExtra("occupation",occupation);
                intent.putExtra("work",work);
                intent.putExtra("Idno",idno);
                intent.putExtra("nominee",nominee);
                intent.putExtra("relationship",relationship);
                intent.putExtra("address",address);
                intent.putExtra("contact",contact);
                intent.putExtra("pimage", Utils.url+photo);
                intent.putExtra("fimage", Utils.url+photo);
                intent.putExtra("mid", mid);



                mContext.startActivity(intent);
            }
        });

        mItemTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ImageView imageView = (ImageView) view.findViewById(R.id.image);
                 BitmapDrawable bitmapDrawable = (BitmapDrawable) mItemTop.getDrawable();
                 Bitmap yourBitmap = bitmapDrawable.getBitmap();
                categoryAdd(yourBitmap);
                //Toast.makeText(mContext, "a", Toast.LENGTH_LONG).show();
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
       /* item.put("child", child);

        item.put("married", married);


        item.put("email", email);
        item.put("", district);



        item.put("work", work);


        item.put("nominee", nominee);
        item.put("work", work);
        item.put("relationship", relationship);

        item.put("contact", contact);
        item.put("mid", mid);
*/
        return view;
	}
    public void categoryAdd(Bitmap yourBitmap) {

        // Create custom dialog object
        final Dialog dialog = new Dialog(mContext);
        // Include dialog.xml file
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.image_popup);
        // Set dialog title
        //dialog.setTitle("Category Add");

        ImageView imgView=(ImageView)dialog.findViewById(R.id.imageView1);


        Button btnClose=(Button)dialog.findViewById(R.id.button2);
        FontHelper.applyFont(mContext, btnClose, "fonts/GothamRnd-Medium.otf");
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

}