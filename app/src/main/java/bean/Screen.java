package bean;

import java.io.Serializable;

/**
 * 创建日期：2017/8/16 on 14:24
 * 作者:孙明明
 * 描述:筛选数据类
 */

public class Screen implements Serializable {

    private String name;
    private int state;
    private String dis;
    private String kind;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getDis() {
        return dis;
    }

    public void setDis(String dis) {
        this.dis = dis;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    @Override
    public String toString() {
        return "Screen{" +
                "name='" + name + '\'' +
                ", state=" + state +
                ", dis='" + dis + '\'' +
                ", kind='" + kind + '\'' +
                '}';
    }
}
