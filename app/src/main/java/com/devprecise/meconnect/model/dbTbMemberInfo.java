package com.devprecise.meconnect.model;

import android.content.Intent;

public class dbTbMemberInfo {

	int id;
	String name;

	int status;


    String gender,marrite,dob,districtofbirth,currentcity,occ,placeofwork,idno,residence,churchid;
    String mobile,email,address,child,nominee,relation,contact,spouse,profileimage,familyimage,spouseimage;

	// constructors
	public dbTbMemberInfo() {
	}

	public dbTbMemberInfo(int id,String name, String gender, String marrite, String dob, String districtofbirth,
                          String currentcity,String residence,String occ,String placeofwork,String idno,String mobile,
                          String email,String address,String contact,String child,String nominee,String relation,
                          String spouse,String profileimage,String familyimage,String spouseimage,String churchid) {

        this.id=id;
		this.name = name;
        this.gender=gender;
        this.marrite=marrite;
        this.dob=dob;
        this.districtofbirth=districtofbirth;
        this.currentcity=currentcity;
        this.occ=occ;
        this.placeofwork=placeofwork;
        this.idno=idno;
        this.mobile=mobile;
        this.email=email;
        this.address=address;
        this.contact=contact;
        this.residence=residence;


        this.child=child;
        this.nominee=nominee;
        this.relation=relation;
        this.spouse=spouse;

        this.profileimage=profileimage;
        this.familyimage=familyimage;
        this.spouseimage=spouseimage;
        this.churchid=churchid;


	}



	// setters
	public void setId(int id) {
		this.id = id;
	}
    // getters
    public long getId() {
        return this.id;
    }


	public void setName(String name) {
		this.name = name;
	}
    public String getName() {
        return this.name;
    }

	public void setGender(String g) {
		this.gender = g;
	}
    public String getGender(){ return  gender;}

    public void setMarrite(String m) {
        this.marrite = m;
    }
    public String getMarrite(){ return  this.marrite;}

      public void setDob(String dateofbirth) {
        this.dob = dateofbirth;
    }
    public String getDob(){ return  dob;}

    public void setNominee(String nominee) {
        this.nominee = nominee;
    }
    public String getNominee(){ return  this.nominee;}

  //  public void setDistrictofBirth(String city) {
  //      this.currentcity = city;
   // }

    //public String getDistrictofBirth(){ return  this.currentcity;}

    public void setResidence(String res) {
        this.residence = res;
    }
    public String getResidence(){ return  residence;}



    public void setDistrictofbirth(String districtofbirth) {
        this.districtofbirth = districtofbirth;
    }
    public String getDistrictofbirth(){ return  this.districtofbirth;}

    public void setCurrentcity(String cucity) {
        this.currentcity = cucity;
    }
    public String getCurrentcity(){ return  this.currentcity;}

    public void setOcc(String occ) {
        this.occ = occ;
    }
    public String getOcc(){ return  occ;}

    public void setPlaceofwork(String placeofwork) {
        this.placeofwork = placeofwork;
    }
    public String getPlaceofwork(){ return  this.placeofwork;}

    public void setIdno(String idno) {
        this.idno = idno;
    }
    public String getIdno(){ return  this.idno;}

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String getMobile()
    {
        return  this.mobile;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail(){ return  this.email;}

    public void setAddress(String address) {
        this.address = address;
    }
    public String getAddress(){ return  this.address;}

    public void setContact(String contact) {
        this.contact = contact;
    }
    public String getContact(){ return  this.contact;}

    public void setChild(String child) {
        this.child = child;
    }
    public String getChild(){ return  this.child;}

    public void setRelation(String relation) {
        this.relation = relation;
    }
    public String getRelation(){ return  this.relation;}

    public void setSpouse(String spouse) {
        this.spouse = spouse;
    }
    public String getSpouse(){ return  this.spouse;}

    public void setProfileimage(String profileimage) {
        this.profileimage = profileimage;
    }
    public String getProfileimage(){ return  this.profileimage;}

    public void setFamilyimage(String familyimage) {
        this.familyimage = familyimage;
    }
    public String getFamilyimage(){ return  this.familyimage;}

    public void setSpouseimage(String spouseimage) {
        this.spouseimage = spouseimage;
    }
    public String getSpouseimage(){ return  this.spouseimage;}


    public void setChurchid(String churchid) {
        this.churchid = churchid;
    }
    public String getChurchid(){ return  this.churchid;}


    public void setStatus(int status){
        this.status=status;
    }
    public int getStatus(){
        return  status;
    }


}
