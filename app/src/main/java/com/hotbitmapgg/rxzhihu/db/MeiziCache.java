package com.hotbitmapgg.rxzhihu.db;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hotbitmapgg.rxzhihu.base.RxZhihuApp;
import com.hotbitmapgg.rxzhihu.model.DoubanMeizi;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.List;

import io.realm.Realm;
import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * Created by hcc on 16/5/16 21:07
 * 100332338@qq.com
 */
public class MeiziCache
{

    private static String DATA_FILE_NAME = "meizi.db";

    private static MeiziCache mMeiziCache;

    private File mFile = new File(RxZhihuApp.getContext().getFilesDir(), DATA_FILE_NAME);

    private Gson mGson = new Gson();

    public static MeiziCache newInstance()
    {

        if (mMeiziCache == null)
        {
            synchronized (MeiziCache.class)
            {
                if (mMeiziCache == null)
                {
                    mMeiziCache = new MeiziCache();
                }
            }
        }

        return mMeiziCache;
    }

    public List<DoubanMeizi> readMeizi()
    {

        try
        {
            Thread.sleep(100);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }


        try
        {
            Reader mReader = new FileReader(mFile);
            return mGson.fromJson(mReader,
                    new TypeToken<List<DoubanMeizi>>()
                    {

                    }.getType());
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
            return null;
        }
    }


    public void writeMeizi(List<DoubanMeizi> meizis)
    {

        try
        {
            String json = mGson.toJson(meizis);
            if (!mFile.exists())
            {
                mFile.createNewFile();
            }

            Writer mWriter = new FileWriter(mFile);
            mWriter.write(json);
            mWriter.flush();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void clearCache()
    {

        mFile.delete();
    }

    public void putMeiziCache(Context context,int type, Response<ResponseBody> response)
    {

        try
        {

            Realm realm = Realm.getInstance(context);
            realm.beginTransaction();

            String string = response.body().string();
            Document parse = Jsoup.parse(string);
            Elements elements = parse.select("div[class=thumbnail]>div[class=img_single]>a>img");
            DoubanMeizi meizi;
            for (Element e : elements)
            {
                String src = e.attr("src");
                String title = e.attr("title");

                meizi = new DoubanMeizi();
                meizi.setUrl(src);
                meizi.setTitle(title);
                meizi.setType(type);

                realm.copyToRealm(meizi);
            }

            realm.commitTransaction();
            realm.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
