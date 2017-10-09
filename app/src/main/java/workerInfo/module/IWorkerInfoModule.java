package workerInfo.module;


import bean.PositionBean;
import workerInfo.listener.LoadWorkerInfoListener;

public interface IWorkerInfoModule {

    void load(String workerKindId, PositionBean positionBean, LoadWorkerInfoListener loadWorkerInfoListener);

    void cancelTask();
}
