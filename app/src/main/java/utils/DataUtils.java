package utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import accountdetail.bean.AccountDetailBean;
import bean.EvaluateBean;
import bean.PublishBean;
import bean.PublishWorkerBean;
import bean.RedPacketBean;
import bean.SkillBean;
import bean.TaskBean;
import bean.TaskWorkerBean;
import bean.VoucherBean;
import bean.WalletBean;
import bean.WorkerBean;
import bean.EmployerManageBean;
import bean.TalkEmployerBean;
import bean.TalkEmployerWorkerBean;
import usermanage.bean.UserInfoBean;
import bean.WorkerManageBean;

public class DataUtils {

    //工种
    public static List<SkillBean> getSkillBeanList(String json) {
        List<SkillBean> resultList = new ArrayList<>();
        try {
            JSONObject beanObj = new JSONObject(json);
            if (beanObj.optInt("code") == 200) {
                JSONArray dataArr = beanObj.optJSONArray("data");
                if (dataArr != null) {
                    for (int i = 0; i < dataArr.length(); i++) {
                        JSONObject o = dataArr.optJSONObject(i);
                        if (o != null) {
                            SkillBean skillBean = new SkillBean();
                            skillBean.setId(o.optString("s_id"));
                            skillBean.setName(o.optString("s_name"));
                            resultList.add(skillBean);
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    //工人
    public static List<WorkerBean> getWorkerBeanList(String workerJson) {
        List<WorkerBean> workerBeanList = new ArrayList<>();
        try {
            JSONObject beanObj = new JSONObject(workerJson);
            int code = beanObj.optInt("code");
            switch (code) {
                case 1:
                    JSONObject dataObj = beanObj.optJSONObject("data");
                    if (dataObj != null) {
                        JSONArray dataArr = dataObj.optJSONArray("data");
                        if (dataArr != null) {
                            for (int i = 0; i < dataArr.length(); i++) {
                                JSONObject o = dataArr.optJSONObject(i);
                                if (o != null) {
                                    WorkerBean workerBean = new WorkerBean();
                                    workerBean.setWorkerId(o.optString("u_id"));
                                    workerBean.setIcon(o.optString("u_img"));
                                    workerBean.setTitle(o.optString("u_name"));
                                    workerBean.setInfo(o.optString("uei_info"));
                                    workerBean.setStatus(o.optString("u_task_status"));
                                    workerBean.setCollectId(o.optString("f_id"));
                                    workerBean.setFavorite(o.optInt("is_fav"));
                                    workerBean.setPositionX(o.optString("ucp_posit_x"));
                                    workerBean.setPositionY(o.optString("ucp_posit_y"));
                                    workerBean.setSex(o.optString("u_sex"));
                                    workerBean.setMobile(o.optString("u_mobile"));
                                    workerBean.setAddress(o.optString("uei_address"));
                                    workerBean.setSkill(o.optString("u_skills"));
                                    workerBeanList.add(workerBean);
                                }
                            }
                        }
                    }
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return workerBeanList;
    }

    //任务
    public static List<TaskBean> getTaskBeanList(String taskJson) {
        List<TaskBean> taskBeanList = new ArrayList<>();
        try {
            JSONObject beanObj = new JSONObject(taskJson);
            int code = beanObj.optInt("code");
            switch (code) {
                case 200:
                    JSONArray dataArr = beanObj.optJSONArray("data");
                    if (dataArr != null) {
                        for (int i = 0; i < dataArr.length(); i++) {
                            JSONObject obj = dataArr.optJSONObject(i);
                            if (obj != null) {
                                TaskBean taskBean = new TaskBean();
                                List<TaskWorkerBean> taskWorkerBeanList = new ArrayList<>();
                                taskBean.setTaskId(obj.optString("t_id"));
                                taskBean.setIcon(obj.optString("u_img"));
                                taskBean.setTitle(obj.optString("t_title"));
                                taskBean.setInfo(obj.optString("t_info"));
                                taskBean.setStatus(obj.optString("t_status"));
                                taskBean.setFavorite(obj.optInt("favorate"));
                                taskBean.setAuthorId(obj.optString("t_author"));
                                taskBean.setTewId(obj.optString("tew_id"));
                                JSONArray workerArr = obj.optJSONArray("workers");
                                if (workerArr != null) {
                                    for (int j = 0; j < workerArr.length(); j++) {
                                        JSONObject o = workerArr.optJSONObject(j);
                                        if (o != null) {
                                            TaskWorkerBean taskWorkerBean = new TaskWorkerBean();
                                            taskWorkerBean.setTewId(o.optString("tew_id"));
                                            taskWorkerBean.setTewSkill(o.optString("tew_skills"));
                                            taskWorkerBeanList.add(taskWorkerBean);
                                        }
                                    }
                                }
                                taskBean.setTaskWorkerBeanList(taskWorkerBeanList);
                                taskBeanList.add(taskBean);
                            }
                        }
                    }
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return taskBeanList;
    }

    //收藏的任务
    public static List<TaskBean> getCollectTaskList(String json) {
        List<TaskBean> collectTaskList = new ArrayList<>();
        try {
            JSONObject beanObj = new JSONObject(json);
            int code = beanObj.optInt("code");
            switch (code) {
                case 1:
                    JSONObject dataObj = beanObj.optJSONObject("data");
                    if (dataObj != null) {
                        JSONArray dataArr = dataObj.optJSONArray("data");
                        if (dataArr != null) {
                            for (int i = 0; i < dataArr.length(); i++) {
                                JSONObject o = dataArr.optJSONObject(i);
                                if (o != null) {
                                    TaskBean taskBean = new TaskBean();
                                    taskBean.setIcon(o.optString("u_img"));
                                    taskBean.setTitle(o.optString("t_title"));
                                    taskBean.setStatus(o.optString("t_status"));
                                    taskBean.setCollectId(o.optString("f_id"));
                                    collectTaskList.add(taskBean);
                                }
                            }
                        }
                    }
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return collectTaskList;
    }

    //收到的评价
    public static List<EvaluateBean> getEvaluateBeanList(String json) {
        List<EvaluateBean> evaluateBeanList = new ArrayList<>();
        try {
            JSONObject beanObj = new JSONObject(json);
            int code = beanObj.optInt("code");
            switch (code) {
                case 1:
                    JSONObject dataObj = beanObj.optJSONObject("data");
                    if (dataObj != null) {
                        JSONArray dataArr = dataObj.optJSONArray("data");
                        if (dataArr != null) {
                            for (int i = 0; i < dataArr.length(); i++) {
                                JSONObject o = dataArr.optJSONObject(i);
                                if (o != null) {
                                    EvaluateBean evaluateBean = new EvaluateBean();
                                    evaluateBean.setIcon(o.optString("u_img"));
                                    evaluateBean.setInfo(o.optString("tce_desc"));
                                    evaluateBean.setTime(o.optString("tc_in_time"));
                                    evaluateBeanList.add(evaluateBean);
                                }
                            }
                        }
                    }
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return evaluateBeanList;
    }

    //余额
    public static WalletBean getWalletBean(String json) {
        WalletBean walletBean = new WalletBean();
        try {
            JSONObject beanObj = new JSONObject(json);
            int code = beanObj.optInt("code");
            switch (code) {
                case 1:
                    JSONObject dataObj = beanObj.optJSONObject("data");
                    if (dataObj != null) {
                        JSONObject o = dataObj.optJSONObject("data");
                        if (o != null) {
                            walletBean.setOverage(o.optString("uef_overage"));
                        }
                    }
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return walletBean;
    }

    //红包
    public static List<RedPacketBean> getRedPacketBeanList(String json) {
        List<RedPacketBean> redPacketBeanList = new ArrayList<>();
        try {
            JSONObject beanObj = new JSONObject(json);
            int code = beanObj.optInt("code");
            switch (code) {
                case 200:
                    JSONArray dataArr = beanObj.optJSONArray("data");
                    if (dataArr != null) {
                        for (int i = 0; i < dataArr.length(); i++) {
                            JSONObject o = dataArr.optJSONObject(i);
                            if (o != null) {
                                RedPacketBean redPacketBean = new RedPacketBean();
                                redPacketBean.setAmount(o.optString("b_amount"));
                                redPacketBean.setStartTime(o.optString("b_start_time"));
                                redPacketBean.setEndTime(o.optString("b_end_time"));
                                redPacketBeanList.add(redPacketBean);
                            }
                        }
                    }
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return redPacketBeanList;
    }

    //代金券
    public static List<VoucherBean> getVoucherBeanList(String json) {
        List<VoucherBean> voucherBeanList = new ArrayList<>();
        try {
            JSONObject beanObj = new JSONObject(json);
            int code = beanObj.optInt("code");
            switch (code) {
                case 200:
                    JSONArray dataArr = beanObj.optJSONArray("data");
                    if (dataArr != null) {
                        for (int i = 0; i < dataArr.length(); i++) {
                            JSONObject o = dataArr.optJSONObject(i);
                            if (o != null) {
                                VoucherBean voucherBean = new VoucherBean();
                                voucherBean.setAmount(o.optString("b_amount"));
                                voucherBean.setStartTime(o.optString("b_start_time"));
                                voucherBean.setEndTime(o.optString("b_end_time"));
                                voucherBean.setInfo(o.optString("b_info"));
                                voucherBeanList.add(voucherBean);
                            }
                        }
                    }
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return voucherBeanList;
    }

    public static List<WorkerManageBean> getWorkerManageBeanList(String json) {
        List<WorkerManageBean> resultList = new ArrayList<>();
        try {
            JSONObject beanObj = new JSONObject(json);
            if (beanObj.optInt("code") == 200) {
                JSONArray dataArr = beanObj.optJSONArray("data");
                if (dataArr != null) {
                    for (int i = 0; i < dataArr.length(); i++) {
                        JSONObject obj = dataArr.optJSONObject(i);
                        if (obj != null) {
                            WorkerManageBean workerManageBean = new WorkerManageBean();
                            workerManageBean.setIcon(obj.optString("u_img"));
                            workerManageBean.setTitle(obj.optString("t_title"));
                            workerManageBean.setInfo(obj.optString("t_info"));
                            workerManageBean.setO_status(obj.optString("o_status"));
                            workerManageBean.setO_confirm(obj.optString("o_confirm"));
                            resultList.add(workerManageBean);
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    public static List<EmployerManageBean> getEmployerManageBeanList(String json) {
        List<EmployerManageBean> resultList = new ArrayList<>();
        try {
            JSONObject beanObj = new JSONObject(json);
            if (beanObj.optInt("code") == 200) {
                JSONArray dataArr = beanObj.optJSONArray("data");
                if (dataArr != null) {
                    for (int i = 0; i < dataArr.length(); i++) {
                        JSONObject obj = dataArr.optJSONObject(i);
                        if (obj != null) {
                            EmployerManageBean employerManageBean = new EmployerManageBean();
                            employerManageBean.setTaskId(obj.optString("t_id"));
                            employerManageBean.setIcon(obj.optString("u_img"));
                            employerManageBean.setTitle(obj.optString("t_title"));
                            employerManageBean.setInfo(obj.optString("t_info"));
                            employerManageBean.setStatus(obj.optString("t_status"));
                            resultList.add(employerManageBean);
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    public static UserInfoBean getUserInfoBean(String json) {
        UserInfoBean userInfoBean = null;
        try {
            JSONObject objBean = new JSONObject(json);
            if (objBean.optInt("code") == 1) {
                JSONObject objData = objBean.optJSONObject("data");
                if (objData != null) {
                    JSONObject obj = objData.optJSONObject("data");
                    if (obj != null) {
                        userInfoBean = new UserInfoBean();
                        userInfoBean.setU_id(obj.optString("u_id"));
                        userInfoBean.setU_name(obj.optString("u_name"));
                        userInfoBean.setU_mobile(obj.optString("u_mobile"));
                        userInfoBean.setU_phone(obj.optString("u_phone"));
                        userInfoBean.setU_fax(obj.optString("u_fax"));
                        userInfoBean.setU_sex(obj.optString("u_sex"));
                        userInfoBean.setU_in_time(obj.optString("u_in_time"));
                        userInfoBean.setU_online(obj.optString("u_online"));
                        userInfoBean.setU_status(obj.optString("u_status"));
                        userInfoBean.setU_type(obj.optString("u_type"));
                        userInfoBean.setU_task_status(obj.optString("u_task_status"));
                        userInfoBean.setU_skills(obj.optString("u_skills"));
                        userInfoBean.setU_start(obj.optString("u_start"));
                        userInfoBean.setU_credit(obj.optString("u_credit"));
                        userInfoBean.setU_top(obj.optString("u_top"));
                        userInfoBean.setU_recommend(obj.optString("u_recommend"));
                        userInfoBean.setU_jobs_num(obj.optString("u_jobs_num"));
                        userInfoBean.setU_worked_num(obj.optString("u_worked_num"));
                        userInfoBean.setU_high_opinions(obj.optString("u_high_opinions"));
                        userInfoBean.setU_low_opinions(obj.optString("u_low_opinions"));
                        userInfoBean.setU_middle_opinions(obj.optString("u_middle_opinions"));
                        userInfoBean.setU_dissensions(obj.optString("u_dissensions"));
                        userInfoBean.setU_true_name(obj.optString("u_true_name"));
                        userInfoBean.setU_idcard(obj.optString("u_idcard"));
                        userInfoBean.setU_info(obj.optString("u_info"));
                        JSONObject objArea = obj.optJSONObject("area");
                        if (objArea != null) {
                            userInfoBean.setArea_uei_province(objArea.optString("uei_province"));
                            userInfoBean.setArea_uei_city(objArea.optString("uei_city"));
                            userInfoBean.setArea_uei_area(objArea.optString("uei_area"));
                            userInfoBean.setArea_uei_address(objArea.optString("uei_address"));
                            userInfoBean.setArea_user_area_name(objArea.optString("user_area_name"));
                        }
                        userInfoBean.setU_img(obj.optString("u_img"));
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return userInfoBean;
    }

    public static List<AccountDetailBean> getAccountDetailBeanList(String json) {
        List<AccountDetailBean> resultList = new ArrayList<>();
        try {
            JSONObject beanObj = new JSONObject(json);
            if (beanObj.optInt("code") == 1) {
                JSONObject dataObj = beanObj.optJSONObject("data");
                if (dataObj != null) {
                    JSONArray dataArr = dataObj.optJSONArray("data");
                    if (dataArr != null) {
                        for (int i = 0; i < dataArr.length(); i++) {
                            JSONObject obj = dataArr.optJSONObject(i);
                            if (obj != null) {
                                AccountDetailBean adb = new AccountDetailBean();
                                adb.setTitle(obj.optString("type"));
                                adb.setTime(obj.optString("time"));
                                adb.setBalance(obj.optString("balances"));
                                adb.setDes(obj.optString("amount"));
                                resultList.add(adb);
                            }
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    public static TalkEmployerBean getTalkEmployerBean(String json) {
        TalkEmployerBean talkEmployerBean = new TalkEmployerBean();
        try {
            JSONObject beanObj = new JSONObject(json);
            if (beanObj.optInt("code") == 200) {
                JSONObject dataObj = beanObj.optJSONObject("data");
                talkEmployerBean.setIcon(dataObj.optString("u_img"));
                talkEmployerBean.setMobile(dataObj.optString("u_mobile"));
                talkEmployerBean.setName(dataObj.optString("u_true_name"));
                talkEmployerBean.setSex(dataObj.optString("u_sex"));
                talkEmployerBean.setAddress(dataObj.optString("tew_address"));
                talkEmployerBean.setDesc(dataObj.optString("t_desc"));
                talkEmployerBean.setPosX(dataObj.optString("t_posit_x"));
                talkEmployerBean.setPosY(dataObj.optString("t_posit_y"));
                JSONArray workerArr = dataObj.optJSONArray("t_workers");
                if (workerArr != null) {
                    List<TalkEmployerWorkerBean> talkEmployerWorkerBeanList = new ArrayList<>();
                    for (int i = 0; i < workerArr.length(); i++) {
                        JSONObject obj = workerArr.optJSONObject(i);
                        if (obj != null) {
                            if (obj.optInt("remaining") == 1) {
                                TalkEmployerWorkerBean t = new TalkEmployerWorkerBean();
                                t.setId(obj.optString("tew_skills"));
                                t.setAmount(obj.optString("tew_worker_num"));
                                t.setPrice(obj.optString("tew_price"));
                                t.setStartTime(obj.optString("tew_start_time"));
                                t.setEndTime(obj.optString("tew_end_time"));
                                talkEmployerWorkerBeanList.add(t);
                            }
                        }
                    }
                    talkEmployerBean.setTalkEmployerWorkerBeanList(talkEmployerWorkerBeanList);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return talkEmployerBean;
    }

    public static List<String> getSkillNameList(String skillJson, List<String> skillIdList) {
        List<String> resultList = new ArrayList<>();
        try {
            JSONObject beanObj = new JSONObject(skillJson);
            if (beanObj.optInt("code") == 200) {
                JSONArray dataArr = beanObj.optJSONArray("data");
                if (dataArr != null) {
                    for (int i = 0; i < skillIdList.size(); i++) {
                        String id = skillIdList.get(i);
                        for (int j = 0; j < dataArr.length(); j++) {
                            JSONObject o = dataArr.optJSONObject(j);
                            if (id.equals(o.optString("s_id"))) {
                                resultList.add(o.optString("s_name"));
                            }
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return resultList;
    }


    public static String getPublishJson(PublishBean publishBean, boolean havePass) {
        JSONObject object = new JSONObject();
        try {
            if (havePass) {
                object.put("u_pass", publishBean.getPass());
            }
            object.put("t_title", publishBean.getTitle());
            object.put("t_info", publishBean.getInfo());
            object.put("t_posit_x", publishBean.getPositionX());
            object.put("t_posit_y", publishBean.getPositionY());
            object.put("t_author", publishBean.getAuthorId());
            object.put("t_type", publishBean.getTypeId());
            object.put("province", publishBean.getProvinceId());
            object.put("city", publishBean.getCityId());
            object.put("area", publishBean.getAreaId());
            object.put("address", publishBean.getAddress());
            object.put("t_storage", "0");
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < publishBean.getPublishWorkerBeanList().size(); i++) {
                PublishWorkerBean publishWorkerBean = publishBean.getPublishWorkerBeanList().get(i);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("skill", publishWorkerBean.getId());
                jsonObject.put("personNum", publishWorkerBean.getAmount());
                jsonObject.put("money", publishWorkerBean.getSalary());
                jsonObject.put("startTime", publishWorkerBean.getStartTime());
                jsonObject.put("endTime", publishWorkerBean.getEndTime());
                jsonArray.put(jsonObject);
            }
            object.put("worker", jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object.toString();
    }
}
