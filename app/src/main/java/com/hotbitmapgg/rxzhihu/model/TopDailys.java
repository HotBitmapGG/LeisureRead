package com.hotbitmapgg.rxzhihu.model;

import android.os.Parcel;
import android.os.Parcelable;

public class TopDailys implements Parcelable
{

    private String image;

    private int type;

    private int id;

    private String ga_prefix;

    private String title;

    public String getImage()
    {

        return image;
    }

    public void setImage(String image)
    {

        this.image = image;
    }

    public int getType()
    {

        return type;
    }

    public void setType(int type)
    {

        this.type = type;
    }

    public int getId()
    {

        return id;
    }

    public void setId(int id)
    {

        this.id = id;
    }

    public String getGa_prefix()
    {

        return ga_prefix;
    }

    public void setGa_prefix(String ga_prefix)
    {

        this.ga_prefix = ga_prefix;
    }

    public String getTitle()
    {

        return title;
    }

    public void setTitle(String title)
    {

        this.title = title;
    }

    @Override
    public int describeContents()
    {

        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {

        dest.writeString(this.image);
        dest.writeInt(this.type);
        dest.writeInt(this.id);
        dest.writeString(this.ga_prefix);
        dest.writeString(this.title);
    }

    public TopDailys()
    {

    }

    protected TopDailys(Parcel in)
    {

        this.image = in.readString();
        this.type = in.readInt();
        this.id = in.readInt();
        this.ga_prefix = in.readString();
        this.title = in.readString();
    }

    public static final Creator<TopDailys> CREATOR = new Creator<TopDailys>()
    {

        public TopDailys createFromParcel(Parcel source)
        {

            return new TopDailys(source);
        }

        public TopDailys[] newArray(int size)
        {

            return new TopDailys[size];
        }
    };
}
