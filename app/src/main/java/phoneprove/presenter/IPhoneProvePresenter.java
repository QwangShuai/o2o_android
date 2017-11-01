package phoneprove.presenter;

/**
 * Created by Administrator on 2017/10/25.
 */

public interface IPhoneProvePresenter {

    void getVerifyCode(String mobile);

    void proveMobileCode(String mobile,String code);

    void destroy();
}
