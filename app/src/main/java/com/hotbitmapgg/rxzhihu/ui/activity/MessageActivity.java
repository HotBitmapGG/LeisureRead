package com.hotbitmapgg.rxzhihu.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hotbitmapgg.rxzhihu.R;
import com.hotbitmapgg.rxzhihu.base.AbsBaseActivity;
import com.hotbitmapgg.rxzhihu.base.RxZhihuApp;
import com.hotbitmapgg.rxzhihu.model.RxZhiHuMessage;
import com.hotbitmapgg.rxzhihu.utils.LogUtil;

import butterknife.Bind;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.listener.SaveListener;


/**
 * Created by hcc on 16/5/15 16:33
 * 100332338@qq.com
 */
public class MessageActivity extends AbsBaseActivity implements View.OnClickListener
{


    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.feed_edit)
    EditText mFeedBack;

    @Bind(R.id.tip)
    TextView mTip;

    @Bind(R.id.btn_submit)
    Button mSubmit;


    @Override
    public int getLayoutId()
    {

        return R.layout.activity_feedback;
    }

    @Override
    public void initViews(Bundle savedInstanceState)
    {

        //初始化Bmob
        Bmob.initialize(this, RxZhihuApp.BMBO_KEY);

        mSubmit.setOnClickListener(this);
        mFeedBack.addTextChangedListener(new TextWatcher()
        {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

                mTip.setText(String.valueOf(160 - s.length()));
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                // TODO Auto-generated method stub

            }
        });
    }
    @Override
    public void initToolBar()
    {
        mToolbar.setTitle("意见反馈");
        setSupportActionBar(mToolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null)
            supportActionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        if(item.getItemId() == android.R.id.home)
        {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v)
    {

        if (v.getId() == R.id.btn_submit)
        {
            String text = mFeedBack.getText().toString().trim();
            if (TextUtils.isEmpty(text))
            {
                Toast.makeText(MessageActivity.this, "输入的内容不能为空", Toast.LENGTH_SHORT).show();
                return;
            }

            sendFeedBackText(text);
        }
    }

    private void sendFeedBackText(String text)
    {

        RxZhiHuMessage mMessage = new RxZhiHuMessage();
        mMessage.setContent(text);
        mMessage.save(MessageActivity.this, new SaveListener()
        {

            @Override
            public void onSuccess()
            {
                // TODO Auto-generated method stub
                Toast.makeText(MessageActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
                mFeedBack.setText("");
            }

            @Override
            public void onFailure(int errorCode, String errorMsg)
            {
                // TODO Auto-generated method stub
                Toast.makeText(MessageActivity.this, "提交失败", Toast.LENGTH_SHORT).show();
                LogUtil.all(errorMsg);
            }
        });
    }
}
