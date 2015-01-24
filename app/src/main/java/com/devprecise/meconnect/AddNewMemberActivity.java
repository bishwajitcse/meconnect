package com.devprecise.meconnect;



import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import com.devprecise.meconnect.database.SofitelDbHelper;
import com.devprecise.meconnect.model.dbTbMemberInfo;
import com.devprecise.meconnect.utils.Service;
import com.devprecise.meconnect.utils.Utils;
import com.nexa.adapter.DatePickerDialogFragment;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;


import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;


import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AddNewMemberActivity extends Activity {
	Typeface custom_fontbold,custom_fontregular,custom_fontmedium;
	//Bitmap image;
	String imgPath="",imgPath2="",imgPath3="";
	private static final int IMAGE_PICK 	= 1;
	private static final int IMAGE_PICK2	= 2;
    private static final int IMAGE_PICK3	= 3;
	private String selectedImagePath;
	ImageView imgGallery,imgfamily,imgSpouse;
	int i=0;
	EditText txtname,txtEmail,txtPhone,txtEditSpouse,txtChild,txtcontact;

	EditText txtbirthplace,txtcurlocation,txtoccupation,txtwork,txtidno,txtaddress,txtkin;
	Spinner spRelation,spMarried;
	DatePickerDialogFragment txtdob;
	String pimage,fimage;
	ProgressDialog dialogp;
	CheckBox chMale,chFeMale;
	TextView txtfamilyPhoto;
	String gender="Male";
	DisplayImageOptions a;
	DisplayImageOptions options;
	String name="",mid="";

    SofitelDbHelper db;
    TextView txtSpouseImageTitle;
    private static final String[] peopele = new String[] { "Married",
            "Single"};

    private static final String[] relation = new String[] { "Relationship",
            "Brother","Son","Father","Friend","Mother","Sister","Wife"};

    Typeface custom_font;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_add_new_member);

        custom_font = Typeface.createFromAsset(this.getAssets(), "fonts/GothamRnd-Light.otf");
        db=new SofitelDbHelper(this);
		Intent in= getIntent();
		 name = in.getStringExtra("name");
		 mid = in.getStringExtra("mid");
		String email = in.getStringExtra("email");
		String ph = in.getStringExtra("photo");
		String gender = in.getStringExtra("gender");
		String phone = in.getStringExtra("phone");
		String child = in.getStringExtra("child");
		String married=in.getStringExtra("marriedstatus");
		String dob=  in.getStringExtra("dob");
		String district= in.getStringExtra("district");
		String curlocation=  in.getStringExtra("curlocation");
		String occupation=  in.getStringExtra("occupation");
		String work= in.getStringExtra("work");
		String idno= in.getStringExtra("Idno");
		String nominee= in.getStringExtra("nominee");
		String relationship= in.getStringExtra("relationship");
		String address= in.getStringExtra("address");
		String contact= in.getStringExtra("contact");
		pimage= in.getStringExtra("pimage");
		fimage= in.getStringExtra("fimage");
		
		custom_fontbold= Typeface.createFromAsset(this.getAssets(), "fonts/GothamRnd-Bold.otf");
		custom_fontregular= Typeface.createFromAsset(this.getAssets(), "fonts/GothamRnd-Light.otf");
		custom_fontmedium= Typeface.createFromAsset(this.getAssets(), "fonts/GothamRnd-Medium.otf");


       final dbTbMemberInfo tbmember= db.getMember(mid);


		txtname=(EditText)findViewById(R.id.txtName);
		txtname.setText(tbmember.getName());

		txtname.setTypeface(custom_fontregular);

		txtEmail=(EditText)findViewById(R.id.txtEmail);
		txtEmail.setText(tbmember.getEmail());
		txtEmail.setTypeface(custom_fontregular);
		txtPhone=(EditText)findViewById(R.id.txtMobile);
		txtPhone.setText(tbmember.getMobile());
		txtPhone.setTypeface(custom_fontregular);
		txtChild=(EditText)findViewById(R.id.txtChild);
		txtChild.setTypeface(custom_fontregular);
		txtChild.setText(tbmember.getChild());

		chMale=(CheckBox)findViewById(R.id.chMale);
		chMale.setTypeface(custom_fontregular);
		chFeMale=(CheckBox)findViewById(R.id.chFemale);
		chFeMale.setTypeface(custom_fontregular);


		txtfamilyPhoto=(TextView)findViewById(R.id.txtfamilyimage);
		txtfamilyPhoto.setTypeface(custom_fontbold);
		
		

		
		

        if(tbmember.getGender()!=null)
		if(tbmember.getGender().equalsIgnoreCase("Male")){
			chMale.setChecked(true);
		}
		else{
			chFeMale.setChecked(true);
		}
        else
           chMale.setChecked(true);

		txtbirthplace=(EditText)findViewById(R.id.txtbirthPlace);
		txtbirthplace.setTypeface(custom_fontregular);
		txtbirthplace.setText(tbmember.getDistrictofbirth());

		txtcurlocation=(EditText)findViewById(R.id.txtCurrectLocation);
		txtcurlocation.setTypeface(custom_fontregular);
		txtcurlocation.setText(tbmember.getResidence());

		txtoccupation=(EditText)findViewById(R.id.txtOccupation);

		txtoccupation.setTypeface(custom_fontregular);
		txtoccupation.setText(tbmember.getOcc());
		txtwork=(EditText)findViewById(R.id.txtworkplace);
		txtwork.setTypeface(custom_fontregular);
		txtwork.setText(tbmember.getPlaceofwork());
		txtidno=(EditText)findViewById(R.id.txtid);
		txtidno.setText(tbmember.getIdno());
		txtidno.setTypeface(custom_fontregular);
		txtaddress=(EditText)findViewById(R.id.txtAddress);
		txtaddress.setTypeface(custom_fontregular);
		txtaddress.setText(tbmember.getAddress());
		txtkin=(EditText)findViewById(R.id.txtNominee);
		txtkin.setText(tbmember.getNominee());
		txtkin.setTypeface(custom_fontregular);

		txtcontact=(EditText)findViewById(R.id.txtContact);
		txtcontact.setTypeface(custom_fontregular);
		txtcontact.setText(tbmember.getContact());
		txtdob=(DatePickerDialogFragment)findViewById(R.id.txtdob);
		spRelation=(Spinner)findViewById(R.id.spRelation);

        imgSpouse=(ImageView)findViewById(R.id.imgSpouse);
        txtSpouseImageTitle=(TextView)findViewById(R.id.imgSpouseTitle);
        txtSpouseImageTitle.setTypeface(custom_fontbold);

        MyRelationAdpater myrealtion = new MyRelationAdpater(this);

        spRelation.setAdapter(myrealtion);

		if(nominee.equalsIgnoreCase("")){
			String[] nomine = getResources().getStringArray(R.array.nomine);
			spRelation.setSelection(Arrays.asList(nomine).indexOf(nominee));

		}

		spMarried=(Spinner)findViewById(R.id.spMarried);

        MyArrayAdapter ma = new MyArrayAdapter(this);
        spMarried.setAdapter(ma);


		txtdob.setTypeface(custom_fontregular);
		txtdob.setText(tbmember.getDob());

		chFeMale.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				if (chMale.isChecked()) {
					chMale.setChecked(false);

				}
			}
		});
		chMale.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub




				if (chFeMale.isChecked()) {
					chFeMale.setChecked(false);

				}
			}
		});



		Button btnSave=(Button)findViewById(R.id.button1);
		TextView txtPhoto=(TextView)findViewById(R.id.txtPhoto);
		txtPhoto.setTypeface(custom_fontbold);
		final TextView txtSpouse=(TextView)findViewById(R.id.txtSpose);	
		txtSpouse.setTypeface(custom_fontbold);
		txtEditSpouse=(EditText)findViewById(R.id.txteditSpouse);
		txtEditSpouse.setTypeface(custom_fontregular);
        txtEditSpouse.setText(tbmember.getSpouse());

		options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.preloader)
		.showImageForEmptyUri(R.drawable.preloader)
		.showImageOnFail(R.drawable.preloader)
		.cacheInMemory(true)
		.cacheOnDisk(true)
		.considerExifParams(true)
		
		.build();

		btnSave.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(nullCheck()){
					if(tbmember.getName().equalsIgnoreCase("")){
						dialogp = ProgressDialog.show(AddNewMemberActivity.this, "", "Add Profile Information...", true);
						new Thread(new Runnable() {
							public void run() {
								try {
									if(SendMultipartFile()){
										Log.i("ok upload","ok");
										dialogp.dismiss();
                                        String id= MyApp.getInstance().getSetting().getString("churchid","");
                                        new Service(getApplicationContext(),false).getRecords("api.php?f=getallmembers&id="+id, new Service.ResultJSONArray() {

                                            public void completed(JSONArray records) throws Exception {
                                                // TODO Auto-generated method stub

                                                insertDb(records);
                                                finish();
                                                //addlistview(records);
                                            }
                                        });



									}
									else
									{
										Log.i("failed upload","failed");
										dialogp.dismiss();
									}

								} catch (ClientProtocolException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
									Log.i("error upload",e+"");
									dialogp.dismiss();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
									Log.i("error upload",e+"");
									dialogp.dismiss();
								}
							}
						}).start(); 
					}else{
						dialogp = ProgressDialog.show(AddNewMemberActivity.this, "", "Update Profile Information...", true);
						new Thread(new Runnable() {
							public void run() {
								try {
									if(UpdateMultipartFile()){
										Log.i("ok upload","ok");
										dialogp.dismiss();
                                        String id= MyApp.getInstance().getSetting().getString("churchid","");
                                        new Service(getApplicationContext(),false).getRecords("api.php?f=getallmembers&id="+id, new Service.ResultJSONArray() {

                                            public void completed(JSONArray records) throws Exception {
                                                // TODO Auto-generated method stub

                                                insertDb(records);
                                                finish();
                                                //addlistview(records);
                                            }
                                        });
										//finish();
									}
									else
									{
										Log.i("failed upload","failed");
										dialogp.dismiss();
									}

								} catch (ClientProtocolException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
									Log.i("error upload",e+"");
									dialogp.dismiss();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
									Log.i("error upload",e+"");
									dialogp.dismiss();
								}
							}
						}).start();
					}
					

				}
			}
		});

		txtSpouse.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(i==0){
					txtEditSpouse.setVisibility(View.VISIBLE);
					i=1;
					txtSpouse.setText("Remove Spouse");
				}
				else{
					i=0;
					txtSpouse.setText("Add Spouse");
					txtEditSpouse.setVisibility(View.GONE);
				}
			}
		});


		imgGallery=(ImageView)findViewById(R.id.imageView1);
		txtPhoto.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub


				Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				intent.setType("image/*");
				startActivityForResult(Intent.createChooser(intent, "Sofitel"), IMAGE_PICK);

			}
		});

		imgfamily=(ImageView)findViewById(R.id.imgfamily);
		txtfamilyPhoto.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub


				Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				intent.setType("image/*");
				startActivityForResult(Intent.createChooser(intent, "Sofitel"), IMAGE_PICK2);

			}
		});

        //imgSpouse=(ImageView)findViewById(R.id.imgSpouse);
        txtSpouseImageTitle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub


                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Sofitel"), IMAGE_PICK3);

            }
        });

		if(!pimage.equalsIgnoreCase("")){
			ImageLoader.getInstance().displayImage(pimage, imgGallery, options,new SimpleImageLoadingListener() {
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

		}

		if(!fimage.equalsIgnoreCase("")){
			ImageLoader.getInstance().displayImage(fimage, imgfamily, options,new SimpleImageLoadingListener() {
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

		}

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

	public boolean SendMultipartFile() throws ClientProtocolException, IOException {

		String upLoadServerUri=Utils.url+"api.php?f=registration";
		HttpClient httpClient = new DefaultHttpClient();
		Log.i("url", upLoadServerUri);
		HttpPost request = new HttpPost(upLoadServerUri);
		MultipartEntity entity = new MultipartEntity();

		if(!imgPath.equals(""))
			entity.addPart("uploaded_file", new FileBody(new File(imgPath)));
		
		if(!imgPath2.equals(""))
			entity.addPart("uploaded_file2", new FileBody(new File(imgPath2)));
        if(!imgPath3.equals(""))
            entity.addPart("uploaded_file3", new FileBody(new File(imgPath3)));
		entity.addPart("name", new StringBody(txtname.getText()+""));
		entity.addPart("email", new StringBody(txtEmail.getText()+""));
		entity.addPart("phone",new StringBody(txtPhone.getText()+""));
		entity.addPart("children", new StringBody(txtChild.getText()+""));

		entity.addPart("churchid", new StringBody(MyApp.getInstance().getSetting().getString("churchid", "")+""));
		entity.addPart("married", new StringBody(spMarried.getSelectedItem()+""));
		entity.addPart("dob", new StringBody(txtdob.getText()+""));
		entity.addPart("district", new StringBody(txtbirthplace.getText()+""));
		entity.addPart("currentlocation", new StringBody(txtcurlocation.getText()+""));
		entity.addPart("occupation", new StringBody(txtoccupation.getText()+""));
		entity.addPart("work", new StringBody(txtwork.getText()+""));
		entity.addPart("idno", new StringBody(txtidno.getText()+""));
		entity.addPart("address", new StringBody(txtaddress.getText()+""));
		entity.addPart("nominee", new StringBody(txtkin.getText()+""));
		entity.addPart("reation", new StringBody(spRelation.getSelectedItem()+""));
		entity.addPart("contact", new StringBody(txtcontact.getText()+""));

		gender=chMale.isChecked()?"Male":"Female";

		if(!txtEditSpouse.getText().equals(null))
			entity.addPart("spouse",new StringBody( txtEditSpouse.getText()+""));

		entity.addPart("gender",new StringBody(gender));
		request.setEntity(entity);

		HttpResponse response = httpClient.execute(request);

		int status = response.getStatusLine().getStatusCode();

		HttpEntity enitityCheck = response.getEntity();

		String responseString = new BasicResponseHandler().handleResponse(response);
		//value=responseString;


		Log.i("ok bro:",responseString);
		//txtname.setText(responseString);
		//Toast.makeText(AddNewMemberActivity.this, responseString+"", Toast.LENGTH_LONG).show();

		if(!responseString.equalsIgnoreCase("False")){


			//imgPath="";
			return true;

		}
		else{
			return false;
		}


	}
	public boolean UpdateMultipartFile() throws ClientProtocolException, IOException {

		String upLoadServerUri=Utils.url+"api.php?f=updateregistration";
		HttpClient httpClient = new DefaultHttpClient();
		Log.i("url", upLoadServerUri);
		HttpPost request = new HttpPost(upLoadServerUri);
		MultipartEntity entity = new MultipartEntity();

		if(!imgPath.equals(""))
			entity.addPart("uploaded_file", new FileBody(new File(imgPath)));
		
		if(!imgPath2.equals(""))
			entity.addPart("uploaded_file2", new FileBody(new File(imgPath2)));

        if(!imgPath3.equals(""))
            entity.addPart("uploaded_file3", new FileBody(new File(imgPath3)));

		entity.addPart("mid", new StringBody(mid+""));
		entity.addPart("name", new StringBody(txtname.getText()+""));
		entity.addPart("email", new StringBody(txtEmail.getText()+""));
		entity.addPart("phone",new StringBody(txtPhone.getText()+""));
		entity.addPart("children", new StringBody(txtChild.getText()+""));

		entity.addPart("churchid", new StringBody(MyApp.getInstance().getSetting().getString("churchid", "")+""));
		entity.addPart("married", new StringBody(spMarried.getSelectedItem()+""));
		entity.addPart("dob", new StringBody(txtdob.getText()+""));
		entity.addPart("district", new StringBody(txtbirthplace.getText()+""));
		entity.addPart("currentlocation", new StringBody(txtcurlocation.getText()+""));
		entity.addPart("occupation", new StringBody(txtoccupation.getText()+""));
		entity.addPart("work", new StringBody(txtwork.getText()+""));
		entity.addPart("idno", new StringBody(txtidno.getText()+""));
		entity.addPart("address", new StringBody(txtaddress.getText()+""));
		entity.addPart("nominee", new StringBody(txtkin.getText()+""));
		entity.addPart("reation", new StringBody(spRelation.getSelectedItem()+""));
		entity.addPart("contact", new StringBody(txtcontact.getText()+""));

		gender=chMale.isChecked()?"Male":"Female";

		if(!txtEditSpouse.getText().equals(null))
			entity.addPart("spouse",new StringBody( txtEditSpouse.getText()+""));


		entity.addPart("gender",new StringBody(gender));
		request.setEntity(entity);


		HttpResponse response = httpClient.execute(request);

		int status = response.getStatusLine().getStatusCode();

		HttpEntity enitityCheck = response.getEntity();

		String responseString = new BasicResponseHandler().handleResponse(response);
		//value=responseString;


		Log.i("ok bro:",responseString);
		//txtname.setText(responseString);
		//Toast.makeText(AddNewMemberActivity.this, responseString+"", Toast.LENGTH_LONG).show();

		if(!responseString.equalsIgnoreCase("False")){


			//imgPath="";
			return true;

		}
		else{
			return false;
		}


	}
	public boolean nullCheck() {
		boolean flag = false;
		String msg = "";
		if (!txtname.getText().toString().trim().equalsIgnoreCase("")) {
			if (!txtEmail.getText().toString().trim().equalsIgnoreCase("")) {
				if (!txtPhone.getText().toString().trim().equalsIgnoreCase("")) {

					return true;

				} else {
					msg = "Please Enter Phone!";
				}

			} else {
				msg = "Please Enter Valid Mail!";
			}

		} else {
			msg = "Please Enter Name!";
		}
		if (!flag)
			MyApp.getInstance().alert(this, msg);
		return flag;
	}




	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			if (requestCode == IMAGE_PICK) {

				this.imageFromGallery(resultCode, data);
				//Uri selectedImageUri = data.getData();
				//selectedImagePath = getPath(selectedImageUri);
				//Log.i("image:",selectedImagePath);
				//System.out.println("Image Path : " + selectedImagePath);
				//imgGallery.setImageURI(selectedImageUri);
			}
			if (requestCode == IMAGE_PICK2) {

				this.imageFromGallery2(resultCode, data);
				//Uri selectedImageUri = data.getData();
				//selectedImagePath = getPath(selectedImageUri);
				//Log.i("image:",selectedImagePath);
				//System.out.println("Image Path : " + selectedImagePath);
				//imgGallery.setImageURI(selectedImageUri);
			}
            if (requestCode == IMAGE_PICK3) {

                this.imageFromGallery3(resultCode, data);
                //Uri selectedImageUri = data.getData();
                //selectedImagePath = getPath(selectedImageUri);
                //Log.i("image:",selectedImagePath);
                //System.out.println("Image Path : " + selectedImagePath);
                //imgGallery.setImageURI(selectedImageUri);
            }
		}
	}

	public String getPath(Uri uri) {
		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(uri, projection, null, null, null);
		int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		imgPath=cursor.getString(column_index);
		return imgPath;
	}



	/**
	 * Image result from gallery
	 * @param resultCode
	 * @param data
	 */
	private void imageFromGallery(int resultCode, Intent data) {
		Uri selectedImage = data.getData();
		String [] filePathColumn = {MediaStore.Images.Media.DATA};

		Cursor cursor = AddNewMemberActivity.this.getContentResolver().query(selectedImage, filePathColumn, null, null, null);
		cursor.moveToFirst();

		int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
		String filePath = cursor.getString(columnIndex);
		cursor.close();
		imgPath=filePath;
		updateImageView(BitmapFactory.decodeFile(filePath));
	}
	private void imageFromGallery2(int resultCode, Intent data) {
		Uri selectedImage = data.getData();
		String [] filePathColumn = {MediaStore.Images.Media.DATA};

		Cursor cursor = AddNewMemberActivity.this.getContentResolver().query(selectedImage, filePathColumn, null, null, null);
		cursor.moveToFirst();

		int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
		String filePath = cursor.getString(columnIndex);
		cursor.close();
		imgPath2=filePath;
		updateImageView2(BitmapFactory.decodeFile(filePath));
	}
    private void imageFromGallery3(int resultCode, Intent data) {
        Uri selectedImage = data.getData();
        String [] filePathColumn = {MediaStore.Images.Media.DATA};

        Cursor cursor = AddNewMemberActivity.this.getContentResolver().query(selectedImage, filePathColumn, null, null, null);
        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String filePath = cursor.getString(columnIndex);
        cursor.close();
        imgPath3=filePath;
        updateImageView3(BitmapFactory.decodeFile(filePath));
    }
	/**
	 * Update the imageView with new bitmap
	 * @param newImage
	 */
	private void updateImageView(Bitmap newImage) {
		//BitmapProcessor bitmapProcessor = new BitmapProcessor(newImage, 1000, 1000, 90);

		//this.image = bitmapProcessor.getBitmap();
		//this.image= newImage;
		imgGallery.setImageBitmap(newImage);


	}
	private void updateImageView2(Bitmap newImage) {
		//BitmapProcessor bitmapProcessor = new BitmapProcessor(newImage, 1000, 1000, 90);

		//this.image = bitmapProcessor.getBitmap();
		//this.image= newImage;
		imgfamily.setImageBitmap(newImage);


	}
    private void updateImageView3(Bitmap newImage) {
        //BitmapProcessor bitmapProcessor = new BitmapProcessor(newImage, 1000, 1000, 90);

        //this.image = bitmapProcessor.getBitmap();
       // this.image= newImage;
        imgSpouse.setImageBitmap(newImage);


    }


    private class MyRelationAdpater extends BaseAdapter {

        private LayoutInflater mInflater;

        public MyRelationAdpater(Context mcontext) {
            // TODO Auto-generated constructor stub
            mInflater = LayoutInflater.from(mcontext);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return relation.length;
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            final ListContent holder;
            View v = convertView;
            if (v == null) {
                v = mInflater.inflate(R.layout.myspinner, null);
                holder = new ListContent();

                holder.name = (TextView) v.findViewById(R.id.textView1);

                v.setTag(holder);
            } else {

                holder = (ListContent) v.getTag();
            }

            holder.name.setTypeface(custom_font);
            holder.name.setText("" + relation[position]);

            return v;
        }

    }


    private class MyArrayAdapter extends BaseAdapter {

        private LayoutInflater mInflater;

        public MyArrayAdapter(Context mcontext) {
            // TODO Auto-generated constructor stub
            mInflater = LayoutInflater.from(mcontext);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return peopele.length;
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            final ListContent holder;
            View v = convertView;
            if (v == null) {
                v = mInflater.inflate(R.layout.myspinner, null);
                holder = new ListContent();

                holder.name = (TextView) v.findViewById(R.id.textView1);

                v.setTag(holder);
            } else {

                holder = (ListContent) v.getTag();
            }

            holder.name.setTypeface(custom_font);
            holder.name.setText("" + peopele[position]);

            return v;
        }

    }


    static class ListContent {

        TextView name;


    }
}
