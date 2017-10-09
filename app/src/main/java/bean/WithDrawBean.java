package bean;

//提现
public class WithDrawBean {

    private String uwl_id;
    private String uwl_amount;
    private String uwl_in_time;
    private String uwl_status;
    private String uwl_solut_time;
    private String uwl_card;
    private String p_id;

    public String getUwl_id() {
        return uwl_id;
    }

    public void setUwl_id(String uwl_id) {
        this.uwl_id = uwl_id;
    }

    public String getUwl_amount() {
        return uwl_amount;
    }

    public void setUwl_amount(String uwl_amount) {
        this.uwl_amount = uwl_amount;
    }

    public String getUwl_in_time() {
        return uwl_in_time;
    }

    public void setUwl_in_time(String uwl_in_time) {
        this.uwl_in_time = uwl_in_time;
    }

    public String getUwl_status() {
        return uwl_status;
    }

    public void setUwl_status(String uwl_status) {
        this.uwl_status = uwl_status;
    }

    public String getUwl_solut_time() {
        return uwl_solut_time;
    }

    public void setUwl_solut_time(String uwl_solut_time) {
        this.uwl_solut_time = uwl_solut_time;
    }

    public String getUwl_card() {
        return uwl_card;
    }

    public void setUwl_card(String uwl_card) {
        this.uwl_card = uwl_card;
    }

    public String getP_id() {
        return p_id;
    }

    public void setP_id(String p_id) {
        this.p_id = p_id;
    }

    @Override
    public String toString() {
        return "WithDrawBean{" +
                "uwl_id='" + uwl_id + '\'' +
                ", uwl_amount='" + uwl_amount + '\'' +
                ", uwl_in_time='" + uwl_in_time + '\'' +
                ", uwl_status='" + uwl_status + '\'' +
                ", uwl_solut_time='" + uwl_solut_time + '\'' +
                ", uwl_card='" + uwl_card + '\'' +
                ", p_id='" + p_id + '\'' +
                '}';
    }
}
