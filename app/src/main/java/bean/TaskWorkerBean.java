package bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/30.
 */

public class TaskWorkerBean implements Serializable {

    private String tewId;
    private String tewSkill;

    public String getTewId() {
        return tewId;
    }

    public void setTewId(String tewId) {
        this.tewId = tewId;
    }

    public String getTewSkill() {
        return tewSkill;
    }

    public void setTewSkill(String tewSkill) {
        this.tewSkill = tewSkill;
    }

    @Override
    public String toString() {
        return "TaskWorkerBean{" +
                "tewId='" + tewId + '\'' +
                ", tewSkill='" + tewSkill + '\'' +
                '}';
    }
}
