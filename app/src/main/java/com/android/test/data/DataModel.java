package com.android.test.data;

/**
 * Created by Basanth on 5/20/2018.
 */
@SuppressWarnings("DefaultFileTemplate")
public class DataModel {

    private final String title;
    private final String description;
    private final String img_url;

    public DataModel(String title,String desc,String url)
    {
        this.title = title;
        description = desc;
        img_url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImg_url() {
        return img_url;
    }


}
