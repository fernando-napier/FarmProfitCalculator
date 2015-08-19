package com.example.fernando.farmingfarming;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.os.AsyncTask;
import android.widget.TextView;

import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Profit extends AppCompatActivity  {





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profit);

        final Spinner regionSpinner = (Spinner) findViewById(R.id.profitRegionSpinner);
        final Button buttonRegionSpinner  = (Button) findViewById(R.id.profitButtonToSpinner);
        final Spinner cropSpinner = (Spinner) findViewById(R.id.profitCropSpinner);
        final Button buttonCropSpinner = (Button) findViewById(R.id.profitButtonCrop);
        final Button getServerData = (Button) findViewById(R.id.profitExecute);
        final SetServerReady serverEnable = new SetServerReady();
        int regionID = 0;


        //Array adapter for choosing the Region
        ArrayAdapter<String> regionAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,getResources().getStringArray(R.array.regionItems));
        // Specify the layout to use when the list of choices appear+
        regionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        regionSpinner.setAdapter(regionAdapter);

        //Array adapter for choosing the crop
        ArrayAdapter<String> cropAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,getResources().getStringArray(R.array.cropItems));
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

            }
        });


        //TODO: find a way to do something with the selected items
        //TODO: specifically find a way to take the int, and be able to
        //TODO: use that data to call to the server


        getServerData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(serverEnable.getEnabled()==false){

                    getServerData.setText("Choose Region/Crop");

                } else {


                    // server request URL
                    int regionID = regionSpinner.getSelectedItemPosition();
                    CreateURL partURL = new CreateURL(regionID);
                    String serverURL = "http://server.farmtwat.com/Corn.php?"+partURL.getURL();

                    // create object and call AsyncTask execute method
                   // new LongOperaton().execute(serverURL);
                }

            }
        });

/**
 *
 * this is what happens when something is selected from spinners
 * theres nothing to do with this code for the time being
 * just going to keep it in case there needs to be some updates for the next round
 *
 *

        regionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        cropSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

 */

    class LongOperation extends AsyncTask<String, Void, JSONArray> {

        @Override
        protected JSONArray doInBackground(String... params) {
            URL url;
            HttpURLConnection urlConnection = null;
            JSONArray response = new JSONArray();

            try {
                url = new URL(params[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                int responseCode = urlConnection.getResponseCode();

                if(responseCode == HttpStatus.SC_OK){
                    String responseString = readStream(urlConnection.getInputStream());
                    Log.v("CatalogClient", responseString);
                    response = new JSONArray(responseString);
                }else{
                    Log.v("CatalogClient", "Response code:"+ responseCode);
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if(urlConnection != null)
                    urlConnection.disconnect();
            }

            return response;
        }


        private String readStream(InputStream in) {
            BufferedReader reader = null;
            StringBuffer response = new StringBuffer();
            try {
                reader = new BufferedReader(new InputStreamReader(in));
                String line = "";
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return response.toString();
            }
        }





    }
}
