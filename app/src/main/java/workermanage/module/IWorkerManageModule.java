package workermanage.module;


import listener.JsonListener;

public interface IWorkerManageModule {

    void load(String url, JsonListener jsonListener);

    void cancelTask();
}
