package redpacket.module;


import redpacket.listener.OnLoadRedPacketListener;

public interface IRedPacketModule {

    void load(OnLoadRedPacketListener onLoadRedPacketListener);

    void cancelTask();
}
