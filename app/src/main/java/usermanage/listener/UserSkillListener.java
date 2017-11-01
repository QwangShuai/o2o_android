package usermanage.listener;


import usermanage.bean.UserInfoBean;

public interface UserSkillListener {

    void success(UserInfoBean uib);

    void failure(String failure);
}
