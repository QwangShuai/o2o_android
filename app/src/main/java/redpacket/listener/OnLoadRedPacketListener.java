package redpacket.listener;


import java.util.List;

import redpacket.bean.RedPacketBean;

public interface OnLoadRedPacketListener {

    void success(List<RedPacketBean> redPacketBeanList);
}
