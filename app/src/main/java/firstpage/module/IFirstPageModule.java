package firstpage.module;


import android.content.Context;

import firstpage.listener.LoadComCityListener;
import firstpage.listener.LoadHotCityListener;
import firstpage.listener.LocateCityListener;
import listener.OnLoadComCityListener;
import listener.OnLoadHotCityListener;

public interface IFirstPageModule {

    void loadHotCityData(String hotUrl, LoadHotCityListener loadHotCityListener);

    void loadComCityData(String comUrl, LoadComCityListener loadComCityListener);

    void saveHotCityData(Context context, String userId, String hotJson, String cacheTime, OnLoadHotCityListener onLoadHotCityListener);

    void saveComCityData(Context context, String userId, String comJson, String cacheTime, OnLoadComCityListener onLoadComCityListener);

    void locateCity(Context context,String userId,String cityName,LocateCityListener locateCityListener);

    void cancelTask();
}
