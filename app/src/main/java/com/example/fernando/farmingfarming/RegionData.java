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

    public RegionData(int id, String name, int year){

        regionID = id;
        regionName = name;
        regionYear = year;
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
