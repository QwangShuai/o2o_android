package fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.gjzg.R;

import java.io.IOException;
import java.util.List;

import activity.CityActivity;
import activity.JobActivity;
import activity.WorkerKindActivity;
import activity.LeftRightActivity;
import activity.SendJobActivity;
import bean.CityBean;
import cache.LruJsonCache;
import config.CacheConfig;
import config.IntentConfig;
import config.NetConfig;
import config.PermissionConfig;
import listener.OnLoadComCityListener;
import listener.OnLoadHotCityListener;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import utils.Utils;
import view.CProgressDialog;

//首页
public class FpFragment extends CommonFragment implements View.OnClickListener, OnLoadHotCityListener, OnLoadComCityListener {

    private View rootView;
    private RelativeLayout cityRl, msgRl;
    private ImageView findWorkerIv, findJobIv, sendJobIv;
    private TextView cityTv;
    private String cityId;
    private String localCityId;
    private String localCity;
    private CProgressDialog cpd;

    private LocationClient locationClient;
    private BDLocationListener bdLocationListener;
    private OkHttpClient okHttpClient;
    private LruJsonCache lruJsonCache;
    private String userId = "-100";
    private String cacheTime = "60";
    private boolean loadHot = false, loadCom = false;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg != null) {
                switch (msg.what) {
                    case 1:
                        cityTv.setText(localCity);
                        loadCityData();
                        break;
                    case 2:
                        toGetCityId();
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
        handler.removeMessages(1);
        handler.removeMessages(2);
    }

    @Override
    protected View getRootView() {
        return rootView = LayoutInflater.from(getActivity()).inflate(R.layout.frag_first_page, null);
    }

    @Override
    protected void initView() {
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

    @Override
    protected void initData() {
        initLocation();
        okHttpClient = new OkHttpClient();
        lruJsonCache = LruJsonCache.get(getActivity());
    }

    private void initLocation() {
        locationClient = new LocationClient(getActivity());
        bdLocationListener = new MyLocationListener();
        locationClient.registerLocationListener(bdLocationListener);
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");
        //可选，默认gcj02，设置返回的定位结果坐标系
        int span = 0;
        option.setScanSpan(span);
        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);
        //可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);
        //可选，默认false,设置是否使用gps
        option.setLocationNotify(true);
        //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);
        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);
        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);
        //可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);
        //可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        locationClient.setLocOption(option);
    }

    private void toLocation() {
        cpd.show();
        locationClient.start();
    }

    @Override
    protected void setData() {
    }

    @Override
    protected void setListener() {
        cityRl.setOnClickListener(this);
        msgRl.setOnClickListener(this);
        findWorkerIv.setOnClickListener(this);
        findJobIv.setOnClickListener(this);
        sendJobIv.setOnClickListener(this);
        lruJsonCache.setOnLoadHotCityListener(this);
        lruJsonCache.setOnLoadComCityListener(this);
    }

    @Override
    protected void loadData() {
        checkLocPermisson();
    }

    private void loadCityData() {
        if (TextUtils.isEmpty(Utils.readCache(getActivity(), userId, CacheConfig.hotCity))) {
            Request hotRequest = new Request.Builder().url(NetConfig.baseCityUrl + NetConfig.hotCityUrl).get().build();
            okHttpClient.newCall(hotRequest).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String result = response.body().string();
                        Utils.log(getActivity(), "hot=" + result);
                        Utils.writeCache(getActivity(), userId, CacheConfig.hotCity, result, cacheTime);
                    }
                }
            });
        } else {
            loadHot = true;
            handler.sendEmptyMessage(2);
        }
        if (TextUtils.isEmpty(Utils.readCache(getActivity(), userId, CacheConfig.comCity))) {
            Request comRequest = new Request.Builder().url(NetConfig.baseCityUrl + NetConfig.letterCityUrl).get().build();
            okHttpClient.newCall(comRequest).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String result = response.body().string();
                        Utils.log(getActivity(), "com=" + result);
                        Utils.writeCache(getActivity(), userId, CacheConfig.comCity, result, cacheTime);
                    }
                }
            });
        } else {
            loadCom = true;
            handler.sendEmptyMessage(2);
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
                startActivity(new Intent(getActivity(), JobActivity.class));
                break;
            case R.id.iv_frag_first_page_send_job:
                startActivity(new Intent(getActivity(), SendJobActivity.class));
                break;
            default:
                break;
        }
    }

    private void toGetCityId() {
        String json = lruJsonCache.getAsString(userId + CacheConfig.comCity);
        if (!TextUtils.isEmpty(json)) {
            localCityId = Utils.getLocCityId(getActivity(), localCity, json);
            cpd.dismiss();
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
                Utils.toast(getActivity(), "localCityId:" + localCityId + "\ncityId:" + cityId);
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
                }
                break;
        }
    }

    @Override
    public void comResult() {
        loadCom = true;
        handler.sendEmptyMessage(2);
    }

    @Override
    public void hotResult() {
        loadHot = true;
        handler.sendEmptyMessage(2);
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
            handler.sendEmptyMessage(1);
        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }
    }
}
