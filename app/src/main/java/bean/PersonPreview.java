package bean;

import java.io.Serializable;
import java.util.List;

/**
 * 创建日期：2017/8/10 on 15:03
 * 作者:孙明明
 * 描述:信息预览
 */

public class PersonPreview implements Serializable {

    private String nameTitle;
    private String nameContent;
    private String sexTitle;
    private boolean sex;
    private String idNumberTitle;
    private String idNumberContent;
    private String addressTitle;
    private String addressContent;
    private String houseHoldTitle;
    private String houseHoldContent;
    private String briefTitle;
    private String briefContent;
    private String phoneNumberTitle;
    private String phoneNumberContent;
    private String roleTitle;
    private boolean role;
    private List<Role> roleList;

    public String getNameTitle() {
        return nameTitle;
    }

    public void setNameTitle(String nameTitle) {
        this.nameTitle = nameTitle;
    }

    public String getNameContent() {
        return nameContent;
    }

    public void setNameContent(String nameContent) {
        this.nameContent = nameContent;
    }

    public String getSexTitle() {
        return sexTitle;
    }

    public void setSexTitle(String sexTitle) {
        this.sexTitle = sexTitle;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public String getIdNumberTitle() {
        return idNumberTitle;
    }

    public void setIdNumberTitle(String idNumberTitle) {
        this.idNumberTitle = idNumberTitle;
    }

    public String getIdNumberContent() {
        return idNumberContent;
    }

    public void setIdNumberContent(String idNumberContent) {
        this.idNumberContent = idNumberContent;
    }

    public String getAddressTitle() {
        return addressTitle;
    }

    public void setAddressTitle(String addressTitle) {
        this.addressTitle = addressTitle;
    }

    public String getAddressContent() {
        return addressContent;
    }

    public void setAddressContent(String addressContent) {
        this.addressContent = addressContent;
    }

    public String getHouseHoldTitle() {
        return houseHoldTitle;
    }

    public void setHouseHoldTitle(String houseHoldTitle) {
        this.houseHoldTitle = houseHoldTitle;
    }

    public String getHouseHoldContent() {
        return houseHoldContent;
    }

    public void setHouseHoldContent(String houseHoldContent) {
        this.houseHoldContent = houseHoldContent;
    }

    public String getBriefTitle() {
        return briefTitle;
    }

    public void setBriefTitle(String briefTitle) {
        this.briefTitle = briefTitle;
    }

    public String getBriefContent() {
        return briefContent;
    }

    public void setBriefContent(String briefContent) {
        this.briefContent = briefContent;
    }

    public String getPhoneNumberTitle() {
        return phoneNumberTitle;
    }

    public void setPhoneNumberTitle(String phoneNumberTitle) {
        this.phoneNumberTitle = phoneNumberTitle;
    }

    public String getPhoneNumberContent() {
        return phoneNumberContent;
    }

    public void setPhoneNumberContent(String phoneNumberContent) {
        this.phoneNumberContent = phoneNumberContent;
    }

    public String getRoleTitle() {
        return roleTitle;
    }

    public void setRoleTitle(String roleTitle) {
        this.roleTitle = roleTitle;
    }

    public boolean isRole() {
        return role;
    }

    public void setRole(boolean role) {
        this.role = role;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    @Override
    public String toString() {
        return "PersonPreview{" +
                "nameTitle='" + nameTitle + '\'' +
                ", nameContent='" + nameContent + '\'' +
                ", sexTitle='" + sexTitle + '\'' +
                ", sex=" + sex +
                ", idNumberTitle='" + idNumberTitle + '\'' +
                ", idNumberContent='" + idNumberContent + '\'' +
                ", addressTitle='" + addressTitle + '\'' +
                ", addressContent='" + addressContent + '\'' +
                ", briefTitle='" + briefTitle + '\'' +
                ", briefContent='" + briefContent + '\'' +
                ", phoneNumberTitle='" + phoneNumberTitle + '\'' +
                ", phoneNumberContent='" + phoneNumberContent + '\'' +
                ", roleTitle='" + roleTitle + '\'' +
                ", role=" + role +
                ", roleList=" + roleList +
                '}';
    }
}
