package workerkind.listener;


import java.util.List;

import workerkind.bean.WorkerKindBean;

public interface LoadWorkerKindListener {

    void loadSuccess(List<WorkerKindBean> workerKindBeanList);

    void loadFailure(String failure);
}
