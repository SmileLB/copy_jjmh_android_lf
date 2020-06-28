package com.lifan.base.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.lifan.base.dialog.CustomProgressDialog;
import com.lifan.base.mvp.BasePresenter;
import com.lifan.base.mvp.IView;
import com.lifan.base.utils.toast.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 配合ViewPager实现
 * mAdapter = new MyPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,
 *                 mFragments);
 * @param <P>
 */
public abstract class LazyFragment<P extends BasePresenter> extends Fragment implements IView {

    private boolean isFirstLoad = true; // 是否第一次加载
    private Bundle savedInstanceState;
    private P p;
    private Unbinder unbinder;
    private Dialog dialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (p == null) {
            p = setPresenter();
        }
        if (p != null) {
            getLifecycle().addObserver(p);
            p.attachV(this);
        }
    }

    /**
     * activity可能由各种情景引发导致activity被回收 此时直接{@link #getArguments()}方法获取参数全部为null
     * 这里为了方便处理 把{@link #onSaveInstanceState}现场保护传的参数全部放入{@link #getArguments()} 以便回收后再次进入activity时getArguments()可以获取到参数
     * @param savedInstanceState
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (getArguments() != null)
                getArguments().putAll(savedInstanceState);
        }
        View view = null;
        if (getLayoutId() > 0) {
            view = inflater.inflate(getLayoutId(), container, false);
            bindUI(view);
        }
        return view == null ? super.onCreateView(inflater, container, savedInstanceState) : view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.savedInstanceState=savedInstanceState;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (useEventBus() && !EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
    }

    public void bindUI(View rootView) {
        unbinder = ButterKnife.bind(this, rootView);
    }

    public abstract int getLayoutId();

    public abstract P setPresenter();

    public P getP() {
        return p;
    }

    public BaseActivity getBaseActivity() {
        if (getActivity() instanceof BaseActivity){
            return (BaseActivity) getActivity();
        }
        return null;
    }

    @Override
    public void onDestroyView() {
        if (useEventBus() && EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);
        if (unbinder != null) unbinder.unbind();
        if (dialog != null) {
            if (dialog.isShowing()) dialog.dismiss();
            dialog = null;
        }
        super.onDestroyView();
        isFirstLoad = true;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isFirstLoad) {
            // 将数据加载逻辑放到onResume()方法中
            initData(savedInstanceState);
            isFirstLoad = false;
        }
    }

    public boolean useEventBus() {
        return false;
    }

    @Override
    public void showProgress() {
        if (dialog == null) {
            if (getActivity() == null) {
                return;
            }
            dialog = new CustomProgressDialog(getActivity(),false);
        }
        if (!dialog.isShowing()) dialog.show();
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

    /**
     * activity现场保护方法
     * 备注：activity可能会被回收 此时需要把相关参数存放到bundle里 防止再次打开activity时getArguments()获取参数为空
     * outState.putAll(getArguments()) 是指把传进来的参数全部放到bundle里
     *
     * @param outState
     */
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (getArguments() != null) {
            outState.putAll(getArguments());
        }
    }

    /**
     * 初始化数据
     */
    protected abstract void initData(Bundle savedInstanceState);
}