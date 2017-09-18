package bean;


import java.util.List;

public class PersonDetailBean {

    private String icon;
    private String name;
    private boolean male;
    private int age;
    private String address;
    private String household;
    private String brief;
    private boolean worker;
    private List<RoleBean> roleBeanList;
    private int count;
    private List<EvaluateBean> evaluateBeanList;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isMale() {
        return male;
    }

    public void setMale(boolean male) {
        this.male = male;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHousehold() {
        return household;
    }

    public void setHousehold(String household) {
        this.household = household;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public boolean isWorker() {
        return worker;
    }

    public void setWorker(boolean worker) {
        this.worker = worker;
    }

    public List<RoleBean> getRoleBeanList() {
        return roleBeanList;
    }

    public void setRoleBeanList(List<RoleBean> roleBeanList) {
        this.roleBeanList = roleBeanList;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<EvaluateBean> getEvaluateBeanList() {
        return evaluateBeanList;
    }

    public void setEvaluateBeanList(List<EvaluateBean> evaluateBeanList) {
        this.evaluateBeanList = evaluateBeanList;
    }
}
