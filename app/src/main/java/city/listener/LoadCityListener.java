package city.listener;


import java.util.List;

import city.bean.CityBean;

public interface LoadCityListener {

    void loadSuccess(List<CityBean> cityBeanList);

    void loadFailure(String failure);
}
