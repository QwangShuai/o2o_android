package wallet.view;

/**
 * Created by Administrator on 2017/10/30.
 */

public interface IWalletActivity {

    void loadSuccess(String json);

    void loadFailure(String failure);
}
