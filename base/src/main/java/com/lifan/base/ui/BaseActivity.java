package com.lifan.base.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleRegistry;

import com.gyf.barlibrary.ImmersionBar;
import com.lifan.base.R;
import com.lifan.base.dialog.CustomProgressDialog;
import com.lifan.base.mvp.BasePresenter;
import com.lifan.base.mvp.IView;
import com.lifan.base.utils.toast.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity <P extends BasePresenter> extends AppCompatActivity implements IView {
    protected BaseActivity context;
    private P p;
    private Unbinder unbinder;
    private Dialog dialog;
    private InputMethodManager mInputMethodManager;
    private Bundle savedInstanceState;
    private boolean isStart;//标记当前activity已经走过一次onStart
    private NetStatusReceiver netReceiver;

    /**
     * activity可能由各种情景引发导致activity被回收 此时直接{@link #getIntent()}方法获取参数全部为null
     * 这里为了方便处理 把{@link #onSaveInstanceState}现场保护传的参数全部放入{@link #getIntent()}
     * 以便回收后再次进入activity时getIntent()可获取到参数
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        this.savedInstanceState = savedInstanceState;
        if (savedInstanceState != null) {
            if (getIntent() != null){
                getIntent().putExtras(savedInstanceState);
            }
        }

        super.onCreate(savedInstanceState);

        context = this;

        if (getLayoutId() > 0) {
            //设置布局并绑定ButterKnife
            setContentView(getLayoutId());
            bindUI(null);
        }

        if (p == null){
            p = setPresenter();
        }

//        lifecycleRegistry = new LifecycleRegistry(this);
//        myObserver = new MyObserver();
//        lifecycleRegistry.addObserver(myObserver);
//        lifecycleRegistry.markState(Lifecycle.State.CREATED)

        if (p != null) {
            p.attachV(this);
            //把Presenter交给LifeCycle管理就OK了，Presenter 实现了 LifecycleObserver
            //把activity的生命周期交给Presenter
            getLifecycle().addObserver(p);
        }

//        该方法是【友盟+】Push后台进行日活统计及多维度推送的必调用方法
//        PushAgent.getInstance(context).onAppStart();
//        initData(savedInstanceState);

        if (isImmersionBarEnabled()) {
            initImmersionBar();
        }

        if (useEventBus() && !EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
        initRegistNetStatusReceiver();
    }

    @Override
    protected void onStart() {
        super.onStart();
        /**
         * 初始化的操作方这里是因为init里面可能有网络请求，会操作到当前界面的lifecycle对象，
         * 但发送当前生命周期到BasePresenter里面的时候会有一定的延时，从而导致bindLifecycle操作空指针
         */
        if (!isStart) {
            initData(savedInstanceState);
        }
        isStart = true;
    }

    protected abstract void initData(Bundle savedInstanceState);

    protected abstract int getLayoutId();

    public void bindUI(View rootView) {
        unbinder = ButterKnife.bind(this);
    }

    public abstract P setPresenter();

    public P getP() {
        return p;
    }

    /**
     * 是否可以使用沉浸式
     * Is immersion bar enabled boolean.
     * @return the boolean
     */
    protected boolean isImmersionBarEnabled() {
        return true;
    }

    protected void initImmersionBar() {
//        在BaseActivity里初始化
        ImmersionBar.with(this)
                .reset()
                .fitsSystemWindows(true)
                .keyboardEnable(false)
                .statusBarDarkFont(true, 0.2f)
                .statusBarColor(R.color.base_color_ffffff)
//                .navigationBarColor(R.color.base_color_ffffff)
                .init();
    }

    public boolean useEventBus() {
        return false;
    }

    private void initRegistNetStatusReceiver() {
        netReceiver = new NetStatusReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(netReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        if (useEventBus() && EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
        if (unbinder != null){
            unbinder.unbind();
        }
        if (dialog != null) {
            if (dialog.isShowing()) dialog.dismiss();
            dialog = null;
        }
        ImmersionBar.with(this).destroy();
        super.onDestroy();
        if (netReceiver != null) {
            unregisterReceiver(netReceiver);
        }
    }

    @Override
    public void showProgress() {
        if (dialog == null){
            dialog = new CustomProgressDialog(this);
        }
        if (!dialog.isShowing()){
            dialog.show();
        }
    }

    /**
     * 当activity设置了状态栏沉浸式样式，首先使activity实现DialogInterface.OnDismissListener接口，并调用此方法显示
     * dialog，然后在avtivity回调的onDismiss方法中重新设置activity的沉浸式样式
     * @param onDismissListener
     */
    public void showProgress(DialogInterface.OnDismissListener onDismissListener) {
        if (dialog == null) dialog = new CustomProgressDialog(this);
        if (!dialog.isShowing()) dialog.show();
        dialog.setOnDismissListener(onDismissListener);
    }

    @Override
    public void hideProgress() {
        if (dialog != null && dialog.isShowing()) dialog.dismiss();
    }

    @Override
    public void showToastShort(CharSequence msg) {
        ToastUtil.showToastShort(msg);
    }

    @Override
    public void showToastLong(CharSequence msg) {
        ToastUtil.showToastLong(msg);
    }

    public void onResume() {
        super.onResume();
        if (!hasFragment()) {
//            MobclickAgent.onPageStart(getClass().getName()); //手动统计页面
        }
//        MobclickAgent.onResume(this);
    }

    public boolean hasFragment() {
        return false;
    }

    @Override
    public void finish() {
        super.finish();
        hideSoftKeyBoard();
    }

    public void hideSoftKeyBoard() {
        View localView = getCurrentFocus();
        if (this.mInputMethodManager == null) {
            this.mInputMethodManager = ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE));
        }
        if ((localView != null) && (this.mInputMethodManager != null)) {
            this.mInputMethodManager.hideSoftInputFromWindow(localView.getWindowToken(), 2);
        }
    }

    /**
     * activity现场保护方法
     * 备注：activity可能会被回收 此时需要把相关参数存放到bundle里 防止再次打开activity时getIntent()获取参数为空
     * outState.putAll(getIntent().getExtras()) 是指把传进来的参数全部放到bundle里
     *
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (getIntent() != null && getIntent().getExtras() != null) {
            outState.putAll(getIntent().getExtras());
        }
    }
}
