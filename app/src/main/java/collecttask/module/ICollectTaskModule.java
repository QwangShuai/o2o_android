package collecttask.module;


import listener.JsonListener;

public interface ICollectTaskModule {

    void load(String url, JsonListener jsonListener);

    void cancelCollect(String url,JsonListener jsonListener);

    void cancelTask();
}
