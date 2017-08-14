package com.zocki.entity;

import com.zocki.framelibrary.entity.BaseEntity;

import java.util.List;

public class RecoverEntity extends BaseEntity{

    /**
     * msg : 首页推荐列表获取成功
     * data : {"recommendList":[{"jumpType":1,"recommendId":7,"belongsCity":"深圳","createTime":1494493151000,"telephone":"18677340005","userName":"雷孟琦","materialId":"3_1494495491097659922_jpg","userId":24,"urlLink":"","desc":"","lastUpdateTime":1500970362000},{"jumpType":1,"recommendId":4,"belongsCity":"北京","createTime":1494491764000,"telephone":"18677340002","userName":"模特莫妮卡","materialId":"3_1494555180103470695_jpg","userId":21,"urlLink":"","desc":"","lastUpdateTime":1500970362000},{"jumpType":1,"recommendId":9,"belongsCity":"北京","createTime":1494494535000,"telephone":"18677340006","userName":"著名导演冯导","materialId":"3_1494495434862867828_jpg","userId":25,"urlLink":"","desc":"","lastUpdateTime":1500287664000},{"jumpType":1,"recommendId":5,"belongsCity":"重庆","createTime":1494491775000,"telephone":"18677340003","userName":"演员梦想家夏目","materialId":"3_1494495476121760206_jpg","userId":22,"urlLink":"","desc":"","lastUpdateTime":1497861108000},{"jumpType":1,"recommendId":40,"belongsCity":"重庆","createTime":1495193998000,"telephone":"13715348131","userName":"偷偷一脸萌比oe","materialId":"3_1495193994419578514_jpg","userId":14,"urlLink":"","desc":"","lastUpdateTime":1497860319000},{"jumpType":1,"recommendId":41,"belongsCity":"重庆","createTime":1495194372000,"telephone":"18677340004","userName":"萌萌","materialId":"3_1495194368959499932_jpg","userId":23,"urlLink":"","desc":"","lastUpdateTime":1497860317000},{"jumpType":1,"recommendId":31,"belongsCity":"上海","createTime":1494942334000,"telephone":"18680751111","userName":"曾小海","materialId":"3_1494942332243365309_png","userId":12,"urlLink":"","desc":"","lastUpdateTime":1497860315000},{"jumpType":1,"recommendId":42,"belongsCity":"深圳","createTime":1495194481000,"telephone":"15812345678","userName":"如初","materialId":"3_1495194478551045477_jpg","userId":19,"urlLink":"","desc":"test","lastUpdateTime":1500604125000},{"jumpType":1,"recommendId":43,"belongsCity":"六个","createTime":1495194524000,"telephone":"18823305131","userName":"改名字了","materialId":"3_1495194521576147259_jpg","userId":17,"urlLink":"","desc":"","lastUpdateTime":1500604161000}],"totalCount":9}
     * errCode : 0
     */

    private String msg;
    private DataBean data;
    private int errCode;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

    public static class DataBean {
        @Override
        public String toString() {
            return "DataBean{" +
                    "totalCount=" + totalCount +
                    ", recommendList=" + recommendList +
                    '}';
        }

