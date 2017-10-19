package publishjob.bean;


import java.io.Serializable;
import java.util.List;

public class PublishJobBean implements Serializable{

    private String title;
    private String description;
    private String type;
    private String area;
    private String address;
    private List<PublishKindBean> publishKindBeanList;

    public PublishJobBean() {
    }

    public PublishJobBean(String title, String description, String type, String area, String address, List<PublishKindBean> publishKindBeanList) {
        this.title = title;
        this.description = description;
        this.type = type;
        this.area = area;
        this.address = address;
        this.publishKindBeanList = publishKindBeanList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<PublishKindBean> getPublishKindBeanList() {
        return publishKindBeanList;
    }

    public void setPublishKindBeanList(List<PublishKindBean> publishKindBeanList) {
        this.publishKindBeanList = publishKindBeanList;
    }

    @Override
    public String toString() {
        return "PublishJobBean{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", area='" + area + '\'' +
                ", address='" + address + '\'' +
                ", publishKindBeanList=" + publishKindBeanList +
                '}';
    }
}
