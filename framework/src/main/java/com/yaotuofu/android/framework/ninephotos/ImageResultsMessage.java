package com.yaotuofu.android.framework.ninephotos;

import com.yaotuofu.android.framework.message.IMessage;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/4/1.
 */

public class ImageResultsMessage implements IMessage {


    private ArrayList<String> imageResults;
    private HashMap<String,String> hashMapResults;

    public ImageResultsMessage() {
    }
    public ImageResultsMessage(ArrayList<String> imageResults) {
        this.imageResults = imageResults;
    }

    public ImageResultsMessage(ArrayList<String> imageResults, HashMap<String, String> hashMapResults) {
        this.imageResults = imageResults;
        this.hashMapResults = hashMapResults;
    }
    public HashMap<String, String> getHashMapResults() {
        return hashMapResults;
    }

    public void setHashMapResults(HashMap<String, String> hashMapResults) {
        this.hashMapResults = hashMapResults;
    }



    public ArrayList<String> getImageResults() {
        return imageResults;
    }

    public void setImageResults(ArrayList<String> imageResults) {
        this.imageResults = imageResults;
    }
}

