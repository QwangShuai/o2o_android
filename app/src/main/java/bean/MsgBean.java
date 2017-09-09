package bean;

/**
 * 创建日期：2017/8/22 on 15:51
 * 作者:孙明明
 * 描述:工作邀约数据类
 */

public class MsgBean {

    private String title;
    private String date;
    private String des;
    private boolean arrowShow;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public boolean isArrowShow() {
        return arrowShow;
    }

    public void setArrowShow(boolean arrowShow) {
        this.arrowShow = arrowShow;
    }
}
