package collectworker.module;


import collectworker.listener.OnLoadCollectWorkerListener;

public interface ICollectWorkerModule {

    void load(String id, OnLoadCollectWorkerListener onLoadCollectWorkerListener);

    void cancelTask();
}
