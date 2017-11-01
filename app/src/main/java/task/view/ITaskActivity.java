package task.view;

public interface ITaskActivity {

    void loadSuccess(String taskJson);

    void loadFailure(String taskFailure);

    void collectSuccess(String collectJson);

    void collectFailure(String collectFailure);
}
