package bean;

import java.io.Serializable;

/**
 * 创建日期：2017/7/31 on 13:46
 * 作者:孙明明
 * 描述:
 */

public class Job implements Serializable{

    private String image;
    private String name;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
