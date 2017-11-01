package selecttask.module;

import listener.JsonListener;

/**
 * Created by Administrator on 2017/10/30.
 */

public interface ISelectTaskModule {

    void load(String url, JsonListener jsonListener);

    void invite(String url,JsonListener jsonListener);

    void cancelTask();
}
