package com.example.fernando.farmingfarming;

import android.util.Log;

/**
 * Created by fernando on 8/24/15.
 *
 * to use for the time being
 * so as to not use the server data
 * and as an example of what the app
 * will be able to do
 *
 */
public class CropStats {


    private int region;
    private int crop;
    private String regionName;
    private String cropName;

    // gross value of production
    private double primaryProduct;
    private double secondaryProduct;
    private double totalProduct;

    // operating costs
    private double seed;
    private double fertilizer;
    private double chemicals;
    private double customOps;
    private double fueLubeElec;
    private double repairs;
    private double irrigationWater;
    private double intOnCap;
    private double totalOperationalCosts;

    //overhead costs
    private double hiredLabor;
    private double costUnpaidLabor;
    private double capRecoveryOfEquip;
    private double costOfLandRentalRate;
    private double taxesInsurance;
    private double generalFarmOverhead;
    private double totalOverheadCost;

    //total cost
    private double totalCost;

    //supporting information
    private int yield;
    private double price;
    private int sizeAcresPlanted;







    /**
     *
     * 7 regions
     * 3 or 4 crops
     * @param regionID
     * @param cropID
     */

    public CropStats(int cropID, int regionID){

        region = regionID;
        crop = cropID;
        getStats();


    }

    private void getStats(){

        String intermediate = crop + "" + region;
        Log.d("string", intermediate);



        /**
         * corn class will
         */



        switch(intermediate){

            //corn usa
            case "00":

                regionName = "United States";
                cropName = "Corn";

                primaryProduct = 601.80;
                secondaryProduct = 1.38;
                totalProduct = 603.18;

                // operating costs
                seed = 101.04;
                fertilizer = 149.23;
                chemicals = 28.57;
                customOps = 17.77;
                fueLubeElec = 32.27;
                repairs = 25.79;
                irrigationWater = 0.12;
                intOnCap = 0.12;
                totalOperationalCosts = 356.92;

                //overhead costs
                hiredLabor = 3.16;
                costUnpaidLabor = 24.75;
                capRecoveryOfEquip = 99.25;
                costOfLandRentalRate = 175.60;
                taxesInsurance = 9.34;
                generalFarmOverhead = 19.88;
                totalOverheadCost = 331.98;

                //total cost
                totalCost = 688.90;

                //supporting information
                yield = 170;
                price = 3.54;

                //not quite important to the app
                sizeAcresPlanted = 280;


                break;

            //corn heartland
            case "01":

                regionName = "HeartLand";
                cropName = "Corn";

                primaryProduct = 628.29;
                secondaryProduct = 0.29;
                totalProduct = 628.58;

                // operating costs
                seed = 108.41;
                fertilizer = 156.78;
                chemicals = 29.94;
                customOps = 16.93;
                fueLubeElec = 28.20;
                repairs = 23.79;
                irrigationWater = 0;
                intOnCap = 0.12;
                totalOperationalCosts = 364.17;

                //overhead costs
                hiredLabor = 2.86;
                costUnpaidLabor = 22.17;
                capRecoveryOfEquip = 95.64;
                costOfLandRentalRate = 208.03;
                taxesInsurance = 8.58;
                generalFarmOverhead = 18.98;
                totalOverheadCost = 356.26;

                //total cost
                totalCost = 720.43;

                //supporting information
                yield = 179;
                price = 3.51;

                //not quite important to the app
                sizeAcresPlanted = 313;



                break;

            //corn northern crescent
            case "02":

                regionName = "Northern Crescent";
                cropName = "Corn";

                primaryProduct = 558.48;
                secondaryProduct = 6.95;
                totalProduct = 565.43;

                // operating costs
                seed = 93.01;
                fertilizer = 162.11;
                chemicals = 28.56;
                customOps = 22.51;
                fueLubeElec = 29.68;
                repairs = 25.77;
                irrigationWater = 0.00;
                intOnCap = 0.12;
                totalOperationalCosts = 361.76;

                //overhead costs
                hiredLabor = 3.94;
                costUnpaidLabor = 32.89;
                capRecoveryOfEquip = 87.03;
                costOfLandRentalRate = 114.28;
                taxesInsurance = 10.03;
                generalFarmOverhead = 26.07;
                totalOverheadCost = 274.24;

                //total cost
                totalCost = 636.00;

                //supporting information
                yield = 156;
                price = 3.58;

                //not quite important to the app
                sizeAcresPlanted = 156;

                break;

            //corn northern great plains
            case "03":

                regionName = "Northern Great Plains";
                cropName = "Corn";

                primaryProduct = 419.42;
                secondaryProduct = 1.80;
                totalProduct = 421.22;

                // operating costs
                seed = 98.90;
                fertilizer = 125.96;
                chemicals = 20.38;
                customOps = 17.94;
                fueLubeElec = 32.83;
                repairs = 29.36;
                irrigationWater = 0.83;
                intOnCap = 0.10;
                totalOperationalCosts = 326.30;

                //overhead costs
                hiredLabor = 3.48;
                costUnpaidLabor = 29.17;
                capRecoveryOfEquip = 112.67;
                costOfLandRentalRate = 104.31;
                taxesInsurance = 9.78;
                generalFarmOverhead = 19.76;
                totalOverheadCost = 279.37;

                //total cost
                totalCost = 605.67;

                //supporting information
                yield = 134;
                price = 3.13;

                //not quite important to the app
                sizeAcresPlanted = 390;

                break;

            //corn prairie gateway
            case "04":

                regionName = "Prairie Gateway";
                cropName = "Corn";

                primaryProduct = 605.79;
                secondaryProduct = 1.41;
                totalProduct = 607.20;

                // operating costs
                seed = 78.12;
                fertilizer = 112.79;
                chemicals = 29.36;
                customOps = 21.14;
                fueLubeElec = 54.69;
                repairs = 35.38;
                irrigationWater = 0.41;
                intOnCap = 0.11;
                totalOperationalCosts = 332.00;

                //overhead costs
                hiredLabor = 3.66;
                costUnpaidLabor = 26.74;
                capRecoveryOfEquip = 118.98;
                costOfLandRentalRate = 119.52;
                taxesInsurance = 11.54;
                generalFarmOverhead = 18.40;
                totalOverheadCost = 298.84;

                //total cost
                totalCost = 630.84;

                //supporting information
                yield = 159;
                price = 3.81;

                //not quite important to the app
                sizeAcresPlanted = 371;

                break;

            //corn eastern uplands
            case "05":

                regionName = "Eastern Uplands";
                cropName = "Corn";

                primaryProduct = 554.21;
                secondaryProduct = 8.15;
                totalProduct = 562.36;

                // operating costs
                seed = 69.22;
                fertilizer = 174.46;
                chemicals = 27.28;
                customOps = 6.96;
                fueLubeElec = 24.71;
                repairs = 24.78;
                irrigationWater = 0.00;
                intOnCap = 0.10;
                totalOperationalCosts = 327.51;

                //overhead costs
                hiredLabor = 2.57;
                costUnpaidLabor = 37.08;
                capRecoveryOfEquip = 84.62;
                costOfLandRentalRate = 100.40;
                taxesInsurance = 12.10;
                generalFarmOverhead = 25.55;
                totalOverheadCost = 262.32;

                //total cost
                totalCost = 589.83;

                //supporting information
                yield = 157;
                price = 3.53;

                //not quite important to the app
                sizeAcresPlanted = 63;


                break;

            //corn southern seaboard
            case "06":

                regionName = "Southern Seaboard";
                cropName = "Corn";

                primaryProduct = 563.42;
                secondaryProduct = 0.00;
                totalProduct = 563.42;

                // operating costs
                seed = 82.97;
                fertilizer = 182.92;
                chemicals = 39.22;
                customOps = 19.72;
                fueLubeElec = 42.30;
                repairs = 28.45;
                irrigationWater = 0.00;
                intOnCap = 0.13;
                totalOperationalCosts = 395.71;

                //overhead costs
                hiredLabor = 4.53;
                costUnpaidLabor = 35.61;
                capRecoveryOfEquip = 96.32;
                costOfLandRentalRate = 92.45;
                taxesInsurance = 13.16;
                generalFarmOverhead = 28.07;
                totalOverheadCost = 270.14;

                //total cost
                totalCost = 665.85;

                //supporting information
                yield = 143;
                price = 3.94;

                //not quite important to the app
                sizeAcresPlanted = 132;

                break;
        }



    }

