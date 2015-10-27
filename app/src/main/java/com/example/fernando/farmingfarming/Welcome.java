package com.example.fernando.farmingfarming;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ortiz.touch.*;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;


import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Welcome extends AppCompatActivity {

    private static int REQUEST_CODE_RECOVER_PLAY_SERVICES = 200;


    private SliderLayout slider;
    private RegionData region = null;
    private int crop;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);


        setTitle("What's your bottom line?");


        slider = (SliderLayout) findViewById(R.id.welcomeSlider);

        final TextSliderView cornSlider = new TextSliderView(this);
        cornSlider.description("Corn").image(R.drawable.corn);
        slider.addSlider(cornSlider);
        TextSliderView soybeanSlider = new TextSliderView(this);
        soybeanSlider.description("Soybean").image(R.drawable.soybean);
        slider.addSlider(soybeanSlider);


        final Spinner spinner = (Spinner) findViewById(R.id.welcomeSpinner);
        final TextView spinnerText = (TextView) findViewById(R.id.welcomeCropText);
        // final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        final TextView regionText = (TextView) findViewById(R.id.welcomeRegionText);
        regionText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String string = regionText.getText().toString();
                Log.d("main class text", string);
                slider.stopAutoCycle();

                if ((string.equalsIgnoreCase("Help With Choosing a Region") || (string.equalsIgnoreCase("regions image")))) {

                    regionText.setText("Close Region Image");
                    showPopup();

                } else if (string.equalsIgnoreCase("Close Region Image")) ;
                regionText.setText("Help With Choosing a Region");


            }
        });


        /**
         * this works perfectly...just need to implement for all images.!!!
         */
        cornSlider.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
            @Override
            public void onSliderClick(BaseSliderView v) {

                //corn is 0
                crop = 0;

                if (region == null) {

                    AlertDialog alert = new AlertDialog.Builder(Welcome.this).create();
                    alert.setTitle("Getting Started");
                    alert.setMessage("In order to view a model for our application you would first need to choose a region of study.");
                    alert.setButton(AlertDialog.BUTTON_NEUTRAL, "CHOOSE A REGION",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    createRegionDialogBox(spinner, spinnerText, 0);


                                }
                            });

                    //alert.setCanceledOnTouchOutside(false);
                    alert.show();


                }


                if (region != null) {


                    createModelDialogBox(spinner, spinnerText, crop);


                }
            }
        });

        soybeanSlider.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
            @Override
            public void onSliderClick(BaseSliderView v) {

                //soybean is 1
                crop = 1;

                if (region == null) {

                    AlertDialog alert = new AlertDialog.Builder(Welcome.this).create();
                    alert.setTitle("Getting Started");
                    alert.setMessage("In order to view a model for our application you would first need to choose a region of study.");
                    alert.setButton(AlertDialog.BUTTON_NEUTRAL, "CHOOSE A REGION",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    createRegionDialogBox(spinner, spinnerText, 1);


                                }
                            });

                    //alert.setCanceledOnTouchOutside(false);
                    alert.show();


                }


                if (region != null) {


                    createModelDialogBox(spinner, spinnerText, crop);


                }
            }
        });

        if (isFirstTime()) {
            initiatePopupWindow();
        }


    }

    private boolean isFirstTime() {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        boolean ranBefore = preferences.getBoolean("WelcomeRanBefore", false);
        if (!ranBefore) {
            // first time
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("WelcomeRanBefore", true);
            editor.commit();
        }
        return !ranBefore;
    }

    private void showPopup() {

        final TextView regionText = (TextView) findViewById(R.id.welcomeRegionText);
        regionText.setText("Close Region Image");
        final TouchImageView regionImage = (TouchImageView) findViewById(R.id.welcomeTouchImage);
        regionImage.setVisibility(View.VISIBLE);
        regionImage.bringToFront();
        regionText.bringToFront();
        regionText.setText("Close Region Image");

        regionImage.setOnDoubleTapListener(new GestureDetector.OnDoubleTapListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                regionImage.resetZoom();
                return false;
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {

                regionImage.resetZoom();
                regionText.setText("Close Region Image");

                return false;
            }

            @Override
            public boolean onDoubleTapEvent(MotionEvent e) {
                return false;
            }
        });
        regionText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String string = regionText.getText().toString();
                Log.d("popup text", string);

                if (string.equalsIgnoreCase("Help With Choosing a Region")) {
                    Log.d("showpopup", "true");
                    regionImage.setVisibility(View.VISIBLE);


                    regionImage.bringToFront();
                    regionText.bringToFront();

                } else if (string.equalsIgnoreCase("Close Region Image")) {
                    Log.d("do not popup", "true");
                    regionText.setText("Help With Choosing a Region");
                    regionImage.setVisibility(View.INVISIBLE);
                }


            }
        });


    }


    /**
     * from the which int chosen, then region then is instatiated
     *
     * @param spinner
     * @param spinnerText
     */
    private void createRegionDialogBox(final Spinner spinner, final TextView spinnerText, int id) {

        final int cropID = id;
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        final ArrayAdapter<CharSequence> adapter;
        if (cropID == 0) {

            adapter = ArrayAdapter.createFromResource(this, R.array.regionItemsCorn, android.R.layout.simple_spinner_item);

        } else {

            adapter = ArrayAdapter.createFromResource(this, R.array.regionItemsSoybean, android.R.layout.simple_spinner_item);

        }
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
                //spinnerText.setText("" + region.getRegionName());
                createModelDialogBox(spinner, spinnerText, cropID);


            }
        });

        builder.create();
        builder.show();


    }

    //TODO figure out why this method passes a textview
    private void createModelDialogBox(final Spinner spinner, final TextView spinnerText, int id) {

        AlertDialog alert = new AlertDialog.Builder(Welcome.this).create();

        String name;
        final int cropID = id;

        switch (cropID) {
            case 0:
                name = "Corn";
                break;
            case 1:
                name = "Soybean";
                break;
            default:
                name = "Crop";
                break;
        }

        alert.setTitle("Model Variables Ready");
        alert.setMessage("The model will show " + name + " averages for the " + region.getRegionName());
 /*       alert.setButton(AlertDialog.BUTTON_NEGATIVE, "Go to Model",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        Intent i = new Intent(Welcome.this, CornProfit.class);
                        ArrayList<CropStats> cropStatsArrayList = new ArrayList<>();
                        cropStatsArrayList.add(new CropStats(crop, region.getRegionID()));
                        i.putParcelableArrayListExtra("crops", cropStatsArrayList);
                        final ProgressDialog RENDER = ProgressDialog.show(Welcome.this, "", "Rendering...");
                        RENDER.setCancelable(true);


                        new Timer().schedule(
                                new TimerTask() {
                                    @Override
                                    public void run() {
                                        RENDER.dismiss();

                                    }
                                },
                                2000L
                        );

                        startActivity(i);


                    }
                });*/


        alert.setButton(AlertDialog.BUTTON_NEUTRAL, "Set Own Values",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent i = new Intent(Welcome.this, CustomCornCost.class);
                        ArrayList<CropStats> cropStatsArrayList = new ArrayList<>();
                        cropStatsArrayList.add(new CropStats(crop, region.getRegionID()));
                        i.putParcelableArrayListExtra("crops", cropStatsArrayList);
                        startActivity(i);

                    }
                });

        alert.setButton(AlertDialog.BUTTON_POSITIVE, "Change Region", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                createModelDialogBox(spinner, spinnerText, cropID);
            }
        });

        alert.show();

    }

    private PopupWindow pWindow;

    private void initiatePopupWindow() {
        try {
// We need to get the instance of the LayoutInflater
            LayoutInflater inflater = (LayoutInflater) Welcome.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View layout = inflater.inflate(R.layout.welcome_menu_popup,
                    (ViewGroup) findViewById(R.id.popup_element));
            pWindow = new PopupWindow(layout, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT, true);
            findViewById(R.id.welcomeBackground).post(new Runnable() {
                @Override
                public void run() {
                    pWindow.showAtLocation(layout, Gravity.TOP, 0, 0);

                }
            });


            Button btnClosePopup = (Button) layout.findViewById(R.id.welcomeMenuButton);
            btnClosePopup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pWindow.dismiss();

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
