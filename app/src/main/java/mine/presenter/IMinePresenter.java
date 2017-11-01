package mine.presenter;

/**
 * Created by Administrator on 2017/10/24.
 */

public interface IMinePresenter {

    void load(String id);

    void postOnline(String id, String online);

    void destroy();
}
