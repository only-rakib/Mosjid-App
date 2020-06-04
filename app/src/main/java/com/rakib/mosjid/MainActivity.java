package com.rakib.mosjid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.Calendar;


public class MainActivity extends AppCompatActivity {

    private ImageButton loginButton;
    private EditText userName,pass;
    private MyDataHelper myDataHelper;
    private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = (AdView  ) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        mAdView.loadAd(adRequest);

        /*AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("ca-app-pub-3967653352921641/4116894439");*/

        loginButton = (ImageButton)findViewById(R.id.imageButtonLogin);
        userName = (EditText) findViewById(R.id.editTextUser);
        pass = (EditText) findViewById(R.id.editTextPass);


        Calendar calendar = Calendar.getInstance();

        int thisYear = calendar.get(Calendar.YEAR);
        int thisMonth = calendar.get(Calendar.MONTH);
        int thisDay = calendar.get(Calendar.DAY_OF_MONTH);
        myDataHelper = new MyDataHelper(this);
        myDataHelper.addRowToTable(thisMonth,thisYear);
        //Toast.makeText(getApplicationContext(),"res: " +res,Toast.LENGTH_LONG).show();


    }
    public void loginButtonClick(View v)
    {
        Intent intent = new Intent(MainActivity.this,main_page.class);
        startActivity(intent);
        Toast.makeText(getApplicationContext(),"Logged in",Toast.LENGTH_SHORT).show();
        finish();
    }

}
