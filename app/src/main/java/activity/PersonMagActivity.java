package activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gjzg.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import bean.PersonPreviewBean;
import config.PermissionConfig;
import fragment.DeliveryRecordFragment;
import fragment.EditInfoFragment;
import fragment.PicaViewFragment;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import utils.Utils;

public class PersonMagActivity extends CommonActivity implements View.OnClickListener {

    private View rootView, editDialogView;
    private RelativeLayout returnRl, previewRl, editRl, recordRl;
    private TextView previewTv, editTv, recordTv, editDialogYesTv, editDialogNotv;
    private AlertDialog editDialog;
    private ImageView iconIv;
    private FragmentManager fragmentManager;
    private List<Fragment> fragmentList;
    private int curPosition = 0, tarPosition = -1;
    public PersonPreviewBean personPreviewBean;
    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg != null) {
                personPreviewBean = (PersonPreviewBean) (msg.getData()).getSerializable("PersonPreviewBean");
            }
        }
    };

    private OkHttpClient okHttpClient;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(personPreviewBean);
    }

    @Override
    protected View getRootView() {
        return rootView = LayoutInflater.from(this).inflate(R.layout.activity_person_mag, null);
    }

    @Override
    protected void initView() {
        initRootView();
        initDialogView();
    }

    private void initRootView() {
        returnRl = (RelativeLayout) rootView.findViewById(R.id.rl_person_manage_return);
        previewRl = (RelativeLayout) rootView.findViewById(R.id.rl_person_manage_preview);
        previewTv = (TextView) rootView.findViewById(R.id.tv_person_manage_preview);
        editRl = (RelativeLayout) rootView.findViewById(R.id.rl_person_manage_edit);
        editTv = (TextView) rootView.findViewById(R.id.tv_person_manage_edit);
        recordRl = (RelativeLayout) rootView.findViewById(R.id.rl_person_manage_record);
        recordTv = (TextView) rootView.findViewById(R.id.tv_person_manage_record);
        iconIv = (ImageView) rootView.findViewById(R.id.iv_person_mag_icon);
    }

    private void initDialogView() {
        editDialogView = View.inflate(this, R.layout.dialog_person_edit, null);
        editDialogNotv = (TextView) editDialogView.findViewById(R.id.tv_dialog_person_edit_no);
        editDialogNotv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editDialog.dismiss();
            }
        });
        editDialogYesTv = (TextView) editDialogView.findViewById(R.id.tv_dialog_person_edit_yes);
        editDialogYesTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editDialog.dismiss();
                changeFragment();
            }
        });
        editDialog = new AlertDialog.Builder(this).setView(editDialogView).create();
        editDialog.setCanceledOnTouchOutside(false);
    }

    @Override
    protected void initData() {
        okHttpClient = new OkHttpClient();
        Fragment picaViewFragment = new PicaViewFragment();
        Fragment editInfoFragment = new EditInfoFragment();
        Fragment deliveryRecordFragment = new DeliveryRecordFragment();
        fragmentList = new ArrayList<>();
        fragmentList.add(picaViewFragment);
        fragmentList.add(editInfoFragment);
        fragmentList.add(deliveryRecordFragment);
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.ll_person_manage_sit, fragmentList.get(curPosition));
        transaction.commit();
    }

    @Override
    protected void setData() {

    }

    @Override
    protected void setListener() {
        returnRl.setOnClickListener(this);
        previewRl.setOnClickListener(this);
        editRl.setOnClickListener(this);
        recordRl.setOnClickListener(this);
        iconIv.setOnClickListener(this);
    }

    @Override
    protected void loadData() {

    }

    private void changeFragment() {
        if (tarPosition != curPosition) {
            switch (tarPosition) {
                case 0:
                    previewTv.setTextColor(Color.parseColor("#ff3e50"));
                    editTv.setTextColor(Color.parseColor("#a0a0a0"));
                    recordTv.setTextColor(Color.parseColor("#a0a0a0"));
                    break;
                case 1:
                    previewTv.setTextColor(Color.parseColor("#a0a0a0"));
                    editTv.setTextColor(Color.parseColor("#ff3e50"));
                    recordTv.setTextColor(Color.parseColor("#a0a0a0"));
                    break;
                case 2:
                    previewTv.setTextColor(Color.parseColor("#a0a0a0"));
                    editTv.setTextColor(Color.parseColor("#a0a0a0"));
                    recordTv.setTextColor(Color.parseColor("#ff3e50"));
                    break;
            }
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.ll_person_manage_sit, fragmentList.get(tarPosition));
            transaction.commit();
            curPosition = tarPosition;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_person_manage_return:
                finish();
                break;
            case R.id.rl_person_manage_preview:
                tarPosition = 0;
                changeFragment();
                break;
            case R.id.rl_person_manage_edit:
                tarPosition = 1;
                if (curPosition != tarPosition) {
                    editDialog.show();
                }
                break;
            case R.id.rl_person_manage_record:
                tarPosition = 2;
                changeFragment();
                break;
            case R.id.iv_person_mag_icon:
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, PermissionConfig.GALLERY);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PermissionConfig.GALLERY && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            if (uri == null) {
                Utils.log(PersonMagActivity.this, "uri == null");
            } else {
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = PersonMagActivity.this.getContentResolver().query(uri, filePathColumn, null, null, null);
                if (cursor == null) {
                    Utils.log(PersonMagActivity.this, "cursor == null");
                } else {
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picPath = cursor.getString(columnIndex);
                    Utils.log(PersonMagActivity.this, "picPath=" + picPath);
                    cursor.close();

                    File file = new File(picPath);

                    Utils.log(PersonMagActivity.this, "file.length=" + file.length());

                    String imgName = file.getName();

                    Utils.log(PersonMagActivity.this, "file.getName = " + file.getName());

                    RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);

                    String url = Utils.getIconUpdateUrl("1", imgName);

                    Utils.log(PersonMagActivity.this, "url=" + url);

                    Request request = new Request.Builder()
                            .url(url)
                            .post(fileBody)
                            .build();

                    okHttpClient.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Utils.log(PersonMagActivity.this, e.getMessage());
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            if (response.isSuccessful()) {
                                String result = response.body().string();
                                Utils.log(PersonMagActivity.this, "result=" + result);
                            } else {
                                Utils.log(PersonMagActivity.this, "response:failure");
                            }
                        }
                    });
                }
            }
        }
    }
}
