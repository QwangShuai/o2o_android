package editinfo.view;


import java.util.List;

import bean.SkillBean;

public interface IEditInfoFragment {

    void showLoading();

    void hideLoading();

    void showAddSkillSuccess(List<SkillBean> skillBeanList);

    void showAddSkillFailure(String failure);

    void showSubmitSuccess(String success);

    void showSubmitFailure(String failure);
}
