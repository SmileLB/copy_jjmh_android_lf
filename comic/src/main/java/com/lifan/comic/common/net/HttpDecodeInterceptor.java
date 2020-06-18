package com.lifan.comic.common.net;

import android.util.Base64;
import android.util.Log;


import com.lifan.base.utils.SignUtil;
import com.lifan.comic.common.constants.Constants;


import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

//需要aes解密拦截器

public class HttpDecodeInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response;
        Request request = chain.request();
        String timeStamp = System.currentTimeMillis() + "";
        String url = request.url().toString();
        if (url.contains(Constants.NEED_DECODE_AES)) {
            url = url.replace(Constants.NEED_DECODE_AES,"");
            Request.Builder builder = request.newBuilder();
//            String uuid = UUID.randomUUID().toString().replace("-", "");
            Request newRequest = builder.url(url)
//                    .header(Constants.RequestBodyKey.NONCE, uuid)
//                    .header(Constants.RequestBodyKey.TIMESTAMP, timeStamp)
//                    .header(Constants.RequestBodyKey.SIGNATURE, SignUtil.signSha1(uuid + timeStamp))
                    .build();

            Response responseTmp = chain.proceed(newRequest);
            String responseStr = responseTmp.body().string();
            Log.d("HttpDecodeInterceptor", responseStr);

            String decrypt = SignUtil.decrypt(Base64.decode(responseStr.getBytes("utf-8"),Base64.DEFAULT), "de87db587d774cdf");
            Log.d("HttpDecodeInterceptor", decrypt);
            ResponseBody responseBody = ResponseBody.create(MediaType.parse(Constants.MEDIA_TYPE_JSON), decrypt);
            Response.Builder responseBuilder = new Response.Builder();
            response = responseBuilder.headers(responseTmp.headers())
                    .code(responseTmp.code())
                    .message(responseTmp.message())
                    .request(responseTmp.request())
                    .protocol(responseTmp.protocol())
                    .networkResponse(responseTmp.networkResponse())
                    .cacheResponse(responseTmp.cacheResponse())
                    .body(responseBody)
                    .build();
        }else {
            response = chain.proceed(request);
        }
        return response;
    }
}

