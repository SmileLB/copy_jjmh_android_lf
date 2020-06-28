package com.lifan.comic.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.lifecycle.Lifecycle;

import com.lifan.base.mvp.BasePresenter;
import com.lifan.base.net.ApiSubscriber2;
import com.lifan.base.net.NetError;
import com.lifan.base.ui.LazyFragment;
import com.lifan.base.utils.toast.ToastUtil;
import com.lifan.comic.R;
import com.lifan.comic.R2;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class OneFragment extends LazyFragment {

    @BindView(R2.id.tv)
    TextView tv;


    @Override
    public int getLayoutId() {
        return R.layout.comic_fragment_common;
    }

    @Override
    public BasePresenter setPresenter() {
        return null;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        showProgress();
        Observable.timer( 1 , TimeUnit.SECONDS )
                .observeOn(AndroidSchedulers.mainThread())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))
                .subscribe(new ApiSubscriber2<Long>() {
                    @Override
                    protected void onFail(NetError error) {
                        ToastUtil.showToastShort("推荐加载出错");
                    }

                    @Override
                    public void onNext(Long aLong) {
                        tv.setText("推荐");
                        hideProgress();
                    }
                }) ;
    }

}
