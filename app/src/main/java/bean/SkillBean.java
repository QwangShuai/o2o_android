package bean;

import java.io.Serializable;

public class SkillBean implements Serializable {

    private String id;
    private String name;
    private boolean check;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    @Override
    public String toString() {
        return "SkillBean{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", check=" + check +
                '}';
    }
}
