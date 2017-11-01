package worker.presenter;

import android.os.Handler;

import listener.JsonListener;
import worker.module.IWorkerModule;
import worker.module.WorkerModule;
import worker.view.IWorkerActivity;

public class WorkerPresenter implements IWorkerPresenter {

    private IWorkerActivity workerActivity;
    private IWorkerModule workerModule;
    private Handler handler;

    public WorkerPresenter(IWorkerActivity workerActivity) {
        this.workerActivity = workerActivity;
        workerModule = new WorkerModule();
        handler = new Handler();
    }

    @Override
    public void load(String url) {
        workerModule.load(url, new JsonListener() {
            @Override
            public void success(final String json) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        workerActivity.loadSuccess(json);
                    }
                });
            }

            @Override
            public void failure(final String failure) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        workerActivity.loadFailure(failure);
                    }
                });
            }
        });
    }

    @Override
    public void addCollect(String url) {
        workerModule.addCollect(url, new JsonListener() {
            @Override
            public void success(final String json) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        workerActivity.collectSuccess(json);
                    }
                });
            }

            @Override
            public void failure(final String failure) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        workerActivity.collectFailure(failure);
                    }
                });
            }
        });
    }

    @Override
    public void destroy() {
        workerModule.cancelTask();
        if (workerModule != null)
            workerModule = null;
        if (workerActivity != null)
            workerActivity = null;
        if (handler != null)
            handler = null;

    }
}
