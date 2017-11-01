package serviceclause.view;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.gjzg.R;

import config.NetConfig;

public class SevClsActivity extends AppCompatActivity implements View.OnClickListener {

    private View rootView;
    private RelativeLayout returnRl;
    private WebView sevClsWv;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        rootView = LayoutInflater.from(this).inflate(R.layout.activity_sev_cls,null);
        setContentView(rootView);
        initView();
        setListener();
        loadData();
    }

    private void initView() {
        initRootView();
    }

    private void initRootView() {
        returnRl = (RelativeLayout) rootView.findViewById(R.id.rl_sev_cls_return);
        sevClsWv = (WebView) rootView.findViewById(R.id.wv_sev_cls);
        progressBar = (ProgressBar) rootView.findViewById(R.id.pb_sev_cls);
        WebSettings settings = sevClsWv.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        sevClsWv.getSettings().setBlockNetworkImage(true);
        sevClsWv.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.INVISIBLE);
                sevClsWv.getSettings().setBlockNetworkImage(false);
                if (!sevClsWv.getSettings().getLoadsImagesAutomatically()) {
                    sevClsWv.getSettings().setLoadsImagesAutomatically(true);
                }
            }
        });
        sevClsWv.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                progressBar.setProgress(newProgress);
            }
        });
    }

    private void setListener() {
        returnRl.setOnClickListener(this);
    }

    private void loadData() {
//        sevClsWv.loadUrl(NetConfig.sevClsUrl);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_sev_cls_return:
                finish();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && sevClsWv.canGoBack()) {
            sevClsWv.goBack();
            return true;
        } else {
            finish();
            return false;
        }
    }

}
