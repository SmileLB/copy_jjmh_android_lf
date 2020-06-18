package com.lifan.live.activity;

import android.os.Bundle;

import com.lifan.base.mvp.BasePresenter;
import com.lifan.base.ui.BaseActivity;
import com.lifan.live.R;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class SplashActivity extends BaseActivity {
    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public BasePresenter setPresenter() {
        return null;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void dealWxLogin(String wxLoginEvent) {

    }

    @Override
    public boolean useEventBus() {
        return true;
    }
}
