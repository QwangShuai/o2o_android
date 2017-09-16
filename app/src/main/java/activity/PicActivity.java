package activity;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;

import com.gjzg.R;

import java.util.ArrayList;
import java.util.List;

import adapter.ComplainPicAdapter;
import bean.Pic;
import config.PathConfig;

public class PicActivity extends CommonActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private View rootView;
    private RelativeLayout returnRl;
    private GridView gv;

    private List<Pic> list;
    private ComplainPicAdapter adapter;

    @Override
    protected View getRootView() {
        return rootView = LayoutInflater.from(this).inflate(R.layout.activity_pic, null);
    }

    @Override
    protected void initView() {
        initRootView();
    }

    private void initRootView() {
        returnRl = (RelativeLayout) rootView.findViewById(R.id.rl_pic_return);
        gv = (GridView) rootView.findViewById(R.id.gv_pic);
    }

    @Override
    protected void initData() {
        list = new ArrayList<>();
        adapter = new ComplainPicAdapter(this, list);
    }

    @Override
    protected void setData() {
        gv.setAdapter(adapter);
    }

    @Override
    protected void setListener() {
        returnRl.setOnClickListener(this);
        gv.setOnItemClickListener(this);
    }

    @Override
    protected void loadData() {
        for (int i = 0; i < 10; i++) {
            Pic p = new Pic();
            p.setChoose(false);
            p.setPath(PathConfig.cameraPath + "IMG_20170911_105345.jpg");
            list.add(p);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_pic_return:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        boolean b = list.get(position).isChoose();
        list.get(position).setChoose(!b);
        adapter.notifyDataSetChanged();
    }
}
