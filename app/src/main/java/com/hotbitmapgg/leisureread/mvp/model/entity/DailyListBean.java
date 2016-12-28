package com.hotbitmapgg.leisureread.mvp.model.entity;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by hcc on 2016/12/28 13:35
 * 100332338@qq.com
 * LeisureRead
 *
 * @HotBitmapGG 日报列表模型类
 */
public class DailyListBean implements Parcelable {

  /**
   * date : 20161214
   * stories : [{"images":["http://pic3.zhimg.com/afab44d4ef0758d1940eaccbe622cf9a.jpg"],"type":0,"id":9065007,"ga_prefix":"121415","title":"三国杀的战术和专利竞争"},{"images":["http://pic4.zhimg.com/76197937faa9a8b1005b716b12612a53.jpg"],"type":0,"id":9063416,"ga_prefix":"121414","title":"如何巧妙地达到沟通目的？"},{"images":["http://pic4.zhimg.com/ec1e27a350b31f700d866a600be2354f.jpg"],"type":0,"id":9064857,"ga_prefix":"121413","title":"苹果的
   * AirPods 开卖了，这是用了一个多月的体验"},{"images":["http://pic3.zhimg.com/84d406badd1e58ad3e2afc4127f4178a.jpg"],"type":0,"id":9064184,"ga_prefix":"121412","title":"大误
   * · 一个男人的 100 种死法"},{"images":["http://pic4.zhimg.com/10a91148593f28f42b27d8b3829649b7.jpg"],"type":0,"id":9063457,"ga_prefix":"121411","title":"想在
   * Excel 里少犯错，多用自动化替代人肉操作"},{"images":["http://pic3.zhimg.com/81dcc5a406e23fb60345d9a59eb7f4f2.jpg"],"type":0,"id":9062157,"ga_prefix":"121410","title":"美欧日拒绝承认中国市场经济地位，还能愉快地赚钱吗？"},{"images":["http://pic2.zhimg.com/ae5c912d6c327ceb9d22bcd213ee0799.jpg"],"type":0,"id":9063445,"ga_prefix":"121409","title":"「整个画面白屏的时候，我以为自己真的死了」"},{"images":["http://pic3.zhimg.com/946e7941861fdb50fc49cae3a6de1ad6.jpg"],"type":0,"id":9062771,"ga_prefix":"121408","title":"银行员工怎样拉到真正的第一笔存款？"},{"images":["http://pic4.zhimg.com/7af56a9af569984f29d9682e3755bbfb.jpg"],"type":0,"id":9063370,"ga_prefix":"121407","title":"「投资你两千万，可别拿了钱就跑路啊」"},{"images":["http://pic4.zhimg.com/170ce9d78aab2de932aa479cd14d7ce7.jpg"],"type":0,"id":9061596,"ga_prefix":"121407","title":"怎样解决男男性传播艾滋病疫情上升的问题？"},{"images":["http://pic3.zhimg.com/cfb7ea85402fdb42e335bc696eee5556.jpg"],"type":0,"id":9062473,"ga_prefix":"121407","title":"《你的名字。》里的「口嚼酒」真的存在，还有点甜"},{"images":["http://pic3.zhimg.com/9cbca8cb0f92f979a2ef2e46ddd4f4ea.jpg"],"type":0,"id":9063201,"ga_prefix":"121407","title":"读读日报
   * 24 小时热门 TOP 5 · 最烂的一次约会到底有多烂？"},{"images":["http://pic3.zhimg.com/5abc7bd13ce5a34bfc849068c2a584a6.jpg"],"type":0,"id":9062270,"ga_prefix":"121406","title":"瞎扯
   * · 如何正确地吐槽"}]
   * top_stories : [{"image":"http://pic3.zhimg.com/65e1e452a62f7e279f516b654b5af3e2.jpg","type":0,"id":9064857,"ga_prefix":"121413","title":"苹果的
   * AirPods 开卖了，这是用了一个多月的体验"},{"image":"http://pic1.zhimg.com/76b28d30f82e95e6d08fa536d52b0a68.jpg","type":0,"id":9062157,"ga_prefix":"121410","title":"美欧日拒绝承认中国市场经济地位，还能愉快地赚钱吗？"},{"image":"http://pic2.zhimg.com/cdde24410b4ec4f61f48f915d2450ccd.jpg","type":0,"id":9062473,"ga_prefix":"121407","title":"《你的名字。》里的「口嚼酒」真的存在，还有点甜"},{"image":"http://pic2.zhimg.com/79941c4337224e9e1021126d650a5a7d.jpg","type":0,"id":9063201,"ga_prefix":"121407","title":"读读日报
   * 24 小时热门 TOP 5 · 最烂的一次约会到底有多烂？"},{"image":"http://pic2.zhimg.com/0995f252d5e81ea61c0d4209ed64ddf1.jpg","type":0,"id":9062187,"ga_prefix":"121317","title":"知乎好问题
   * · 如何教会儿童在突发灾害中保护自己？"}]
   */

  private String date;
  private List<StoriesBean> stories;
  private List<TopStoriesBean> top_stories;


  public String getDate() { return date;}


  public void setDate(String date) { this.date = date;}


  public List<StoriesBean> getStories() { return stories;}


  public void setStories(List<StoriesBean> stories) { this.stories = stories;}


