package com.lifan.comic.greendao;

import android.text.TextUtils;

import com.lifan.base.BaseApplication;
import com.lifan.base.log.LogUtil;
import com.lifan.base.utils.SharedPref;
import com.lifan.comic.common.constants.Constants;
import com.lifan.comic.greendao.gen.UserInfoDao;
import com.lifan.comic.util.DateHelper;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by 皓然 on 2017/8/20.
 */

public class DaoHelper<T> {
    private DaoManager manager;
    private Class<T> clazz;

    public DaoHelper() {
        manager = DaoManager.getInstance();
        manager.setDebug();
    }

    private Class<T> getClazz() {
        if (clazz == null) {//获取泛型的Class对象
            clazz = ((Class<T>) (((ParameterizedType) (this.getClass().getGenericSuperclass())).getActualTypeArguments()[0]));
        }
        return clazz;
    }

    // 插入增加
    public boolean insert(T t) {
        return manager.getDaoSession().insert(t) != -1;
    }

    //删除
    public boolean delete(T t) {
        try {
            manager.getDaoSession().delete(t);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 删除所有
    public boolean deleteAll() {
        try {
            manager.getDaoSession().deleteAll(clazz);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //列出所有
    public List<T> listAll() {
        return manager.getDaoSession().loadAll(getClazz());
    }

    public T find(long id) {
        return manager.getDaoSession().load(clazz, id);
    }

    //更新
    public boolean update(T t) {
        try {
            manager.getDaoSession().update(t);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean insertOrUpdate(T t, int id) {
        try {
            if (find(id) == null)
                insert(t);
            else
                update(t);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 用户登陆之后的操作
     */
    public boolean updateUser(UserInfo userInfo) {
        UserInfoDao userInfoDao = manager.getDaoSession().getUserInfoDao();
        List<UserInfo> userInfos = userInfoDao.loadAll();
        for (UserInfo info : userInfos) {
            if (info.getIsLogin()) {
                info.setIsLogin(false);
                userInfoDao.update(info);
            }
        }
        if (!userInfo.getIsLogin()) {
            userInfo.setIsLogin(true);
        }
        return manager.getDaoSession().insertOrReplace(userInfo) > 0;
    }

    public void logOffAllUser() {
//        UserInfoDao userInfoDao = manager.getDaoSession().getUserInfoDao();
//        List<UserInfo> userInfos = userInfoDao.loadAll();
//        for (UserInfo info : userInfos) {
//            if (info.getIsLogin()) {
//                info.setIsLogin(false);
//                userInfoDao.update(info);
//            }
//        }
//        insertORupdateOnlineTimeData(0, null, DateHelper.getCurrentDate(Constants.DateFormat.YMDHMS), 0, new Date().getTime() / 1000);

        UserInfoDao userInfoDao = manager.getDaoSession().getUserInfoDao();
        if (userInfoDao != null) {
            userInfoDao.deleteAll();
        }
        //切换用户时清空本地任务上报和在线时长数据
//        deleteReportData();
//        deleteOnlineTimeData();
    }

    public UserInfo getLoginUser() {
        List<UserInfo> list = manager.getDaoSession().getUserInfoDao().queryBuilder().where(UserInfoDao.Properties.IsLogin.eq(true)).listLazy();
        if (list == null || list.isEmpty()) return null;
        return list.get(0);
    }

    /*public List<SearchModel> loadAllSearchKey() {
        return manager.getDaoSession().getSearchModelDao().queryBuilder().orderDesc(SearchModelDao.Properties.Time).listLazy();
    }

    public void insertOrUpdateKey(String key) {
        SearchModel model = new SearchModel(key, System.currentTimeMillis());
        SearchModelDao dao = manager.getDaoSession().getSearchModelDao();
        dao.insertOrReplace(model);
    }

    public void deleteKey() {
        SearchModelDao dao = manager.getDaoSession().getSearchModelDao();
        dao.deleteAll();
    }

    *//**
     * 保存历史记录 逻辑如下：
     * 1。如果当前userId>0 说明此事有用户登录  数据处于待上传状态  此时插入数据库可直接插入或替换
     * 2.如果当前userId<=0 此时无用户登录  新数据处于未上传状态  此时查看数据库中是否由之前数据
     * 1.无  直接插入
     * 2.有  查看数据库中数据  此时直接替换
     * 结论 ：直接插入或者替换
     *
     * @param model        转化为记录model的目录model
     * @param userId       用户id
     * @param chapterid    章节id
     * @param chapterorder 阅读至多少话
     *//*
    public void insertOrUpdateRecord(BookModel model, String userId, String chapterid, int chapterorder) {
        BookModel localBookModel = queryBookModelByBookidAndUid(model.getId(), userId);
        if (localBookModel != null) {
            //如果本地存在这本书，就设置主键去执行更新操作
            model.set_id(localBookModel.get_id());
            //设置更新时间
            model.setUpdate_time(System.currentTimeMillis());
        } else {//不存在这本书，没有设置主键，后续会自动执行插入操作
            //设置创建时间
            model.setCreate_time(System.currentTimeMillis());
            //这里插入的时候同时设置更新时间是为了方便展示的时候排序
            model.setUpdate_time(System.currentTimeMillis());
        }
        model.setUserId(userId);
        model.setChapterid(chapterid);
        model.setOrder(chapterorder);
        BookModelDao bookModelDao = manager.getDaoSession().getBookModelDao();
        bookModelDao.insertOrReplace(model);
    }

    *//**
     * 根据一本书的id和userId查询这本书对应该用户的记录
     *
     * @return
     *//*
    public BookModel queryBookModelByBookidAndUid(String bookId, String userId) {
        BookModelDao bookModelDao = manager.getDaoSession().getBookModelDao();
        List<BookModel> list = bookModelDao.queryBuilder().where(BookModelDao.Properties.Id.eq(bookId), BookModelDao.Properties.UserId.eq(userId)).listLazy();
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    *//**
     * 根据userid查询阅读记录
     *
     * @warn 使用的时候无需判空处理，默认返回空集合
     *//*
    public List<BookModel> queryAllReadRecordByUserid(long userId) {
        List<BookModel> list = manager.getDaoSession().getBookModelDao().queryBuilder().where(BookModelDao.Properties.UserId.eq(userId)).orderDesc(BookModelDao.Properties.Update_time).listLazy();
        return list == null ? new ArrayList<BookModel>() : list;
    }

    *//**
     * 查询本地所有的阅读记录，userid=0的记录，userid=0表示未上传的记录
     *
     * @return
     *//*
    public List<BookModel> queryAllReadRecord() {
        List<BookModel> list;
        list = manager.getDaoSession().getBookModelDao().queryBuilder().where(BookModelDao.Properties.UserId.eq(0)).orderDesc(BookModelDao.Properties.Update_time).listLazy();
        return list == null ? new ArrayList<BookModel>() : list;
    }


    //删除已上传的数据
    public void deleteSomeReadRecords() {
//        Iterator<BookModel> iterator = queryAllReadRecordByUserid().iterator();
//        BookModelDao bookModelDao = manager.getDaoSession().getBookModelDao();
//        while (iterator.hasNext()) {
//            BookModel bookModel = iterator.next();
//            if (bookModel.getUserId() > 0) {
//                bookModelDao.delete(bookModel);
//            }
//        }
    }

    *//**
     * 查询章节内容
     *
     * @param id
     * @return
     *//*
    public BookModel getRecordByMainId(int id) {
//        List<ReadRecords> list = manager.getDaoSession().getReadRecordsDao().queryBuilder()
//                .where(ReadRecordsDao.Properties.ObjectId.eq(id))
//                .orderDesc(ReadRecordsDao.Properties.BrowseTime)
//                .listLazy();
//        if (list == null || list.isEmpty()) return null;
        List<BookModel> list = manager.getDaoSession().getBookModelDao().queryBuilder()
                .where(BookModelDao.Properties.Id.eq(id))
                .orderDesc(BookModelDao.Properties.Update_time).listLazy();
        if (list == null || list.isEmpty()) return null;
        return list.get(0);
    }

    *//**
     * 插入或更新用户点赞评论信息
     *//*
    public void insertOrUpdateUserCommentFavorData(UserCommentFavorData data) {
        UserCommentFavorDataDao favorDataDao = manager.getDaoSession().getUserCommentFavorDataDao();
        favorDataDao.insertOrReplace(data);
    }

    *//**
     * 根据用户id和评论id获取用户点赞评论数据
     *//*
    public UserCommentFavorData getUserCommentFavorDataById(String userId, String commentId) {
        UserCommentFavorDataDao favorDataDao = manager.getDaoSession().getUserCommentFavorDataDao();
        List<UserCommentFavorData> list = favorDataDao.queryBuilder().where(UserCommentFavorDataDao.Properties.UserId.eq(userId), UserCommentFavorDataDao.Properties.CommentId.eq(commentId)).listLazy();
        if (list == null || list.size() == 0) {
            return null;
        } else {
            return list.get(0);
        }
    }

    public List<OnlineTimeData> getOnlineTimeDataAll() {
        OnlineTimeDataDao onlineTimeDataDao = manager.getDaoSession().getOnlineTimeDataDao();
        return onlineTimeDataDao.loadAll();
    }

    *//**
     * 插入或更新用户在线时长数据
     *//*
    public void insertORupdateOnlineTimeData(int duration, String lastLoginTime, String lastLogoutTime,
                                             long lastLoginTimeStamp, long lastLogoutTimestamp) {
        OnlineTimeDataDao onlineTimeDataDao = manager.getDaoSession().getOnlineTimeDataDao();
        String currentDate = DateHelper.getCurrentDate(Constants.DateFormat.YMD);
        String uid = getUid();
        OnlineTimeData onlineTimeData = getOnlineTimeData(currentDate, uid);
        if (onlineTimeData == null) {
            //新增数据
            onlineTimeData = new OnlineTimeData();
            onlineTimeData.setDate(currentDate);
            onlineTimeData.setUid(uid + "");
            onlineTimeData.setDuration(duration);
            if (lastLoginTime == null) {
                //这里如果没有值需要设置一个，因为可能是用户登录或者切换账号之后产生的数据，此时也算一个登录时间
                onlineTimeData.setLastLoginTime(DateHelper.getCurrentDate(Constants.DateFormat.YMDHMS));
            } else {
                onlineTimeData.setLastLoginTime(lastLoginTime);
            }
            onlineTimeData.setLastLogoutTime(lastLogoutTime);
            try {
                Long.parseLong(uid);
                onlineTimeData.setIs_visitor(false);
            } catch (Exception e) {
                //解析异常代表是游客登录
                onlineTimeData.setIs_visitor(true);
            }
            onlineTimeDataDao.insert(onlineTimeData);
            LogUtil.e("lifeCycle", "LogTime 插入在线数据:");
        } else {
            //更新数据
            if (duration > 0) {
                //累计在线时长
                onlineTimeData.setDuration(onlineTimeData.getDuration() + duration);
            }
            if (lastLoginTime != null) {
                //最近登录时间
                onlineTimeData.setLastLoginTime(lastLoginTime);
            }
            if (lastLogoutTime != null) {
                //最近退出时间
                onlineTimeData.setLastLogoutTime(lastLogoutTime);
            }
            onlineTimeDataDao.update(onlineTimeData);
            LogUtil.e("lifeCycle", "LogTime 更新在线数据:" + getOnlineTimeData(currentDate, uid).toString());
        }

        //同时执行上报数据统计
        insertORupdateOnlineTimeReportData(duration, lastLoginTime, lastLogoutTime, lastLoginTimeStamp, lastLogoutTimestamp);
    }

    public String getUid() {
        String uid;
        UserInfo onLineUser = LoginHelper.getOnLineUser();
        if (onLineUser != null && !TextUtils.isEmpty(onLineUser.getUid())) {
            //登录用户统计
            uid = onLineUser.getUid() + "";
        } else {
            //游客登录统计
            //优先使用UM统计的ID
            String umidString = UMConfigure.getUMIDString(BaseApplication.getApplication());
            if (umidString != null && umidString.length() > 0) {
                //友盟id可用
                uid = umidString;
            } else {
                //没有UM统计id就从本地共享参数里面读取

                String visitorId = SharedPref.getInstance().getString("UUID", UUID.randomUUID().toString());
                if (visitorId != null) {
                    //本地有游客的id，可直接使用
                    uid = visitorId;
                } else {
                    //本地没有，就使用创建一个UUID
                    String uuid = UUID.randomUUID().toString();
                    //这么处理是为了保持长度和UM的id一致
                    uid = "j-" + uuid.replace("-", "");
                }
            }
            //此时保存游客id到本地
            SharedPref.getInstance().putString("UUID", uid);
        }
        return uid;
    }

    *//**
     * 获取该天的当前用户的在线时长数据
     *//*
    public OnlineTimeData getOnlineTimeData(String date, String uid) {
        OnlineTimeDataDao onlineTimeDataDao = manager.getDaoSession().getOnlineTimeDataDao();
        List<OnlineTimeData> list = onlineTimeDataDao.queryBuilder().where(OnlineTimeDataDao.Properties.Date.eq(date), OnlineTimeDataDao.Properties.Uid.eq(uid)).listLazy();
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    public OnlineTimeData getCurrentOnlineTimeData() {
        OnlineTimeDataDao onlineTimeDataDao = manager.getDaoSession().getOnlineTimeDataDao();
        String currentDate = DateHelper.getCurrentDate(Constants.DateFormat.YMD);
        String uid = getUid();
        List<OnlineTimeData> list = onlineTimeDataDao.queryBuilder().where(OnlineTimeDataDao.Properties.Date.eq(currentDate), OnlineTimeDataDao.Properties.Uid.eq(uid)).listLazy();
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    *//**
     * 根据主键查询OnlineTimeData
     *
     * @param id
     * @return
     *//*
    public OnlineTimeData getOnlineTimeDataBYid(long id) {
        OnlineTimeDataDao onlineTimeDataDao = manager.getDaoSession().getOnlineTimeDataDao();
        List<OnlineTimeData> list = onlineTimeDataDao.queryBuilder().where(OnlineTimeDataDao.Properties.Id.eq(id)).listLazy();
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    *//**
     * 删除不是当日的本地时长记录
     *
     * @param data
     * @param isOnlineTime
     *//*
    public void deleteTimeData(TimeReportData data, boolean isOnlineTime) {
        String currentDate = DateHelper.getCurrentDate(Constants.DateFormat.YMD);
        if (data == null || data.getDate() == null || data.getId() <= 0) {
            //无效数据
            return;
        }
        //如果不是当天数据那就删除
        if (!data.getDate().equals(currentDate)) {
            if (isOnlineTime) {
                //用户在线时长数据表
                manager.getDaoSession().getOnlineTimeDataDao().deleteByKey(data.getId());
            }
        } else {//是当天数据并且是当前用户就不删除，清空duration字段，因为该条数据还可以重复利用,否则还是删除
            //获取当前用户id
            String uid = getUid();
            //如果是游客登录，那就用token字段去匹配；如果是登录用户，那就用uid字段去匹配是否为当前用户
            if (data.isIs_visitor() && uid.equals(data.getToken()) || !data.isIs_visitor() && uid.equals(data.getUid() + "")) {
                //如果是当前用户，清空duration字段并更新
                if (isOnlineTime) {
                    //用户在线时长数据表
                    OnlineTimeData onlineTimeData = getOnlineTimeDataBYid(data.getId());
                    if (onlineTimeData != null) {
                        //清空用户在线时长
                        onlineTimeData.setDuration(0);
                        manager.getDaoSession().getOnlineTimeDataDao().update(onlineTimeData);
                    }
                }
            } else {
                //是当天数据，但不是当前用户也直接删除数据
                if (isOnlineTime) {
                    //用户在线时长数据表
                    manager.getDaoSession().getOnlineTimeDataDao().deleteByKey(data.getId());
                }
            }
        }
    }

    public void deleteOnlineTimeData() {
        OnlineTimeDataDao onlineTimeDataDao = manager.getDaoSession().getOnlineTimeDataDao();
        onlineTimeDataDao.deleteAll();

        deleteOnlineTimeReportData();
    }


    public void updateTaskReportStatus(TaskReportModel reportModel) {
        TaskReportModelDao taskReportModelDao = manager.getDaoSession().getTaskReportModelDao();
        TaskReportModel taskReportModel = queryTaskReportStatusDataByTypeToday(reportModel.getType());
        if (taskReportModel != null) {//已经存在，更新
            taskReportModel.setIsReported(reportModel.getIsReported());
            taskReportModel.setDate(reportModel.getDate());
            taskReportModel.setTitle(reportModel.getTitle());
            taskReportModelDao.update(taskReportModel);
        } else {
            taskReportModelDao.insert(reportModel);
        }
    }

    public void updateTaskReportStatusByType(String type) {
        TaskReportModelDao taskReportModelDao = manager.getDaoSession().getTaskReportModelDao();
        TaskReportModel taskReportModel = queryTaskReportStatusDataByTypeToday(type);
        if (taskReportModel != null) {
            taskReportModel.setIsReported(true);
            taskReportModelDao.update(taskReportModel);
        }
    }

    public TaskReportModel queryTaskReportStatusDataByTypeToday(String type) {
        TaskReportModelDao taskReportModelDao = manager.getDaoSession().getTaskReportModelDao();
        String currentDate = DateHelper.getCurrentDate(Constants.DateFormat.YMD);
        List<TaskReportModel> list = taskReportModelDao.queryBuilder()
                .where(TaskReportModelDao.Properties.Date.eq(currentDate),
                        TaskReportModelDao.Properties.Type.eq(type))
                .listLazy();
        LogUtil.e("lifeCycle", "TaskReportModel:" + list.size() + "  " + type);

        if (list != null && list.size() > 0)
            return list.get(0);
        return null;
    }

    public boolean queryTaskReportStatusByTypeToday(String type) {
        TaskReportModel taskReportModel = queryTaskReportStatusDataByTypeToday(type);
        if (taskReportModel == null)
            return false;
        return taskReportModel.getIsReported();
    }

    public void deleteReportData() {
        TaskReportModelDao taskReportModelDao = manager.getDaoSession().getTaskReportModelDao();
        taskReportModelDao.deleteAll();
    }


    *//**
     * 插入或更新用户在线时长数据
     *//*
    public void insertORupdateOnlineTimeReportData(int duration, String lastLoginTime, String lastLogoutTime,
                                                   long lastLoginTimeStamp, long lastLogoutTimestamp) {
        OnlineTimeReportDataDao onlineTimeReportDataDao = manager.getDaoSession().getOnlineTimeReportDataDao();
        String currentDate = DateHelper.getCurrentDate(Constants.DateFormat.YMD);
        String uid = getUid();
        OnlineTimeReportData onlineTimeReportData = getOnlineTimeReportData(currentDate, uid);
        if (onlineTimeReportData == null) {
            //新增数据
            onlineTimeReportData = new OnlineTimeReportData();
            onlineTimeReportData.setDate(currentDate);
            onlineTimeReportData.setUid(uid + "");
            onlineTimeReportData.setDuration(duration);
            if (lastLoginTime == null) {
                //这里如果没有值需要设置一个，因为可能是用户登录或者切换账号之后产生的数据，此时也算一个登录时间
                onlineTimeReportData.setLastLoginTime(DateHelper.getCurrentDate(Constants.DateFormat.YMDHMS));
                onlineTimeReportData.setLastLoginTimeStamp((new Date().getTime()) / 1000);
            } else {
                onlineTimeReportData.setLastLoginTime(lastLoginTime);
                onlineTimeReportData.setLastLoginTimeStamp(lastLoginTimeStamp);
            }
            onlineTimeReportData.setLastLogoutTime(lastLogoutTime);
            onlineTimeReportData.setLastLogoutTimeStamp(lastLogoutTimestamp);
            try {
                Long.parseLong(uid);
                onlineTimeReportData.setIs_visitor(false);
            } catch (Exception e) {
                //解析异常代表是游客登录
                onlineTimeReportData.setIs_visitor(true);
            }
            onlineTimeReportDataDao.insert(onlineTimeReportData);
            LogUtil.e("lifeCycle", "LogTime 插入在线上报数据:");
        } else {
            //更新数据
            if (duration > 0) {
                //累计在线时长
                onlineTimeReportData.setDuration(onlineTimeReportData.getDuration() + duration);
            }
            if (lastLoginTime != null) {
                //最近登录时间
                onlineTimeReportData.setLastLoginTime(lastLoginTime);
                onlineTimeReportData.setLastLoginTimeStamp(lastLoginTimeStamp);
            }
            if (lastLogoutTime != null) {
                //最近退出时间
                onlineTimeReportData.setLastLogoutTime(lastLogoutTime);
                onlineTimeReportData.setLastLogoutTimeStamp(lastLogoutTimestamp);
            }
            onlineTimeReportDataDao.update(onlineTimeReportData);
            LogUtil.e("lifeCycle", "LogTime 更新在线上报数据:" + getOnlineTimeReportData(currentDate, uid).toString());
        }
    }

    public OnlineTimeReportData getOnlineTimeReportData(String date, String uid) {
        OnlineTimeReportDataDao onlineTimeReportDataDao = manager.getDaoSession().getOnlineTimeReportDataDao();
        List<OnlineTimeReportData> list = onlineTimeReportDataDao.queryBuilder()
                .where(OnlineTimeReportDataDao.Properties.Date.eq(date), OnlineTimeReportDataDao.Properties.Uid.eq(uid))
                .listLazy();
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    public void deleteOnlineTimeReportData() {
        OnlineTimeReportDataDao onlineTimeReportDataDao = manager.getDaoSession().getOnlineTimeReportDataDao();
        onlineTimeReportDataDao.deleteAll();
    }

    public void deleteOnlineTimeReportDurationData() {
        LogUtil.e("lifeCycle", "deleteOnlineTimeReportDurationData:");

        OnlineTimeReportDataDao onlineTimeReportDataDao = manager.getDaoSession().getOnlineTimeReportDataDao();
        String currentDate = DateHelper.getCurrentDate(Constants.DateFormat.YMD);
        String uid = getUid();
        OnlineTimeReportData onlineTimeReportData = getOnlineTimeReportData(currentDate, uid);
        if (onlineTimeReportData != null) {
            onlineTimeReportData.setDuration(0);
            onlineTimeReportDataDao.update(onlineTimeReportData);
        }
    }

    public List<OnlineTimeReportData> getOnlineTimeReportDataAll() {
        OnlineTimeReportDataDao onlineTimeReportDataDao = manager.getDaoSession().getOnlineTimeReportDataDao();
        return onlineTimeReportDataDao.loadAll();
    }*/
}

