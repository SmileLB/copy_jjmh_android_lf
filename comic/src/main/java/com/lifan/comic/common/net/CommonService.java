package com.lifan.comic.common.net;


import com.lifan.comic.common.constants.Constants;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface CommonService {
    String API_ON_LINE_TASK_REPORT = "api/on_line";
    String GATEWAY = "gateway/";
    String CARTOON_API = "cartoon/api/";
    String CARTOON_FITSTPAGE = GATEWAY + "cartoon/v1/com/firstpage/";
    String API_RANKING_LIST = CARTOON_FITSTPAGE + "ranking_list";
    String API_FREE = CARTOON_FITSTPAGE + "free";
    String API_FOR_FREE = CARTOON_FITSTPAGE + "for_free";
    String API_RECOMMENDED = CARTOON_FITSTPAGE + "recommended";
    String API_RECENT_UPDATES = CARTOON_FITSTPAGE + "recent_updates" ;
    String API_CAROUSEL = CARTOON_FITSTPAGE + "carousel";
    String API_SECTION_RECOMMENDED = CARTOON_FITSTPAGE + "section_recommended";
    String API_RICHEST_LIST = CARTOON_FITSTPAGE + "richest_list";
    String API_RICHEST_ROLL = CARTOON_FITSTPAGE + "richest_roll" ;

    String CARTOON_DISCOVERY = GATEWAY + "cartoon/v1/com/discovery/";
    String API_HOT = CARTOON_DISCOVERY + "hot" ;
    String API_SEARCH = CARTOON_DISCOVERY + "search" ;
    String API_WATCHING = CARTOON_DISCOVERY + "watching" ;

    String CARTOON_CATEGORY = GATEWAY + "cartoon/v1/com/categorys/";
    String API_CATEGORY = CARTOON_CATEGORY + "category" ;
    String API_BOOK_CATEGORY = CARTOON_CATEGORY + "book_category" ;

    String CARTOON_DETAIL = GATEWAY + "cartoon/v1/com/details/";
    String API_REVIEW_ZAN = CARTOON_DETAIL + "review_zan";
    String API_GUESS_YOUR_LIKE = CARTOON_DETAIL + "guess_your_like" ;
    String API_CHECK_COLLECT = CARTOON_DETAIL + "check_collect";
    String API_CHECK_DIANZAN = CARTOON_DETAIL + "check_dianzan";
    String API_CATALOGUE = CARTOON_DETAIL + "catalogue" ;
    String API_READ = CARTOON_DETAIL + "read";
    String API_CARTOON_INFO = CARTOON_DETAIL + "cartoon_info" ;
    String API_REVIEW = CARTOON_DETAIL + "review";
    String API_BOOK_BONUS = CARTOON_DETAIL + "book_bonus";
    String API_REVIEW_LIST = CARTOON_DETAIL + "review_list";
    String API_COLLECT = CARTOON_DETAIL + "collect";
    String API_BUY_BOOK = CARTOON_DETAIL + "buy_book";
    String API_BOOK_REWARD = CARTOON_DETAIL + "book_reward";
    String API_GIFT = CARTOON_DETAIL + "gift";
    String API_DIANZAN = CARTOON_DETAIL + "dianzan";

    String CARTOON_FACEBACK = GATEWAY + "cartoon/v1/com/feceback_info/";
    String API_SUGGESTION = CARTOON_FACEBACK + "suggestion";
    String API_RESPONSELIST = CARTOON_FACEBACK + "responselist";
    String API_REVERT = CARTOON_FACEBACK + "revert";

    String CARTOON_SHELF = GATEWAY + "cartoon/v1/com/bookcase/";
    String API_COLLECTION = CARTOON_SHELF + "collection";
    String API_COLLECTDEL = CARTOON_SHELF + "collectdel";

    String CARTOON_HISTORY = GATEWAY + "cartoon/v1/com/history/";
    String API_READINGHISTORYDEL = CARTOON_HISTORY + "readinghistorydel";
    String API_READINGHISTORY = CARTOON_HISTORY + "readinghistory";
    String API_SYNCHRONIZATION = CARTOON_HISTORY + "synchronization";

    String USER_CUSTOMER = GATEWAY + "user/v2/com/user/";
    String API_SET_AUTOBUY = USER_CUSTOMER + "set_autobuy";
    String API_GET_USERINFO = USER_CUSTOMER + "get_user_info";
    String API_PAYLOG = USER_CUSTOMER + "paylog";
    String API_CONSUME = USER_CUSTOMER + "consume";
    String API_REST = USER_CUSTOMER + "rest";
    String API_FULI = USER_CUSTOMER + "fuli";
    String API_GET_USER_ACCOUNT = USER_CUSTOMER + "get_user_account";

    String API_GETGOLD = GATEWAY + "user/v1/com/new_task/getgold";

    String USER_DAILY_TASK = GATEWAY + "user/v1/com/daily_task/";
    String API_CHECK = USER_DAILY_TASK + "check";
    String API_DAYGET = USER_DAILY_TASK + "dayget";
    String API_INVITE_SHARE = USER_DAILY_TASK + "app_invite_share";

    String USER_APP_LOGIN = GATEWAY + "user/v2/com/login/";
    String API_APP_WEIBO_LOGIN = USER_APP_LOGIN + "app_weibo_login";
    String API_APP_WXLOGIN = USER_APP_LOGIN + "app_wx_login";
    String API_QQ_LOGIN = USER_APP_LOGIN + "app_qq_login";
    String API_SEND_CODE = USER_APP_LOGIN + "send_code";
    String API_MLOGIN = USER_APP_LOGIN + "phone_login";


    String USER_BOOKBONUS = GATEWAY + "user/v1/com/bookbonus/";
    String API_LIKEBOOKINFO = USER_BOOKBONUS + "likebookinfo";
    String API_MYBOOKBONUS = USER_BOOKBONUS + "mybookbonus";


    String ACCOUNT_USER = GATEWAY + "account/v1/com/user/";
    String API_APP_LOGIN_GIVE = ACCOUNT_USER + "app_login_give";

    String APP_CONFIG = GATEWAY + "cartoon/v1/app_config/";
    String API_PAY_SETTING = APP_CONFIG + "app_pay_setting" ;

    String APP_UPDATE_CHECK = GATEWAY + "cartoon/v1/com/app_forced_update_config/get_app_forced_update_config?device=android";

    String APP_SERVICE_CONFIG = GATEWAY + "cartoon/v1/com/customer_service_config/get_customer_service_config?platform=0";

    String API_MEMBERCENTER = CARTOON_API + "membercenter";
    String API_WECHAT_ORDER = "user/v1/order/wechat_order";
    String API_ALIPAY_APP = "user/v1/order/alipay_app";
    String API_HUIFUBAO_APP = CARTOON_API + "huifubao";
    String BEHAVIOR_COLLECTION = "http://behavior.jjmh887.cn/behavior_collect?";
    String BEHAVIOR_COLLECTION_TEST = "http://t361651510580256768.kgbol.club/behavior/behavior_collect?";

    /**
     * 设置是否自动购买(暂时未被使用，客户端只做本地保存)
     *
     * @return
     */
    @POST(API_SET_AUTOBUY)
    Observable<Object> setAutoBuy(@Body RequestBody requestBody);

    /**
     * 我的打赏猜你喜欢
     *
     * @return
     */
    @GET(API_LIKEBOOKINFO)
    Observable<Object> getLikeBookList();

    /**
     * 获取详情页猜你喜欢列表
     */
    @GET(API_GUESS_YOUR_LIKE)
    Observable<Object> getLikeBookList(@QueryMap Map<String, Object> parames);

    /**
     * 查询漫画收藏状态
     */
    @GET(API_CHECK_COLLECT)
    Observable<Object> getCollectStatus(@Query("id") String id);

    /**
     * 首页推荐
     *
     * @return
     */
    @GET(API_RECOMMENDED)
    Observable<Object> getRecommond(@Header(Constants.RequestBodyKey.TOKEN) String token);

    /**
     * 活跃上报
     *
     * @param parames
     * @return
     */
    @GET(BEHAVIOR_COLLECTION + Constants.IDENTIFICATION_IGNORE)
    Observable<Object> behaviorCollection(@QueryMap Map<String, Object> parames);

    @GET("http://192.168.8.38:9999/gateway/work/get_key"+ Constants.NEED_DECODE_AES+ Constants.IDENTIFICATION_IGNORE)
    Observable<Object> getKey();
}
