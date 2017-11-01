package usermanage.module;


import android.content.Context;
import android.net.Uri;

import usermanage.bean.UserInfoBean;
import usermanage.listener.UserInfoListener;
import usermanage.listener.UpLoadingIconListener;
import usermanage.listener.UserSkillListener;

public interface IUserManageModule {

    void loadUserInfo(String url, UserInfoListener userInfoListener);

    void loadUserSkill(UserInfoBean userInfoBean, UserSkillListener userSkillListener);

    void upLoadIcon(Context context, String id, Uri uri, UpLoadingIconListener upLoadingIconListener);

    void cancelTask();
}
