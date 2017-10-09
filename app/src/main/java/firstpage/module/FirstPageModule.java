package firstpage.module;


import android.content.Context;
import android.text.TextUtils;

import java.io.IOException;

import cache.LruJsonCache;
import config.CacheConfig;
import firstpage.listener.LoadComCityListener;
import firstpage.listener.LoadHotCityListener;
import firstpage.listener.LocateCityListener;
import listener.OnLoadComCityListener;
import listener.OnLoadHotCityListener;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import utils.Utils;

public class FirstPageModule implements IFirstPageModule {

    private OkHttpClient okHttpClient;
    private LruJsonCache lruJsonCache;
    private Call hotCall, comCall;

    public FirstPageModule() {
        okHttpClient = new OkHttpClient();
    }

    @Override
    public void loadHotCityData(String hotUrl, final LoadHotCityListener loadHotCityListener) {
        Request request = new Request.Builder().url(hotUrl).get().build();
        hotCall = okHttpClient.newCall(request);
        hotCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String json = response.body().string();
                    if (!TextUtils.isEmpty(json)) {
                        loadHotCityListener.loadHotCitySuccess(json);
                    }
                }
            }
        });
    }

    @Override
    public void loadComCityData(String comUrl, final LoadComCityListener loadComCityListener) {
        Request request = new Request.Builder().url(comUrl).get().build();
        comCall = okHttpClient.newCall(request);
        comCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String json = response.body().string();
                    if (!TextUtils.isEmpty(json)) {
                        loadComCityListener.loadComCitySuccess(json);
                    }
                }
            }
        });
    }

    @Override
    public void saveHotCityData(Context context, String userId, String hotJson, String cacheTime, OnLoadHotCityListener onLoadHotCityListener) {
        lruJsonCache = LruJsonCache.get(context);
        lruJsonCache.setOnLoadHotCityListener(onLoadHotCityListener);
        Utils.writeCache(context, userId, CacheConfig.hotCity, hotJson, cacheTime);
    }

    @Override
    public void saveComCityData(Context context, String userId, String comJson, String cacheTime, OnLoadComCityListener onLoadComCityListener) {
        lruJsonCache.setOnLoadComCityListener(onLoadComCityListener);
        Utils.writeCache(context, userId, CacheConfig.comCity, comJson, cacheTime);
    }

    @Override
    public void locateCity(Context context, String userId, String cityName, LocateCityListener locateCityListener) {
        String json = lruJsonCache.getAsString(userId + CacheConfig.comCity);
        if (!TextUtils.isEmpty(json)) {
            String localCityId = Utils.getLocCityId(context, cityName, json);
            locateCityListener.locateCitySuccess(localCityId);
        }
    }


    @Override
    public void cancelTask() {
        if (hotCall != null) {
            hotCall.cancel();
            hotCall = null;
        }
        if (comCall != null) {
            comCall.cancel();
            comCall = null;
        }
        if (okHttpClient != null) {
            okHttpClient = null;
        }
        if (lruJsonCache != null) {
            lruJsonCache = null;
        }
    }
}
