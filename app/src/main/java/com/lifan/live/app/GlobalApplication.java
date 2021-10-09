package com.lifan.live.app;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import androidx.multidex.MultiDex;
import com.alibaba.android.arouter.launcher.ARouter;
import com.lifan.base.BaseApplication;
import com.lifan.base.log.LogUtil;
import com.lifan.comic.common.constants.Constants;
import com.lifan.comic.greendao.DaoHelper;
import com.lifan.comic.util.enentbus.EventBusHelper;
import java.util.List;
import io.reactivex.functions.Consumer;
import io.reactivex.plugins.RxJavaPlugins;
import me.jessyan.autosize.AutoSizeConfig;

public class GlobalApplication extends BaseApplication {

    private DaoHelper mDaoHelper;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //处理方法数超过65535
        MultiDex.install(this);
    }

    @Override
    public void init() {
        if (isMainProcess()) {
            //初始化Arouter
            initArouter();
            //初始化AutoSize
            initAutoSizeConfig();
            //全局捕获Rxjava异常
            initRxJavaException();
            //初始化网络请求
            HttpUtil.initHttp();
            //初始化EventBus
            EventBusHelper.init();
            //适配通知栏
            initNotificationChannel();
            //初始化GreenDAO
            mDaoHelper = new DaoHelper();
        }
    }
    //因为低版本的手机系统并没有通知渠道这个功能，不做系统版本检查的话会在低版本手机上造成崩溃。
    private void initNotificationChannel() {
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationManager manager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel channel1 = new NotificationChannel("update", "更新", NotificationManager.IMPORTANCE_HIGH);
            NotificationChannel channel2 = new NotificationChannel("info", "通知", NotificationManager.IMPORTANCE_HIGH);
            NotificationChannel channel3 = new NotificationChannel("user", "提醒", NotificationManager.IMPORTANCE_HIGH);

            channel1.setShowBadge(true);
            channel2.setShowBadge(true);
            channel3.setShowBadge(true);
            if (manager != null) {
                manager.createNotificationChannel(channel1);
                manager.createNotificationChannel(channel2);
                manager.createNotificationChannel(channel3);
            }
        }
    }


    private void initRxJavaException() {
        //处理Rxjava全局捕获异常，防止下游终止订阅之后，上游有未处理的异常导致崩溃
        if (!Constants.DEBUG) {//如果是调试模式就不开启，这样方便排查BUG
            RxJavaPlugins.setErrorHandler(new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Exception {
                    LogUtil.e(this.getClass().getName() + ":Rxjava全局捕获的异常-" + throwable.getMessage());
                }
            });
        }
    }

    private void initAutoSizeConfig() {
        AutoSizeConfig.getInstance()

                //是否让框架支持自定义 Fragment 的适配参数, 由于这个需求是比较少见的, 所以须要使用者手动开启
                //如果没有这个需求建议不开启
                .setCustomFragment(true)

                //是否屏蔽系统字体大小对 AndroidAutoSize 的影响, 如果为 true, App 内的字体的大小将不会跟随系统设置中字体大小的改变
                //如果为 false, 则会跟随系统设置中字体大小的改变, 默认为 false
                .setExcludeFontScale(true)

        //屏幕适配监听器
//                .setOnAdaptListener(new onAdaptListener() {
//                    @Override
//                    public void onAdaptBefore(Object target, Activity activity) {
//                        //使用以下代码, 可支持 Android 的分屏或缩放模式, 但前提是在分屏或缩放模式下当用户改变您 App 的窗口大小时
//                        //系统会重绘当前的页面, 经测试在某些机型, 某些情况下系统不会重绘当前页面, ScreenUtils.getScreenSize(activity) 的参数一定要不要传 Application!!!
//                        AutoSizeConfig.getInstance().setScreenWidth(ScreenUtils.getScreenSize(activity)[0]);
//                        AutoSizeConfig.getInstance().setScreenHeight(ScreenUtils.getScreenSize(activity)[1]);
//                        LogUtils.d(String.format(Locale.ENGLISH, "%s onAdaptBefore!", target.getClass().getName()));
//                    }
//
//                    @Override
//                    public void onAdaptAfter(Object target, Activity activity) {
//                        LogUtils.d(String.format(Locale.ENGLISH, "%s onAdaptAfter!", target.getClass().getName()));
//                    }
//                })

        //是否打印 AutoSize 的内部日志, 默认为 true, 如果您不想 AutoSize 打印日志, 则请设置为 false
//                .setLog(false)

        //是否使用设备的实际尺寸做适配, 默认为 false, 如果设置为 false, 在以屏幕高度为基准进行适配时
        //AutoSize 会将屏幕总高度减去状态栏高度来做适配
        //设置为 true 则使用设备的实际屏幕高度, 不会减去状态栏高度
//                .setUseDeviceSize(true)

        //是否全局按照宽度进行等比例适配, 默认为 true, 如果设置为 false, AutoSize 会全局按照高度进行适配
//                .setBaseOnWidth(false)

        //设置屏幕适配逻辑策略类, 一般不用设置, 使用框架默认的就好
//                .setAutoAdaptStrategy(new AutoAdaptStrategy())
        ;
    }

    private void initArouter() {
        if (Constants.DEBUG) {
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(this);
    }

    public boolean isMainProcess() {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        if (activityManager != null) {
            List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = activityManager.getRunningAppProcesses();
            if (runningAppProcesses != null) {
                for (ActivityManager.RunningAppProcessInfo appProcess : runningAppProcesses) {
                    if (appProcess.pid == pid) {
                        return getApplicationInfo().packageName.equals(appProcess.processName);
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
