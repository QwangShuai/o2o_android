package wallet.module;

import listener.JsonListener;

/**
 * Created by Administrator on 2017/10/30.
 */

public interface IWalletModule {

    void load(String url, JsonListener jsonListener);

    void cancelTask();
}
