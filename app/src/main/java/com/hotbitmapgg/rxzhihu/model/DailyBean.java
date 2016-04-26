package com.hotbitmapgg.rxzhihu.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * body : HTML 格式的新闻
 * image-source : 图片的内容提供方。为了避免被起诉非法使用图片，在显示图片时最好附上其版权信息。
 * title : 新闻标题
 * image : 获得的图片同 最新消息 获得的图片分辨率不同。这里获得的是在文章浏览界面中使用的大图。
 * share_url : 供在线查看内容与分享至 SNS 用的 URL
 * js : 供手机端的 WebView(UIWebView) 使用
 * recommenders : 这篇文章的推荐者
 * ga_prefix : 供 Google Analytics 使用
 * section : 栏目的信息
 * <p/>
 * thumbnail : 栏目的缩略图
 * id : 该栏目的 id
 * name : 该栏目的名称
 * <p/>
 * type : 新闻的类型
 * id : 新闻的 id
 * css : 供手机端的 WebView(UIWebView) 使用
 */
public class DailyBean implements Parcelable
{

    private int type;

    private int id;

    private String ga_prefix;

    private String title;

    private List<String> images;

    private boolean multipic;

    private boolean isRead = false;

    private String Date;

    public String getDate()
    {

        return Date;
    }

    public void setDate(String date)
    {

        Date = date;
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

    public List<String> getImages()
    {

        return images;
    }

    public void setImages(List<String> images)
    {

        this.images = images;
    }

    public boolean isRead()
    {

        return isRead;
    }

    public void setRead(boolean read)
    {

        isRead = read;
    }

    public boolean isMultipic()
    {

        return multipic;
    }

    public void setMultipic(boolean multipic)
    {

        this.multipic = multipic;
    }

    @Override
    public int describeContents()
    {

        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {

        dest.writeInt(this.type);
        dest.writeInt(this.id);
        dest.writeString(this.ga_prefix);
        dest.writeString(this.title);
        dest.writeStringList(this.images);
        dest.writeByte(isRead ? (byte) 1 : (byte) 0);
    }

    public DailyBean()
    {

    }

    protected DailyBean(Parcel in)
    {

        this.type = in.readInt();
        this.id = in.readInt();
        this.ga_prefix = in.readString();
        this.title = in.readString();
        this.images = in.createStringArrayList();
        this.isRead = in.readByte() != 0;
    }

    public static final Creator<DailyBean> CREATOR = new Creator<DailyBean>()
    {

        public DailyBean createFromParcel(Parcel source)
        {

            return new DailyBean(source);
        }

        public DailyBean[] newArray(int size)
        {

            return new DailyBean[size];
        }
    };
}
