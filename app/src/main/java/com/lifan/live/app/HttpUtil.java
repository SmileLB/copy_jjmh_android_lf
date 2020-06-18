package com.lifan.live.app;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lifan.base.net.CommonApiImpl;
import com.lifan.base.net.NetProvider;
import com.lifan.base.net.RequestHandler;
import com.lifan.base.utils.SignUtil;
import com.lifan.comic.common.constants.Constants;
import com.lifan.comic.common.net.HttpDecodeInterceptor;
import com.lifan.comic.common.net.HttpUrlInterceptor;
import com.lifan.comic.common.net.gsonconvert.CommonGsonConverterFactory;

import okhttp3.CookieJar;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Converter;

import static com.lifan.base.net.CommonApiImpl.STRING;


public class HttpUtil {

    public static void initHttp() {
        //配置你的Gson
        final Gson gson = new GsonBuilder()
                .setDateFormat(Constants.DateFormat.YMDHMS)
                .registerTypeHierarchyAdapter(String.class, STRING)//设置解析的时候null转成""
                .create();

        //配置HttpClient
        CommonApiImpl.registerProvider(new NetProvider() {
            @Override
            public Interceptor[] configInterceptors() {
                HttpUrlInterceptor httpUrlInterceptor = new HttpUrlInterceptor();
                HttpDecodeInterceptor httpDecodeInterceptor = new HttpDecodeInterceptor();
                return new Interceptor[]{httpUrlInterceptor,httpDecodeInterceptor};
            }

            @Override
            public Converter.Factory[] configConverterFactories() {
                return new Converter.Factory[]{CommonGsonConverterFactory.create(gson)};
            }

            @Override
            public void configHttps(OkHttpClient.Builder builder) {

            }

            @Override
            public CookieJar configCookie() {
                return null;
            }

            @Override
            public RequestHandler configHandler() {
                return new RequestHandler() {
                    @Override
                    public Request onBeforeRequest(Request request, Interceptor.Chain chain) {
                        String timeStamp = System.currentTimeMillis() + "";
                        String nonce = Constants.NONCE();
                        Request.Builder newRequest = request.newBuilder()
                                .header(Constants.RequestBodyKey.SOURCEID, Constants.SOURCE_ID())
                                .header(Constants.RequestBodyKey.CHANNEL_ID, Constants.CHANNEL_ID_PHP())
                                .header(Constants.RequestBodyKey.DEVICE, "android")
                                .header(Constants.RequestBodyKey.NONCE, nonce)
                                .header(Constants.RequestBodyKey.TIMESTAMP, timeStamp)
                                .header(Constants.RequestBodyKey.SIGNATURE, SignUtil.signSha1(nonce + timeStamp))
                                ;
//                        if (!request.url().toString().contains(Constants.AUTHORIZATION_IGNORE)) {
//                            if (LoginHelper.getOnLineUser() != null) {
//                                newRequest.header(Constants.RequestBodyKey.TOKEN, "Bearer " + SharedPref.getInstance().getString(Constants.SharedPrefKey.TOKEN, ""));
//                            }else {
//                                newRequest.header(Constants.RequestBodyKey.TOKEN, "Bearer " + SharedPref.getInstance().getString(Constants.SharedPrefKey.ACCESS_TOKEN, ""));
//                            }
//                        }
                        return newRequest.build();
                    }
                };
            }

            @Override
            public long configConnectTimeoutMills() {
                return Constants.NET_TIMEOUT;
            }

            @Override
            public long configReadTimeoutMills() {
                return Constants.NET_TIMEOUT;
            }

            @Override
            public boolean configLogEnable() {
                return Constants.DEBUG;
            }
        });
    }
}
