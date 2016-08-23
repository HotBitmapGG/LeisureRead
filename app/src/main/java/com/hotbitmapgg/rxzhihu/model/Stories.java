package com.hotbitmapgg.rxzhihu.model;

import org.json.*;

import java.util.ArrayList;

public class Stories
{

    private int id;

    private String title;

    private int type;

    private ArrayList<String> images;


    public Stories()
    {

    }

    public Stories(JSONObject json)
    {

        this.id = json.optInt("id");
        this.title = json.optString("title");
        this.type = json.optInt("type");

        this.images = new ArrayList<String>();
        JSONArray arrayImages = json.optJSONArray("images");
        if (null != arrayImages)
        {
            int imagesLength = arrayImages.length();
            for (int i = 0; i < imagesLength; i++)
            {
                String item = arrayImages.optString(i);
                if (null != item)
                {
                    this.images.add(item);
                }
            }
        } else
        {
            String item = json.optString("images");
            if (null != item)
            {
                this.images.add(item);
            }
        }
    }

    public int getId()
    {

        return this.id;
    }

    public void setId(int id)
    {

        this.id = id;
    }

    public String getTitle()
    {

        return this.title;
    }

    public void setTitle(String title)
    {

        this.title = title;
    }

    public int getType()
    {

        return this.type;
    }

    public void setType(int type)
    {

        this.type = type;
    }

    public ArrayList<String> getImages()
    {

        return this.images;
    }

    public void setImages(ArrayList<String> images)
    {

        this.images = images;
    }
}
