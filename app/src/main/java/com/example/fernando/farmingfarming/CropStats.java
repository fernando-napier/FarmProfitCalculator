package com.example.fernando.farmingfarming;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.daimajia.slider.library.Tricks.ViewPagerEx;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by fernando on 8/24/15.
 * <p/>
 * to use for the time being
 * so as to not use the server data
 * and as an example of what the app
 * will be able to do
 */
public class CropStats implements Parcelable{

    private int id;
    private int region;
    private int crop;
    private String regionName;
    private String cropName;

    // gross value of Returnsion
    private float primaryReturns;
    private float secondaryReturns;
    private float totalReturns;

    // operating costs
    private float seed; //0
    private float fertilizer; //1
    private float chemicals; //2
    private float customOps; //3
    private float fueLubeElec; //4
    private float repairs; //5
    private float irrigationWater; //6
    private float intOnCap; //7
    private float totalOperationalCosts; //8

    //overhead costs
    private float hiredLabor; //9
    private float costUnpaidLabor; //10
    private float capRecoveryOfEquip; //11
    private float costOfLandRentalRate; //12
    private float taxesInsurance; //13
    private float generalFarmOverhead; //14
    private float totalOverheadCost; //15

    private float miscellaneous;

    //total cost
    private float totalCost;

    //supporting information

    //yield and break even used for the graph
    private int yield;
    private int yieldBreakEven;

    private float price;
    private int sizeAcresPlanted;


    //for setting customizable variables
    private String title;


    /**
     * 7 regions
     * 3 or 4 crops
     *
     * @param regionID
     * @param cropID   this shows the default for specifically corn
     */

    public CropStats(int cropID, int regionID) {

        region = regionID;
        crop = cropID;
        getRegionalAverage();
        title = null;


    }

    /**
     * this constructor is used when retrieving a data stored in the DatabaseStats class
     * this is also where setting the data is important
     */
    public CropStats() {


    }


    /**
     * getStats() method is used to get the default values for corn for each region
     */

