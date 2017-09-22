package fragment;


import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gjzg.R;

import activity.TalkActivity;
import config.VarConfig;

public class TalkQrkgFragment extends CommonFragment implements View.OnClickListener {

    private View rootView;
    private TextView hintTv1, hintTv2, tipTv1, tipTv2;

    private int state = VarConfig.QRKG;

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
        return rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_talk_qrkg, null);
    }

    @Override
    protected void initView() {
        initRootView();
    }

    private void initRootView() {
        rootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        hintTv1 = (TextView) rootView.findViewById(R.id.tv_talk_qrkg_hint_1);
        hintTv2 = (TextView) rootView.findViewById(R.id.tv_talk_qrkg_hint_2);
        tipTv1 = (TextView) rootView.findViewById(R.id.tv_talk_qrkg_tip_1);
        tipTv2 = (TextView) rootView.findViewById(R.id.tv_talk_qrkg_tip_2);
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
        tipTv1.setOnClickListener(this);
        tipTv2.setOnClickListener(this);
    }

    @Override
    protected void loadData() {

    }

    private void notifyUi() {
        switch (state) {
            case VarConfig.QRKG:
                hintTv1.setText(VarConfig.qxgrHint);
                hintTv2.setText(VarConfig.qrkgHint);
                tipTv1.setText(VarConfig.qxgrTip);
                tipTv2.setText(VarConfig.qrkgTip);
                break;
            case VarConfig.DDGR:
                tipTv2.setText(VarConfig.ddgrTip);
                break;
            case VarConfig.GCJS:
                hintTv1.setText(VarConfig.jggrHint);
                hintTv2.setText(VarConfig.gcjsHint);
                tipTv1.setText(VarConfig.jggrTip);
                tipTv2.setText(VarConfig.gcjsTip);
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_talk_qrkg_tip_1:
                switch (state) {
                    case VarConfig.QRKG:
                        talkHandler.sendEmptyMessage(VarConfig.QXGR);
                        break;
                    case VarConfig.DDGR:
                        talkHandler.sendEmptyMessage(VarConfig.QXGR);
                        break;
                    case VarConfig.GCJS:
                        talkHandler.sendEmptyMessage(VarConfig.jggr);
                        break;
                    default:
                        break;
                }
                break;
            case R.id.tv_talk_qrkg_tip_2:
                switch (state) {
                    case VarConfig.QRKG:
                        talkHandler.sendEmptyMessage(VarConfig.qrkg);
                        break;
                    case VarConfig.DDGR:
                        talkHandler.sendEmptyMessage(VarConfig.GCJS);
                        break;
                    case VarConfig.GCJS:
                        talkHandler.sendEmptyMessage(VarConfig.qrwg);
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
    }
}
