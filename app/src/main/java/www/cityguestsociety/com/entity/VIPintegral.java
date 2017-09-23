package www.cityguestsociety.com.entity;

import java.util.List;

/**
 * Created by LuoPan on 2017/9/23 9:40.
 */

public class VIPintegral {

    /**
     * code : 1
     * info : 获取成功
     * pagecount : 3
     * zongintegral : 136
     * data : [{"title":"买房3","integral":"50","time":"2017-09-23 09:13"},{"title":"买房2","integral":"12","time":"2017-09-23 09:12"},{"title":"买房1","integral":"12","time":"2017-09-23 09:12"}]
     */

    private int code;
    private String info;
    private String pagecount;
    private String zongintegral;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getPagecount() {
        return pagecount;
    }

    public void setPagecount(String pagecount) {
        this.pagecount = pagecount;
    }

    public String getZongintegral() {
        return zongintegral;
    }

    public void setZongintegral(String zongintegral) {
        this.zongintegral = zongintegral;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * title : 买房3
         * integral : 50
         * time : 2017-09-23 09:13
         */

        private String title;
        private String integral;
        private String time;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getIntegral() {
            return integral;
        }

        public void setIntegral(String integral) {
            this.integral = integral;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}
