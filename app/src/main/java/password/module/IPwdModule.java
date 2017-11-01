package password.module;

import password.listener.EditPwdListener;
import password.listener.ForgetPwdListener;
import password.listener.ProveOriPwdListener;
import password.listener.SetPwdListener;

/**
 * Created by Administrator on 2017/10/25.
 */

public interface IPwdModule {

    void setPwd(String id, String pass, SetPwdListener setPwdListener);

    void proveOriPwd(String id, String pass, ProveOriPwdListener proveOriPwdListener);

    void editPwd(String id, String oldPass, String newPass, EditPwdListener editPwdListener);

    void forgetPwd(String mobile, String verifycode, String newPwd, String idcard, ForgetPwdListener forgetPwdListener);

    void cancelTask();
}
