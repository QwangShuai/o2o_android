package city.presenter;

import city.bean.CityBigBean;

public interface ICityPresenter {

    void load(String[] letter, CityBigBean cityBigBean);

    void destroy();
}
