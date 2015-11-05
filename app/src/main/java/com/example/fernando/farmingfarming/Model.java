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


        // check for the togle button, and alternate between graphs being visible/ invisible
        final ToggleButton toggle = (ToggleButton) findViewById(R.id.modelToggleButton);
        toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean on = toggle.isChecked();

                PieChart region = (PieChart) findViewById(R.id.modelRegionPieChart);
                PieChart custom = (PieChart) findViewById(R.id.modelCustomPieChart);
                HorizontalBarChart bar = (HorizontalBarChart) findViewById(R.id.modelInputsBarChart);

                if (on) {

                    region.setVisibility(View.INVISIBLE);
                    custom.setVisibility(View.INVISIBLE);
                    bar.setVisibility(View.VISIBLE);
                } else if (!on) {
                    region.setVisibility(View.VISIBLE);
                    custom.setVisibility(View.VISIBLE);
                    bar.setVisibility(View.INVISIBLE);
                }

            }
        });


        Bundle bundle = getIntent().getExtras();

        // get parcelable list of cropstats from the sqlite database
        final ArrayList<CropStats> cropStatsArrayList = bundle.getParcelableArrayList("crops");
        final CropStats REGIONAL_AVERAGE = cropStatsArrayList.get(0);

        /**
         * with this, transfer the regional avg to the custom activity, so that the
         * values are set prior to allowing them to be edited
         */
        Button activityModelToCustom = (Button) findViewById(R.id.modelEditValues);
        activityModelToCustom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Model.this, Custom.class);
                i.putParcelableArrayListExtra("crops", cropStatsArrayList);
                startActivity(i);
            }
        });


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
        HorizontalBarChart inputBarChart = (HorizontalBarChart) findViewById(R.id.modelInputsBarChart);
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
        inputBarChart.setOnChartValueSelectedListener(listener);
        inputBarChart.invalidate();

        // the render method creates the and generates the chart
        renderPieValues(cropStatsArrayList);

        // set the text for the inputs analysis
        inputsAnalysis(cropStatsArrayList);

        profitAnalysis(cropStatsArrayList);


        /**
         * set the buttons at the bottom of the activity to do something when clicked
         */


        // have a different link if soybean or corn chosen
        TextView linkCBOT = (TextView) findViewById(R.id.modelLinkCBOT);
        if (REGIONAL_AVERAGE.getCrop() == 0) {
            linkCBOT.setText(R.string.linkCBOTCorn);
        } else {
            linkCBOT.setText(R.string.linkCBOTSoy);
        }

        linkCBOT.setMovementMethod(LinkMovementMethod.getInstance());


        // if had already set a price for the bushel
        final EditText pricePerBushel = (EditText) findViewById(R.id.modelPriceBushel);
        if (cropStatsArrayList.get(1).getPrice() == cropStatsArrayList.get(0).getPrice()) {

            Log.d("price reg", cropStatsArrayList.get(0).getPrice() + "");
            Log.d("price custom", cropStatsArrayList.get(1).getPrice() + "");

            pricePerBushel.setHint("Regional Average: " + REGIONAL_AVERAGE.getPrice());

        } else {

            pricePerBushel.setHint("" + cropStatsArrayList.get(1).getPrice());


            Log.d("price reg", cropStatsArrayList.get(0).getPrice() + "");
            Log.d("price custom", cropStatsArrayList.get(1).getPrice() + "");


        }

        // create the listeners for the graphs
        HorizontalBarChart returnsChart = getReturnsGraph(cropStatsArrayList);
        returnsChart.setOnChartValueSelectedListener(listener);
        HorizontalBarChart profitChart = getProfitGraph(cropStatsArrayList);
        profitChart.setOnChartValueSelectedListener(listener);


        // set a text watcher for the price per bushel
        pricePerBushel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                String string = s.toString();

                // if the edittext has a value, make sure that if it's "$" that it doesn't crash the system
                // if string empty after changes, then set the hint
                if (!string.equalsIgnoreCase("")) {
                    float value = Float.valueOf(string);
                    try {
                        cropStatsArrayList.get(1).setPrice(value);
                        HorizontalBarChart returnsChart = getReturnsGraph(cropStatsArrayList);
                        returnsChart.setOnChartValueSelectedListener(listener);
                        HorizontalBarChart profitChart = getProfitGraph(cropStatsArrayList);
                        profitChart.setOnChartValueSelectedListener(listener);

                        DatabaseStats dbStats = new DatabaseStats(getApplicationContext());
                        dbStats.updateCrop(cropStatsArrayList.get(1), cropStatsArrayList.get(1).getTitle());


                    } catch (Exception e) {
                        e.printStackTrace();
                        pricePerBushel.setHint("Enter custom crop values");


                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {


                profitAnalysis(cropStatsArrayList);


            }

        });


        /**
         * this section is dialogs for each of the headings
         * inputs, commodity costs, returns, profits
         */

        // this popup will give people a little information about the inputs
        Button inputsButton = (Button) findViewById(R.id.modelInputsButton);
        inputsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(Model.this).create();
                dialog.setTitle("Inputs");
                dialog.setMessage("There are 3 different graphs. The pie graphs are the default view."
                        + "\n\nThese pie graphs show what the proportion of costs compared to total cost for both the regional average and for your custom values"
                        + "\n\nThe bar graphs are accessed by pressing the toggle button"
                        + "\n\nThis graph shows the comparison between the regional average and your values for each of the input categories");
                dialog.setButton(AlertDialog.BUTTON_POSITIVE, "Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });


        // just a little popup if they click the heading for model commodity pricing
        Button modelCommdityButton = (Button) findViewById(R.id.modelCommodityText);
        modelCommdityButton.setOnClickListener(new View.OnClickListener() {
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

        // a little popup that explains what the returns graph is
        Button returnsButton = (Button) findViewById(R.id.modelReturnsButton);
        returnsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(Model.this).create();
                dialog.setTitle("Returns");
                dialog.setMessage("This bar chart shows the values of your return simply"
                        + " based on the price at which you sold you crop and your yield per acre");
                dialog.setButton(AlertDialog.BUTTON_POSITIVE, "Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        // a little popup that explains what the profit graph is
        Button profitButton = (Button) findViewById(R.id.modelProfitButton);
        profitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(Model.this).create();
                dialog.setTitle("Profit");
                dialog.setMessage("This bar chart shows the values of your profit simply"
                        + " based on the price at which the returns minus inputs per acre.");
                dialog.setButton(AlertDialog.BUTTON_POSITIVE, "Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });


    }

    // add some text that explains returns
    private void profitAnalysis(ArrayList<CropStats> cropStats) {

        TextView proftText = (TextView) findViewById(R.id.modelProfitAnalysis);

        CropStats region = cropStats.get(0);
        CropStats custom = cropStats.get(1);

        String string = "Profit Analysis:\n\n";

        // first find out if they are making a profit
        if (custom.getProfitFloat() > 0) {

            string = string + "Congratulations on turning a profit!\n\n";

            // if they are, check if their region is also profitable
            if (region.getProfitFloat() > 0) {

                string = string + "It looks as though you are making ";

                // check if they are profiting better than their region
                if (region.getProfitFloat() > custom.getProfitFloat()) {

                    string = string + (region.getProfitFloat() - custom.getProfitFloat()) + " less than your region's average."
                            + "Your bottom line looks good";


                } else if (region.getProfitFloat() < custom.getProfitFloat()) {

                    string = string + (custom.getProfitFloat() - region.getProfitFloat()) + " more than your region's average. You turned a profit"
                            + "and beat the region average, it takes a great business mind to do so. Your bottom line looks very good!";


                }

            } else if (region.getProfitFloat() < 0) {

                string = string + "Not only did you earn a profit, but you did it in a region that is losing money per acre at " + region.getProfitFloat()
                        + " per acre. It takes a great business mind to do so. Again, Congratulations!!!";

            } else {

                string = string + " about the same as your region.";


            }

        } else if (custom.getProfitFloat() < 0) {

            string = string + "Unfortunately your farm is losing money.\n\n";

            // if they are, check if their region is also profitable
            if (region.getProfitFloat() > 0) {


                string = string + "It looks as though your region is a profitable region. Maybe you should look at making a few input changes. " +
                        "There are studies that show that reducing inputs may lower your yield but lower your costs exponentially per acre. " +
                        "Hopefully this will help your bottom line.";


            } else if (region.getProfitFloat() < 0) {

                string = string + "It looks as though you are making ";

                // check if they are profiting better than their region
                if (region.getProfitFloat() > custom.getProfitFloat()) {

                    string = string + (region.getProfitFloat() - custom.getProfitFloat()) + " less than your region's average."
                            + "Maybe you should look at making a few input changes." +
                            "There are studies that show that reducing inputs may lower your yield but lower your costs exponentially per acre. Hopefully this helps your bottom line";


                } else if (region.getProfitFloat() < custom.getProfitFloat()) {

                    string = string + (custom.getProfitFloat() - region.getProfitFloat()) + " more than your region's average." +
                            "There are studies that show that reducing inputs may lower your yield but lower your costs exponentially per acre. Hopefully this helps your bottom line";


                }


            } else {

                string = string + " about the same as your region." +
                        "There are studies that show that reducing inputs may lower your yield but lower your costs exponentially per acre. Hopefully this helps your bottom line";


            }

        } else {

            string = string + "It looks as though you haven't set any custom values. If you did set some values, either you are right on the average values for your region "+
                    "or there is some errors within this program. Let the developer of this application know by submitting a review on the app store reviews. Thanks!";


        }

        proftText.setText(string);

    }


    // add a snippet of an analysis regarding the inputs
    private void inputsAnalysis(ArrayList<CropStats> cropStats) {

        // access the textview
        TextView view = (TextView) findViewById(R.id.modelInputsAnalysis);

        //create the regional and custom values
        CropStats region = cropStats.get(0);
        CropStats custom = cropStats.get(1);

        Log.d("region cost", "" + region.getTotalCost());
        Log.d("custom cost", "" + custom.getTotalCost());
        Log.d("custom overhead", "" + custom.getTotalOverheadCost());
        Log.d("custom operational", "" + custom.getTotalOperationalCosts());
        Log.d("custom misc", "" + custom.getMiscellaneous());

        String string = "Cost Analysis: \n\n";


        // if region costs are greater than the custom costs
        if (region.getTotalCost() > custom.getTotalCost()) {


            string = string + "The regional cost per acre is actually higher than what you spent overall. \n\n";
            string = string + "Your cost: $" + custom.getTotalCost() + ", regional cost: $" + region.getTotalCost();

            view.setText(string);

        }

        // if custom costs are greate than the regional costs
        else if (region.getTotalCost() < custom.getTotalCost()) {

            string = string + "Unfortunately it seems you are spending more per acre than your region. \n\n";
            string = string + "Your cost: $" + custom.getTotalCost() + ", regional cost: $" + region.getTotalCost();

            view.setText(string);

        } else {

            string = "You are spending the same as your region we think! Or you have not set your own values. Go and set your own values!";
            view.setText(string);

        }


    }

    /**
     * this method takes the 2 cropstats are rendered into their pie values
     * this creates two different pie charts, one for the regional averages
     * and one for the custom values
     *
     * @param cropStats
     */

    private void renderPieValues(ArrayList<CropStats> cropStats) {


        /**
         * make sure the indexes of none of the entries are the same,
         * even if they are comparisons, because it will not show on
         *
         * also make sure that the slices that are zero, not be included
         */

        final CropStats region = cropStats.get(0);


        ArrayList<CropStats.Result> regionResult = region.getOrderedCostValues();
        ArrayList<CropStats.Result> pieRegionResult = region.getOrderedPieValues(regionResult);
        ArrayList<Float> regionFloatValues = region.getFloatValues(pieRegionResult);
        ArrayList<Integer> regionIndexValues = region.getIndices(pieRegionResult);
        ArrayList<Entry> regionEntries = new ArrayList<>();
        ArrayList<String> regionStringPie = getChartStrings(regionIndexValues);


        for (int a = 0; a < regionFloatValues.size(); a++) {
            regionEntries.add(new Entry(regionFloatValues.get(a), a));
        }


        PieDataSet regionPieDataSet = new PieDataSet(regionEntries, "");
        regionPieDataSet.setColors(getPieColors());
        regionPieDataSet.setValueFormatter(new MyValueFormatter());
        regionPieDataSet.setValueTextSize(8f);
        regionPieDataSet.setSliceSpace(5f);
        regionPieDataSet.setHighlightEnabled(true);


        PieData regionPieData = new PieData(regionStringPie, regionPieDataSet);

        PieChart pieRegionChart = (PieChart) findViewById(R.id.modelRegionPieChart);

        pieRegionChart.setData(regionPieData);

        Legend regionLegend = pieRegionChart.getLegend();
        regionLegend.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        regionLegend.setWordWrapEnabled(true);
        regionLegend.setFormSize(8f);
        regionLegend.setXEntrySpace(0f);

        pieRegionChart.setCenterText("Regional Values");

        pieRegionChart.invalidate();

        pieRegionChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {

            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {


                // need to get the chart in order to change it
                // need to reorder the result data to mimmick the pie data
                // need to set the string available when searching through the data
                PieChart pieChart = (PieChart) findViewById(R.id.modelRegionPieChart);
                ArrayList<CropStats.Result> result = region.getOrderedPieValues(region.getOrderedCostValues());


                // format number for currency
                NumberFormat nf = NumberFormat.getCurrencyInstance();
                String floats = nf.format(e.getVal());
                String total = nf.format(region.getTotalCost());
                pieChart.setCenterText("Regional Average: " + total + "\n" + region.getIndexString(result.get(e.getXIndex()).getIndex()) + "\n" + floats);


            }

            @Override
            public void onNothingSelected() {


                PieChart pieChart1 = (PieChart) findViewById(R.id.modelRegionPieChart);
                NumberFormat nf = NumberFormat.getCurrencyInstance();
                String total = nf.format(region.getTotalCost());
                pieChart1.setCenterText("Regional Average: " + total);


            }
        });


        /**
         * create the chart for the custom pie chart
         * //TODO: make sure that the pie legent values are wrapped
         */

        final CropStats custom = cropStats.get(1);

        ArrayList<CropStats.Result> customResult = custom.getOrderedCostValues();
        ArrayList<CropStats.Result> pieCustomResult = custom.getOrderedPieValues(customResult);
        ArrayList<Float> customFloatValues = region.getFloatValues(pieCustomResult);
        ArrayList<Integer> customIndexValues = region.getIndices(pieCustomResult);
        ArrayList<Entry> customEntries = new ArrayList<>();
        ArrayList<String> customStringPie = getChartStrings(customIndexValues);

        for (int a = 0; a < customFloatValues.size(); a++) {
            customEntries.add(new Entry(customFloatValues.get(a), a));
        }


        PieDataSet customPieDataSet = new PieDataSet(regionEntries, "");
        customPieDataSet.setColors(getPieColors());
        customPieDataSet.setValueFormatter(new MyValueFormatter());
        customPieDataSet.setValueTextSize(8f);
        customPieDataSet.setSliceSpace(5f);
        customPieDataSet.setHighlightEnabled(true);


        PieData customPieData = new PieData(customStringPie, regionPieDataSet);

        PieChart pieCustomChart = (PieChart) findViewById(R.id.modelCustomPieChart);

        pieCustomChart.setData(customPieData);

        Legend customLegend = pieCustomChart.getLegend();
        customLegend.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        customLegend.setWordWrapEnabled(true);
        customLegend.setFormSize(8f);
        customLegend.setXEntrySpace(0f);

        pieCustomChart.setCenterText("Values from: " + custom.getTitle());

        pieCustomChart.invalidate();

        pieCustomChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {

            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {


                // need to get the chart in order to change it
                // need to reorder the result data to mimmick the pie data
                // need to set the string available when searching through the data
                PieChart pieChart = (PieChart) findViewById(R.id.modelCustomPieChart);
                ArrayList<CropStats.Result> result = custom.getOrderedPieValues(custom.getOrderedCostValues());


                // format number for currency
                NumberFormat nf = NumberFormat.getCurrencyInstance();
                String floats = nf.format(e.getVal());
                String total = nf.format(custom.getTotalCost());
                pieChart.setCenterText("Custom Cost: " + total + "\n" + custom.getIndexString(result.get(e.getXIndex()).getIndex()) + "\n" + floats);


            }

            @Override
            public void onNothingSelected() {


                PieChart pieChart1 = (PieChart) findViewById(R.id.modelCustomPieChart);
                NumberFormat nf = NumberFormat.getCurrencyInstance();
                String total = nf.format(custom.getTotalCost());
                pieChart1.setCenterText("Custom Cost: " + total);


            }
        });


    }


    /**
     * this section of the code is the individual methods made.
     */


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
        }

        // set these values to mimic the previous setting
        // get the ordered result, and it's original index
        for (int i = 0; i < customResult.size(); i++) {
            int index = defaultResult.get(i).getIndex();
            customEntries.add(new BarEntry(customResult.get(index).getValue(), i));

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
        HorizontalBarChart returnsChart = (HorizontalBarChart) findViewById(R.id.modelReturnsChart);

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
            BarDataSet defaultDataSet = new BarDataSet(defaultReturnEntry, "Region Avg ");
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
        HorizontalBarChart profitChart = (HorizontalBarChart) findViewById(R.id.modelProfitChart);

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


        if (cropStatsArrayList.size() >= 1) {
            defaultReturnEntry.add(new BarEntry(cropStatsArrayList.get(0).getProfitFloat(), 0));
            BarDataSet defaultDataSet = new BarDataSet(defaultReturnEntry, "Region Avg ");
            defaultDataSet.setValueFormatter(new MyValueFormatter());
            defaultDataSet.setColor(Color.BLUE);
            returnsDataSet.add(defaultDataSet);


        }
        if (cropStatsArrayList.size() >= 2) {

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