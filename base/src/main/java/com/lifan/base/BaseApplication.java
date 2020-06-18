package com.lifan.base;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.lifan.base.log.LogUtil;

public abstract class BaseApplication extends Application implements Application.ActivityLifecycleCallbacks {

    private static BaseApplication instance;
    protected Activity topActivity;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        registerActivityLifecycleCallbacks(this);
        init();
    }

    public abstract void init();

    public static BaseApplication getApplication(){
        return instance;
    }

    @Override
    public void onActivityStarted(Activity activity) {
        topActivity = activity;
    }

    @Override
    public void onActivityResumed(Activity activity) {
        topActivity = activity;
        LogUtil.e("TOP ACTIVITY:" + activity.getComponentName());
    }
}
