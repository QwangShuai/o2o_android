package task.presenter;


public interface ITaskPresenter {

    void load(String url);

    void collect(String url);

    void destroy();
}