    private void getRegionalAverage() {

        String intermediate = crop + "" + region;


        /**
         * corn class will
         */


        switch (intermediate) {

            //corn usa
            case "00":

                regionName = "United States";
                cropName = "Corn";

                primaryReturns = (float) 601.80;
                secondaryReturns = (float) 1.38;
                totalReturns = (float) 603.18;

                // operating costs
                seed = (float) 101.04;
                fertilizer = (float) 149.23;
                chemicals = (float) 28.57;
                customOps = (float) 17.77;
                fueLubeElec = (float) 32.27;
                repairs = (float) 25.79;
                irrigationWater = (float) 0.12;
                intOnCap = (float) 0.12;
                totalOperationalCosts = (float) 356.92;

                //overhead costs
                hiredLabor = (float) 3.16;
                costUnpaidLabor = (float) 24.75;
                capRecoveryOfEquip = (float) 99.25;
                costOfLandRentalRate = (float) 175.60;
                taxesInsurance = (float) 9.34;
                generalFarmOverhead = (float) 19.88;
                totalOverheadCost = (float) 331.98;

                //extra costs
                miscellaneous = (float) 0.00;

                //total cost
                totalCost = (float) 688.90;

                //supporting information
                yield = 170;
                price = (float) 3.54;

                //not quite important to the app
                sizeAcresPlanted = 280;


                break;

            //corn heartland
            case "01":

                regionName = "HeartLand";
                cropName = "Corn";

                primaryReturns = (float) 628.29;
                secondaryReturns = (float) 0.29;
                totalReturns = (float) 628.58;

                // operating costs
                seed = (float) 108.41;
                fertilizer = (float) 156.78;
                chemicals = (float) 29.94;
                customOps = (float) 16.93;
                fueLubeElec = (float) 28.20;
                repairs = (float) 23.79;
                irrigationWater = (float) 0;
                intOnCap = (float) 0.12;
                totalOperationalCosts = (float) 364.17;

                //overhead costs
                hiredLabor = (float) 2.86;
                costUnpaidLabor = (float) 22.17;
                capRecoveryOfEquip = (float) 95.64;
                costOfLandRentalRate = (float) 208.03;
                taxesInsurance = (float) 8.58;
                generalFarmOverhead = (float) 18.98;
                totalOverheadCost = (float) 356.26;

                //extra costs
                miscellaneous = (float) 0.00;

                //total cost
                totalCost = (float) 720.43;

                //supporting information
                yield = 179;
                price = (float) 3.51;

                //not quite important to the app
                sizeAcresPlanted = 313;


                break;

            //corn northern crescent
            case "02":

                regionName = "Northern Crescent";
                cropName = "Corn";

                primaryReturns = (float) 558.48;
                secondaryReturns = (float) 6.95;
                totalReturns = (float) 565.43;

                // operating costs
                seed = (float) 93.01;
                fertilizer = (float) 162.11;
                chemicals = (float) 28.56;
                customOps = (float) 22.51;
                fueLubeElec = (float) 29.68;
                repairs = (float) 25.77;
                irrigationWater = (float) 0.00;
                intOnCap = (float) 0.12;
                totalOperationalCosts = (float) 361.76;

                //overhead costs
                hiredLabor = (float) 3.94;
                costUnpaidLabor = (float) 32.89;
                capRecoveryOfEquip = (float) 87.03;
                costOfLandRentalRate = (float) 114.28;
                taxesInsurance = (float) 10.03;
                generalFarmOverhead = (float) 26.07;
                totalOverheadCost = (float) 274.24;

                //extra costs
                miscellaneous = (float) 0.00;

                //total cost
                totalCost = (float) 636.00;

                //supporting information
                yield = 156;
                price = (float) 3.58;

                //not quite important to the app
                sizeAcresPlanted = 146;

                break;

            //corn northern great plains
            case "03":

                regionName = "Northern Great Plains";
                cropName = "Corn";

                primaryReturns = (float) 419.42;
                secondaryReturns = (float) 1.80;
                totalReturns = (float) 421.22;

                // operating costs
                seed = (float) 98.90;
                fertilizer = (float) 125.96;
                chemicals = (float) 20.38;
                customOps = (float) 17.94;
                fueLubeElec = (float) 32.83;
                repairs = (float) 29.36;
                irrigationWater = (float) 0.83;
                intOnCap = (float) 0.10;
                totalOperationalCosts = (float) 326.30;

                //overhead costs
                hiredLabor = (float) 3.48;
                costUnpaidLabor = (float) 29.17;
                capRecoveryOfEquip = (float) 112.67;
                costOfLandRentalRate = (float) 104.31;
                taxesInsurance = (float) 9.78;
                generalFarmOverhead = (float) 19.76;
                totalOverheadCost = (float) 279.37;

                //extra costs
                miscellaneous = (float) 0.00;

                //total cost
                totalCost = (float) 605.67;

                //supporting information
                yield = 134;
                price = (float) 3.13;

                //not quite important to the app
                sizeAcresPlanted = 390;

                break;

            //corn prairie gateway
            case "04":

                regionName = "Prairie Gateway";
                cropName = "Corn";

                primaryReturns = (float) 605.79;
                secondaryReturns = (float) 1.41;
                totalReturns = (float) 607.20;

                // operating costs
                seed = (float) 78.12;
                fertilizer = (float) 112.79;
                chemicals = (float) 29.36;
                customOps = (float) 21.14;
                fueLubeElec = (float) 54.69;
                repairs = (float) 35.38;
                irrigationWater = (float) 0.41;
                intOnCap = (float) 0.11;
                totalOperationalCosts = (float) 332.00;

                //overhead costs
                hiredLabor = (float) 3.66;
                costUnpaidLabor = (float) 26.74;
                capRecoveryOfEquip = (float) 118.98;
                costOfLandRentalRate = (float) 119.52;
                taxesInsurance = (float) 11.54;
                generalFarmOverhead = (float) 18.40;
                totalOverheadCost = (float) 298.84;

                //extra costs
                miscellaneous = (float) 0.00;

                //total cost
                totalCost = (float) 630.84;

                //supporting information
                yield = 159;
                price = (float) 3.81;

                //not quite important to the app
                sizeAcresPlanted = 371;

                break;

            //corn eastern uplands
            case "05":

                regionName = "Eastern Uplands";
                cropName = "Corn";

                primaryReturns = (float) 554.21;
                secondaryReturns = (float) 8.15;
                totalReturns = (float) 562.36;

                // operating costs
                seed = (float) 69.22;
                fertilizer = (float) 174.46;
                chemicals = (float) 27.28;
                customOps = (float) 6.96;
                fueLubeElec = (float) 24.71;
                repairs = (float) 24.78;
                irrigationWater = (float) 0.00;
                intOnCap = (float) 0.10;
                totalOperationalCosts = (float) 327.51;

                //overhead costs
                hiredLabor = (float) 2.57;
                costUnpaidLabor = (float) 37.08;
                capRecoveryOfEquip = (float) 84.62;
                costOfLandRentalRate = (float) 100.40;
                taxesInsurance = (float) 12.10;
                generalFarmOverhead = (float) 25.55;
                totalOverheadCost = (float) 262.32;

                //extra costs
                miscellaneous = (float) 0.00;

                //total cost
                totalCost = (float) 589.83;

                //supporting information
                yield = 157;
                price = (float) 3.53;

                //not quite important to the app
                sizeAcresPlanted = 63;


                break;

            //corn southern seaboard
            case "06":

                regionName = "Southern Seaboard";
                cropName = "Corn";

                primaryReturns = (float) 563.42;
                secondaryReturns = (float) 0.00;
                totalReturns = (float) 563.42;

                // operating costs
                seed = (float) 82.97;
                fertilizer = (float) 182.92;
                chemicals = (float) 39.22;
                customOps = (float) 19.72;
                fueLubeElec = (float) 42.30;
                repairs = (float) 28.45;
                irrigationWater = (float) 0.00;
                intOnCap = (float) 0.13;
                totalOperationalCosts = (float) 395.71;

                //overhead costs
                hiredLabor = (float) 4.53;
                costUnpaidLabor = (float) 35.61;
                capRecoveryOfEquip = (float) 96.32;
                costOfLandRentalRate = (float) 92.45;
                taxesInsurance = (float) 13.16;
                generalFarmOverhead = (float) 28.07;
                totalOverheadCost = (float) 270.14;

                //extra costs
                miscellaneous = (float) 0.00;

                //total cost
                totalCost = (float) 665.85;

                //supporting information
                yield = 143;
                price = (float) 3.94;

                //not quite important to the app
                sizeAcresPlanted = 132;

                break;

            //soybean united states
            case "10":

                regionName = "United States";
                cropName = "Soybean";

                primaryReturns = (float) 497.76;
                secondaryReturns = (float) 0.00;
                totalReturns = (float) 497.76;

                // operating costs
                seed = (float) 60.06;
                fertilizer = (float) 36.70;
                chemicals = (float) 28.62;
                customOps = (float) 10.25;
                fueLubeElec = (float) 21.58;
                repairs = (float) 23.33;
                irrigationWater = (float) 0.06;
                intOnCap = (float) 0.05;
                totalOperationalCosts = (float) 180.65;

                //overhead costs
                hiredLabor = (float) 3.10;
                costUnpaidLabor = (float) 18.07;
                capRecoveryOfEquip = (float) 88.19;
                costOfLandRentalRate = (float) 158.43;
                taxesInsurance = (float) 10.17;
                generalFarmOverhead = (float) 18.32;
                totalOverheadCost = (float) 296.28;

                //extra costs
                miscellaneous = (float) 0.00;

                //total cost
                totalCost = (float) 476.93;

                //supporting information
                yield = 48;
                price = (float) 10.37;

                //not quite important to the app
                sizeAcresPlanted = 273;

                break;

            //soybean heartland
            case "11":

                regionName = "Northern Crescent";
                cropName = "Soybean";

                primaryReturns = (float) 554.37;
                secondaryReturns = (float) 0.00;
                totalReturns = (float) 554.37;

                // operating costs
                seed = (float) 57.83;
                fertilizer = (float) 35.53;
                chemicals = (float) 27.83;
                customOps = (float) 9.24;
                fueLubeElec = (float) 16.73;
                repairs = (float) 19.46;
                irrigationWater = (float) 0.00;
                intOnCap = (float) 0.05;
                totalOperationalCosts = (float) 166.67;

                //overhead costs
                hiredLabor = (float) 1.17;
                costUnpaidLabor = (float) 16.01;
                capRecoveryOfEquip = (float) 79.81;
                costOfLandRentalRate = (float) 192.03;
                taxesInsurance = (float) 10.36;
                generalFarmOverhead = (float) 18.34;
                totalOverheadCost = (float) 318.32;

                //extra costs
                miscellaneous = (float) 0.00;

                //total cost
                totalCost = (float) 484.99;

                //supporting information
                yield = 51;
                price = (float) 10.87;

                //not quite important to the app
                sizeAcresPlanted = 268;

                break;

            //soybean northern crescent
            case "12":

                regionName = "United States";
                cropName = "Soybean";

                primaryReturns = (float) 495.00;
                secondaryReturns = (float) 0.00;
                totalReturns = (float) 495.00;

                // operating costs
                seed = (float) 62.99;
                fertilizer = (float) 49.47;
                chemicals = (float) 22.53;
                customOps = (float) 12.56;
                fueLubeElec = (float) 17.33;
                repairs = (float) 19.63;
                irrigationWater = (float) 0.00;
                intOnCap = (float) 0.06;
                totalOperationalCosts = (float) 184.57;

                //overhead costs
                hiredLabor = (float) 1.71;
                costUnpaidLabor = (float) 18.04;
                capRecoveryOfEquip = (float) 73.45;
                costOfLandRentalRate = (float) 125.97;
                taxesInsurance = (float) 10.41;
                generalFarmOverhead = (float) 22.82;
                totalOverheadCost = (float) 252.40;

                //extra costs
                miscellaneous = (float) 0.00;

                //total cost
                totalCost = (float) 436.97;

                //supporting information
                yield = 44;
                price = (float) 11.25;

                //not quite important to the app
                sizeAcresPlanted = 136;

                break;

            //soybean northern great plains
            case "13":

                regionName = "Northern Great Plains";
                cropName = "Soybean";

                primaryReturns = (float) 376.66;
                secondaryReturns = (float) 0.00;
                totalReturns = (float) 376.66;

                // operating costs
                seed = (float) 58.12;
                fertilizer = (float) 18.91;
                chemicals = (float) 17.00;
                customOps = (float) 8.95;
                fueLubeElec = (float) 18.18;
                repairs = (float) 27.72;
                irrigationWater = (float) 0.00;
                intOnCap = (float) 0.04;
                totalOperationalCosts = (float) 148.92;

                //overhead costs
                hiredLabor = (float) 2.33;
                costUnpaidLabor = (float) 15.15;
                capRecoveryOfEquip = (float) 110.04;
                costOfLandRentalRate = (float) 99.80;
                taxesInsurance = (float) 9.16;
                generalFarmOverhead = (float) 14.61;
                totalOverheadCost = (float) 251.09;

                //extra costs
                miscellaneous = (float) 0.00;

                //total cost
                totalCost = (float) 400.01;

                //supporting information
                yield = 37;
                price = (float) 10.18;

                //not quite important to the app
                sizeAcresPlanted = 511;

                break;

            //soybean prairie gateway
            case "14":

                regionName = "Prairie Gateway";
                cropName = "Soybean";

                primaryReturns = (float) 452.76;
                secondaryReturns = (float) 0.00;
                totalReturns = (float) 452.76;

                // operating costs
                seed = (float) 58.44;
                fertilizer = (float) 22.17;
                chemicals = (float) 27.85;
                customOps = (float) 9.92;
                fueLubeElec = (float) 26.08;
                repairs = (float) 26.08;
                irrigationWater = (float) 0.74;
                intOnCap = (float) 0.05;
                totalOperationalCosts = (float) 172.46;

                //overhead costs
                hiredLabor = (float) 1.25;
                costUnpaidLabor = (float) 17.73;
                capRecoveryOfEquip = (float) 97.42;
                costOfLandRentalRate = (float) 101.79;
                taxesInsurance = (float) 9.86;
                generalFarmOverhead = (float) 15.16;
                totalOverheadCost = (float) 243.21;

                //extra costs
                miscellaneous = (float) 0.00;

                //total cost
                totalCost = (float) 415.67;

                //supporting information
                yield = 42;
                price = (float) 10.78;

                //not quite important to the app
                sizeAcresPlanted = 293;

                break;

            //soybean eastern uplands
            case "15":

                regionName = "Eastern Uplands";
                cropName = "Soybean";

                primaryReturns = (float) 461.16;
                secondaryReturns = (float) 0.00;
                totalReturns = (float) 461.16;

                // operating costs
                seed = (float) 55.69;
                fertilizer = (float) 67.62;
                chemicals = (float) 23.18;
                customOps = (float) 8.08;
                fueLubeElec = (float) 17.07;
                repairs = (float) 17.20;
                irrigationWater = (float) 0.00;
                intOnCap = (float) 0.06;
                totalOperationalCosts = (float) 188.90;

                //overhead costs
                hiredLabor = (float) 3.93;
                costUnpaidLabor = (float) 21.39;
                capRecoveryOfEquip = (float) 66.42;
                costOfLandRentalRate = (float) 101.81;
                taxesInsurance = (float) 10.00;
                generalFarmOverhead = (float) 18.30;
                totalOverheadCost = (float) 221.85;

                //extra costs
                miscellaneous = (float) 0.00;

                //total cost
                totalCost = (float) 410.75;

                //supporting information
                yield = 42;
                price = (float) 10.98;

                //not quite important to the app
                sizeAcresPlanted = 137;

                break;

            //soybean southern seaboard
            case "16":

                regionName = "Southern Seaboard";
                cropName = "Soybean";

                primaryReturns = (float) 442.00;
                secondaryReturns = (float) 0.00;
                totalReturns = (float) 442.00;

                // operating costs
                seed = (float) 55.71;
                fertilizer = (float) 68.66;
                chemicals = (float) 38.00;
                customOps = (float) 8.52;
                fueLubeElec = (float) 16.87;
                repairs = (float) 21.82;
                irrigationWater = (float) 0.00;
                intOnCap = (float) 0.06;
                totalOperationalCosts = (float) 209.64;

                //overhead costs
                hiredLabor = (float) 2.85;
                costUnpaidLabor = (float) 23.11;
                capRecoveryOfEquip = (float) 74.29;
                costOfLandRentalRate = (float) 55.71;
                taxesInsurance = (float) 8.79;
                generalFarmOverhead = (float) 20.86;
                totalOverheadCost = (float) 185.61;

                //extra costs
                miscellaneous = (float) 0.00;

                //total cost
                totalCost = (float) 395.25;

                //supporting information
                yield = 40;
                price = (float) 11.05;

                //not quite important to the app
                sizeAcresPlanted = 233;

                break;

            //soybean mississippi portal
            case "17":

                regionName = "Mississippi Portal";
                cropName = "Soybean";

                primaryReturns = (float) 563.50;
                secondaryReturns = (float) 0.00;
                totalReturns = (float) 563.50;

                // operating costs
                seed = (float) 64.21;
                fertilizer = (float) 36.01;
                chemicals = (float) 42.98;
                customOps = (float) 15.15;
                fueLubeElec = (float) 51.55;
                repairs = (float) 38.75;
                irrigationWater = (float) 0.00;
                intOnCap = (float) 0.07;
                totalOperationalCosts = (float) 248.72;

                //overhead costs
                hiredLabor = (float) 13.29;
                costUnpaidLabor = (float) 26.96;
                capRecoveryOfEquip = (float) 111.06;
                costOfLandRentalRate = (float) 103.98;
                taxesInsurance = (float) 8.46;
                generalFarmOverhead = (float) 16.51;
                totalOverheadCost = (float) 280.26;

                //extra costs
                miscellaneous = (float) 0.00;

                //total cost
                totalCost = (float) 528.98;

                //supporting information
                yield = 50;
                price = (float) 11.27;

                //not quite important to the app
                sizeAcresPlanted = 635;

                break;


        }


    }

