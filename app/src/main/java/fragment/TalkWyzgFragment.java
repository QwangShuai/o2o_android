package fragment;


import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gjzg.R;

import activity.ProjectActivity;
import config.VarConfig;

public class TalkWyzgFragment extends CommonFragment implements View.OnClickListener {

    private View rootView;
    private TextView tv;

    public int state = VarConfig.WYZG;

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg != null) {

            }
        }
    };

    @Override
    protected View getRootView() {
        return rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_talk_wyzg, null);
    }

    @Override
    protected void initView() {
        rootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        tv = (TextView) rootView.findViewById(R.id.tv_talk_wyzg);
    }

    @Override
    protected void initData() {
        handler.sendEmptyMessage(0);
    }

    @Override
    protected void setData() {

    }

    @Override
    protected void setListener() {
        tv.setOnClickListener(this);
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_talk_wyzg:
                startActivity(new Intent(getActivity(), ProjectActivity.class));
                break;
            default:
                break;
        }
    }

    private void changeState() {
        switch (state) {
            case VarConfig.WYZG:
                tv.setText("");
                break;
            case VarConfig.YYQQ:
                break;
            default:
                break;
        }
    }
}
