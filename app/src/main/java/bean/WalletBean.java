package bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/30.
 */

public class WalletBean implements Serializable {

    private String overage;

    public String getOverage() {
        return overage;
    }

    public void setOverage(String overage) {
        this.overage = overage;
    }
}
