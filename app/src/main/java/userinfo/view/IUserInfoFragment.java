package userinfo.view;

/**
 * Created by Administrator on 2017/11/1.
 */

public interface IUserInfoFragment {

    void infoSuccess(String json);

    void infoFailure(String failure);

    void skillSuccess(String json);

    void skillFailure(String failure);
}
