package collectworker.bean;


import java.io.Serializable;

public class CollectWorkerBean implements Serializable {

    private String uId;
    private String uImg;
    private String uName;
    private String uTaskStatus;
    private String uSex;
    private String ueiInfo;
    private String ucpPositX;
    private String ucpPositY;
    private String fId;

    public CollectWorkerBean() {
    }

    public CollectWorkerBean(String uId, String uImg, String uName, String uTaskStatus, String uSex, String ueiInfo, String ucpPositX, String ucpPositY, String fId) {
        this.uId = uId;
        this.uImg = uImg;
        this.uName = uName;
        this.uTaskStatus = uTaskStatus;
        this.uSex = uSex;
        this.ueiInfo = ueiInfo;
        this.ucpPositX = ucpPositX;
        this.ucpPositY = ucpPositY;
        this.fId = fId;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getuImg() {
        return uImg;
    }

    public void setuImg(String uImg) {
        this.uImg = uImg;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getuTaskStatus() {
        return uTaskStatus;
    }

    public void setuTaskStatus(String uTaskStatus) {
        this.uTaskStatus = uTaskStatus;
    }

    public String getuSex() {
        return uSex;
    }

    public void setuSex(String uSex) {
        this.uSex = uSex;
    }

    public String getUeiInfo() {
        return ueiInfo;
    }

    public void setUeiInfo(String ueiInfo) {
        this.ueiInfo = ueiInfo;
    }

    public String getUcpPositX() {
        return ucpPositX;
    }

    public void setUcpPositX(String ucpPositX) {
        this.ucpPositX = ucpPositX;
    }

    public String getUcpPositY() {
        return ucpPositY;
    }

    public void setUcpPositY(String ucpPositY) {
        this.ucpPositY = ucpPositY;
    }

    public String getfId() {
        return fId;
    }

    public void setfId(String fId) {
        this.fId = fId;
    }

    @Override
    public String toString() {
        return "CollectWorkerBean{" +
                "uId='" + uId + '\'' +
                ", uImg='" + uImg + '\'' +
                ", uName='" + uName + '\'' +
                ", uTaskStatus='" + uTaskStatus + '\'' +
                ", uSex='" + uSex + '\'' +
                ", ueiInfo='" + ueiInfo + '\'' +
                ", ucpPositX='" + ucpPositX + '\'' +
                ", ucpPositY='" + ucpPositY + '\'' +
                ", fId='" + fId + '\'' +
                '}';
    }
}
