package collectjob.listener;


import java.util.List;

import collectjob.bean.CollectJobBean;

public interface OnLoadCollectJobListener {

    void onLoadSuccess(List<CollectJobBean> collectJobBeanList);

    void onLoadFailure(String failure);
}
