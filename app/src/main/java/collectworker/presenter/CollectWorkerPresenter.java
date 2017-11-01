package collectworker.presenter;


import android.os.Handler;

import collectworker.module.CollectWorkerModule;
import collectworker.module.ICollectWorkerModule;
import collectworker.view.ICollectWorkerFragment;
import listener.JsonListener;

public class CollectWorkerPresenter implements ICollectWorkerPresenter {

    private ICollectWorkerFragment collectWorkerFragment;
    private ICollectWorkerModule collectWorkerModule;
    private Handler handler;

    public CollectWorkerPresenter(ICollectWorkerFragment collectWorkerFragment) {
        this.collectWorkerFragment = collectWorkerFragment;
        collectWorkerModule = new CollectWorkerModule();
        handler = new Handler();
    }

    @Override
    public void load(String url) {
        collectWorkerModule.load(url, new JsonListener() {
            @Override
            public void success(final String json) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        collectWorkerFragment.loadSuccess(json);
                    }
                });
            }

            @Override
            public void failure(final String failure) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        collectWorkerFragment.loadFailure(failure);
                    }
                });
            }
        });
    }

    @Override
    public void cancelCollect(String url) {
        collectWorkerModule.cancelCollect(url, new JsonListener() {
            @Override
            public void success(final String json) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        collectWorkerFragment.cancelCollectSuccess(json);
                    }
                });
            }

            @Override
            public void failure(final String failure) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        collectWorkerFragment.cancelCollectFailure(failure);
                    }
                });
            }
        });
    }

    @Override
    public void destroy() {
        if (collectWorkerModule != null) {
            collectWorkerModule.cancelTask();
            collectWorkerModule = null;
        }
        if (handler != null) {
            handler = null;
        }
        if (collectWorkerFragment != null) {
            collectWorkerFragment = null;
        }
    }
}
