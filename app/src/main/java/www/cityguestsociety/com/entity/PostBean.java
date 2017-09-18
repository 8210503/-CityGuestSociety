package www.cityguestsociety.com.entity;

import java.util.List;

/**
 * Created by LuoPan on 2017/9/15 9:51.
 */

public class PostBean {


    /**
     * pagecount : 3
     * info : 获取成功
     * data : [{"member":{"nickname":"i被拒绝","img":"http://120.26.141.238/chengkehui/Uploads/touxiang/2017-09-15/59bb25e5ddf0c.png"},"release_time":"1970.01.01  08:00","title":"分享","id":"4","comment":"123","img":[{"img":"/chengkehui/Uploads/Attract/2017-09-13/59b8a0abb4d0c.jpg"},{"img":"/chengkehui/Uploads/CarpenterList/2017-09-13/59b8d366237f3.png"},{"img":"/chengkehui/Uploads/Activity/2017-09-13/59b8a19d630f1.jpg"}],"collection_num":"12","member_id":"26"},{"member":{"nickname":"i被拒绝","img":"http://120.26.141.238/chengkehui/Uploads/touxiang/2017-09-15/59bb25e5ddf0c.png"},"release_time":"1970.01.01  08:00","title":"分享","id":"3","comment":"12","img":[{"img":"/chengkehui/Uploads/Activity/2017-09-13/59b8e9f3aed8a.png"},{"img":"/chengkehui/Uploads/CarpenterList/2017-09-13/59b8d366237f3.png"},{"img":"/chengkehui/Uploads/Activity/2017-09-13/59b8a19d630f1.jpg"}],"collection_num":"132","member_id":"26"},{"member":{"nickname":"i被拒绝","img":"http://120.26.141.238/chengkehui/Uploads/touxiang/2017-09-15/59bb25e5ddf0c.png"},"release_time":"1970.01.01  08:00","title":"分享","id":"1","comment":"123","img":[{"img":"/chengkehui/Uploads/Activity/2017-09-13/59b8e9f3aed8a.png"},{"img":"/chengkehui/Uploads/Activity/2017-09-13/59b8bf922e0d4.jpg"},{"img":"/chengkehui/Uploads/CarpenterList/2017-09-13/59b8d366237f3.png"}],"collection_num":"213","member_id":"26"}]
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
         * member : {"nickname":"i被拒绝","img":"http://120.26.141.238/chengkehui/Uploads/touxiang/2017-09-15/59bb25e5ddf0c.png"}
         * release_time : 1970.01.01  08:00
         * title : 分享
         * id : 4
         * comment : 123
         * img : [{"img":"/chengkehui/Uploads/Attract/2017-09-13/59b8a0abb4d0c.jpg"},{"img":"/chengkehui/Uploads/CarpenterList/2017-09-13/59b8d366237f3.png"},{"img":"/chengkehui/Uploads/Activity/2017-09-13/59b8a19d630f1.jpg"}]
         * collection_num : 12
         * member_id : 26
         */

        private MemberBean member;
        private String release_time;
        private String title;
        private String id;
        private String comment;
        private String collection_num;
        private String member_id;
        private List<ImgBean> img;

        public MemberBean getMember() {
            return member;
        }

        public void setMember(MemberBean member) {
            this.member = member;
        }

        public String getRelease_time() {
            return release_time;
        }

        public void setRelease_time(String release_time) {
            this.release_time = release_time;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getCollection_num() {
            return collection_num;
        }

        public void setCollection_num(String collection_num) {
            this.collection_num = collection_num;
        }

        public String getMember_id() {
            return member_id;
        }

        public void setMember_id(String member_id) {
            this.member_id = member_id;
        }

        public List<ImgBean> getImg() {
            return img;
        }

        public void setImg(List<ImgBean> img) {
            this.img = img;
        }

        public static class MemberBean {
            /**
             * nickname : i被拒绝
             * img : http://120.26.141.238/chengkehui/Uploads/touxiang/2017-09-15/59bb25e5ddf0c.png
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

        public static class ImgBean {
            /**
             * img : /chengkehui/Uploads/Attract/2017-09-13/59b8a0abb4d0c.jpg
             */

            private String img;

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }
        }
    }
}
