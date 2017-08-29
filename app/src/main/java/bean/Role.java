package bean;

import java.io.Serializable;

/**
 * 创建日期：2017/8/11 on 9:42
 * 作者:孙明明
 * 描述:角色
 */

public class Role implements Serializable{

    private String id;
    private String content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id='" + id + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
