package editinfo.listener;


import java.util.List;

import bean.SkillBean;

public interface AddSkillListener {

    void success(List<SkillBean> skillBeanList);

    void failure(String failure);
}
