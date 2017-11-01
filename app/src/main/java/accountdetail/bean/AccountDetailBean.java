package accountdetail.bean;

import java.io.Serializable;

public class AccountDetailBean implements Serializable{

    private String title;
    private String balance;
    private String time;
    private String des;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    @Override
    public String toString() {
        return "AccountDetailBean{" +
                "title='" + title + '\'' +
                ", balance='" + balance + '\'' +
                ", time='" + time + '\'' +
                ", des='" + des + '\'' +
                '}';
    }
}
