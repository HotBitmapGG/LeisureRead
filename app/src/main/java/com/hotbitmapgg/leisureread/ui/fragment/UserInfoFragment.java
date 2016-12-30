package com.hotbitmapgg.leisureread.ui.fragment;

import butterknife.OnClick;
import com.hotbitmapgg.leisureread.ui.fragment.base.BaseFragment;
import com.hotbitmapgg.leisureread.ui.activity.AppAboutActivity;
import com.hotbitmapgg.leisureread.ui.activity.FeedBackActivity;
import com.hotbitmapgg.leisureread.ui.activity.HotBitmapGGInfoActivity;
import com.hotbitmapgg.rxzhihu.R;

import android.content.Intent;

/**
 * Created by hcc on 2016/12/28 13:35
 * 100332338@qq.com
 * LeisureRead
 *
 * @HotBitmapGG 用户中心界面
 */
public class UserInfoFragment extends BaseFragment {

  public static UserInfoFragment newInstance() {

    return new UserInfoFragment();
  }


  @Override
  public int getLayoutId() {

    return R.layout.fragment_user;
  }


  @Override
  public void initViews() {

  }


  @Override
  public void initData() {

  }


  @OnClick(R.id.layout_user_info)
  void startHotBitmapGGInfo() {

    startActivity(new Intent(getActivity(), HotBitmapGGInfoActivity.class));
  }


  @OnClick(R.id.layout_feed_back)
  void startFeedBack() {

    startActivity(new Intent(getActivity(), FeedBackActivity.class));
  }


  @OnClick(R.id.layout_setting)
  void startSetting() {

  }


  @OnClick(R.id.layout_about_app)
  void startAboutApp() {

    startActivity(new Intent(getActivity(), AppAboutActivity.class));
  }
}
