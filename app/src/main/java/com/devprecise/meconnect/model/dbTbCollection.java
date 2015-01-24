package com.devprecise.meconnect.model;

/**
 * Created by bishwajit on 1/23/15.
 */
public class dbTbCollection {


    int id;
    String _collectionid,_collection_name,_membername,_amount,_date;

    public dbTbCollection(){


    }

    public void setID(int id){
        this.id=id;
    }
    public int getId(){

        return this.id;
    }

    public void setCollectionID(String cid){
        this._collectionid=cid;
    }
    public String getCollectionID(){

        return this._collectionid;
    }

    public void setCollectionName(String collectionName){
        this._collection_name=collectionName;
    }
    public String getCollectionName(){

        return this._collection_name;
    }

    public void setMemberName(String name){
        this._membername=name;
    }
    public String getMemberName(){

        return this._membername;
    }

    public void setAmount(String amt){
        this._amount=amt;
    }
    public String getAmount(){

        return this._amount;
    }


    public void setDate(String date){
        this._date=date;
    }
    public String getDate(){

        return this._date;
    }




}
