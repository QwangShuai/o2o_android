package complain.presenter;


import android.os.Handler;

import java.util.List;

import complain.bean.ComplainIssueBean;
import complain.listener.OnCplIsListener;
import complain.module.ComplainModule;
import complain.module.IComplainModule;
import complain.view.IComplainActivity;

public class ComplainPresenter implements IComplainPresenter {

    private IComplainActivity iComplainActivity;
    private IComplainModule iComplainModule;
    private Handler mHandler;

    public ComplainPresenter(IComplainActivity iComplainActivity) {
        this.iComplainActivity = iComplainActivity;
        iComplainModule = new ComplainModule();
        mHandler = new Handler();
    }

    @Override
    public void loadCplIs(String typeId) {
        iComplainActivity.showLoading();
        iComplainModule.load(typeId, new OnCplIsListener() {
            @Override
            public void success(final List<ComplainIssueBean> complainIssueBeanList) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iComplainActivity.showLoadIssueSuccess(complainIssueBeanList);
                        iComplainActivity.hideLoading();
                    }
                });
            }

            @Override
            public void failure(String failure) {

            }
        });
    }

    @Override
    public void destory() {
        if (iComplainModule != null) {
            iComplainModule.cancelTask();
            iComplainModule = null;
        }
        if (iComplainActivity != null) {
            iComplainActivity = null;
        }
        if (mHandler != null) {
            mHandler = null;
        }
    }
}