    public double getPrimaryProduct() {
        return primaryProduct;
    }

    public double getSecondaryProduct() {
        return secondaryProduct;
    }

    public double getTotalProduct() {
        return totalProduct;
    }

    public double getSeed() {
        return seed;
    }

    public double getFertilizer() {
        return fertilizer;
    }

    public double getChemicals() {
        return chemicals;
    }

    public double getCustomOps() {
        return customOps;
    }

    public double getFueLubeElec() {
        return fueLubeElec;
    }

    public double getRepairs() {
        return repairs;
    }

    public double getIrrigationWater() {
        return irrigationWater;
    }

    public double getIntOnCap() {
        return intOnCap;
    }

    public double getTotalOperationalCosts() {
        return totalOperationalCosts;
    }

    public double getHiredLabor() {
        return hiredLabor;
    }

    public double getCostUnpaidLabor() {
        return costUnpaidLabor;
    }

    public double getCapRecoveryOfEquip() {
        return capRecoveryOfEquip;
    }

    public double getCostOfLandRentalRate() {
        return costOfLandRentalRate;
    }

    public double getTaxesInsurance() {
        return taxesInsurance;
    }

    public double getGeneralFarmOverhead() {
        return generalFarmOverhead;
    }

    public double getTotalOverheadCost() {
        return totalOverheadCost;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public int getYield() {
        return yield;
    }

    public double getPrice() {
        return price;
    }

    public int getSizeAcresPlanted() {
        return sizeAcresPlanted;
    }

    public String getRegionName() {
        return regionName;
    }

    public String getCropName() {
        return cropName;
    }

    public String getProfit(){
        double profit = (totalProduct - totalCost);
        return String.format("%.2f",profit);

    }
}
