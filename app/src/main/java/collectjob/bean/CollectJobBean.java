package collectjob.bean;


import java.io.Serializable;

public class CollectJobBean implements Serializable{

    private String title;
    private String amount;
    private String duration;
    private String author;
    private String status;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "CollectJobBean{" +
                "title='" + title + '\'' +
                ", amount='" + amount + '\'' +
                ", duration='" + duration + '\'' +
                ", author='" + author + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
