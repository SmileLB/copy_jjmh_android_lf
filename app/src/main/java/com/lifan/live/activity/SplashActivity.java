package com.lifan.live.activity;

import android.os.Bundle;

import com.alibaba.android.arouter.launcher.ARouter;
import com.gyf.barlibrary.BarHide;
import com.gyf.barlibrary.ImmersionBar;
import com.lifan.base.router.RouterMap;
import com.lifan.base.ui.BaseActivity;
import com.lifan.live.R;
import com.lifan.live.present.SplashContract;
import com.lifan.live.present.SplashPresenter;

import butterknife.OnClick;

public class SplashActivity extends BaseActivity<SplashPresenter> implements SplashContract.ISplashView {
    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public SplashPresenter setPresenter() {
        return new SplashPresenter();
    }

    @Override
    protected void initImmersionBar() {
        ImmersionBar.with(this)
                .fitsSystemWindows(false)
                .keyboardEnable(false)
                .statusBarDarkFont(true, 0.2f)
                .hideBar(BarHide.FLAG_HIDE_STATUS_BAR)
                .init();
    }

    @Override
    protected boolean isImmersionBarEnabled() {
        return true;
    }

    @OnClick(R.id.splash_container)
    public void onViewClicked() {
        ARouter.getInstance().build(RouterMap.COMIC_MAIN_ACTIVITY).navigation(this);
        finish();
    }
}
