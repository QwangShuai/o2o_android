package publishjob.listener;

import java.util.List;

/**
 * Created by Administrator on 2017/10/26.
 */

public interface TaskTypeListener {

    void success(List<String> taskTypeList);

    void failure(String failure);
}
