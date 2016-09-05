package io.github.ylbfdev.wechatwallet;

import android.app.Application;

import im.fir.sdk.FIR;
import io.github.ylbfdev.wechatwallet.utils.Tools;

/**
 * Created by ylbf_ on 2016/9/5.
 */
public class App extends Application {
    private static int mLocalVersionCode;
    private static String mLocalVersionName;

    public static int getmLocalVersionCode() {
        return mLocalVersionCode;
    }

    public static String getmLocalVersionName() {
        return mLocalVersionName;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        FIR.init(this);
        mLocalVersionCode = Tools.getAppPackageInfo(this).versionCode;
        mLocalVersionName = Tools.getAppPackageInfo(this).versionName;
    }
}
