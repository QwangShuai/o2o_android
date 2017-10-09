package redpacket.view;


import java.util.List;

import redpacket.bean.RedPacketBean;

public interface IRedPacketActivity {

    void showLoading();

    void hideLoading();

    void receiveData(List<RedPacketBean> redPacketBeanList);
}
