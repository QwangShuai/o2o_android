package mine.listener;

import usermanage.bean.UserInfoBean;

/**
 * Created by Administrator on 2017/10/24.
 */

public interface MineListener {

    void success(UserInfoBean userInfoBean);

    void failure(String failure);
}
