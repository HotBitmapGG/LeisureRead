package com.hotbitmapgg.leisureread.utils;

import com.hotbitmapgg.leisureread.LeisureReadApp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by hcc on 2016/12/28 13:35
 * 100332338@qq.com
 * LeisureRead
 *
 * @HotBitmapGG 网络工具类
 */
public class NetWorkUtil {

  private NetWorkUtil() {

  }


  public static boolean isNetworkConnected() {

    if (LeisureReadApp.getAppContext() != null) {
      ConnectivityManager mConnectivityManager
          = (ConnectivityManager) LeisureReadApp.getAppContext()
          .getSystemService(Context.CONNECTIVITY_SERVICE);
      NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
      if (mNetworkInfo != null) {
        return mNetworkInfo.isAvailable();
      }
    }
    return false;
  }


  public static boolean isWifiConnected() {

    if (LeisureReadApp.getAppContext() != null) {
      ConnectivityManager mConnectivityManager
          = (ConnectivityManager) LeisureReadApp.getAppContext()
          .getSystemService(Context.CONNECTIVITY_SERVICE);
      NetworkInfo mWiFiNetworkInfo = mConnectivityManager.getNetworkInfo(
          ConnectivityManager.TYPE_WIFI);
      if (mWiFiNetworkInfo != null) {
        return mWiFiNetworkInfo.isAvailable();
      }
    }
    return false;
  }


  public static boolean isMobileConnected() {

    if (LeisureReadApp.getAppContext() != null) {
      ConnectivityManager mConnectivityManager
          = (ConnectivityManager) LeisureReadApp.getAppContext()
          .getSystemService(Context.CONNECTIVITY_SERVICE);
      NetworkInfo mMobileNetworkInfo = mConnectivityManager
          .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
      if (mMobileNetworkInfo != null) {
        return mMobileNetworkInfo.isAvailable();
      }
    }
    return false;
  }


  public static int getConnectedType() {

    if (LeisureReadApp.getAppContext() != null) {
      ConnectivityManager mConnectivityManager
          = (ConnectivityManager) LeisureReadApp.getAppContext()
          .getSystemService(Context.CONNECTIVITY_SERVICE);
      NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
      if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {
        return mNetworkInfo.getType();
      }
    }
    return -1;
  }
}
