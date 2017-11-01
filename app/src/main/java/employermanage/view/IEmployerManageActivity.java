package employermanage.view;

/**
 * Created by Administrator on 2017/10/23.
 */

public interface IEmployerManageActivity {

    void loadSuccess(String json);

    void loadFailure(String failure);

    void cancelSuccess(String json);

    void cancelFailure(String failure);
}
