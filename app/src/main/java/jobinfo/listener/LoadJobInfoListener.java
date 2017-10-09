package jobinfo.listener;


import java.util.List;

import jobinfo.bean.JobInfoBean;

public interface LoadJobInfoListener {

    void loadSuccess(List<JobInfoBean> jobInfoBeanList);

    void loadFailure(String failure);
}
