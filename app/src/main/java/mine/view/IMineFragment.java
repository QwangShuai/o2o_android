package mine.view;

import usermanage.bean.UserInfoBean;

/**
 * Created by Administrator on 2017/10/24.
 */

public interface IMineFragment {

    void showUserInfoSuccess(UserInfoBean userInfoBean);

    void showUserInfoFailure(String failure);

    void showPostOnlineSuccess(String success);

    void showPostOnlineFailure(String failure);
}
