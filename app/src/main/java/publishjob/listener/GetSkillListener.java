package publishjob.listener;

/**
 * Created by Administrator on 2017/10/26.
 */

public interface GetSkillListener {

    void success(String json);

    void failure(String failure);
}
