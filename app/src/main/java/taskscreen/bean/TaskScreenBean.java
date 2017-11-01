package taskscreen.bean;


import java.io.Serializable;

public class TaskScreenBean implements Serializable {

    private String t_title;

    public String getT_title() {
        return t_title;
    }

    public void setT_title(String t_title) {
        this.t_title = t_title;
    }

    @Override
    public String toString() {
        return "TaskScreenBean{" +
                "t_title='" + t_title + '\'' +
                '}';
    }
}
