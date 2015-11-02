package com.example.fernando.farmingfarming;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.github.mikephil.charting.buffer.HorizontalBarBuffer;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ValueFormatter;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Model extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model);


        Bundle bundle = getIntent().getExtras();

        // get parcelable list of cropstats from the sqlite database
        final ArrayList<CropStats> cropStatsArrayList = bundle.getParcelableArrayList("crops");
        final CropStats REGIONAL_AVERAGE = cropStatsArrayList.get(0);

        // get the data for the region as added in the previous activity (welcome class)
        RegionData regionData = new RegionData(REGIONAL_AVERAGE.getRegion());

        // set the title of the activity so as to reflect what the page is about
        setTitle(REGIONAL_AVERAGE.getCropName() + " in " + regionData.getRegionName());


        Button homeButton = (Button) findViewById(R.id.modelWelcomeButton);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Model.this, Welcome.class));

            }
        });
        // get the statistics for the region 


        /**
         * this if else statement is used to verify if
         */

        // create the
        HorizontalBarChart inputBarChart = (HorizontalBarChart) findViewById(R.id.cornInputsBarChart);
        inputBarChart.setDrawBarShadow(true);
        inputBarChart.setDrawValueAboveBar(true);
        inputBarChart.setDescription("");
        inputBarChart.setPinchZoom(false);
        inputBarChart.setDrawGridBackground(true);
        inputBarChart.setDoubleTapToZoomEnabled(false);

        // create the xAxis for the
        XAxis xAxis = inputBarChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(true);
        xAxis.setGridLineWidth(0.3f);

        // create the left and right y-axes for the horizontal input barchart
        YAxis leftAxis = inputBarChart.getAxisLeft();
        leftAxis.setDrawAxisLine(true);
        leftAxis.setDrawGridLines(false);
        leftAxis.setGridLineWidth(0.3f);

        YAxis rightAxis = inputBarChart.getAxisRight();
        rightAxis.setDrawAxisLine(true);
        rightAxis.setDrawGridLines(false);

        // create the legend for bar chart
        Legend legend = inputBarChart.getLegend();
        legend.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        legend.setWordWrapEnabled(true);
        legend.setFormSize(8f);
        legend.setXEntrySpace(4f);

        // set the data for the bar chart using the getBarChartData(CropData) method
        // the only way for a chart to render is to invalidate it
        BarData costData = getBarChartData(cropStatsArrayList.get(0), cropStatsArrayList.get(1));
        inputBarChart.setData(costData);
        inputBarChart.invalidate();


        //TODO:Make a second invisible piechart in the same location as the original piechart
        //TODO:that allows the user to toggle from the default to custom views
        PieChart pieCostChart = (PieChart) findViewById(R.id.cornInputsPieChart);
        pieCostChart.setDescription("");

        // pie data used for the pie chart that overlays the horizontal bar chart
        PieData pieData = getPieData(REGIONAL_AVERAGE);
        pieCostChart.setData(pieData);

        // set the legend for the piechart
        Legend lPieCostChart = pieCostChart.getLegend();
        lPieCostChart.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        lPieCostChart.setWordWrapEnabled(true);
        lPieCostChart.setFormSize(8f);
        lPieCostChart.setXEntrySpace(0f);

        pieCostChart.setCenterText("Regional Values");

        pieCostChart.invalidate();

        pieCostChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {

                // need to get the chart in order to change it
                // need to reorder the result data to mimmick the pie data
                // need to set the string available when searching through the data
                PieChart pieChart1 = (PieChart) findViewById(R.id.cornInputsPieChart);
                ArrayList<CropStats.Result> result = REGIONAL_AVERAGE.getOrderedPieValues(REGIONAL_AVERAGE.getOrderedCostValues());


                // format number for currency
                NumberFormat nf = NumberFormat.getCurrencyInstance();
                String floats = nf.format(e.getVal());
                String total = nf.format(REGIONAL_AVERAGE.getTotalCost());
                pieChart1.setCenterText("Regional Cost: " + total + "\n" + REGIONAL_AVERAGE.getIndexString(result.get(e.getXIndex()).getIndex()) + "\n" + floats);


            }

            @Override
            public void onNothingSelected() {


                PieChart pieChart1 = (PieChart) findViewById(R.id.cornInputsPieChart);
                NumberFormat nf = NumberFormat.getCurrencyInstance();
                String total = nf.format(REGIONAL_AVERAGE.getTotalCost());
                pieChart1.setCenterText("Regional Cost: " + total);


            }
        });

        if (cropStatsArrayList.size() > 1) {
            PieChart pieCostChart1 = (PieChart) findViewById(R.id.cornInputsPieChart1);
            pieCostChart.setDescription("");

            CropStats customCrop = cropStatsArrayList.get(1);
            // pie data used for the pie chart that overlays the horizontal bar chart
            PieData pieData1 = getPieData(customCrop);
            pieCostChart1.setData(pieData1);

            // set the legend for the piechart
            Legend lPieChart = pieCostChart.getLegend();
            lPieChart.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
            lPieChart.setWordWrapEnabled(true);
            lPieChart.setFormSize(8f);
            lPieChart.setXEntrySpace(0f);

            pieCostChart1.setCenterText("Values from: " + cropStatsArrayList.get(1).getTitle());

            pieCostChart1.invalidate();

            pieCostChart1.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                @Override
                public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {


                    CropStats customCrop = cropStatsArrayList.get(1);

                    // need to get the chart in order to change it
                    // need to reorder the result data to mimmick the pie data
                    // need to set the string available when searching through the data
                    PieChart pieChart1 = (PieChart) findViewById(R.id.cornInputsPieChart1);
                    ArrayList<CropStats.Result> result = customCrop.getOrderedPieValues(customCrop.getOrderedCostValues());


                    // format number for currency
                    NumberFormat nf = NumberFormat.getCurrencyInstance();
                    String floats = nf.format(e.getVal());
                    String total = nf.format(customCrop.getTotalCost());
                    pieChart1.setCenterText("Custom Cost: " + total + "\n" + customCrop.getIndexString(result.get(e.getXIndex()).getIndex()) + "\n" + floats);


                }

                @Override
                public void onNothingSelected() {

                    CropStats customCrop = cropStatsArrayList.get(1);

                    PieChart pieChart1 = (PieChart) findViewById(R.id.cornInputsPieChart1);
                    NumberFormat nf = NumberFormat.getCurrencyInstance();
                    String total = nf.format(customCrop.getTotalCost());
                    pieChart1.setCenterText("Custom Cost: " + total);


                }
            });
        }


        /**
         * set the buttons at the bottom of the activity to do something when clicked
         */


        /**
         * with this, transfer the regional avg to the customcorn activity, so that the
         * values are set prior to allowing them to be edited
         */
        Button inputsButton = (Button) findViewById(R.id.modelEditValues);
        inputsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Model.this, Custom.class);
                i.putParcelableArrayListExtra("crops", cropStatsArrayList);
                startActivity(i);
            }
        });


        TextView linkCBOT = (TextView) findViewById(R.id.cornLinkCBOT);
        if (REGIONAL_AVERAGE.getCrop() == 0) {
            linkCBOT.setText(R.string.linkCBOTCorn);
        } else {
            linkCBOT.setText(R.string.linkCBOTSoy);
        }

        linkCBOT.setMovementMethod(LinkMovementMethod.getInstance());


        // if had already set a price for the bushel
        final EditText pricePerBushel = (EditText) findViewById(R.id.cornPriceBushel);
        if (cropStatsArrayList.get(1).getPrice() == 0) {

            pricePerBushel.setHint("Regional Average: " + REGIONAL_AVERAGE.getPrice());

        } else {

            pricePerBushel.setHint("" + cropStatsArrayList.get(1).getPrice());
            HorizontalBarChart returnsChart = getReturnsGraph(cropStatsArrayList);
            returnsChart.setOnChartValueSelectedListener(listener);
            HorizontalBarChart profitChart = getProfitGraph(cropStatsArrayList);
            profitChart.setOnChartValueSelectedListener(listener);


        }

        //Button profitButton = (Button) findViewById(R.id.cornReturnsButton);

        //EditText priceButton = (EditText) findViewById(R.id.cornPriceBushel);


        pricePerBushel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                // if the edittext has a value, make sure that if it's "$" that it doesn't crash the system
                // if string empty after changes, then set the hint

                String string = s.toString();
                if (!string.equalsIgnoreCase("")) {
                    float value = Float.valueOf(string);
                    try {
                        cropStatsArrayList.get(1).setPrice(value);
                        HorizontalBarChart returnsChart = getReturnsGraph(cropStatsArrayList);
                        returnsChart.setOnChartValueSelectedListener(listener);
                        HorizontalBarChart profitChart = getProfitGraph(cropStatsArrayList);
                        profitChart.setOnChartValueSelectedListener(listener);
                    } catch (Exception e) {
                        e.printStackTrace();
                        pricePerBushel.setHint("Enter custom crop values");

                    }
                }


            }

        });


        Button cornCommdityButton = (Button) findViewById(R.id.cornCommodityText);
        cornCommdityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alert = new AlertDialog.Builder(Model.this).create();
                alert.setTitle("Set commodity price");
                alert.setMessage("You must set the commodity prices for your own crop in order to visualize your own data to the regional averages");
                alert.setButton(AlertDialog.BUTTON_POSITIVE, "Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alert.show();
            }
        });


    }

    // this onchart listener works for both the returns and profits
    private OnChartValueSelectedListener listener = new OnChartValueSelectedListener() {
        @Override
        public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {


            String text = decimalFormatter(e.getVal());
            Toast.makeText(Model.this, text, Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onNothingSelected() {

        }
    };

    // formatting
    private String decimalFormatter(float value) {
        DecimalFormat formatter = new DecimalFormat("$#,###,###.##");
        return formatter.format(value);


    }


    /**
     * if toggled, display one or the other graphs (pie vs bar)
     *
     * @param v
     */
    public void onToggleClicked(View v) {
        ToggleButton toggle = (ToggleButton) findViewById(R.id.cornToggleButton);
        boolean on = toggle.isChecked();

        PieChart pie = (PieChart) findViewById(R.id.cornInputsPieChart);
        PieChart pie1 = (PieChart) findViewById(R.id.cornInputsPieChart1);
        HorizontalBarChart bar = (HorizontalBarChart) findViewById(R.id.cornInputsBarChart);

        if (on) {

            pie.setVisibility(View.INVISIBLE);
            pie1.setVisibility(View.INVISIBLE);
            bar.setVisibility(View.VISIBLE);
        } else if (!on) {
            pie.setVisibility(View.VISIBLE);
            pie1.setVisibility(View.VISIBLE);
            bar.setVisibility(View.INVISIBLE);
        }

    }

    /**
     * this method gets the data for bar charts inputs
     *
     * @param crop
     * @return
     */

    private BarData getBarChartData(CropStats crop) {

        ArrayList<BarEntry> entries = new ArrayList<BarEntry>();


        // ordered result, will get the order of the values from highest
        // to lowest.
        ArrayList<CropStats.Result> result = crop.getOrderedCostValues();

        // take the crop values and put them
        for (int i = 0; i < result.size(); i++) {
            entries.add(new BarEntry(result.get(i).getValue(), i));

        }

        //TODO: could make these two methods one (getIndices and geChartStrings)
        ArrayList<Integer> indices = crop.getIndices(result);
        ArrayList<String> xValues = getChartStrings(indices);

        BarDataSet barDataSet = new BarDataSet(entries, "Regional Average");
        barDataSet.setColor(Color.BLUE);
        barDataSet.setValueFormatter(new MyValueFormatter());
        barDataSet.setValueTextSize(8f);


        BarData barData = new BarData(xValues, barDataSet);


        return barData;

    }

    /**
     * create another getBarChartData() method to include another
     * data
     */

    private BarData getBarChartData(CropStats defaults, CropStats custom) {

        ArrayList<BarEntry> defaultEntries = new ArrayList<>();
        ArrayList<BarEntry> customEntries = new ArrayList<>();

        // get the ordered results for only the default values, that way you don't run the risk
        // of having the wrong amounts for the wrong labels in the custom results,
        // mostly because of the high probability of the high to low values being different for
        // the default and what a custom setting could show
        ArrayList<CropStats.Result> defaultResult = defaults.getOrderedCostValues();
        ArrayList<CropStats.Result> customResult = custom.getUnorderedCostValues();

        // set the barEntries based on the high to low concept
        for (int i = 0; i < defaultResult.size(); i++) {
            defaultEntries.add(new BarEntry(defaultResult.get(i).getValue(), i));
            Log.d("default", defaultResult.get(i).getIndex() + "");
        }

        // set these values to mimic the previous setting
        // get the ordered result, and it's original index
        for (int i = 0; i < customResult.size(); i++) {
            int index = defaultResult.get(i).getIndex();
            customEntries.add(new BarEntry(customResult.get(index).getValue(), i));
            Log.d("custom", customResult.get(i).getIndex() + "");

        }


        // these two methods are made to set the x axis
        ArrayList<Integer> indices = defaults.getIndices(defaultResult);
        ArrayList<String> xValues = getChartStrings(indices);

        BarDataSet defaultDataSet = new BarDataSet(defaultEntries, "Region Average");
        defaultDataSet.setColor(Color.BLUE);
        defaultDataSet.setValueFormatter(new MyValueFormatter());
        defaultDataSet.setValueTextSize(8f);

        BarDataSet customDataSet = new BarDataSet(customEntries, "Custom Values");
        customDataSet.setColor(Color.RED);
        customDataSet.setValueFormatter(new MyValueFormatter());
        customDataSet.setValueTextSize(8f);

        List<BarDataSet> barDataSets = new ArrayList<>();
        barDataSets.add(customDataSet);
        barDataSets.add(defaultDataSet);


        BarData barData = new BarData(xValues, barDataSets);

        return barData;
        //TODO: create the bardatasets and have different colors


    }


    private PieData getPieData(CropStats crop) {


        /**
         * make sure the indexes of none of the entries are the same,
         * even if they are comparisons, because it will not show on
         *
         * also make sure that the slices that are zero, not be included
         */


        ArrayList<CropStats.Result> result = crop.getOrderedCostValues();
        ArrayList<CropStats.Result> pieResult = crop.getOrderedPieValues(result);
        ArrayList<Float> floatValues = crop.getFloatValues(pieResult);
        ArrayList<Integer> indexValues = crop.getIndices(pieResult);
        ArrayList<Entry> entries1 = new ArrayList<>();
        ArrayList<String> stringPie = getChartStrings(indexValues);


        for (int a = 0; a < floatValues.size(); a++) {
            entries1.add(new Entry(floatValues.get(a), a));

        }


        //TODO: index size 13, stringPie size 12
        //// TODO: figure out the issues


        PieDataSet pieDataSet = new PieDataSet(entries1, "");
        pieDataSet.setColors(getPieColors());
        pieDataSet.setValueFormatter(new MyValueFormatter());
        pieDataSet.setValueTextSize(8f);
        pieDataSet.setSliceSpace(5f);
        pieDataSet.setHighlightEnabled(true);


        PieData pieData = new PieData(stringPie, pieDataSet);


        return pieData;

    }

    private ArrayList<String> getChartStrings(ArrayList<Integer> entryArray) {

        ArrayList<String> entries = new ArrayList<>();


        /**
         * make sure the indexes of none of the entries are the same,
         * even if they are comparisons, because it will not show on
         */

        for (int i = 0; i < entryArray.size(); i++) {

            int entry = entryArray.get(i);


            switch (entry) {
                case 0:
                    entries.add("Seed");
                    break;
                case 1:
                    entries.add("Fertilizer");
                    break;
                case 2:
                    entries.add("Chemicals");
                    break;
                case 3:
                    entries.add("Custom Operations");
                    break;
                case 4:
                    entries.add("Fuel");
                    break;
                case 5:
                    entries.add("Repairs");
                    break;
                case 6:
                    entries.add("Irrigation/Water");
                    break;
                case 7:
                    entries.add("Interest on Cap");
                    break;
                case 8:
                    entries.add("Hired Labor");
                    break;
                case 9:
                    entries.add("Cost of Unpaid Labor");
                    break;
                case 10:
                    entries.add("Recovery of Equipment");
                    break;
                case 11:
                    entries.add("Cost of Rental of Land");
                    break;
                case 12:
                    entries.add("Taxes & Insurance");
            }


        }


        return entries;

    }


    // these colors are used for both of the pie charts
    private ArrayList<Integer> getPieColors() {
        ArrayList<Integer> colors = new ArrayList<>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        return colors;
    }


    // create the
    private HorizontalBarChart getReturnsGraph(ArrayList<CropStats> cropStatsArrayList) {
        // horizontal bar chart used for visualizing returns
        HorizontalBarChart returnsChart = (HorizontalBarChart) findViewById(R.id.cornReturnsChart);

        XAxis xAxis1 = returnsChart.getXAxis();
        xAxis1.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis1.setDrawAxisLine(true);
        xAxis1.setDrawGridLines(true);
        xAxis1.setGridLineWidth(0.3f);


        YAxis leftAxis1 = returnsChart.getAxisLeft();
        leftAxis1.setDrawAxisLine(true);
        leftAxis1.setDrawGridLines(false);
        leftAxis1.setGridLineWidth(0.3f);


        YAxis rightAxis1 = returnsChart.getAxisRight();
        rightAxis1.setDrawAxisLine(true);
        rightAxis1.setDrawGridLines(false);

        returnsChart.setDrawBarShadow(true);
        returnsChart.setDrawValueAboveBar(true);
        returnsChart.setDescription("");
        returnsChart.setPinchZoom(false);
        returnsChart.setDrawGridBackground(true);


        ArrayList<BarEntry> defaultReturnEntry = new ArrayList<>();
        ArrayList<BarEntry> customReturnEntry = new ArrayList<>();
        ArrayList<String> returnStrings = new ArrayList<>();
        ArrayList<BarDataSet> returnsDataSet = new ArrayList<>();
        returnStrings.add("Grain");

        Log.d("reg return", cropStatsArrayList.get(0).getTotalReturns() + "");
        Log.d("custom return", cropStatsArrayList.get(1).getTotalReturns() + "");
        if (cropStatsArrayList.size() >= 1) {
            defaultReturnEntry.add(new BarEntry(cropStatsArrayList.get(0).getTotalReturns(), 0));
            BarDataSet defaultDataSet = new BarDataSet(defaultReturnEntry, "Region Avg");
            defaultDataSet.setValueFormatter(new MyValueFormatter());
            defaultDataSet.setColor(Color.BLUE);
            returnsDataSet.add(defaultDataSet);


        }
        if (cropStatsArrayList.size() >= 2) {
            customReturnEntry.add(new BarEntry(cropStatsArrayList.get(1).getTotalReturns(), 0));
            BarDataSet customDataSet = new BarDataSet(customReturnEntry, "Custom");
            customDataSet.setValueFormatter(new MyValueFormatter());
            customDataSet.setColor(Color.RED);
            returnsDataSet.add(customDataSet);
        }


        BarData returnData = new BarData(returnStrings, returnsDataSet);
        returnData.setValueFormatter(new MyValueFormatter());
        returnsChart.setData(returnData);

        Legend returnLegend = returnsChart.getLegend();
        returnLegend.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        returnLegend.setWordWrapEnabled(true);
        returnLegend.setFormSize(8f);
        returnLegend.setXEntrySpace(1f);

        returnsChart.invalidate();

        return returnsChart;
    }

    private HorizontalBarChart getProfitGraph(ArrayList<CropStats> cropStatsArrayList) {
        // horizontal bar chart used for visualizing returns
        HorizontalBarChart profitChart = (HorizontalBarChart) findViewById(R.id.modelChart);

        XAxis xAxis1 = profitChart.getXAxis();
        xAxis1.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis1.setDrawAxisLine(true);
        xAxis1.setDrawGridLines(true);
        xAxis1.setGridLineWidth(0.3f);


        YAxis leftAxis1 = profitChart.getAxisLeft();
        leftAxis1.setDrawAxisLine(true);
        leftAxis1.setDrawGridLines(false);
        leftAxis1.setGridLineWidth(0.3f);


        YAxis rightAxis1 = profitChart.getAxisRight();
        rightAxis1.setDrawAxisLine(true);
        rightAxis1.setDrawGridLines(false);

        profitChart.setDrawBarShadow(true);
        profitChart.setDrawValueAboveBar(true);
        profitChart.setDescription("");
        profitChart.setPinchZoom(false);
        profitChart.setDrawGridBackground(true);


        ArrayList<BarEntry> defaultReturnEntry = new ArrayList<>();
        ArrayList<BarEntry> customReturnEntry = new ArrayList<>();
        ArrayList<String> returnStrings = new ArrayList<>();
        ArrayList<BarDataSet> returnsDataSet = new ArrayList<>();
        returnStrings.add("Grain");

        Log.d("region return", cropStatsArrayList.get(0).getTotalReturns() + "");
        Log.d("custom return", cropStatsArrayList.get(1).getTotalReturns() + "");
        if (cropStatsArrayList.size() >= 1) {
            defaultReturnEntry.add(new BarEntry(cropStatsArrayList.get(0).getProfitFloat(), 0));
            BarDataSet defaultDataSet = new BarDataSet(defaultReturnEntry, "Region Avg");
            defaultDataSet.setValueFormatter(new MyValueFormatter());
            defaultDataSet.setColor(Color.BLUE);
            returnsDataSet.add(defaultDataSet);


        }
        if (cropStatsArrayList.size() >= 2) {
            Log.d("custom returns", cropStatsArrayList.get(1).getTotalReturns() + "");
            Log.d("custom cost", cropStatsArrayList.get(1).getTotalCost() + "");
            Log.d("custom profit", cropStatsArrayList.get(1).getProfitFloat() + "");
            customReturnEntry.add(new BarEntry(cropStatsArrayList.get(1).getProfitFloat(), 0));
            BarDataSet customDataSet = new BarDataSet(customReturnEntry, "Custom");
            customDataSet.setValueFormatter(new MyValueFormatter());
            customDataSet.setColor(Color.RED);
            returnsDataSet.add(customDataSet);
        }


        BarData returnData = new BarData(returnStrings, returnsDataSet);
        returnData.setValueFormatter(new MyValueFormatter());
        profitChart.setData(returnData);

        Legend returnLegend = profitChart.getLegend();
        returnLegend.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        returnLegend.setWordWrapEnabled(true);
        returnLegend.setFormSize(8f);
        returnLegend.setXEntrySpace(1f);

        profitChart.invalidate();

        return profitChart;
    }


}