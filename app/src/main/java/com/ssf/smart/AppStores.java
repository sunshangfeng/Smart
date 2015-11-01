package com.ssf.smart;

import java.util.List;

/**
 * Created by Android on 2015/10/30.
 */
public class AppStores {


    /**
     * status : 1
     * data : [{"StoresID":"1","StoresName":"酒仙桥店","StoresAddress":"通惠国际","AddTime":"2015-08-31 16:29:57","CompanyID":"1","Longitude":"116.498662","Latitude":"39.985034","Color":"7ea3fe"},{"StoresID":"2","StoresName":"国贸店","StoresAddress":"国贸店","AddTime":"2015-08-05 16:32:45","CompanyID":"1","Longitude":"","Latitude":"","Color":"01c0e3"},{"StoresID":"3","StoresName":"朝外店","StoresAddress":"朝外店","AddTime":"2015-08-05 16:33:00","CompanyID":"1","Longitude":"","Latitude":"","Color":"064993"},{"StoresID":"7","StoresName":"北京店","StoresAddress":"北京","AddTime":"2015-08-31 17:19:41","CompanyID":"1","Longitude":"116.441400","Latitude":"39.922820","Color":"5081ff"},{"StoresID":"8","StoresName":"1","StoresAddress":"1","AddTime":"2015-08-28 17:32:05","CompanyID":"1","Longitude":"","Latitude":"","Color":"4ee600"}]
     * msg :
     */

    private int status;
    private String msg;
    /**
     * StoresID : 1
     * StoresName : 酒仙桥店
     * StoresAddress : 通惠国际
     * AddTime : 2015-08-31 16:29:57
     * CompanyID : 1
     * Longitude : 116.498662
     * Latitude : 39.985034
     * Color : 7ea3fe
     */

    private List<DataEntity> data;

    public void setStatus(int status) {
        this.status = status;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public static class DataEntity {
        private String StoresID;
        private String StoresName;
        private String StoresAddress;
        private String AddTime;
        private String CompanyID;
        private String Longitude;
        private String Latitude;
        private String Color;

        public void setStoresID(String StoresID) {
            this.StoresID = StoresID;
        }

        public void setStoresName(String StoresName) {
            this.StoresName = StoresName;
        }

        public void setStoresAddress(String StoresAddress) {
            this.StoresAddress = StoresAddress;
        }

        public void setAddTime(String AddTime) {
            this.AddTime = AddTime;
        }

        public void setCompanyID(String CompanyID) {
            this.CompanyID = CompanyID;
        }

        public void setLongitude(String Longitude) {
            this.Longitude = Longitude;
        }

        public void setLatitude(String Latitude) {
            this.Latitude = Latitude;
        }

        public void setColor(String Color) {
            this.Color = Color;
        }

        public String getStoresID() {
            return StoresID;
        }

        public String getStoresName() {
            return StoresName;
        }

        public String getStoresAddress() {
            return StoresAddress;
        }

        public String getAddTime() {
            return AddTime;
        }

        public String getCompanyID() {
            return CompanyID;
        }

        public String getLongitude() {
            return Longitude;
        }

        public String getLatitude() {
            return Latitude;
        }

        public String getColor() {
            return Color;
        }
    }
}
