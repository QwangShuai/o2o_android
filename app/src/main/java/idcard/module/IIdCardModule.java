package idcard.module;

import idcard.listener.IdCardListener;

/**
 * Created by Administrator on 2017/10/25.
 */

public interface IIdCardModule {

    void verify(String mobile, String idcard, IdCardListener idCardListener);

    void cancelTask();
}
