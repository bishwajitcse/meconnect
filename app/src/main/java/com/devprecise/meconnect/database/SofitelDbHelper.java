package com.devprecise.meconnect.database;

 
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.devprecise.meconnect.model.dbTbMemberInfo;

 
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
 
public class SofitelDbHelper extends SQLiteOpenHelper {
 
    // Logcat tag
    private static final String LOG = SofitelDbHelper.class.getName();
 
    // Database Version
    private static final int DATABASE_VERSION = 3;
 
    // Database Name
    private static final String DATABASE_NAME = "meConnect";
 
    // Table Names
    private static final String TABLE_MemberInfo = "tbMemberInfo";
  
    private static final String TABLE_HotelInfo = "tbhotelinfo";
    private static final String TABLE_UserInfo = "tbuserinfo";
    private static final String TABLE_GcmStatus = "tbgcmpush";
 
    // Common column names
    private static final String KEY_ID = "id";
    private static final String KEY_CREATED_AT = "created_at";

    

    private static final String KEY_User_Name="fullname";
    private static final String KEY_User_Marrite="marrite";
    private static final String KEY_User_Gender="gender";
    private static final String KEY_User_dob="dob";
    private static final String KEY_USER_city="city";
    private static final String KEY_User_residence="residence";
    private static final String KEY_USER_Occ="occupation";
    private static final String KEY_USER_placeofwork="placeofwork";
    private static final String KEY_USER_idno="idno";
    private static final String KEY_User_Mobile="mobile";
    private static final String KEY_User_email="email";
    private static final String KEY_User_address="address";
    private static final String KEY_User_child="child";
    private static final String KEY_User_nominee="nominee";
    private static final String KEY_User_relation="realtionship";
    private static final String KEY_User_spouse="spouse";
    private static final String KEY_User_Nominee_contact="nomineecontact";

    private static final String KEY_User_Churchid="churchid";


    private static final String KEY_USER_IMAGE="userimage";
    private static final String KEY_User_family_image="familyimage";
    private static final String KEY_User_spouseimage="spouseimage";



    private static final String KEY_STATUS="status";
    
    // NOTE_TAGS Table - column names
    private static final String KEY_TODO_ID = "todo_id";
    private static final String KEY_TAG_ID = "tag_id";
 
    // Table Create Statements
    // Todo table create statement
    private static final String CREATE_TABLE_Member= "CREATE TABLE "
            + TABLE_MemberInfo + "(" + KEY_ID + " TEXT," + KEY_User_Name
            + " TEXT,"+ KEY_User_Gender +" TEXT," + KEY_User_dob
            + " TEXT,"+ KEY_User_Churchid +" TEXT,"+ KEY_User_Marrite
            + " TEXT,"+KEY_USER_city +" TEXT," +KEY_User_residence +" TEXT," +KEY_USER_Occ +" TEXT," +KEY_USER_placeofwork +" TEXT," +KEY_USER_idno +" TEXT," +
            KEY_User_Mobile +" TEXT," +KEY_User_email +" TEXT," +KEY_User_address +" TEXT," +KEY_User_child +" TEXT," +
            KEY_User_nominee +" TEXT," +KEY_User_relation +" TEXT," +KEY_User_Nominee_contact +" TEXT," +KEY_USER_IMAGE +" TEXT,"
            +KEY_User_family_image +" TEXT," +KEY_User_spouseimage +" TEXT,"+KEY_User_spouse +" TEXT,"
            +KEY_STATUS + " INTEGER," + KEY_CREATED_AT
            + " DATETIME" + ")";
 

    public SofitelDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {
 
        // creating required tables
        db.execSQL(CREATE_TABLE_Member);

        
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HotelInfo);

