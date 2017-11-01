package wallet.presenter;

import android.os.Handler;

import listener.JsonListener;
import wallet.module.IWalletModule;
import wallet.module.WalletModule;
import wallet.view.IWalletActivity;

/**
 * Created by Administrator on 2017/10/30.
 */

public class WalletPresenter implements IWalletPresenter {

    private IWalletActivity walletActivity;
    private IWalletModule walletModule;
    private Handler handler;

    public WalletPresenter(IWalletActivity walletActivity) {
        this.walletActivity = walletActivity;
        walletModule = new WalletModule();
        handler = new Handler();
    }

    @Override
    public void load(String url) {
        walletModule.load(url, new JsonListener() {
            @Override
            public void success(final String json) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        walletActivity.loadSuccess(json);
                    }
                });
            }

            @Override
            public void failure(final String failure) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        walletActivity.loadFailure(failure);
                    }
                });
            }
        });
    }

    @Override
    public void destroy() {
        if (walletModule != null) {
            walletModule.cancelTask();
            walletModule = null;
        }
        if (handler != null) {
            handler = null;
        }
        if (walletActivity != null) {
            walletActivity = null;
        }
    }
}
