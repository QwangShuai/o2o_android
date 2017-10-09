package personmanage.view;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gjzg.R;

import java.util.ArrayList;
import java.util.List;

import config.PermissionConfig;
import fragment.EditInfoFragment;
import fragment.PicaViewFragment;
import personmanage.bean.PersonPreviewBean;
import personmanage.presenter.IPersonInfoManagePresenter;
import personmanage.presenter.PersonInfoManagePresenter;
import utils.Utils;
import view.CProgressDialog;

public class PersonInfoManageActivity extends AppCompatActivity implements IPersonInfoManageActivity, View.OnClickListener {

    private View rootView, editDialogView;
    private RelativeLayout returnRl, previewRl, editRl;
    private TextView previewTv, editTv, editDialogYesTv, editDialogNotv;
    private AlertDialog editDialog;
    private ImageView iconIv;
    private CProgressDialog cpd;
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

    private IPersonInfoManagePresenter iPersonInfoManagePresenter = new PersonInfoManagePresenter(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        rootView = LayoutInflater.from(this).inflate(R.layout.activity_person_mag, null);
        setContentView(rootView);
        initView();
        initData();
        setListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(personPreviewBean);
    }

    private void initView() {
        initRootView();
        initDialogView();
    }

    private void initRootView() {
        returnRl = (RelativeLayout) rootView.findViewById(R.id.rl_person_manage_return);
        previewRl = (RelativeLayout) rootView.findViewById(R.id.rl_person_manage_preview);
        previewTv = (TextView) rootView.findViewById(R.id.tv_person_manage_preview);
        editRl = (RelativeLayout) rootView.findViewById(R.id.rl_person_manage_edit);
        editTv = (TextView) rootView.findViewById(R.id.tv_person_manage_edit);
        iconIv = (ImageView) rootView.findViewById(R.id.iv_person_mag_icon);
        cpd = Utils.initProgressDialog(PersonInfoManageActivity.this, cpd);
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

    private void initData() {
        Fragment picaViewFragment = new PicaViewFragment();
        Fragment editInfoFragment = new EditInfoFragment();
        fragmentList = new ArrayList<>();
        fragmentList.add(picaViewFragment);
        fragmentList.add(editInfoFragment);
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.ll_person_manage_sit, fragmentList.get(curPosition));
        transaction.commit();
    }

    private void setListener() {
        returnRl.setOnClickListener(this);
        previewRl.setOnClickListener(this);
        editRl.setOnClickListener(this);
        iconIv.setOnClickListener(this);
    }

    private void changeFragment() {
        if (tarPosition != curPosition) {
            switch (tarPosition) {
                case 0:
                    previewTv.setTextColor(Color.parseColor("#ff3e50"));
                    editTv.setTextColor(Color.parseColor("#a0a0a0"));
                    break;
                case 1:
                    previewTv.setTextColor(Color.parseColor("#a0a0a0"));
                    editTv.setTextColor(Color.parseColor("#ff3e50"));
                    break;
                default:
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
                Utils.log(PersonInfoManageActivity.this, "uri == null");
            } else {
                iPersonInfoManagePresenter.upLoadIcon(PersonInfoManageActivity.this, "1", uri);
//                String[] filePathColumn = {MediaStore.Images.Media.DATA};
//                Cursor cursor = PersonInfoManageActivity.this.getContentResolver().query(uri, filePathColumn, null, null, null);
//                if (cursor == null) {
//                    Utils.log(PersonInfoManageActivity.this, "cursor == null");
//                } else {
//                    cursor.moveToFirst();
//                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                    String picPath = cursor.getString(columnIndex);
//                    Utils.log(PersonInfoManageActivity.this, "picPath=" + picPath);
//                    cursor.close();
//                    File file = new File(picPath);
//                    String imgName = file.getName();
//                    String url = Utils.getIconUpdateUrl("1", imgName);
//                    Bitmap bitmap = BitmapFactory.decodeFile(picPath);
//                    String base64 = Utils.Bitmap2StrByBase64(bitmap);
//                    Utils.log(PersonInfoManageActivity.this, "base64=" + base64);
//                    RequestBody baseBody = new FormBody.Builder().add("base64", base64).build();
//                    Request request = new Request.Builder()
//                            .url(url)
//                            .post(baseBody)
//                            .build();
//
//                    okHttpClient.newCall(request).enqueue(new Callback() {
//                        @Override
//                        public void onFailure(Call call, IOException e) {
//                            Utils.log(PersonInfoManageActivity.this, e.getMessage());
//                        }
//
//                        @Override
//                        public void onResponse(Call call, Response response) throws IOException {
//                            if (response.isSuccessful()) {
//                                String result = response.body().string();
//                                Utils.log(PersonInfoManageActivity.this, "result=" + result);
//                            } else {
//                                Utils.log(PersonInfoManageActivity.this, "response:failure");
//                            }
//                        }
//                    });
//                }
            }
        }
    }

    @Override
    public void showLoading() {
        cpd.show();
    }

    @Override
    public void hideLoading() {
        cpd.dismiss();
    }

    @Override
    public void showUpLoadIconFailure(String upLoadIconFailure) {
        Utils.log(PersonInfoManageActivity.this, "upLoadIconFailure=" + upLoadIconFailure);
    }

    @Override
    public void showUpLoadIconSuccess(String upLoadIconSuccess, Bitmap bitmap) {
        Utils.log(PersonInfoManageActivity.this, "upLoadIconSuccess=" + upLoadIconSuccess);
        iconIv.setImageBitmap(bitmap);
    }
}
