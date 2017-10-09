package workerInfo.presenter;

import android.os.Handler;

import java.util.List;

import bean.PositionBean;
import workerInfo.bean.WorkerInfoBean;
import workerInfo.listener.LoadWorkerInfoListener;
import workerInfo.module.IWorkerInfoModule;
import workerInfo.module.WorkerInfoModule;
import workerInfo.view.IWorkerInfoActivity;

public class WorkerInfoPresenter implements IWorkerInfoPresenter {

    private IWorkerInfoActivity iWorkerInfoActivity;
    private IWorkerInfoModule iWorkerInfoModule;
    private Handler mHandler = new Handler();

    public WorkerInfoPresenter(IWorkerInfoActivity iWorkerInfoActivity) {
        this.iWorkerInfoActivity = iWorkerInfoActivity;
        iWorkerInfoModule = new WorkerInfoModule();
    }

    @Override
    public void load(String workerKindId, PositionBean positionBean) {
        iWorkerInfoModule.load(workerKindId, positionBean, new LoadWorkerInfoListener() {
            @Override
            public void loadSuccess(final List<WorkerInfoBean> workerInfoBeanList) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iWorkerInfoActivity.showSuccess(workerInfoBeanList);
                    }
                });
            }

            @Override
            public void loadFailure(final String failure) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iWorkerInfoActivity.showFailure(failure);
                    }
                });
            }
        });
    }

    @Override
    public void destroy() {
        iWorkerInfoModule.cancelTask();
        if (iWorkerInfoModule != null)
            iWorkerInfoModule = null;
        if (iWorkerInfoActivity != null)
            iWorkerInfoActivity = null;
        if (mHandler != null)
            mHandler = null;

    }
}
