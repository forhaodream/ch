package ch.chtool.app;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.android.library.greendao.DaoMaster;
import com.android.library.greendao.DaoSession;

import ch.chtool.bean.User;

/**
 * Created by CH
 * on 2018/11/27 0027 11:34
 */
public class MyApplication extends Application {
    private static final String TAG = "MyApplication";
    private String DATA_BASE_NAME = "";
    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    // 静态单例
    public static MyApplication instances;

    @Override
    public void onCreate() {
        super.onCreate();
        instances = this;
        setDataBase();
        /**
         * greenDao使用说明
         * 增
         */
        User mUser = new User(null, "name", 22);
        try {
            mDaoSession.getUserDao().insert(mUser);
        } catch (Exception e) {
            Log.d(TAG, "数据库插入失败");
        }
        Toast.makeText(this, "插入成功", Toast.LENGTH_SHORT).show();
        /**
         * 删
         */
        try {
            mDaoSession.getUserDao().deleteByKey(1l);
        } catch (Exception e) {
            Log.d(TAG, "删除数据库失败");
        }
        Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
        /**
         * 改
         */
        try {
            mUser.setName("ch");
            mDaoSession.getUserDao().update(mUser);
        } catch (Exception e) {
            Log.d(TAG, "修改数据库失败");
        }
        /**
         * 查
         * loadAll()：查询所有记录
         * load(Long key)：根据主键查询一条记录
         * queryBuilder().list()：返回：List
         * queryBuilder().where(UserDao.Properties.Name.eq("")).list()：返回：List
         * queryRaw(String where,String selectionArg)：返回：List
         */
    }

    public static MyApplication getInstances() {
        return instances;
    }

    private void setDataBase() {
        mHelper = new DaoMaster.DevOpenHelper(this, "sport-db", null);
        db = mHelper.getWritableDatabase();
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    public SQLiteDatabase getDb() {
        return db;
    }


}
