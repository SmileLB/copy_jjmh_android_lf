package com.lifan.comic.common.net;
import com.lifan.base.net.CommonApiImpl;
import com.lifan.comic.common.constants.Constants;


public class CommonApi {

    private static CommonService mComicService;

    public static CommonService getApi() {
        if (mComicService == null)
            synchronized (CommonApi.class) {
                if (mComicService == null)
                    mComicService = CommonApiImpl.getInstance()
                            .getRetrofit(Constants.BASEURL(), true)
                            .create(CommonService.class);
            }
        return mComicService;
    }
}
