package complain.view;


import java.util.List;

import complain.bean.ComplainIssueBean;

public interface IComplainActivity {

    void showLoading();

    void hideLoading();

    void showLoadIssueSuccess(List<ComplainIssueBean> complainIssueBeanList);

    void showLoadIssueFailure(String failure);

}
