package com.example.nikhils1.pharmapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by nikhils1 on 7/20/17.
 */

public class database extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Pharm.db";
    public static final String TABLE_NAME = "Medicines";
    public static final String COL_1 = "Medicine_ID";
    public static final String COL_2 = "Company_Name";
    public static final String COL_3 = "Product_Name";
    public static final String COL_4 = "Chemical_Contents";
    public static final String COL_5 = "UNIT";
    public static final String COL_6 = "Price_per_UNIT";
    public static final String COL_7 = "Quantity_Available";
    public static final String COL_8 = "Alternate_Medicine_ID";
    public database(Context context)
    {
        super(context,DATABASE_NAME,null,1);
        SQLiteDatabase db = this.getWritableDatabase();
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table "+TABLE_NAME+"(Medicine_ID INTEGER PRIMARY KEY AUTOINCREMENT,Company_Name TEXT,Product_Name TEXT,Chemical_Contents TEXT,UNIT TEXT,Price_per_UNIT INTEGER,Quantity_Available INTEGER,Alternate_Medicine_ID TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
    public boolean insert(String Company_name , String Product_name , String Chemical_content,String Unit , int price, int quantity , String alt)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,Company_name);
        contentValues.put(COL_3,Product_name);
        contentValues.put(COL_4,Chemical_content);
        contentValues.put(COL_5,Unit);
        contentValues.put(COL_6,price);
        contentValues.put(COL_7,quantity);
        contentValues.put(COL_8,alt);
        long res = db.insert(TABLE_NAME,null,contentValues);
        if(res == -1 )
            return false;
        else
            return true;
    }
    public Cursor getalldata()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from "+TABLE_NAME,null);
        return cursor;
    }
    public String get_related_ids(String Content)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select "+COL_1+" from "+TABLE_NAME+" where "+COL_4+"='"+Content+"'",null);
        String related_ids = "";
        for(int i=0;i<cursor.getCount();i++)
        {
            cursor.moveToNext();
            related_ids=related_ids+cursor.getString(0);
            if(i!=cursor.getCount()-1)
                related_ids=related_ids+",";
        }
        related_ids=related_ids+".";
        return related_ids;
    }
    public myobject[] get_database()
    {
        Cursor cursor=getalldata();
        myobject obj[] = new myobject[cursor.getCount()];
        int i=0;
        while(cursor.moveToNext())
        {
            obj[i]=new myobject(cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getInt(5),cursor.getInt(6),cursor.getString(7));
            i++;
        }
        return obj;
    }
    public  boolean is_present(String Product)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from "+TABLE_NAME+" where "+COL_3+"='"+Product+"'",null);
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }
    public myobject[] get_that_content(String Product)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from "+TABLE_NAME+" where "+COL_3+"='"+Product+"'",null);
        myobject obj[] = new myobject[cursor.getCount()];
        int i=0;
        while(cursor.moveToNext())
        {
            obj[i]=new myobject(cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getInt(5),cursor.getInt(6),cursor.getString(7));
            i++;
        }
        return obj;
    }
    public  boolean is_related(String Chemical_Contents)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("select * from "+TABLE_NAME+" where "+COL_4+"='"+Chemical_Contents+"'",null);
        if(res.getCount() >0)
            return true;
        else
            return false;
    }
    public myobject[] get_related_products(String Chemical_Contents)
    {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery("select * from "+TABLE_NAME+" where "+COL_4+"='"+Chemical_Contents+"'",null);
        myobject obj[] = new myobject[cursor.getCount()];
        int i=0;
        while(cursor.moveToNext())
        {
            obj[i]=new myobject(cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getInt(5),cursor.getInt(6),cursor.getString(7));
            i++;
        }
        return obj;
    }
    public  int delete(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME,"Medicine_ID=?",new String[]{Integer.toString(id)});
    }
}
