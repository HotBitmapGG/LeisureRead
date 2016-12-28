package com.hotbitmapgg.leisureread.ui.activity;

import butterknife.Bind;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.listener.SaveListener;
import com.hotbitmapgg.leisureread.LeisureReadApp;
import com.hotbitmapgg.leisureread.ui.activity.base.BaseAppCompatActivity;
import com.hotbitmapgg.leisureread.mvp.model.entity.FeedBackInfo;
import com.hotbitmapgg.leisureread.utils.LogUtil;
import com.hotbitmapgg.rxzhihu.R;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by hcc on 2016/12/28 13:35
 * 100332338@qq.com
 * LeisureRead
 *
 * @HotBitmapGG 意见反馈界面
 */
public class FeedBackActivity extends BaseAppCompatActivity implements View.OnClickListener {

  @Bind(R.id.toolbar)
  Toolbar mToolbar;

  @Bind(R.id.feed_edit)
  EditText mFeedBack;

  @Bind(R.id.tip)
  TextView mTip;

  @Bind(R.id.btn_submit)
  Button mSubmit;


  @Override
  public int getLayoutId() {

    return R.layout.activity_feedback;
  }


  @Override
  public void initViews(Bundle savedInstanceState) {

    //初始化Bmob
    Bmob.initialize(this, LeisureReadApp.BMBO_KEY);

    mSubmit.setOnClickListener(this);
    mFeedBack.addTextChangedListener(new TextWatcher() {

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {

        mTip.setText(String.valueOf(160 - s.length()));
      }


      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // TODO Auto-generated method stub

      }


      @Override
      public void afterTextChanged(Editable s) {
        // TODO Auto-generated method stub

      }
    });
  }


  @Override
  public void initToolBar() {

    mToolbar.setTitleTextColor(getResources().getColor(R.color.black_90));
    mToolbar.setTitle("意见反馈");
    mToolbar.setNavigationIcon(R.drawable.ic_action_back);
    mToolbar.setNavigationOnClickListener(view -> onBackPressed());
  }


  @Override
  public void onClick(View v) {

    if (v.getId() == R.id.btn_submit) {
      String text = mFeedBack.getText().toString().trim();
      if (TextUtils.isEmpty(text)) {
        Toast.makeText(FeedBackActivity.this, "输入的内容不能为空", Toast.LENGTH_SHORT).show();
        return;
      }

      sendFeedBackText(text);
    }
  }


  private void sendFeedBackText(String text) {

    FeedBackInfo mMessage = new FeedBackInfo();
    mMessage.setContent(text);
    mMessage.save(FeedBackActivity.this, new SaveListener() {

      @Override
      public void onSuccess() {
        // TODO Auto-generated method stub
        Toast.makeText(FeedBackActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
        mFeedBack.setText("");
      }


      @Override
      public void onFailure(int errorCode, String errorMsg) {
        // TODO Auto-generated method stub
        Toast.makeText(FeedBackActivity.this, "提交失败", Toast.LENGTH_SHORT).show();
        LogUtil.all(errorMsg);
      }
    });
  }
}
