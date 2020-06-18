package com.lifan.comic.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class UserInfo {

    @Id
    private String uid;
    private String openid;
    private String unionid;
    private int sourceid;
    private String username;
    private String password;
    private String nickname;
    private String salt;
    private String avatar;
    private long mobile;
    private int sex;
    private String posCity;
    private int status;
    private long create_time;
    private long update_time;
    private int delete_flag;
    private int login;//登录次数

    //自定义字段，标记当前用户是否登录
    private boolean isLogin = false;

    @Generated(hash = 1992972460)
    public UserInfo(String uid, String openid, String unionid, int sourceid,
            String username, String password, String nickname, String salt,
            String avatar, long mobile, int sex, String posCity, int status,
            long create_time, long update_time, int delete_flag, int login,
            boolean isLogin) {
        this.uid = uid;
        this.openid = openid;
        this.unionid = unionid;
        this.sourceid = sourceid;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.salt = salt;
        this.avatar = avatar;
        this.mobile = mobile;
        this.sex = sex;
        this.posCity = posCity;
        this.status = status;
        this.create_time = create_time;
        this.update_time = update_time;
        this.delete_flag = delete_flag;
        this.login = login;
        this.isLogin = isLogin;
    }

    @Generated(hash = 1279772520)
    public UserInfo() {
    }

    public String getUid() {
        return this.uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getOpenid() {
        return this.openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getUnionid() {
        return this.unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public int getSourceid() {
        return this.sourceid;
    }

    public void setSourceid(int sourceid) {
        this.sourceid = sourceid;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSalt() {
        return this.salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public long getMobile() {
        return this.mobile;
    }

    public void setMobile(long mobile) {
        this.mobile = mobile;
    }

    public int getSex() {
        return this.sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getPosCity() {
        return this.posCity;
    }

    public void setPosCity(String posCity) {
        this.posCity = posCity;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getCreate_time() {
        return this.create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public long getUpdate_time() {
        return this.update_time;
    }

    public void setUpdate_time(long update_time) {
        this.update_time = update_time;
    }

    public int getDelete_flag() {
        return this.delete_flag;
    }

    public void setDelete_flag(int delete_flag) {
        this.delete_flag = delete_flag;
    }

    public int getLogin() {
        return this.login;
    }

    public void setLogin(int login) {
        this.login = login;
    }

    public boolean getIsLogin() {
        return this.isLogin;
    }

    public void setIsLogin(boolean isLogin) {
        this.isLogin = isLogin;
    }
}
