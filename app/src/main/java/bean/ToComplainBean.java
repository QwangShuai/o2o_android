package bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/31.
 */

public class ToComplainBean implements Serializable {

    private String againstId;
    private String authorId;
    private String ctId;
    private String ctType;
    private String content;

    public String getAgainstId() {
        return againstId;
    }

    public void setAgainstId(String againstId) {
        this.againstId = againstId;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getCtId() {
        return ctId;
    }

    public void setCtId(String ctId) {
        this.ctId = ctId;
    }

    public String getCtType() {
        return ctType;
    }

    public void setCtType(String ctType) {
        this.ctType = ctType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "ToComplainBean{" +
                "againstId='" + againstId + '\'' +
                ", authorId='" + authorId + '\'' +
                ", ctId='" + ctId + '\'' +
                ", ctType='" + ctType + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
