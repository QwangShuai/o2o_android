package usermanage.presenter;


import android.content.Context;
import android.net.Uri;

import usermanage.bean.UserInfoBean;

public interface IUserManagePresenter {

    void loadUserInfo(String url);

    void loadUserSkill(UserInfoBean userInfoBean);

    void upLoadIcon(Context context, String id, Uri uri);

    void destroy();
}
