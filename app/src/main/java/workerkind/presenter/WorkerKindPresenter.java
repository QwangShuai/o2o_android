package workerkind.presenter;


import android.os.Handler;

import java.util.List;

import workerkind.bean.WorkerKindBean;
import workerkind.listener.LoadWorkerKindListener;
import workerkind.module.IWorkerKindModule;
import workerkind.module.WorkerKindModule;
import workerkind.view.IWorkerKindActivity;

public class WorkerKindPresenter implements IWorkerKindPresenter {

    private IWorkerKindActivity iWorkerKindActivity;
    private IWorkerKindModule iWorkerKindModule;
    private Handler mHandler = new Handler();

    public WorkerKindPresenter(IWorkerKindActivity iWorkerKindActivity) {
        this.iWorkerKindActivity = iWorkerKindActivity;
        iWorkerKindModule = new WorkerKindModule();
    }

    @Override
    public void load() {
        iWorkerKindActivity.showLoading();
        iWorkerKindModule.load(new LoadWorkerKindListener() {
            @Override
            public void loadSuccess(final List<WorkerKindBean> workerKindBeanList) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iWorkerKindActivity.showSuccess(workerKindBeanList);
                        iWorkerKindActivity.hideLoading();
                    }
                });
            }

            @Override
            public void loadFailure(final String failure) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iWorkerKindActivity.showFailure(failure);
                    }
                });
            }
        });
    }

    @Override
    public void destroy() {
        iWorkerKindModule.cancelTask();
        if (iWorkerKindModule != null)
            iWorkerKindModule = null;
        if (iWorkerKindActivity != null)
            iWorkerKindActivity = null;
        if (mHandler != null)
            mHandler = null;
    }
}
