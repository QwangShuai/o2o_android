package persondetail.view;

/**
 * Created by Administrator on 2017/10/29.
 */

public interface IPersonDetailActivity {

    void infoSuccess(String json);

    void infoFailure(String failure);

    void skillSuccess(String json);

    void skillFailure(String failure);

    void evaluateSuccess(String json);

    void evaluateFailure(String failure);
}
