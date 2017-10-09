package jobinfo.view;


import java.util.List;

import jobinfo.bean.JobInfoBean;

public interface IJobInfoActivity {

    void showSuccess(List<JobInfoBean> jobInfoBeanList);

    void showFailure(String failure);
}
