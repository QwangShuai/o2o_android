package collectworker.bean;


import java.io.Serializable;

public class CollectWorkerBean implements Serializable {

    private String u_id;
    private String u_sex;
    private String u_online;
    private String u_start;
    private String u_worked_num;
    private String f_id;

    public String getU_id() {
        return u_id;
    }

    public void setU_id(String u_id) {
        this.u_id = u_id;
    }

    public String getU_sex() {
        return u_sex;
    }

    public void setU_sex(String u_sex) {
        this.u_sex = u_sex;
    }

    public String getU_online() {
        return u_online;
    }

    public void setU_online(String u_online) {
        this.u_online = u_online;
    }

    public String getU_start() {
        return u_start;
    }

    public void setU_start(String u_start) {
        this.u_start = u_start;
    }

    public String getU_worked_num() {
        return u_worked_num;
    }

    public void setU_worked_num(String u_worked_num) {
        this.u_worked_num = u_worked_num;
    }

    public String getF_id() {
        return f_id;
    }

    public void setF_id(String f_id) {
        this.f_id = f_id;
    }

    @Override
    public String toString() {
        return "CollectWorkerBean{" +
                "u_id='" + u_id + '\'' +
                ", u_sex='" + u_sex + '\'' +
                ", u_online='" + u_online + '\'' +
                ", u_start='" + u_start + '\'' +
                ", u_worked_num='" + u_worked_num + '\'' +
                ", f_id='" + f_id + '\'' +
                '}';
    }
}
