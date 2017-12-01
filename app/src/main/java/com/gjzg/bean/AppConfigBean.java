package com.gjzg.bean;

/**
 * Created by Administrator on 2017/11/29.
 */

public class AppConfigBean {

    /**
     * code : 1
     * data : {"data":{"charge_rate":"0.1","official_website":"www.baidu.com","service_telephone":"13163675676","token_valid":"2"}}
     */

    private int code;
    private DataBeanX data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    public static class DataBeanX {
        /**
         * data : {"charge_rate":"0.1","official_website":"www.baidu.com","service_telephone":"13163675676","token_valid":"2"}
         */

        private DataBean data;

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * charge_rate : 0.1
             * official_website : www.baidu.com
             * service_telephone : 13163675676
             * token_valid : 2
             */

            private String charge_rate;
            private String official_website;
            private String service_telephone;
            private String token_valid;

            public String getCharge_rate() {
                return charge_rate;
            }

            public void setCharge_rate(String charge_rate) {
                this.charge_rate = charge_rate;
            }

            public String getOfficial_website() {
                return official_website;
            }

            public void setOfficial_website(String official_website) {
                this.official_website = official_website;
            }

            public String getService_telephone() {
                return service_telephone;
            }

            public void setService_telephone(String service_telephone) {
                this.service_telephone = service_telephone;
            }

            public String getToken_valid() {
                return token_valid;
            }

            public void setToken_valid(String token_valid) {
                this.token_valid = token_valid;
            }
        }
    }
}
