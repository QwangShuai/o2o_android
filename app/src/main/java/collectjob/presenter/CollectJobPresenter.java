package collectjob.presenter;


import android.os.Handler;

import java.util.List;

import collectjob.bean.CollectJobBean;
import collectjob.listener.OnLoadCollectJobListener;
import collectjob.module.CollectJobModule;
import collectjob.module.ICollectJobModule;
import collectjob.view.ICollectJobFragment;

public class CollectJobPresenter implements ICollectJobPresenter {

    private ICollectJobFragment iCollectJobFragment;
    private ICollectJobModule iCollectJobModule;
    private Handler mHandler;

    public CollectJobPresenter(ICollectJobFragment iCollectJobFragment) {
        this.iCollectJobFragment = iCollectJobFragment;
        iCollectJobModule = new CollectJobModule();
        mHandler = new Handler();
    }

    @Override
    public void load(String id) {
        iCollectJobFragment.showLoading();
        iCollectJobModule.load(id, new OnLoadCollectJobListener() {
            @Override
            public void onLoadSuccess(final List<CollectJobBean> collectJobBeanList) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iCollectJobFragment.showLoadSuccess(collectJobBeanList);
                        iCollectJobFragment.hideLoading();
                    }
                });
            }

            @Override
            public void onLoadFailure(final String failure) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iCollectJobFragment.showLoadFailure(failure);
                    }
                });
            }
        });
    }

    @Override
    public void destroy() {
        if (iCollectJobModule != null) {
            iCollectJobModule.cancelTask();
            iCollectJobModule = null;
        }
        if (iCollectJobFragment != null) {
            iCollectJobFragment = null;
        }
        if (mHandler != null) {
            mHandler = null;
        }
    }
}
