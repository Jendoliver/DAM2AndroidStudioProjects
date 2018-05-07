package cat.flx.legoparts.model;

import com.google.gson.annotations.SerializedName;

public class LegoSet {
    private String name;
    @SerializedName("num_parts")
    private int numParts;
    @SerializedName("set_img_url")
    private String setImgUrl;
    @SerializedName("set_num")
    private String setNum;
    private int year;

    public String getName() { return name; }
    public int getNumParts() { return numParts; }
    public String getSetImgUrl() { return setImgUrl; }
    public String getSetNum() { return setNum; }
    public int getYear() { return year; }
}
