package usermanage.view;


import android.graphics.Bitmap;

import usermanage.bean.UserInfoBean;

public interface IUserManageActivity {

    void showLoading();

    void hideLoading();

    void showUpLoadIconFailure(String upLoadIconFailure);

    void showUpLoadIconSuccess(String upLoadIconSuccess, Bitmap bitmap);

    void showLoadUserInfoSuccess(UserInfoBean userInfoBean);

    void showLoadUserInfoFailure(String failure);

    void showUserSkillSuccess(UserInfoBean uib);

    void showUserSkillFailure(String failure);
}
