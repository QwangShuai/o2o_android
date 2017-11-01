package set.module;

import set.listener.QuitListener;

/**
 * Created by Administrator on 2017/10/24.
 */

public interface ISetModule {

    void quit(String id, QuitListener quitListener);

    void cancelTask();
}
