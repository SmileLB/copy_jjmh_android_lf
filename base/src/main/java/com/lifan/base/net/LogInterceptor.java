package com.lifan.base.net;

import android.util.Log;


import com.lifan.base.log.LogUtil;

import java.io.IOException;
import java.net.URLDecoder;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;


public class LogInterceptor implements Interceptor {
    public static String TAG = "Response_log";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
//        logRequest(request);
        Response response = chain.proceed(request);
        return logResponse(response,request);
    }


    private void logRequest(Request request) {
        try {
            String url = request.url().toString();
            Headers headers = request.headers();
            TAG = "XDroid_Net";

            LogUtil.d(TAG, "url : " + url);
            LogUtil.d(TAG, "method : " + request.method());
            if (headers != null && headers.size() > 0) {
                LogUtil.e(TAG, "headers : " + headers.toString());
            }
            RequestBody requestBody = request.body();
            if (requestBody != null) {
                MediaType mediaType = requestBody.contentType();
                if (mediaType != null) {
                    if (isText(mediaType)) {
                        LogUtil.d(TAG, "params : " + bodyToString(request));
                    } else {
                        LogUtil.d(TAG, "params : " + " maybe [file part] , too large too print , ignored!");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Response logResponse(Response response, Request request) {
        try {
            String url = request.url().toString();
            Headers headers = request.headers();

            LogUtil.header(TAG);

            LogUtil.d(TAG, "url : " + URLDecoder.decode(url));
            LogUtil.d(TAG, "method : " + request.method());
            if (headers != null && headers.size() > 0) {
                LogUtil.d(TAG, "headers : " + headers.toString());
            }
            RequestBody requestBody = request.body();
            if (requestBody != null) {
                MediaType mediaType = requestBody.contentType();
                if (mediaType != null) {
                    if (isText(mediaType)) {
                        LogUtil.d(TAG, "params : " + bodyToString(request));
                    } else {
                        LogUtil.d(TAG, "params : " + " maybe [file part] , too large too print , ignored!");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Response.Builder builder = response.newBuilder();
            Response clone = builder.build();
            ResponseBody body = clone.body();
            if (body != null) {
                MediaType mediaType = body.contentType();
                if (mediaType != null) {
                    if (isText(mediaType)) {
                        String resp = body.string();

                        LogUtil.json(Log.DEBUG, TAG, resp);

                        body = ResponseBody.create(mediaType, resp);
                        return response.newBuilder().body(body).build();
                    } else {
                        LogUtil.d(TAG, "data : " + " maybe [file part] , too large too print , ignored!");
                    }
                }
            }

        } catch (Exception e) {
            LogUtil.d(TAG, "DATA PARSE ERR,SHOW ORIGINAL DATA!");

            Response.Builder builder = response.newBuilder();
            Response clone = builder.build();
            ResponseBody body = clone.body();

            try {
                LogUtil.d(TAG, body.string());
            } catch (IOException ex) {
                ex.printStackTrace();
            }

//            e.printStackTrace();
        }

        LogUtil.footer(TAG);
        return response;
    }

    private Response logResponse(Response response, String url) {
        try {
            Response.Builder builder = response.newBuilder();
            Response clone = builder.build();
            ResponseBody body = clone.body();
            if (body != null) {
                MediaType mediaType = body.contentType();
                if (mediaType != null) {
                    if (isText(mediaType)) {
                        String resp = body.string();

                        String tag = "XDroid_Net RESPONSE  " + url;
                        LogUtil.json(Log.DEBUG, tag, resp);

                        body = ResponseBody.create(mediaType, resp);
                        return response.newBuilder().body(body).build();
                    } else {
                        TAG = "XDroid_Net";
                        LogUtil.d(TAG, "data : " + " maybe [file part] , too large too print , ignored!");
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }


    private boolean isText(MediaType mediaType) {
        if (mediaType == null) return false;

        return ("text".equals(mediaType.subtype())
                || "json".equals(mediaType.subtype())
                || "xml".equals(mediaType.subtype())
                || "html".equals(mediaType.subtype())
                || "webviewhtml".equals(mediaType.subtype())
                || "x-www-form-urlencoded".equals(mediaType.subtype()));
    }

    private String bodyToString(final Request request) {
        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "something error when show requestBody.";
        }
    }
}
