package com.lifan.base.net;

import com.lifan.base.net.RequestHandler;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
public class CommonInterceptor implements Interceptor {

    RequestHandler handler;

    public CommonInterceptor(RequestHandler handler) {
        this.handler = handler;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (handler != null) {
            request = handler.onBeforeRequest(request, chain);
        }
        Response response = chain.proceed(request);
        if (handler != null) {
            response = handler.onAfterRequest(response, chain);
        }
        return response;
    }
}
