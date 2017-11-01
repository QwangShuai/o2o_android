package city.listener;


import java.util.List;

import city.bean.CityBean;

public interface CityListener {

    void loadSuccess(List<CityBean> cityBeanList);

    void loadFailure(String failure);
}
