package phoneprove.view;

/**
 * Created by Administrator on 2017/10/25.
 */

public interface IPhoneProveActivity {

    void showVerifyCodeSuccess(String success);

    void showVerifyCodeFailure(String failure);

    void showProveMobileCodeSuccess(String success);

    void showProveMobileCodeFailure(String failure);
}
