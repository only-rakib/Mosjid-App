package com.rakib.mosjid;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class BalanceSheet extends AppCompatActivity {

    private ListView listView;
    private MyDataHelper myDataHelper;
    //private String[] columnNames;
    private MyArrayAdapter myArrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance_sheet);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Balance Sheet");
        myDataHelper = new MyDataHelper(this);
        populateListView();
    }

    private void populateListView()
    {

        //listView = (ListView) findViewById(R.id.listAllName);
        //myDataHelper = new MyDataHelper(this);

        String[] columnNames = myDataHelper.getAllColumnName();
        Cursor cursor = myDataHelper.getDataDbForBalance("All_Info");
        final ArrayList<MonthCollectionAndDetailsInfo> arrayList = new ArrayList<>();
        int n = cursor.getColumnCount();
        long[] amount =new long[n];
        if(cursor.getCount()>0)
        {
            //Log.d("Col",""+n);
            while(cursor.moveToNext())
            {
                //String tt =cursor.getString(n - 1);
                for(int i=4;i<n;i++)
                {
                    if (cursor.getString(i) == null)
                    {
                        amount[i] += 0;
                    }
                    else {
                        amount[i] += Long.parseLong(cursor.getString(i));
                    }
                }

            }

        }
        for(int i=4;i<n;i++)
        {
            MonthCollectionAndDetailsInfo monthCollectionAndDetailsInfo = new MonthCollectionAndDetailsInfo(columnNames[i],""+amount[i]);
            arrayList.add(monthCollectionAndDetailsInfo);
        }

        ArrayList<MonthCollectionAndDetailsInfo> arrayOfUsers = new ArrayList<>();
        myArrayAdapter = new MyArrayAdapter(this,R.layout.horizontal_list_view,arrayOfUsers);
        //adapter = new MyArrayAdapter(this,arrayOfUsers);
        ListView listView = (ListView) findViewById(R.id.listAllName);
        listView.setAdapter(myArrayAdapter);
        myArrayAdapter.addAll(arrayList);
        myDataHelper.close();
        //return arrayList;
    }


}
