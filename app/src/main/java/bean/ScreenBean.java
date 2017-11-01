package bean;

import java.io.Serializable;

public class ScreenBean implements Serializable {

    private String name;
    private int state;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "ScreenBean{" +
                "name='" + name + '\'' +
                ", state=" + state +
                '}';
    }
}
