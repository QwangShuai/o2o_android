package firstpage.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.gjzg.R;

import java.util.List;

import leftright.view.LeftRightActivity;
import city.bean.CityBean;
import city.view.CityActivity;
import config.CacheConfig;
import config.IntentConfig;
import config.NetConfig;
import config.PermissionConfig;
import firstpage.presenter.FirstPagePresenter;
import firstpage.presenter.IFirstPagePresenter;
import jobinfo.view.JobInfoActivity;
import publishjob.PublishJobActivity;
import utils.Utils;
import view.CProgressDialog;
import workerkind.view.WorkerKindActivity;

public class FirstPageFragment extends Fragment implements IFirstPageFragment, View.OnClickListener {

    private View rootView;
    private RelativeLayout cityRl, msgRl;
    private ImageView findWorkerIv, findJobIv, sendJobIv;
    private TextView cityTv;
    private String cityId;
    private String localCityId;
    private String localCity;
    private CProgressDialog cpd;
    private boolean loadHot, loadCom;
    private String userId = "-100";
    private LocationClient locationClient;
    private BDLocationListener bdLocationListener;
    private IFirstPagePresenter iFirstPagePresenter = new FirstPagePresenter(this);

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg != null) {
                switch (msg.what) {
                    case 0:
                        cityTv.setText(localCity);
                        loadCityData();
                        break;
                    case 1:
                        if (loadHot && loadCom)
                            cpd.dismiss();
                        break;
                    default:
                        break;
                }
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (iFirstPagePresenter != null) {
            iFirstPagePresenter.destroy();
            iFirstPagePresenter = null;
        }
        locationClient.unRegisterLocationListener(bdLocationListener);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.frag_first_page, null);
        initView();
        initData();
        setListener();
        loadData();
        return rootView;
    }

    private void initView() {
        initRootView();
    }

    private void initRootView() {
        cityRl = (RelativeLayout) rootView.findViewById(R.id.rl_frag_first_page_city);
        msgRl = (RelativeLayout) rootView.findViewById(R.id.rl_frag_first_page_msg);
        findWorkerIv = (ImageView) rootView.findViewById(R.id.iv_frag_first_page_find_worker);
        findJobIv = (ImageView) rootView.findViewById(R.id.iv_frag_first_page_find_job);
        sendJobIv = (ImageView) rootView.findViewById(R.id.iv_frag_first_page_send_job);
        cityTv = (TextView) rootView.findViewById(R.id.tv_fp_city);
        cpd = Utils.initProgressDialog(getActivity(), cpd);
    }

    private void initData() {
        initLocation();
    }

    private void initLocation() {
        locationClient = new LocationClient(getActivity());
        bdLocationListener = new MyLocationListener();
        locationClient.registerLocationListener(bdLocationListener);
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType("bd09ll");
        int span = 0;
        option.setScanSpan(span);
        option.setIsNeedAddress(true);
        option.setOpenGps(true);
        option.setLocationNotify(true);
        option.setIsNeedLocationDescribe(true);
        option.setIsNeedLocationPoiList(true);
        option.setIgnoreKillProcess(false);
        option.SetIgnoreCacheException(false);
        option.setEnableSimulateGps(false);
        locationClient.setLocOption(option);
    }

    private void toLocation() {
        locationClient.start();
    }

    private void setListener() {
        cityRl.setOnClickListener(this);
        msgRl.setOnClickListener(this);
        findWorkerIv.setOnClickListener(this);
        findJobIv.setOnClickListener(this);
        sendJobIv.setOnClickListener(this);
    }

    private void loadData() {
        checkLocPermisson();
    }

    private void loadCityData() {
        if (TextUtils.isEmpty(Utils.readCache(getActivity(), userId, CacheConfig.hotCity))) {
            iFirstPagePresenter.loadHotCity(NetConfig.baseCityUrl + NetConfig.hotCityUrl);
        } else {
            loadHot = true;
            handler.sendEmptyMessage(1);
        }
        if (TextUtils.isEmpty(Utils.readCache(getActivity(), userId, CacheConfig.comCity))) {
            iFirstPagePresenter.loadComCity(NetConfig.baseCityUrl + NetConfig.letterCityUrl);
        } else {
            loadCom = true;
            handler.sendEmptyMessage(1);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_frag_first_page_city:
                Intent cityIntent = new Intent(getActivity(), CityActivity.class);
                CityBean c = new CityBean();
                c.setId(localCityId);
                c.setName(localCity);
                cityIntent.putExtra(IntentConfig.LOCAL_CITY, c);
                startActivityForResult(cityIntent, IntentConfig.CITY_REQUEST);
                break;
            case R.id.rl_frag_first_page_msg:
                Intent msgIntent = new Intent(getActivity(), LeftRightActivity.class);
                msgIntent.putExtra(IntentConfig.intentName, IntentConfig.MESSAGE);
                startActivity(msgIntent);
                break;
            case R.id.iv_frag_first_page_find_worker:
                startActivity(new Intent(getActivity(), WorkerKindActivity.class));
                break;
            case R.id.iv_frag_first_page_find_job:
                startActivity(new Intent(getActivity(), JobInfoActivity.class));
                break;
            case R.id.iv_frag_first_page_send_job:
                startActivity(new Intent(getActivity(), PublishJobActivity.class));
                break;
            default:
                break;
        }
    }

    private void checkLocPermisson() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int permisson = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION);
            if (permisson != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PermissionConfig.LOCATION);
            } else {
                toLocation();
            }
        } else {
            toLocation();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IntentConfig.CITY_REQUEST && resultCode == IntentConfig.CITY_RESULT && data != null) {
            CityBean cityBean = (CityBean) data.getSerializableExtra(IntentConfig.CITY);
            if (cityBean != null) {
                cityTv.setText(cityBean.getName());
                cityId = cityBean.getId();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PermissionConfig.LOCATION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    toLocation();
                } else {
                    Utils.toast(getActivity(), "请在系统设置中打开定位权限");
                    getActivity().finish();
                }
                break;
        }
    }

    @Override
    public void showLoading() {
        cpd.show();
    }

    @Override
    public void showHotJson(String hotJson) {
        iFirstPagePresenter.saveHotCity(getActivity(), "-100", hotJson, "60");
        iFirstPagePresenter.loadComCity(NetConfig.baseCityUrl + NetConfig.letterCityUrl);
    }

    @Override
    public void showComJson(String comJson) {
        iFirstPagePresenter.saveComCity(getActivity(), "-100", comJson, "60");
    }

    @Override
    public void showSaveHotJsonSuccess() {
        loadHot = true;
        handler.sendEmptyMessage(1);
    }

    @Override
    public void showSaveComJsonSuccess() {
        iFirstPagePresenter.getLocateCityId(getActivity(), userId, localCity);
        loadCom = true;
        handler.sendEmptyMessage(1);
    }

    @Override
    public void getLocateCityId(String cityId) {
        localCityId = cityId;
    }

    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            //获取定位结果
            StringBuffer sb = new StringBuffer(256);
            sb.append("time : ");
            sb.append(location.getTime());    //获取定位时间
            sb.append("\nerror code : ");
            sb.append(location.getLocType());    //获取类型类型
            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());    //获取纬度信息
            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());    //获取经度信息
            sb.append("\nradius : ");
            sb.append(location.getRadius());    //获取定位精准度
            if (location.getLocType() == BDLocation.TypeGpsLocation) {
                // GPS定位结果
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());    // 单位：公里每小时
                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());    //获取卫星数
                sb.append("\nheight : ");
                sb.append(location.getAltitude());    //获取海拔高度信息，单位米
                sb.append("\ndirection : ");
                sb.append(location.getDirection());    //获取方向信息，单位度
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());    //获取地址信息
                sb.append("\ndescribe : ");
                sb.append("gps定位成功");
            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
                // 网络定位结果
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());    //获取地址信息
                sb.append("\noperationers : ");
                sb.append(location.getOperators());    //获取运营商信息
                sb.append("\ndescribe : ");
                sb.append("网络定位成功");
            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {
                // 离线定位结果
                sb.append("\ndescribe : ");
                sb.append("离线定位成功，离线定位结果也是有效的");
            } else if (location.getLocType() == BDLocation.TypeServerError) {
                sb.append("\ndescribe : ");
                sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                sb.append("\ndescribe : ");
                sb.append("网络不同导致定位失败，请检查网络是否通畅");
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                sb.append("\ndescribe : ");
                sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
            }
            sb.append("\nlocationdescribe : ");
            sb.append(location.getLocationDescribe());    //位置语义化信息
            List<Poi> list = location.getPoiList();    // POI数据
            if (list != null) {
                sb.append("\npoilist size = : ");
                sb.append(list.size());
                for (Poi p : list) {
                    sb.append("\npoi= : ");
                    sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
                }
            }
            String str = sb.toString();
            int provinceIndex = str.indexOf("省");
            int cityIndex = str.indexOf("市");
            localCity = str.substring(provinceIndex + 1, cityIndex);
            handler.sendEmptyMessage(0);
        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }
    }
}
