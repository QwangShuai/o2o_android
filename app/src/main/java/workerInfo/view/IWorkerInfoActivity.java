package workerInfo.view;

import java.util.List;

import workerInfo.bean.WorkerInfoBean;

public interface IWorkerInfoActivity {

    void showSuccess(List<WorkerInfoBean> workerInfoBeanList);

    void showFailure(String failure);
}
