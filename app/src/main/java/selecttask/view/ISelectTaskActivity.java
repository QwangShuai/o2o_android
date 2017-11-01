package selecttask.view;

/**
 * Created by Administrator on 2017/10/30.
 */

public interface ISelectTaskActivity {

    void loadSuccess(String json);

    void loadFailure(String failure);

    void inviteSuccess(String json);

    void inviteFailure(String failure);
}
