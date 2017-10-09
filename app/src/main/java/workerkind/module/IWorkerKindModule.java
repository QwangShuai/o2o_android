package workerkind.module;


import workerkind.listener.LoadWorkerKindListener;

public interface IWorkerKindModule {

    void load(LoadWorkerKindListener loadWorkerKindListener);

    void cancelTask();
}
