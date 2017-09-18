package www.cityguestsociety.com.entity;

import java.util.List;

/**
 * Created by LuoPan on 2017/9/15 13:32.
 */

public class House {

    /**
     * code : 1
     * info : 获取成功
     * pagecount : 0
     * data : [{"id":"8","city":"1","community":"1","ban":"1","room":"1"}]
     */

    private int code;
    private String info;
    private int pagecount;
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

    public int getPagecount() {
        return pagecount;
    }

    public void setPagecount(int pagecount) {
        this.pagecount = pagecount;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        @Override
        public String toString() {
            return "DataBean{" +
                    "id='" + id + '\'' +
                    ", city='" + city + '\'' +
                    ", community='" + community + '\'' +
                    ", ban='" + ban + '\'' +
                    ", room='" + room + '\'' +
                    '}';
        }

        /**
         * id : 8
         * city : 1
         * community : 1
         * ban : 1
         * room : 1
         */

        private String id;
        private String city;
        private String community;
        private String ban;
        private String room;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCommunity() {
            return community;
        }

        public void setCommunity(String community) {
            this.community = community;
        }

        public String getBan() {
            return ban;
        }

        public void setBan(String ban) {
            this.ban = ban;
        }

        public String getRoom() {
            return room;
        }

        public void setRoom(String room) {
            this.room = room;
        }
    }
}
