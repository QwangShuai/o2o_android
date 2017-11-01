package login.view;


import login.bean.UserBean;

public interface ILoginActivity {

    void showLoading();

    void hideLoading();

    void getSecurityCodeFailure(String codeFailure);

    void getSecurityCodeSuccess(String codeSuccess);

    void loginFailure(String loginFailure);

    void loginSuccess(UserBean userBean);

    void postOnlineFailure();

    void postOnlineSuccess();
}
