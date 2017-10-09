package voucher.module;


import voucher.listener.OnLoadVoucherListener;

public interface IVoucherModule {

    void load(OnLoadVoucherListener onLoadVoucherListener);

    void cancelTask();
}
