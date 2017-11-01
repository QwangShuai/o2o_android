package collectworker.presenter;


public interface ICollectWorkerPresenter {

    void load(String url);

    void cancelCollect(String url);

    void destroy();
}
