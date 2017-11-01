package bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/30.
 */

public class TalkToSelect implements Serializable {

    private String workerId;
    private String skill;

    public String getWorkerId() {
        return workerId;
    }

    public void setWorkerId(String workerId) {
        this.workerId = workerId;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    @Override
    public String toString() {
        return "TalkToSelect{" +
                "workerId='" + workerId + '\'' +
                ", skill='" + skill + '\'' +
                '}';
    }
}
