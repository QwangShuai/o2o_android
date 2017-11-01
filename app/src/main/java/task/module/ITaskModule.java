package task.module;


import listener.JsonListener;

public interface ITaskModule {

    void load(String url, JsonListener jsonListener);

    void collect(String url, JsonListener jsonListener);

    void cancelTask();
}
