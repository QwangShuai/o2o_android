package accountdetail.presenter;

import android.os.Handler;

import java.util.List;

import accountdetail.bean.AccountDetailBean;
import accountdetail.listener.AccountDetailListener;
import accountdetail.module.AccountDetailModule;
import accountdetail.module.IAccountDetailModule;
import accountdetail.view.IAccountDetailActivity;

/**
 * Created by Administrator on 2017/10/24.
 */

public class AccountDetailPresenter implements IAccountDetailPresenter {

    private IAccountDetailActivity accountDetailActivity;
    private IAccountDetailModule accountDetailModule;
    private Handler handler;

    public AccountDetailPresenter(IAccountDetailActivity accountDetailActivity) {
        this.accountDetailActivity = accountDetailActivity;
        accountDetailModule = new AccountDetailModule();
        handler = new Handler();
    }

    @Override
    public void load(String url) {
        accountDetailModule.load(url, new AccountDetailListener() {
            @Override
            public void success(final List<AccountDetailBean> accountDetailBeanList) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        accountDetailActivity.showSuccess(accountDetailBeanList);
                    }
                });
            }

            @Override
            public void failure(final String failure) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        accountDetailActivity.showFailure(failure);
                    }
                });
            }
        });
    }

    @Override
    public void destroy() {
        if (accountDetailModule != null) {
            accountDetailModule.cancelTask();
            accountDetailModule = null;
        }
        if (handler != null) {
            handler = null;
        }
        if (accountDetailActivity != null) {
            accountDetailActivity = null;
        }
    }
}
