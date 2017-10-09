package city.module;


import android.content.Context;

import city.listener.LoadCityListener;

public interface ICityModule {

    void load(Context context, LoadCityListener loadCityListener);
}
