package com.example.fernando.farmingfarming;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by fernando on 10/4/15.
 */
public class CustomCornCost extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_custom_corn);

        setTitle("Customize Variables");

        //Create the SQLite db for first time users
        final SQLiteDatabase DB_STATS = openOrCreateDatabase("stats", MODE_PRIVATE, null);


        Bundle bundle = getIntent().getExtras();

        // get the extras from the previous intent
        final ArrayList<CropStats> cropStatsArrayList = bundle.getParcelableArrayList("crops");
        final CropStats CORN = cropStatsArrayList.get(0);

        setTitle("Customize "+ CORN.getRegionName() + " " + CORN.getCropName() );

        // get the data for the region as added in the previous activity (welcome class)
        RegionData regionData = new RegionData(CORN.getRegion());

        // get the statistics for the region


        final TextView ACRE_ALERT = (TextView) findViewById(R.id.customAcresAlert);
        final TextView YIELD_ALERT = (TextView) findViewById(R.id.customYieldAlert);
        ACRE_ALERT.setTextColor(Color.RED);
        YIELD_ALERT.setTextColor(Color.RED);


        final TextView COST_REFRESH_BUTTON = (TextView) findViewById(R.id.customCostRefresh);
        final TextView TOTAL_COST_VIEW = (TextView) findViewById(R.id.customTotalCost);
        final ToggleButton COST_TOGGLE_BUTTON = (ToggleButton) findViewById(R.id.customToggleButton);


        /**
         * these are the first boxes that need to be manipulated
         *
         * the GUI should function such that the person enters some text
         * and then the enter button then sets the text as a regular textbox
         * also the enter button then becomes an edit button
         */
        final EditText ACRE_EDIT_TEXT = (EditText) findViewById(R.id.customAcreSetValue);
        final TextView ACRE_DISPLAY = (TextView) findViewById(R.id.customAcreDisplayValue);

        final EditText YIELD_EDIT_TEXT = (EditText) findViewById(R.id.customYieldSetValue);
        final TextView YIELD_DISPLAY = (TextView) findViewById(R.id.customYieldDisplayValues);

        final EditText SEED_SET = (EditText) findViewById(R.id.customSeedSetValue);

        final EditText FERTILIZER_SET = (EditText) findViewById(R.id.customFertilizerSetValue);

        final EditText CHEMICALS_SET = (EditText) findViewById(R.id.customChemicalsSetValue);

        final EditText CUSTOM_OPS_SET = (EditText) findViewById(R.id.customCustomOpsSetValue);

        final EditText FUEL_LUBE_SET = (EditText) findViewById(R.id.customFueLubeElecSetValue);

        final EditText REPAIRS_SET = (EditText) findViewById(R.id.customRepairsSetValue);

        final EditText IRRIGATION_WATER_SET = (EditText) findViewById(R.id.customIrrigationWaterSetValue);

        final EditText INT_ON_CAP_SET = (EditText) findViewById(R.id.customIntOnCapSetValue);

        final EditText HIRED_LABOR_SET = (EditText) findViewById(R.id.customHiredLaborSetValue);

        final EditText UNPAID_LABOR_SET = (EditText) findViewById(R.id.customUnpaidLaborSetValue);

        final EditText RECOVERY_EQUIP_SET = (EditText) findViewById(R.id.customRecoveryOfEquipSetValue);

        final EditText LAND_RENTAL_RATE_SET = (EditText) findViewById(R.id.customLandRentalRateSetValue);

        final EditText TAX_INSURANCE_SET = (EditText) findViewById(R.id.customTaxInsuranceSetValue);

        final EditText MISC_SET = (EditText) findViewById(R.id.customMiscSetValue);

        ACRE_EDIT_TEXT.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if ((!hasFocus) && (v.toString().length() > 0)) {
                    ACRE_DISPLAY.setText(ACRE_EDIT_TEXT.getText());
                    ACRE_EDIT_TEXT.setVisibility(View.INVISIBLE);
                    ACRE_DISPLAY.setVisibility(View.VISIBLE);
                    CORN.setSizeAcresPlanted(Integer.parseInt(ACRE_EDIT_TEXT.getText().toString()));
                    ACRE_ALERT.setVisibility(View.INVISIBLE);

                    Log.d("acres",ACRE_EDIT_TEXT.getText().toString());

                    // YIELD_DISPLAY.setVisibility(View.VISIBLE);
                    // YIELD_EDIT_TEXT.setVisibility(View.INVISIBLE);

                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(YIELD_EDIT_TEXT, InputMethodManager.SHOW_IMPLICIT);


                } else if ((!hasFocus) && (v.toString().length() == 0)) {
                    ACRE_DISPLAY.setText("Set a Value");
                    ACRE_EDIT_TEXT.setVisibility(View.INVISIBLE);
                    ACRE_DISPLAY.setVisibility(View.VISIBLE);
                    ACRE_ALERT.setVisibility(View.INVISIBLE);

                } else if (hasFocus) {
                    YIELD_DISPLAY.setVisibility(View.VISIBLE);
                    YIELD_EDIT_TEXT.setVisibility(View.INVISIBLE);
                }
            }
        });

        ACRE_EDIT_TEXT.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                ACRE_DISPLAY.setText(ACRE_EDIT_TEXT.getText());
                //ACRE_EDIT_TEXT.setVisibility(View.INVISIBLE);
                //ACRE_DISPLAY.setVisibility(View.VISIBLE);
                ACRE_ALERT.setVisibility(View.INVISIBLE);

                YIELD_DISPLAY.setVisibility(View.VISIBLE);
                YIELD_EDIT_TEXT.setVisibility(View.INVISIBLE);

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(ACRE_EDIT_TEXT, InputMethodManager.SHOW_IMPLICIT);


            }
        });


        ACRE_EDIT_TEXT.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    ACRE_DISPLAY.setVisibility(View.VISIBLE);
                    ACRE_EDIT_TEXT.setVisibility(View.INVISIBLE);
                    CORN.setSizeAcresPlanted(Integer.parseInt(ACRE_EDIT_TEXT.getText().toString()));
                    Log.d("acres", ACRE_EDIT_TEXT.getText().toString());

                    return true;
                }
                return false;
            }

        });

        ACRE_DISPLAY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ACRE_EDIT_TEXT.setText(ACRE_DISPLAY.getText());
                ACRE_EDIT_TEXT.setVisibility(View.VISIBLE);
                ACRE_DISPLAY.setVisibility(View.INVISIBLE);
                ACRE_ALERT.setVisibility(View.INVISIBLE);

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(ACRE_EDIT_TEXT, InputMethodManager.SHOW_IMPLICIT);

            }
        });


        YIELD_EDIT_TEXT.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if ((!hasFocus) && (v.toString().length() > 0)) {
                    YIELD_DISPLAY.setText(YIELD_EDIT_TEXT.getText());
                    YIELD_EDIT_TEXT.setVisibility(View.INVISIBLE);
                    YIELD_DISPLAY.setVisibility(View.VISIBLE);
                    CORN.setYield(Integer.parseInt(YIELD_EDIT_TEXT.getText().toString()));
                    YIELD_ALERT.setVisibility(View.INVISIBLE);
                    Log.d("yield",YIELD_EDIT_TEXT.getText().toString());

                    //ACRE_DISPLAY.setVisibility(View.VISIBLE);
                    //ACRE_EDIT_TEXT.setVisibility(View.INVISIBLE);

                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(YIELD_EDIT_TEXT, InputMethodManager.SHOW_IMPLICIT);


                } else if ((!hasFocus) && (v.toString().length() == 0)) {
                    YIELD_DISPLAY.setText("Set a Value");
                    YIELD_EDIT_TEXT.setVisibility(View.INVISIBLE);
                    YIELD_DISPLAY.setVisibility(View.VISIBLE);
                    YIELD_ALERT.setVisibility(View.INVISIBLE);
                } else if (hasFocus) {
                    ACRE_DISPLAY.setVisibility(View.VISIBLE);
                    ACRE_EDIT_TEXT.setVisibility(View.INVISIBLE);
                }
            }
        });

        YIELD_EDIT_TEXT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                YIELD_DISPLAY.setText(YIELD_EDIT_TEXT.getText());
                YIELD_ALERT.setVisibility(View.INVISIBLE);

                ACRE_DISPLAY.setVisibility(View.VISIBLE);
                ACRE_EDIT_TEXT.setVisibility(View.INVISIBLE);


                CORN.setYield(Integer.parseInt(YIELD_EDIT_TEXT.getText().toString()));

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(YIELD_EDIT_TEXT, InputMethodManager.SHOW_IMPLICIT);

                Log.d("yield", YIELD_EDIT_TEXT.getText().toString());

            }
        });

        YIELD_EDIT_TEXT.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    YIELD_DISPLAY.setVisibility(View.VISIBLE);
                    YIELD_EDIT_TEXT.setVisibility(View.INVISIBLE);

                    CORN.setYield(Integer.parseInt(YIELD_EDIT_TEXT.getText().toString()));
                    Log.d("yield", YIELD_EDIT_TEXT.getText().toString());

                    return true;
                }
                return false;
            }

        });

        YIELD_DISPLAY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YIELD_EDIT_TEXT.setText(YIELD_DISPLAY.getText());
                YIELD_EDIT_TEXT.setVisibility(View.VISIBLE);
                YIELD_DISPLAY.setVisibility(View.INVISIBLE);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(YIELD_EDIT_TEXT, InputMethodManager.SHOW_IMPLICIT);
                YIELD_ALERT.setVisibility(View.INVISIBLE);
                Log.d("yield", YIELD_EDIT_TEXT.getText().toString());

            }
        });


        /**
         * this is to set the regional values when
         */

        if (CORN != null) {
            ACRE_DISPLAY.setText(CORN.getSizeAcresPlanted() + "");
            YIELD_DISPLAY.setText(CORN.getYield() + "");

            SEED_SET.setText(getCurrencyString(CORN.getSeed()));
            FERTILIZER_SET.setText(getCurrencyString(CORN.getFertilizer()));
            CHEMICALS_SET.setText(getCurrencyString(CORN.getChemicals()));
            CUSTOM_OPS_SET.setText(getCurrencyString(CORN.getCustomOps()));
            FUEL_LUBE_SET.setText(getCurrencyString(CORN.getFueLubeElec()));
            REPAIRS_SET.setText(getCurrencyString(CORN.getRepairs()));
            IRRIGATION_WATER_SET.setText(getCurrencyString(CORN.getIrrigationWater()));
            INT_ON_CAP_SET.setText(getCurrencyString(CORN.getIntOnCap()));
            HIRED_LABOR_SET.setText(getCurrencyString(CORN.getHiredLabor()));
            UNPAID_LABOR_SET.setText(getCurrencyString(CORN.getCostUnpaidLabor()));
            RECOVERY_EQUIP_SET.setText(getCurrencyString(CORN.getCapRecoveryOfEquip()));
            LAND_RENTAL_RATE_SET.setText(getCurrencyString(CORN.getCostOfLandRentalRate()));
            TAX_INSURANCE_SET.setText(getCurrencyString(CORN.getTaxesInsurance()));
            MISC_SET.setText(getCurrencyString(CORN.getMiscellaneous()));
            TOTAL_COST_VIEW.setText(getCurrencyString(CORN.getTotalCost()));

        }





        COST_REFRESH_BUTTON.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (COST_TOGGLE_BUTTON.isChecked()) {

                    TOTAL_COST_VIEW.setText(getCurrencyString(CORN.getTotalCost() * CORN.getSizeAcresPlanted()));

                } else if (!(COST_TOGGLE_BUTTON.isChecked())) {

                    TOTAL_COST_VIEW.setText(getCurrencyString(CORN.getTotalCost()));

                }
            }
        });


        /**
         * the next section of ontextchange listeners are to give info regarding each input
         * and to update the total cost
         *
         */

        SEED_SET.addTextChangedListener(new TextWatcher() {

            CharSequence string;
            float value;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {


                String text = s.subSequence(0,s.length()).toString();
                if ((s.length() == 0)||(text.equalsIgnoreCase("$"))) {
                    SEED_SET.setHint("Enter Value");

                } else if ((COST_TOGGLE_BUTTON.isChecked())) {

                    string = s.subSequence(1, s.length());
                    value = Float.valueOf(string.toString());
                    CORN.setSeed(value / CORN.getSizeAcresPlanted());


                } else if (!(COST_TOGGLE_BUTTON.isChecked())) {

                    /**
                     * what to do here
                     */

                    string = s.subSequence(1, s.length());
                    value = Float.valueOf(string.toString());
                    CORN.setSeed(value);

                }


            }
        });

        final TextView SEED_TEXT_VIEW = (TextView) findViewById(R.id.customSeedText);

        SEED_TEXT_VIEW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String seed = "The cost of corn seed when it was bought and the associated costs of getting seed ";


                final AlertDialog alert = new AlertDialog.Builder(CustomCornCost.this).create();
                alert.setTitle("Seed");
                alert.setMessage(seed);
                alert.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alert.dismiss();
                    }
                });

                alert.show();


            }
        });


        FERTILIZER_SET.addTextChangedListener(new TextWatcher() {

            CharSequence string;
            float value;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                String text = s.subSequence(0,s.length()).toString();
                if ((s.length() == 0)||(text.equalsIgnoreCase("$"))) {
                    FERTILIZER_SET.setHint("Enter Value");

                } else if (COST_TOGGLE_BUTTON.isChecked()) {

                    string = s.subSequence(1, s.length());
                    value = Float.valueOf(string.toString());
                    CORN.setFertilizer(value / CORN.getSizeAcresPlanted());


                } else if (!(COST_TOGGLE_BUTTON.isChecked())) {

                    /**
                     * what to do here
                     */

                    string = s.subSequence(1, s.length());
                    value = Float.valueOf(string.toString());
                    CORN.setFertilizer(value);

                }


            }
        });

        CHEMICALS_SET.addTextChangedListener(new TextWatcher() {

            CharSequence string;
            float value;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                String text = s.subSequence(0,s.length()).toString();
                if ((s.length() == 0)||(text.equalsIgnoreCase("$"))) {
                    CHEMICALS_SET.setHint("Enter Value");

                } else if (COST_TOGGLE_BUTTON.isChecked()) {

                    string = s.subSequence(1, s.length());
                    value = Float.valueOf(string.toString());
                    CORN.setChemicals(value / CORN.getSizeAcresPlanted());

                    Log.d("cost is checked", CORN.getTotalCost() + "");


                } else if (!(COST_TOGGLE_BUTTON.isChecked())) {

                    /**
                     * what to do here
                     */

                    string = s.subSequence(1, s.length());
                    value = Float.valueOf(string.toString());
                    CORN.setChemicals(value);


                    Log.d("cost is not checked", CORN.getTotalCost() + "");

                }


            }
        });

        CUSTOM_OPS_SET.addTextChangedListener(new TextWatcher() {

            CharSequence string;
            float value;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                String text = s.subSequence(0,s.length()).toString();
                if ((s.length() == 0)||(text.equalsIgnoreCase("$"))) {
                    CUSTOM_OPS_SET.setHint("Enter Value");

                } else if (COST_TOGGLE_BUTTON.isChecked()) {

                    string = s.subSequence(1, s.length());
                    value = Float.valueOf(string.toString());
                    CORN.setCustomOps(value / CORN.getSizeAcresPlanted());


                } else if (!(COST_TOGGLE_BUTTON.isChecked())) {

                    /**
                     * what to do here
                     */

                    string = s.subSequence(1, s.length());
                    value = Float.valueOf(string.toString());
                    CORN.setCustomOps(value);

                }


            }
        });

        FUEL_LUBE_SET.addTextChangedListener(new TextWatcher() {

            CharSequence string;
            float value;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                String text = s.subSequence(0,s.length()).toString();
                if ((s.length() == 0)||(text.equalsIgnoreCase("$"))) {
                    FUEL_LUBE_SET.setHint("Enter Value");

                } else if (COST_TOGGLE_BUTTON.isChecked()) {

                    string = s.subSequence(1, s.length());
                    value = Float.valueOf(string.toString());
                    CORN.setFueLubeElec(value / CORN.getSizeAcresPlanted());


                } else if (!(COST_TOGGLE_BUTTON.isChecked())) {

                    /**
                     * what to do here
                     */

                    string = s.subSequence(1, s.length());
                    value = Float.valueOf(string.toString());
                    CORN.setFueLubeElec(value);

                }


            }
        });

        REPAIRS_SET.addTextChangedListener(new TextWatcher() {

            CharSequence string;
            float value;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                String text = s.subSequence(0,s.length()).toString();
                if ((s.length() == 0)||(text.equalsIgnoreCase("$"))) {
                    REPAIRS_SET.setHint("Enter Value");

                } else if (COST_TOGGLE_BUTTON.isChecked()) {

                    string = s.subSequence(1, s.length());
                    value = Float.valueOf(string.toString());
                    CORN.setRepairs(value / CORN.getSizeAcresPlanted());


                } else if (!(COST_TOGGLE_BUTTON.isChecked())) {

                    /**
                     * what to do here
                     */

                    string = s.subSequence(1, s.length());
                    value = Float.valueOf(string.toString());
                    CORN.setRepairs(value);

                }


            }
        });

        IRRIGATION_WATER_SET.addTextChangedListener(new TextWatcher() {

            CharSequence string;
            float value;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                String text = s.subSequence(0,s.length()).toString();
                if ((s.length() == 0)||(text.equalsIgnoreCase("$"))) {
                    IRRIGATION_WATER_SET.setHint("Enter Value");

                } else if (COST_TOGGLE_BUTTON.isChecked()) {

                    string = s.subSequence(1, s.length());
                    value = Float.valueOf(string.toString());
                    CORN.setIrrigationWater(value / CORN.getSizeAcresPlanted());


                } else if (!(COST_TOGGLE_BUTTON.isChecked())) {

                    /**
                     * what to do here
                     */

                    string = s.subSequence(1, s.length());
                    value = Float.valueOf(string.toString());
                    CORN.setIrrigationWater(value);

                }


            }
        });

        INT_ON_CAP_SET.addTextChangedListener(new TextWatcher() {

            CharSequence string;
            float value;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                String text = s.subSequence(0,s.length()).toString();
                if ((s.length() == 0)||(text.equalsIgnoreCase("$"))) {
                    INT_ON_CAP_SET.setHint("Enter Value");

                } else if (COST_TOGGLE_BUTTON.isChecked()) {

                    string = s.subSequence(1, s.length());
                    value = Float.valueOf(string.toString());
                    CORN.setIntOnCap(value / CORN.getSizeAcresPlanted());


                } else if (!(COST_TOGGLE_BUTTON.isChecked())) {

                    /**
                     * what to do here
                     */

                    string = s.subSequence(1, s.length());
                    value = Float.valueOf(string.toString());
                    CORN.setIntOnCap(value);

                }


            }
        });

        HIRED_LABOR_SET.addTextChangedListener(new TextWatcher() {

            CharSequence string;
            float value;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                String text = s.subSequence(0,s.length()).toString();
                if ((s.length() == 0)||(text.equalsIgnoreCase("$"))) {
                    HIRED_LABOR_SET.setHint("Enter Value");

                } else if (COST_TOGGLE_BUTTON.isChecked()) {

                    string = s.subSequence(1, s.length());
                    value = Float.valueOf(string.toString());
                    CORN.setHiredLabor(value / CORN.getSizeAcresPlanted());


                } else if (!(COST_TOGGLE_BUTTON.isChecked())) {

                    /**
                     * what to do here
                     */

                    string = s.subSequence(1, s.length());
                    value = Float.valueOf(string.toString());
                    CORN.setHiredLabor(value);

                }


            }
        });

        UNPAID_LABOR_SET.addTextChangedListener(new TextWatcher() {

            CharSequence string;
            float value;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                String text = s.subSequence(0,s.length()).toString();
                if ((s.length() == 0)||(text.equalsIgnoreCase("$"))) {
                    UNPAID_LABOR_SET.setHint("Enter Value");

                } else if (COST_TOGGLE_BUTTON.isChecked()) {

                    string = s.subSequence(1, s.length());
                    value = Float.valueOf(string.toString());
                    CORN.setCostUnpaidLabor(value / CORN.getSizeAcresPlanted());


                } else if (!(COST_TOGGLE_BUTTON.isChecked())) {

                    /**
                     * what to do here
                     */

                    string = s.subSequence(1, s.length());
                    value = Float.valueOf(string.toString());
                    CORN.setCostUnpaidLabor(value);

                }


            }
        });

        RECOVERY_EQUIP_SET.addTextChangedListener(new TextWatcher() {

            CharSequence string;
            float value;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                String text = s.subSequence(0,s.length()).toString();
                if ((s.length() == 0)||(text.equalsIgnoreCase("$"))) {
                    RECOVERY_EQUIP_SET.setHint("Enter Value");

                } else if (COST_TOGGLE_BUTTON.isChecked()) {

                    string = s.subSequence(1, s.length());
                    value = Float.valueOf(string.toString());
                    CORN.setCapRecoveryOfEquip(value / CORN.getSizeAcresPlanted());


                } else if (!(COST_TOGGLE_BUTTON.isChecked())) {

                    /**
                     * what to do here
                     */

                    string = s.subSequence(1, s.length());
                    value = Float.valueOf(string.toString());
                    CORN.setCapRecoveryOfEquip(value);

                }


            }
        });

        LAND_RENTAL_RATE_SET.addTextChangedListener(new TextWatcher() {

            CharSequence string;
            float value;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                String text = s.subSequence(0,s.length()).toString();
                if ((s.length() == 0)||(text.equalsIgnoreCase("$"))) {
                    LAND_RENTAL_RATE_SET.setHint("Enter Value");

                } else if (COST_TOGGLE_BUTTON.isChecked()) {

                    string = s.subSequence(1, s.length());
                    value = Float.valueOf(string.toString());
                    CORN.setCostOfLandRentalRate(value / CORN.getSizeAcresPlanted());


                } else if (!(COST_TOGGLE_BUTTON.isChecked())) {

                    /**
                     * what to do here
                     */

                    string = s.subSequence(1, s.length());
                    value = Float.valueOf(string.toString());
                    CORN.setCostOfLandRentalRate(value);

                }


            }
        });

        TAX_INSURANCE_SET.addTextChangedListener(new TextWatcher() {

            CharSequence string;
            float value;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                String text = s.subSequence(0,s.length()).toString();
                if ((s.length() == 0)||(text.equalsIgnoreCase("$"))) {
                    TAX_INSURANCE_SET.setHint("Enter Value");

                } else if (COST_TOGGLE_BUTTON.isChecked()) {

                    string = s.subSequence(1, s.length());
                    value = Float.valueOf(string.toString());
                    CORN.setTaxesInsurance(value / CORN.getSizeAcresPlanted());


                } else if (!(COST_TOGGLE_BUTTON.isChecked())) {

                    /**
                     * what to do here
                     */

                    string = s.subSequence(1, s.length());
                    value = Float.valueOf(string.toString());
                    CORN.setTaxesInsurance(value);

                }


            }
        });



        MISC_SET.addTextChangedListener(new TextWatcher() {

            CharSequence string;
            float value;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                String text = s.subSequence(0,s.length()).toString();
                if ((s.length() == 0)||(text.equalsIgnoreCase("$"))) {
                    MISC_SET.setHint("Enter Value");

                } else if (COST_TOGGLE_BUTTON.isChecked()) {

                    string = s.subSequence(1, s.length());
                    value = Float.valueOf(string.toString());
                    CORN.setMiscellaneous(value / CORN.getSizeAcresPlanted());


                } else if (!(COST_TOGGLE_BUTTON.isChecked())) {

                    /**
                     * what to do here
                     */

                    string = s.subSequence(1, s.length());
                    value = Float.valueOf(string.toString());
                    CORN.setMiscellaneous(value);

                }


            }
        });

        COST_TOGGLE_BUTTON.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean on = COST_TOGGLE_BUTTON.isChecked();

                // show total cost of each per acre
                if (on) {

                    SEED_SET.setText(getCurrencyString(CORN.getSeed() * CORN.getSizeAcresPlanted()));
                    FERTILIZER_SET.setText(getCurrencyString(CORN.getFertilizer() * CORN.getSizeAcresPlanted()));
                    CHEMICALS_SET.setText(getCurrencyString(CORN.getChemicals() * CORN.getSizeAcresPlanted()));
                    CUSTOM_OPS_SET.setText(getCurrencyString(CORN.getCustomOps() * CORN.getSizeAcresPlanted()));
                    FUEL_LUBE_SET.setText(getCurrencyString(CORN.getFueLubeElec() * CORN.getSizeAcresPlanted()));
                    REPAIRS_SET.setText(getCurrencyString(CORN.getRepairs() * CORN.getSizeAcresPlanted()));
                    IRRIGATION_WATER_SET.setText(getCurrencyString(CORN.getIrrigationWater() * CORN.getSizeAcresPlanted()));
                    INT_ON_CAP_SET.setText(getCurrencyString(CORN.getIntOnCap() * CORN.getSizeAcresPlanted()));
                    HIRED_LABOR_SET.setText(getCurrencyString(CORN.getHiredLabor() * CORN.getSizeAcresPlanted()));
                    UNPAID_LABOR_SET.setText(getCurrencyString(CORN.getCostUnpaidLabor() * CORN.getSizeAcresPlanted()));
                    RECOVERY_EQUIP_SET.setText(getCurrencyString(CORN.getCapRecoveryOfEquip() * CORN.getSizeAcresPlanted()));
                    LAND_RENTAL_RATE_SET.setText(getCurrencyString(CORN.getCostOfLandRentalRate() * CORN.getSizeAcresPlanted()));
                    TAX_INSURANCE_SET.setText(getCurrencyString(CORN.getTaxesInsurance() * CORN.getSizeAcresPlanted()));
                    MISC_SET.setText(getCurrencyString(CORN.getMiscellaneous() * CORN.getSizeAcresPlanted()));
                    TOTAL_COST_VIEW.setText(getCurrencyString(CORN.getTotalCost() * CORN.getSizeAcresPlanted()));


                } else if (!on) {

                    SEED_SET.setText(getCurrencyString(CORN.getSeed()));
                    FERTILIZER_SET.setText(getCurrencyString(CORN.getFertilizer()));
                    CHEMICALS_SET.setText(getCurrencyString(CORN.getChemicals()));
                    CUSTOM_OPS_SET.setText(getCurrencyString(CORN.getCustomOps()));
                    FUEL_LUBE_SET.setText(getCurrencyString(CORN.getFueLubeElec()));
                    REPAIRS_SET.setText(getCurrencyString(CORN.getRepairs()));
                    IRRIGATION_WATER_SET.setText(getCurrencyString(CORN.getIrrigationWater()));
                    INT_ON_CAP_SET.setText(getCurrencyString(CORN.getIntOnCap()));
                    HIRED_LABOR_SET.setText(getCurrencyString(CORN.getHiredLabor()));
                    UNPAID_LABOR_SET.setText(getCurrencyString(CORN.getCostUnpaidLabor()));
                    RECOVERY_EQUIP_SET.setText(getCurrencyString(CORN.getCapRecoveryOfEquip()));
                    LAND_RENTAL_RATE_SET.setText(getCurrencyString(CORN.getCostOfLandRentalRate()));
                    TAX_INSURANCE_SET.setText(getCurrencyString(CORN.getTaxesInsurance()));
                    MISC_SET.setText(getCurrencyString(CORN.getMiscellaneous()));
                    TOTAL_COST_VIEW.setText(getCurrencyString(CORN.getTotalCost()));

                }
            }
        });

        final Button SAVE_CROP = (Button) findViewById(R.id.customSaveState);
        final Button PREV_SAVED_CROP = (Button) findViewById(R.id.customPrevSavedStats);

        SAVE_CROP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog builder = new AlertDialog.Builder(CustomCornCost.this).create();
                final EditText SAVE_CROP_EDIT_TEXT = new EditText(CustomCornCost.this);

                builder.setTitle("Save Corn Inputs");
                SAVE_CROP_EDIT_TEXT.setHint("name your values");
                builder.setView(SAVE_CROP_EDIT_TEXT);
                builder.setButton(AlertDialog.BUTTON_POSITIVE, "Save Crop Values", new DialogInterface.OnClickListener() {


                    //TODO: set hints on all of the edittexts, dont set text USE HINTS!!!!!!!!!!!!!!!!!!!!!


                    /**
                     * if the positive button is pressed, then we save the values of the corn into sqlite
                     *
                     * @param dialog
                     * @param which
                     */
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        DatabaseStats dbStats = DatabaseStats.getInstance(getApplicationContext());

                        String title = SAVE_CROP_EDIT_TEXT.getText().toString();
                        Log.d("title", title);

                        if ((title.equalsIgnoreCase("")) || (title.equalsIgnoreCase("name your values"))) {
                            String msg = "You must enter a name in order to save the data";
                            Toast.makeText(CustomCornCost.this, msg, Toast.LENGTH_SHORT).show();
                            SAVE_CROP.performClick();
                        } else {
                            CORN.setTitle(title);
                            dbStats.addCropStats(CORN);
                        }
                        Log.d("title", title);
                        Log.d("countOfDbStats", "" + dbStats.getCropStatsCount());
                        Toast.makeText(CustomCornCost.this,"Saved. Displaying regional data now.",Toast.LENGTH_LONG);
                        setRegionalAverageValues(CORN.getCrop(), CORN.getRegion());

                    }
                    private void setRegionalAverageValues(int crop, int region) {

                        CropStats cropStat = new CropStats(crop,region);

                        SEED_SET.setText(getCurrencyString(cropStat.getSeed()));
                        FERTILIZER_SET.setText(getCurrencyString(cropStat.getFertilizer()));
                        CHEMICALS_SET.setText(getCurrencyString(cropStat.getChemicals()));
                        CUSTOM_OPS_SET.setText(getCurrencyString(cropStat.getCustomOps()));
                        FUEL_LUBE_SET.setText(getCurrencyString(cropStat.getFueLubeElec()));
                        REPAIRS_SET.setText(getCurrencyString(cropStat.getRepairs()));
                        IRRIGATION_WATER_SET.setText(getCurrencyString(cropStat.getIrrigationWater()));
                        INT_ON_CAP_SET.setText(getCurrencyString(cropStat.getIntOnCap()));
                        HIRED_LABOR_SET.setText(getCurrencyString(cropStat.getHiredLabor()));
                        UNPAID_LABOR_SET.setText(getCurrencyString(cropStat.getCostUnpaidLabor()));
                        RECOVERY_EQUIP_SET.setText(getCurrencyString(cropStat.getCapRecoveryOfEquip()));
                        LAND_RENTAL_RATE_SET.setText(getCurrencyString(cropStat.getCostOfLandRentalRate()));
                        TAX_INSURANCE_SET.setText(getCurrencyString(cropStat.getTaxesInsurance()));
                        TOTAL_COST_VIEW.setText(getCurrencyString(cropStat.getTotalCost()));




                    }
                });
                builder.setButton(AlertDialog.BUTTON_NEGATIVE, "OverWrite", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        final DatabaseStats dbStats = DatabaseStats.getInstance(getApplicationContext());
                        final ArrayList<CropStats> arrayCrop = dbStats.getCropRegionCropStats(CORN.getCropName(), CORN.getRegionName(), DB_STATS);
                        final String[] array = new String[arrayCrop.size()];
                        //dbStats.close();

                        for (int i = 0; i < arrayCrop.size(); i++) {

                            array[i] = arrayCrop.get(i).getTitle();

                        }


                        final AlertDialog.Builder SPINNER_DIALOG = new AlertDialog.Builder(CustomCornCost.this);
                        SPINNER_DIALOG.setTitle("Choose a crop");

                        if (arrayCrop.size() == 0) {
                            SPINNER_DIALOG.setMessage("No saved custom stats. Add custom values, and save them");
                            SPINNER_DIALOG.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    dialog.dismiss();
                                }
                            });
                            SPINNER_DIALOG.create();
                            SPINNER_DIALOG.show();
                        } else {

                            for (int i = 0; i < arrayCrop.size(); i++) {

                                array[i] = arrayCrop.get(i).getTitle();

                            }

                            Spinner spinner = new Spinner(CustomCornCost.this);
                            ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(CustomCornCost.this, android.R.layout.simple_spinner_item, array);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner.setAdapter(adapter);


                            SPINNER_DIALOG.setAdapter(adapter, new DialogInterface.OnClickListener() {
                                @Override

                                //TODO:
                                // use which to set data accumulation
                                public void onClick(DialogInterface dialog, int which) {

                                    dbStats.updateCrop(CORN,arrayCrop.get(which).getTitle());
                                    Toast.makeText(CustomCornCost.this, "Saved Data, values are now regional standard", Toast.LENGTH_LONG).show();
                                    setRegionalAverageValues(CORN.getCrop(),CORN.getRegion());



                                }

                                private void setRegionalAverageValues(int crop, int region) {

                                    CropStats cropStat = new CropStats(crop,region);

                                    SEED_SET.setText(getCurrencyString(cropStat.getSeed()));
                                    FERTILIZER_SET.setText(getCurrencyString(cropStat.getFertilizer()));
                                    CHEMICALS_SET.setText(getCurrencyString(cropStat.getChemicals()));
                                    CUSTOM_OPS_SET.setText(getCurrencyString(cropStat.getCustomOps()));
                                    FUEL_LUBE_SET.setText(getCurrencyString(cropStat.getFueLubeElec()));
                                    REPAIRS_SET.setText(getCurrencyString(cropStat.getRepairs()));
                                    IRRIGATION_WATER_SET.setText(getCurrencyString(cropStat.getIrrigationWater()));
                                    INT_ON_CAP_SET.setText(getCurrencyString(cropStat.getIntOnCap()));
                                    HIRED_LABOR_SET.setText(getCurrencyString(cropStat.getHiredLabor()));
                                    UNPAID_LABOR_SET.setText(getCurrencyString(cropStat.getCostUnpaidLabor()));
                                    RECOVERY_EQUIP_SET.setText(getCurrencyString(cropStat.getCapRecoveryOfEquip()));
                                    LAND_RENTAL_RATE_SET.setText(getCurrencyString(cropStat.getCostOfLandRentalRate()));
                                    TAX_INSURANCE_SET.setText(getCurrencyString(cropStat.getTaxesInsurance()));
                                    TOTAL_COST_VIEW.setText(getCurrencyString(cropStat.getTotalCost()));




                                }
                            });
                            SPINNER_DIALOG.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            SPINNER_DIALOG.create();
                            SPINNER_DIALOG.show();




                        }



                    }
                });
                builder.show();


            }
        });

        PREV_SAVED_CROP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog builder = new AlertDialog.Builder(CustomCornCost.this).create();
                builder.setTitle("Edit Corn Values");
                builder.setMessage("You can choose to get saved data or delete entries");
                builder.setButton(AlertDialog.BUTTON_POSITIVE, "Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        DatabaseStats dbStats = DatabaseStats.getInstance(getApplicationContext());
                        Log.d("ccc crop", CORN.getCropName());
                        final ArrayList<CropStats> arrayCrop = dbStats.getCropRegionCropStats(CORN.getCropName(), CORN.getRegionName(), DB_STATS);
                        String[] array = new String[arrayCrop.size()];
                        //dbStats.close();

                        for (int i = 0; i < arrayCrop.size(); i++) {

                            array[i] = arrayCrop.get(i).getTitle();

                        }


                        final AlertDialog.Builder SPINNER_DIALOG = new AlertDialog.Builder(CustomCornCost.this);
                        SPINNER_DIALOG.setTitle("Choose a crop");

                        if (arrayCrop.size() == 0) {
                            SPINNER_DIALOG.setMessage("No saved custom stats. Add custom values, and save them");
                            SPINNER_DIALOG.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    dialog.dismiss();
                                }
                            });
                            SPINNER_DIALOG.create();
                            SPINNER_DIALOG.show();
                        } else {

                            for (int i = 0; i < arrayCrop.size(); i++) {

                                array[i] = arrayCrop.get(i).getTitle();

                            }

                            Spinner spinner = new Spinner(CustomCornCost.this);
                            ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(CustomCornCost.this, android.R.layout.simple_spinner_item, array);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner.setAdapter(adapter);


                            SPINNER_DIALOG.setAdapter(adapter, new DialogInterface.OnClickListener() {
                                @Override

                                //TODO: 
                                // use which to set data accumulation
                                public void onClick(DialogInterface dialog, int which) {

                                    displayValues(arrayCrop.get(which));

                                }
                            });
                            SPINNER_DIALOG.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            SPINNER_DIALOG.create();
                            SPINNER_DIALOG.show();


                        }
                    }


                });

                // this button should pop up another dialog that shows a checkbox of what should be deleted
                builder.setButton(AlertDialog.BUTTON_NEUTRAL, "Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        AlertDialog.Builder dialogInner = new AlertDialog.Builder(CustomCornCost.this);
                        dialogInner.setTitle("Choose which crops to delete");
                        DatabaseStats dbStats = DatabaseStats.getInstance(getApplicationContext());
                        final ArrayList<CropStats> CROP_DB_DELETE = dbStats.getCropRegionCropStats(CORN.getCropName(), CORN.getRegionName(), DB_STATS);
                        Log.d("crop size", "" + CROP_DB_DELETE.size());


                        String[] checkBoxes = new String[CROP_DB_DELETE.size()];
                        for (int i = 0; i < CROP_DB_DELETE.size(); i++) {
                            checkBoxes[i] = CROP_DB_DELETE.get(i).getTitle();

                        }

                        //itemsSelected is used when a button is clicked
                        final ArrayList itemsSelected = new ArrayList();

                        if (CROP_DB_DELETE.size() == 0) {
                            dialogInner.setMessage("Set some crop data in order to be able to delete it");
                            dialogInner.setNeutralButton("Okay", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    //Your logic when OK button is clicked
                                    dialog.dismiss();
                                }
                            });

                        } else {

                            dialogInner.setMultiChoiceItems(checkBoxes, null, new DialogInterface.OnMultiChoiceClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int selectedItemId,
                                                    boolean isSelected) {
                                    if (isSelected) {
                                        itemsSelected.add(selectedItemId);
                                    } else if (itemsSelected.contains(selectedItemId)) {
                                        itemsSelected.remove(Integer.valueOf(selectedItemId));
                                    }
                                }
                            })
                                    .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int id) {
                                            if (itemsSelected.size() > 0) {
                                                for (int i = 0; i < itemsSelected.size(); i++) {

                                                    DatabaseStats dbStats = DatabaseStats.getInstance(getApplicationContext());
                                                    dbStats.deleteCropStat(CROP_DB_DELETE.get(i));

                                                }
                                            } else {
                                                Toast.makeText(CustomCornCost.this, "Choose crop stats to delete", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    })
                                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int id) {
                                        }
                                    });


                        }
                        dialogInner.create();
                        dialogInner.show();

                    }
                });

                builder.show();
            }

            private void displayValues(CropStats cropStats) {

                ACRE_DISPLAY.setText(cropStats.getSizeAcresPlanted()+"");
                YIELD_DISPLAY.setText(cropStats.getYield()+"");

                SEED_SET.setText(getCurrencyString(cropStats.getSeed()));
                FERTILIZER_SET.setText(getCurrencyString(cropStats.getFertilizer()));
                CHEMICALS_SET.setText(getCurrencyString(cropStats.getChemicals()));
                CUSTOM_OPS_SET.setText(getCurrencyString(cropStats.getCustomOps()));
                FUEL_LUBE_SET.setText(getCurrencyString(cropStats.getFueLubeElec()));
                REPAIRS_SET.setText(getCurrencyString(cropStats.getRepairs()));
                IRRIGATION_WATER_SET.setText(getCurrencyString(cropStats.getIrrigationWater()));
                INT_ON_CAP_SET.setText(getCurrencyString(cropStats.getIntOnCap()));
                HIRED_LABOR_SET.setText(getCurrencyString(cropStats.getHiredLabor()));
                UNPAID_LABOR_SET.setText(getCurrencyString(cropStats.getCostUnpaidLabor()));
                RECOVERY_EQUIP_SET.setText(getCurrencyString(cropStats.getCapRecoveryOfEquip()));
                LAND_RENTAL_RATE_SET.setText(getCurrencyString(cropStats.getCostOfLandRentalRate()));
                TAX_INSURANCE_SET.setText(getCurrencyString(cropStats.getTaxesInsurance()));
                TOTAL_COST_VIEW.setText(getCurrencyString(cropStats.getTotalCost()));

                TextView currentCrop = (TextView) findViewById(R.id.customCurrentCrop);
                currentCrop.setText("currently viewing: " + cropStats.getTitle());


            }

        });


        /**
         * this is the method that leads to go back to the model
         */

        final Button ARRAY_CROP = (Button) findViewById(R.id.customArrayCrop);
        ARRAY_CROP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(CustomCornCost.this);
                dialog.setTitle("View crop data on the model");
                //dialog.setMessage("Choose as many of the data sets as you'd like");

                DatabaseStats dbStats = new DatabaseStats(getApplicationContext());
                final ArrayList<CropStats> CROPS_ARRAY = dbStats.getCropRegionCropStats(CORN.getCropName(), CORN.getRegionName(), DB_STATS);
                Log.d("crop size", "" + CROPS_ARRAY.size());

                String[] checkBoxes = new String[CROPS_ARRAY.size()];

                for (int i = 0; i < CROPS_ARRAY.size(); i++) {
                    checkBoxes[i] = CROPS_ARRAY.get(i).getTitle();

                }

                final ArrayList<Integer> itemsSelected = new ArrayList();

                if (CROPS_ARRAY.size() == 0) {
                    dialog.setMessage("Set some crop data in order to show it on the model");
                    dialog.setNeutralButton("Okay", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            //Your logic when OK button is clicked
                            dialog.dismiss();
                        }
                    });

                } else {

                    dialog.setMultiChoiceItems(checkBoxes, null, new DialogInterface.OnMultiChoiceClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int selectedItemId,
                                            boolean isSelected) {
                            if (isSelected) {
                                itemsSelected.add(selectedItemId);
                                Log.d("items", "" + selectedItemId);
                            } else if (itemsSelected.contains(selectedItemId)) {
                                itemsSelected.remove(Integer.valueOf(selectedItemId));
                            }
                        }
                    })
                            .setPositiveButton("Show", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {

                                    ArrayList<CropStats> modelCrops = new ArrayList<>();
                                    modelCrops.add(new CropStats(CORN.getCrop(), CORN.getRegion()));
                                    for (int i = 0; i < itemsSelected.size(); i++) {
                                        modelCrops.add(CROPS_ARRAY.get(itemsSelected.get(i)));
                                    }
                                    Log.d("array add to model", "" + modelCrops.size());

                                    Intent intent = new Intent(CustomCornCost.this, CornProfit.class);
                                    intent.putParcelableArrayListExtra("crops", modelCrops);
                                    final ProgressDialog RENDER = ProgressDialog.show(CustomCornCost.this, "", "Rendering...", true);
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

                                    startActivity(intent);


                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                }
                            });

                }
                dialog.create();
                dialog.show();


            }
        });


    }




    //TODO: create a boolean array/arraylist to gather which checkboxes the person wants to save
    //TODO: basically just need an array 15 values deep to have all the boolean values for
    //TODO: the checkboxes



    /**
     * this method is used to keep the dollar sign when changing and adding
     * currency to an edit text
     */

    private String getCurrencyString(float num) {

        String builder = String.format("$%.2f", num);
        return builder;
    }

}






