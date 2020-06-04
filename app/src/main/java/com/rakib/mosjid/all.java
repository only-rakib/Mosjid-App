package com.rakib.mosjid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
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

import static java.util.Collections.sort;

public class all extends AppCompatActivity {

    private MyDataHelper myDataHelper;
    private ListView listView;
    private CustomCursorAdapter customAdapter;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Info");
        this.context = this;
        displayDataListView();
    }
    private void displayDataListView()
    {
        myDataHelper = new MyDataHelper(this);

        listView = (ListView) findViewById(R.id.listAllName);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                //Log.d("TAG", "clicked on item: " + position+" Db ID: "+customAdapter.getItemId(position));
                Intent intent = new Intent(all.this, DetailsInfoPage.class);
                Bundle b = new Bundle();
                b.putInt("key", Integer.parseInt(""+customAdapter.getItemId(position))); //Your id
                intent.putExtras(b); //Put your id to your next Intent
                myDataHelper.close();
                startActivity(intent);
                //finish();


            }
        });

        customAdapter = new CustomCursorAdapter(context,myDataHelper.getDataDb("All_Info"));
        listView.setAdapter(customAdapter);


        customAdapter.setFilterQueryProvider(new FilterQueryProvider() {
            @Override
            public Cursor runQuery(CharSequence constraint) {
                Log.d("LOG TAG", "runQuery constraint:"+constraint);
                return myDataHelper.fetchAllPeopleByName(constraint.toString());
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



}
