package complain.listener;


import java.util.List;

import complain.bean.ComplainIssueBean;

public interface OnCplIsListener {

    void success(List<ComplainIssueBean> complainIssueBeanList);

    void failure(String failure);
}