        // create new tables
        onCreate(db);
        
    }
    

    /********Hetel Table Method***********/
    /*
     * Creating a Hotel
     */

     /*
     * getting all outlet
     * */
    public List<dbTbMemberInfo> getAllMember(String churchid,String name) {
        List<dbTbMemberInfo> todos = new ArrayList<dbTbMemberInfo>();

        String membername="";
        String selectQuery = "SELECT  * FROM " + TABLE_MemberInfo +" where "+KEY_User_Churchid+" = '"+churchid+"' ";

        if(!name.equalsIgnoreCase("")){
            membername=" and "+ KEY_User_Name +" like '%"+name+"%'";
            selectQuery = "SELECT  * FROM " + TABLE_MemberInfo +" where "+KEY_User_Churchid+" = '"+churchid+"' "+ membername;
        }


        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst() && c!=null) {
            do {

                dbTbMemberInfo td = new dbTbMemberInfo();
                td.setId(c.getInt((c.getColumnIndex(KEY_ID))));

                td.setName((c.getString(c.getColumnIndex(KEY_User_Name))));
                td.setGender((c.getString(c.getColumnIndex(KEY_User_Gender))));
                td.setMarrite((c.getString(c.getColumnIndex(KEY_User_Marrite))));
                td.setDob((c.getString(c.getColumnIndex(KEY_User_dob))));
                td.setDistrictofbirth((c.getString(c.getColumnIndex(KEY_USER_city))));
                td.setResidence((c.getString(c.getColumnIndex(KEY_User_residence))));
                td.setOcc((c.getString(c.getColumnIndex(KEY_USER_Occ))));
                td.setPlaceofwork((c.getString(c.getColumnIndex(KEY_USER_placeofwork))));
                td.setIdno((c.getString(c.getColumnIndex(KEY_USER_idno))));
                td.setMobile((c.getString(c.getColumnIndex(KEY_User_Mobile))));
                td.setEmail((c.getString(c.getColumnIndex(KEY_User_email))));
                td.setAddress((c.getString(c.getColumnIndex(KEY_User_address))));
                td.setChild((c.getString(c.getColumnIndex(KEY_User_child))));
                td.setNominee((c.getString(c.getColumnIndex(KEY_User_nominee))));
                td.setRelation((c.getString(c.getColumnIndex(KEY_User_relation))));
                td.setContact((c.getString(c.getColumnIndex(KEY_User_Nominee_contact))));
                td.setSpouse((c.getString(c.getColumnIndex(KEY_User_spouse))));
                td.setProfileimage((c.getString(c.getColumnIndex(KEY_USER_IMAGE))));
                td.setFamilyimage((c.getString(c.getColumnIndex(KEY_User_family_image))));
                td.setSpouseimage((c.getString(c.getColumnIndex(KEY_User_spouseimage))));
                td.setChurchid((c.getString(c.getColumnIndex(KEY_User_Churchid))));



                // adding to todo list
                todos.add(td);
            } while (c.moveToNext());
        }

        return todos;
    }

    public dbTbMemberInfo getMember(String mid) {
    dbTbMemberInfo td =new dbTbMemberInfo();

        String membername="";
        String selectQuery = "SELECT  * FROM " + TABLE_MemberInfo +" where "+KEY_ID+" = '"+mid+"' ";



        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst() && c!=null) {


              //  dbTbMemberInfo td = new dbTbMemberInfo();
                td.setId(c.getInt((c.getColumnIndex(KEY_ID))));

                td.setName((c.getString(c.getColumnIndex(KEY_User_Name))));
                td.setGender((c.getString(c.getColumnIndex(KEY_User_Gender))));
                td.setMarrite((c.getString(c.getColumnIndex(KEY_User_Marrite))));
                td.setDob((c.getString(c.getColumnIndex(KEY_User_dob))));
                td.setDistrictofbirth((c.getString(c.getColumnIndex(KEY_USER_city))));
                td.setResidence((c.getString(c.getColumnIndex(KEY_User_residence))));
                td.setOcc((c.getString(c.getColumnIndex(KEY_USER_Occ))));
                td.setPlaceofwork((c.getString(c.getColumnIndex(KEY_USER_placeofwork))));
                td.setIdno((c.getString(c.getColumnIndex(KEY_USER_idno))));
                td.setMobile((c.getString(c.getColumnIndex(KEY_User_Mobile))));
                td.setEmail((c.getString(c.getColumnIndex(KEY_User_email))));
                td.setAddress((c.getString(c.getColumnIndex(KEY_User_address))));
                td.setChild((c.getString(c.getColumnIndex(KEY_User_child))));
                td.setNominee((c.getString(c.getColumnIndex(KEY_User_nominee))));
                td.setRelation((c.getString(c.getColumnIndex(KEY_User_relation))));
                td.setContact((c.getString(c.getColumnIndex(KEY_User_Nominee_contact))));
                td.setSpouse((c.getString(c.getColumnIndex(KEY_User_spouse))));
                td.setProfileimage((c.getString(c.getColumnIndex(KEY_USER_IMAGE))));
                td.setFamilyimage((c.getString(c.getColumnIndex(KEY_User_family_image))));
                td.setSpouseimage((c.getString(c.getColumnIndex(KEY_User_spouseimage))));
                td.setChurchid((c.getString(c.getColumnIndex(KEY_User_Churchid))));



                // adding to todo list


        }

        return td;
    }
    public void delleteAllMember(String churchid) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_MemberInfo+" where "+ KEY_User_Churchid+"='"+churchid+"'");
    }

    public long createHotelInfo(dbTbMemberInfo member) {
        SQLiteDatabase db = this.getWritableDatabase();
     
        ContentValues values = new ContentValues();
        values.put(KEY_ID,member.getId()+"");
        values.put(KEY_User_Name, member.getName());

        values.put(KEY_User_dob, member.getDob());
        values.put(KEY_User_Gender, member.getGender());
        values.put(KEY_User_Marrite, member.getMarrite());
        values.put(KEY_USER_city, member.getDistrictofbirth());
        values.put(KEY_User_residence, member.getResidence());
        values.put(KEY_USER_placeofwork, member.getPlaceofwork());
        values.put(KEY_User_Mobile, member.getMobile());
        values.put(KEY_User_email, member.getEmail());
        values.put(KEY_User_address, member.getAddress());
        values.put(KEY_User_child, member.getChild());
        values.put(KEY_User_nominee, member.getNominee());
        values.put(KEY_User_relation, member.getRelation());
        values.put(KEY_User_Nominee_contact, member.getContact());
        values.put(KEY_USER_IMAGE, member.getProfileimage());
        values.put(KEY_User_spouse, member.getSpouse());
        values.put(KEY_User_family_image, member.getFamilyimage());
        values.put(KEY_User_spouseimage, member.getSpouseimage());
        values.put(KEY_User_Churchid, member.getChurchid());
        values.put(KEY_USER_idno,member.getIdno());
        values.put(KEY_USER_Occ,member.getOcc());
        values.put(KEY_STATUS, "1");

        values.put(KEY_CREATED_AT, getDateTime());


        // insert row
        long todo_id = db.insert(TABLE_MemberInfo, null, values);
     
        // assigning tags to todo
     /*   for (long tag_id : tag_ids) {
            createTodoTag(todo_id, tag_id);
        }*/
      //  DBUtil.safeCloseCursor(c);
        DBUtil.safeCloseDataBase(db);
        return todo_id;
    }
    


    
    
    /*******************/
 
    
    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }
 
    /**
     * get datetime
     * */
    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
}