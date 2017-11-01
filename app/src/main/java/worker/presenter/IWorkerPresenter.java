package worker.presenter;


public interface IWorkerPresenter {

    void load(String url);

    void addCollect(String url);

    void destroy();
}
