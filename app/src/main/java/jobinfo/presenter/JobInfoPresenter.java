package jobinfo.presenter;


import android.os.Handler;

import java.util.List;

import jobinfo.bean.JobInfoBean;
import jobinfo.listener.LoadJobInfoListener;
import jobinfo.module.IJobInfoModule;
import jobinfo.module.JobInfoModule;
import jobinfo.view.IJobInfoActivity;

public class JobInfoPresenter implements IJobInfoPresenter {

    private IJobInfoActivity iJobInfoActivity;
    private IJobInfoModule iJobInfoModule;
    private Handler mHandler = new Handler();

    public JobInfoPresenter(IJobInfoActivity iJobInfoActivity) {
        this.iJobInfoActivity = iJobInfoActivity;
        iJobInfoModule = new JobInfoModule();
    }

    @Override
    public void load(String url) {
        iJobInfoModule.load(url, new LoadJobInfoListener() {
            @Override
            public void loadSuccess(final List<JobInfoBean> jobInfoBeanList) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iJobInfoActivity.showSuccess(jobInfoBeanList);
                    }
                });
            }

            @Override
            public void loadFailure(final String failure) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iJobInfoActivity.showFailure(failure);
                    }
                });
            }
        });
    }

    @Override
    public void destroy() {
        iJobInfoModule.cancelTask();
        if (iJobInfoModule != null)
            iJobInfoModule = null;
        if (iJobInfoActivity != null)
            iJobInfoActivity = null;
        if (mHandler != null)
            mHandler = null;
    }
}
