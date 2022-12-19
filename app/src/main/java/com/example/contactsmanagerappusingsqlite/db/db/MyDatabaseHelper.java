package com.example.contactsmanagerappusingsqlite.db.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.contactsmanagerappusingsqlite.db.entities.Contacts;

import java.util.ArrayList;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME="contacts_db";
    public static final int DB_VERSION=1;


    public MyDatabaseHelper( Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(Contacts.CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Contacts.TABLE_CONTACT);

        onCreate(sqLiteDatabase);

    }

    // Insert Data into Database
    public long insertContacts(String name,String phoneNo){
        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(Contacts.COLUMN_NAME , name);
        values.put(Contacts.COLUMN_PHONE_NO , phoneNo);

        long id=sqLiteDatabase.insert(Contacts.TABLE_CONTACT , null , values);
        sqLiteDatabase.close();

        return id;
    }

    //getting contacts from DB
    public Contacts getContact(long id){
        SQLiteDatabase sqLiteDatabase= this.getReadableDatabase();
        Cursor cursor= sqLiteDatabase.query(Contacts.TABLE_CONTACT ,
                     new String[]{
                             Contacts.COLUMN_ID ,
                             Contacts.COLUMN_NAME ,
                             Contacts.COLUMN_PHONE_NO
                     } , Contacts.COLUMN_ID + "=?" ,
                     new String[]{String.valueOf(id)} ,
                    null ,
                     null ,
                     null ,
                        null
                     );
        if (cursor!=null) {
            cursor.moveToFirst();
        }
            Contacts contacts=new Contacts(
                    cursor.getInt(cursor.getColumnIndexOrThrow(Contacts.COLUMN_ID)) ,
                    cursor.getString(cursor.getColumnIndexOrThrow(Contacts.COLUMN_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Contacts.COLUMN_PHONE_NO)));

            cursor.close();
            return contacts;
    }

    //Getting All Contacts from DB
    public ArrayList<Contacts> getAllContacts(){
        ArrayList<Contacts> contactsArrayList=new ArrayList<>();

        String selectQuery="SELECT * FROM " + Contacts.TABLE_CONTACT + " ORDER BY " + Contacts.COLUMN_ID + " DESC";

        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery(selectQuery , null);

        if(cursor.moveToFirst()){
            do {
                Contacts contacts=new Contacts();
                contacts.setId(cursor.getInt(cursor.getColumnIndexOrThrow(Contacts.COLUMN_ID)));
                contacts.setName(cursor.getString(cursor.getColumnIndexOrThrow(Contacts.COLUMN_NAME)));
                contacts.setPhoneNo(cursor.getString(cursor.getColumnIndexOrThrow(Contacts.COLUMN_PHONE_NO)));
                contactsArrayList.add(contacts);
            }while (cursor.moveToNext());
        }
        sqLiteDatabase.close();
        return contactsArrayList;
    }

    public int updateContact(Contacts contacts){
        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(Contacts.COLUMN_NAME , contacts.getName());
        contentValues.put(Contacts.COLUMN_PHONE_NO , contacts.getPhoneNo());
        return  sqLiteDatabase.update(
                Contacts.TABLE_CONTACT ,
                contentValues ,
                Contacts.COLUMN_ID + " = ? " ,
                new String[]{String.valueOf(contacts.getId())}
          );
    }

    public void deleteContacts(Contacts contacts){
        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
        sqLiteDatabase.delete(
                Contacts.TABLE_CONTACT ,
                Contacts.COLUMN_ID + " = ?" ,
                new String[]{String.valueOf(contacts.getId())}
           );
        sqLiteDatabase.close();
    }
}
