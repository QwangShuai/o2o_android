package login.presenter;


import android.os.Handler;

import login.bean.UserBean;
import login.listener.GetSecurityCodeListener;
import login.listener.LoginListener;
import login.module.ILoginModule;
import login.module.LoginModule;
import login.view.ILoginActivity;

public class LoginPresenter implements ILoginPresenter {

    private ILoginActivity iLoginActivity;
    private ILoginModule iLoginModule;
    private Handler mHandler = new Handler();

    public LoginPresenter(ILoginActivity iLoginActivity) {
        this.iLoginActivity = iLoginActivity;
        iLoginModule = new LoginModule();
    }

    @Override
    public void getSecurityCode(String phoneNumber) {
        iLoginActivity.showLoading();
        iLoginModule.getSecurityCode(phoneNumber, new GetSecurityCodeListener() {
            @Override
            public void getSecurityCodeSuccess(final String codeSuccess) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iLoginActivity.getSecurityCodeSuccess(codeSuccess);
                        iLoginActivity.hideLoading();
                    }
                });
            }

            @Override
            public void getSecurityCodeFailure(final String codeFailure) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iLoginActivity.getSecurityCodeFailure(codeFailure);
                        iLoginActivity.hideLoading();
                    }
                });
            }
        });
    }

    @Override
    public void login(String phoneNumber, String securityCode) {
        iLoginActivity.showLoading();
        iLoginModule.login(phoneNumber, securityCode, new LoginListener() {
            @Override
            public void loginSuccess(final UserBean userBean) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iLoginActivity.loginSuccess(userBean);
                        iLoginActivity.hideLoading();
                    }
                });
            }

            @Override
            public void loginFailure(final String loginFailure) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iLoginActivity.loginFailure(loginFailure);
                        iLoginActivity.hideLoading();
                    }
                });
            }
        });
    }

    @Override
    public void destroy() {
        if (iLoginModule != null) {
            iLoginModule.cancelTask();
            iLoginModule = null;
        }
        if (iLoginActivity != null)
            iLoginActivity = null;
        if (mHandler != null)
            mHandler = null;
    }
}
