package com.fengyang.tallynote;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.fengyang.tallynote.database.DBHelper;
import com.fengyang.tallynote.utils.ContansUtils;
import com.fengyang.tallynote.utils.CrashHandler;
import com.fengyang.tallynote.utils.FileUtils;
import com.fengyang.tallynote.utils.SystemUtils;

/**
 * Created by wuhuihui on 2017/6/23.
 */
public class MyApp extends Application {

    private static String TAG = "MyApp";

    public static DBHelper dbHelper;

    public int count = 0;

    @Override
    public void onCreate() {
        super.onCreate();

        CrashHandler.getInstance().init(getApplicationContext());//程序崩溃日志输出保存

        ContansUtils.setPres(this);//设置存储空间，获取编辑器

        dbHelper = new DBHelper(this); //开辟用户数据库

        FileUtils.createDir(); //创建项目文件目录

        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {

            @Override
            public void onActivityStopped(Activity activity) {
                count--;
                if (count == 0) {
                    SystemUtils.setBack(activity);
                }
            }

            @Override
            public void onActivityStarted(Activity activity) {
                if (count == 0) {
                    SystemUtils.setFore(activity);
                }
                count++;
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }

            @Override
            public void onActivityResumed(Activity activity) {
            }

            @Override
            public void onActivityPaused(Activity activity) {
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
            }

            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            }
        });

    }

}
