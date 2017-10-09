package redpacket.presenter;


import android.os.Handler;

import java.util.List;

import redpacket.bean.RedPacketBean;
import redpacket.listener.OnLoadRedPacketListener;
import redpacket.module.IRedPacketModule;
import redpacket.module.RedPacketModule;
import redpacket.view.IRedPacketActivity;

public class RedPacketPresenter implements IRedPacketPresenter {

    private IRedPacketActivity iRedPacketActivity;
    private IRedPacketModule iRedPacketModule;
    private Handler mHandler = new Handler();

    public RedPacketPresenter(IRedPacketActivity iRedPacketActivity) {
        this.iRedPacketActivity = iRedPacketActivity;
        iRedPacketModule = new RedPacketModule();
    }

    @Override
    public void load() {
        iRedPacketActivity.showLoading();
        iRedPacketModule.load(new OnLoadRedPacketListener() {
            @Override
            public void success(final List<RedPacketBean> redPacketBeanList) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iRedPacketActivity.hideLoading();
                        iRedPacketActivity.receiveData(redPacketBeanList);
                    }
                });
            }
        });
    }

    @Override
    public void destroy() {
        if (iRedPacketModule != null) {
            iRedPacketModule.cancelTask();
            iRedPacketModule = null;
        }
        if (iRedPacketActivity != null) {
            iRedPacketActivity = null;
        }
        if (mHandler != null) {
            mHandler = null;
        }
    }
}
