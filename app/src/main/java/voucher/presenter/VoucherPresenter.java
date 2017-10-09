package voucher.presenter;


import android.os.Handler;

import java.util.List;

import voucher.bean.VoucherBean;
import voucher.listener.OnLoadVoucherListener;
import voucher.module.IVoucherModule;
import voucher.module.VoucherModule;
import voucher.view.IVoucherActivity;

public class VoucherPresenter implements IVoucherPresenter {

    private IVoucherActivity iVoucherActivity;
    private IVoucherModule iVoucherModule;
    private Handler mHandler = new Handler();

    public VoucherPresenter(IVoucherActivity iVoucherActivity) {
        this.iVoucherActivity = iVoucherActivity;
        iVoucherModule = new VoucherModule();
    }

    @Override
    public void load() {
        iVoucherActivity.showLoading();
        iVoucherModule.load(new OnLoadVoucherListener() {
            @Override
            public void success(final List<VoucherBean> voucherBeanList) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iVoucherActivity.hideLoading();
                        iVoucherActivity.receiveData(voucherBeanList);
                    }
                });
            }
        });
    }

    @Override
    public void destroy() {
        if (iVoucherModule != null) {
            iVoucherModule.cancelTask();
            iVoucherModule = null;
        }
        if (iVoucherActivity != null) {
            iVoucherActivity = null;
        }
        if (mHandler != null) {
            mHandler = null;
        }
    }
}
