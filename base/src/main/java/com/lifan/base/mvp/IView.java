package com.lifan.base.mvp;


import com.lifan.base.utils.toast.ToastUtil;

public interface IView {

    default void showProgress(){

    }

    default void hideProgress(){

    }

    default void showToastShort(CharSequence msg){
        ToastUtil.showToastShort(msg);
    }

    default void showToastLong(CharSequence msg){
        ToastUtil.showToastLong(msg);
    }

}
