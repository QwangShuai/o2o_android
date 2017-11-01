package collectworker.view;

public interface ICollectWorkerFragment {

    void loadSuccess(String loadJson);

    void loadFailure(String failure);

    void cancelCollectSuccess(String cancelCollectJson);

    void cancelCollectFailure(String failure);
}
