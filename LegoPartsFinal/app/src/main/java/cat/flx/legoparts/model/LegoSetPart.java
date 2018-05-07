package cat.flx.legoparts.model;

import com.google.gson.annotations.SerializedName;

public class LegoSetPart {
    private String name;
    @SerializedName("part_img_url")
    private String imgUrl;

    public String getName() { return name; }
    public String getImgUrl() { return imgUrl; }
}
