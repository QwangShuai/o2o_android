package selectaddress.listener;


import java.util.List;

import selectaddress.bean.SelectAddressBean;

public interface OnLoadSelAddListener {

    void success(List<SelectAddressBean> selectAddressBeanList);

    void failure(String failure);
}
