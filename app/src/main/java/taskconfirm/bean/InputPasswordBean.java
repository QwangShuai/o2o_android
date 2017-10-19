package taskconfirm.bean;


import java.io.Serializable;

public class InputPasswordBean implements Serializable {

    private int type;
    private int number;

    public InputPasswordBean() {
    }

    public InputPasswordBean(int type, int number) {
        this.type = type;
        this.number = number;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

}
