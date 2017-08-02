package activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;

import com.gjzg.R;

import utils.Utils;

public class MessageActivity extends AppCompatActivity implements View.OnClickListener {

    private View rootView;
    private RelativeLayout returnRl;
    private RelativeLayout jobInviteRl, systemMsgRl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        rootView = View.inflate(this, R.layout.activity_message, null);
        setContentView(rootView);
        initView();
        setListener();
    }

    private void initView() {
        initRootView();
    }

    private void initRootView() {
        returnRl = (RelativeLayout) rootView.findViewById(R.id.rl_message_return);
        jobInviteRl = (RelativeLayout) rootView.findViewById(R.id.rl_message_job_invite);
        systemMsgRl = (RelativeLayout) rootView.findViewById(R.id.rl_message_system_msg);
    }

    private void setListener() {
        returnRl.setOnClickListener(this);
        jobInviteRl.setOnClickListener(this);
        systemMsgRl.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_message_return:
                finish();
                break;
            case R.id.rl_message_job_invite:
                Utils.toast(this, "工作邀约");
                break;
            case R.id.rl_message_system_msg:
                Utils.toast(this, "系统消息");
                break;
        }
    }
}
