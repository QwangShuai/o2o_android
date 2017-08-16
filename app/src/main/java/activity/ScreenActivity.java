package activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gjzg.R;

import java.util.ArrayList;
import java.util.List;

import bean.Screen;
import config.CodeConfig;

public class ScreenActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private View rootView, kindDialogView;
    private RelativeLayout closeRl, searchRl;
    private ImageView leisureIv, talkIv, midIv;

    private LinearLayout disLl;

    private LinearLayout kindLl;
    private ListView kindLv;
    private TextView kindTv;

    private List<String> kindList;
    private ArrayAdapter<String> kindAdapter;

    private AlertDialog kindDialog;

    private int curState = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        rootView = View.inflate(this, R.layout.activity_screen, null);
        setContentView(rootView);
        initView();
        initData();
        setData();
        setListener();
        loadData();
    }

    private void initView() {
        initRootView();
        initDialogView();
    }

    private void initRootView() {
        closeRl = (RelativeLayout) rootView.findViewById(R.id.rl_screen_close);
        searchRl = (RelativeLayout) rootView.findViewById(R.id.rl_screen_search);
        leisureIv = (ImageView) rootView.findViewById(R.id.iv_screen_leisure);
        talkIv = (ImageView) rootView.findViewById(R.id.iv_screen_talk);
        midIv = (ImageView) rootView.findViewById(R.id.iv_screen_mid);
        kindLl = (LinearLayout) rootView.findViewById(R.id.ll_screen_kind);
        kindTv = (TextView) rootView.findViewById(R.id.tv_screen_kind);
    }

    private void initDialogView() {
        kindDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_screen, null);
        AlertDialog.Builder kindBuilder = new AlertDialog.Builder(this);
        kindBuilder.setView(kindDialogView);
        kindDialog = kindBuilder.create();
        kindLv = (ListView) kindDialogView.findViewById(R.id.lv_dialog_screen);
    }

    private void initData() {
        kindList = new ArrayList<>();
        kindAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, kindList);
    }

    private void setData() {
        kindLv.setAdapter(kindAdapter);
    }

    private void setListener() {
        closeRl.setOnClickListener(this);
        searchRl.setOnClickListener(this);
        leisureIv.setOnClickListener(this);
        talkIv.setOnClickListener(this);
        midIv.setOnClickListener(this);
        kindLl.setOnClickListener(this);
        kindLv.setOnItemClickListener(this);
    }

    private void loadData() {
        loadKindData();
    }

    private void loadKindData() {
        kindList.add("水泥工");
        kindList.add("搬运工");
        kindList.add("焊接工");
        kindList.add("水暖工");
        kindList.add("瓦工");
        kindAdapter.notifyDataSetChanged();
    }

    private void changeState(int tarState) {
        if (tarState != curState) {
            switch (tarState) {
                case 0:
                    leisureIv.setImageResource(R.mipmap.worker_leisure);
                    talkIv.setImageResource(R.mipmap.worker_talk_g);
                    midIv.setImageResource(R.mipmap.worker_mid_g);
                    break;
                case 1:
                    leisureIv.setImageResource(R.mipmap.worker_leisure_g);
                    talkIv.setImageResource(R.mipmap.worker_talk);
                    midIv.setImageResource(R.mipmap.worker_mid_g);
                    break;
                case 2:
                    leisureIv.setImageResource(R.mipmap.worker_leisure_g);
                    talkIv.setImageResource(R.mipmap.worker_talk_g);
                    midIv.setImageResource(R.mipmap.worker_mid);
                    break;
            }
            curState = tarState;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_screen_close:
                finish();
                break;
            case R.id.rl_screen_search:
                Intent intent = new Intent();
                Screen screen = new Screen();
                screen.setState(curState);
                intent.putExtra("screen", screen);
                setResult(CodeConfig.screenResultCode, intent);
                finish();
                break;
            case R.id.iv_screen_leisure:
                changeState(0);
                break;
            case R.id.iv_screen_talk:
                changeState(1);
                break;
            case R.id.iv_screen_mid:
                changeState(2);
                break;
            case R.id.ll_screen_kind:
                kindDialog.show();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.lv_dialog_screen:
                kindDialog.dismiss();
                kindTv.setText(kindList.get(position));
                break;
        }
    }
}
