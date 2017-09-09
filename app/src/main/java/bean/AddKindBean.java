package bean;

/**
 * 创建日期：2017/8/29 on 14:47
 * 作者:孙明明
 * 描述:添加工种数据类
 */

public class AddKindBean {

    private int id;
    private String img;
    private String content;
    private boolean checked;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
