package com.hotbitmapgg.eyepetizer.utils;

import com.hotbitmapgg.eyepetizer.EyepetizerDailyApp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 网络工具类
 */
public class NetWorkUtil {

  private NetWorkUtil() {

  }


  public static boolean isNetworkConnected() {

    if (EyepetizerDailyApp.getAppContext() != null) {
      ConnectivityManager mConnectivityManager
          = (ConnectivityManager) EyepetizerDailyApp.getAppContext()
          .getSystemService(Context.CONNECTIVITY_SERVICE);
      NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
      if (mNetworkInfo != null) {
        return mNetworkInfo.isAvailable();
      }
    }
    return false;
  }


  public static boolean isWifiConnected() {

    if (EyepetizerDailyApp.getAppContext() != null) {
      ConnectivityManager mConnectivityManager
          = (ConnectivityManager) EyepetizerDailyApp.getAppContext()
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

    if (EyepetizerDailyApp.getAppContext() != null) {
      ConnectivityManager mConnectivityManager
          = (ConnectivityManager) EyepetizerDailyApp.getAppContext()
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

    if (EyepetizerDailyApp.getAppContext() != null) {
      ConnectivityManager mConnectivityManager
          = (ConnectivityManager) EyepetizerDailyApp.getAppContext()
          .getSystemService(Context.CONNECTIVITY_SERVICE);
      NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
      if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {
        return mNetworkInfo.getType();
      }
    }
    return -1;
  }
}
