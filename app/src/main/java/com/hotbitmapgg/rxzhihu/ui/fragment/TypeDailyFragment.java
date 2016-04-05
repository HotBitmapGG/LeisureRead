package com.hotbitmapgg.rxzhihu.ui.fragment;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hotbitmapgg.rxzhihu.R;
import com.hotbitmapgg.rxzhihu.base.LazyFragment;
import com.hotbitmapgg.rxzhihu.model.DailyTypeBean;

import butterknife.Bind;

/**
 * Created by hcc on 16/4/4.
 */
public class TypeDailyFragment extends LazyFragment
{

    @Bind(R.id.type_image)
    ImageView mTypeImage;

    @Bind(R.id.type_title)
    TextView mTypeTitle;

    private static final String EXTRA_TYPE = "extra_type";

    private DailyTypeBean.SubjectDaily mSubjectDaily;

    public static TypeDailyFragment newInstance(DailyTypeBean.SubjectDaily subjectDaily)
    {

        TypeDailyFragment typeDailyFragment = new TypeDailyFragment();
        Bundle mBundle = new Bundle();
        mBundle.putSerializable(EXTRA_TYPE, subjectDaily);
        typeDailyFragment.setArguments(mBundle);

        return typeDailyFragment;
    }

    @Override
    public int getLayoutId()
    {

        return R.layout.fragment_type_daily;
    }

    @Override
    public void initViews()
    {

        mSubjectDaily = (DailyTypeBean.SubjectDaily) getArguments().getSerializable(EXTRA_TYPE);
        Glide.with(getActivity()).load(mSubjectDaily.getThumbnail()).into(mTypeImage);
        mTypeTitle.setText(mSubjectDaily.getDescription());
    }
}
