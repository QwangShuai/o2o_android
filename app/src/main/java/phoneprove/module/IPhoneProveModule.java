package phoneprove.module;

import phoneprove.listener.ForgetPwdCodeListener;
import phoneprove.listener.ProveMobileCodeListener;

/**
 * Created by Administrator on 2017/10/25.
 */

public interface IPhoneProveModule {

    void getVerifyCode(String mobile, ForgetPwdCodeListener forgetPwdCodeListener);

    void proveMobileCode(String mobile, String code, ProveMobileCodeListener proveMobileCodeListener);

    void cancelTask();
}
