package phoneprove.listener;

/**
 * Created by Administrator on 2017/10/25.
 */

public interface ProveMobileCodeListener {

    void success(String success);

    void failure(String failure);
}
