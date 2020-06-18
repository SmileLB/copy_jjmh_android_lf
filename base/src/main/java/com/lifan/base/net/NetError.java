package com.lifan.base.net;


import com.lifan.base.BaseApplication;
import com.lifan.base.R;

public class NetError extends Exception {
    private Throwable exception;
    private int type = NoConnectError;

    public static final int NotSubscribe = 0;
    public static final int NoConnectError = 1;   //无连接异常
    public static final int ParseError = 2;   //数据解析异常
    public static final int AuthError = 403;   //token过期(新后台描述叫：用户身份验证错误)
    public static final int TokenNull = -107;   //token为空
    public static final int BalanceError = -22;   //余额不足
    public static final int NoDataError = 3;   //无数据
    public static final int BusinessError = 4;   //业务异常
    public static final int OtherError = 5;   //其他异常
    public static final int NullDataError = 6;   //数据为null
    public static final int DataOverSizeError = 1004;//参数超过指定长度

    public NetError(Throwable exception, int type) {
        this.exception = exception;
        this.type = type;
    }

    public NetError(String detailMessage, int type) {
        super(detailMessage);
        this.type = type;
    }

    public static NetError noDataError() {
        return new NetError(BaseApplication.getApplication().getString(R.string.base_empty_data), NetError.NoDataError);
    }

    public static NetError authError() {
        return new NetError("登录已失效", NetError.AuthError);
    }

    public static NetError noConnectError(String message) {
        NetError netError = new NetError(BaseApplication.getApplication().getString(R.string.base_connect_error), NetError.NoConnectError);
        return netError;
    }

    @Override
    public String getMessage() {
        if (exception != null) return exception.getMessage();
        return super.getMessage();
    }

    public Throwable getException() {
        return exception;
    }

    public int getType() {
        return type;
    }
}
