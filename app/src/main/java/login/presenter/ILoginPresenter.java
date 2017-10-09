package login.presenter;


public interface ILoginPresenter {

    void getSecurityCode(String phoneNumber);

    void login(String phoneNumber, String securityCode);

    void destroy();
}
