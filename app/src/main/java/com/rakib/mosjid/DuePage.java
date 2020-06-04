package com.rakib.mosjid;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DuePage extends AppCompatActivity {
    private MyDataHelper myDataHelper;
    private ListView listView;
    private Context context;
    private SearchView searchView;
    //private String m_Text = "";
    private String pos_for_update="";
    private ArrayList<DataStore> arrayList;
    private String[] items;
    private MyArrayAdapter adapter;
    private boolean fromWhereClickList=false;
    private ArrayList<DataStore> filteredArray;
    private CustomCursorAdapter customAdapter;
    private Cursor cursor=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_due_page);
        setTitle("Due List");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.context=this;
        myDataHelper = new MyDataHelper(this);
        //customAdapter = new CustomCursorAdapter(this,cursor);

        //displayDataListViewArrayAdapter();
        displayDataListViewCursorAdapter();


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

    private void displayDataListViewCursorAdapter()
    {

        listView = (ListView) findViewById(R.id.listAllName);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                //Log.d("TAG", "clicked on item: " + position+" Db ID: "+customAdapter.getItemId(position));
                alertMessage(customAdapter.getItemId(position));

            }
        });
        cursor = myDataHelper.getDataDue("All_Info",columnName());
        customAdapter = new CustomCursorAdapter(context,cursor);
        listView.setAdapter(customAdapter);


        /**
         * search
         */
       customAdapter.setFilterQueryProvider(new FilterQueryProvider() {
            @Override
            public Cursor runQuery(CharSequence constraint) {
                Log.d("LOG TAG", "runQuery constraint:"+constraint);
                return myDataHelper.fetchPeopleByNameDue(constraint.toString(),columnName());
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

    private void alertMessage(final long ID)
    {
                //Log.d("Alert",""+ID);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Pay taka");
                // Set up the input
        final EditText input = new EditText(context);
                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        builder.setView(input);

                // Set up the buttons
        builder.setPositiveButton("Pay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String m_Text = input.getText().toString();



                AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                builder1.setTitle("Enter Password");
                // Set up the input
                final EditText input = new EditText(context);
                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                builder1.setView(input);
                // Set up the buttons
                builder1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String pass_Text = input.getText().toString();
                        //Log.d("alert",m_Text);
                        if(pass_Text.equals("admin")){
                            try{
                                int a = Integer.parseInt(m_Text);
                                boolean res = myDataHelper.updateDueToPay(columnName(),ID+"",m_Text);
                                if(res)
                                {
                                    Toast.makeText(context, "Successfully Add the amount", Toast.LENGTH_SHORT).show();
                                    displayDataListViewCursorAdapter();
                                }
                                else {
                                    Toast.makeText(context, "Operation Failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                            catch (Exception e)
                            {
                                Toast.makeText(context, "Faild! Enter decimal number ", Toast.LENGTH_LONG).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(context,"Wrong Password",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder1.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder1.show();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        myDataHelper.close();
        builder.show();
    }

}
