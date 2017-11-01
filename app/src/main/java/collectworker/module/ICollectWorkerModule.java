package collectworker.module;


import listener.JsonListener;

public interface ICollectWorkerModule {

    void load(String url, JsonListener jsonListener);

    void cancelCollect(String url, JsonListener jsonListener);

    void cancelTask();
}
