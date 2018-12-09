package com.testapp.testtask;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.testapp.testtask.entities.DaoMaster;
import com.testapp.testtask.entities.DaoSession;
import com.testapp.testtask.utils.UserPreferences;

import org.greenrobot.greendao.database.Database;

public class BaseApplication extends Application {
    public static Context context;
    private static DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        UserPreferences.init(context);
        initDB();
    }

    private void initDB() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "testapp_db");
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

    public static boolean isConnect() {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public static DaoSession getDaoSession() {
        return daoSession;
    }
}
