package jobinfo.module;


import jobinfo.listener.LoadJobInfoListener;

public interface IJobInfoModule {

    void load(String url,LoadJobInfoListener loadJobInfoListener);

    void cancelTask();
}
