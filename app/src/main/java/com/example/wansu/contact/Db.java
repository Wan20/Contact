package com.example.wansu.contact;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by wansu on 15/04/16.
 */
public class Db extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "contactdb.sqlite";
    public static final String CONTACTS_TABLE_NAME = "mycontacts";
    public static final String CONTACTS_COLUMN_ID  = "id";
    public static final String CONTACTS_COLUMN_NAME = "name";
    public static final String CONTACTS_COLUMN_PHONE = "phone";
    public static final String CONTACTS_COLUMN_CITY = "place";

    private HashMap hp;

    public Db (Context context)
    {
        super(context, DATABASE_NAME , null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table mycontacts " +
                        "(id integer primary key autoincrement, name text, phone text, place text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS mycontacts");
        onCreate(db);
    }
    public boolean addContact(String contactname, String contactphone, String contactplace)
    {
    /*Add*/
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contantValues = new ContentValues();
        contantValues.put("name",contactname);
        contantValues.put("phone", contactphone);
        contantValues.put("place",contactplace);
        db.insert("mycontacts", null, contantValues);
        db.close();
        return true;
    }

    public boolean updateStudentContact(Integer contactid, String contactname, String contactphone, String contactplace)
    {
    /*Update contactname*/
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contantValues = new ContentValues();
        contantValues.put("name",contactname);
        contantValues.put("phone", contactphone);
        contantValues.put("place",contactplace);
        db.update("mycontacts", contantValues, "id = ?", new String[]{Integer.toString(contactid)});
        db.close();
        return true;
    }

    public Integer deleteContact(Integer id)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        return db.delete("mycontacts","id = ?",new String[]{Integer.toString(id)});
    }

    public Cursor getData(int contactid)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res=db.rawQuery("Select * from mycontacts where id = " + contactid + "", null);
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db=this.getWritableDatabase();
        int numRows=(int) DatabaseUtils.queryNumEntries(db,CONTACTS_TABLE_NAME);
        return numRows;
    }
    public ArrayList<String> getAllContacts(){
        ArrayList<String> arraylist= new ArrayList<String>();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("Select * from mycontacts",null);

        if (cursor.moveToFirst()) {
            do {
                arraylist.add(cursor.getString(cursor.getColumnIndex(CONTACTS_COLUMN_NAME)));
            } while (cursor.moveToNext());
        }
        return arraylist;
    }
}