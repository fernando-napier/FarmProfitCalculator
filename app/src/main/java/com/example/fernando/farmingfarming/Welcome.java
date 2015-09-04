package com.example.fernando.farmingfarming;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

public class Welcome extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static int REQUEST_CODE_RECOVER_PLAY_SERVICES = 200;

    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private SliderLayout slider;
    private RegionData region = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        slider = (SliderLayout) findViewById(R.id.welcomeSlider);

        TextSliderView cornSlider = new TextSliderView(this);
        cornSlider.description("Corn").image(R.drawable.corn);
        slider.addSlider(cornSlider);
        TextSliderView soybeanSlider = new TextSliderView(this);
        soybeanSlider.description("Soybean").image(R.drawable.soybean);
        slider.addSlider(soybeanSlider);
        TextSliderView cottonSlider = new TextSliderView(this);
        cottonSlider.description("Cotton").image(R.drawable.cotton);
        slider.addSlider(cottonSlider);


        final Spinner spinner = (Spinner) findViewById(R.id.welcomeSpinner);
        final TextView spinnerText = (TextView) findViewById(R.id.welcomeSpinnerText);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);


        //TODO: find something to do with this if statement.
        if (checkGooglePlayServices() == false) {

        }


        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.regionItems, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appear
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        // make invisible because the texview shows "Select a state"

        //TODO: set a cancel button

        builder.setTitle("Choose a Region");
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            @Override

            // use which to set data accumulation
            public void onClick(DialogInterface dialog, int which) {

                region = new RegionData(which);
                spinnerText.setText("" + region.getRegionName());


            }
        });

        builder.create();

        spinnerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.show();

            }
        });


        //  spinner.setVisibility(View.VISIBLE);
        // spinner.setPrompt("Choose a Region");


        /**
         * this works perfectly...just need to implement for all images.!!!
         */
        cornSlider.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
            @Override
            public void onSliderClick(BaseSliderView v) {

                if (region == null) {

                    AlertDialog alert = new AlertDialog.Builder(Welcome.this).create();
                    alert.setTitle("Error");
                    alert.setMessage("Region not chosen!");
                    alert.setButton(AlertDialog.BUTTON_NEGATIVE, "Try Again",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    dialog.dismiss();
                                }
                            });

                    alert.show();


                } else {


                    Log.d("corn", "image pressed");

                    AlertDialog alert = new AlertDialog.Builder(Welcome.this).create();
                    alert.setTitle("Corn Chosen");
                    alert.setMessage("Choose an option!");
                    alert.setButton(AlertDialog.BUTTON_NEGATIVE, "Region Avg",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    Intent i = new Intent(Welcome.this, CornProfit.class);
                                    i.putExtra("region", region.getRegionID());
                                    i.putExtra("crop", 0);
                                    startActivity(i);
                                }
                            });
                    alert.setButton(AlertDialog.BUTTON_NEUTRAL, "Customize",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }


                            });
                    alert.setButton(AlertDialog.BUTTON_POSITIVE, "Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }


                            });

                    alert.show();

                }
            }
        });

        soybeanSlider.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
            @Override
            public void onSliderClick(BaseSliderView v) {

                Intent i = new Intent(Welcome.this, CornProfit.class);
                i.putExtra("crop", 0);
                i.putExtra("region", 1);
                startActivity(i);

            }
        });


    }


    private boolean checkGooglePlayServices() {

        int checkGooglePlayServices = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(this);
        if (checkGooglePlayServices != ConnectionResult.SUCCESS) {
            /*
            * google play services is missing or update is required
			*  return code could be
			* SUCCESS,
			* SERVICE_MISSING, SERVICE_VERSION_UPDATE_REQUIRED,
			* SERVICE_DISABLED, SERVICE_INVALID.
			*/
            GooglePlayServicesUtil.getErrorDialog(checkGooglePlayServices,
                    this, REQUEST_CODE_RECOVER_PLAY_SERVICES).show();

            return false;
        }

        return true;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_RECOVER_PLAY_SERVICES) {

            if (resultCode == RESULT_OK) {
                // Make sure the app is not already connected or attempting to connect
                if (!mGoogleApiClient.isConnecting() &&
                        !mGoogleApiClient.isConnected()) {
                    mGoogleApiClient.connect();
                }
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Google Play Services must be installed.",
                        Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

    }


    @Override
    public void onConnected(Bundle bundle) {

        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {

            Toast.makeText(this, "Latitude:" + mLastLocation.getLatitude() + ", Longitude:" + mLastLocation.getLongitude(), Toast.LENGTH_LONG).show();


        }

    }

    @Override
    public void onConnectionSuspended(int i) {

        Log.d("connnection", "suspended");

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

        Log.d("connnection", "failed");

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
        }
    }


}
