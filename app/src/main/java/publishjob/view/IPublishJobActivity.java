package publishjob.view;

import java.util.List;

/**
 * Created by Administrator on 2017/10/26.
 */

public interface IPublishJobActivity {

    void taskTypeSuccess(List<String> taskTypeList);

    void taskTypeFailure(String failure);

    void skillSuccess(String json);

    void skillFailure(String failure);
}
