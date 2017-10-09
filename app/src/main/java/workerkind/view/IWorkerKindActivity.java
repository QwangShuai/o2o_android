package workerkind.view;


import java.util.List;

import workerkind.bean.WorkerKindBean;

public interface IWorkerKindActivity {

    void showSuccess(List<WorkerKindBean> workerKindBeanList);

    void showFailure(String failure);

    void showLoading();

    void hideLoading();

}
