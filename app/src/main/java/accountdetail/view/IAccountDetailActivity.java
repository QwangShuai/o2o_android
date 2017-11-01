package accountdetail.view;

import java.util.List;

import accountdetail.bean.AccountDetailBean;

/**
 * Created by Administrator on 2017/10/24.
 */

public interface IAccountDetailActivity {

    void showSuccess(List<AccountDetailBean> accountDetailBeanList);

    void showFailure(String failure);
}
