package com.example.user.datastoragepractice;
import android.content.ContentValues;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DataBaseHelper mDataBaseHelper;
    private ListView mBookListView;
    private ArrayList<BookData> mAllBookData;
    private ArrayList<String> mString;

    String[] book_name=new String[]
            {
               "ABC","DEF","GHI","JKL","MNO","PQR"
            };

    String[] author_name=new String[]
            {
                    "Q","W","E","R","T","Y"
            };

    String[] book_id=new String[]
            {
                    "123","456","789","987","654","321"
            };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAllBookData = new ArrayList<>();
        mString = new ArrayList<>();
        mDataBaseHelper=new DataBaseHelper(MainActivity.this,Constants.TABLE_NAME,null,Constants.DATABASE_VERSION);
        mBookListView=(ListView) findViewById(R.id.books);
        if(mDataBaseHelper.getRowCountFromTable()==0)
        {
            //TODO:INSERT DATA TO THE TABLE
            insertRecordInTable();
        }
        mAllBookData = mDataBaseHelper.getAllBooks();
        for (int j=0;j<mAllBookData.size();j++)
        {
            mString.add(mAllBookData.get(j).getAuthor_name());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,mString);
        mBookListView.setAdapter(adapter);

        mBookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity.this,mString.get(i),Toast.LENGTH_SHORT).show();
                mDataBaseHelper.updateRecord();
                Toast.makeText(MainActivity.this,"Updated",Toast.LENGTH_SHORT).show();
            }
        });
        mDataBaseHelper.deleteRecord();
    }

    private void insertRecordInTable() {
         for(int i=0;i<book_name.length;i++)
         {
             ContentValues contentValues = new ContentValues();
             contentValues.put(Constants.BOOK_NAME,book_name[i]);
             contentValues.put(Constants.AUTHOR_NAME,author_name[i]);
             contentValues.put(Constants.BOOK_ID,book_id[i]);
             mDataBaseHelper.insertBookRecords(contentValues);
         }
    }
}
