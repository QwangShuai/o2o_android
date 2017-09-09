package bean;

import java.io.Serializable;
import java.util.List;

/**
 * 创建日期：2017/8/30 on 13:29
 * 作者:孙明明
 * 描述:发布工作数据类
 */

public class SendJob implements Serializable{

    private String name;
    private String des;
    private String kind;
    private String area;
    private String adddress;
    private List<JobKind> jobKindList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAdddress() {
        return adddress;
    }

    public void setAdddress(String adddress) {
        this.adddress = adddress;
    }

    public List<JobKind> getJobKindList() {
        return jobKindList;
    }

    public void setJobKindList(List<JobKind> jobKindList) {
        this.jobKindList = jobKindList;
    }

    @Override
    public String toString() {
        return "SendJob{" +
                "name='" + name + '\'' +
                ", des='" + des + '\'' +
                ", kind='" + kind + '\'' +
                ", area='" + area + '\'' +
                ", adddress='" + adddress + '\'' +
                ", jobKindList=" + jobKindList +
                '}';
    }
}
