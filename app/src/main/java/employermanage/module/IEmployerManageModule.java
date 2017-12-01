package employermanage.module;

import com.gjzg.listener.JsonListener;

/**
 * Created by Administrator on 2017/10/23.
 */

public interface IEmployerManageModule {

    void load(String url, JsonListener jsonListener);

    void cancel(String url,JsonListener jsonListener);

    void cancelTask();
}
