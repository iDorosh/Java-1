package com.idorosh.i_dorosh_connectedapp;

/**
 * Created by iDorosh on 9/22/15.
 */


public class Info {
    //variables for the data from the api
    private String mTitle;
    private String mMedia;
    private String mWidth;
    private String mHeight;

    //Setting variables to the data that's being passed in.
    public Info(String title, String media, String width, String height){
        mTitle = title;
        mMedia = media;
        mWidth = width;
        mHeight = height;
    }

    //Returning variables to main activity
    public String getmTitle(){
        return mTitle;
    }

    public String getmMedia(){
        return mMedia;
    }

    public String getmWidth(){
        return mWidth;
    }

    public String getmHeight(){return mHeight;}
}
