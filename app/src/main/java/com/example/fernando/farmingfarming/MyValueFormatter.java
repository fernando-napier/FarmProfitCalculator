package com.example.fernando.farmingfarming;

import com.github.mikephil.charting.utils.ValueFormatter;

import java.text.DecimalFormat;

/**
 * Created by fernando on 9/15/15.
 *
 * created to include the values and the dollar signs
 */
public class MyValueFormatter implements ValueFormatter{


    private DecimalFormat format;

    public MyValueFormatter(){

        format = new DecimalFormat("###,##0.00"); // use two decimals

    }

    @Override
    public String getFormattedValue(float value){
        return "$" + format.format(value);
    }



}
