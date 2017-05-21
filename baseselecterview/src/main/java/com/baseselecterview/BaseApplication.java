package com.baseselecterview;

import android.app.Application;
import android.content.Context;

/**
 * Created by wanghailong on 2017/5/22.
 */

public class BaseApplication extends Application {
    private static Context mContext;

    public static Context getContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

}
