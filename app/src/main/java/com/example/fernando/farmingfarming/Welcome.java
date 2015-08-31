package com.example.fernando.farmingfarming;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class Welcome extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    GoogleApiClient apiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        final ImageView cornImage = (ImageView) findViewById(R.id.welcomeCorn);
        final ImageView soybeanImage = (ImageView) findViewById(R.id.welcomeSoybean);
        final ImageView cottonImage = (ImageView) findViewById(R.id.welcomeCotton);
        final TextView text = (TextView) findViewById(R.id.welcomeSampleText);
        final CenterHorizontalView imageScroll = (CenterHorizontalView) findViewById(R.id.welcomeScrollImages);
        imageScroll.setCurrentItemAndCenter(1);



        apiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        apiClient.connect();

        cornImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Log.d("current active item", "" + imageScroll.getCurrentActiveItem());
                //Log.d("corn ID", "" + cornImage.getId());
                if (imageScroll.getCurrentActiveItem() == 0) {

                    AlertDialog alert = new AlertDialog.Builder(Welcome.this).create();
                    alert.setTitle("Corn Chosen");
                    alert.setMessage("Choose an option!");
                    alert.setButton(AlertDialog.BUTTON_NEGATIVE, "Region Average",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent i = new Intent(Welcome.this, CornProfit.class);
                                    i.putExtra("region", 0);
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
        soybeanImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Log.d("current active item", "" + imageScroll.getCurrentActiveItem());
                //Log.d("soybean ID", "" + soybeanImage.getId());

                if (imageScroll.getCurrentActiveItem() == 1) {
                    AlertDialog alert = new AlertDialog.Builder(Welcome.this).create();
                    alert.setTitle("Soybean Chosen");
                    alert.setMessage("Choose an option!");
                    alert.setButton(AlertDialog.BUTTON_NEGATIVE, "Region Average",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
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
        cottonImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Log.d("current active item", "" + imageScroll.getCurrentActiveItem());
                // Log.d("cotton ID", "" + cottonImage.getId());


                if (imageScroll.getCurrentActiveItem() == 2) {


                    AlertDialog alert = new AlertDialog.Builder(Welcome.this).create();
                    alert.setTitle("Cotton Chosen");
                    alert.setMessage("Choose an option!");
                    alert.setButton(AlertDialog.BUTTON_NEGATIVE, "Region Average",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
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


    }

    /**
     * this method is made to show the last known location of the user
     */


    @Override
    public void onConnected(Bundle bundle) {

        double latitude;
        double longitude;
        List<Address> addresses = null;
        TextView text = (TextView) findViewById(R.id.welcomeSampleText);

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(apiClient);
        if (lastLocation != null) {
            latitude = lastLocation.getLatitude();
            longitude = lastLocation.getLongitude();
            try {
                addresses = geocoder.getFromLocation(latitude, longitude, 1);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (addresses == null) {

                    text.setText("Please Input Region");

                } else {

                    text.setText("State: " + addresses.get(0).getAdminArea());
                }
            }


        }


    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
