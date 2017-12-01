package com.gjzg.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gjzg.R;
import com.gjzg.bean.AppConfigBean;
import com.gjzg.singleton.SingleGson;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.gjzg.config.NetConfig;
import com.gjzg.config.VarConfig;
import com.gjzg.utils.Utils;

public class AboutUsActivity extends AppCompatActivity {


    @BindView(R.id.rl_about_us_return)
    RelativeLayout rlAboutUsReturn;
    @BindView(R.id.tv_about_us_official_website)
    TextView tvAboutUsOfficialWebsite;
    @BindView(R.id.ll_about_us_official_website)
    LinearLayout llAboutUsOfficialWebsite;
    @BindView(R.id.tv_about_us_service_telephone)
    TextView tvAboutUsServiceTelephone;
    @BindView(R.id.ll_about_us_service_telephone)
    LinearLayout llAboutUsServiceTelephone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(this);
    }

    @OnClick({R.id.rl_about_us_return, R.id.ll_about_us_official_website, R.id.ll_about_us_service_telephone})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_about_us_return:
                finish();
                break;
            case R.id.ll_about_us_official_website:
                if (!TextUtils.isEmpty(tvAboutUsOfficialWebsite.getText().toString())) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW).setData(Uri.parse(tvAboutUsOfficialWebsite.getText().toString()));
                    intent.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
                    if (intent.resolveActivity(getPackageManager()) != null)
                        startActivity(intent);
                }
                break;
            case R.id.ll_about_us_service_telephone:
                if (!TextUtils.isEmpty(tvAboutUsServiceTelephone.getText().toString())) {
                    Intent in = new Intent(Intent.ACTION_DIAL);
                    in.setData(Uri.parse("tel:" + tvAboutUsServiceTelephone.getText().toString()));
                    if (in.resolveActivity(getPackageManager()) != null)
                        startActivity(in);
                }
                break;
        }
    }

    private void loadData() {
        OkHttpUtils.get().tag(this).url(NetConfig.appConfigUrl).build().execute(new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {
                Utils.toast(AboutUsActivity.this, VarConfig.noNetTip);
            }

            @Override
            public void onResponse(String response) {
                if (!TextUtils.isEmpty(response)) {
                    AppConfigBean appConfigBean = SingleGson.getInstance().fromJson(response, AppConfigBean.class);
                    if (appConfigBean != null) {
                        if (appConfigBean.getCode() == 1) {
                            if (appConfigBean.getData() != null) {
                                if (appConfigBean.getData().getData() != null) {
                                    if (!TextUtils.isEmpty(appConfigBean.getData().getData().getOfficial_website()))
                                        tvAboutUsOfficialWebsite.setText(appConfigBean.getData().getData().getOfficial_website());
                                    if (!TextUtils.isEmpty(appConfigBean.getData().getData().getService_telephone()))
                                        tvAboutUsServiceTelephone.setText(appConfigBean.getData().getData().getService_telephone());
                                }
                            }
                        }
                    }
                }
            }
        });
    }
}
