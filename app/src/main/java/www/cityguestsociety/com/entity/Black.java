package www.cityguestsociety.com.entity;

import java.util.List;

/**
 * Created by LuoPan on 2017/9/25 9:05.
 */

public class Black {

    /**
     * code : 1
     * info : 获取成功
     * pagecount : 0
     * data : [{"id":"4","black_member_id":"4","member":{"nickname":"二点多发发发","img":"http://120.26.141.238/chengkehui/Uploads/touxiang/2017-09-23/59c62c1e28594.jpg"}},{"id":"5","black_member_id":"4","member":{"nickname":"二点多发发发","img":"http://120.26.141.238/chengkehui/Uploads/touxiang/2017-09-23/59c62c1e28594.jpg"}},{"id":"6","black_member_id":"3","member":{"nickname":"XIAO","img":"http://120.26.141.238/chengkehui/Uploads/touxiang/2017-09-23/59c5c28ca4607.png"}},{"id":"7","black_member_id":"3","member":{"nickname":"XIAO","img":"http://120.26.141.238/chengkehui/Uploads/touxiang/2017-09-23/59c5c28ca4607.png"}}]
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
        /**
         * id : 4
         * black_member_id : 4
         * member : {"nickname":"二点多发发发","img":"http://120.26.141.238/chengkehui/Uploads/touxiang/2017-09-23/59c62c1e28594.jpg"}
         */

        private String id;
        private String black_member_id;
        private MemberBean member;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getBlack_member_id() {
            return black_member_id;
        }

        public void setBlack_member_id(String black_member_id) {
            this.black_member_id = black_member_id;
        }

        public MemberBean getMember() {
            return member;
        }

        public void setMember(MemberBean member) {
            this.member = member;
        }

        public static class MemberBean {
            /**
             * nickname : 二点多发发发
             * img : http://120.26.141.238/chengkehui/Uploads/touxiang/2017-09-23/59c62c1e28594.jpg
             */

            private String nickname;
            private String img;

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }
        }
    }
}
