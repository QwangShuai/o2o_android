package password.view;

/**
 * Created by Administrator on 2017/10/25.
 */

public interface IPwdActivity {

    void showSetPwdSuccess(String success);

    void showSetPwdFailure(String failure);

    void showProveOriPwdSuccess(String success);

    void showProveOriPwdFailure(String failure);

    void showEditPwdSuccess(String success);

    void showEditPwdFailure(String failure);

    void showForgetPwdSuccess(String success);

    void showForgetPwdFailure(String failure);
}
