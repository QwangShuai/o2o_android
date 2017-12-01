package userinfo.module;

import com.gjzg.listener.JsonListener;

/**
 * Created by Administrator on 2017/11/1.
 */

public interface IUserInfoModule {

    void info(String url, JsonListener jsonListener);

    void skill(String url,JsonListener jsonListener);

    void cancelTask();
}
