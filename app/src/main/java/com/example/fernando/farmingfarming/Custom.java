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

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by fernando on 10/4/15.
 */
public class Custom extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_custom);

        setTitle("Customize Variables");

        //Create the SQLite db for first time users
        final SQLiteDatabase DB_STATS = openOrCreateDatabase("stats", MODE_PRIVATE, null);


        Bundle bundle = getIntent().getExtras();

        // get the extras from the previous intent
        final ArrayList<CropStats> cropStatsArrayList = bundle.getParcelableArrayList("crops");
        final CropStats CROP = cropStatsArrayList.get(0);

        setTitle("Customize " + CROP.getRegionName() + " " + CROP.getCropName());

        // get the data for the region as added in the previous activity (welcome class)
        RegionData regionData = new RegionData(CROP.getRegion());

        // get the statistics for the region


        final TextView ACRE_ALERT = (TextView) findViewById(R.id.customAcresAlert);
        final TextView YIELD_ALERT = (TextView) findViewById(R.id.customYieldAlert);
        ACRE_ALERT.setTextColor(Color.RED);
        YIELD_ALERT.setTextColor(Color.RED);


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
                    CROP.setSizeAcresPlanted(Integer.parseInt(ACRE_EDIT_TEXT.getText().toString()));
                    ACRE_ALERT.setVisibility(View.INVISIBLE);

                    Log.d("acres", ACRE_EDIT_TEXT.getText().toString());

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
                    CROP.setSizeAcresPlanted(Integer.parseInt(ACRE_EDIT_TEXT.getText().toString()));
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
                    CROP.setYield(Integer.parseInt(YIELD_EDIT_TEXT.getText().toString()));
                    YIELD_ALERT.setVisibility(View.INVISIBLE);
                    Log.d("yield", YIELD_EDIT_TEXT.getText().toString());

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


                CROP.setYield(Integer.parseInt(YIELD_EDIT_TEXT.getText().toString()));

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

                    CROP.setYield(Integer.parseInt(YIELD_EDIT_TEXT.getText().toString()));
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

        if (CROP != null) {
            ACRE_DISPLAY.setText(CROP.getSizeAcresPlanted() + "");
            YIELD_DISPLAY.setText(CROP.getYield() + "");

            SEED_SET.setText(getCurrencyString(CROP.getSeed()));
            FERTILIZER_SET.setText(getCurrencyString(CROP.getFertilizer()));
            CHEMICALS_SET.setText(getCurrencyString(CROP.getChemicals()));
            CUSTOM_OPS_SET.setText(getCurrencyString(CROP.getCustomOps()));
            FUEL_LUBE_SET.setText(getCurrencyString(CROP.getFueLubeElec()));
            REPAIRS_SET.setText(getCurrencyString(CROP.getRepairs()));
            IRRIGATION_WATER_SET.setText(getCurrencyString(CROP.getIrrigationWater()));
            INT_ON_CAP_SET.setText(getCurrencyString(CROP.getIntOnCap()));
            HIRED_LABOR_SET.setText(getCurrencyString(CROP.getHiredLabor()));
            UNPAID_LABOR_SET.setText(getCurrencyString(CROP.getCostUnpaidLabor()));
            RECOVERY_EQUIP_SET.setText(getCurrencyString(CROP.getCapRecoveryOfEquip()));
            LAND_RENTAL_RATE_SET.setText(getCurrencyString(CROP.getCostOfLandRentalRate()));
            TAX_INSURANCE_SET.setText(getCurrencyString(CROP.getTaxesInsurance()));
            MISC_SET.setText(getCurrencyString(CROP.getMiscellaneous()));
            TOTAL_COST_VIEW.setText(getCurrencyString(CROP.getTotalCost()));

        }


        TOTAL_COST_VIEW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                float valueTotal = CROP.getTotalCost() * CROP.getSizeAcresPlanted();
                float valueAcre = CROP.getTotalCost();
                NumberFormat nf = NumberFormat.getCurrencyInstance();


                if (COST_TOGGLE_BUTTON.isChecked()) {

                    String value = nf.format(valueTotal);

                    TOTAL_COST_VIEW.setText(value);

                } else if (!(COST_TOGGLE_BUTTON.isChecked())) {

                    String value = nf.format(valueAcre);

                    TOTAL_COST_VIEW.setText(value);

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

                // this is a nested else if conditional
                // the first level is just to differentiate between having
                // no inputs and having the toggle button pressed or not
                // the nested conditionals are to know the difference between having
                // kept the entered $ value or it having been deleted

                String text = s.subSequence(0, s.length()).toString();

                // if there is no input or the input is only % then nothing happens
                if ((s.length() == 0) || (text.equalsIgnoreCase("$"))) {
                    // DO NOTHING, JUST DONT LET ANYTHING ELSE HAPPEN


                }

                // if the toggle button is pressed, then the values need to be presented as total cost
                // this means they need to be presented as (value * size of farm)
                else if (COST_TOGGLE_BUTTON.isChecked()) {

                    // if the $ is present, then proceed to add the $ when adding values
                    if (text.substring(0, 1).equalsIgnoreCase("$")) {

                        // if delete $ token then make sure not to add
                        string = s.subSequence(1, s.length());
                        value = Float.valueOf(string.toString());
                        CROP.setSeed(value / CROP.getSizeAcresPlanted());


                    }

                    // if no $ is present, then when the values are entered, set the text with the currency
                    else {
                        string = s.subSequence(0, s.length());
                        value = Float.valueOf(string.toString());
                        CROP.setSeed(value / CROP.getSizeAcresPlanted());
                        SEED_SET.setText(getCurrencyString(CROP.getSeed() * CROP.getSizeAcresPlanted()));

                    }

                }

                // if the toggle button is not pressed, then the values will show up as the per acre values
                // and will be as they were saved
                else if (!(COST_TOGGLE_BUTTON.isChecked())) {

                    // if the $ is present, then proceed to add the $ when adding values
                    if (text.substring(0, 1).equalsIgnoreCase("$")) {

                        // if delete $ token then make sure not to add
                        string = s.subSequence(1, s.length());
                        value = Float.valueOf(string.toString());
                        CROP.setSeed(value);


                    }

                    // if no $ is present, then when the values are entered, set the text with the currency
                    else {
                        string = s.subSequence(0, s.length());
                        value = Float.valueOf(string.toString());
                        CROP.setSeed(value);
                        SEED_SET.setText(getCurrencyString(CROP.getSeed()));

                    }

                }

                // update the total cost, whether per acre or total
                TOTAL_COST_VIEW.performClick();

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

                // this is a nested else if conditional
                // the first level is just to differentiate between having
                // no inputs and having the toggle button pressed or not
                // the nested conditionals are to know the difference between having
                // kept the entered $ value or it having been deleted

                String text = s.subSequence(0, s.length()).toString();

                // if there is no input or the input is only % then nothing happens
                if ((s.length() == 0) || (text.equalsIgnoreCase("$"))) {
                    // DO NOTHING, JUST DONT LET ANYTHING ELSE HAPPEN


                }

                // if the toggle button is pressed, then the values need to be presented as total cost
                // this means they need to be presented as (value * size of farm)
                else if (COST_TOGGLE_BUTTON.isChecked()) {

                    // if the $ is present, then proceed to add the $ when adding values
                    if (text.substring(0, 1).equalsIgnoreCase("$")) {

                        // if delete $ token then make sure not to add
                        string = s.subSequence(1, s.length());
                        value = Float.valueOf(string.toString());
                        CROP.setFertilizer(value / CROP.getSizeAcresPlanted());


                    }

                    // if no $ is present, then when the values are entered, set the text with the currency
                    else {
                        string = s.subSequence(0, s.length());
                        value = Float.valueOf(string.toString());
                        CROP.setIntOnCap(value / CROP.getSizeAcresPlanted());
                        FERTILIZER_SET.setText(getCurrencyString(CROP.getFertilizer() * CROP.getSizeAcresPlanted()));

                    }

                }

                // if the toggle button is not pressed, then the values will show up as the per acre values
                // and will be as they were saved
                else if (!(COST_TOGGLE_BUTTON.isChecked())) {

                    // if the $ is present, then proceed to add the $ when adding values
                    if (text.substring(0, 1).equalsIgnoreCase("$")) {

                        // if delete $ token then make sure not to add
                        string = s.subSequence(1, s.length());
                        value = Float.valueOf(string.toString());
                        CROP.setFertilizer(value);

                    }

                    // if no $ is present, then when the values are entered, set the text with the currency
                    else {
                        string = s.subSequence(0, s.length());
                        value = Float.valueOf(string.toString());
                        CROP.setFertilizer(value);
                        FERTILIZER_SET.setText(getCurrencyString(CROP.getFertilizer()));

                    }

                }

                // update the total cost, whether per acre or total
                TOTAL_COST_VIEW.performClick();

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

                // this is a nested else if conditional
                // the first level is just to differentiate between having
                // no inputs and having the toggle button pressed or not
                // the nested conditionals are to know the difference between having
                // kept the entered $ value or it having been deleted

                String text = s.subSequence(0, s.length()).toString();

                // if there is no input or the input is only % then nothing happens
                if ((s.length() == 0) || (text.equalsIgnoreCase("$"))) {
                    // DO NOTHING, JUST DONT LET ANYTHING ELSE HAPPEN


                }

                // if the toggle button is pressed, then the values need to be presented as total cost
                // this means they need to be presented as (value * size of farm)
                else if (COST_TOGGLE_BUTTON.isChecked()) {

                    // if the $ is present, then proceed to add the $ when adding values
                    if (text.substring(0, 1).equalsIgnoreCase("$")) {

                        // if delete $ token then make sure not to add
                        string = s.subSequence(1, s.length());
                        value = Float.valueOf(string.toString());
                        CROP.setChemicals(value / CROP.getSizeAcresPlanted());


                    }

                    // if no $ is present, then when the values are entered, set the text with the currency
                    else {
                        string = s.subSequence(0, s.length());
                        value = Float.valueOf(string.toString());
                        CROP.setChemicals(value / CROP.getSizeAcresPlanted());
                        CHEMICALS_SET.setText(getCurrencyString(CROP.getChemicals() * CROP.getSizeAcresPlanted()));

                    }

                }

                // if the toggle button is not pressed, then the values will show up as the per acre values
                // and will be as they were saved
                else if (!(COST_TOGGLE_BUTTON.isChecked())) {

                    // if the $ is present, then proceed to add the $ when adding values
                    if (text.substring(0, 1).equalsIgnoreCase("$")) {

                        // if delete $ token then make sure not to add
                        string = s.subSequence(1, s.length());
                        value = Float.valueOf(string.toString());
                        CROP.setChemicals(value);


                    }

                    // if no $ is present, then when the values are entered, set the text with the currency
                    else {
                        string = s.subSequence(0, s.length());
                        value = Float.valueOf(string.toString());
                        CROP.setChemicals(value);
                        CHEMICALS_SET.setText(getCurrencyString(CROP.getChemicals()));

                    }

                }

                // update the total cost, whether per acre or total
                TOTAL_COST_VIEW.performClick();

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

                // this is a nested else if conditional
                // the first level is just to differentiate between having
                // no inputs and having the toggle button pressed or not
                // the nested conditionals are to know the difference between having
                // kept the entered $ value or it having been deleted

                String text = s.subSequence(0, s.length()).toString();

                // if there is no input or the input is only % then nothing happens
                if ((s.length() == 0) || (text.equalsIgnoreCase("$"))) {
                    // DO NOTHING, JUST DONT LET ANYTHING ELSE HAPPEN

                }

                // if the toggle button is pressed, then the values need to be presented as total cost
                // this means they need to be presented as (value * size of farm)
                else if (COST_TOGGLE_BUTTON.isChecked()) {

                    // if the $ is present, then proceed to add the $ when adding values
                    if (text.substring(0, 1).equalsIgnoreCase("$")) {

                        // if delete $ token then make sure not to add
                        string = s.subSequence(1, s.length());
                        value = Float.valueOf(string.toString());
                        CROP.setCustomOps(value / CROP.getSizeAcresPlanted());


                    }

                    // if no $ is present, then when the values are entered, set the text with the currency
                    else {
                        string = s.subSequence(0, s.length());
                        value = Float.valueOf(string.toString());
                        CROP.setCustomOps(value / CROP.getSizeAcresPlanted());
                        CUSTOM_OPS_SET.setText(getCurrencyString(CROP.getCustomOps() * CROP.getSizeAcresPlanted()));

                    }

                }

                // if the toggle button is not pressed, then the values will show up as the per acre values
                // and will be as they were saved
                else if (!(COST_TOGGLE_BUTTON.isChecked())) {

                    // if the $ is present, then proceed to add the $ when adding values
                    if (text.substring(0, 1).equalsIgnoreCase("$")) {

                        // if delete $ token then make sure not to add
                        string = s.subSequence(1, s.length());
                        value = Float.valueOf(string.toString());
                        CROP.setCustomOps(value);


                    }

                    // if no $ is present, then when the values are entered, set the text with the currency
                    else {
                        string = s.subSequence(0, s.length());
                        value = Float.valueOf(string.toString());
                        CROP.setCustomOps(value);
                        CUSTOM_OPS_SET.setText(getCurrencyString(CROP.getCustomOps()));

                    }

                }

                // update the total cost, whether per acre or total
                TOTAL_COST_VIEW.performClick();

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

                // this is a nested else if conditional
                // the first level is just to differentiate between having
                // no inputs and having the toggle button pressed or not
                // the nested conditionals are to know the difference between having
                // kept the entered $ value or it having been deleted

                String text = s.subSequence(0, s.length()).toString();

                // if there is no input or the input is only % then nothing happens
                if ((s.length() == 0) || (text.equalsIgnoreCase("$"))) {
                    // DO NOTHING, JUST DONT LET ANYTHING ELSE HAPPEN


                }

                // if the toggle button is pressed, then the values need to be presented as total cost
                // this means they need to be presented as (value * size of farm)
                else if (COST_TOGGLE_BUTTON.isChecked()) {

                    // if the $ is present, then proceed to add the $ when adding values
                    if (text.substring(0, 1).equalsIgnoreCase("$")) {

                        // if delete $ token then make sure not to add
                        string = s.subSequence(1, s.length());
                        value = Float.valueOf(string.toString());
                        CROP.setFueLubeElec(value / CROP.getSizeAcresPlanted());


                    }

                    // if no $ is present, then when the values are entered, set the text with the currency
                    else {
                        string = s.subSequence(0, s.length());
                        value = Float.valueOf(string.toString());
                        CROP.setFueLubeElec(value / CROP.getSizeAcresPlanted());
                        FUEL_LUBE_SET.setText(getCurrencyString(CROP.getFueLubeElec() * CROP.getSizeAcresPlanted()));

                    }

                }

                // if the toggle button is not pressed, then the values will show up as the per acre values
                // and will be as they were saved
                else if (!(COST_TOGGLE_BUTTON.isChecked())) {

                    // if the $ is present, then proceed to add the $ when adding values
                    if (text.substring(0, 1).equalsIgnoreCase("$")) {

                        // if delete $ token then make sure not to add
                        string = s.subSequence(1, s.length());
                        value = Float.valueOf(string.toString());
                        CROP.setFueLubeElec(value);


                    }

                    // if no $ is present, then when the values are entered, set the text with the currency
                    else {
                        string = s.subSequence(0, s.length());
                        value = Float.valueOf(string.toString());
                        CROP.setFueLubeElec(value);
                        FUEL_LUBE_SET.setText(getCurrencyString(CROP.getFueLubeElec()));

                    }

                }

                // update the total cost, whether per acre or total
                TOTAL_COST_VIEW.performClick();

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

                // this is a nested else if conditional
                // the first level is just to differentiate between having
                // no inputs and having the toggle button pressed or not
                // the nested conditionals are to know the difference between having
                // kept the entered $ value or it having been deleted

                String text = s.subSequence(0, s.length()).toString();

                // if there is no input or the input is only % then nothing happens
                if ((s.length() == 0) || (text.equalsIgnoreCase("$"))) {
                    // DO NOTHING, JUST DONT LET ANYTHING ELSE HAPPEN


                }

                // if the toggle button is pressed, then the values need to be presented as total cost
                // this means they need to be presented as (value * size of farm)
                else if (COST_TOGGLE_BUTTON.isChecked()) {

                    // if the $ is present, then proceed to add the $ when adding values
                    if (text.substring(0, 1).equalsIgnoreCase("$")) {

                        // if delete $ token then make sure not to add
                        string = s.subSequence(1, s.length());
                        value = Float.valueOf(string.toString());
                        CROP.setRepairs(value / CROP.getSizeAcresPlanted());


                    }

                    // if no $ is present, then when the values are entered, set the text with the currency
                    else {
                        string = s.subSequence(0, s.length());
                        value = Float.valueOf(string.toString());
                        CROP.setRepairs(value / CROP.getSizeAcresPlanted());
                        REPAIRS_SET.setText(getCurrencyString(CROP.getRepairs() * CROP.getSizeAcresPlanted()));

                    }

                }

                // if the toggle button is not pressed, then the values will show up as the per acre values
                // and will be as they were saved
                else if (!(COST_TOGGLE_BUTTON.isChecked())) {

                    // if the $ is present, then proceed to add the $ when adding values
                    if (text.substring(0, 1).equalsIgnoreCase("$")) {

                        // if delete $ token then make sure not to add
                        string = s.subSequence(1, s.length());
                        value = Float.valueOf(string.toString());
                        CROP.setRepairs(value);


                    }

                    // if no $ is present, then when the values are entered, set the text with the currency
                    else {
                        string = s.subSequence(0, s.length());
                        value = Float.valueOf(string.toString());
                        CROP.setRepairs(value);
                        REPAIRS_SET.setText(getCurrencyString(CROP.getRepairs()));

                    }

                }

                // update the total cost, whether per acre or total
                TOTAL_COST_VIEW.performClick();

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

                // this is a nested else if conditional
                // the first level is just to differentiate between having
                // no inputs and having the toggle button pressed or not
                // the nested conditionals are to know the difference between having
                // kept the entered $ value or it having been deleted

                String text = s.subSequence(0, s.length()).toString();

                // if there is no input or the input is only % then nothing happens
                if ((s.length() == 0) || (text.equalsIgnoreCase("$"))) {
                    // DO NOTHING, JUST DONT LET ANYTHING ELSE HAPPEN


                }

                // if the toggle button is pressed, then the values need to be presented as total cost
                // this means they need to be presented as (value * size of farm)
                else if (COST_TOGGLE_BUTTON.isChecked()) {

                    // if the $ is present, then proceed to add the $ when adding values
                    if (text.substring(0, 1).equalsIgnoreCase("$")) {

                        // if delete $ token then make sure not to add
                        string = s.subSequence(1, s.length());
                        value = Float.valueOf(string.toString());
                        CROP.setIrrigationWater(value / CROP.getSizeAcresPlanted());


                    }

                    // if no $ is present, then when the values are entered, set the text with the currency
                    else {
                        string = s.subSequence(0, s.length());
                        value = Float.valueOf(string.toString());
                        CROP.setIrrigationWater(value / CROP.getSizeAcresPlanted());
                        IRRIGATION_WATER_SET.setText(getCurrencyString(CROP.getIrrigationWater() * CROP.getSizeAcresPlanted()));

                    }

                }

                // if the toggle button is not pressed, then the values will show up as the per acre values
                // and will be as they were saved
                else if (!(COST_TOGGLE_BUTTON.isChecked())) {

                    // if the $ is present, then proceed to add the $ when adding values
                    if (text.substring(0, 1).equalsIgnoreCase("$")) {

                        // if delete $ token then make sure not to add
                        string = s.subSequence(1, s.length());
                        value = Float.valueOf(string.toString());
                        CROP.setIrrigationWater(value);


                    }

                    // if no $ is present, then when the values are entered, set the text with the currency
                    else {
                        string = s.subSequence(0, s.length());
                        value = Float.valueOf(string.toString());
                        CROP.setIrrigationWater(value);
                        IRRIGATION_WATER_SET.setText(getCurrencyString(CROP.getIrrigationWater()));

                    }

                }

                // update the total cost, whether per acre or total
                TOTAL_COST_VIEW.performClick();

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

                // this is a nested else if conditional
                // the first level is just to differentiate between having
                // no inputs and having the toggle button pressed or not
                // the nested conditionals are to know the difference between having
                // kept the entered $ value or it having been deleted

                String text = s.subSequence(0, s.length()).toString();

                // if there is no input or the input is only % then nothing happens
                if ((s.length() == 0) || (text.equalsIgnoreCase("$"))) {
                    // DO NOTHING, JUST DONT LET ANYTHING ELSE HAPPEN


                }

                // if the toggle button is pressed, then the values need to be presented as total cost
                // this means they need to be presented as (value * size of farm)
                else if (COST_TOGGLE_BUTTON.isChecked()) {

                    // if the $ is present, then proceed to add the $ when adding values
                    if (text.substring(0, 1).equalsIgnoreCase("$")) {

                        // if delete $ token then make sure not to add
                        string = s.subSequence(1, s.length());
                        value = Float.valueOf(string.toString());
                        CROP.setIntOnCap(value / CROP.getSizeAcresPlanted());


                    }

                    // if no $ is present, then when the values are entered, set the text with the currency
                    else {
                        string = s.subSequence(0, s.length());
                        value = Float.valueOf(string.toString());
                        CROP.setIntOnCap(value / CROP.getSizeAcresPlanted());
                        INT_ON_CAP_SET.setText(getCurrencyString(CROP.getIntOnCap() * CROP.getSizeAcresPlanted()));

                    }

                }

                // if the toggle button is not pressed, then the values will show up as the per acre values
                // and will be as they were saved
                else if (!(COST_TOGGLE_BUTTON.isChecked())) {

                    // if the $ is present, then proceed to add the $ when adding values
                    if (text.substring(0, 1).equalsIgnoreCase("$")) {

                        // if delete $ token then make sure not to add
                        string = s.subSequence(1, s.length());
                        value = Float.valueOf(string.toString());
                        CROP.setIntOnCap(value);


                    }

                    // if no $ is present, then when the values are entered, set the text with the currency
                    else {
                        string = s.subSequence(0, s.length());
                        value = Float.valueOf(string.toString());
                        CROP.setIntOnCap(value);
                        INT_ON_CAP_SET.setText(getCurrencyString(CROP.getIntOnCap()));

                    }

                }

                // update the total cost, whether per acre or total
                TOTAL_COST_VIEW.performClick();

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

                // this is a nested else if conditional
                // the first level is just to differentiate between having
                // no inputs and having the toggle button pressed or not
                // the nested conditionals are to know the difference between having
                // kept the entered $ value or it having been deleted

                String text = s.subSequence(0, s.length()).toString();

                // if there is no input or the input is only % then nothing happens
                if ((s.length() == 0) || (text.equalsIgnoreCase("$"))) {
                    // DO NOTHING, JUST DONT LET ANYTHING ELSE HAPPEN

                }

                // if the toggle button is pressed, then the values need to be presented as total cost
                // this means they need to be presented as (value * size of farm)
                else if (COST_TOGGLE_BUTTON.isChecked()) {

                    // if the $ is present, then proceed to add the $ when adding values
                    if (text.substring(0, 1).equalsIgnoreCase("$")) {

                        // if delete $ token then make sure not to add
                        string = s.subSequence(1, s.length());
                        value = Float.valueOf(string.toString());
                        CROP.setHiredLabor(value / CROP.getSizeAcresPlanted());

                    }

                    // if no $ is present, then when the values are entered, set the text with the currency
                    else {
                        string = s.subSequence(0, s.length());
                        value = Float.valueOf(string.toString());
                        CROP.setHiredLabor(value / CROP.getSizeAcresPlanted());
                        HIRED_LABOR_SET.setText(getCurrencyString(CROP.getHiredLabor() * CROP.getSizeAcresPlanted()));

                    }

                }

                // if the toggle button is not pressed, then the values will show up as the per acre values
                // and will be as they were saved
                else if (!(COST_TOGGLE_BUTTON.isChecked())) {

                    // if the $ is present, then proceed to add the $ when adding values
                    if (text.substring(0, 1).equalsIgnoreCase("$")) {
                        // if delete $ token then make sure not to add
                        string = s.subSequence(1, s.length());
                        value = Float.valueOf(string.toString());
                        CROP.setHiredLabor(value);

                    }

                    // if no $ is present, then when the values are entered, set the text with the currency
                    else {
                        string = s.subSequence(0, s.length());
                        value = Float.valueOf(string.toString());
                        CROP.setHiredLabor(value);
                        HIRED_LABOR_SET.setText(getCurrencyString(CROP.getHiredLabor()));

                    }

                }
                // update the total cost, whether per acre or total
                TOTAL_COST_VIEW.performClick();

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

                // this is a nested else if conditional
                // the first level is just to differentiate between having
                // no inputs and having the toggle button pressed or not
                // the nested conditionals are to know the difference between having
                // kept the entered $ value or it having been deleted

                String text = s.subSequence(0, s.length()).toString();


                // if there is no input or the input is only % then nothing happens
                if ((s.length() == 0) || (text.equalsIgnoreCase("$"))) {
                    // DO NOTHING, JUST DONT LET ANYTHING ELSE HAPPEN

                }


                // if the toggle button is pressed, then the values need to be presented as total cost
                // this means they need to be presented as (value * size of farm)
                else if (COST_TOGGLE_BUTTON.isChecked()) {

                    // if the $ is present, then proceed to add the $ when adding values
                    if (text.substring(0, 1).equalsIgnoreCase("$")) {

                        // if delete $ token then make sure not to add
                        string = s.subSequence(1, s.length());
                        value = Float.valueOf(string.toString());
                        CROP.setCostUnpaidLabor(value / CROP.getSizeAcresPlanted());

                    }

                    // if no $ is present, then when the values are entered, set the text with the currency
                    else {
                        string = s.subSequence(0, s.length());
                        value = Float.valueOf(string.toString());
                        CROP.setCostUnpaidLabor(value / CROP.getSizeAcresPlanted());
                        UNPAID_LABOR_SET.setText(getCurrencyString(CROP.getCostUnpaidLabor() * CROP.getSizeAcresPlanted()));

                    }

                }

                // if the toggle button is not pressed, then the values will show up as the per acre values
                // and will be as they were saved
                else if (!(COST_TOGGLE_BUTTON.isChecked())) {


                    // if the $ is present, then proceed to add the $ when adding values
                    if (text.substring(0, 1).equalsIgnoreCase("$")) {
                        // if delete $ token then make sure not to add
                        string = s.subSequence(1, s.length());
                        value = Float.valueOf(string.toString());
                        CROP.setCostUnpaidLabor(value);

                    }

                    // if no $ is present, then when the values are entered, set the text with the currency
                    else {
                        string = s.subSequence(0, s.length());
                        value = Float.valueOf(string.toString());
                        CROP.setCostUnpaidLabor(value);
                        UNPAID_LABOR_SET.setText(getCurrencyString(CROP.getCostUnpaidLabor()));

                    }

                }
                // update the total cost, whether per acre or total
                TOTAL_COST_VIEW.performClick();

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

                // this is a nested else if conditional
                // the first level is just to differentiate between having
                // no inputs and having the toggle button pressed or not
                // the nested conditionals are to know the difference between having
                // kept the entered $ value or it having been deleted

                String text = s.subSequence(0, s.length()).toString();

                // if there is no input or the input is only % then nothing happens
                if ((s.length() == 0) || (text.equalsIgnoreCase("$"))) {
                    // DO NOTHING, JUST DONT LET ANYTHING ELSE HAPPEN

                }

                // if the toggle button is pressed, then the values need to be presented as total cost
                // this means they need to be presented as (value * size of farm)
                else if (COST_TOGGLE_BUTTON.isChecked()) {

                    // if the $ is present, then proceed to add the $ when adding values
                    if (text.substring(0, 1).equalsIgnoreCase("$")) {

                        // if delete $ token then make sure not to add
                        string = s.subSequence(1, s.length());
                        value = Float.valueOf(string.toString());
                        CROP.setCapRecoveryOfEquip(value / CROP.getSizeAcresPlanted());

                    }

                    // if no $ is present, then when the values are entered, set the text with the currency
                    else {
                        string = s.subSequence(0, s.length());
                        value = Float.valueOf(string.toString());
                        CROP.setCapRecoveryOfEquip(value / CROP.getSizeAcresPlanted());
                        RECOVERY_EQUIP_SET.setText(getCurrencyString(CROP.getCapRecoveryOfEquip() * CROP.getSizeAcresPlanted()));

                    }

                }

                // if the toggle button is not pressed, then the values will show up as the per acre values
                // and will be as they were saved
                else if (!(COST_TOGGLE_BUTTON.isChecked())) {

                    // if the $ is present, then proceed to add the $ when adding values
                    if (text.substring(0, 1).equalsIgnoreCase("$")) {
                        // if delete $ token then make sure not to add
                        string = s.subSequence(1, s.length());
                        value = Float.valueOf(string.toString());
                        CROP.setCapRecoveryOfEquip(value);

                    }

                    // if no $ is present, then when the values are entered, set the text with the currency
                    else {
                        string = s.subSequence(0, s.length());
                        value = Float.valueOf(string.toString());
                        CROP.setCapRecoveryOfEquip(value);
                        RECOVERY_EQUIP_SET.setText(getCurrencyString(CROP.getCapRecoveryOfEquip()));

                    }

                }
                // update the total cost, whether per acre or total
                TOTAL_COST_VIEW.performClick();

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

                // this is a nested else if conditional
                // the first level is just to differentiate between having
                // no inputs and having the toggle button pressed or not
                // the nested conditionals are to know the difference between having
                // kept the entered $ value or it having been deleted

                String text = s.subSequence(0, s.length()).toString();

                // if there is no input or the input is only % then nothing happens
                if ((s.length() == 0) || (text.equalsIgnoreCase("$"))) {
                    // DO NOTHING, JUST DONT LET ANYTHING ELSE HAPPEN

                }

                // if the toggle button is pressed, then the values need to be presented as total cost
                // this means they need to be presented as (value * size of farm)
                else if (COST_TOGGLE_BUTTON.isChecked()) {

                    // if the $ is present, then proceed to add the $ when adding values
                    if (text.substring(0, 1).equalsIgnoreCase("$")) {

                        // if delete $ token then make sure not to add
                        string = s.subSequence(1, s.length());
                        value = Float.valueOf(string.toString());
                        CROP.setCostOfLandRentalRate(value / CROP.getSizeAcresPlanted());

                    }

                    // if no $ is present, then when the values are entered, set the text with the currency
                    else {
                        string = s.subSequence(0, s.length());
                        value = Float.valueOf(string.toString());
                        CROP.setCostOfLandRentalRate(value / CROP.getSizeAcresPlanted());
                        LAND_RENTAL_RATE_SET.setText(getCurrencyString(CROP.getCostOfLandRentalRate() * CROP.getSizeAcresPlanted()));

                    }

                }

                // if the toggle button is not pressed, then the values will show up as the per acre values
                // and will be as they were saved
                else if (!(COST_TOGGLE_BUTTON.isChecked())) {

                    // if the $ is present, then proceed to add the $ when adding values
                    if (text.substring(0, 1).equalsIgnoreCase("$")) {
                        // if delete $ token then make sure not to add
                        string = s.subSequence(1, s.length());
                        value = Float.valueOf(string.toString());
                        CROP.setCostOfLandRentalRate(value);

                    }

                    // if no $ is present, then when the values are entered, set the text with the currency
                    else {
                        string = s.subSequence(0, s.length());
                        value = Float.valueOf(string.toString());
                        CROP.setCostOfLandRentalRate(value);
                        LAND_RENTAL_RATE_SET.setText(getCurrencyString(CROP.getCostOfLandRentalRate()));

                    }

                }
                // update the total cost, whether per acre or total
                TOTAL_COST_VIEW.performClick();

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

                // this is a nested else if conditional
                // the first level is just to differentiate between having
                // no inputs and having the toggle button pressed or not
                // the nested conditionals are to know the difference between having
                // kept the entered $ value or it having been deleted

                String text = s.subSequence(0, s.length()).toString();


                // if there is no input or the input is only % then nothing happens
                if ((s.length() == 0) || (text.equalsIgnoreCase("$"))) {
                    // DO NOTHING, JUST DONT LET ANYTHING ELSE HAPPEN

                }

                // if the toggle button is pressed, then the values need to be presented as total cost
                // this means they need to be presented as (value * size of farm)
                else if (COST_TOGGLE_BUTTON.isChecked()) {

                    // if the $ is present, then proceed to add the $ when adding values
                    if (text.substring(0, 1).equalsIgnoreCase("$")) {

                        // if delete $ token then make sure not to add
                        string = s.subSequence(1, s.length());
                        value = Float.valueOf(string.toString());
                        CROP.setTaxesInsurance(value / CROP.getSizeAcresPlanted());

                    }

                    // if no $ is present, then when the values are entered, set the text with the currency
                    else {
                        string = s.subSequence(0, s.length());
                        value = Float.valueOf(string.toString());
                        CROP.setTaxesInsurance(value / CROP.getSizeAcresPlanted());
                        TAX_INSURANCE_SET.setText(getCurrencyString(CROP.getTaxesInsurance() * CROP.getSizeAcresPlanted()));

                    }


                }

                // if the toggle button is not pressed, then the values will show up as the per acre values
                // and will be as they were saved
                else if (!(COST_TOGGLE_BUTTON.isChecked())) {

                    // if the $ is present, then proceed to add the $ when adding values
                    if (text.substring(0, 1).equalsIgnoreCase("$")) {
                        // if delete $ token then make sure not to add
                        string = s.subSequence(1, s.length());
                        value = Float.valueOf(string.toString());
                        CROP.setTaxesInsurance(value);

                    }

                    // if no $ is present, then when the values are entered, set the text with the currency
                    else {
                        string = s.subSequence(0, s.length());
                        value = Float.valueOf(string.toString());
                        CROP.setTaxesInsurance(value);
                        TAX_INSURANCE_SET.setText(getCurrencyString(CROP.getTaxesInsurance()));

                    }

                }
                // update the total cost, whether per acre or total
                TOTAL_COST_VIEW.performClick();


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

                // this is a nested else if conditional
                // the first level is just to differentiate between having
                // no inputs and having the toggle button pressed or not
                // the nested conditionals are to know the difference between having
                // kept the entered $ value or it having been deleted

                String text = s.subSequence(0, s.length()).toString();

                // if there is no input or the input is only % then nothing happens
                if ((s.length() == 0) || (text.equalsIgnoreCase("$"))) {
                    // DO NOTHING, JUST DONT LET ANYTHING ELSE HAPPEN
                }

                // if the toggle button is pressed, then the values need to be presented as total cost
                // this means they need to be presented as (value * size of farm)
                else if (COST_TOGGLE_BUTTON.isChecked()) {

                    // if the $ is present, then proceed to add the $ when adding values
                    if (text.substring(0, 1).equalsIgnoreCase("$")) {

                        // if delete $ token then make sure not to add
                        string = s.subSequence(1, s.length());
                        value = Float.valueOf(string.toString());
                        CROP.setMiscellaneous(value / CROP.getSizeAcresPlanted());

                    }

                    // if no $ is present, then when the values are entered, set the text with the currency
                    else {
                        string = s.subSequence(0, s.length());
                        value = Float.valueOf(string.toString());
                        CROP.setMiscellaneous(value / CROP.getSizeAcresPlanted());
                        MISC_SET.setText(getCurrencyString(CROP.getMiscellaneous() * CROP.getSizeAcresPlanted()));

                    }


                }

                // if the toggle button is not pressed, then the values will show up as the per acre values
                // and will be as they were saved
                else if (!(COST_TOGGLE_BUTTON.isChecked())) {


                    // if the $ is present, then proceed to add the $ when adding values
                    if (text.substring(0, 1).equalsIgnoreCase("$")) {
                        // if delete $ token then make sure not to add
                        string = s.subSequence(1, s.length());
                        value = Float.valueOf(string.toString());
                        CROP.setMiscellaneous(value);

                    }

                    // if no $ is present, then when the values are entered, set the text with the currency
                    else {
                        string = s.subSequence(0, s.length());
                        value = Float.valueOf(string.toString());
                        CROP.setMiscellaneous(value);
                        MISC_SET.setText(getCurrencyString(CROP.getMiscellaneous()));

                    }

                }
                // update the total cost, whether per acre or total
                TOTAL_COST_VIEW.performClick();


            }
        });

        // clicking on this toggle button changes the amount shown on the display
        COST_TOGGLE_BUTTON.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean on = COST_TOGGLE_BUTTON.isChecked();

                // show total cost of each input
                if (on) {

                    SEED_SET.setText(getCurrencyString(CROP.getSeed() * CROP.getSizeAcresPlanted()));
                    FERTILIZER_SET.setText(getCurrencyString(CROP.getFertilizer() * CROP.getSizeAcresPlanted()));
                    CHEMICALS_SET.setText(getCurrencyString(CROP.getChemicals() * CROP.getSizeAcresPlanted()));
                    CUSTOM_OPS_SET.setText(getCurrencyString(CROP.getCustomOps() * CROP.getSizeAcresPlanted()));
                    FUEL_LUBE_SET.setText(getCurrencyString(CROP.getFueLubeElec() * CROP.getSizeAcresPlanted()));
                    REPAIRS_SET.setText(getCurrencyString(CROP.getRepairs() * CROP.getSizeAcresPlanted()));
                    IRRIGATION_WATER_SET.setText(getCurrencyString(CROP.getIrrigationWater() * CROP.getSizeAcresPlanted()));
                    INT_ON_CAP_SET.setText(getCurrencyString(CROP.getIntOnCap() * CROP.getSizeAcresPlanted()));
                    HIRED_LABOR_SET.setText(getCurrencyString(CROP.getHiredLabor() * CROP.getSizeAcresPlanted()));
                    UNPAID_LABOR_SET.setText(getCurrencyString(CROP.getCostUnpaidLabor() * CROP.getSizeAcresPlanted()));
                    RECOVERY_EQUIP_SET.setText(getCurrencyString(CROP.getCapRecoveryOfEquip() * CROP.getSizeAcresPlanted()));
                    LAND_RENTAL_RATE_SET.setText(getCurrencyString(CROP.getCostOfLandRentalRate() * CROP.getSizeAcresPlanted()));
                    TAX_INSURANCE_SET.setText(getCurrencyString(CROP.getTaxesInsurance() * CROP.getSizeAcresPlanted()));
                    MISC_SET.setText(getCurrencyString(CROP.getMiscellaneous() * CROP.getSizeAcresPlanted()));
                    TOTAL_COST_VIEW.setText(getCurrencyString(CROP.getTotalCost() * CROP.getSizeAcresPlanted()));

                    // show the total cost per acre of each input
                } else if (!on) {

                    SEED_SET.setText(getCurrencyString(CROP.getSeed()));
                    FERTILIZER_SET.setText(getCurrencyString(CROP.getFertilizer()));
                    CHEMICALS_SET.setText(getCurrencyString(CROP.getChemicals()));
                    CUSTOM_OPS_SET.setText(getCurrencyString(CROP.getCustomOps()));
                    FUEL_LUBE_SET.setText(getCurrencyString(CROP.getFueLubeElec()));
                    REPAIRS_SET.setText(getCurrencyString(CROP.getRepairs()));
                    IRRIGATION_WATER_SET.setText(getCurrencyString(CROP.getIrrigationWater()));
                    INT_ON_CAP_SET.setText(getCurrencyString(CROP.getIntOnCap()));
                    HIRED_LABOR_SET.setText(getCurrencyString(CROP.getHiredLabor()));
                    UNPAID_LABOR_SET.setText(getCurrencyString(CROP.getCostUnpaidLabor()));
                    RECOVERY_EQUIP_SET.setText(getCurrencyString(CROP.getCapRecoveryOfEquip()));
                    LAND_RENTAL_RATE_SET.setText(getCurrencyString(CROP.getCostOfLandRentalRate()));
                    TAX_INSURANCE_SET.setText(getCurrencyString(CROP.getTaxesInsurance()));
                    MISC_SET.setText(getCurrencyString(CROP.getMiscellaneous()));
                    TOTAL_COST_VIEW.setText(getCurrencyString(CROP.getTotalCost()));

                }
            }
        });

        final Button SAVE_CROP = (Button) findViewById(R.id.customSaveState);
        final Button PREV_SAVED_CROP = (Button) findViewById(R.id.customPrevSavedStats);

        SAVE_CROP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog builder = new AlertDialog.Builder(Custom.this).create();
                final EditText SAVE_CROP_EDIT_TEXT = new EditText(Custom.this);

                builder.setTitle("Save Corn Inputs");
                SAVE_CROP_EDIT_TEXT.setHint("name your values");
                builder.setView(SAVE_CROP_EDIT_TEXT);
                builder.setButton(AlertDialog.BUTTON_POSITIVE, "Save Crop Values", new DialogInterface.OnClickListener() {


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
                            Toast.makeText(Custom.this, msg, Toast.LENGTH_SHORT).show();
                            SAVE_CROP.performClick();
                        } else {
                            CROP.setTitle(title);
                            dbStats.addCropStats(CROP);
                        }
                        Log.d("title", title);
                        Log.d("countOfDbStats", "" + dbStats.getCropStatsCount());
                        Toast.makeText(Custom.this, "Saved. Displaying regional data now.", Toast.LENGTH_LONG);
                        setRegionalAverageValues(CROP.getCrop(), CROP.getRegion());

                    }

                    private void setRegionalAverageValues(int crop, int region) {

                        CropStats cropStat = new CropStats(crop, region);

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

                        // get database crop values
                        final DatabaseStats dbStats = DatabaseStats.getInstance(getApplicationContext());
                        final ArrayList<CropStats> ARRAY_CROP = dbStats.getCropRegionCropStats(CROP.getCropName(), CROP.getRegionName());


                        // create the alert dialog for saving, including both a "save new data" and "overwrite" buttons
                        final AlertDialog.Builder SPINNER_DIALOG = new AlertDialog.Builder(Custom.this);
                        SPINNER_DIALOG.setTitle("Choose a crop");

                        // if there are no saved stats, then have then let the user know to add one
                        if (ARRAY_CROP.size() == 0) {
                            SPINNER_DIALOG.setMessage("No saved custom stats. Add custom values, and save them");
                            SPINNER_DIALOG.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    dialog.dismiss();
                                }
                            });
                            SPINNER_DIALOG.create();
                            SPINNER_DIALOG.show();

                            // if there are saved values, then can overwrite values
                            // create the array of
                        } else {

                            // initialize a string array
                            final String[] array = new String[ARRAY_CROP.size()];

                            // get the title strings for each of the saved values
                            for (int i = 0; i < ARRAY_CROP.size(); i++) {

                                array[i] = ARRAY_CROP.get(i).getTitle();

                            }

                            // create the spinner that will allow any of the crops to be overwritten
                            Spinner spinner = new Spinner(Custom.this);
                            ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(Custom.this, android.R.layout.simple_spinner_item, array);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner.setAdapter(adapter);


                            SPINNER_DIALOG.setAdapter(adapter, new DialogInterface.OnClickListener() {
                                @Override

                                // use which to set data accumulation
                                public void onClick(DialogInterface dialog, int which) {

                                    dbStats.updateCrop(CROP, ARRAY_CROP.get(which).getTitle());
                                    Toast.makeText(Custom.this, "Saved Data, values are now regional standard", Toast.LENGTH_LONG).show();
                                    setRegionalAverageValues(CROP.getCrop(), CROP.getRegion());


                                }

                                // regional a
                                private void setRegionalAverageValues(int crop, int region) {

                                    CropStats cropStat = new CropStats(crop, region);

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

        // this is the "edit prev values" button
        PREV_SAVED_CROP.setOnClickListener(new View.OnClickListener() {

            final DatabaseStats DB_STATS = DatabaseStats.getInstance(getApplicationContext());

            @Override
            public void onClick(View v) {

                // create alert dialog
                final AlertDialog builder = new AlertDialog.Builder(Custom.this).create();
                builder.setTitle("Edit Corn Values");
                builder.setMessage("You can choose to get saved data or delete entries");
                builder.setButton(AlertDialog.BUTTON_POSITIVE, "Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        Log.d("ccc crop", CROP.getCropName());
                        final ArrayList<CropStats> arrayCrop = DB_STATS.getCropRegionCropStats(CROP.getCropName(), CROP.getRegionName());
                        String[] array = new String[arrayCrop.size()];
                        //dbStats.close();

                        for (int i = 0; i < arrayCrop.size(); i++) {

                            array[i] = arrayCrop.get(i).getTitle();

                        }


                        final AlertDialog.Builder SPINNER_DIALOG = new AlertDialog.Builder(Custom.this);
                        SPINNER_DIALOG.setTitle("Choose a crop");

                        // if there are previously saved data, then prompt the user to save some data
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
                        }

                        // if there are values, then take the titles into an array
                        // and set them for the next dialog popup
                        else {

                            for (int i = 0; i < arrayCrop.size(); i++) {

                                array[i] = arrayCrop.get(i).getTitle();

                            }

                            Spinner spinner = new Spinner(Custom.this);
                            ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(Custom.this, android.R.layout.simple_spinner_item, array);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner.setAdapter(adapter);


                            SPINNER_DIALOG.setAdapter(adapter, new DialogInterface.OnClickListener() {
                                @Override

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


                        // create the dialog for deleting crops
                        AlertDialog.Builder dialogInner = new AlertDialog.Builder(Custom.this);
                        dialogInner.setTitle("Choose which crops to delete");
                        final ArrayList<CropStats> CROP_DB_DELETE = DB_STATS.getCropRegionCropStats(CROP.getCropName(), CROP.getRegionName());
                        Log.d("crop size", "" + CROP_DB_DELETE.size());


                        String[] checkBoxes = new String[CROP_DB_DELETE.size()];
                        for (int i = 0; i < CROP_DB_DELETE.size(); i++) {
                            checkBoxes[i] = CROP_DB_DELETE.get(i).getTitle();

                        }

                        //itemsSelected is used when a button is clicked
                        final ArrayList itemsSelected = new ArrayList();

                        // if there are no crops to delete, then tell them to first save data
                        if (CROP_DB_DELETE.size() == 0) {
                            dialogInner.setMessage("Set some crop data in order to be able to delete it");
                            dialogInner.setNeutralButton("Okay", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    //Your logic when OK button is clicked
                                    dialog.dismiss();
                                }
                            });

                            // create the list of crops to delete
                        } else {


                            // this is the delete dialog with a checkbox-like display to choose as many
                            // saved data points as the user would like
                            dialogInner.setMultiChoiceItems(checkBoxes, null, new DialogInterface.OnMultiChoiceClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int selectedItemId, boolean isSelected) {

                                    // anytime a checkbox is checked, it is either added to the arraylist of id's
                                    // or it is removed from the arraylist if it is not selected
                                    if (isSelected) {

                                        itemsSelected.add(selectedItemId);
                                    } else if (itemsSelected.contains(selectedItemId)) {
                                        itemsSelected.remove(Integer.valueOf(selectedItemId));
                                    }
                                }
                            })      // first choose some crops to delete, and they will be deleted
                                    .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int id) {

                                            // once the delete button is chosen, the id's are then matched with the
                                            // cropstats arraylist index and are passed to the db to be deleted
                                            if (itemsSelected.size() > 0) {
                                                for (int i = 0; i < itemsSelected.size(); i++) {

                                                    DatabaseStats dbStats = DatabaseStats.getInstance(getApplicationContext());
                                                    dbStats.deleteCropStat(CROP_DB_DELETE.get(i));

                                                }
                                            }

                                            // if nothing is chosen to be deleted, then prompt the user to select values to be deleted
                                            else {

                                                Toast.makeText(Custom.this, "Choose crop stats to delete", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    })
                                            // cancel button to remove the popup
                                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.dismiss();
                                        }

                                    });


                        }
                        dialogInner.create();
                        dialogInner.show();

                    }
                });

                builder.show();
            }

            // display the values that were chosen to edit
            private void displayValues(CropStats cropStats) {

                ToggleButton toggle = (ToggleButton) findViewById(R.id.customToggleButton);

                ACRE_DISPLAY.setText(cropStats.getSizeAcresPlanted() + "");
                YIELD_DISPLAY.setText(cropStats.getYield() + "");

                // if the toggle button shows just the per acre values
                if (!toggle.isChecked()) {

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
                    TOTAL_COST_VIEW.setText(getCurrencyString( cropStats.getTotalCost()));


                } else {

                    SEED_SET.setText(getCurrencyString(cropStats.getSizeAcresPlanted() * cropStats.getSeed()));
                    FERTILIZER_SET.setText(getCurrencyString(cropStats.getSizeAcresPlanted() * cropStats.getFertilizer()));
                    CHEMICALS_SET.setText(getCurrencyString(cropStats.getSizeAcresPlanted() * cropStats.getChemicals()));
                    CUSTOM_OPS_SET.setText(getCurrencyString(cropStats.getSizeAcresPlanted() * cropStats.getCustomOps()));
                    FUEL_LUBE_SET.setText(getCurrencyString(cropStats.getSizeAcresPlanted() * cropStats.getFueLubeElec()));
                    REPAIRS_SET.setText(getCurrencyString(cropStats.getSizeAcresPlanted() * cropStats.getRepairs()));
                    IRRIGATION_WATER_SET.setText(getCurrencyString(cropStats.getSizeAcresPlanted() * cropStats.getIrrigationWater()));
                    INT_ON_CAP_SET.setText(getCurrencyString(cropStats.getSizeAcresPlanted() * cropStats.getIntOnCap()));
                    HIRED_LABOR_SET.setText(getCurrencyString(cropStats.getSizeAcresPlanted() * cropStats.getHiredLabor()));
                    UNPAID_LABOR_SET.setText(getCurrencyString(cropStats.getSizeAcresPlanted() * cropStats.getCostUnpaidLabor()));
                    RECOVERY_EQUIP_SET.setText(getCurrencyString(cropStats.getSizeAcresPlanted() * cropStats.getCapRecoveryOfEquip()));
                    LAND_RENTAL_RATE_SET.setText(getCurrencyString(cropStats.getSizeAcresPlanted() * cropStats.getCostOfLandRentalRate()));
                    TAX_INSURANCE_SET.setText(getCurrencyString(cropStats.getSizeAcresPlanted() * cropStats.getTaxesInsurance()));
                    TOTAL_COST_VIEW.setText(getCurrencyString(cropStats.getSizeAcresPlanted() * cropStats.getTotalCost()));

                }


                TextView currentCrop = (TextView) findViewById(R.id.customCurrentCrop);
                currentCrop.setText("currently viewing: " + cropStats.getTitle());


            }

        });


        /**
         * this is the method that leads to go back to the model
         */

        final Button MODEL_BUTTON = (Button) findViewById(R.id.customArrayCrop);
        MODEL_BUTTON.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // create and set the title for the alert dialog
                AlertDialog.Builder dialog = new AlertDialog.Builder(Custom.this);
                dialog.setTitle("View crop data on the model");

                // get the saved db crops from
                DatabaseStats dbStats = new DatabaseStats(getApplicationContext());
                final ArrayList<CropStats> CROPS_ARRAY = dbStats.getCropRegionCropStats(CROP.getCropName(), CROP.getRegionName());


                String[] checkBoxes = new String[CROPS_ARRAY.size()];

                for (int i = 0; i < CROPS_ARRAY.size(); i++) {
                    checkBoxes[i] = CROPS_ARRAY.get(i).getTitle();

                }

                // used to get the correct id of the item chosen from the radio buttons set on
                // the setSingleChoiceItems() on the dialog
                final ArrayList<Integer> itemsSelected = new ArrayList();

                // if no crops are found, let the user know to set crops
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

                    // set the list as a radio button list, so that only one crop data set gets transferred
                    // to the model
                    dialog.setSingleChoiceItems(checkBoxes, -1, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            // if there are already an item set, then need to remove from ArrayList
                            // and add the one that was most recently selected into the arraylist
                            // the arraylist should never have more than one int value stored in it
                            if (itemsSelected.size() == 1) {
                                itemsSelected.remove(0);
                                itemsSelected.add(which);
                            } else {
                                itemsSelected.add(which);
                            }
                            ;

                        }
                    }).setPositiveButton("Show", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {

                            // this method makes it so that the crop int chosen
                            // in the setSingleChoiceItems() method is then is added
                            // to the intent for passing to the Model page
                            ArrayList<CropStats> modelCrops = new ArrayList<>();
                            modelCrops.add(new CropStats(CROP.getCrop(), CROP.getRegion()));
                            modelCrops.add(CROPS_ARRAY.get(itemsSelected.get(0)));

                            // create the intent to go to the Model
                            // include the parcelable arraylist of regional avg
                            // and the custom crop
                            Intent intent = new Intent(Custom.this, Model.class);
                            intent.putParcelableArrayListExtra("crops", modelCrops);
                            final ProgressDialog RENDER = ProgressDialog.show(Custom.this, "", "Rendering...", true);
                            RENDER.setCancelable(false);


                            // this timer is used because the loading of the Model
                            // takes a few seconds so a rendering popup is created
                            new Timer().schedule(
                                    new TimerTask() {
                                        @Override
                                        public void run() {
                                            RENDER.dismiss();

                                        }
                                    },
                                    5000L
                            );

                            startActivity(intent);


                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            // make the dialog disappear from the layout
                            dialog.dismiss();
                        }
                    });

                }
                dialog.create();
                dialog.show();


            }
        });


    }


    /**
     * this method is used to keep the dollar sign when changing and adding
     * currency to an edit text
     */

    private String getCurrencyString(float num) {
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        String builder = nf.format(num);
        //  String builder = String.format("$%.2f", num);
        return builder;
    }

    // if back button is pressed, make sure to let the user know that unsaved data would be
    // lost
    public void onBackPressed() {
        AlertDialog dialog = new AlertDialog.Builder(Custom.this).create();
        dialog.setTitle("Go Back Home");
        dialog.setMessage("If you go back you will lose any progress that is not saved.");
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "Okay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                startActivity(new Intent(Custom.this, Welcome.class));

            }
        });

        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

}






