package www.cityguestsociety.com.entity;

import java.util.List;

/**
 * Created by LuoPan on 2017/9/22 15:59.
 */

public class HouseInfo {

    /**
     * pagecount : 0
     * info : 查询成功
     * data : [{"community":"北京1","city":"北京","name":"我就是老小","phone":"15680969037","room":"北京单元房号测试2","ban":"北京楼栋号"},{"speed":[{"time":"2017-09-22","content":"进度1"},{"time":"2017-09-22","content":"进度2"},{"time":"2017-09-22","content":"进度34s"}]}]
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
         * community : 北京1
         * city : 北京
         * name : 我就是老小
         * phone : 15680969037
         * room : 北京单元房号测试2
         * ban : 北京楼栋号
         * speed : [{"time":"2017-09-22","content":"进度1"},{"time":"2017-09-22","content":"进度2"},{"time":"2017-09-22","content":"进度34s"}]
         */

        private String community;
        private String city;
        private String name;
        private String phone;
        private String room;
        private String ban;
        private List<SpeedBean> speed;

        public String getCommunity() {
            return community;
        }

        public void setCommunity(String community) {
            this.community = community;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
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

        public List<SpeedBean> getSpeed() {
            return speed;
        }

        public void setSpeed(List<SpeedBean> speed) {
            this.speed = speed;
        }

        public static class SpeedBean {
            @Override
            public String toString() {
                return "SpeedBean{" +
                        "time='" + time + '\'' +
                        ", content='" + content + '\'' +
                        '}';
            }

            /**
             * time : 2017-09-22
             * content : 进度1
             */



            private String time;
            private String content;

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }
        }
    }
}
