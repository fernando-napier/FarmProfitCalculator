package com.example.fernando.farmingfarming;

/**
 * Created by Fernando on 8/21/2015.
 * To create a Region object to hold the values received from the server
 *
 */

public class RegionData {

    private int regionID;
    private String regionName;
    private int regionYear;

    public RegionData(int id){

        regionID = id;
        regionYear = 2014;

        switch (id){
            case 0: regionName = "United States";
                break;
            case 1: regionName = "Heartland";
                break;
            case 2: regionName = "Northern Crescent";
                break;
            case 3: regionName = "Northern Great Plains";
                break;
            case 4: regionName = "Prairie Gateway";
                break;
            case 5: regionName = "Eastern Uplands";
                break;
            case 6: regionName = "Southern Seaboard";
                break;
            case 7: regionName = "Mississippi Portal";
                break;
            default: regionName = "Choose a Region";

        }

    }

    /**
     * setters not part of this because at no point should these be changed
     */
    public int getRegionID() {
        return regionID;
    }


    public String getRegionName() {
        return regionName;
    }


    public int getRegionYear() {
        return regionYear;
    }

}
