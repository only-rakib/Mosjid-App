package com.rakib.mosjid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class main_page extends AppCompatActivity {

    private MyDataHelper myDataHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        setTitle("Menu");
    }
    public void imageButtonExitClick(View v)
    {
        finish();
    }
    public void imageButtonSwitchClick(View v)
    {
        startActivity(new Intent(main_page.this, MainActivity.class));
        finish();
    }
    public void imageButtonAllClick(View v)
    {
        startActivity(new Intent(main_page.this, all.class));
        //myDataHelper = new MyDataHelper(this);
        //myDataHelper.getWritableDatabase();
    }
    public void imageButtonAddClick(View v)
    {
        startActivity(new Intent(main_page.this, AddPage.class));

    }
    public void imageButtonDueClick(View v)
    {
        startActivity(new Intent(main_page.this, DuePage.class));
    }
    public void imageButtonBalanceClick(View v)
    {
        startActivity(new Intent(main_page.this, BalanceSheet.class));
    }
    public void imageButtonPaidClick(View v)
    {
        startActivity(new Intent(main_page.this, PaidPage.class));
    }
    public void imageButtonAboutClick(View v)
    {
        startActivity(new Intent(main_page.this, about.class));
    }
    @Override
    public void onBackPressed()
    {
        return;

    }
}
