package com.example.fernando.farmingfarming;

/**
 * Created by Fernando on 8/17/2015.
 */
public class CreateURL {

    private String url;

    public CreateURL(int i){


        switch(i) {

            case (1):
                url = "integer=1";
                break;
            case (2):
                url = "integer=2";
                break;
            case (3):
                url = "integer=3";
                break;
            case (4):
                url = "integer=4";
                break;
            case (5):
                url = "integer=5";
                break;
            case (6):
                url = "integer=6";
                break;
            case (7):
                url = "integer=7";
                break;
            default:
                url = "error";
        }

    }


    public String getURL(){
        return url;
    }

}
