package www.cityguestsociety.com.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by LuoPan on 2017/9/18 11:51.
 */

public class Ban implements Serializable {

    /**
     * pagecount : 0
     * info : 获取成功
     * data : [{"id":"11","ban":"华贸大厦"},{"id":"12","ban":"保利大厦"}]
     * code : 1
     */

    private int pagecount;
    private String info;
    private int code;
    private List<DataBean> data;

    public int getPagecount() {
        return pagecount;
    }

    public void setPagecount(int pagecount) {
        this.pagecount = pagecount;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        /**
         * id : 11
         * ban : 华贸大厦
         */

        private String id;
        private String ban;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getBan() {
            return ban;
        }

        public void setBan(String ban) {
            this.ban = ban;
        }
    }
}
