package bean;

import java.io.Serializable;

/**
 * 创建日期：2017/8/19 on 16:37
 * 作者:孙明明
 * 描述:工作筛选条件
 */

public class ScnJobBean implements Serializable {

    private String name;
    private String nameHint;
    private String disTitle;
    private String disContent;
    private String durationTitle;
    private String durationContent;
    private String moneyTitle;
    private String moneyContent;
    private String startTimeTitle;
    private String startTimeContent;
    private String kindTitle;
    private String kindContent;
    private String typeTitle;
    private String typeContent;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameHint() {
        return nameHint;
    }

    public void setNameHint(String nameHint) {
        this.nameHint = nameHint;
    }

    public String getDisTitle() {
        return disTitle;
    }

    public void setDisTitle(String disTitle) {
        this.disTitle = disTitle;
    }

    public String getDisContent() {
        return disContent;
    }

    public void setDisContent(String disContent) {
        this.disContent = disContent;
    }

    public String getDurationTitle() {
        return durationTitle;
    }

    public void setDurationTitle(String durationTitle) {
        this.durationTitle = durationTitle;
    }

    public String getDurationContent() {
        return durationContent;
    }

    public void setDurationContent(String durationContent) {
        this.durationContent = durationContent;
    }

    public String getMoneyTitle() {
        return moneyTitle;
    }

    public void setMoneyTitle(String moneyTitle) {
        this.moneyTitle = moneyTitle;
    }

    public String getMoneyContent() {
        return moneyContent;
    }

    public void setMoneyContent(String moneyContent) {
        this.moneyContent = moneyContent;
    }

    public String getStartTimeTitle() {
        return startTimeTitle;
    }

    public void setStartTimeTitle(String startTimeTitle) {
        this.startTimeTitle = startTimeTitle;
    }

    public String getStartTimeContent() {
        return startTimeContent;
    }

    public void setStartTimeContent(String startTimeContent) {
        this.startTimeContent = startTimeContent;
    }

    public String getKindTitle() {
        return kindTitle;
    }

    public void setKindTitle(String kindTitle) {
        this.kindTitle = kindTitle;
    }

    public String getKindContent() {
        return kindContent;
    }

    public void setKindContent(String kindContent) {
        this.kindContent = kindContent;
    }

    public String getTypeTitle() {
        return typeTitle;
    }

    public void setTypeTitle(String typeTitle) {
        this.typeTitle = typeTitle;
    }

    public String getTypeContent() {
        return typeContent;
    }

    public void setTypeContent(String typeContent) {
        this.typeContent = typeContent;
    }

    @Override
    public String toString() {
        return "ScnJobBean{" +
                "name='" + name + '\'' +
                ", nameHint='" + nameHint + '\'' +
                ", disTitle='" + disTitle + '\'' +
                ", disContent='" + disContent + '\'' +
                ", durationTitle='" + durationTitle + '\'' +
                ", durationContent='" + durationContent + '\'' +
                ", moneyTitle='" + moneyTitle + '\'' +
                ", moneyContent='" + moneyContent + '\'' +
                ", startTimeTitle='" + startTimeTitle + '\'' +
                ", startTimeContent='" + startTimeContent + '\'' +
                ", kindTitle='" + kindTitle + '\'' +
                ", kindContent='" + kindContent + '\'' +
                ", typeTitle='" + typeTitle + '\'' +
                ", typeContent='" + typeContent + '\'' +
                '}';
    }
}
