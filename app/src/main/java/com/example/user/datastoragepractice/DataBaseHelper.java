package com.example.user.datastoragepractice;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by user on 08-11-2017.
 */

public class DataBaseHelper extends SQLiteOpenHelper {
    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTable=" CREATE  TABLE "+Constants.TABLE_NAME + " ("
                + Constants.ID +  " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + Constants.BOOK_ID + " TEXT ,"
                + Constants.BOOK_NAME + " TEXT ,"
                + Constants.AUTHOR_NAME + " TEXT ) ";

        db.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public int getRowCountFromTable() {
        int rowcount = 0;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor= database.query(false, Constants.TABLE_NAME, null, null, null, null, null, null, null);
        if(cursor!=null)
        {
            cursor.moveToFirst();
            rowcount=cursor.getCount();
        }
        return rowcount;
    }
//contentValues is class which is used to insert data in database & it maps the data to the corresponding row & column
    public long insertBookRecords(ContentValues values)
    {
        long count=0;
        SQLiteDatabase database = this.getWritableDatabase();
        try {
           count= database.insert(Constants.TABLE_NAME,null,values);

        }catch (Exception e)
        {
           e.printStackTrace();
        }

        return count;
    }

    public ArrayList<BookData> getAllBooks()
    {
        ArrayList<BookData> list = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        String query = "select * from "+Constants.TABLE_NAME;
        Cursor cursor = database.rawQuery(query,null);
        BookData bookData = null;
        if (cursor!=null)
        {
            cursor.moveToFirst();
            do{
                bookData = new BookData();
                bookData.setId(cursor.getInt(0));
                bookData.setBook_id(cursor.getString(1));
                bookData.setBook_name(cursor.getString(2));
                bookData.setAuthor_name(cursor.getString(3));
                list.add(bookData);
            }while (cursor.moveToNext());
        }
        return list;
    }

    public int updateRecord()
    {
        int i=0;
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.AUTHOR_NAME,"Vibhor");
        i= database.update(Constants.TABLE_NAME,contentValues,Constants.BOOK_ID+" = 654",null);
        return i;
    }

    public int deleteRecord()
    {
        int i=0;
        SQLiteDatabase database = this.getWritableDatabase();
        i= database.delete(Constants.TABLE_NAME, Constants.BOOK_ID+" = 456",null);
        return i;
    }
}
