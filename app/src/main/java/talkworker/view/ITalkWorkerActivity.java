package talkworker.view;

/**
 * Created by Administrator on 2017/10/29.
 */

public interface ITalkWorkerActivity {

    void loadSuccess(String json);

    void loadFailure(String failure);

    void checkSuccess(String json);

    void checkFailure(String failure);

}
