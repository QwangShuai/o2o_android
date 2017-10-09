package city.module;


import android.content.Context;
import android.text.TextUtils;

import com.gjzg.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import city.bean.CityBean;
import city.listener.LoadCityListener;
import config.CacheConfig;
import utils.Utils;

public class CityModule implements ICityModule {

    @Override
    public void load(Context context, LoadCityListener loadCityListener) {
        String hotCityJson = Utils.readCache(context, "-100", CacheConfig.hotCity);
        if (!TextUtils.isEmpty(hotCityJson)) {
            try {
                JSONObject objBean = new JSONObject(hotCityJson);
                if (objBean.optInt("code") == 200) {
                    List<CityBean> cityBeanList = new ArrayList<>();
                    CityBean b = new CityBean();
                    b.setId("-1");
                    b.setName("热门城市");
                    cityBeanList.add(b);
                    JSONArray arrData = objBean.optJSONArray("data");
                    for (int i = 0; i < arrData.length(); i++) {
                        JSONObject o = arrData.optJSONObject(i);
                        if (o != null) {
                            CityBean cb = new CityBean();
                            cb.setId(o.optString("r_id"));
                            cb.setName(o.optString("r_name"));
                            cityBeanList.add(cb);
                        }
                    }
                    String comCityJson = Utils.readCache(context, "-100", CacheConfig.comCity);
                    if (!TextUtils.isEmpty(comCityJson)) {
                        try {
                            JSONObject objBean0 = new JSONObject(comCityJson);
                            if (objBean.optInt("code") == 200) {
                                JSONObject objData = objBean0.optJSONObject("data");
                                String[] lowerLetter = context.getResources().getStringArray(R.array.lowerletter);
                                for (int i = 0; i < lowerLetter.length; i++) {
                                    JSONArray lowArr = objData.optJSONArray(lowerLetter[i]);
                                    if (lowArr != null) {
                                        CityBean cb = new CityBean();
                                        cb.setId("-1");
                                        cb.setName(lowerLetter[i].toUpperCase());
                                        cityBeanList.add(cb);
                                        for (int j = 0; j < lowArr.length(); j++) {
                                            JSONObject o = lowArr.optJSONObject(j);
                                            if (o != null) {
                                                CityBean c = new CityBean();
                                                c.setId(o.optString("r_id"));
                                                c.setName(o.optString("r_name"));
                                                cityBeanList.add(c);
                                            }
                                        }
                                    }
                                }
                                loadCityListener.loadSuccess(cityBeanList);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
