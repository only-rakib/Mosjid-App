package com.rakib.mosjid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddPage extends AppCompatActivity {

    private EditText textName,textFather,textPhone;
    private Button buttonAdd;
    private MyDataHelper myDataHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_page);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Add Person");

        textName =(EditText) findViewById(R.id.editTextName);
        textFather =(EditText) findViewById(R.id.editTextFatherName);
        textPhone =(EditText) findViewById(R.id.editTextPhone);

    }
    public void buttonAddClick(View v)
    {

        String name = textName.getText().toString();
        String father = textFather.getText().toString();
        String phone = textPhone.getText().toString();
        myDataHelper = new MyDataHelper(this);

        if(name.length()>0)
        {
            long val = myDataHelper.insertPeopleDb(name,father,phone);
            if(val>0)
            {
                Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_SHORT).show();
            }
            textName.setText("");
            textFather.setText("");
            textPhone.setText("");
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Fill the boxes",Toast.LENGTH_SHORT).show();
        }

        myDataHelper.close();
    }
}
