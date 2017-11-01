package editinfo.presenter;


import usermanage.bean.UserInfoBean;

public interface IEditInfoPresenter {

    void load(String url);

    void submit(UserInfoBean userInfoBean);

    void destroy();
}
