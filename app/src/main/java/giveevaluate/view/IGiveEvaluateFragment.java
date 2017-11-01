package giveevaluate.view;

/**
 * Created by Administrator on 2017/10/29.
 */

public interface IGiveEvaluateFragment {

    void loadSuccess(String json);

    void loadFailure(String failure);
}
