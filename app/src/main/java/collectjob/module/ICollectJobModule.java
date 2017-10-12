package collectjob.module;


import collectjob.listener.OnLoadCollectJobListener;

public interface ICollectJobModule {

    void load(String id, OnLoadCollectJobListener onLoadCollectJobListener);

    void cancelTask();
}
