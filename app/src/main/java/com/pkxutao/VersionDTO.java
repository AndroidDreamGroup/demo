package com.pkxutao;

import java.io.Serializable;

/**
 * Created by snowaves on 2015/12/3.
 */
public class VersionDTO implements Serializable {

    /**
     * Status : 0
     * Msg : 操作成功
     * Data : {"VersionNo":"1.9","Client":2,"Description":"测试修改","IsForceUpdate":false,"LowestVersionNo":"1.1","CreateTime":"2015-11-20T11:30:46.88","VersionChannel":{"AppVersionId":2,"IsEnable":true,"DownloadUrl":"http://m.estay.com/qrcode/v2.html?from=beta","ChannelKey":"beta","CreateTime":"2015-11-20T14:00:11.153","Id":2}}
     */

    private int Status;
    private String Msg;
    /**
     * VersionNo : 1.9
     * Client : 2
     * Description : 测试修改
     * IsForceUpdate : false
     * LowestVersionNo : 1.1
     * CreateTime : 2015-11-20T11:30:46.88
     * VersionChannel : {"AppVersionId":2,"IsEnable":true,"DownloadUrl":"http://m.estay.com/qrcode/v2.html?from=beta","ChannelKey":"beta","CreateTime":"2015-11-20T14:00:11.153","Id":2}
     */

    private DataEntity Data;

    public void setStatus(int Status) {
        this.Status = Status;
    }

    public void setMsg(String Msg) {
        this.Msg = Msg;
    }

    public void setData(DataEntity Data) {
        this.Data = Data;
    }

    public int getStatus() {
        return Status;
    }

    public String getMsg() {
        return Msg;
    }

    public DataEntity getData() {
        return Data;
    }

    public static class DataEntity {
        private String VersionNo;
        private int Client;
        private String Description;
        private boolean IsForceUpdate;
        private String LowestVersionNo;
        private String CreateTime;
        /**
         * AppVersionId : 2
         * IsEnable : true
         * DownloadUrl : http://m.estay.com/qrcode/v2.html?from=beta
         * ChannelKey : beta
         * CreateTime : 2015-11-20T14:00:11.153
         * Id : 2
         */

        private VersionChannelEntity VersionChannel;

        public void setVersionNo(String VersionNo) {
            this.VersionNo = VersionNo;
        }

        public void setClient(int Client) {
            this.Client = Client;
        }

        public void setDescription(String Description) {
            this.Description = Description;
        }

        public void setIsForceUpdate(boolean IsForceUpdate) {
            this.IsForceUpdate = IsForceUpdate;
        }

        public void setLowestVersionNo(String LowestVersionNo) {
            this.LowestVersionNo = LowestVersionNo;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }

        public void setVersionChannel(VersionChannelEntity VersionChannel) {
            this.VersionChannel = VersionChannel;
        }

        public String getVersionNo() {
            return VersionNo;
        }

        public int getClient() {
            return Client;
        }

        public String getDescription() {
            return Description;
        }

        public boolean isIsForceUpdate() {
            return IsForceUpdate;
        }

        public String getLowestVersionNo() {
            return LowestVersionNo;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public VersionChannelEntity getVersionChannel() {
            return VersionChannel;
        }

        public static class VersionChannelEntity {
            private int AppVersionId;
            private boolean IsEnable;
            private String DownloadUrl;
            private String ChannelKey;
            private String CreateTime;
            private String LowestVersionNo;
            private int Id;

            public String getLowestVersionNo() {
                return LowestVersionNo;
            }

            public void setAppVersionId(int AppVersionId) {
                this.AppVersionId = AppVersionId;
            }

            public void setIsEnable(boolean IsEnable) {
                this.IsEnable = IsEnable;
            }

            public void setDownloadUrl(String DownloadUrl) {
                this.DownloadUrl = DownloadUrl;
            }

            public void setChannelKey(String ChannelKey) {
                this.ChannelKey = ChannelKey;
            }

            public void setCreateTime(String CreateTime) {
                this.CreateTime = CreateTime;
            }

            public void setId(int Id) {
                this.Id = Id;
            }

            public int getAppVersionId() {
                return AppVersionId;
            }

            public boolean isIsEnable() {
                return IsEnable;
            }

            public String getDownloadUrl() {
                return DownloadUrl;
            }

            public String getChannelKey() {
                return ChannelKey;
            }

            public String getCreateTime() {
                return CreateTime;
            }

            public int getId() {
                return Id;
            }
        }
    }
}
