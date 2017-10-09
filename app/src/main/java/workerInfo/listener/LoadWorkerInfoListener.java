package workerInfo.listener;


import java.util.List;

import workerInfo.bean.WorkerInfoBean;

public interface LoadWorkerInfoListener {

    void loadSuccess(List<WorkerInfoBean> workerInfoBeanList);

    void loadFailure(String failure);
}
