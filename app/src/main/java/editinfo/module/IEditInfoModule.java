package editinfo.module;


import editinfo.listener.AddSkillListener;
import editinfo.listener.SubmitListener;
import usermanage.bean.UserInfoBean;

public interface IEditInfoModule {

    void load(String url, AddSkillListener addSkillListener);

    void submit(UserInfoBean userInfoBean, SubmitListener submitListener);

    void cancelTask();
}
