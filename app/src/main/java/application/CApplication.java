package application;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;

/**
 * 创建日期：2017/7/31 on 16:31
 * 作者:孙明明
 * 描述:自定义Application
 */

public class CApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        SDKInitializer.initialize(getApplicationContext());
    }
}
