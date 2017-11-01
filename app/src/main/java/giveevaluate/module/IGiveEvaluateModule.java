package giveevaluate.module;

import listener.JsonListener;

/**
 * Created by Administrator on 2017/10/29.
 */

public interface IGiveEvaluateModule {

    void load(String url, JsonListener jsonListener);

    void cancelTask();
}
