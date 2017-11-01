package idcard.presenter;

/**
 * Created by Administrator on 2017/10/25.
 */

public interface IIdCardPresenter {

    void verify(String mobile,String idcard);

    void destroy();
}
