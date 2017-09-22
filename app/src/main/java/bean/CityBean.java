package bean;

import java.io.Serializable;

//城市
public class CityBean implements Serializable{

    private String id;
    private String name;

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

    @Override
    public String toString() {
        return "CityBean{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
