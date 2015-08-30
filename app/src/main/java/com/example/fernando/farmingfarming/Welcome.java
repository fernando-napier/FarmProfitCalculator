package com.example.fernando.farmingfarming;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Welcome extends AppCompatActivity {

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

                if (imageScroll.getCurrentActiveItem() == 0) {
                    cornImage.performClick();

                } else if (imageScroll.getCurrentActiveItem() == 1) {
                    soybeanImage.performClick();

                } else if (imageScroll.getCurrentActiveItem() == 2) {
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

    protected synchronized void buildGoogleApiClient(){
       // GoogleApiClient apiClient = new GoogleApiClient.Builder(this);
    }

}
