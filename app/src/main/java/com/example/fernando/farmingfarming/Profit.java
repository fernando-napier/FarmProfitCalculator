package com.example.fernando.farmingfarming;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class Profit extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profit);

        // Volley volley = new Volley();

        final Spinner regionSpinner = (Spinner) findViewById(R.id.profitRegionSpinner);
        final Button buttonRegionSpinner = (Button) findViewById(R.id.profitButtonToSpinner);
        final Spinner cropSpinner = (Spinner) findViewById(R.id.profitCropSpinner);
        final Button buttonCropSpinner = (Button) findViewById(R.id.profitButtonCrop);
        final TextView getServerData = (TextView) findViewById(R.id.profitExecute);
        //final TextView waiting = (TextView) findViewById(R.id.profitWaiting);
        //final TextView output = (TextView) findViewById(R.id.profitOutput);
        final SetServerReady serverEnable = new SetServerReady();
        int regionID = 0;


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
                Log.d("Region Set","its set");
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
                Log.d("Crop Set","its set");
                serverEnable.setCrop(true);

            }
        });


        getServerData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (serverEnable.getEnabled() == false) {

                    Log.d("serverenable","no");


                } else {

                    Log.d("serverenable","yes");
                    // server request URL
                    int regionID = regionSpinner.getSelectedItemPosition();
                    Log.d("regionID", " "+regionID);
                    CreateURL partURL = new CreateURL(regionID);
                    String serverURL = "http://10.0.2.2:8080/agapp/Corn.php?" + partURL.getURL();



                    Log.d("server request method",serverURL);
                    getServerRequestJSON(serverURL, regionID);
                    Log.d("server request method","completed");


                }

            }
        });
    }


    private void getServerRequestJSON(String url, int i) {

        final TextView output = (TextView) findViewById(R.id.profitOutput);
        final TextView waiting = (TextView) findViewById(R.id.profitWaiting);

        Log.d("url",url);
        Log.d("int"," "+i);

        JSONObject jason = null;

        /**
         *  this is where the volley requests occur.
         */


        //TODO: need to make sure that the server request actually returns a JSONobject
        RequestQueue req = Volley.newRequestQueue(this);
        StringRequest serverReq = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // the response is already constructed as a JSONObject

                        Log.d("actual server req", response.toString());
                        waiting.setVisibility(View.INVISIBLE);
                        output.setVisibility(View.VISIBLE);
                        output.setText("Test: " + response);


                    }

                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                //Error handling
                Log.d("actual server req","Something went wrong");
                error.printStackTrace();

            }


        });

        Volley.newRequestQueue(this).add(serverReq);

        Log.d("request queue", "made it through");






    }

}
