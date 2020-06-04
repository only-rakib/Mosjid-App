package com.rakib.mosjid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PaidPage extends AppCompatActivity {

    private MyDataHelper myDataHelper;
    private ListView listView;
    private Context contex;
    private Cursor cursor;
    private CustomCursorAdapter customAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paid_page);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Paid List");
        this.contex=this;
        myDataHelper = new MyDataHelper(this);
        //customAdapter = new CustomCursorAdapter(this,cursor);

        displayDataListView();
    }
    private void displayDataListView()
    {


        listView = (ListView) findViewById(R.id.listAllName);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                Log.d("TAG", "clicked on item: " + position+" Db ID: "+customAdapter.getItemId(position));


            }
        });

        cursor = myDataHelper.getDataPaid("All_Info",columnName());
        customAdapter = new CustomCursorAdapter(contex,cursor);
        listView.setAdapter(customAdapter);


        /**
         * search
         */
        customAdapter.setFilterQueryProvider(new FilterQueryProvider() {
            @Override
            public Cursor runQuery(CharSequence constraint) {
                //Log.d("LOG TAG", "runQuery constraint:"+constraint);
                return myDataHelper.fetchPeopleByNamePaid(constraint.toString(),columnName());
            }
        });

        EditText myFilter = (EditText) findViewById(R.id.searchFieldDue);
        listView.setTextFilterEnabled(true);
        myFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

                customAdapter.getFilter().filter(s.toString());
                customAdapter.notifyDataSetChanged();

            }
        });
        myDataHelper.close();
    }
    private String columnName()
    {
        List<String> monthList = new ArrayList<>();
        monthList.add(0,"JAN");
        monthList.add(1,"FEB");
        monthList.add(2,"MAR");
        monthList.add(3,"APR");
        monthList.add(4,"MAY");
        monthList.add(5,"JUNE");
        monthList.add(6,"JULY");
        monthList.add(7,"AUG");
        monthList.add(8,"SEP");
        monthList.add(9,"OCT");
        monthList.add(10,"NOV");
        monthList.add(11,"DEC");

        Calendar calendar = Calendar.getInstance();

        int thisYear = calendar.get(Calendar.YEAR);
        int thisMonth = calendar.get(Calendar.MONTH);
        //int thisDay = calendar.get(Calendar.DAY_OF_MONTH);

        return monthList.get(thisMonth)+"_"+thisYear;
    }
}
