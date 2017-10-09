package voucher.view;


import java.util.List;

import voucher.bean.VoucherBean;

public interface IVoucherActivity {

    void showLoading();

    void hideLoading();

    void receiveData(List<VoucherBean> voucherBeanList);
}
