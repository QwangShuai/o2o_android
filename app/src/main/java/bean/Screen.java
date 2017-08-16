package bean;

import java.io.Serializable;

/**
 * 创建日期：2017/8/16 on 14:24
 * 作者:孙明明
 * 描述:筛选数据类
 */

public class Screen implements Serializable {

    private int state;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
