package complain.module;


import complain.listener.OnCplIsListener;

public interface IComplainModule {

    void load(String typeId, OnCplIsListener onCplIsListener);

    void cancelTask();
}
