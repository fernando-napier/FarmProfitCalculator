package com.example.fernando.farmingfarming;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class ChangeVariables extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_variables);

        Bundle bundle = getIntent().getExtras();
        // get the extras from the previous intent
        int region = bundle.getInt("region");
        int crop = bundle.getInt("crop");
        Log.d("region CORNPROFIT", "" + region);
        Log.d("crop CORNPROFIT", "" + crop);

        CropStats corn = new CropStats(crop,region);
    }

}
