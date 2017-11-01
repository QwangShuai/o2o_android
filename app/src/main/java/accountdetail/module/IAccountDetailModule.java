package accountdetail.module;

import accountdetail.listener.AccountDetailListener;

/**
 * Created by Administrator on 2017/10/24.
 */

public interface IAccountDetailModule {

    void load(String url, AccountDetailListener accountDetailListener);

    void cancelTask();
}
