package firstpage.presenter;


import android.content.Context;
import android.os.Handler;

import firstpage.listener.LoadComCityListener;
import firstpage.listener.LoadHotCityListener;
import firstpage.listener.LocateCityListener;
import firstpage.module.FirstPageModule;
import firstpage.module.IFirstPageModule;
import firstpage.view.IFirstPageFragment;
import listener.OnLoadComCityListener;
import listener.OnLoadHotCityListener;

public class FirstPagePresenter implements IFirstPagePresenter {

    private IFirstPageFragment iFirstPageFragment;
    private IFirstPageModule iFirstPageModule;
    private Handler mHandler = new Handler();

    public FirstPagePresenter(IFirstPageFragment iFirstPageFragment) {
        this.iFirstPageFragment = iFirstPageFragment;
        iFirstPageModule = new FirstPageModule();
    }

    @Override
    public void loadHotCity(String hotUrl) {
        iFirstPageFragment.showLoading();
        iFirstPageModule.loadHotCityData(hotUrl, new LoadHotCityListener() {
            @Override
            public void loadHotCitySuccess(final String hotJson) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iFirstPageFragment.showHotJson(hotJson);
                    }
                });
            }
        });
    }

    @Override
    public void loadComCity(String comUrl) {
        iFirstPageModule.loadComCityData(comUrl, new LoadComCityListener() {
            @Override
            public void loadComCitySuccess(final String comJson) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iFirstPageFragment.showComJson(comJson);
                    }
                });
            }
        });
    }

    @Override
    public void saveHotCity(Context context, String userId, String hotJson, String cacheTime) {
        iFirstPageModule.saveHotCityData(context, userId, hotJson, cacheTime, new OnLoadHotCityListener() {
            @Override
            public void hotResult() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iFirstPageFragment.showSaveHotJsonSuccess();
                    }
                });
            }
        });
    }

    @Override
    public void saveComCity(Context context, String userId, String comJson, String cacheTime) {
        iFirstPageModule.saveComCityData(context, userId, comJson, cacheTime, new OnLoadComCityListener() {
            @Override
            public void comResult() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iFirstPageFragment.showSaveComJsonSuccess();
                    }
                });
            }
        });
    }

    @Override
    public void getLocateCityId(Context context, String userId, String cityName) {
        iFirstPageModule.locateCity(context, userId, cityName, new LocateCityListener() {
            @Override
            public void locateCitySuccess(final String locateCity) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iFirstPageFragment.getLocateCityId(locateCity);
                    }
                });
            }
        });
    }


    @Override
    public void destroy() {
        if (iFirstPageModule != null) {
            iFirstPageModule.cancelTask();
            iFirstPageModule = null;
        }
        if (iFirstPageFragment != null) {
            iFirstPageFragment = null;
        }
        if (mHandler != null) {
            mHandler = null;
        }
    }
}
