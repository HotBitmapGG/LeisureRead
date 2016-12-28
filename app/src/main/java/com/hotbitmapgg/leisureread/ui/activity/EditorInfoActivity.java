package com.hotbitmapgg.leisureread.ui.activity;

import butterknife.Bind;
import com.hotbitmapgg.leisureread.app.AppConstant;
import com.hotbitmapgg.leisureread.ui.activity.base.BaseAppCompatActivity;
import com.hotbitmapgg.leisureread.widget.CircleProgressView;
import com.hotbitmapgg.rxzhihu.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by hcc on 2016/12/28 13:35
 * 100332338@qq.com
 * LeisureRead
 *
 * @HotBitmapGG 编辑详情信息界面
 */
public class EditorInfoActivity extends BaseAppCompatActivity {

  @Bind(R.id.toolbar)
  Toolbar mToolbar;

  @Bind(R.id.editor_web)
  WebView mWebView;

  @Bind(R.id.circle_progress)
  CircleProgressView mCircleProgressView;

  private int id;

  private String name;

  private String url;

  WebViewClientBase webViewClient = new WebViewClientBase();


  @Override
  public int getLayoutId() {

    return R.layout.activity_editor_info;
  }


  @Override
  public void initViews(Bundle savedInstanceState) {

    Intent intent = getIntent();
    if (intent != null) {
      id = intent.getIntExtra(AppConstant.EXTRA_ID, -1);
      name = intent.getStringExtra(AppConstant.EXTRA_NAME);
    }

    url = "http://news-at.zhihu.com/api/4/editor/" + id + "/profile-page/android";
    showProgress();
    setupWebView();
  }


  @Override
  public void initToolBar() {

    mToolbar.setTitleTextColor(getResources().getColor(R.color.black_90));
    mToolbar.setTitle(name);
    mToolbar.setNavigationIcon(R.drawable.ic_action_back);
    mToolbar.setNavigationOnClickListener(view -> onBackPressed());
  }


  public static void launch(Activity activity, int id, String name) {

    Intent mIntent = new Intent(activity, EditorInfoActivity.class);
    mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    mIntent.putExtra(AppConstant.EXTRA_ID, id);
    mIntent.putExtra(AppConstant.EXTRA_NAME, name);
    activity.startActivity(mIntent);
  }


  @SuppressLint("SetJavaScriptEnabled")
  private void setupWebView() {

    final WebSettings webSettings = mWebView.getSettings();
    webSettings.setJavaScriptEnabled(true);
    webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
    webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
    webSettings.setDomStorageEnabled(true);
    webSettings.setGeolocationEnabled(true);
    mWebView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
    mWebView.getSettings().setBlockNetworkImage(true);
    mWebView.setWebViewClient(webViewClient);
    mWebView.requestFocus(View.FOCUS_DOWN);
    mWebView.getSettings().setDefaultTextEncodingName("UTF-8");
    mWebView.setWebChromeClient(new WebChromeClient() {

      @Override
      public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {

        AlertDialog.Builder b2 = new AlertDialog.Builder(EditorInfoActivity.this).setTitle(
            R.string.app_name)
            .setMessage(message)
            .setPositiveButton("确定", (dialog, which) -> result.confirm());

        b2.setCancelable(false);
        b2.create();
        b2.show();
        return true;
      }
    });
    mWebView.loadUrl(url);
  }


  public class WebViewClientBase extends WebViewClient {

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {

      super.onPageStarted(view, url, favicon);
    }


    @Override
    public void onPageFinished(WebView view, String url) {

      super.onPageFinished(view, url);
      hideProgress();
      mWebView.getSettings().setBlockNetworkImage(false);
    }


    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {

      super.onReceivedError(view, request, error);
      String errorHtml = "<html><body><h2>找不到网页</h2></body><ml>";
      view.loadDataWithBaseURL(null, errorHtml, "textml", "UTF-8", null);
    }
  }


  public void showProgress() {

    mCircleProgressView.setVisibility(View.VISIBLE);
    mCircleProgressView.spin();
  }


  public void hideProgress() {

    mCircleProgressView.setVisibility(View.GONE);
    mCircleProgressView.stopSpinning();
  }
}
