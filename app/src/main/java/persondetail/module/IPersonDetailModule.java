package persondetail.module;

import com.gjzg.listener.JsonListener;

/**
 * Created by Administrator on 2017/10/29.
 */

public interface IPersonDetailModule {

    void info(String url, JsonListener jsonListener);

    void getSkill(String url,JsonListener jsonListener);

    void evaluate(String url,JsonListener jsonListener);

    void cancelTask();
}
