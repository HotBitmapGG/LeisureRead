package com.hotbitmapgg.leisureread.utils;

import java.text.SimpleDateFormat;

import android.annotation.SuppressLint;

/**
 * Created by hcc on 2016/12/28 13:35
 * 100332338@qq.com
 * LeisureRead
 *
 * @HotBitmapGG 时间工具类
 */
public class DateUtil {

  private DateUtil() {

  }


  public static String formatDate(String date) {

    String dateFormat = null;
    try {
      dateFormat = date.substring(4, 6) + "月" + date.substring(6, 8) + "日";
    } catch (Exception e) {
      e.printStackTrace();
    }
    return dateFormat;
  }


  @SuppressLint("SimpleDateFormat")
  public static String getTime(long date) {
    SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm:ss");
    return format.format(date);
  }
}
