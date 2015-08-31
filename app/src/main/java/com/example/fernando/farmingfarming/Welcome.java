package com.example.fernando.farmingfarming;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

public class Welcome extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static int REQUEST_CODE_RECOVER_PLAY_SERVICES = 200;

    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;


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


        cornImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("current active item", "" + imageScroll.getCurrentActiveItem());
                Log.d("corn ID", "" + cornImage.getId());
                if (imageScroll.getCurrentActiveItem() == 0) {
                    text.setText("Corn Selected");
                    AlertDialog alert = new AlertDialog.Builder(Welcome.this).create();
                    alert.setTitle("Corn Chosen");
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

                } else if (imageScroll.getCurrentActiveItem() == 1) {
                    soybeanImage.performClick();

                } else if (imageScroll.getCurrentActiveItem() == 2) {
                    cottonImage.performClick();

                }

            }
        });
        soybeanImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("current active item", "" + imageScroll.getCurrentActiveItem());
                Log.d("soybean ID", "" + soybeanImage.getId());

                if (imageScroll.getCurrentActiveItem() == 0) {
                    cornImage.performClick();

                } else if (imageScroll.getCurrentActiveItem() == 1) {
                    text.setText("Soybean Selected");
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

                } else if (imageScroll.getCurrentActiveItem() == 2) {
                    cottonImage.performClick();

                }


            }

        });
        cottonImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("current active item", "" + imageScroll.getCurrentActiveItem());
                Log.d("cotton ID", "" + cottonImage.getId());

                if (imageScroll.getCurrentActiveItem() == 2) {
                    text.setText("Cotton Selected");

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

        Log.d("connnection","suspended");

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

        Log.d("connnection","failed");

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
