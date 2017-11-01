package login.module;


import login.listener.GetSecurityCodeListener;
import login.listener.LoginListener;
import login.listener.PostOnLineListener;

public interface ILoginModule {

    void getSecurityCode(String phoneNumber, GetSecurityCodeListener getSecurityCodeListener);

    void login(String phoneNumber, String securityCode, LoginListener loginListener);

    void postOnLine(String id, PostOnLineListener postOnLineListener);

    void cancelTask();
}
