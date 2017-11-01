package usermanage.listener;


import usermanage.bean.UserInfoBean;

public interface UserInfoListener {

    void success(UserInfoBean userInfoBean);

    void failure(String failure);
}
