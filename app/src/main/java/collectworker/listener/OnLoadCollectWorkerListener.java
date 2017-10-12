package collectworker.listener;


import java.util.List;

import collectworker.bean.CollectWorkerBean;

public interface OnLoadCollectWorkerListener {

    void onLoadSuccess(List<CollectWorkerBean> collectWorkerBeanList);

    void onLoadFailure(String failure);
}
