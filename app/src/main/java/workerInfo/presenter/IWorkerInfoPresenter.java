package workerInfo.presenter;


import bean.PositionBean;

public interface IWorkerInfoPresenter {

    void load(String workerKindId, PositionBean positionBean);

    void destroy();
}
