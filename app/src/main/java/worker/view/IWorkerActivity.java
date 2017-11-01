package worker.view;

public interface IWorkerActivity {

    void loadSuccess(String workerJson);

    void loadFailure(String loadfailure);

    void collectSuccess(String collectJson);

    void collectFailure(String collectfailure);

}
