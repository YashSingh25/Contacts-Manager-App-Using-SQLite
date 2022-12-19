package com.example.contactsmanagerappusingsqlite.db.entities;

public class Contacts {

    // Constants For Database
    public static final String TABLE_CONTACT="contacts";
    public static final String COLUMN_ID="id";
    public static final String COLUMN_NAME="name";
    public static final String COLUMN_PHONE_NO="phone_no";

    private int id;
    private String name,phoneNo;

    public Contacts(){

    }

    public Contacts(int id, String name, String phoneNo) {
        this.id = id;
        this.name = name;
        this.phoneNo = phoneNo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    //Sql Query : for Creating a table

    public static final String CREATE_TABLE= " CREATE TABLE " + TABLE_CONTACT +
                        "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"  +
                              COLUMN_NAME + " TEXT," +
                              COLUMN_PHONE_NO + " DATETIME DEFAULT CURRENT_TIMESTAMP" + ")";
}
