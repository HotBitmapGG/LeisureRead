package com.hotbitmapgg.eyepetizer.ui.fragment;

import android.content.Intent;

import com.hotbitmapgg.eyepetizer.base.LazyFragment;
import com.hotbitmapgg.eyepetizer.ui.activity.AppAboutActivity;
import com.hotbitmapgg.eyepetizer.ui.activity.FeedBackActivity;
import com.hotbitmapgg.eyepetizer.ui.activity.HotBitmapGGInfoActivity;
import com.hotbitmapgg.rxzhihu.R;

import butterknife.OnClick;

/**
 * Created by hcc on 16/5/15 16:33
 * 100332338@qq.com
 */
public class UserInfoFragment extends LazyFragment
{

    public static UserInfoFragment newInstance()
    {

        return new UserInfoFragment();
    }

    @Override
    public int getLayoutId()
    {

        return R.layout.fragment_user;
    }

    @Override
    public void initViews()
    {

    }


    @OnClick(R.id.more_btn_info)
    void startHotBitmapGGInfo()
    {

        startActivity(new Intent(getActivity(), HotBitmapGGInfoActivity.class));
    }


    @OnClick(R.id.more_btn_feed_back)
    void startFeedBack()
    {

        startActivity(new Intent(getActivity(), FeedBackActivity.class));
    }

    @OnClick(R.id.more_btn_setting)
    void startSetting()
    {

    }

    @OnClick(R.id.more_btn_about_app)
    void startAboutApp()
    {

        startActivity(new Intent(getActivity(), AppAboutActivity.class));
    }
}
