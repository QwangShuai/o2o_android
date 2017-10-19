package publishjob.bean;


import java.io.Serializable;

public class PublishKindBean implements Serializable {

    private String kind;
    private String amount;
    private String salary;
    private String startTime;
    private String endTime;

    public PublishKindBean() {
    }

    public PublishKindBean(String kind, String amount, String salary, String startTime, String endTime) {
        this.kind = kind;
        this.amount = amount;
        this.salary = salary;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "PublishKindBean{" +
                "kind='" + kind + '\'' +
                ", amount='" + amount + '\'' +
                ", salary='" + salary + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                '}';
    }
}
