package getevaluate.module;

import listener.JsonListener;

/**
 * Created by Administrator on 2017/10/25.
 */

public interface IGetEvaluateModule {

    void load(String url, JsonListener jsonListener);

    void cancelTask();
}
