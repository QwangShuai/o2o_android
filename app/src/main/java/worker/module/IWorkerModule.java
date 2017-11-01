package worker.module;


import listener.JsonListener;

public interface IWorkerModule {

    void load(String url, JsonListener jsonListener);

    void addCollect(String url, JsonListener jsonListener);

    void cancelTask();
}
