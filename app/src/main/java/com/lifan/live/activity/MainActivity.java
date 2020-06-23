package com.lifan.live.activity;

import android.os.Bundle;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.gyf.barlibrary.ImmersionBar;
import com.lifan.base.router.RouterMap;
import com.lifan.base.ui.BaseActivity;
import com.lifan.live.R;
import com.lifan.live.present.MainContract;
import com.lifan.live.present.MainPresenter;

@Route(path = RouterMap.COMIC_MAIN_ACTIVITY)
public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.IMainView {


    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public MainPresenter setPresenter() {
        return new MainPresenter();
    }

    @Override
    protected void initImmersionBar() {
        ImmersionBar.with(MainActivity.this).init();
    }
}
