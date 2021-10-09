package com.lifan.comic.greendao.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.lifan.comic.greendao.UserInfo;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "USER_INFO".
*/
public class UserInfoDao extends AbstractDao<UserInfo, String> {

    public static final String TABLENAME = "USER_INFO";

    /**
     * Properties of entity UserInfo.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Uid = new Property(0, String.class, "uid", true, "UID");
        public final static Property Openid = new Property(1, String.class, "openid", false, "OPENID");
        public final static Property Unionid = new Property(2, String.class, "unionid", false, "UNIONID");
        public final static Property Sourceid = new Property(3, int.class, "sourceid", false, "SOURCEID");
        public final static Property Username = new Property(4, String.class, "username", false, "USERNAME");
        public final static Property Password = new Property(5, String.class, "password", false, "PASSWORD");
        public final static Property Nickname = new Property(6, String.class, "nickname", false, "NICKNAME");
        public final static Property Salt = new Property(7, String.class, "salt", false, "SALT");
        public final static Property Avatar = new Property(8, String.class, "avatar", false, "AVATAR");
        public final static Property Mobile = new Property(9, long.class, "mobile", false, "MOBILE");
        public final static Property Sex = new Property(10, int.class, "sex", false, "SEX");
        public final static Property PosCity = new Property(11, String.class, "posCity", false, "POS_CITY");
        public final static Property Status = new Property(12, int.class, "status", false, "STATUS");
        public final static Property Create_time = new Property(13, long.class, "create_time", false, "CREATE_TIME");
        public final static Property Update_time = new Property(14, long.class, "update_time", false, "UPDATE_TIME");
        public final static Property Delete_flag = new Property(15, int.class, "delete_flag", false, "DELETE_FLAG");
        public final static Property Login = new Property(16, int.class, "login", false, "LOGIN");
        public final static Property IsLogin = new Property(17, boolean.class, "isLogin", false, "IS_LOGIN");
    }


    public UserInfoDao(DaoConfig config) {
        super(config);
    }
    
    public UserInfoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"USER_INFO\" (" + //
                "\"UID\" TEXT PRIMARY KEY NOT NULL ," + // 0: uid
                "\"OPENID\" TEXT," + // 1: openid
                "\"UNIONID\" TEXT," + // 2: unionid
                "\"SOURCEID\" INTEGER NOT NULL ," + // 3: sourceid
                "\"USERNAME\" TEXT," + // 4: username
                "\"PASSWORD\" TEXT," + // 5: password
                "\"NICKNAME\" TEXT," + // 6: nickname
                "\"SALT\" TEXT," + // 7: salt
                "\"AVATAR\" TEXT," + // 8: avatar
                "\"MOBILE\" INTEGER NOT NULL ," + // 9: mobile
                "\"SEX\" INTEGER NOT NULL ," + // 10: sex
                "\"POS_CITY\" TEXT," + // 11: posCity
                "\"STATUS\" INTEGER NOT NULL ," + // 12: status
                "\"CREATE_TIME\" INTEGER NOT NULL ," + // 13: create_time
                "\"UPDATE_TIME\" INTEGER NOT NULL ," + // 14: update_time
                "\"DELETE_FLAG\" INTEGER NOT NULL ," + // 15: delete_flag
                "\"LOGIN\" INTEGER NOT NULL ," + // 16: login
                "\"IS_LOGIN\" INTEGER NOT NULL );"); // 17: isLogin
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"USER_INFO\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, UserInfo entity) {
        stmt.clearBindings();
 
        String uid = entity.getUid();
        if (uid != null) {
            stmt.bindString(1, uid);
        }
 
        String openid = entity.getOpenid();
        if (openid != null) {
            stmt.bindString(2, openid);
        }
 
        String unionid = entity.getUnionid();
        if (unionid != null) {
            stmt.bindString(3, unionid);
        }
        stmt.bindLong(4, entity.getSourceid());
 
        String username = entity.getUsername();
        if (username != null) {
            stmt.bindString(5, username);
        }
 
        String password = entity.getPassword();
        if (password != null) {
            stmt.bindString(6, password);
        }
 
        String nickname = entity.getNickname();
        if (nickname != null) {
            stmt.bindString(7, nickname);
        }
 
        String salt = entity.getSalt();
        if (salt != null) {
            stmt.bindString(8, salt);
        }
 
        String avatar = entity.getAvatar();
        if (avatar != null) {
            stmt.bindString(9, avatar);
        }
        stmt.bindLong(10, entity.getMobile());
        stmt.bindLong(11, entity.getSex());
 
        String posCity = entity.getPosCity();
        if (posCity != null) {
            stmt.bindString(12, posCity);
        }
        stmt.bindLong(13, entity.getStatus());
        stmt.bindLong(14, entity.getCreate_time());
        stmt.bindLong(15, entity.getUpdate_time());
        stmt.bindLong(16, entity.getDelete_flag());
        stmt.bindLong(17, entity.getLogin());
        stmt.bindLong(18, entity.getIsLogin() ? 1L: 0L);
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, UserInfo entity) {
        stmt.clearBindings();
 
        String uid = entity.getUid();
        if (uid != null) {
            stmt.bindString(1, uid);
        }
 
        String openid = entity.getOpenid();
        if (openid != null) {
            stmt.bindString(2, openid);
        }
 
        String unionid = entity.getUnionid();
        if (unionid != null) {
            stmt.bindString(3, unionid);
        }
        stmt.bindLong(4, entity.getSourceid());
 
        String username = entity.getUsername();
        if (username != null) {
            stmt.bindString(5, username);
        }
 
        String password = entity.getPassword();
        if (password != null) {
            stmt.bindString(6, password);
        }
 
        String nickname = entity.getNickname();
        if (nickname != null) {
            stmt.bindString(7, nickname);
        }
 
        String salt = entity.getSalt();
        if (salt != null) {
            stmt.bindString(8, salt);
        }
 
        String avatar = entity.getAvatar();
        if (avatar != null) {
            stmt.bindString(9, avatar);
        }
        stmt.bindLong(10, entity.getMobile());
        stmt.bindLong(11, entity.getSex());
 
        String posCity = entity.getPosCity();
        if (posCity != null) {
            stmt.bindString(12, posCity);
        }
        stmt.bindLong(13, entity.getStatus());
        stmt.bindLong(14, entity.getCreate_time());
        stmt.bindLong(15, entity.getUpdate_time());
        stmt.bindLong(16, entity.getDelete_flag());
        stmt.bindLong(17, entity.getLogin());
        stmt.bindLong(18, entity.getIsLogin() ? 1L: 0L);
    }

    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    @Override
    public UserInfo readEntity(Cursor cursor, int offset) {
        UserInfo entity = new UserInfo( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // uid
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // openid
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // unionid
            cursor.getInt(offset + 3), // sourceid
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // username
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // password
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // nickname
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // salt
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // avatar
            cursor.getLong(offset + 9), // mobile
            cursor.getInt(offset + 10), // sex
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // posCity
            cursor.getInt(offset + 12), // status
            cursor.getLong(offset + 13), // create_time
            cursor.getLong(offset + 14), // update_time
            cursor.getInt(offset + 15), // delete_flag
            cursor.getInt(offset + 16), // login
            cursor.getShort(offset + 17) != 0 // isLogin
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, UserInfo entity, int offset) {
        entity.setUid(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setOpenid(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setUnionid(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setSourceid(cursor.getInt(offset + 3));
        entity.setUsername(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setPassword(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setNickname(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setSalt(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setAvatar(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setMobile(cursor.getLong(offset + 9));
        entity.setSex(cursor.getInt(offset + 10));
        entity.setPosCity(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setStatus(cursor.getInt(offset + 12));
        entity.setCreate_time(cursor.getLong(offset + 13));
        entity.setUpdate_time(cursor.getLong(offset + 14));
        entity.setDelete_flag(cursor.getInt(offset + 15));
        entity.setLogin(cursor.getInt(offset + 16));
        entity.setIsLogin(cursor.getShort(offset + 17) != 0);
     }
    
    @Override
    protected final String updateKeyAfterInsert(UserInfo entity, long rowId) {
        return entity.getUid();
    }
    
    @Override
    public String getKey(UserInfo entity) {
        if(entity != null) {
            return entity.getUid();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(UserInfo entity) {
        return entity.getUid() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
