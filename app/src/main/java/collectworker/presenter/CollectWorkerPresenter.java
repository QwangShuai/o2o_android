package collectworker.presenter;


import android.os.Handler;

import java.util.List;

import collectworker.bean.CollectWorkerBean;
import collectworker.listener.OnLoadCollectWorkerListener;
import collectworker.module.CollectWorkerModule;
import collectworker.module.ICollectWorkerModule;
import collectworker.view.ICollectWorkerFragment;

public class CollectWorkerPresenter implements ICollectWorkerPresenter {

    private ICollectWorkerFragment iCollectWorkerFragment;
    private ICollectWorkerModule iCollectWorkerModule;
    private Handler mHandler;

    public CollectWorkerPresenter(ICollectWorkerFragment iCollectWorkerFragment) {
        this.iCollectWorkerFragment = iCollectWorkerFragment;
        iCollectWorkerModule = new CollectWorkerModule();
        mHandler = new Handler();
    }

    @Override
    public void load(String id) {
        iCollectWorkerFragment.showLoading();
        iCollectWorkerModule.load(id, new OnLoadCollectWorkerListener() {
            @Override
            public void onLoadSuccess(final List<CollectWorkerBean> collectWorkerBeanList) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iCollectWorkerFragment.showLoadSuccess(collectWorkerBeanList);
                        iCollectWorkerFragment.hideLoading();
                    }
                });
            }

            @Override
            public void onLoadFailure(final String failure) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iCollectWorkerFragment.showLoadFailure(failure);
                    }
                });
            }
        });
    }

    @Override
    public void destroy() {
        if (iCollectWorkerModule != null) {
            iCollectWorkerModule.cancelTask();
            iCollectWorkerModule = null;
        }
    }
}
