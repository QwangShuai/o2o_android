package voucher.listener;


import java.util.List;

import voucher.bean.VoucherBean;

public interface OnLoadVoucherListener {

    void success(List<VoucherBean> voucherBeanList);
}
