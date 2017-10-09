package login.listener;


public interface GetSecurityCodeListener {

    void getSecurityCodeSuccess(String codeSuccess);

    void getSecurityCodeFailure(String codeFailure);

}
