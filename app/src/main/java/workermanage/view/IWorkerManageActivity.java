package workermanage.view;


import java.util.List;

import bean.WorkerManageBean;

public interface IWorkerManageActivity {

    void loadSuccess(String json);

    void loadFailure(String failure);
}
