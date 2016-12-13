package com.hotbitmapgg.eyepetizer.view.fragments;

import android.content.Intent;

import com.hotbitmapgg.eyepetizer.base.BaseFragment;
import com.hotbitmapgg.eyepetizer.view.activitys.AppAboutActivity;
import com.hotbitmapgg.eyepetizer.view.activitys.FeedBackActivity;
import com.hotbitmapgg.eyepetizer.view.activitys.HotBitmapGGInfoActivity;
import com.hotbitmapgg.rxzhihu.R;

import butterknife.OnClick;

/**
 * Created by hcc on 16/5/15 16:33
 * 100332338@qq.com
 */
public class UserInfoFragment extends BaseFragment
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

    @Override
    public void initData()
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
