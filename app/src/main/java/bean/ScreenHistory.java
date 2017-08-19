package bean;

import java.io.Serializable;
import java.util.List;

/**
 * 创建日期：2017/8/17 on 13:37
 * 作者:孙明明
 * 描述:筛选历史集合数据类
 */

public class ScreenHistory implements Serializable {

    private List<Screen> screenList;

    public List<Screen> getScreenList() {
        return screenList;
    }

    public void setScreenList(List<Screen> screenList) {
        this.screenList = screenList;
    }

}
