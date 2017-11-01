package voucher.module;


import listener.JsonListener;

public interface IVoucherModule {

    void load(String url, JsonListener jsonListener);

    void cancelTask();
}
