package publishjob.module;

import publishjob.listener.GetSkillListener;
import publishjob.listener.TaskTypeListener;

/**
 * Created by Administrator on 2017/10/26.
 */

public interface IPublishJobModule {

    void getTaskType(String url, TaskTypeListener taskTypeListener);

    void getSkill(String url, GetSkillListener getSkillListener);

    void cancelTask();
}
