package com.example.fernando.farmingfarming;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CornProfit extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_corn_profit);

        Bundle bundle = getIntent().getExtras();
        // get the extras from the previous intent
        final int region = bundle.getInt("region");
        final int crop = bundle.getInt("crop");
        Log.d("region CORNPROFIT", "" + region);
        Log.d("crop CORNPROFIT", "" + crop);

        CropStats corn = new CropStats(crop,region);


        TextView gross = (TextView) findViewById(R.id.cornProfitGrossAmt);
        TextView profit = (TextView) findViewById(R.id.cornProfitProfitAmt);
        TextView cost = (TextView) findViewById(R.id.cornProfitCostAmt);
        TextView info = (TextView) findViewById(R.id.cornProfitChosenParams);
        TextView title = (TextView) findViewById(R.id.cornProfitTitle);


        Button execute = (Button) findViewById(R.id.cornProfitExecuteButton);
        Button variables = (Button) findViewById(R.id.cornProfitChangeButton);

        variables.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(CornProfit.this, ChangeVariables.class);
                i.putExtra("crop",crop);
                i.putExtra("region",region);
                startActivity(i);
            }
        });

        execute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(CornProfit.this, Profit.class);
                startActivity(i);
            }
        });


        gross.setText("Total Gross: " + corn.getTotalProduct());
        gross.append("\nPrimary Product: "+corn.getPrimaryProduct());
        gross.append("\nSecondary Product: "+corn.getSecondaryProduct());

        profit.setText("Profit: "+corn.getProfit());
        profit.append("\nTotal Product: "+corn.getTotalProduct());
        profit.append("\nTotal Cost: "+corn.getTotalCost());

        cost.setText("Total Cost: " + corn.getTotalCost());
        cost.append("\nSeed: " + corn.getSeed());
        cost.append("\nFertilizer: " + corn.getFertilizer());
        cost.append("\nTractor Lube: " + corn.getFueLubeElec());
        cost.append("\nLabor: " + corn.getHiredLabor());
        cost.append("\nWater: " + corn.getIrrigationWater());




        title.setText(corn.getCropName() + " for " + corn.getRegionName());
        info.setText("This shows 2014 data for " + corn.getCropName() + " in the " + corn.getRegionName() + " " +
                " This does not include most of the overhead/operating costs");



    }

}
