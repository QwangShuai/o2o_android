package collectworker.view;


import java.util.List;

import collectworker.bean.CollectWorkerBean;

public interface ICollectWorkerFragment {

    void showLoading();

    void hideLoading();

    void showLoadSuccess(List<CollectWorkerBean> collectWorkerBeanList);

    void showLoadFailure(String failure);
}
