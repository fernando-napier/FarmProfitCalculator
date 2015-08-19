package com.example.fernando.farmingfarming;

/**
 * Created by Fernando on 8/17/2015.
 *
 * this class is to make sure that a selection is made for both region and
 * crop type
 *
 */
public class SetServerReady {

    private boolean region;
    private boolean crop;


    // constructor
    public SetServerReady(){
        this.crop = false;
        this.region = false;
    }


    public void setRegion(boolean i){
        region = i;
   }
    public void setCrop (boolean i){
        crop = i;
    }

    public boolean getEnabled(){
        return (region && crop) != false;

    }
}
