package com.example.fernando.farmingfarming;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import android.widget.TextView;


public class Profit extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profit);

        final Spinner regionSpinner = (Spinner) findViewById(R.id.profitRegionSpinner);
        final Button buttonRegionSpinner = (Button) findViewById(R.id.profitButtonToSpinner);
        final Spinner cropSpinner = (Spinner) findViewById(R.id.profitCropSpinner);
        final Button buttonCropSpinner = (Button) findViewById(R.id.profitButtonCrop);
        final TextView getServerData = (TextView) findViewById(R.id.profitExecute);
        final TextView waitExecute = (TextView) findViewById(R.id.profitWaiting);
        final SetServerReady serverEnable = new SetServerReady();


        CropStats currentStats;


        //Array adapter for choosing the Region
        ArrayAdapter<String> regionAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.regionItems));
        // Specify the layout to use when the list of choices appear+
        regionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        regionSpinner.setAdapter(regionAdapter);

        //Array adapter for choosing the crop
        ArrayAdapter<String> cropAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.cropItems));
        // Specify the layout to use when the list of choices appear+
        cropAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        cropSpinner.setAdapter(cropAdapter);


        /**
         * creating a click listener for the button, then having the spinner displayed
         * with all data so that the user doesn't have to click twice for it to open
         */
        buttonRegionSpinner.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {


                buttonRegionSpinner.setVisibility(View.INVISIBLE);
                regionSpinner.setVisibility(View.VISIBLE);
                regionSpinner.performClick();

                // region selected
                serverEnable.setRegion(true);
                toggleWaitingExecute(serverEnable);


            }
        });


        buttonCropSpinner.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                //Spinner regionSpinner = (Spinner) findViewById(R.id.profitRegionSpinner);
                buttonCropSpinner.setVisibility(View.INVISIBLE);
                cropSpinner.setVisibility(View.VISIBLE);
                cropSpinner.performClick();

                // region selected
                serverEnable.setCrop(true);
                toggleWaitingExecute(serverEnable);

            }
        });


        //TODO: find a way to do something with the selected items
        //TODO: specifically find a way to take the int, and be able to
        //TODO: use that data to call to the server


        getServerData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (serverEnable.getEnabled() == false) {

                    getServerData.setText("Choose Region/Crop");

                } else {


                    // server request URL
                    int regionID = regionSpinner.getSelectedItemPosition();
                    int cropID = cropSpinner.getSelectedItemPosition();
                    Log.d("regionID", regionID + "");
                    Log.d("cropID ", cropID + "");

                    Intent i = new Intent(Profit.this, CornProfit.class);
                    i.putExtra("region", regionID);
                    i.putExtra("crop", cropID);

                    //start the activity with
                    startActivity(i);

                    // create object and call AsyncTask execute method
                    // new LongOperaton().execute(serverURL);
                }

            }
        });
    }


    private void toggleWaitingExecute(SetServerReady get) {

        TextView waitExecute = (TextView) findViewById(R.id.profitWaiting);
        TextView execute = (TextView) findViewById(R.id.profitExecute);

        if (get.getEnabled() == true) {

            waitExecute.setVisibility(View.VISIBLE);
            waitExecute.setText("Press the Execute button");
            execute.setText("EXECUTE");


        } else{

            waitExecute.setVisibility(View.VISIBLE);
            waitExecute.setText("Choose a Region and a Crop");

        }

    }


}



