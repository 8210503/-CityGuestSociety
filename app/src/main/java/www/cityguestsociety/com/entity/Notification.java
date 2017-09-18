package www.cityguestsociety.com.entity;

import java.util.List;

/**
 * Created by LuoPan on 2017/9/14 11:51.
 */

public class Notification {


    /**
     * pagecount : 4
     * info : 获取成功
     * data : [{"release_time":"123123","id":"4","notice_type":"1","state":"0","content":"你的余额不足","member_id":"26"},{"release_time":"12321","id":"3","notice_type":"3","state":"0","content":"你的余额不足","member_id":"26"},{"release_time":"21321","id":"2","notice_type":"2","state":"1","content":"你的余额不足","member_id":"26"},{"release_time":"123123","id":"1","notice_type":"缴费通知","state":"1","content":"你的余额不足","member_id":"26"}]
     * code : 1
     */

    private String pagecount;
    private String info;
    private int code;
    private List<DataBean> data;

    public String getPagecount() {
        return pagecount;
    }

    public void setPagecount(String pagecount) {
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
         * release_time : 123123
         * id : 4
         * notice_type : 1
         * state : 0
         * content : 你的余额不足
         * member_id : 26
         */

        private String release_time;
        private String id;
        private String notice_type;
        private String state;
        private String content;
        private String member_id;

        public String getRelease_time() {
            return release_time;
        }

        public void setRelease_time(String release_time) {
            this.release_time = release_time;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getNotice_type() {
            return notice_type;
        }

        public void setNotice_type(String notice_type) {
            this.notice_type = notice_type;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getMember_id() {
            return member_id;
        }

        public void setMember_id(String member_id) {
            this.member_id = member_id;
        }
    }
}
