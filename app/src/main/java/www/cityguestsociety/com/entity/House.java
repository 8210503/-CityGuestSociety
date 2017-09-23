package www.cityguestsociety.com.entity;

import java.util.List;

/**
 * Created by LuoPan on 2017/9/15 13:32.
 */

public class House {


    /**
     * pagecount : 0
     * info : 获取成功
     * data : [{"community":"大厦","speed":0,"id":"14","city":"四川","room":"佛尔沙特","ban":"保利大厦"},{"community":"北京1","speed":"0","id":"22","city":"北京","room":"北京单元房号测试1","ban":"北京楼栋号"}]
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

    public static class DataBean {
        /**
         * community : 大厦
         * speed : 0
         * id : 14
         * city : 四川
         * room : 佛尔沙特
         * ban : 保利大厦
         */

        private String community;
        private int speed;
        private String id;
        private String city;
        private String room;
        private String ban;

        public String getCommunity() {
            return community;
        }

        public void setCommunity(String community) {
            this.community = community;
        }

        public int getSpeed() {
            return speed;
        }

        public void setSpeed(int speed) {
            this.speed = speed;
        }

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

        public String getRoom() {
            return room;
        }

        public void setRoom(String room) {
            this.room = room;
        }

        public String getBan() {
            return ban;
        }

        public void setBan(String ban) {
            this.ban = ban;
        }
    }
}
