package city.view;


import java.util.List;

import city.bean.CityBean;

public interface ICityActivity {

    void showSuccess(List<CityBean> cityBeanList);

    void showFailure(String failure);

}
