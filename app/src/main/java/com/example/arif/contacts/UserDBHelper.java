package com.example.arif.contacts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;

import java.util.jar.Attributes;

public class UserDBHelper extends SQLiteOpenHelper {

    String TAG = "DEBUG";

    private static final String DATABASE_NAME = "USERINFO.DB";
    private static final String TABLE_NAME = "ContactTable";

    private static final String TABLE_COL_NAME = "NAME";
    private static final String TABLE_COL_MOB = "MOB";
    private static final String TABLE_COL_EMAIL = "EMAIL";
    private static final String TABLE_COL_IMG = "NewImage";
    private static final String TABLE_COL_ID = "ID";
    private static final int DATABASE_VERSION = 1;
    private static final String CREATE_QUERY =
            "CREATE TABLE "+TABLE_NAME+"("+TABLE_COL_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+TABLE_COL_NAME+" TEXT, "+TABLE_COL_MOB+" TEXT, "+TABLE_COL_EMAIL+" TEXT, "+TABLE_COL_IMG+" blob)" ;


    public UserDBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.e("DataBase", "Database created / Opened");
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_QUERY);
        Log.e("DataBase", "Table Created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

    public boolean AddInfo(String Name, String Mob , String Email, byte[] img )
    {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues CV = new ContentValues();
        CV.put(TABLE_COL_NAME, Name);
        CV.put( TABLE_COL_MOB, Mob);
        CV.put(TABLE_COL_EMAIL, Email );
        CV.put(TABLE_COL_IMG, img);

        long result = db.insert(TABLE_NAME, null,CV);
        Log.e("DataBase", "Add Info Bug");
        if(result == -1)
        {
            return false;
        }
        else
        {
            return true;
        }

    }

    public Cursor getInformation()
    {
        Cursor data;
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select * from " + TABLE_NAME;
        data = db.rawQuery(query,null);

        return data;

    }

    public String fetch_Name(int i)
    {
        String Str = "";

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select "+TABLE_COL_NAME+" FROM " + TABLE_NAME + " WHERE " +TABLE_COL_ID+" = "+i;

        Cursor data = db.rawQuery(query,null);

        if(data.moveToFirst())
        {
            Str = data.getString(data.getColumnIndex(TABLE_COL_NAME+""));
        }


        return Str;
    }

    public String fetch_MOB(int i)
    {
        String Str = "";

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select "+TABLE_COL_MOB+" FROM " + TABLE_NAME + " WHERE " +TABLE_COL_ID+" = "+i;

        Cursor data = db.rawQuery(query,null);

        if(data.moveToFirst())
        {
            Str = data.getString(data.getColumnIndex(TABLE_COL_MOB+""));
        }


        return Str;
    }

    public String fetch_Email(int i)
    {
        String Str = "";

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select "+TABLE_COL_EMAIL+" FROM " + TABLE_NAME + " WHERE " +TABLE_COL_ID+" = "+i;

        Cursor data = db.rawQuery(query,null);

        if(data.moveToFirst())
        {
            Str = data.getString(data.getColumnIndex(TABLE_COL_EMAIL+""));
        }


        return Str;
    }


    public Bitmap fetch_Img(int i) {  /// THIS FUNCTION IS NOT WORKING



        byte[] ImgByte;
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "Select "+TABLE_COL_IMG+" FROM " + TABLE_NAME + " WHERE " +TABLE_COL_ID+" = "+i;

        Cursor data = db.rawQuery(query,null);
        if(data.moveToFirst())
        {
            ImgByte =  data.getBlob(data.getColumnIndex(TABLE_COL_IMG+""));
            Bitmap bitMAP =  BitmapFactory.decodeByteArray(ImgByte, 0, ImgByte.length);
            return bitMAP;
        }
        return null;

    }

    public boolean UpDateContact(int POS, String name, String mob, String email, byte[] img) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues CV = new ContentValues();
        CV.put(TABLE_COL_NAME, name);
        CV.put( TABLE_COL_MOB, mob);
        CV.put(TABLE_COL_EMAIL, email );
        CV.put(TABLE_COL_IMG, img);

        long result = db.update(TABLE_NAME,CV,TABLE_COL_ID+" = ? ", new String[]{POS+""});

        if(result == -1) {
            return false;
        }
        else return true;
    }


    public boolean Delete(String Name) {
        SQLiteDatabase db = this.getWritableDatabase();

        long result = db.delete(TABLE_NAME, TABLE_COL_NAME +" = ? ",new String[]{Name});

        if(result == -1) {
            return false;
        }
        else return true;
    }
}
