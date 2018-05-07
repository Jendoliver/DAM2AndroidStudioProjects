package com.apporelbotna.lego.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Jandol on 11/23/2017.
 */

public class LegoSet
{
    public String name;

    @SerializedName("num_parts")
    public int numParts;

    @SerializedName("set_img_url")
    public String imgUrl;

    @SerializedName("set_num")
    public String id;

    public int year;
}