        /**
         * recommendList : [{"jumpType":1,"recommendId":7,"belongsCity":"深圳","createTime":1494493151000,"telephone":"18677340005","userName":"雷孟琦","materialId":"3_1494495491097659922_jpg","userId":24,"urlLink":"","desc":"","lastUpdateTime":1500970362000},{"jumpType":1,"recommendId":4,"belongsCity":"北京","createTime":1494491764000,"telephone":"18677340002","userName":"模特莫妮卡","materialId":"3_1494555180103470695_jpg","userId":21,"urlLink":"","desc":"","lastUpdateTime":1500970362000},{"jumpType":1,"recommendId":9,"belongsCity":"北京","createTime":1494494535000,"telephone":"18677340006","userName":"著名导演冯导","materialId":"3_1494495434862867828_jpg","userId":25,"urlLink":"","desc":"","lastUpdateTime":1500287664000},{"jumpType":1,"recommendId":5,"belongsCity":"重庆","createTime":1494491775000,"telephone":"18677340003","userName":"演员梦想家夏目","materialId":"3_1494495476121760206_jpg","userId":22,"urlLink":"","desc":"","lastUpdateTime":1497861108000},{"jumpType":1,"recommendId":40,"belongsCity":"重庆","createTime":1495193998000,"telephone":"13715348131","userName":"偷偷一脸萌比oe","materialId":"3_1495193994419578514_jpg","userId":14,"urlLink":"","desc":"","lastUpdateTime":1497860319000},{"jumpType":1,"recommendId":41,"belongsCity":"重庆","createTime":1495194372000,"telephone":"18677340004","userName":"萌萌","materialId":"3_1495194368959499932_jpg","userId":23,"urlLink":"","desc":"","lastUpdateTime":1497860317000},{"jumpType":1,"recommendId":31,"belongsCity":"上海","createTime":1494942334000,"telephone":"18680751111","userName":"曾小海","materialId":"3_1494942332243365309_png","userId":12,"urlLink":"","desc":"","lastUpdateTime":1497860315000},{"jumpType":1,"recommendId":42,"belongsCity":"深圳","createTime":1495194481000,"telephone":"15812345678","userName":"如初","materialId":"3_1495194478551045477_jpg","userId":19,"urlLink":"","desc":"test","lastUpdateTime":1500604125000},{"jumpType":1,"recommendId":43,"belongsCity":"六个","createTime":1495194524000,"telephone":"18823305131","userName":"改名字了","materialId":"3_1495194521576147259_jpg","userId":17,"urlLink":"","desc":"","lastUpdateTime":1500604161000}]
         * totalCount : 9
         */

        private int totalCount;
        private List<RecommendListBean> recommendList;

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }

        public List<RecommendListBean> getRecommendList() {
            return recommendList;
        }

        public void setRecommendList(List<RecommendListBean> recommendList) {
            this.recommendList = recommendList;
        }

        public static class RecommendListBean {
            @Override
            public String toString() {
                return "RecommendListBean{" +
                        "jumpType=" + jumpType +
                        ", recommendId=" + recommendId +
                        ", belongsCity='" + belongsCity + '\'' +
                        ", createTime=" + createTime +
                        ", telephone='" + telephone + '\'' +
                        ", userName='" + userName + '\'' +
                        ", materialId='" + materialId + '\'' +
                        ", userId=" + userId +
                        ", urlLink='" + urlLink + '\'' +
                        ", desc='" + desc + '\'' +
                        ", lastUpdateTime=" + lastUpdateTime +
                        '}';
            }

            /**
             * jumpType : 1
             * recommendId : 7
             * belongsCity : 深圳
             * createTime : 1494493151000
             * telephone : 18677340005
             * userName : 雷孟琦
             * materialId : 3_1494495491097659922_jpg
             * userId : 24
             * urlLink :
             * desc :
             * lastUpdateTime : 1500970362000
             */

            private int jumpType;
            private int recommendId;
            private String belongsCity;
            private long createTime;
            private String telephone;
            private String userName;
            private String materialId;
            private int userId;
            private String urlLink;
            private String desc;
            private long lastUpdateTime;

            public int getJumpType() {
                return jumpType;
            }

            public void setJumpType(int jumpType) {
                this.jumpType = jumpType;
            }

            public int getRecommendId() {
                return recommendId;
            }

            public void setRecommendId(int recommendId) {
                this.recommendId = recommendId;
            }

            public String getBelongsCity() {
                return belongsCity;
            }

            public void setBelongsCity(String belongsCity) {
                this.belongsCity = belongsCity;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public String getTelephone() {
                return telephone;
            }

            public void setTelephone(String telephone) {
                this.telephone = telephone;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public String getMaterialId() {
                return materialId;
            }

            public void setMaterialId(String materialId) {
                this.materialId = materialId;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public String getUrlLink() {
                return urlLink;
            }

            public void setUrlLink(String urlLink) {
                this.urlLink = urlLink;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public long getLastUpdateTime() {
                return lastUpdateTime;
            }

            public void setLastUpdateTime(long lastUpdateTime) {
                this.lastUpdateTime = lastUpdateTime;
            }
        }
    }

    @Override
    public String toString() {
        return "RecoverEntity{" +
                "msg='" + msg + '\'' +
                ", data=" + data +
                ", errCode=" + errCode +
                '}';
    }
}
