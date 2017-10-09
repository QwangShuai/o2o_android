package city.presenter;


import android.content.Context;
import android.os.Handler;

import java.util.List;

import city.bean.CityBean;
import city.listener.LoadCityListener;
import city.module.CityModule;
import city.module.ICityModule;
import city.view.ICityActivity;

public class CityPresenter implements ICityPresenter {

    private ICityActivity iCityActivity;
    private ICityModule iCityModule;
    private Handler mHandler = new Handler();

    public CityPresenter(ICityActivity iCityActivity) {
        this.iCityActivity = iCityActivity;
        iCityModule = new CityModule();
    }

    @Override
    public void load(Context context) {
        iCityModule.load(context, new LoadCityListener() {
            @Override
            public void loadSuccess(final List<CityBean> cityBeanList) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iCityActivity.showSuccess(cityBeanList);
                    }
                });
            }

            @Override
            public void loadFailure(final String failure) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iCityActivity.showFailure(failure);
                    }
                });
            }
        });
    }

    @Override
    public void destroy() {
        if (iCityModule != null)
            iCityModule = null;
        if (iCityActivity != null)
            iCityActivity = null;
        if (mHandler != null)
            mHandler = null;
    }
}
