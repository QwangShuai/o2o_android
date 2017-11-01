package redpacket.module;

import listener.JsonListener;

public interface IRedPacketModule {

    void load(String url, JsonListener jsonListener);

    void cancelTask();
}
