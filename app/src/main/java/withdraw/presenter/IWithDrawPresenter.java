package withdraw.presenter;

import bean.WithDrawBean;

/**
 * Created by Administrator on 2017/10/24.
 */

public interface IWithDrawPresenter {

    void withdraw(WithDrawBean withDrawBean);

    void destroy();
}
