package jobinfo.bean;

import java.io.Serializable;

public class JobInfoBean implements Serializable {

    private String t_id;
    private String t_title;
    private String t_info;
    private String t_amount;
    private String t_duration;
    private String t_edit_amount;
    private String t_amount_edit_times;
    private String t_posit_x;
    private String t_posit_y;
    private String t_author;
    private String t_in_time;
    private String t_last_edit_time;
    private String t_last_editor;
    private String t_status;
    private String t_phone;
    private String t_phone_status;

    public String getT_id() {
        return t_id;
    }

    public void setT_id(String t_id) {
        this.t_id = t_id;
    }

    public String getT_title() {
        return t_title;
    }

    public void setT_title(String t_title) {
        this.t_title = t_title;
    }

    public String getT_info() {
        return t_info;
    }

    public void setT_info(String t_info) {
        this.t_info = t_info;
    }

    public String getT_amount() {
        return t_amount;
    }

    public void setT_amount(String t_amount) {
        this.t_amount = t_amount;
    }

    public String getT_duration() {
        return t_duration;
    }

    public void setT_duration(String t_duration) {
        this.t_duration = t_duration;
    }

    public String getT_edit_amount() {
        return t_edit_amount;
    }

    public void setT_edit_amount(String t_edit_amount) {
        this.t_edit_amount = t_edit_amount;
    }

    public String getT_amount_edit_times() {
        return t_amount_edit_times;
    }

    public void setT_amount_edit_times(String t_amount_edit_times) {
        this.t_amount_edit_times = t_amount_edit_times;
    }

    public String getT_posit_x() {
        return t_posit_x;
    }

    public void setT_posit_x(String t_posit_x) {
        this.t_posit_x = t_posit_x;
    }

    public String getT_posit_y() {
        return t_posit_y;
    }

    public void setT_posit_y(String t_posit_y) {
        this.t_posit_y = t_posit_y;
    }

    public String getT_author() {
        return t_author;
    }

    public void setT_author(String t_author) {
        this.t_author = t_author;
    }

    public String getT_in_time() {
        return t_in_time;
    }

    public void setT_in_time(String t_in_time) {
        this.t_in_time = t_in_time;
    }

    public String getT_last_edit_time() {
        return t_last_edit_time;
    }

    public void setT_last_edit_time(String t_last_edit_time) {
        this.t_last_edit_time = t_last_edit_time;
    }

    public String getT_last_editor() {
        return t_last_editor;
    }

    public void setT_last_editor(String t_last_editor) {
        this.t_last_editor = t_last_editor;
    }

    public String getT_status() {
        return t_status;
    }

    public void setT_status(String t_status) {
        this.t_status = t_status;
    }

    public String getT_phone() {
        return t_phone;
    }

    public void setT_phone(String t_phone) {
        this.t_phone = t_phone;
    }

    public String getT_phone_status() {
        return t_phone_status;
    }

    public void setT_phone_status(String t_phone_status) {
        this.t_phone_status = t_phone_status;
    }

    @Override
    public String toString() {
        return "JobInfoBean{" +
                "t_id='" + t_id + '\'' +
                ", t_title='" + t_title + '\'' +
                ", t_info='" + t_info + '\'' +
                ", t_amount='" + t_amount + '\'' +
                ", t_duration='" + t_duration + '\'' +
                ", t_edit_amount='" + t_edit_amount + '\'' +
                ", t_amount_edit_times='" + t_amount_edit_times + '\'' +
                ", t_posit_x='" + t_posit_x + '\'' +
                ", t_posit_y='" + t_posit_y + '\'' +
                ", t_author='" + t_author + '\'' +
                ", t_in_time='" + t_in_time + '\'' +
                ", t_last_edit_time='" + t_last_edit_time + '\'' +
                ", t_last_editor='" + t_last_editor + '\'' +
                ", t_status='" + t_status + '\'' +
                ", t_phone='" + t_phone + '\'' +
                ", t_phone_status='" + t_phone_status + '\'' +
                '}';
    }
}
