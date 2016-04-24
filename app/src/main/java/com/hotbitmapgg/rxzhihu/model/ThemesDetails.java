package com.hotbitmapgg.rxzhihu.model;

import org.json.*;

import java.util.ArrayList;

/**
 * 主题日报详情内容模型类
 */
public class ThemesDetails
{

    private String imageSource;

    private ArrayList<Stories> stories;

    private double color;

    private String image;

    private ArrayList<Editors> editors;

    private String background;

    private String description;

    private String name;


    public ThemesDetails()
    {

    }

    public ThemesDetails(JSONObject json)
    {

        this.imageSource = json.optString("image_source");

        this.stories = new ArrayList<Stories>();
        JSONArray arrayStories = json.optJSONArray("stories");
        if (null != arrayStories)
        {
            int storiesLength = arrayStories.length();
            for (int i = 0; i < storiesLength; i++)
            {
                JSONObject item = arrayStories.optJSONObject(i);
                if (null != item)
                {
                    this.stories.add(new Stories(item));
                }
            }
        } else
        {
            JSONObject item = json.optJSONObject("stories");
            if (null != item)
            {
                this.stories.add(new Stories(item));
            }
        }

        this.color = json.optDouble("color");
        this.image = json.optString("image");

        this.editors = new ArrayList<Editors>();
        JSONArray arrayEditors = json.optJSONArray("editors");
        if (null != arrayEditors)
        {
            int editorsLength = arrayEditors.length();
            for (int i = 0; i < editorsLength; i++)
            {
                JSONObject item = arrayEditors.optJSONObject(i);
                if (null != item)
                {
                    this.editors.add(new Editors(item));
                }
            }
        } else
        {
            JSONObject item = json.optJSONObject("editors");
            if (null != item)
            {
                this.editors.add(new Editors(item));
            }
        }

        this.background = json.optString("background");
        this.description = json.optString("description");
        this.name = json.optString("name");
    }

    public String getImageSource()
    {

        return this.imageSource;
    }

    public void setImageSource(String imageSource)
    {

        this.imageSource = imageSource;
    }

    public ArrayList<Stories> getStories()
    {

        return this.stories;
    }

    public void setStories(ArrayList<Stories> stories)
    {

        this.stories = stories;
    }

    public double getColor()
    {

        return this.color;
    }

    public void setColor(double color)
    {

        this.color = color;
    }

    public String getImage()
    {

        return this.image;
    }

    public void setImage(String image)
    {

        this.image = image;
    }

    public ArrayList<Editors> getEditors()
    {

        return this.editors;
    }

    public void setEditors(ArrayList<Editors> editors)
    {

        this.editors = editors;
    }

    public String getBackground()
    {

        return this.background;
    }

    public void setBackground(String background)
    {

        this.background = background;
    }

    public String getDescription()
    {

        return this.description;
    }

    public void setDescription(String description)
    {

        this.description = description;
    }

    public String getName()
    {

        return this.name;
    }

    public void setName(String name)
    {

        this.name = name;
    }
}
