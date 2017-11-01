package bean;


import java.io.Serializable;

public class WorkerBean implements Serializable {

    private String workerId;
    private String icon;
    private String title;
    private String info;
    private String status;
    private String collectId;
    private int favorite;
    private String positionX;
    private String positionY;
    private String sex;
    private String mobile;
    private String address;
    private String skill;
    private String skillName;

    public String getWorkerId() {
        return workerId;
    }

    public void setWorkerId(String workerId) {
        this.workerId = workerId;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCollectId() {
        return collectId;
    }

    public void setCollectId(String collectId) {
        this.collectId = collectId;
    }

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }

    public String getPositionX() {
        return positionX;
    }

    public void setPositionX(String positionX) {
        this.positionX = positionX;
    }

    public String getPositionY() {
        return positionY;
    }

    public void setPositionY(String positionY) {
        this.positionY = positionY;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    @Override
    public String toString() {
        return "WorkerBean{" +
                "workerId='" + workerId + '\'' +
                ", icon='" + icon + '\'' +
                ", title='" + title + '\'' +
                ", info='" + info + '\'' +
                ", status='" + status + '\'' +
                ", collectId='" + collectId + '\'' +
                ", favorite=" + favorite +
                ", positionX='" + positionX + '\'' +
                ", positionY='" + positionY + '\'' +
                ", sex='" + sex + '\'' +
                ", mobile='" + mobile + '\'' +
                ", address='" + address + '\'' +
                ", skill='" + skill + '\'' +
                ", skillName='" + skillName + '\'' +
                '}';
    }
}
