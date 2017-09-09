package bean;

import java.io.Serializable;
import java.util.List;

/**
 * 创建日期：2017/8/17 on 13:37
 * 作者:孙明明
 * 描述:筛选历史集合数据类
 */

public class ScreenHistoryBean implements Serializable {

    private List<ScreenBean> screenBeanList;

    public List<ScreenBean> getScreenBeanList() {
        return screenBeanList;
    }

    public void setScreenBeanList(List<ScreenBean> screenBeanList) {
        this.screenBeanList = screenBeanList;
    }

}
