package accountdetail.listener;

import java.util.List;

import accountdetail.bean.AccountDetailBean;

/**
 * Created by Administrator on 2017/10/24.
 */

public interface AccountDetailListener {

    void success(List<AccountDetailBean> accountDetailBeanList);

    void failure(String failure);
}
