package com.gjzg.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/31.
 */

public class ToSelectTaskBean implements Serializable{

    private String skillId;
    private String workerId;

    public String getSkillId() {
        return skillId;
    }

    public void setSkillId(String skillId) {
        this.skillId = skillId;
    }

    public String getWorkerId() {
        return workerId;
    }

    public void setWorkerId(String workerId) {
        this.workerId = workerId;
    }
}
