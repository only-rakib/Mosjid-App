package com.rakib.mosjid;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DetailsInfoPage extends AppCompatActivity {
    private ListView listView;
    private MyDataHelper myDataHelper;
    //private String[] columnNames;
    //private MyArrayAdapter myArrayAdapter;
    private String dataID;
    private Context context;
    private MenuItem menuItem;
    private String updateButtonState = "notVisible";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_info_page);
        setTitle("View");
        myDataHelper = new MyDataHelper(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.context = this;

        Bundle b = getIntent().getExtras();
        int value = -1; // or other values
        if(b != null) {
            value = b.getInt("key");
            dataID = ""+value;
            populateListView(dataID,"Initial");
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.actionbar_menu, menu);

        if(updateButtonState.equals("Visible"))
        {
            menu.findItem(R.id.menuUpdate).setVisible(true);
            menu.findItem(R.id.menuEdit).setVisible(false);
            menu.findItem(R.id.menuDelete).setVisible(false);
        }
        else
        {
            menu.findItem(R.id.menuUpdate).setVisible(false);
            menu.findItem(R.id.menuEdit).setVisible(true);
            menu.findItem(R.id.menuDelete).setVisible(true);
        }
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menuDelete) {
            alertMessage(dataID);
            return true;
        }
        else if(id==R.id.menuEdit){
            updateButtonState = "Visible";
            populateListView(dataID,"edit");
            invalidateOptionsMenu();
            setTitle("Edit");
            return true;
        }
        else if(id==R.id.menuUpdate){
            updateButtonState = "notVisible";
            invalidateOptionsMenu();
            setTitle("View");
            getTextFromListView();
            populateListView(dataID,"Initial");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void populateListView(String ID,String Operation)
    {

        String[] columnNames = myDataHelper.getAllColumnName();
        Cursor cursor = myDataHelper.getDataFromID(ID);
        final ArrayList<MonthCollectionAndDetailsInfo> arrayList = new ArrayList<>();
        int n = cursor.getColumnCount();
        String[] info =new String[n];
        if(cursor.getCount()>0)
        {
            while(cursor.moveToNext())
            {
                for(int i=1;i<n;i++)
                {
                    if (cursor.getString(i) == null)
                    {
                        info[i] = "0";
                    }
                    else {
                        info[i] = cursor.getString(i);
                    }
                }


            }

        }
        for(int i=1;i<n;i++)
        {
            MonthCollectionAndDetailsInfo monthCollectionAndDetailsInfo = new MonthCollectionAndDetailsInfo(columnNames[i],info[i]);
            arrayList.add(monthCollectionAndDetailsInfo);
        }

        ArrayList<MonthCollectionAndDetailsInfo> arrayOfUsers = new ArrayList<>();
        if(Operation.equals("Initial")){
            MyArrayAdapter myArrayAdapter = new MyArrayAdapter(this,R.layout.horizontal_list_view,arrayOfUsers);
            ListView listView = (ListView) findViewById(R.id.allInfo);
            listView.setAdapter(myArrayAdapter);
            myArrayAdapter.addAll(arrayList);
        }
        else
        {
           ArrayAdapterForEdit adapterForEdit = new ArrayAdapterForEdit(this,R.layout.horizontal_list_edit,arrayOfUsers);
            ListView listView = (ListView) findViewById(R.id.allInfo);
            listView.setAdapter(adapterForEdit);
            adapterForEdit.addAll(arrayList);
        }

        myDataHelper.close();
        /*ListView listView = (ListView) findViewById(R.id.allInfo);
        listView.setAdapter(myArrayAdapter);
        myArrayAdapter.addAll(arrayList);*/

    }


    private void alertMessage(final String idd)
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Do you want to delete it?");
        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(input);
        // Set up the buttons
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String m_Text = input.getText().toString();
                //Log.d("alert",m_Text);
                if(m_Text.equals("admin")){
                    boolean res = myDataHelper.deleteData(idd);
                    if(res)
                    {
                        startActivity(new Intent(DetailsInfoPage.this, all.class));
                        finish();
                        Toast.makeText(context,"Deleted",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(context,"Failed",Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(context,"Wrong Password",Toast.LENGTH_SHORT).show();
                }
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
    public void getTextFromListView()
    {
        ListView listView = (ListView) findViewById(R.id.allInfo);
        int listSize = listView.getAdapter().getCount();
        String[] colName = new String[listSize];
        String[] updateValue = new String[listSize];
        for (int i = 0; i < listSize; i++) {
            View viewTelefone = listView.getChildAt(i);
            TextView colTextView = (TextView) viewTelefone.findViewById(R.id.text1);
            EditText editedEditText = (EditText) viewTelefone.findViewById(R.id.text2);
            colName[i]=colTextView.getText().toString();
            updateValue[i] = editedEditText.getText().toString();

            //Log.d("TAG", colTextView.getText().toString() + ":" + editedEditText.getText().toString());
        }
        boolean res = myDataHelper.updateEditedValues(colName,updateValue,dataID);
        if(res)
        {
            Toast.makeText(this,"Updated",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this,"Failed!",Toast.LENGTH_SHORT).show();
        }
        myDataHelper.close();
    }

}
