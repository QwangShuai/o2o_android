package application;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * 创建日期：2017/7/31 on 16:31
 * 作者:孙明明
 * 描述:自定义Application
 */

public class CApplication extends Application {

    private static final String APP_ID = "wx88a7414f850651c8";
    private IWXAPI iwxapi;

    @Override
    public void onCreate() {
        super.onCreate();
        SDKInitializer.initialize(getApplicationContext());
        regToWx();
    }

    private void regToWx() {
        iwxapi = WXAPIFactory.createWXAPI(this, APP_ID, true);
        iwxapi.registerApp(APP_ID);
    }
}