    public int getSizeAcresPlanted() {
        return sizeAcresPlanted;
    }

    public void setSizeAcresPlanted(int sizeAcresPlanted) {
        this.sizeAcresPlanted = sizeAcresPlanted;
    }

    public int getRegion() {
        return region;
    }

    public void setRegion(int region) {
        this.region = region;
    }

    public int getCrop() {
        return crop;
    }

    public void setCrop(int crop) {
        this.crop = crop;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getCropName() {
        return cropName;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

    public float getPrimaryReturns() {
        return primaryReturns;
    }

    public void setPrimaryReturns(float primaryReturns) {
        this.primaryReturns = primaryReturns;
    }

    public float getSecondaryReturns() {
        return secondaryReturns;
    }

    public void setSecondaryReturns(float secondaryReturns) {
        this.secondaryReturns = secondaryReturns;
    }

    public float getTotalReturns() {
        return  price * yield;
    }

    public float getSeed() {
        return seed;
    }

    public void setSeed(float seed) {
        this.seed = seed;
    }

    public float getFertilizer() {
        return fertilizer;
    }

    public void setFertilizer(float fertilizer) {
        this.fertilizer = fertilizer;
    }

    public float getChemicals() {
        return chemicals;
    }

    public void setChemicals(float chemicals) {
        this.chemicals = chemicals;
    }

    public float getCustomOps() {
        return customOps;
    }

    public void setCustomOps(float customOps) {
        this.customOps = customOps;
    }

    public float getFueLubeElec() {
        return fueLubeElec;
    }

    public void setFueLubeElec(float fueLubeElec) {
        this.fueLubeElec = fueLubeElec;
    }

    public float getRepairs() {
        return repairs;
    }

    public void setRepairs(float repairs) {
        this.repairs = repairs;
    }

    public float getIrrigationWater() {
        return irrigationWater;
    }

    public void setIrrigationWater(float irrigationWater) {
        this.irrigationWater = irrigationWater;
    }

    public float getIntOnCap() {
        return intOnCap;
    }

    public void setIntOnCap(float intOnCap) {
        this.intOnCap = intOnCap;
    }


    public float getHiredLabor() {
        return hiredLabor;
    }

    public void setHiredLabor(float hiredLabor) {
        this.hiredLabor = hiredLabor;
    }

    public float getCostUnpaidLabor() {
        return costUnpaidLabor;
    }

    public void setCostUnpaidLabor(float costUnpaidLabor) {
        this.costUnpaidLabor = costUnpaidLabor;
    }

    public float getCapRecoveryOfEquip() {
        return capRecoveryOfEquip;
    }

    public void setCapRecoveryOfEquip(float capRecoveryOfEquip) {
        this.capRecoveryOfEquip = capRecoveryOfEquip;
    }

    public float getCostOfLandRentalRate() {
        return costOfLandRentalRate;
    }

    public void setCostOfLandRentalRate(float costOfLandRentalRate) {
        this.costOfLandRentalRate = costOfLandRentalRate;
    }

    public float getTaxesInsurance() {
        return taxesInsurance;
    }

    public void setTaxesInsurance(float taxesInsurance) {
        this.taxesInsurance = taxesInsurance;
    }

    public float getGeneralFarmOverhead() {
        return generalFarmOverhead;
    }

    public void setGeneralFarmOverhead(float generalFarmOverhead) {
        this.generalFarmOverhead = generalFarmOverhead;
    }

    public float getTotalOperationalCosts() {
        return (seed + fertilizer + chemicals + customOps
                + fueLubeElec + repairs + irrigationWater + intOnCap);
    }

    public float getTotalOverheadCost() {
        return (hiredLabor + costUnpaidLabor + capRecoveryOfEquip +
                costOfLandRentalRate + taxesInsurance);
    }

    public void setMiscellaneous(float miscellaneous){
        this.miscellaneous = miscellaneous;

    }

    public float getMiscellaneous(){
        return miscellaneous;
    }

    public float getTotalCost() {
        return (getTotalOperationalCosts() + getTotalOverheadCost() + getMiscellaneous());
    }


    public int getYield() {
        return yield;
    }

    public void setYield(int yield) {
        this.yield = yield;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProfitString() {
        float profit = (float) (totalReturns - totalCost);
        return String.format("%.2f", profit);
    }

    public float getProfitFloat() {
        float profit = (getTotalReturns() - getTotalCost());
        return profit;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    /*
        if the profit is positive, then calculate the yield for breaking even
        definition of breakeven: yield at which price of corn is equal to the inputs
        TODO: find out what to do if profit is negative (aka a loss)
     */
    public float getYieldBreakEven() {

        float inputs = getTotalCost();
        float price = getPrice();
        //int yield = getYield();
        float yieldBreakEven = inputs / price;
        return yieldBreakEven;


    }


    /**
     * CREATED AN INNER CLASS TO CROPSTATS TO BE ABLE TO REORDER THE STATS
     * I NEEDED THE VALUES TO BE ABLE TO HOLD BOTH AN INDEX AND VALUE FOR
     * EACH OF THE ENTRIES FOR A DATASET
     */

    public class Result {
        int index;
        float value;

        Result(float value, int index) {

            this.value = value;
            this.index = index;

        }

        public void setIndex(int index) {

            this.index = index;

        }

        public void setValue(float value) {
            this.value = value;
        }

        public int getIndex() {
            return index;
        }

        public float getValue() {
            return value;
        }


    }

    // this specific method does a few things.
    // 1) it orders the values from highest to lowest
    // 2) it assigns an int to each individual result
    //      The reason an int is assigned to each was originally only for naming on the chart
    //      axes, but is also useful for keeping multiple result lists ordered. You only need
    //      to have the first list values ordered by high to low, after that just use the result
    //      index values when adding datasets
    //
    public ArrayList<Result> getOrderedCostValues() {

        ArrayList<Result> result = new ArrayList<>();

        result.add(new Result(getSeed(), 0));
        result.add(new Result(getFertilizer(), 1));
        result.add(new Result(getChemicals(), 2));
        result.add(new Result(getCustomOps(), 3));
        result.add(new Result(getFueLubeElec(), 4));
        result.add(new Result(getRepairs(), 5));
        result.add(new Result(getIrrigationWater(), 6));
        result.add(new Result(getIntOnCap(), 7));
        result.add(new Result(getHiredLabor(), 8));
        result.add(new Result(getCostUnpaidLabor(), 9));
        result.add(new Result(getCapRecoveryOfEquip(), 10));
        result.add(new Result(getCostOfLandRentalRate(), 11));
        result.add(new Result(getTaxesInsurance(), 12));


        Result temp;

        //TODO: change the sorting algorithm, to make the program run faster
        // TODO: specifically quicksort algorithm
        for (int i = 0; i < result.size(); i++) {

            for (int j = i; j > 0; j--) {


                if (result.get(j).getValue() < result.get(j - 1).getValue()) {

                    temp = result.get(j);
                    result.set(j, result.get(j - 1));
                    result.set(j - 1, temp);

                }
            }

        }


        return result;
    }

    public ArrayList<Result> getUnorderedCostValues() {

        ArrayList<Result> result = new ArrayList<>();

        result.add(new Result(getSeed(), 0));
        result.add(new Result(getFertilizer(), 1));
        result.add(new Result(getChemicals(), 2));
        result.add(new Result(getCustomOps(), 3));
        result.add(new Result(getFueLubeElec(), 4));
        result.add(new Result(getRepairs(), 5));
        result.add(new Result(getIrrigationWater(), 6));
        result.add(new Result(getIntOnCap(), 7));
        result.add(new Result(getHiredLabor(), 8));
        result.add(new Result(getCostUnpaidLabor(), 9));
        result.add(new Result(getCapRecoveryOfEquip(), 10));
        result.add(new Result(getCostOfLandRentalRate(), 11));
        result.add(new Result(getTaxesInsurance(), 12));

        return result;
    }

    /**
     * //TODO: figure out how to both arrange the float values and indexes alternating between highest and lowest
     */
    public ArrayList<Result> getOrderedPieValues(ArrayList<Result> result) {

        ArrayList<Result> reorderResult = new ArrayList<>();

        while (!result.isEmpty()) {


            int end = result.size() - 1;


            if (end == 0) {

                reorderResult.add(result.get(0));
                result.remove(0);


            } else if (end > 0) {

                reorderResult.add(result.get(end));
                reorderResult.add(result.get(0));
                result.remove(end);
                result.remove(0);

            }
        }

        return reorderResult;
    }


    // get the values from the ordered result arraylist
    // this is only for the pie chart values
    public ArrayList<Float> getFloatValues(ArrayList<Result> array) {

        ArrayList<Float> floats = new ArrayList<>();

        for (int i = 0; i < array.size(); i++) {
            floats.add(array.get(i).getValue());
        }

        return floats;
    }

    // get the arraylist of the indices for the charts
    public ArrayList<Integer> getIndices(ArrayList<Result> array) {

        ArrayList<Integer> index = new ArrayList<>();

        for (int i = 0; i < array.size(); i++) {
            index.add(array.get(i).getIndex());
        }

        return index;
    }

    public String getIndexString(int index) {
        String indexString;

        switch (index) {
            case 0:
                indexString = "Seed";
                break;
            case 1:
                indexString = "Fertilizer";
                break;
            case 2:
                indexString = "Chemicals";
                break;
            case 3:
                indexString = "Custom Operations";
                break;
            case 4:
                indexString = "Fuel";
                break;
            case 5:
                indexString = "Repairs";
                break;
            case 6:
                indexString = "Irrigation/Water";
                break;
            case 7:
                indexString = "Interest on Cap";
                break;
            case 8:
                indexString = "Hired Labor";
                break;
            case 9:
                indexString = "Cost of Unpaid Labor";
                break;
            case 10:
                indexString = "Recovery of Equipment";
                break;
            case 11:
                indexString = "Cost of Rental of Land";
                break;
            case 12:
                indexString = "Taxes & Insurance";
                break;
            default:
                indexString = "Error! did not find category";
        }

        return indexString;

    }


    protected CropStats(Parcel in) {
        id = in.readInt();
        region = in.readInt();
        crop = in.readInt();
        regionName = in.readString();
        cropName = in.readString();
        primaryReturns = in.readFloat();
        secondaryReturns = in.readFloat();
        totalReturns = in.readFloat();
        seed = in.readFloat();
        fertilizer = in.readFloat();
        chemicals = in.readFloat();
        customOps = in.readFloat();
        fueLubeElec = in.readFloat();
        repairs = in.readFloat();
        irrigationWater = in.readFloat();
        intOnCap = in.readFloat();
        totalOperationalCosts = in.readFloat();
        hiredLabor = in.readFloat();
        costUnpaidLabor = in.readFloat();
        capRecoveryOfEquip = in.readFloat();
        costOfLandRentalRate = in.readFloat();
        taxesInsurance = in.readFloat();
        generalFarmOverhead = in.readFloat();
        totalOverheadCost = in.readFloat();
        totalCost = in.readFloat();
        yield = in.readInt();
        yieldBreakEven = in.readInt();
        price = in.readFloat();
        sizeAcresPlanted = in.readInt();
        title = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(region);
        dest.writeInt(crop);
        dest.writeString(regionName);
        dest.writeString(cropName);
        dest.writeFloat(primaryReturns);
        dest.writeFloat(secondaryReturns);
        dest.writeFloat(totalReturns);
        dest.writeFloat(seed);
        dest.writeFloat(fertilizer);
        dest.writeFloat(chemicals);
        dest.writeFloat(customOps);
        dest.writeFloat(fueLubeElec);
        dest.writeFloat(repairs);
        dest.writeFloat(irrigationWater);
        dest.writeFloat(intOnCap);
        dest.writeFloat(totalOperationalCosts);
        dest.writeFloat(hiredLabor);
        dest.writeFloat(costUnpaidLabor);
        dest.writeFloat(capRecoveryOfEquip);
        dest.writeFloat(costOfLandRentalRate);
        dest.writeFloat(taxesInsurance);
        dest.writeFloat(generalFarmOverhead);
        dest.writeFloat(totalOverheadCost);
        dest.writeFloat(totalCost);
        dest.writeInt(yield);
        dest.writeInt(yieldBreakEven);
        dest.writeFloat(price);
        dest.writeInt(sizeAcresPlanted);
        dest.writeString(title);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<CropStats> CREATOR = new Parcelable.Creator<CropStats>() {
        @Override
        public CropStats createFromParcel(Parcel in) {
            return new CropStats(in);
        }

        @Override
        public CropStats[] newArray(int size) {
            return new CropStats[size];
        }
    };


}