  public List<TopStoriesBean> getTop_stories() { return top_stories;}


  public void setTop_stories(List<TopStoriesBean> top_stories) { this.top_stories = top_stories;}


  public static class StoriesBean implements Parcelable {
    /**
     * images : ["http://pic3.zhimg.com/afab44d4ef0758d1940eaccbe622cf9a.jpg"]
     * type : 0
     * id : 9065007
     * ga_prefix : 121415
     * title : 三国杀的战术和专利竞争
     */

    private int type;
    private int id;
    private String ga_prefix;
    private String title;
    private List<String> images;
    private String date;
    private boolean multipic;
    private boolean isRead = false;


    public boolean isMultipic() {
      return multipic;
    }


    public void setMultipic(boolean multipic) {
      this.multipic = multipic;
    }


    public boolean isRead() {
      return isRead;
    }


    public void setRead(boolean read) {
      isRead = read;
    }


    public String getDate() {
      return date;
    }


    public void setDate(String date) {
      this.date = date;
    }


    public int getType() { return type;}


    public void setType(int type) { this.type = type;}


    public int getId() { return id;}


    public void setId(int id) { this.id = id;}


    public String getGa_prefix() { return ga_prefix;}


    public void setGa_prefix(String ga_prefix) { this.ga_prefix = ga_prefix;}


    public String getTitle() { return title;}


    public void setTitle(String title) { this.title = title;}


    public List<String> getImages() { return images;}


    public void setImages(List<String> images) { this.images = images;}


    @Override public int describeContents() { return 0; }


    @Override public void writeToParcel(Parcel dest, int flags) {
      dest.writeInt(this.type);
      dest.writeInt(this.id);
      dest.writeString(this.ga_prefix);
      dest.writeString(this.title);
      dest.writeStringList(this.images);
      dest.writeString(this.date);
      dest.writeByte(this.multipic ? (byte) 1 : (byte) 0);
      dest.writeByte(this.isRead ? (byte) 1 : (byte) 0);
    }


    public StoriesBean() {}


    protected StoriesBean(Parcel in) {
      this.type = in.readInt();
      this.id = in.readInt();
      this.ga_prefix = in.readString();
      this.title = in.readString();
      this.images = in.createStringArrayList();
      this.date = in.readString();
      this.multipic = in.readByte() != 0;
      this.isRead = in.readByte() != 0;
    }


    public static final Creator<StoriesBean> CREATOR = new Creator<StoriesBean>() {
      @Override public StoriesBean createFromParcel(Parcel source) {return new StoriesBean(source);}


      @Override public StoriesBean[] newArray(int size) {return new StoriesBean[size];}
    };
  }

  public static class TopStoriesBean implements Parcelable {
    /**
     * image : http://pic3.zhimg.com/65e1e452a62f7e279f516b654b5af3e2.jpg
     * type : 0
     * id : 9064857
     * ga_prefix : 121413
     * title : 苹果的 AirPods 开卖了，这是用了一个多月的体验
     */

    private String image;
    private int type;
    private int id;
    private String ga_prefix;
    private String title;


    public String getImage() { return image;}


    public void setImage(String image) { this.image = image;}


    public int getType() { return type;}


    public void setType(int type) { this.type = type;}


    public int getId() { return id;}


    public void setId(int id) { this.id = id;}


    public String getGa_prefix() { return ga_prefix;}


    public void setGa_prefix(String ga_prefix) { this.ga_prefix = ga_prefix;}


    public String getTitle() { return title;}


    public void setTitle(String title) { this.title = title;}


    @Override public int describeContents() { return 0; }


    @Override public void writeToParcel(Parcel dest, int flags) {
      dest.writeString(this.image);
      dest.writeInt(this.type);
      dest.writeInt(this.id);
      dest.writeString(this.ga_prefix);
      dest.writeString(this.title);
    }


    public TopStoriesBean() {}


    protected TopStoriesBean(Parcel in) {
      this.image = in.readString();
      this.type = in.readInt();
      this.id = in.readInt();
      this.ga_prefix = in.readString();
      this.title = in.readString();
    }


    public static final Creator<TopStoriesBean> CREATOR = new Creator<TopStoriesBean>() {
      @Override public TopStoriesBean createFromParcel(Parcel source) {
        return new TopStoriesBean(source);
      }


      @Override public TopStoriesBean[] newArray(int size) {return new TopStoriesBean[size];}
    };
  }


  @Override public int describeContents() { return 0; }


  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.date);
    dest.writeList(this.stories);
    dest.writeList(this.top_stories);
  }


  public DailyListBean() {}


  protected DailyListBean(Parcel in) {
    this.date = in.readString();
    this.stories = new ArrayList<StoriesBean>();
    in.readList(this.stories, StoriesBean.class.getClassLoader());
    this.top_stories = new ArrayList<TopStoriesBean>();
    in.readList(this.top_stories, TopStoriesBean.class.getClassLoader());
  }


  public static final Parcelable.Creator<DailyListBean> CREATOR
      = new Parcelable.Creator<DailyListBean>() {
    @Override public DailyListBean createFromParcel(Parcel source) {
      return new DailyListBean(source);
    }


    @Override public DailyListBean[] newArray(int size) {return new DailyListBean[size];}
  };
}
