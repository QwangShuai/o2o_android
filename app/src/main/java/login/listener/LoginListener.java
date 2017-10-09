package login.listener;


import login.bean.UserBean;

public interface LoginListener {

    void loginSuccess(UserBean userBean);

    void loginFailure(String loginFailure);

}
