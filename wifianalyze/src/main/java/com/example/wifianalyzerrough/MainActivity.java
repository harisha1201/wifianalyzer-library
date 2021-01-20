package com.example.wifianalyzerrough;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnAccessPints, btnSSID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAccessPints = (Button)findViewById(R.id.access_points);
        btnSSID = (Button)findViewById(R.id.ssid);

        btnAccessPints.setOnClickListener(v -> {
            Intent next = new Intent(this, AccessPointActivity.class);
            startActivity(next);
        });
        btnSSID.setOnClickListener(v -> {
        });


    }

}