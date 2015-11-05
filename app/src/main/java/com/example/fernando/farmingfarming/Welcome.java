package com.example.fernando.farmingfarming;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.ortiz.touch.*;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import java.util.ArrayList;


public class Welcome extends AppCompatActivity {

    /*
        The first three data members are used in gathering information
        for the region and crop to be used.

        The last one is used as part of the tutorial that shows up on the app
     */


    private SliderLayout slider;
    private RegionData region = null;
    private int crop;
    private PopupWindow pWindow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);


        setTitle("What's your bottom line?");


        slider = (SliderLayout) findViewById(R.id.welcomeSlider);

        TextSliderView cornSlider = new TextSliderView(this);
        TextSliderView soybeanSlider = new TextSliderView(this);

        // add an image of corn to the scrolling view
        cornSlider.description("Corn").image(R.drawable.cornfield);
        slider.addSlider(cornSlider);

        // add an image of soybeans to the scrolling view
        soybeanSlider.description("Soybean").image(R.drawable.soybeanfield);
        slider.addSlider(soybeanSlider);


        final Spinner SPINNER = (Spinner) findViewById(R.id.welcomeSpinner);
        final TextView REGION_TEXT = (TextView) findViewById(R.id.welcomeRegionText);

        // REGION_TEXT being clicked pops up an image of the US divided by regions
        REGION_TEXT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String string = REGION_TEXT.getText().toString();
                Log.d("string", string);

                // the slider automatically stops the scrolling from the slider, as this was
                // causing an error when zooming in on the region image
                slider.stopAutoCycle();


                if ((string.equalsIgnoreCase("Help With Choosing a Region") || (string.equalsIgnoreCase("regions image")))) {
                    showRegionalImage();

                } else if (string.equalsIgnoreCase("Close Region Image")) {
                    REGION_TEXT.setText("Help With Choosing a Region");
                }


            }
        });

        // set in front of the slider view
        REGION_TEXT.bringToFront();


        /**
         * the next few onclicklisteners are for the images in the slider view
         */
        cornSlider.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
            @Override
            public void onSliderClick(BaseSliderView v) {

                //corn is 0
                crop = 0;

                if (region == null) {

                    initialDialogPopup(SPINNER, crop);

                }


                if (region != null) {

                    createModelDialogBox(SPINNER, crop);

                }
            }
        });

        soybeanSlider.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
            @Override
            public void onSliderClick(BaseSliderView v) {

                //soybean is 1
                crop = 1;

                if (region == null) {

                    initialDialogPopup(SPINNER, crop);

                }

                if (region != null) {

                    createModelDialogBox(SPINNER, crop);

                }
            }
        });

        if (isFirstTime()) {

            initiateTutorial();

        }

    }

    // this method is used to open up the tutorial the first time the app is opened
    // its the only point at which a sharedpreference is used
    private boolean isFirstTime() {

        // create the shared preference
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);

        // set it to false if it doesn't exist
        String value = "WelcomeRandBefore";
        boolean ranBefore = preferences.getBoolean(value, false);

        // this if statement only runs once,
        if (!ranBefore) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(value, true);
            editor.commit();
        }
        return !ranBefore;
    }


    // create a tutorial from welcome_menu_popup layout
    private void initiateTutorial() {
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

            // make the tutorial transparent
            pWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


            // button to be able to close the popup
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

    // this is a guided dialog that simply gives the user an explanation that they need to
    // select a region after they select a crop
    private void initialDialogPopup(final Spinner SPINNER, final int CROP) {
        AlertDialog alert = new AlertDialog.Builder(Welcome.this).create();
        alert.setTitle("Getting Started");
        alert.setMessage("In order to view a model for our application you would first need to choose a region of study.");
        alert.setButton(AlertDialog.BUTTON_NEUTRAL, "CHOOSE A REGION",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        createRegionDialogBox(SPINNER, CROP);

                    }
                });

        // without show() the dialog would not pop up
        alert.show();
    }


    // this method opens the regions image as a TouchImageView, allowing users to zoom
    // we are bringing the regiontext to front in order to allow for closing of the region image
    private void showRegionalImage() {

        final TextView regionText = (TextView) findViewById(R.id.welcomeRegionText);
        regionText.setText("Close Region Image");
        final TouchImageView regionImage = (TouchImageView) findViewById(R.id.welcomeTouchImage);
        regionImage.setVisibility(View.VISIBLE);
        regionImage.bringToFront();
        regionText.bringToFront();
        regionText.setText("Close Region Image");

        //single tap resets the image to the original size
        // double tap zooms into the image
        regionImage.setOnDoubleTapListener(new GestureDetector.OnDoubleTapListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                Log.d("this", "onsingletapconfirmed");
                regionImage.resetZoom();
                regionText.setText("Close Region Image");
                regionText.bringToFront();
                return false;
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                Log.d("image", "ondoubletap");

                regionImage.resetZoom();
                regionText.setText("Close Region Image");
                regionImage.bringToFront();

                return false;
            }

            @Override
            public boolean onDoubleTapEvent(MotionEvent e) {
                return false;
            }
        });

        // this click listener closes the
        regionText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String string = regionText.getText().toString();
                Log.d("popup text", string);

                // if the
                if (string.equalsIgnoreCase("Help With Choosing a Region") || string.equalsIgnoreCase("Regions Image")) {
                    Log.d("within method", "help/regions");
                    regionImage.setVisibility(View.VISIBLE);
                    regionText.setText("Close Region Image");
                    regionImage.resetZoom();


                    regionImage.bringToFront();
                    //regionText.bringToFront();

                } else if (string.equalsIgnoreCase("Close Region Image")) {
                    Log.d("within method", "close image");
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
     */
    private void createRegionDialogBox(final Spinner spinner, int id) {

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

        //TODO: set a cancel button

        builder.setTitle("Choose a Region");
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            @Override

            // use which to set data accumulation
            public void onClick(DialogInterface dialog, int which) {

                region = new RegionData(which);

                createModelDialogBox(spinner, cropID);


            }
        });

        builder.create();
        builder.show();


    }

    //This method allows a user to either go to the next activity and set their own variables
    // or it can allow you to change the chosen region
    private void createModelDialogBox(final Spinner spinner, int id) {

        AlertDialog alert = new AlertDialog.Builder(Welcome.this).create();

        String name;
        final int cropID = id;

        // get the string of the crop for setting the message of the alert dialog
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
        alert.setButton(AlertDialog.BUTTON_NEUTRAL, "Set Own Values",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // this intent passes a parcelable arraylist of size 1 with only the regional average
                        // this is because the other activities could have multiple objects within the arraylist
                        Intent i = new Intent(Welcome.this, Custom.class);
                        ArrayList<CropStats> cropStatsArrayList = new ArrayList<>();
                        cropStatsArrayList.add(new CropStats(crop, region.getRegionID()));
                        i.putParcelableArrayListExtra("crops", cropStatsArrayList);
                        startActivity(i);

                    }
                });
        // if this button is pressed then it opens the regional dialog
        alert.setButton(AlertDialog.BUTTON_POSITIVE, "Change Region", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                createRegionDialogBox(spinner, cropID);
            }
        });

        alert.show();

    }


}
