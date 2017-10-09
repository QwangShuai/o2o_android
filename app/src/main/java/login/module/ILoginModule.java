package login.module;


import login.listener.GetSecurityCodeListener;
import login.listener.LoginListener;

public interface ILoginModule {

    void getSecurityCode(String phoneNumber, GetSecurityCodeListener getSecurityCodeListener);

    void login(String phoneNumber, String securityCode, LoginListener loginListener);

    void cancelTask();
}
