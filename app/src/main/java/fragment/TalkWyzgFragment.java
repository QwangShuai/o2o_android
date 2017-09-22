package fragment;


import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gjzg.R;

import activity.ProjectActivity;
import activity.TalkActivity;
import config.VarConfig;

public class TalkWyzgFragment extends CommonFragment implements View.OnClickListener {

    private View rootView;
    private TextView tv;

    private int state = VarConfig.WYZG;
    private Handler talkHandler;

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg != null) {
                state = msg.what;
                notifyUi();
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
        talkHandler = ((TalkActivity) getActivity()).handler;
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
                switch (state) {
                    case VarConfig.WYZG:
                        startActivityForResult(new Intent(getActivity(), ProjectActivity.class), 1);
                        break;
                    case VarConfig.YYQQ:
                        talkHandler.sendEmptyMessage(VarConfig.QRKG);
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
    }

    private void notifyUi() {
        switch (state) {
            case VarConfig.WYZG:
                tv.setText(VarConfig.wyzgTip);
                break;
            case VarConfig.YYQQ:
                tv.setText(VarConfig.yyqqTip);
                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 1) {
            Log.e("TAG", "data");
            talkHandler.sendEmptyMessage(VarConfig.YYQQ);
        }
    }
}
